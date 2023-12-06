package finalVersionBitmexBot.service;

import finalVersionBitmexBot.model.order.Order;

import java.util.List;

public interface OrderService {

    void closeOrder(String id);

    void closeMarketPosition(String positionId);

    Order getOrderById(String id);

    String openOrder(String jsonOrder);

    List<Order> getAllOrdersList();

    List<Order> getOpenOrdersList();

    void closeAllOrders();

    void chooseOrderToClose();

}
