package me.devtools4.crypto.cache.kafka;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import me.devtools4.crypto.Serde;
import me.devtools4.crypto.dto.avro.OhlcvEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
public class CryptoKafkaListener {

  private final List<OhlcvEvent> events = new CopyOnWriteArrayList<>();

  public List<OhlcvEvent> getEvents() {
    return events;
  }

  public Supplier<Optional<OhlcvEvent>> supplier() {
    return () -> getEvents()
        .stream()
        .findAny();
  }

  @KafkaListener(topics = "${kafka.topics.ohlcvEvent}", groupId = "test")
  public void handle(ConsumerRecord<String, byte[]> record) {
    log.info("Received message, key={}", record.key());
    events.add(Serde.deserialize(record.value(), OhlcvEvent.getClassSchema()));
  }
}