package me.devtools4.crypto.cache.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@Profile("!test")
public class KafkaConfig {

  @Bean
  public KafkaProps kafkaProps() {
    return new KafkaProps();
  }
}