package finalVersionBitmexBot;

import finalVersionBitmexBot.model.util.OrderPriceOnlineGetter;

public class App {
    public static void main(String[] args) {
//       BitmexHTTPClient bitmexClient = new BitmexHTTPClientImpl();
//       bitmexClient.openOrder(); //work
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

//        BitmexWebSocketClient bitmexWebSocketClient = new BitmexWebSocketClient(); //work
//        bitmexWebSocketClient.subscribeToOrderBook10();


        ThreadManager threadManager = new ThreadManager();
        threadManager.startSubscriptions();

        boolean flag = true;
        while (flag) {
            if (OrderPriceOnlineGetter.priceForNewOrder == 0) {
                System.out.println("WAITING for price Update");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else {
                FiboManager fiboManager = new FiboManager();
                fiboManager.createFirstOrder();

                flag = false;
            }
        }
    }
}

