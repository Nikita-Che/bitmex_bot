package finalVersionBitmexBot.exception;

import finalVersionBitmexBot.repository.OrderRepositoryList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OrderNotFoundException extends Exception {
    private static final Logger logger = LogManager.getLogger(OrderRepositoryList.class);

    public OrderNotFoundException(String s) {
        logger.error("Order no found");
        System.out.println(s);
    }
}
