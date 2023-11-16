package myVersion;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

public class OrderList {
    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newBuilder().build();
        long expires = System.currentTimeMillis() / 1000+5;
        HttpRequest httpRequest = HttpRequest.newBuilder()
//                .uri(URI.create("https://testnet.bitmex.com/api/v1/order"))
                .uri(URI.create("https://testnet.bitmex.com/api/v1/order?count=10&reverse=false"))
                .header("api-expires", String.valueOf(expires))
                .header("api-key", "DI-FVmnjsNvWGcJLEyVxqncH")
                .header("api-signature", generateSignature("bv3Z35DKSh7No26QZfYGsx75QBwo8KasCpkD2hKDJ5yLmd7v", "GET",
                        "https://testnet.bitmex.com/api/v1/order?count=10&reverse=false", expires, "") )
                .build();

        HttpResponse<String> send = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println(send.body());

    }

    private static String generateSignature(String secret, String verb, String url, long expires, String data) {
        String path = url;
        String query = "";
        try {
            java.net.URL parsedURL = new java.net.URL(url);
            path = parsedURL.getPath();
            query = parsedURL.getQuery();
        } catch (java.net.MalformedURLException e) {
            e.printStackTrace();
        }

        if (query != null && !query.isEmpty()) {
            path = path + "?" + query;
        }

        if (data != null && !data.isEmpty()) {
            data = URLEncoder.encode(data, StandardCharsets.UTF_8);
        }

        String message = verb + path + expires + data;
//        System.out.println("Вычисляем HMAC: " + message);

        String signature = "";
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] hash = sha256_HMAC.doFinal(message.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            signature = hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (java.security.InvalidKeyException e) {
            e.printStackTrace();
        }

        return signature;
    }

}
