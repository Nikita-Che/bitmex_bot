package finalVersionBitmexBot.model.order;

import lombok.Data;

@Data
public class Order {
    private String orderId;
    private Symbol symbol;
    private Side side;
    private long orderQty;
    private long price;
    private OrderType ordType;

    public Order(Symbol symbol, Side side, long orderQty, long price, OrderType ordType) {
        this.symbol = symbol;
        this.side = side;
        this.orderQty = orderQty;
        this.price = price;
        this.ordType = ordType;
    }
}


/*      orderID	:	553b8d62-8ae8-4bc5-8b17-47a681c4839d
        symbol	:	XBTUSD
        side	:	Buy
        orderQty	:	100
        price	:	36000
        ordType	:	Limit
*/