package finalVersionBitmexBot.model.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import finalVersionBitmexBot.service.BitmexWebSocketClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OrderPriceOnlineGetter {
    private static final Logger logger = LogManager.getLogger(BitmexWebSocketClient.class);
    public static double priceForNewOrder;

    public static void writeOrderPrice(double price) {
        priceForNewOrder = price;
    }

    public static void getPriceBetweenBidAsk(String message) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(message);

            JsonNode dataNode = rootNode.path("data").get(0);
            JsonNode bidsNode = dataNode.path("bids").get(0);
            JsonNode asksNode = dataNode.path("asks").get(0);

            double firstBid = bidsNode.get(0).asDouble();
            double firstAsk = asksNode.get(0).asDouble();

            double priceForNewOrder = firstAsk - ((firstAsk - firstBid) / 2);
            writeOrderPrice(priceForNewOrder);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

   public static void orderPriceOnlineGetterInfoTask () {
       double lastCheckedPrice = OrderPriceOnlineGetter.priceForNewOrder;

       while (true) {
           try {
               Thread.sleep(1000);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }

           double currentPrice = OrderPriceOnlineGetter.priceForNewOrder;

           if (currentPrice != lastCheckedPrice) {
               System.out.println("Price For New Order has changed: " + currentPrice);
               lastCheckedPrice = currentPrice;
           }
       }
   }
}
