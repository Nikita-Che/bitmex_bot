package finalVersionBitmexBot.service;

import finalVersionBitmexBot.model.util.Endpoints;
import finalVersionBitmexBot.model.authentification.AuthenticationHeaders;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class BitmexClientImpl implements BitmexClient{
    OrderService orderService = new OrderServiceImpl();
    SignatureService signatureService = new SignatureServiceImpl();
    AuthenticationHeaders authenticationHeaders;

    @Override
    public void openOrder() {
        String orderJson = orderService.createOrder(); // тут надо получить Json оредера

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(Endpoints.BASE_TEST_URL))
                .header("Content-Type", "application/json")
                .header("api-expires", authenticationHeaders.getExpires())
                .header("api-key", authenticationHeaders.getApiKey())
                .header("api-signature", signatureService.createSignature())
                .POST(HttpRequest.BodyPublishers.ofString(orderJson))
                .build();

        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Response Code: " + response.statusCode());
        System.out.println("Response Body: " + response.body());
    }

    @Override
    public void closeOrder(int id) {

    }

    @Override
    public void clodeAllOrders() {

    }

    @Override
    public void getOrder() {

    }
}
