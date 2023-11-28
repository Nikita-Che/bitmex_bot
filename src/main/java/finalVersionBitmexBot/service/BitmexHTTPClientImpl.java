package finalVersionBitmexBot.service;

import finalVersionBitmexBot.model.order.Order;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class BitmexHTTPClientImpl implements BitmexHTTPClient {
    OrderService orderService = new OrderServiceImpl();

    @Override
    public void openOrder() {
        List<String> props = loadFromProps();
        String orderJson = orderService.createJsonOrder(props.get(0), props.get(1), Double.parseDouble(props.get(2)), Double.parseDouble(props.get(3)), props.get(4));
        orderService.openOrder(orderJson);
    }

    @Override
    public void chooseOrderToClose() {
        orderService.chooseOrderToClose();
    }

    @Override
    public void closeAllOrders() {
        orderService.closeAllOrders();
    }

    @Override
    public void closeOrder(String orderId) {
        orderService.closeOrder(orderId);
    }

    @Override
    public Order getOrder(String orderId)  {
       return orderService.getOrderById(orderId);
    }

    @Override
    public List<Order> getAllOrdersList() {
        List<Order> allOrdersList = orderService.getAllOrdersList();
        return allOrdersList;
    }

    @Override
    public List<Order> getOpenOrdersList() {
        getAllOrdersList();
        List<Order> openOrdersList = orderService.getOpenOrdersList();
        return openOrdersList;
    }

    private List<String> loadFromProps() {
        List<String> props = new ArrayList<>();
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("properties.props"));
            props.add (properties.getProperty("symbol"));
            props.add (properties.getProperty("side"));
            props.add (properties.getProperty("orderQty"));
            props.add (properties.getProperty("price"));
            props.add (properties.getProperty("ordType"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return props;
    }
}
