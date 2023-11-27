package finalVersionBitmexBot.service;

import finalVersionBitmexBot.model.util.Endpoints;
import jakarta.websocket.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

public class BitmexWebSocketClient {
    private Session session;
    private Boolean isConnected;

    public void connect() {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            session = container.connectToServer(this, new URI(Endpoints.BASE_TEST_URL_WEBSOCKET));
            session.setMaxIdleTimeout(TimeUnit.MINUTES.toMillis(60));
            if (session.isOpen()) {
                isConnected = true;
                System.out.println("Session is opened");
//                session.getBasicRemote().sendText(); 1,35
            }

        } catch (DeploymentException | IOException | URISyntaxException e) {
            System.out.println("Cannot connect to simulation Server");
        }
    }

    @OnMessage
    public void onMessage(String message) {

    }

    @OnMessage
    public void onMessage(PongMessage pongMessage) {

    }
}
