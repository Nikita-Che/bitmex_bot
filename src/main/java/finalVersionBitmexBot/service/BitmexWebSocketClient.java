package finalVersionBitmexBot.service;

import finalVersionBitmexBot.ThreadManager;
import finalVersionBitmexBot.model.authentification.AuthenticationHeaders;
import finalVersionBitmexBot.model.util.Endpoints;
import finalVersionBitmexBot.model.util.JsonParser;
import finalVersionBitmexBot.model.util.OrderPriceOnlineGetter;
import jakarta.websocket.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@ClientEndpoint
public class BitmexWebSocketClient {
    private final AuthenticationHeaders authenticationHeaders = new AuthenticationHeaders();
    private final SignatureService signatureService = new SignatureServiceImpl();
    private static final Logger logger = LogManager.getLogger(BitmexWebSocketClient.class);

    public void subscribeToOrderBook10() {
        // подписка по которой смотрю цену онлайн. Спред считает
        subscribe("orderBook10:XBTUSD");
    }

    public void subscribeToPosition() {
        //подписка которая мониторит мои открытые позиции. в том числе и тиках и если закрыто/ Раз в секунду дает тик
        subscribe("position");
    }

    public void subscribeToOrder() {
        // подписка которая мониторит изменения в лимитных отложенных ордерах
        subscribe("order");
    }

    // ИСПРАВИТЬ ВРЕМЯ РАБОТЫ ПРОГРААММЫ!!!
    private void subscribe(String subscriptionType) {
        // само выполнение метода надо зациклить.
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            BitmexWebSocketClient bitmexWebSocketClient = new BitmexWebSocketClient();
            URI uri = new URI(Endpoints.BASE_TEST_URL_WEBSOCKET);

            try {
                Session session = container.connectToServer(bitmexWebSocketClient, uri);

                Map<String, Object> subscription = new HashMap<>();
                subscription.put("op", "subscribe");

                subscription.put("args", subscriptionType);

                String json = JsonParser.toJson(subscription);
                session.getBasicRemote().sendText(json);

                while (ThreadManager.running) {
                }
                // ВОТ ТУТ ПОКА УПРАВЛЯТЬ ВРЕМЕНЕМ РАБОТЫ ПРОГРАММЫ!!!
                session.close();
            } catch (DeploymentException | IOException e) {
                throw new RuntimeException(e);

            }

        } catch (URISyntaxException e) {
            logger.error(e.getMessage());
        }

    }

    @OnOpen
    public void onOpen(Session session) {
        try {
            Map<String, Object> args = new HashMap<>();
            args.put("op", "authKeyExpires");
            args.put("args", new Object[]{authenticationHeaders.getApiKey(), authenticationHeaders.getExpires(), signatureService.generateSignatureWebSocket()});

            String json = JsonParser.toJson(args);

            session.getBasicRemote().sendText(json);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        if (message.contains("\"table\":\"order\",\"action\":\"update\",\"data\":")) {
            JsonParser.parseOrderWithId(message);
        }
        if (message.contains("\"table\":\"orderBook10\",\"action\":\"update\"")) {
            OrderPriceOnlineGetter.getPriceBetweenBidAsk(message);
        }
        System.out.println("Received message: " + message);
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        System.out.println("Disconnected. Reason: " + closeReason.getReasonPhrase());
    }

    public void createSession() {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            BitmexWebSocketClient bitmexWebSocketClient = new BitmexWebSocketClient(); //work
            URI uri = new URI(Endpoints.BASE_TEST_URL_WEBSOCKET);

            try {
                container.connectToServer(bitmexWebSocketClient, uri);
            } catch (DeploymentException | IOException e) {
                throw new RuntimeException(e);
            }
        } catch (URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }
}
