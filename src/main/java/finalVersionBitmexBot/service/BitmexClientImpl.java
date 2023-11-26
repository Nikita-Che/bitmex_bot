package finalVersionBitmexBot.service;

import finalVersionBitmexBot.model.order.Order;
import finalVersionBitmexBot.model.util.Endpoints;
import finalVersionBitmexBot.model.authentification.AuthenticationHeaders;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class BitmexClientImpl implements BitmexClient {
    OrderService orderService = new OrderServiceImpl();
    SignatureService signatureService = new SignatureServiceImpl();
    AuthenticationHeaders authenticationHeaders = new AuthenticationHeaders();


    // TODO: 26.11.2023 в первую очередь когда надо сделать метод Init. который закинет Воообще все ордера в репозиторий.
    //  разделив их при этом на 2 этапа, первый это отрктытие ордера это первый список,
    //  второй список это просто закрытые ордера нужные для истории.  Возможно нужно прям в интерфейс закинуть метод init.

    public void test() {

        String verb = "POST";
        String uri = Endpoints.BASE_TEST_URL + Endpoints.BASE_TEST_URL_SECOND_PART + Endpoints.OPEN_ORDER_ENDPOINT;
        String uriForSignature = Endpoints.BASE_TEST_URL_SECOND_PART + Endpoints.OPEN_ORDER_ENDPOINT;
        String orderJson = orderService.createOrder();

        System.out.println(authenticationHeaders.getApiKey());
        System.out.println(authenticationHeaders.getApiSecret());
        System.out.println(authenticationHeaders.getExpires());
        System.out.println(orderService.createOrder());
        System.out.println(signatureService.createSignature(verb, uriForSignature, orderJson));
    }

    @Override
    public void openOrder() {
        String verb = "POST";
        String uri = Endpoints.BASE_TEST_URL + Endpoints.BASE_TEST_URL_SECOND_PART + Endpoints.OPEN_ORDER_ENDPOINT;
        String uriForSignature = Endpoints.BASE_TEST_URL_SECOND_PART + Endpoints.OPEN_ORDER_ENDPOINT;
        String orderJson = orderService.createOrder(); // тут надо получить Json оредера

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("Content-Type", "application/json")
                .header("api-expires", String.valueOf(authenticationHeaders.getExpires()))
                .header("api-key", authenticationHeaders.getApiKey())
                .header("api-signature", signatureService.createSignature(verb, uriForSignature, orderJson))
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
    public void getOrder(String orderId) {

    }

    @Override
    public List<Order> getOrderList() {
        return null;
    }
}
