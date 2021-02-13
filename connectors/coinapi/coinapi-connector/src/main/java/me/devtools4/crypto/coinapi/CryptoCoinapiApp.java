package me.devtools4.crypto.coinapi;

import me.devtools4.crypto.coinapi.config.CoinapiConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({
    CoinapiConfig.class
})
public class CryptoCoinapiApp {

  public static void main(String[] args) {
    SpringApplication.run(CryptoCoinapiApp.class, args);
  }
}