package me.devtools4.crypto;

import static io.coinapi.websocket.model.MessageTypeEnum.*;

import io.coinapi.websocket.CoinAPIWebSocket;
import io.coinapi.websocket.CoinAPIWebSocketImpl;
import io.coinapi.websocket.interfaces.InvokeFunction;
import io.coinapi.websocket.model.Hello;
import java.util.concurrent.atomic.AtomicReference;

public class CoinAPIWebSocketRun {

  private static String API_KEY = "76E7E6A1-AED9-4AC0-BFC1-E1C53B45666D";

  public static void main(String[] args) throws Exception {
    CoinAPIWebSocket coinAPIWebSocket = new CoinAPIWebSocketImpl(false);

    AtomicReference<Integer> msgCount = new AtomicReference<>(0);

    InvokeFunction cb = message -> {
      System.out.println(message);
      msgCount.getAndSet(msgCount.get() + 1);
    };

    coinAPIWebSocket.setTradesInvoke(cb);
    coinAPIWebSocket.setQuotesInvoke(cb);
    coinAPIWebSocket.setBookInvoke(cb);
    coinAPIWebSocket.setOHLCVInvoke(cb);
    coinAPIWebSocket.setVolumeInvoke(cb);
    coinAPIWebSocket.setErrorInvoke(cb);
    coinAPIWebSocket.setReconnectInvoke(cb);

    String[] types = new String[] {
        trade.name(),
//        quote.name(),
//        book.name(),
//        book5.name(),
//        book20.name(),
//        book50.name(),
        ohlcv.name(),
        volume.name(),
//        hearbeat.name(),
//        error.name(),
//        reconnect.name()
    };
    coinAPIWebSocket.sendHelloMessage(createHello(types));

    Thread.sleep(10000);
    System.out.println("processing " + msgCount.get() + " messages");

    coinAPIWebSocket.closeConnect();
  }


  public static Hello createHello(String[] types) {
    Hello hello = new Hello();
    hello.setApikey(API_KEY);
    hello.setSubscribeDataType(types);
    return hello;
  }
}
