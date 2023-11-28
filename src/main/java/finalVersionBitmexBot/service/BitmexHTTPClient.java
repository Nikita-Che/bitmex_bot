package finalVersionBitmexBot.service;

import finalVersionBitmexBot.model.order.Order;

import java.util.List;

public interface BitmexHTTPClient {

    void openOrder();

    void chooseOrderToClose();

    void closeOrder(String orderId);

    void closeAllOrders();

    Order getOrder(String orderId);

    List<Order> getAllOrdersList();

    List<Order> getOpenOrdersList();
}
