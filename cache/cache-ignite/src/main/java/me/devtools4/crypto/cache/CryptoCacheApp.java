package me.devtools4.crypto.cache;

import me.devtools4.crypto.cache.config.CacheConfigs;
import me.devtools4.crypto.cache.config.IgniteConfig;
import me.devtools4.crypto.cache.config.JmxConfig;
import me.devtools4.crypto.cache.config.KafkaConfig;
import me.devtools4.crypto.cache.config.StreamerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.KafkaMetricsAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootApplication(exclude = {
    TransactionAutoConfiguration.class,
    KafkaMetricsAutoConfiguration.class
})
@Import({
    IgniteConfig.class,
    CacheConfigs.class,
    StreamerConfig.class,
    KafkaConfig.class,
    JmxConfig.class
})
public class CryptoCacheApp {

  public static void main(String[] args) {
    SpringApplication.run(CryptoCacheApp.class, args);
  }
}