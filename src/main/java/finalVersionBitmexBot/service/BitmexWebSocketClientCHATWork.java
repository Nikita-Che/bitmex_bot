package finalVersionBitmexBot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import finalVersionBitmexBot.model.authentification.AuthenticationHeaders;
import jakarta.websocket.*;
import org.glassfish.tyrus.client.ClientManager;
import org.glassfish.tyrus.client.ClientProperties;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.net.URI;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@ClientEndpoint
public class BitmexWebSocketClientCHATWork {
    AuthenticationHeaders authenticationHeaders;

    private static final String API_KEY = "DI-FVmnjsNvWGcJLEyVxqncH";
    private static final String API_SECRET = "bv3Z35DKSh7No26QZfYGsx75QBwo8KasCpkD2hKDJ5yLmd7v";

    private static final String BITMEX_URL = "wss://ws.testnet.bitmex.com/realtime";

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Connected to BitMEX WebSocket");
        try {
            Map<String, Object> args = new HashMap<>();
            args.put("op", "authKeyExpires");
            args.put("args", new Object[]{authenticationHeaders.getApiKey(), authenticationHeaders.getExpires(), generateSignature()});

            String json = toJson(args);

            session.getBasicRemote().sendText(json);
        } catch (IOException e) {
            e.printStackTrace();
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

    public static void main(String[] args) {
        ClientManager client = ClientManager.createClient();
        try {
            client.getProperties().put(ClientProperties.HANDSHAKE_TIMEOUT, 5000);
            client.connectToServer(BitmexWebSocketClientCHATWork.class, URI.create(BITMEX_URL));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String generateSignature() {
        try {
            String expires = String.valueOf(System.currentTimeMillis() / 1000 + 5);
            String message = "GET/realtime" + expires;

            Mac sha256Hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(API_SECRET.getBytes(), "HmacSHA256");
            sha256Hmac.init(secretKeySpec);
            byte[] signatureBytes = sha256Hmac.doFinal(message.getBytes());
            return API_KEY + ":" + expires + ":" + Base64.getEncoder().encodeToString(signatureBytes);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String toJson(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
