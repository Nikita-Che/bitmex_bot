package myFirstVercionNotForLook;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;

public class BitMEXTradingBot {

    private static final String API_KEY = "DI-FVmnjsNvWGcJLEyVxqncH";
    private static final String API_SECRET = "bv3Z35DKSh7No26QZfYGsx75QBwo8KasCpkD2hKDJ5yLmd7v";

    public static void main(String[] args) throws Exception {
        String orderID = placeOrder("XBTUSD", "Buy", 100, 35000);
        System.out.println("Placed Order ID: " + orderID);

//        Thread.sleep(5000); // Wait 5 seconds before cancelling
//        cancelOrder(orderID);
//        System.out.println("Order Cancelled: " + orderID);
    }

    private static String placeOrder(String symbol, String side, int qty, double price) throws Exception {
        String verb = "POST";
        String path = "/api/v1/order";
        long expires = Instant.now().getEpochSecond() + 5;

        String orderJson = String.format("{\"symbol\":\"%s\",\"side\":\"%s\",\"orderQty\":%d,\"price\":%f}",
                symbol, side, qty, price);

        String signature = generateSignature(API_SECRET, verb, path, expires, orderJson);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://testnet.bitmex.com" + path))
                .header("Content-Type", "application/json")
                .header("api-expires", String.valueOf(expires))
                .header("api-key", API_KEY)
                .header("api-signature", signature)
                .POST(HttpRequest.BodyPublishers.ofString(orderJson))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.statusCode());
        return response.body();
    }

    private static void cancelOrder(String orderID) throws Exception {
        String verb = "DELETE";
        String path = "/api/v1/order";
        long expires = Instant.now().getEpochSecond() + 5;

        String signature = generateSignature(API_SECRET, verb, path, expires, "");

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://testnet.bitmex.com" + path + "?orderID=" + orderID))
                .header("Content-Type", "application/json")
                .header("api-expires", String.valueOf(expires))
                .header("api-key", API_KEY)
                .header("api-signature", signature)
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }

    private static String generateSignature(String secret, String verb, String path, long expires, String data) throws NoSuchAlgorithmException, InvalidKeyException {
        String message = verb + path + expires + data;

        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        sha256_HMAC.init(secretKeySpec);
        byte[] hash = sha256_HMAC.doFinal(message.getBytes(StandardCharsets.UTF_8));
        byte[] signatureBytes = Base64.getEncoder().encode(hash);

        return new String(signatureBytes, StandardCharsets.UTF_8);
    }
}
