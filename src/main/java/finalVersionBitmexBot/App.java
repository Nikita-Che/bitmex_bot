package finalVersionBitmexBot;

import finalVersionBitmexBot.service.BitmexClient;
import finalVersionBitmexBot.service.BitmexClientImpl;

public class App {
    public static void main(String[] args) {
        BitmexClient bitmexClient = new BitmexClientImpl();
//        bitmexClient.openOrder(); //work
//        bitmexClient.getAllOrdersList(); //work
//        bitmexClient.getOpenOrdersList(); //work
//        bitmexClient.closeAllOrders(); //work
//        bitmexClient.getOrder("123123");
//        System.out.println(bitmexClient.getOrder("e8b93681-ee5d-44d9-8d4f-5d9fa205ff80")); //work
//        bitmexClient.closeOrder("23123"); //work
//        bitmexClient.closeOrder("cbb3500c-4543-4036-8486-39717798ce21"); //work

        bitmexClient.chooseOrderToClose();

    }
}
