package me.devtools4.crypto.cache;

import static org.junit.Assert.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import feign.Feign;
import feign.Logger.ErrorLogger;
import feign.Logger.Level;
import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import lombok.extern.slf4j.Slf4j;
import me.devtools4.crypto.cache.api.MetricsApi;
import me.devtools4.crypto.cache.config.TestContextInitializer;
import me.devtools4.crypto.cache.jmx.CacheOps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@Slf4j
@ContextConfiguration(classes = {
    CryptoCacheApp.class
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

  private MetricsApi metricsApi() {
    return Feign.builder()
        .logger(new ErrorLogger())
        .logLevel(Level.BASIC)
        .target(MetricsApi.class, "http://localhost:" + port);
  }
}