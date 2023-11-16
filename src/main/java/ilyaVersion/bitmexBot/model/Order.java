package ilyaVersion.bitmexBot.model;

import lombok.Data;

@Data
public class Order {
    private String orderId;
    private Symbol symbol;
    private boolean isBuy;
    private double orderQty;
    private Double price;
    private Double stopPx;
    private OrderType orderType;
    private OrderStatus orderStatus;
}
