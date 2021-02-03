package com.crypto.c3.coinapi;

import feign.Feign;
import feign.Logger.ErrorLogger;
import feign.Logger.Level;
import feign.Request.Options;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import io.coinapi.rest.model.CryptoErrorDecoder;
import io.coinapi.rest.MetadataApi;
import java.util.concurrent.TimeUnit;

public class MetadataApiRun {

  public static void main(String args[]) {

    final Decoder decoder = new GsonDecoder();
    final Encoder encoder = new GsonEncoder();
    MetadataApi api = Feign.builder()
        .encoder(encoder)
        .decoder(decoder)
        .errorDecoder(new CryptoErrorDecoder(decoder))
        .logger(new ErrorLogger())
        .logLevel(Level.BASIC)
        .requestInterceptor(template -> {
          template.header(
              "X-CoinAPI-Key",
              "76E7E6A1-AED9-4AC0-BFC1-E1C53B45666D");
        })
        .options(new Options(10, TimeUnit.SECONDS, 60, TimeUnit.SECONDS, true))
        .target(MetadataApi.class, "https://rest.coinapi.io");

    try {
//    api.exchanges()
//        .forEach(System.out::println);
//    api.exchange("BINANCE")
//        .forEach(System.out::println);
//    api.exchanges("BINANCE;LMAXDIGITALUAT;COINEX")
//        .forEach(System.out::println);
//    api.exchangesIcons(32)
//        .forEach(System.out::println);
//    api.assets()
//        .forEach(System.out::println);
//    api.asset("GEND")
//        .forEach(System.out::println);
//    api.assets("GEND;YPNG")
//        .forEach(System.out::println);
//    api.assetsIcons(32)
//        .forEach(System.out::println);
//    api.symbols("BINANCE")
//        .forEach(System.out::println);
//    api.symbols("BINANCE")
//        .forEach(System.out::println);
      api.symbols("BINANCE_SPOT_BAT_USDT", "BINANCE", "BAT")
          .forEach(System.out::println);
    } catch (Exception ex) {
      System.out.println("Error=" + ex.getMessage());
    }
  }
}
