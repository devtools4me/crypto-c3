package me.devtools4.crypto.kafka;

import io.coinapi.websocket.model.Trades;
import me.devtools4.crypto.dto.avro.TradeEvent;

public class Ops {

  private Ops() {
  }

  public static TradeEvent event(Trades t) {
    return TradeEvent.newBuilder()
        .setSymbolId(t.getSymbolId())
        .setSequence(t.getSequence())
        .setTimeExchange(t.getTimeExchange().toInstant())
        .setTimeCoinapi(t.getTimeCoinApi().toInstant())
        .setUuid(str(t.getUuid()))
        .setPrice(t.getPrice())
        .setSize(t.getSize())
        .setTakerSide(t.getTakerSide())
        .build();
  }

  private static String str(Object o) {
    return o == null ? null : o.toString();
  }
}