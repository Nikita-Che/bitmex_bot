package finalVersionBitmexBot.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import finalVersionBitmexBot.model.authentification.AuthenticationHeaders;
import finalVersionBitmexBot.model.order.Order;
import finalVersionBitmexBot.model.util.Endpoints;
import finalVersionBitmexBot.model.util.JsonParser;
import finalVersionBitmexBot.model.util.OrderPriceOnlineGetter;
import jakarta.websocket.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@ClientEndpoint
public class BitmexWebSocketClient {
    private final AuthenticationHeaders authenticationHeaders = new AuthenticationHeaders();
    private final SignatureService signatureService = new SignatureServiceImpl();
    private static final Logger logger = LogManager.getLogger(BitmexWebSocketClient.class);

    public void createSession() {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            BitmexWebSocketClient bitmexWebSocketClient = new BitmexWebSocketClient(); //work
            URI uri = new URI(Endpoints.BASE_TEST_URL_WEBSOCKET);

            try {
                container.connectToServer(bitmexWebSocketClient, uri);
            } catch (DeploymentException | IOException e) {
                throw new RuntimeException(e);
            }

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        } catch (URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    public void createSessionAndSubscribeToOrder() {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            BitmexWebSocketClient bitmexWebSocketClient = new BitmexWebSocketClient();
            URI uri = new URI(Endpoints.BASE_TEST_URL_WEBSOCKET);

            try {
                Session session = container.connectToServer(bitmexWebSocketClient, uri);

                Map<String, Object> subscription = new HashMap<>();
                subscription.put("op", "subscribe");

//                subscription.put("args", "orderBook10:XBTUSD"); // подписка по которой смотрю цену онлайн. Спред считает
//                subscription.put("args", "position"); // подписка которая мониторит мои открытые позиции.
//                subscription.put("args", "order"); // подписка которая мониторит изменения в лимитных отложенных ордерах
                subscription.put("args", "position"); // О подписка которая мониторит мои открытые позиции. в том числе и тиках и если закрыто

                String json = JsonParser.toJson(subscription);
                session.getBasicRemote().sendText(json);

                Thread.sleep(130000);

                session.close();

            } catch (DeploymentException | IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }

        } catch (URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    @OnOpen
    public void onOpen(Session session) {
        try {
            Map<String, Object> args = new HashMap<>();
            args.put("op", "authKeyExpires");
            args.put("args", new Object[]{authenticationHeaders.getApiKey(), authenticationHeaders.getExpires(), signatureService.generateSignatureWebSocket()});

            String json = JsonParser.toJson(args);

            session.getBasicRemote().sendText(json);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        if (message.contains("\"table\":\"order\",\"action\":\"update\",\"data\":")) {
            parseOrderWithId(message);
        }
        if (message.contains("\"table\":\"orderBook10\",\"action\":\"update\"")) {
            OrderPriceOnlineGetter.getPriceBetweenBidAsk(message);
        }
        System.out.println("Received message: " + message);
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        System.out.println("Disconnected. Reason: " + closeReason.getReasonPhrase());
    }

    private void parseOrderWithId(String message) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(message);

            JsonNode dataNode = jsonNode.get("data");
            if (dataNode != null && dataNode.isArray() && dataNode.size() > 0) {
                for (JsonNode orderNode : dataNode) {
                    String orderID = orderNode.path("orderID").asText();
                    String action = jsonNode.get("action").asText();

                    System.out.println("orderID: " + orderID + ", action: " + action);
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void parseOrderWithIdSymbolSidePrice(String message) {
        Order order;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(message);

            JsonNode dataNode = jsonNode.get("data");
            if (dataNode != null && dataNode.isArray() && dataNode.size() > 0) {
                for (JsonNode orderNode : dataNode) {
                    order = new Order("", "", 0., 0., "");
                    order.setOrderID(orderNode.path("orderID").asText());
                    order.setSymbol(orderNode.path("symbol").asText());
                    order.setSide(orderNode.path("side").asText());
                    order.setOrderQty(orderNode.path("orderQty").asDouble());
                    order.setPrice(orderNode.path("price").asDouble());
                    order.setOrdStatus(orderNode.path("ordStatus").asText());

                    System.out.println("Table: order. Action: UPDATE. " + order);
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (NullPointerException e) {
            logger.error("Received invalid order data: " + message);
        }
    }
}
