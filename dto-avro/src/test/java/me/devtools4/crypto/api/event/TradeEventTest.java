package me.devtools4.crypto.api.event;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.UUID;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.junit.Test;

public class TradeEventTest {

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
        .setSequence(210084L)
        .setType(Type.TRADE)
        .build();

    DatumWriter<TradeEvent> userDatumWriter = new SpecificDatumWriter<>(TradeEvent.class);
    File file = new File("event.avro");
    try (DataFileWriter<TradeEvent> dataFileWriter = new DataFileWriter<>(userDatumWriter)) {
      dataFileWriter.create(event.getSchema(), file);
    }

    DatumReader<TradeEvent> userDatumReader = new SpecificDatumReader<>(TradeEvent.class);
    try (DataFileReader<TradeEvent> dataFileReader = new DataFileReader<>(file, userDatumReader)) {
      while (dataFileReader.hasNext()) {
        TradeEvent e = dataFileReader.next();
        assertNotNull(e);
      }
    }
  }
}
