package finalVersionBitmexBot;

import finalVersionBitmexBot.service.BitmexHTTPClient;
import finalVersionBitmexBot.service.BitmexHTTPClientImpl;

public class App {
    public static void main(String[] args) {
        BitmexHTTPClient bitmexClient = new BitmexHTTPClientImpl();
//        bitmexClient.openOrder(); //work
//        bitmexClient.openMarketOrder();
        bitmexClient.closeMarketPosition("fb8eba10-4c5a-401e-9595-4e49e0686890");
//        bitmexClient.getAllOrdersList(); //work

//        List<Order> openOrdersList = bitmexClient.getOpenOrdersList();//work
//        for (Order order : openOrdersList) {
//            System.out.println(order.getOrderID() + " : " + order.getPrice());
//        }
//        bitmexClient.closeAllOrders(); //work
//        bitmexClient.getOrder("123123");
//        System.out.println(bitmexClient.getOrder("e8b93681-ee5d-44d9-8d4f-5d9fa205ff80")); //work
//        bitmexClient.closeOrder("23123"); //work
//        bitmexClient.closeOrder("deb1e8e2-89bd-425f-adb9-88d6d7f16901"); //work
//        bitmexClient.chooseOrderToClose();//work
//
//        BitmexWebSocketClient bitmexWebSocketClient = new BitmexWebSocketClient(); //work
//        bitmexWebSocketClient.createSessionAndSubscribeToOrder();
//        bitmexWebSocketClient.createSession();
    }
}

