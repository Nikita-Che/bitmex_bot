package danilVersion;

import java.io.IOException;
import java.net.URISyntaxException;

public class App {
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {

        String publicKey = "DI-FVmnjsNvWGcJLEyVxqncH";

        SendRequest request = new SendRequest(publicKey);
//        request.getActiveOrder();
        request.CreateNewOrder("XBTUSD", "Buy", 36000.0, 100.0);
//        request.getActiveOrder();
    }
}
