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

public class BitmexCloseAllOrders {
    private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";

    public static void main(String[] args) {
        String apiSecret = "bv3Z35DKSh7No26QZfYGsx75QBwo8KasCpkD2hKDJ5yLmd7v"; // Ваш API-секрет BitMEX
        String apiKey = "DI-FVmnjsNvWGcJLEyVxqncH"; // Ваш API-ключ BitMEX
        long accountId = 411991; // ID вашего аккаунта на BitMEX
        long expires = System.currentTimeMillis() / 1000 + 5;

        String signature = generateSignature(apiSecret, "DELETE", "/api/v1/order/all", expires, String.valueOf(accountId));

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://testnet.bitmex.com/api/v1/order/all"))
                .header("Content-Type", "application/json")
                .header("api-expires", String.valueOf(expires))
                .header("api-key", apiKey)
                .header("api-signature", signature)
                .DELETE()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Response status code: " + response.statusCode());
            System.out.println("Response body: " + response.body());
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
