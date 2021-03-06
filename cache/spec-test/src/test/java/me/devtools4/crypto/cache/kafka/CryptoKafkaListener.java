package me.devtools4.crypto.cache.kafka;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import me.devtools4.crypto.Serde;
import me.devtools4.crypto.dto.avro.OhlcvEvent;
import org.springframework.kafka.annotation.KafkaListener;

public class CryptoKafkaListener {
  private final List<OhlcvEvent> events = new CopyOnWriteArrayList<>();

  @KafkaListener(topics = "${kafka.topics.ohlcvEvent}", groupId = "test")
  public void handle(String key, byte[] value) {
    events.add(Serde.deserialize(value, OhlcvEvent.getClassSchema()));
  }
}