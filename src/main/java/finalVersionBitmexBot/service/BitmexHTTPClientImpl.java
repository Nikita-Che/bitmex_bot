package finalVersionBitmexBot.service;

import finalVersionBitmexBot.model.order.Order;

import java.util.List;

public class BitmexHTTPClientImpl implements BitmexClient {
    OrderService orderService = new OrderServiceImpl();

    @Override
    public void openOrder() {
        String orderJson = orderService.createJsonOrder("XBTUSD", "Buy", 100., 34500., "Limit");
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
        List<Order> openOrdersList = orderService.getOpenOrdersList();
        return openOrdersList;
    }
}
