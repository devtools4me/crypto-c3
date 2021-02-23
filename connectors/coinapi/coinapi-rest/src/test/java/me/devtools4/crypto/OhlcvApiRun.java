package me.devtools4.crypto;

import feign.Feign;
import feign.Logger.ErrorLogger;
import feign.Logger.Level;
import feign.Request.Options;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.dsljson.DslJsonDecoder;
import feign.dsljson.DslJsonEncoder;
import io.coinapi.rest.CryptoErrorDecoder;
import io.coinapi.rest.OhlcvApi;
import java.util.concurrent.TimeUnit;

public class OhlcvApiRun {

  public static void main(String args[]) {

    final Decoder decoder = new DslJsonDecoder();
    final Encoder encoder = new DslJsonEncoder();
    OhlcvApi api = Feign.builder()
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
        .target(OhlcvApi.class, "https://rest.coinapi.io");

    try {
      api.latest("BITSTAMP_SPOT_BTC_USD", "1MIN", false, 100)
          .forEach(System.out::println);
    } catch (Exception ex) {
      System.out.println("Error=" + ex.getMessage());
    }
  }
}