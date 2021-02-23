package me.devtools4.crypto.coinapi;

import com.dslplatform.json.DslJson;
import io.coinapi.rest.model.Ohlcv;
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

  public static OhlcvEvent event(String symbol_id, String period_id, Ohlcv other) {
    return OhlcvEvent.newBuilder()
        .setSymbolId(symbol_id)
        .setPeriodId(period_id)
        .setTimePeriodStart(other.getTime_period_start().toInstant())
        .setTimePeriodEnd(other.getTime_period_end().toInstant())
        .setTimeOpen(other.getTime_open().toInstant())
        .setTimeClose(other.getTime_close().toInstant())
        .setPriceOpen(other.getPrice_open())
        .setPriceHigh(other.getPrice_high())
        .setPriceLow(other.getPrice_low())
        .setPriceClose(other.getPrice_close())
        .setVolumeTraded(other.getVolume_traded())
        .setTradesCount(other.getTrades_count())
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