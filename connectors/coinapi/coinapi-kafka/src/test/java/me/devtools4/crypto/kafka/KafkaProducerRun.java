package me.devtools4.crypto.kafka;

import com.dslplatform.json.DslJson;
import io.coinapi.rest.model.Ohlcv;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import lombok.extern.slf4j.Slf4j;
import me.devtools4.crypto.Serde;
import me.devtools4.crypto.coinapi.Ops;
import me.devtools4.crypto.dto.avro.OhlcvEvent;
import org.apache.commons.io.IOUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;

@Slf4j
public class KafkaProducerRun {

  public static void main(String[] args) throws Exception {
    var props = new Properties();
    props.putAll(Map.of(
        "acks", "1",
        "clientId", "test",
        "bootstrap.servers", "localhost:9092"
    ));
    var exec = new CompletableExecutor(new ForkJoinPool());

    var kafkaProducer = new KafkaProducer<>(
        props,
        new StringSerializer(),
        new ByteArraySerializer());
    try (var in = KafkaProducerRun.class.getClassLoader()
        .getResourceAsStream("ohlcv-event.json")) {
      var s = IOUtils.toString(in, Charset.defaultCharset());
      var ohlcv = Ops.fromJson(new DslJson<>(), s, Ohlcv.class);
      var event = Ops.event("BITSTAMP_SPOT_BTC_USD", "1MIN", ohlcv);
      send(exec, kafkaProducer, new ProducerRecord<>(OhlcvEvent.class.getCanonicalName(),
          UUID.randomUUID().toString(),
          Serde.serialize(event, event.getSchema()))).get();
    }
  }

  private static <K, V> CompletableFuture<Object> send(CompletableExecutor executor,
      KafkaProducer<K, V> kafkaProducer,
      ProducerRecord<K, V> r) {
    return executor.execute(f -> kafkaProducer.send(r, (m, e) -> {
      if (m != null) {
        log.info("topic={}, partition={}, offset={}", m.topic(), m.partition(), m.offset());
        f.complete(m);
      } else {
        log.error(e.getMessage(), e);
        f.completeExceptionally(e);
      }
    }));
  }
}