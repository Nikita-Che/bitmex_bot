package finalVersionBitmexBot.repository;

import finalVersionBitmexBot.exception.OrderNotFoundException;
import finalVersionBitmexBot.model.order.Order;

import java.util.List;

public interface OrderRepository {
    Order getOrderById(String orderID) throws OrderNotFoundException;

    List<Order> getOrderList();

    List<Order> getOpenOrders();

}
