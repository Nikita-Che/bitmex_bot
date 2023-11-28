package finalVersionBitmexBot.model.order;

import lombok.Data;

@Data
public class Order {
    private String orderID;
    private String symbol;
    private String side;
    private Double orderQty;
    private Double price;
    private String ordType;

    //Ниже параметры для json объекта. Не участвуют в открытии ордера
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

    public Order(String symbol, String side, Double orderQty, Double price, String ordType) {
        this.symbol = symbol;
        this.side = side;
        this.orderQty = orderQty;
        this.price = price;
        this.ordType = ordType;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public void setOrderQty(Double orderQty) {
        this.orderQty = orderQty;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setOrdType(String ordType) {
        this.ordType = ordType;
    }

    public void setOrdStatus(String ordStatus) {
        this.ordStatus = ordStatus;
    }

}

