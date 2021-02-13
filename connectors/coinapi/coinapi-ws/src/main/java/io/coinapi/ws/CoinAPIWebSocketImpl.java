package io.coinapi.ws;

import com.dslplatform.json.DslJson;
import io.coinapi.websocket.model.Book;
import io.coinapi.websocket.model.Error;
import io.coinapi.websocket.model.Hello;
import io.coinapi.websocket.model.MessageBase;
import io.coinapi.websocket.model.MessageTypeEnum;
import io.coinapi.websocket.model.OHLCV;
import io.coinapi.websocket.model.Quotes;
import io.coinapi.websocket.model.Reconnect;
import io.coinapi.websocket.model.Trades;
import io.coinapi.websocket.model.Volume;
import jakarta.websocket.ClientEndpointConfig;
import jakarta.websocket.CloseReason;
import jakarta.websocket.Endpoint;
import jakarta.websocket.EndpointConfig;
import jakarta.websocket.MessageHandler;
import jakarta.websocket.Session;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.tyrus.client.ClientManager;

@Slf4j
public class CoinAPIWebSocketImpl implements CoinAPIWebSocket {

    private final URI uri;
    private DslJson<Object> json = new DslJson<>();

    private static CountDownLatch latch;
    private ClientManager client;
    private Optional<Session> connection = Optional.empty();
    private Optional<Thread> processingMessages;
    private volatile boolean running = true;

    private Queue messagesQueue = new LinkedBlockingDeque();

    private InvokeFunction tradesInvoke;
    private InvokeFunction quotesInvoke;
    private InvokeFunction bookInvoke;
    private InvokeFunction ohlcvInvoke;
    private InvokeFunction volumeInvoke;
    private InvokeFunction errorInvoke;
    private InvokeFunction reconnectInvoke;

    public CoinAPIWebSocketImpl(URI uri) {

        this.uri = uri;
        client = ClientManager.createClient();

        Runnable task = () -> {
            while (running) {
                if (messagesQueue.size() > 0) {
                    String message = (String) messagesQueue.remove();
                    log.trace("message={}", message);

                    InputStream stream = new ByteArrayInputStream(message.getBytes());
                    try {
                        MessageBase type = json.deserialize(MessageBase.class, stream);
                        MessageTypeEnum messageType = MessageTypeEnum.valueOf(type.getType());
                        switch (messageType) {
                            case trade:
                                handle(message, Trades.class, tradesInvoke);
                                break;
                            case quote:
                                handle(message, Quotes.class, quotesInvoke);
                                break;
                            case book:
                            case book5:
                            case book20:
                            case book50:
                                handle(message, Book.class, bookInvoke);
                                break;
                            case ohlcv:
                                handle(message, OHLCV.class, ohlcvInvoke);
                                break;
                            case volume:
                                handle(message, Volume.class, volumeInvoke);
                                break;
                            case error:
                                handle(message, Error.class, errorInvoke);
                                break;
                            case reconnect:
                                handle(message, Reconnect.class, reconnectInvoke);
                                break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        processingMessages = Optional.of(new Thread(task));
    }

    /**
     *
     * @param hello
     * @throws IOException
     */
    @Override
    public void sendHelloMessage(Hello hello) throws IOException {

        processingMessages.get().start();

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        json.serialize(hello, os);

        try {
            latch = new CountDownLatch(1);

            final ClientEndpointConfig cec = ClientEndpointConfig.Builder.create().build();

            Endpoint endpoint = new Endpoint() {
                @Override
                public void onOpen(Session session, EndpointConfig config) {
                    try {
                        session.addMessageHandler(new MessageHandler.Whole<String>() {

                            @Override
                            public void onMessage(String message) {
                                messagesQueue.add(message);
                                latch.countDown();
                            }
                        });
                        session.getBasicRemote().sendText(os.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onClose(Session session, CloseReason closeReason) {
                    super.onClose(session, closeReason);
                }
            };

            connection = Optional.ofNullable(client.connectToServer(endpoint, cec, uri));
            latch.await(100, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @throws IOException
     */
    @Override
    public void closeConnect() throws IOException {

        if (connection.isPresent()) {
            connection.get().close();
            System.out.println("websocket connection closed");
        }

        if (processingMessages.isPresent() && processingMessages.get().isAlive()) {
            running = false;
            processingMessages.get().interrupt();
            System.out.println("processingMessage thread interrupt");
        }
    }

    /**
     *
     * @param function
     */
    @Override
    public void setTradesInvoke(InvokeFunction function) {
        this.tradesInvoke = function;
    }

    /**
     *
     * @param function
     */
    @Override
    public void setQuotesInvoke(InvokeFunction function) {
        this.quotesInvoke = function;
    }

    /**
     *
     * @param function
     */
    @Override
    public void setBookInvoke(InvokeFunction function) {
        this.bookInvoke = function;
    }

    /**
     *
     * @param function
     */
    @Override
    public void setOHLCVInvoke(InvokeFunction function) {
        this.ohlcvInvoke = function;
    }

    /**
     *
     * @param function
     */
    @Override
    public void setVolumeInvoke(InvokeFunction function) {
        this.volumeInvoke = function;
    }

    /**
     *
     * @param function
     */
    @Override
    public void setErrorInvoke(InvokeFunction function) { 
        this.errorInvoke = function; 
    }

    /**
     *
     * @param function
     */
    @Override
    public void setReconnectInvoke(InvokeFunction function) { 
        this.reconnectInvoke = function; 
    }

    private void handle(String message, Class deserializeClass, InvokeFunction invokeFunction) throws IOException, NotImplementedException {

        Object deserialize = json.deserialize(deserializeClass, new ByteArrayInputStream(message.getBytes()));

        if (invokeFunction == null) {
            return;
        }
        invokeFunction.preprocesMessages((MessageBase) deserialize);
    }

    @Override
    protected final void finalize() throws Throwable {
        super.finalize();
        closeConnect();
    }
}
