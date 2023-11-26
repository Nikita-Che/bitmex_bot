package finalVersionBitmexBot;

import finalVersionBitmexBot.service.BitmexClient;
import finalVersionBitmexBot.service.BitmexClientImpl;

public class App {
    public static void main(String[] args) {
        BitmexClient bitmexClient = new BitmexClientImpl();
        bitmexClient.openOrder(); //work
//        bitmexClient.getOrderList();
//        bitmexClient.test();
    }
}
