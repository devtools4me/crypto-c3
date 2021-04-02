package me.devtools4.crypto.cache.config;

import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import me.devtools4.crypto.Serde;
import me.devtools4.crypto.dto.avro.OhlcvEvent;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteDataStreamer;
import org.apache.ignite.stream.StreamMultipleTupleExtractor;
import org.apache.ignite.stream.kafka.KafkaStreamer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

@Slf4j
public class StreamerConfig {

  @Autowired
  private KafkaProps kafkaProps;

  @Bean(initMethod = "start", destroyMethod = "stop")
  public KafkaStreamer<String, OhlcvEvent> ohlcvEventStreamer(
      Ignite ignite,
      IgniteDataStreamer<String, OhlcvEvent> igniteDataStreamer,
      StreamMultipleTupleExtractor<ConsumerRecord, String, OhlcvEvent> extractor) {
    log.info("kafkaProps={}", kafkaProps);
    KafkaStreamer<String, OhlcvEvent> kafkaStreamer = new KafkaStreamer<>();
    kafkaStreamer.setIgnite(ignite);
    kafkaStreamer.setStreamer(igniteDataStreamer);
    kafkaStreamer.setTopic(List.of(kafkaProps.getTopics().getOhlcvEvent()));
    kafkaStreamer.setThreads(kafkaProps.getThreads());
    kafkaStreamer.setConsumerConfig(kafkaProps.configs());
    kafkaStreamer.setMultipleTupleExtractor(extractor);
    return kafkaStreamer;
  }

  @Bean(destroyMethod = "close")
  public IgniteDataStreamer<String, OhlcvEvent> ohlcvEventIgniteDataStreamer(Ignite ignite) {
    IgniteDataStreamer<String, OhlcvEvent> stmr = ignite
        .dataStreamer(OhlcvEvent.class.getCanonicalName());
    stmr.allowOverwrite(true);
    stmr.autoFlushFrequency(100);
    return stmr;
  }

  @Bean
  public StreamMultipleTupleExtractor<ConsumerRecord<String, byte[]>, String, OhlcvEvent> streamSingleTupleExtractor() {
    return x -> {
      log.info("Received message, key={}", x.key());
      OhlcvEvent e = Serde.deserialize(x.value(), OhlcvEvent.getClassSchema());
      log.info("Received event={}", e);
      return Map.of(x.key(), e);
    };
  }
}