package me.devtools4.crypto;

import io.coinapi.websocket.model.MessageBase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;

@Slf4j
public class CryptoStompSessionHandler extends StompSessionHandlerAdapter {

  @Override
  public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
    log.info("New session established : " + session.getSessionId());
    session.subscribe("/v1", this);
    log.info("Subscribed to /topic/messages");
    session.send("/v1", "");
    log.info("Message sent to websocket server");
  }

  @Override
  public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
    log.error("Got an exception", exception);
  }

  @Override
  public Type getPayloadType(StompHeaders headers) {
    return MessageBase.class;
  }

  @Override
  public void handleFrame(StompHeaders headers, Object payload) {
    MessageBase msg = (MessageBase) payload;
    log.info("Received : {}", msg);
  }
}