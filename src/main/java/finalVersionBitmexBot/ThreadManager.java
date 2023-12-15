package finalVersionBitmexBot;

import finalVersionBitmexBot.model.util.OrderPriceOnlineGetter;
import finalVersionBitmexBot.service.BitmexWebSocketClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadManager {
    BitmexWebSocketClient bitmexWebSocketClient = new BitmexWebSocketClient();
    public static volatile boolean running = true;

    public void startSubscriptions() {
        ExecutorService service = Executors.newFixedThreadPool(4);
        service.execute(new SubscribeToOrderBook10());
        service.execute(new OrderPriceOnlineGetterInfoTask());
//        service.execute(new SubscribeToToPosition());
//        service.execute(new SubscribeToToOrder());

        service.shutdown();
    }

    class SubscribeToOrderBook10 implements Runnable {
        @Override
        public void run() {
            while (running) {
                bitmexWebSocketClient.subscribeToOrderBook10();
            }
        }
    }

    static class OrderPriceOnlineGetterInfoTask implements Runnable {
        @Override
        public void run() {
            while (running) {
                OrderPriceOnlineGetter.orderPriceOnlineGetterInfoTaskForConsole();
            }
        }
    }

    class SubscribeToToOrder implements Runnable {
        @Override
        public void run() {
            while (running) {
                bitmexWebSocketClient.subscribeToOrder();
            }
        }
    }

    class SubscribeToToPosition implements Runnable {
        @Override
        public void run() {
            while (running) {
                bitmexWebSocketClient.subscribeToPosition();
            }
        }
    }
}
