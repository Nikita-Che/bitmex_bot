package finalVersionBitmexBot.service;

import finalVersionBitmexBot.model.order.Order;

import java.util.List;

public interface BitmexHTTPClient {

    void openOrder();

    public void openMarketOrder();

    void chooseOrderToClose();

    void closeOrder(String orderId);

    void closeAllOrders();

    void closeMarketPosition(String orderId);

    Order getOrder(String orderId);

    List<Order> getAllOrdersList();

    List<Order> getOpenOrdersList();
}
