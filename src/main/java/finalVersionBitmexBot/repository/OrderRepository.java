package finalVersionBitmexBot.repository;

import finalVersionBitmexBot.exception.OrderNotFoundException;
import finalVersionBitmexBot.model.order.Order;

import java.util.List;

public interface OrderRepository {
    Order getOrderById(String orderID) throws OrderNotFoundException;
    
    List<Order> getLimitOrdersList();
    List<Order> getOpenOrdersList();
    List<Order> getAllOrdersList();

    void clear();

    void init(List<Order> orders);

    void removeOrder(String orderID) throws OrderNotFoundException;
}
