package finalVersionBitmexBot.service;

public interface OrderService {

    public String createOrder();

    public void closeOrder();

    public void getOrderId();

    public void closeOrders();
}
