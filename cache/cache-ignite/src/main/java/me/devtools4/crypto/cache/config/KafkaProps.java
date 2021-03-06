package me.devtools4.crypto.cache.config;

import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ToString
@ConfigurationProperties(prefix = "kafka")
public class KafkaProps {

  private Topics topics;
  private Integer threads;
  private Map<String, String> configs;

  public int threads() {
    return Optional.ofNullable(threads)
        .filter(x -> x > 0)
        .orElse(1);
  }

  public Properties configs() {
    var props = new Properties();
    props.putAll(configs);
    return props;
  }

  @Data
  @ToString
  public static class Topics {

    private String ohlcvEvent;
  }
}