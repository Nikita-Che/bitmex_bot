package finalVersionBitmexBot;

import finalVersionBitmexBot.model.order.Order;
import finalVersionBitmexBot.model.util.OrderPriceOnlineGetter;
import finalVersionBitmexBot.service.OrderServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class FiboManager {
    private static final Logger logger = LogManager.getLogger(FiboManager.class);
    OrderServiceImpl orderService = new OrderServiceImpl();

    // взять цену из  OrderPriceOnlineGetter
    private double getPriceForOrderToOpen() {
        return OrderPriceOnlineGetter.getPriceForNewOrder();
    }

    //взять параметры Step coef lvl загрузить из пропертис
    public static List<Integer> loadIntNetParamsFromProps() {
        List<Integer> props = new ArrayList<>();
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("properties.props"));
            props.add(Integer.valueOf(properties.getProperty("netOrderStep")));
            props.add(Integer.valueOf(properties.getProperty("netOrderCoef")));
            props.add(Integer.valueOf(properties.getProperty("netOrderLevel")));

        } catch (IOException e) {
            logger.error("Props file or data накосячено");
            throw new RuntimeException(e);
        }
        return props;
    }

    public static List<String> loadStringNetParamsFromProps() {
        List<String> props = new ArrayList<>();
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("properties.props"));
            props.add(properties.getProperty("netOrderSymbol"));
            props.add(properties.getProperty("netOrderType"));

        } catch (IOException e) {
            logger.error("Props file or data накосячено");
            throw new RuntimeException(e);
        }
        return props;
    }

    public static List<Double> loadDoubleNetParamsFromProps() {
        List<Double> props = new ArrayList<>();
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("properties.props"));

            props.add(Double.valueOf(properties.getProperty("netOrderQty")));

        } catch (IOException e) {
            logger.error("Props file or data накосячено");
            throw new RuntimeException(e);
        }
        return props;
    }

    public void createFirstOrder() {
//        String orderSide = RandomOrderSideGenerator.generateRandomSide(); //случайное Бай или селл
        String orderSide = "Sell";  // ПРОВЕРИТЬ! РАБОТАЕТ ТОЛЬКО СЕЛЛ
        List<Integer> netIntParams = loadIntNetParamsFromProps();
        List<String> netStringParams = loadStringNetParamsFromProps();
        List<Double> netDoubleParams = loadDoubleNetParamsFromProps();

        double firstOrderPrice = OrderPriceOnlineGetter.priceForNewOrder;

        orderService.openOrder(orderService.createJsonOrder(netStringParams.get(0), orderSide, netDoubleParams.get(0), 42230, netStringParams.get(1)));

//        Order firstOrder = new Order(netStringParams.get(0), orderSide, netDoubleParams.get(0), firstOrderPrice, netStringParams.get(1));
//        // получить этот ордер обратно открытый из сообщения и прокинуть в createNetAfterFistOrder
//        createNetAfterFistOrder(firstOrder, netIntParams.get(0), netIntParams.get(1), netIntParams.get(2));

    }

    private void createNetAfterFistOrder(Order firstOrder, Integer orderStep, Integer orderCoef, Integer orderLevel) {
        String symbol = firstOrder.getSymbol();
        String side = firstOrder.getSide();
        double orderQty = firstOrder.getOrderQty();
        double price = firstOrder.getPrice();
        String ordType = firstOrder.getOrdType();
        int[] fiboNumbers = generateFibonacci(orderLevel);

        for (int i = 1; i < orderLevel; i++) {
            orderQty = fiboNumbers[i] * orderQty;
            price = price - (fiboNumbers[i] * orderStep);

            orderService.openOrder(orderService.createJsonOrder(symbol, side, orderQty, price, ordType));
        }
    }

    public static int[] generateFibonacci(int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("Количество чисел должно быть положительным");
        }

        int[] fibonacciArray = new int[count];
        fibonacciArray[0] = 0;

        if (count > 1) {
            fibonacciArray[1] = 1;
            for (int i = 2; i < count; i++) {
                fibonacciArray[i] = fibonacciArray[i - 1] + fibonacciArray[i - 2];
            }
        }
        return fibonacciArray;
    }
}