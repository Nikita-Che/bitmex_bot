package finalVersionBitmexBot.service;

import finalVersionBitmexBot.model.order.Order;

import java.util.List;

public interface BitmexClient {
    void openOrder();
    void closeOrder(int id);
    void clodeAllOrders();
    void getOrder(String orderId);

    List<Order> getOrderList();

}
