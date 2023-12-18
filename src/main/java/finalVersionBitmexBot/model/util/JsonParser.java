package finalVersionBitmexBot.model.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import finalVersionBitmexBot.model.order.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonParser {
    private static final Logger logger = LogManager.getLogger(JsonParser.class);

    public static List<Order> parseOrderFromStringToJson(String ordersJson) {
        Gson gson = new Gson();
        Order[] orderArray = gson.fromJson(ordersJson, Order[].class);

        List<Order> orders = new ArrayList<>();
        for (Order order : orderArray) {
            orders.add(order);
        }
        return orders;
    }

    public static String parseOrderFromJsonToString(Order order) {
        Gson gson = new Gson();
        return gson.toJson(order);
    }

    public static String toJson(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    public static String extractOrderIDfromString(String input) {
        String[] parts = input.split("\"orderID\":\"");
        if (parts.length > 1) {
            String[] subParts = parts[1].split("\"");
            return subParts[0];
        }
        return null;
    }

    public static void parseOrderWithId(String message) {
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

    public static void parseOrderWithIdSymbolSidePrice(String message) {
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

    public static void checkOpenPosition(String jsonString) {
        int isOpenIndex = jsonString.indexOf("\"isOpen\":true");

        if (isOpenIndex != -1) {
            System.out.println("Открытая позиция avgCostPrice = 41141.5");
        } else {
            int isOpenFalseIndex = jsonString.indexOf("\"isOpen\":false");
            if (isOpenFalseIndex != -1) {
                System.out.println("Открытых позиций нет");
            }
        }
    }

    public static void checkLimitOrders(String jsonString) {
        int buyOrderQtyIndex = jsonString.indexOf("\"openOrderBuyQty\":0");
        int sellOrderQtyIndex = jsonString.indexOf("\"openOrderSellQty\":0");

        if (buyOrderQtyIndex != -1 && sellOrderQtyIndex != -1) {
            System.out.println("Лимитных ордеров нет");
        } else {
            System.out.println("Есть лимитные ордеры");
        }
    }

}


