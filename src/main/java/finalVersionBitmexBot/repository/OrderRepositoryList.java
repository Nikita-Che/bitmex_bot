package finalVersionBitmexBot.repository;

import finalVersionBitmexBot.exception.OrderNotFoundException;
import finalVersionBitmexBot.model.order.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderRepositoryList implements OrderRepository {
    List<Order> orders = new ArrayList<>();

    @Override
    public Order getOrderById(String orderID) throws OrderNotFoundException {
        for (Order order : orders) {
            if (order.getOrderID().equals(orderID)) {
                return order;
            }
        }
        throw new OrderNotFoundException("Order with id " + orderID + " not found");
    }

    @Override
    public List<Order> getOrderList() {
        // обратись в Битмекс и возьми список всех ордеров.
        return orders;
    }

    @Override
    public List<Order> getOpenOrders() {
        List<Order> orderList = getOrderList();
        List<Order> openList = new ArrayList<>();

        for (Order order : orderList) {
            if (order.getOrdStatus().equalsIgnoreCase("new")) {
                openList.add(order);
            }
        }
        return openList;
    }
}
