package finalVersionBitmexBot.model.order;

import lombok.Data;
import lombok.Setter;

@Data
public class Order {
    @Setter
    private String orderID;
    @Setter
    private String symbol;
    @Setter
    private String side;
    @Setter
    private Double orderQty;
    @Setter
    private Double price;
    @Setter
    private String ordType;

    //Ниже параметры для json объекта. Не участвуют в открытии ордера
    @Setter
    private String ordStatus;
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
    private String triggered;
    private boolean workingIndicator;
    private String ordRejReason;
    private int leavesQty;
    private int cumQty;
    private double avgPx;
    private String text;
    private String transactTime;
    private String timestamp;

    public Order(String symbol, String side, Double orderQty, String ordType) {
        this.symbol = symbol;
        this.side = side;
        this.orderQty = orderQty;
        this.ordType = ordType;
    }

    public Order(String symbol, String side, Double orderQty, Double price, String ordType) {
        this.symbol = symbol;
        this.side = side;
        this.orderQty = orderQty;
        this.price = price;
        this.ordType = ordType;
    }
}

