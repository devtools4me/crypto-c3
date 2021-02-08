package com.coingecko.api.v3;

import feign.Feign;
import feign.Logger.ErrorLogger;
import feign.Logger.Level;
import feign.Request.Options;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.dsljson.DslJsonDecoder;
import feign.dsljson.DslJsonEncoder;
import java.util.concurrent.TimeUnit;

public class CoinGeckoApiRun {

  public static void main(String args[]) {

    final Decoder decoder = new DslJsonDecoder();
    final Encoder encoder = new DslJsonEncoder();
    CoinGeckoApi api = Feign.builder()
        .encoder(encoder)
        .decoder(decoder)
        .errorDecoder(new CryptoErrorDecoder(decoder))
        .logger(new ErrorLogger())
        .logLevel(Level.BASIC)
        .options(new Options(10, TimeUnit.SECONDS, 60, TimeUnit.SECONDS, true))
        .target(CoinGeckoApi.class, "https://api.coingecko.com/api/v3");

    api.markets("BTC", null, null, null, null, null, null)
        .forEach(System.out::println);
  }
}