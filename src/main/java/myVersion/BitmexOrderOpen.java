package myVersion;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

public class BitmexOrderOpen {
    private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";

    public static void main(String[] args) {
        String apiKey = "DI-FVmnjsNvWGcJLEyVxqncH"; // Замените на ваш API-ключ BitMEX
        String apiSecret = "bv3Z35DKSh7No26QZfYGsx75QBwo8KasCpkD2hKDJ5yLmd7v"; // Замените на ваш API-секрет BitMEX

        // Создание JSON-строки с данными для создания ордера
        String orderJson = "{\"symbol\":\"XBTUSD\",\"side\":\"Buy\",\"orderQty\":100,\"price\":36000,\"ordType\":\"Limit\"}";

        try {
            long expires = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) + 5;
            String signature = generateSignature(apiSecret, "POST", "/api/v1/order", expires, orderJson);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://testnet.bitmex.com/api/v1/order"))
                    .header("Content-Type", "application/json")
                    .header("api-expires", String.valueOf(expires))
                    .header("api-key", apiKey)
                    .header("api-signature", signature)
                    .POST(HttpRequest.BodyPublishers.ofString(orderJson))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Response Code: " + response.statusCode());
            System.out.println("Response Body: " + response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String generateSignature(String secret, String verb, String url, long expires, String data) {
        String message = verb + url + expires + data;

        String signature = "";
        try {
            Mac sha256_HMAC = Mac.getInstance(HMAC_SHA256_ALGORITHM);
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), HMAC_SHA256_ALGORITHM);
            sha256_HMAC.init(secret_key);

            byte[] hash = sha256_HMAC.doFinal(message.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            signature = hexString.toString();
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }

        return signature;
    }
}
