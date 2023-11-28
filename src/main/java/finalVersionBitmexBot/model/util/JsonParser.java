package finalVersionBitmexBot.model.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import finalVersionBitmexBot.model.order.Order;

import java.util.ArrayList;
import java.util.List;

public class JsonParser {

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
            e.printStackTrace();
            return null;
        }
    }
}


