package myFirstVercionNotForLook;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocketListener;

public class WebSocket {

    private static final String API_KEY = "CfwQ4SZ6gM_t6dIy1bCLJylX";
    private static final String API_SECRET = "f9XOPLacPCZJ1dvPzN8B6Et7nMEaPGeomMSHk8Cr2zD4NfCY";

    private static final String BITMEX_URL = "wss://www.bitmex.com";
    private static final String VERB = "GET";
    private static final String ENDPOINT = "/realtime";

    public static void main(String[] args) {
        testWithMessage();
        testWithQueryString();
    }

    public static void testWithMessage() {
        long expires = System.currentTimeMillis() / 1000 + 5;

        String signature = bitmexSignature(API_SECRET, VERB, ENDPOINT, expires);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(BITMEX_URL + ENDPOINT)
                .build();

        WebSocketListener listener = new WebSocketListener() {
            @Override
            public void onOpen(okhttp3.WebSocket webSocket, Response response) {
                System.out.println("Receiving Welcome Message...");
            }

            @Override
            public void onMessage(okhttp3.WebSocket webSocket, String text) {
                System.out.println("Received '" + text + "'");
            }
        };

        okhttp3.WebSocket webSocket = client.newWebSocket(request, listener);

        Map<String, Object> args = new HashMap<>();
        args.put("op", "authKeyExpires");
        args.put("args", new Object[] {API_KEY, expires, signature});

        String json = toJson(args);

        webSocket.send(json);
    }

    public static void testWithQueryString() {
        long expires = System.currentTimeMillis() / 1000 + 5;

        String signature = bitmexSignature(API_SECRET, VERB, ENDPOINT, expires);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(BITMEX_URL + ENDPOINT + "?api_key=" + API_KEY + "&expires=" + expires + "&signature=" + signature)
                .build();

        WebSocketListener listener = new WebSocketListener() {
            @Override
            public void onOpen(okhttp3.WebSocket webSocket, Response response) {
                System.out.println("Receiving Welcome Message...");
            }

            @Override
            public void onMessage(okhttp3.WebSocket webSocket, String text) {
                System.out.println("Received '" + text + "'");
            }
        };

        okhttp3.WebSocket webSocket = client.newWebSocket(request, listener);
    }

    private static String bitmexSignature(String secret, String verb, String endpoint, long expires) {
        try {
            String message = verb + endpoint + expires;
            Mac sha256Hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256Hmac.init(secretKeySpec);
            byte[] signatureBytes = sha256Hmac.doFinal(message.getBytes());
            return Base64.getEncoder().encodeToString(signatureBytes);
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

