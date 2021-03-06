package me.devtools4.crypto.cache.kafka;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import lombok.extern.slf4j.Slf4j;
import me.devtools4.crypto.Serde;
import me.devtools4.crypto.dto.avro.OhlcvEvent;
import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
public class CryptoKafkaListener {
  private final List<OhlcvEvent> events = new CopyOnWriteArrayList<>();

  public List<OhlcvEvent> getEvents() {
    return events;
  }

  @KafkaListener(topics = "${kafka.topics.ohlcvEvent}", groupId = "test")
  public void handle(String key, byte[] value) {
    log.info("Received message, key={}", key);
    events.add(Serde.deserialize(value, OhlcvEvent.getClassSchema()));
  }
}