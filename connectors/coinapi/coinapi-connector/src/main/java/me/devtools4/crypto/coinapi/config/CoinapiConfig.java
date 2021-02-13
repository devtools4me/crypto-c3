package me.devtools4.crypto.coinapi.config;

import io.coinapi.ws.CoinAPIWebSocket;
import io.coinapi.ws.CoinAPIWebSocketImpl;
import io.coinapi.ws.InvokeFunction;
import lombok.extern.slf4j.Slf4j;
import me.devtools4.crypto.coinapi.service.CoinapiWsStarter;
import org.springframework.context.annotation.Bean;

@Slf4j
public class CoinapiConfig {

  @Bean(destroyMethod = "closeConnect")
  public CoinAPIWebSocket coinapiWebSocket(CoinapiProps props, InvokeFunction cb) {
    log.info("props={}", props);
    CoinAPIWebSocketImpl ws = new CoinAPIWebSocketImpl(props.wsUri());
    ws.setTradesInvoke(cb);
    ws.setQuotesInvoke(cb);
    ws.setBookInvoke(cb);
    ws.setOHLCVInvoke(cb);
    ws.setVolumeInvoke(cb);
    ws.setErrorInvoke(cb);
    ws.setReconnectInvoke(cb);
    return ws;
  }

  @Bean(initMethod = "start")
  public CoinapiWsStarter CoinapiWsStarter(CoinAPIWebSocket ws, CoinapiProps props) {
    return new CoinapiWsStarter(ws, props.getApiKey(), props.typesArray());
  }

  @Bean
  public InvokeFunction cb() {
    return message -> {
      log.info("message={}", message);
    };
  }

  @Bean
  CoinapiProps coinapiProps() {
    return new CoinapiProps();
  }
}