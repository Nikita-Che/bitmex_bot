package finalVersionBitmexBot.repository;

import finalVersionBitmexBot.exception.OrderNotFoundException;
import finalVersionBitmexBot.model.order.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class OrderRepositoryList implements OrderRepository {
    private List<Order> orders = new ArrayList<>();
    private static final Logger logger = LogManager.getLogger(OrderRepositoryList.class);

    @Override
    public void init(List<Order> orders) {
        this.orders = orders;
        logger.info("init repository");
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
    public List<Order> getLimitOrdersList() {
        List<Order> limitOrdersList = new ArrayList<>();
        System.out.println("All Limits orders");
        for (Order order : orders) {
            // Условия для лимитного ордера
            if (order.getOrdStatus().equals("New") && order.getLeavesQty() > 0) {
                limitOrdersList.add(order);
            }
        }
        return limitOrdersList;
    }

    @Override
    public List<Order> getOpenOrdersList() {
        List<Order> opeOrdersList = new ArrayList<>();
        System.out.println("All Opened orders");
        for (Order order : orders) {
            // Условия для открытой позиции
            if (order.getOrdStatus().equalsIgnoreCase("Filled")
                    && order.getLeavesQty() == 0
                    && order.getCumQty() > 0) {
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
        logger.info("repository cleared");
        orders.clear();
    }
}

