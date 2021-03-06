package me.devtools4.crypto.cache;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import feign.Feign;
import feign.Logger.ErrorLogger;
import feign.Logger.Level;
import java.util.Arrays;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import me.devtools4.crypto.Serde;
import me.devtools4.crypto.cache.api.MetricsApi;
import me.devtools4.crypto.cache.config.KafkaProps;
import me.devtools4.crypto.cache.config.TestContextInitializer;
import me.devtools4.crypto.cache.jmx.CacheOps;
import me.devtools4.crypto.cache.kafka.CryptoKafkaListener;
import me.devtools4.crypto.dto.avro.OhlcvEvent;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@Slf4j
@ContextConfiguration(classes = {
    CryptoCacheApp.class,
    TestKafkaConsumerConfig.class,
    TestKafkaProducerConfig.class
}, initializers = TestContextInitializer.class)
@SpringBootTest(
    webEnvironment = RANDOM_PORT
)
@ActiveProfiles({
    "local",
    "test"
})
@EmbeddedKafka(partitions = 1)
public class CryptoCacheAppSteps {
  @Autowired
  private CacheOps cacheOps;

  @Autowired
  private CryptoKafkaListener listener;

  @Autowired
  private KafkaProducer<String, byte[]> kafkaProducer;

  @Autowired
  private KafkaProps kafkaProps;

  @LocalServerPort
  private Integer port;

  private Queue<String> responses = new ConcurrentLinkedQueue<>();

  @Given("^Cache is available")
  public void setupDownstream() {
    String topology = cacheOps.topology();
    log.info("topology={}", topology);

    Arrays.stream(cacheOps.caches().split(", "))
        .map(cacheOps::clear)
        .forEach(log::info);
  }

  @When("^OHLCV event is available in (.+) sec with details$")
  public void ohlcvEventIsPresent(Integer timeout, DataTable table) throws Exception {
    var details = table.asList(OhlcvEventDetails.class).get(0);
    var event = Executors.newSingleThreadExecutor().submit(() -> {
      while (true) {
        var opt = listener.getEvents()
            .stream()
            .findAny();
        if (opt.isPresent()) {
          return opt.get();
        }
        Thread.sleep(500);
      }
    }).get(timeout, TimeUnit.SECONDS);
    log.info("event={}", event);
    assertNotNull(event);
    assertThat(event.getSymbolId(), is(details.getSymbolId()));
  }

  @When("^OHLCV event was populated with details$")
  public void ohlcvEventPopulated(DataTable table) {
    var details = table.asList(OhlcvEventDetails.class).get(0);
    var event = OhlcvEvent.newBuilder()
        .setSymbolId(details.getSymbolId())
    .build();

    kafkaProducer.send(new ProducerRecord<>(kafkaProps.getTopics().getOhlcvEvent(),
        UUID.randomUUID().toString(),
        Serde.serialize(event, event.getSchema())));
  }

  @When("^User send metrics request")
  public void metrics() {
    String response = metricsApi().metrics();
    log.info("response={}", response);
    responses.add(response);
  }

  @Then("^System responded")
  public void verifyResponse() {
    String response = responses.peek();
    assertNotNull(response);
  }

  @Data
  @ToString
  public static class OhlcvEventDetails {
    private String symbolId;
  }

  private MetricsApi metricsApi() {
    return Feign.builder()
        .logger(new ErrorLogger())
        .logLevel(Level.BASIC)
        .target(MetricsApi.class, "http://localhost:" + port);
  }
}