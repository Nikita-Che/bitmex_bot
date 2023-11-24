package finalVersionBitmexBot.service;

import finalVersionBitmexBot.model.order.Order;
import finalVersionBitmexBot.model.order.OrderType;
import finalVersionBitmexBot.model.order.Side;
import finalVersionBitmexBot.model.order.Symbol;
import finalVersionBitmexBot.model.util.OrderParser;

public class OrderServiceImpl implements OrderService {
    private Order order;

    public String createOrder() {
        order.builder().
                symbol(Symbol.valueOf("XBTUSD"))
                .side(Side.Buy)
                .orderQty(100)
                .price(38000.0)
                .ordType(OrderType.Limit)
                .build();

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
