package me.devtools4.crypto.coinapi.service;

import io.coinapi.websocket.model.Hello;
import io.coinapi.ws.CoinAPIWebSocket;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CoinapiWsStarter {
  private final CoinAPIWebSocket ws;
  private final String apiKey;
  private final String[] types;

  public CoinapiWsStarter(CoinAPIWebSocket ws, String apiKey, String[] types) {
    this.ws = ws;
    this.apiKey = apiKey;
    this.types = types;
  }

  public void start() throws IOException {
    Hello hello = new Hello();
    hello.setApikey(apiKey);
    hello.setSubscribe_data_type(types);
    log.info("hello={}", hello);

    ws.sendHelloMessage(hello);
  }
}