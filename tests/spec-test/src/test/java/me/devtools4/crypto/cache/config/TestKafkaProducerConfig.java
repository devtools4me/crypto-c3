package me.devtools4.crypto.cache.config;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;

public class TestKafkaProducerConfig {

  @Bean
  public KafkaProducer<String, byte[]> producer(KafkaProps props) {
    return new KafkaProducer<>(props.configs(), new StringSerializer(), new ByteArraySerializer());
  }
}