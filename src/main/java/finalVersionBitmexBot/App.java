package finalVersionBitmexBot;

import finalVersionBitmexBot.model.util.JsonParser;
import finalVersionBitmexBot.service.BitmexHTTPClient;
import finalVersionBitmexBot.service.BitmexHTTPClientImpl;

public class App {
    public static void main(String[] args) {
       BitmexHTTPClient bitmexClient = new BitmexHTTPClientImpl();
        JsonParser.checkOpenPosition(bitmexClient.getOpenPosition());
        JsonParser.checkLimitOrders(bitmexClient.getOpenPosition());

//       bitmexClient.openOrder(); //work
//        bitmexClient.openMarketOrder(); //work
        //      bitmexClient.closeMarketPosition("her"); //work

//        List<Order> ordersList = bitmexClient.getAllOrdersList();//work
//        for (Order order : ordersList) {
//            System.out.println(order.getOrderID() + " : " + order.getPrice());
//        }

//        List<Order> limitOrderList = bitmexClient.getLimitOrdersList();//work
//        for (Order order : limitOrderList) {
//            System.out.println(order.getOrderID() + " : " + order.getPrice());
//        }
////
//        List<Order> openOrdersList = bitmexClient.getOpenOrdersList();//work
//        for (Order order : openOrdersList) {
//            System.out.println(order.getOrderID() + " : " + order.getPrice());
//        }

//        bitmexClient.closeAllOrders(); //work
//        bitmexClient.getOrder("123123");
//        System.out.println(bitmexClient.getOrder("f97141bd-a8ea-4919-a748-38a130b8f466")); //work
//        bitmexClient.closeOrder("23123"); //work
//        bitmexClient.closeOrder("deb1e8e2-89bd-425f-adb9-88d6d7f16901"); //work
//        bitmexClient.chooseOrderToClose();//work

//        BitmexWebSocketClient bitmexWebSocketClient = new BitmexWebSocketClient(); //work
//        bitmexWebSocketClient.subscribeToOrderBook10();


//        ThreadManager threadManager = new ThreadManager();
//        threadManager.startSubscriptions();
//
//        boolean flag = true;
//        while (flag) {
//                if (OrderPriceOnlineGetter.priceForNewOrder == 0) {
//                    System.out.println("WAITING for price Update");
//                    try {
//                        Thread.sleep(2000);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                } else {
//                    FiboManager fiboManager = new FiboManager();
//                    fiboManager.createFirstOrder();
//                    flag = false;
//                }
//        }


//        OrderPriceOnlineGetter.priceForNewOrder = 42870.;
//        FiboManager fiboManager = new FiboManager();
//        fiboManager.createFirstOrder();
    }
}

