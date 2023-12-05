package finalVersionBitmexBot;

import finalVersionBitmexBot.service.BitmexWebSocketClient;

public class App {
    public static void main(String[] args) {
//        BitmexHTTPClient bitmexClient = new BitmexHTTPClientImpl();
//        bitmexClient.openOrder(); //work
//        bitmexClient.getAllOrdersList(); //work

//        List<Order> openOrdersList = bitmexClient.getOpenOrdersList();//work
//        for (Order order : openOrdersList) {
//            System.out.println(order.getOrderID() + " : " + order.getPrice());
//        }
//        bitmexClient.closeAllOrders(); //work
//        bitmexClient.getOrder("123123");
//        System.out.println(bitmexClient.getOrder("e8b93681-ee5d-44d9-8d4f-5d9fa205ff80")); //work
//        bitmexClient.closeOrder("23123"); //work
//        bitmexClient.closeOrder("cbb3500c-4543-4036-8486-39717798ce21"); //work
//        bitmexClient.chooseOrderToClose();//work
//
        BitmexWebSocketClient bitmexWebSocketClient = new BitmexWebSocketClient(); //work
        bitmexWebSocketClient.createSessionAndSubscribeToOrder();
//        bitmexWebSocketClient.createSession();
    }
}

