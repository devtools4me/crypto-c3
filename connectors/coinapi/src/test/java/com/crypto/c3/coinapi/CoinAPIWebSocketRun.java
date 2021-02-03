package com.crypto.c3.coinapi;

import io.coinapi.websocket.CoinAPIWebSocket;
import io.coinapi.websocket.CoinAPIWebSocketImpl;
import io.coinapi.websocket.model.Hello;
import io.coinapi.websocket.model.Quotes;
import io.coinapi.websocket.model.Trades;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.Assert;

public class CoinAPIWebSocketRun {

  private static String API_KEY = "76E7E6A1-AED9-4AC0-BFC1-E1C53B45666D";

  public static void main(String[] args) throws Exception {
    CoinAPIWebSocket coinAPIWebSocket = new CoinAPIWebSocketImpl(false);

    AtomicReference<Integer> msgCount = new AtomicReference<>(0);

    coinAPIWebSocket.setTradesInvoke(message -> {
      System.out.println(message);
      Trades trades = (Trades) message;
      msgCount.getAndSet(msgCount.get() + 1);
    });

    coinAPIWebSocket.setQuotesInvoke(message -> {
      System.out.println(message);
      Quotes quotes = (Quotes) message;
      msgCount.getAndSet(msgCount.get() + 1);
    });

    coinAPIWebSocket.sendHelloMessage(createHello(new String[]{
        "trade", "quote"
    }));

    Thread.sleep(10000);
    System.out.println("processing " + msgCount.get() + " trade messages");

    coinAPIWebSocket.closeConnect();
    Assert.assertNotEquals(0, msgCount.get().intValue());
  }


  public static Hello createHello(String[] types) {
    Hello hello = new Hello();
    hello.setApikey(API_KEY);
    hello.setSubscribeDataType(types);
    return hello;
  }
}
