package finalVersionBitmexBot.service;

import finalVersionBitmexBot.model.authentification.AuthenticationHeaders;
import finalVersionBitmexBot.model.util.Endpoints;
import finalVersionBitmexBot.model.util.JsonParser;
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

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        } catch (URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    public void createSessionAndSubscribeToOrder() {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            BitmexWebSocketClient bitmexWebSocketClient = new BitmexWebSocketClient();
            URI uri = new URI(Endpoints.BASE_TEST_URL_WEBSOCKET);

            try {
                Session session = container.connectToServer(bitmexWebSocketClient, uri);

                // Отправка запроса на подписку на orderBookL2_25
                Map<String, Object> subscription = new HashMap<>();
                subscription.put("op", "subscribe");
//                subscription.put("args", "orderBookL2_25:XBTUSD"); // Подписка на orderBookL2_25 по конкретной паре
                subscription.put("args", "order"); // Подписка на orderBookL2_25 по конкретной паре

                String json = JsonParser.toJson(subscription);
                session.getBasicRemote().sendText(json);

                // Ждем некоторое время для получения сообщений
                Thread.sleep(100000); // Например, ждем 10 секунд

                // Закрытие сессии
                session.close();

            } catch (DeploymentException | IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }

        } catch (URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("You just Connected to BitMEX WebSocket PIDOR");
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
        System.out.println("Received message: " + message);
        // Обработка полученных сообщений
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        System.out.println("Disconnected. Reason: " + closeReason.getReasonPhrase());
    }
}
