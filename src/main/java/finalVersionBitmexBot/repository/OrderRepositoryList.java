package finalVersionBitmexBot.repository;

import finalVersionBitmexBot.exception.OrderNotFoundException;
import finalVersionBitmexBot.model.order.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderRepositoryList implements OrderRepository {

    List<Order> orders = new ArrayList<>();

    @Override
    public void init(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public void removeOrder(String orderID) throws OrderNotFoundException {
        orders.removeIf(order -> order.getOrderID().equals(orderID));
        throw new OrderNotFoundException("Order with id " + orderID + " not found");
    }

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
    public List<Order> getOpenOrdersList() {
        List<Order> opeOrdersList = new ArrayList<>();
        for (Order order : orders) {
            if (order.getOrdStatus().equalsIgnoreCase("new")) {
                opeOrdersList.add(order);
            }
        }
        return opeOrdersList;
    }

    @Override
    public List<Order> getAllOrdersList() {
        return orders;
    }

    @Override
    public void clear() {
        orders.clear();
    }
}

