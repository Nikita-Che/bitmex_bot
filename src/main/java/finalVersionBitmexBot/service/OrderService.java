package finalVersionBitmexBot.service;

import finalVersionBitmexBot.model.order.Order;

import java.util.List;

public interface OrderService {

    void closeOrder(String id);

    Order getOrderById(String id);

    String createJsonOrder(String symbol, String side, double orderQty, double price, String ordType);

    String openOrder(String jsonOrder);

    List<Order> getAllOrdersList();

    List<Order> getOpenOrdersList();

    void closeAllOrders();

    void chooseOrderToClose();
}
