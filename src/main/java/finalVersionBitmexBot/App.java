package finalVersionBitmexBot;

import finalVersionBitmexBot.service.BitmexHTTPClient;
import finalVersionBitmexBot.service.BitmexHTTPClientImpl;
import finalVersionBitmexBot.service.BitmexWebSocketClient;

public class App {
    public static void main(String[] args) {
        BitmexHTTPClient bitmexClient = new BitmexHTTPClientImpl();
//        bitmexClient.openOrder(); //work
//        bitmexClient.openMarketOrder(); //work
        //      bitmexClient.closeMarketPosition("her"); //work
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

        BitmexWebSocketClient bitmexWebSocketClient = new BitmexWebSocketClient(); //work
        bitmexWebSocketClient.createSessionAndSubscribeToOrder();
        //        bitmexWebSocketClient.createSession();

//        Runnable task = new Runnable() {
//            @Override
//            public void run() {
//                BitmexWebSocketClient bitmexWebSocketClient = new BitmexWebSocketClient(); //work
//                bitmexWebSocketClient.createSessionAndSubscribeToOrder();
//            }
//        };
//        Runnable task2 = new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i < 30; i++) {
//                    System.out.println(OrderPriceOnlineGetter.priceForNewOrder);
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//            }
//        };
//
//        Thread thread = new Thread(task);
//        Thread thread2 = new Thread(task2);
//
//        thread.start();
//        thread2.start();



    }
}

