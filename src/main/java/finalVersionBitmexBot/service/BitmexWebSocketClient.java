package finalVersionBitmexBot.service;

import finalVersionBitmexBot.model.authentification.AuthenticationHeaders;
import finalVersionBitmexBot.model.util.Endpoints;
import finalVersionBitmexBot.model.util.JsonParser;
import jakarta.websocket.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.tyrus.client.ClientManager;
import org.glassfish.tyrus.client.ClientProperties;

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

    public void createSessionWithClient() {
        ClientManager client = ClientManager.createClient();
        try {
            client.getProperties().put(ClientProperties.HANDSHAKE_TIMEOUT, 5000);
            client.connectToServer(BitmexWebSocketClient.class, URI.create(Endpoints.BASE_TEST_URL_WEBSOCKET));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public void createSessionWithSession() {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        try {
            Session session = container.connectToServer(this, new URI(Endpoints.BASE_TEST_URL_WEBSOCKET));
        } catch (DeploymentException | IOException | URISyntaxException e) {
            throw new RuntimeException(e);
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
