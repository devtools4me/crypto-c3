package me.devtools4.crypto.cache;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import com.dslplatform.json.DslJson;
import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import feign.Feign;
import feign.Logger.ErrorLogger;
import feign.Logger.Level;
import io.coinapi.rest.model.Ohlcv;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import me.devtools4.crypto.Serde;
import me.devtools4.crypto.cache.api.MetricsApi;
import me.devtools4.crypto.cache.api.impl.OhlcvEventQueryService;
import me.devtools4.crypto.cache.config.KafkaProps;
import me.devtools4.crypto.cache.config.TestConfig;
import me.devtools4.crypto.cache.config.TestContextInitializer;
import me.devtools4.crypto.cache.config.TestKafkaConsumerConfig;
import me.devtools4.crypto.cache.config.TestKafkaProducerConfig;
import me.devtools4.crypto.cache.jmx.CacheOps;
import me.devtools4.crypto.cache.kafka.CryptoKafkaListener;
import me.devtools4.crypto.coinapi.Ops;
import org.apache.commons.io.IOUtils;
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
    TestConfig.class,
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
  private OhlcvEventQueryService ohlcvEventQueryService;

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

    var caches = cacheOps.caches();
    log.info("caches={}", caches);

    Arrays.stream(caches.split(","))
        .map(cacheOps::clear)
        .forEach(log::info);
  }

  @When("^OHLCV event is available in KAFKA in (.+) sec with details$")
  public void ohlcvEventIsPresent(Integer timeout, DataTable table) throws Exception {
    var details = table.asList(OhlcvEventDetails.class).get(0);
    var pair = wait(listener.supplier(), timeout);
    log.info("pair={}", pair);
    assertNotNull(pair);
    assertThat(pair.getRight().getSymbolId(), is(details.getSymbolId()));
  }

  @When("^OHLCV event is available in cache in (.+) with details$")
  public void ohlcvEventIsPopulated(Integer timeout, DataTable table) throws Exception {
    var details = table.asList(OhlcvEventDetails.class).get(0);
    var pair = listener.getEvents().get(0);
    log.info("pair={}", pair);
    assertNotNull(pair);

    var event = wait(() -> ohlcvEventQueryService.all(Set.of(pair.getLeft()))
        .findAny(), timeout);
    log.info("event={}", event);
    assertNotNull(event);
    assertThat(event.getSymbolId(), is(details.getSymbolId()));
  }

  @When("^OHLCV event was populated with details$")
  public void ohlcvEventPopulated(DataTable table) throws IOException {
    var details = table.asList(OhlcvEventDetails.class).get(0);
    try (var is = getClass().getClassLoader().getResourceAsStream(details.fileName)) {
      var s = IOUtils.toString(is, Charset.defaultCharset());
      var ohlcv = Ops.fromJson(new DslJson<>(), s, Ohlcv.class);
      var event = Ops.event("BITSTAMP_SPOT_BTC_USD", "1MIN", ohlcv);
      kafkaProducer.send(new ProducerRecord<>(kafkaProps.getTopics().getOhlcvEvent(),
          UUID.randomUUID().toString(),
          Serde.serialize(event, event.getSchema())));
    }
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
    private String periodId;
    private String fileName;
  }

  private MetricsApi metricsApi() {
    return Feign.builder()
        .logger(new ErrorLogger())
        .logLevel(Level.BASIC)
        .target(MetricsApi.class, "http://localhost:" + port);
  }

  private static <T> T wait(Supplier<Optional<T>> sup, Integer timeout) throws Exception {
    return Executors.newSingleThreadExecutor().submit(() -> {
      while (true) {
        var opt = sup.get();
        if (opt.isPresent()) {
          return opt.get();
        }
        Thread.sleep(500);
      }
    }).get(timeout, TimeUnit.SECONDS);
  }
}