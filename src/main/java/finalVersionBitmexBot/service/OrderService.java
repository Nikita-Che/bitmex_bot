package finalVersionBitmexBot.service;

import finalVersionBitmexBot.model.order.Order;

import java.util.List;

public interface OrderService {

    void closeOrder(String id);

    void closeMarketPosition(String positionId);

    Order getOrderById(String id);

    String openOrder(String jsonOrder);

    List<Order> getAllOrdersList();

    List<Order> getLimitOrdersList();
    List<Order> getOpenOrdersList();

    String getOpenPosition();

    void closeAllOrders();

    void chooseOrderToClose();

}
