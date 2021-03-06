package me.devtools4.crypto.cache;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import me.devtools4.crypto.cache.config.KafkaProps;
import me.devtools4.crypto.cache.kafka.CryptoKafkaListener;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

@Slf4j
public class TestKafkaConsumerConfig {

  @Bean
  public CryptoKafkaListener cryptoKafkaListener() {
    return new CryptoKafkaListener();
  }

  @Bean
  @SuppressWarnings("unchecked")
  public ConsumerFactory<String, byte[]> consumerFactory(KafkaProps kafkaProps) {
    log.info("kafkaProps={}", kafkaProps);
    return new DefaultKafkaConsumerFactory<>((Map)kafkaProps.getConfigs());
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, byte[]> kafkaListenerContainerFactory(
      ConsumerFactory<String, byte[]> consumerFactory) {
    ConcurrentKafkaListenerContainerFactory<String, byte[]> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory);
    return factory;
  }

  @Bean
  public KafkaProps kafkaProps() {
    return new KafkaProps();
  }
}