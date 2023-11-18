package danilVersion;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SendRequest {
    String publicKey;
    long expires = System.currentTimeMillis() / 1000 + 5;

    public SendRequest(String publicKey) {
        this.publicKey = publicKey;
    }

    public void CreateNewOrder(String symbol, String side, double price, double orderQty) throws URISyntaxException, IOException, InterruptedException {
        String url = "https://testnet.bitmex.com/api/v1/order";
        String requestBody = "{\"symbol\":\"" + symbol + "\",\"side\":\"" + side +
                "\",\"price\":" + price + ",\"orderQty\":" + orderQty + "}";

        String signature = genSignature(url, "POST", requestBody);

        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .header("Content-Type", "application/json")
                .header("api-expires", String.valueOf(expires))
                .header("api-key", publicKey)
                .header("api-signature", signature)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.statusCode());
        System.out.println(response.body());
    }

    private String genSignature(String path, String verb, String data) throws URISyntaxException {
        URI uri = new URI(path);
        String pathToSign = uri.getPath();
        String qwery = uri.getRawAuthority();
        String fullPath = pathToSign + (qwery != null ? "?" + qwery : "");
        Signature signature = new Signature(verb, fullPath, data, expires);
        return signature.generateSignature();
    }
}
