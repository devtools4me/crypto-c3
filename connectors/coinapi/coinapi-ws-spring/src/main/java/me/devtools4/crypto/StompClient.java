package me.devtools4.crypto;

import java.util.Scanner;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

public class StompClient {
  private static String URL = "wss://ws-sandbox.coinapi.io/v1";

  public static void main(String[] args) {
    WebSocketClient client = new StandardWebSocketClient();
    WebSocketStompClient stompClient = new WebSocketStompClient(client);

    stompClient.setMessageConverter(new MappingJackson2MessageConverter());

    StompSessionHandler sessionHandler = new CryptoStompSessionHandler();
    stompClient.connect(URL, sessionHandler);

    new Scanner(System.in).nextLine(); // Don't close immediately.
  }
}
