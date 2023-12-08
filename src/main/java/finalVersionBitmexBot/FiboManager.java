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


    //выставить ордер в бай или селл
    public void createFirstOrder() {

        double firstOrderPrice = OrderPriceOnlineGetter.priceForNewOrder;
        //запустить поток дождаться тика и когда будет != null то открыть ордер

//        String orderSide = RandomOrderSideGenerator.generateRandomSide(); //случайное Бай или селл
        String orderSide = "Sell";
        List<Integer> netIntParams = loadIntNetParamsFromProps();
        List<String> netStringParams = loadStringNetParamsFromProps();
        List<Double> netDoubleParams = loadDoubleNetParamsFromProps();

        // открыть лимитник битмексХТТПлиент открыть ордер. и сделать метод БЕЗ пропертис.

        orderService.openOrder(orderService.createJsonOrder(netStringParams.get(0), orderSide, netDoubleParams.get(0), firstOrderPrice, netStringParams.get(1)));

        Order firstOrder = new Order(netStringParams.get(0), orderSide, netDoubleParams.get(0), firstOrderPrice, netStringParams.get(1));
        // получить этот ордер обратно открытый из сообщения и прокинуть в createNetAfterFistOrder
        createNetAfterFistOrder(firstOrder, netIntParams.get(0), netIntParams.get(1), netIntParams.get(2));

    }

    private void createNetAfterFistOrder(Order firstOrder, Integer orderStep, Integer orderCoef, Integer orderLevel) {
        System.out.println("CЕТКА ХУЕТКА! ");
        //выставить сетку ордеров на основе параметров

    }
}