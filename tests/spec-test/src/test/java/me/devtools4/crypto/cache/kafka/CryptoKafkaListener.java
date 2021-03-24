package me.devtools4.crypto.cache.kafka;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import me.devtools4.crypto.Serde;
import me.devtools4.crypto.dto.avro.OhlcvEvent;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
public class CryptoKafkaListener {

  private final List<Pair<String, OhlcvEvent>> events = new CopyOnWriteArrayList<>();

  public List<Pair<String, OhlcvEvent>> getEvents() {
    return events;
  }

  public void clear() {
    events.clear();
  }

  public Supplier<Optional<Pair<String, OhlcvEvent>>> supplier() {
    return () -> getEvents()
        .stream()
        .findAny();
  }

  @KafkaListener(topics = "${kafka.topics.ohlcvEvent}", groupId = "test")
  public void handle(ConsumerRecord<String, byte[]> record) {
    var key = record.key();
    log.info("Received message, key={}", key);
    OhlcvEvent event = Serde.deserialize(record.value(), OhlcvEvent.getClassSchema());
    events.add(Pair.of(key, event));
  }
}