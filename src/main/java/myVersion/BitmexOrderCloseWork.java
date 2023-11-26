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

public class BitmexOrderCloseWork {
    private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";

    public static void main(String[] args) {
        String apiSecret = "bv3Z35DKSh7No26QZfYGsx75QBwo8KasCpkD2hKDJ5yLmd7v"; // Ваш API-секрет BitMEX
        String apiKey = "DI-FVmnjsNvWGcJLEyVxqncH"; // Ваш API-ключ BitMEX
        String verb = "DELETE"; // Метод DELETE для отмены ордера
        String path = "/api/v1/order"; // Путь для отмены ордера
        String orderIdToClose = "8ad0c8d0-663c-4a63-ad3d-9679adf42506"; // ID открытого ордера, который требуется закрыть
        System.out.println(orderIdToClose);
        long expires = System.currentTimeMillis() / 1000 + 5;

        String signature = generateSignature(apiSecret, verb, path + "?orderID=" + orderIdToClose, expires, "");

//  6e179549-586b-40bc-bd2f-c253bd95c014
//  139a12d4-cfa1-4d0e-bcbe-104b9326651e

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://testnet.bitmex.com" + path + "?orderID=" + orderIdToClose))
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
