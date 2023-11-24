package finalVersionBitmexBot.model.order;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Order {
    private String orderID;
    private Symbol symbol;
    private Side side;
    private long orderQty;
    private Double price;
    private OrderType ordType;

    //Ниже параметры для json объекта. Не участвуют в открытии ордера
    private String clOrdID;
    private String clOrdLinkID;
    private int account;
    private Object displayQty; // Может быть как int, так и null
    private Object stopPx; // Может быть как int, так и null
    private Object pegOffsetValue; // Может быть как int, так и null
    private String pegPriceType;
    private String currency;
    private String settlCurrency;
    private String timeInForce;
    private String execInst;
    private String contingencyType;
    private String ordStatus;
    private String triggered;
    private boolean workingIndicator;
    private String ordRejReason;
    private int leavesQty;
    private int cumQty;
    private double avgPx;
    private String text;
    private String transactTime;
    private String timestamp;

//    public Order(Symbol symbol, Side side, long orderQty, Double price, OrderType ordType) {
//        this.symbol = symbol;
//        this.side = side;
//        this.orderQty = orderQty;
//        this.price = price;
//        this.ordType = ordType;
//    }
}


/*      orderID	:	553b8d62-8ae8-4bc5-8b17-47a681c4839d
        symbol	:	XBTUSD
        side	:	Buy
        orderQty	:	100
        price	:	36000
        ordType	:	Limit
*/