package me.devtools4.crypto;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.time.Instant;
import java.util.UUID;
import me.devtools4.crypto.dto.avro.TradeEvent;
import org.junit.Test;

public class SerdeTest {

  @Test
  public void testSerde() throws IOException {
    TradeEvent event = TradeEvent.newBuilder()
        .setUuid(UUID.randomUUID().toString())
        .setTimeExchange(Instant.now())
        .setTimeCoinapi(Instant.now())
        .setPrice(417.02200000)
        .setSize(0.01400000)
        .setTakerSide("SELL")
        .setSymbolId("BINANCE_SPOT_AAVE_USDT")
        .setSequence(210084)
        .build();

    byte[] bytes = Serde.serialize(event, event.getSchema());
    assertNotNull(bytes);

    TradeEvent t = Serde.deserialize(bytes, event.getSchema());
    assertNotNull(t);
  }
}