package finalVersionBitmexBot;

import finalVersionBitmexBot.model.util.OrderPriceOnlineGetter;
import finalVersionBitmexBot.service.BitmexWebSocketClient;

public class ThreadManager {
    BitmexWebSocketClient bitmexWebSocketClient = new BitmexWebSocketClient();

    public void startSubscriptions() {
        Thread subscribeToOrderBook10 = new Thread(this::subscribeToOrderBook10);
        Thread orderPriceOnlineGetterInfoTask = new Thread(this::orderPriceOnlineGetterInfoTask);
        Thread subscribeToOrder = new Thread(this::subscribeToOrder);
        Thread subscribeToPosition = new Thread(this::subscribeToPosition);

        subscribeToOrderBook10.start();
        orderPriceOnlineGetterInfoTask.start();

//        subscribeToOrder.start();
//        subscribeToPosition.start();
    }

    public void startOrderPriceOnlineGetterInfoTaskThread () {
        Thread orderPriceOnlineGetterInfoTask = new Thread(this::orderPriceOnlineGetterInfoTask);
        orderPriceOnlineGetterInfoTask.start();
        if(OrderPriceOnlineGetter.priceForNewOrder!=0){
            orderPriceOnlineGetterInfoTask.interrupt();
        }
    }

    public void orderPriceOnlineGetterInfoTask (){
        OrderPriceOnlineGetter.orderPriceOnlineGetterInfoTaskForConsole();
    }

    public void subscribeToOrderBook10(){
        bitmexWebSocketClient.subscribeToOrderBook10();
    }

    public void subscribeToOrder(){
        bitmexWebSocketClient.subscribeToOrder();
    }

    public void subscribeToPosition(){
        bitmexWebSocketClient.subscribeToPosition();
    }
}
