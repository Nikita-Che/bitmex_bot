package finalVersionBitmexBot.service;

public interface BitmexClient {
    void openOrder();
    void closeOrder(int id);
    void clodeAllOrders();
    void getOrder();

}
