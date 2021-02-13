package io.coinapi.ws;

import io.coinapi.websocket.model.MessageBase;

@FunctionalInterface
public interface InvokeFunction {

    void preprocesMessages(MessageBase message);
}
