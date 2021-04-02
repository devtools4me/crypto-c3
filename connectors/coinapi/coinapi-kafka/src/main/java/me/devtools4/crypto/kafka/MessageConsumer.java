package me.devtools4.crypto.kafka;

import io.coinapi.websocket.model.MessageBase;
import io.coinapi.websocket.model.MessageTypeEnum;
import io.coinapi.websocket.model.OHLCV;
import io.coinapi.websocket.model.Trades;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import me.devtools4.crypto.Serde;
import me.devtools4.crypto.coinapi.Ops;
import me.devtools4.crypto.dto.avro.OhlcvEvent;
import me.devtools4.crypto.dto.avro.TradeEvent;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

@Slf4j
public class MessageConsumer implements Consumer<MessageBase> {

  private final KafkaProducer<String, byte[]> kafkaProducer;
  private final CompletableExecutor executor;

  public MessageConsumer(
      KafkaProducer<String, byte[]> kafkaProducer,
      CompletableExecutor executor) {
    this.kafkaProducer = kafkaProducer;
    this.executor = executor;
  }

  @Override
  public void accept(MessageBase message) {
    switch (MessageTypeEnum.valueOf(message.getType())) {
      case trade: {
        var event = Ops.event((Trades) message);
        send(new ProducerRecord<>(
            TradeEvent.class.getCanonicalName(),
            event.getSymbolId(),
            Serde.serialize(event, event.getSchema())));
        break;
      }
      case ohlcv: {
        var event = Ops.event((OHLCV) message);
        send(new ProducerRecord<>(
            OhlcvEvent.class.getCanonicalName(),
            event.getSymbolId(),
            Serde.serialize(event, event.getSchema())));
        break;
      }
      default:
    }
  }

  private void send(ProducerRecord<String, byte[]> r) {
    executor.execute(f -> kafkaProducer.send(r, (m, e) -> {
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