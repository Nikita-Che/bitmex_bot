package finalVersionBitmexBot.service;

import finalVersionBitmexBot.exception.OrderNotFoundException;
import finalVersionBitmexBot.model.authentification.AuthenticationHeaders;
import finalVersionBitmexBot.model.order.Order;
import finalVersionBitmexBot.model.util.Endpoints;
import finalVersionBitmexBot.model.util.JsonParser;
import finalVersionBitmexBot.repository.OrderRepository;
import finalVersionBitmexBot.repository.OrderRepositoryList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Scanner;

public class OrderServiceImpl implements OrderService {
    private final SignatureService signatureService = new SignatureServiceImpl();
    private final AuthenticationHeaders authenticationHeaders = new AuthenticationHeaders();
    private final OrderRepository orderRepository = new OrderRepositoryList();
    private static final Logger logger = LogManager.getLogger(OrderServiceImpl.class);


    String createJsonOrder(String symbol, String side, double orderQty, double price, String ordType) {
        Order order = new Order(symbol, side, orderQty, price, ordType);
        logger.info("Json Order created");
        return JsonParser.parseOrderFromJsonToString(order);
    }

    String createMarketJsonOrder(String symbol, String side, double orderQty, String ordType) {
        Order order = new Order(symbol, side, orderQty, ordType);
        logger.info("Json Order created");
        return JsonParser.parseOrderFromJsonToString(order);
    }

    @Override
    public String openOrder(String jsonOrder) {
        String verb = "POST";
        String uri = Endpoints.BASE_TEST_URL + Endpoints.BASE_TEST_URL_SECOND_PART + Endpoints.ORDER_ENDPOINT;
        String uriForSignature = Endpoints.BASE_TEST_URL_SECOND_PART + Endpoints.ORDER_ENDPOINT;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("Content-Type", "application/json")
                .header("api-expires", String.valueOf(authenticationHeaders.getExpires()))
                .header("api-key", authenticationHeaders.getApiKey())
                .header("api-signature", signatureService.createSignature(verb, uriForSignature, jsonOrder))
                .POST(HttpRequest.BodyPublishers.ofString(jsonOrder))
                .build();

        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        String order = response.body();
        logger.info(JsonParser.extractOrderIDfromString(order) + " was opened");
        return order;
    }

    @Override
    public List<Order> getAllOrdersList() {
        String verb = "GET";
        String uri = Endpoints.BASE_TEST_URL
                + Endpoints.BASE_TEST_URL_SECOND_PART
                + Endpoints.ORDER_ENDPOINT
                + Endpoints.COUNT_100_REVERSE_FALSE;

        HttpClient httpClient = HttpClient.newBuilder().build();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("api-expires", String.valueOf(authenticationHeaders.getExpires()))
                .header("api-key", authenticationHeaders.getApiKey())
                .header("api-signature", signatureService.createSignatureForOrdersList(verb, uri, ""))
                .build();

        HttpResponse<String> send = null;
        try {
            send = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        String body = send.body(); // Вся строка из всех ордеров.

        List<Order> orders = JsonParser.parseOrderFromStringToJson(body);
        orderRepository.init(orders);
        logger.info("all OrderList получено нах");
        return orders;
    }

    @Override
    public List<Order> getOpenOrdersList() {
        logger.info("all OpenOrderList получено нах");
        return orderRepository.getOpenOrdersList();
    }

    @Override
    public void closeAllOrders() {
        String verb = "DELETE";
        String uri = Endpoints.BASE_TEST_URL + Endpoints.BASE_TEST_URL_SECOND_PART + Endpoints.ORDER_ENDPOINT + Endpoints.ORDER_ALL;
        String uriForSignature = Endpoints.BASE_TEST_URL_SECOND_PART + Endpoints.ORDER_ENDPOINT + Endpoints.ORDER_ALL;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("Content-Type", "application/json")
                .header("api-expires", String.valueOf(authenticationHeaders.getExpires()))
                .header("api-key", authenticationHeaders.getApiKey())
                .header("api-signature", signatureService.createSignature(verb, uriForSignature, ""))
                .DELETE()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            logger.info(response.body() + "all Orders closed");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        orderRepository.clear();
    }

    @Override
    public void chooseOrderToClose() {
        orderRepository.init(getAllOrdersList());  // инит сделан для теста

        List<Order> openOrders = orderRepository.getOpenOrdersList();
        for (Order openOrder : openOrders) {
            System.out.println(openOrder.getOrderID());
        }

        System.out.println("Please enter orderId which you want to close PIDOR");
        Scanner scanner = new Scanner(System.in);
        String userChoice = scanner.nextLine();
        closeOrder(userChoice);
    }

    @Override
    public void closeMarketPosition(String positionId) {
        String verb = "POST";
        String uri = Endpoints.BASE_TEST_URL
                + Endpoints.BASE_TEST_URL_SECOND_PART
                + Endpoints.ORDER_ENDPOINT
                + "/closePosition";

        String uriForSignature = Endpoints.BASE_TEST_URL_SECOND_PART
                + Endpoints.ORDER_ENDPOINT
                + "/closePosition";

        String body = "{\"symbol\":\"XBTUSD\", \"orderQty\":0, \"execInst\":\"Close\", \"text\":\"Closing position\"}";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("Content-Type", "application/json")
                .header("api-expires", String.valueOf(authenticationHeaders.getExpires()))
                .header("api-key", authenticationHeaders.getApiKey())
                .header("api-signature", signatureService.createSignature(verb, uriForSignature, body))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            logger.info("Position closed with ID: " + positionId);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public void closeOrder(String orderId) {
        String verb = "DELETE";
        String uri = Endpoints.BASE_TEST_URL
                + Endpoints.BASE_TEST_URL_SECOND_PART
                + Endpoints.ORDER_ENDPOINT
                + Endpoints.ORDER_ID
                + orderId;
        String uriForSignature = Endpoints.BASE_TEST_URL_SECOND_PART
                + Endpoints.ORDER_ENDPOINT
                + Endpoints.ORDER_ID
                + orderId;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("Content-Type", "application/json")
                .header("api-expires", String.valueOf(authenticationHeaders.getExpires()))
                .header("api-key", authenticationHeaders.getApiKey())
                .header("api-signature", signatureService.createSignature(verb, uriForSignature, ""))
                .DELETE()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            logger.info("Order closed " + JsonParser.extractOrderIDfromString(response.body()));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        // TODO: 26.11.2023  выдает OrderNotFoundException. косяк в репозитории.
//        try {
//            orderRepository.removeOrder(orderId);
//        } catch (OrderNotFoundException e) {
//            throw new RuntimeException(e);
//        }
    }

    public Order getOrderById(String orderId) {
        try {
            logger.info("Order get "+ orderId);
            return orderRepository.getOrderById(orderId);
        } catch (OrderNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
