package finalVersionBitmexBot.service;

import finalVersionBitmexBot.model.order.Order;
import finalVersionBitmexBot.model.order.orderEnumsNotUsed.Symbol;
import finalVersionBitmexBot.model.util.OrderParser;

public class OrderServiceImpl implements OrderService {
    // TODO: 26.11.2023 вернуть сюда Order order; и нормально внедрить зависимости

    public String createOrder() {
        String symbol = "XBTUSD";
        String side = "Buy";
        Double orderQty = 100.;
        Double price = 35000.;
        String ordType = "Limit";

        Order order = new Order(symbol, side, orderQty, price, ordType);

        // тут создаем ордер и передаем его в клиента
        return OrderParser.parseOrderFromJsonToString(order);
    }

    public void closeOrder() {

    }

    public void getOrderId() {

    }

    public void closeOrders() {

    }
}
