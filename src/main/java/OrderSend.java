import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

public class OrderSend {
    public static void main(String[] args) throws Exception {
        // Создаем JSON-строку с вашим телом запроса
        String jsonBody = "{\n" +
                "  \"clOrdID\": \"\",\n" +
                "  \"clOrdLinkID\": \"\",\n" +
                "  \"account\": 411991,\n" +
                "  \"symbol\": \"XBTUSD\",\n" +
                "  \"side\": \"Buy\",\n" +
                "  \"orderQty\": 100,\n" +
                "  \"price\": 35526,\n" +
                "  \"displayQty\": null,\n" +
                "  \"stopPx\": null,\n" +
                "  \"pegOffsetValue\": null,\n" +
                "  \"pegPriceType\": \"\",\n" +
                "  \"currency\": \"USD\",\n" +
                "  \"settlCurrency\": \"XBt\",\n" +
                "  \"ordType\": \"Limit\",\n" +
                "  \"timeInForce\": \"GoodTillCancel\",\n" +
                "  \"execInst\": \"\",\n" +
                "  \"contingencyType\": \"\",\n" +
                "  \"ordStatus\": \"New\",\n" +
                "  \"triggered\": \"\",\n" +
                "  \"workingIndicator\": true,\n" +
                "  \"ordRejReason\": \"\",\n" +
                "  \"leavesQty\": 100,\n" +
                "  \"cumQty\": 0,\n" +
                "  \"avgPx\": null,\n" +
                "  \"text\": \"Submitted via API.\",\n" +
                "  \"transactTime\": \"2023-11-15T12:04:01.278Z\",\n" +
                "  \"timestamp\": \"2023-11-15T12:04:01.278Z\"\n" +
                "}";

        // Создаем HttpClient
        HttpClient client = HttpClient.newHttpClient();

        long expires = System.currentTimeMillis() / 1000+5;
        // Создаем объект HttpRequest
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://testnet.bitmex.com/api/v1/order"))
                .header("Content-Type", "application/json")
                .header("api-expires", String.valueOf(expires))
                .header("api-key", "DI-FVmnjsNvWGcJLEyVxqncH")
                .header("api-signature", generateSignature("bv3Z35DKSh7No26QZfYGsx75QBwo8KasCpkD2hKDJ5yLmd7v", "POST",
                        "https://testnet.bitmex.com/api/v1/order", expires, "") )
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        // Отправляем запрос и получаем ответ
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Выводим ответ на экран
        System.out.println(response.body());
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
