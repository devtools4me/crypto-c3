package me.devtools4.crypto.coinapi;

import com.dslplatform.json.DslJson;
import io.coinapi.websocket.model.OHLCV;
import io.coinapi.websocket.model.Trades;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import me.devtools4.crypto.dto.avro.OhlcvEvent;
import me.devtools4.crypto.dto.avro.TradeEvent;

public class Ops {

  private Ops() {
  }

  public static TradeEvent event(Trades other) {
    return TradeEvent.newBuilder()
        .setSymbolId(other.getSymbolId())
        .setSequence(other.getSequence())
        .setTimeExchange(other.getTimeExchange().toInstant())
        .setTimeCoinapi(other.getTimeCoinApi().toInstant())
        .setUuid(str(other.getUuid()))
        .setPrice(other.getPrice())
        .setSize(other.getSize())
        .setTakerSide(other.getTakerSide())
        .build();
  }

  public static OhlcvEvent event(OHLCV other) {
    return OhlcvEvent.newBuilder()
        .setSymbolId(other.getSymbolId())
        .setSequence(other.getSequence())
        .setPeriodId(other.getPeriodId())
        .setTimePeriodStart(other.getTimePeriodStart().toInstant())
        .setTimePeriodEnd(other.getTimePeriodEnd().toInstant())
        .setTimeOpen(other.getTimeOpen().toInstant())
        .setTimeClose(other.getTimeClose().toInstant())
        .setPriceOpen(other.getPriceOpen())
        .setPriceHigh(other.getPriceHigh())
        .setPriceLow(other.getPriceLow())
        .setPriceClose(other.getPriceClose())
        .setVolumeTraded(other.getVolumeTraded())
        .setTradesCount(other.getTradesCount())
        .build();
  }

  @SuppressWarnings("unchecked")
  public static <T> T fromJson(DslJson dsl, String json, Class<T> type) {
    try (var reader = new ByteArrayInputStream(json.getBytes())) {
      return (T) dsl.deserialize(type, reader);
    } catch (IOException e) {
      throw new IllegalArgumentException(e);
    }
  }

  private static String str(Object o) {
    return o == null ? null : o.toString();
  }
}