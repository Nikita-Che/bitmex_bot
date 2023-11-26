package myFirstVercionNotForLook.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class MyOrder {
    @JsonProperty("orderID")
    private String orderID;

    @JsonProperty("clOrdID")
    private String clOrdID;

    @JsonProperty("clOrdLinkID")
    private String clOrdLinkID;

    @JsonProperty("account")
    private int account;

    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("side")
    private String side;

    @JsonProperty("orderQty")
    private int orderQty;

    @JsonProperty("price")
    private double price;

    @JsonProperty("displayQty")
    private Double displayQty;

    @JsonProperty("stopPx")
    private Double stopPx;

    @JsonProperty("pegOffsetValue")
    private Double pegOffsetValue;

    @JsonProperty("pegPriceType")
    private String pegPriceType;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("settlCurrency")
    private String settlCurrency;

    @JsonProperty("ordType")
    private String ordType;

    @JsonProperty("timeInForce")
    private String timeInForce;

    @JsonProperty("execInst")
    private String execInst;

    @JsonProperty("contingencyType")
    private String contingencyType;

    @JsonProperty("ordStatus")
    private String ordStatus;

    @JsonProperty("triggered")
    private String triggered;

    @JsonProperty("workingIndicator")
    private boolean workingIndicator;

    @JsonProperty("ordRejReason")
    private String ordRejReason;

    @JsonProperty("leavesQty")
    private int leavesQty;

    @JsonProperty("cumQty")
    private int cumQty;

    @JsonProperty("avgPx")
    private double avgPx;

    @JsonProperty("text")
    private String text;

    @JsonProperty("transactTime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date transactTime;

    @JsonProperty("timestamp")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date timestamp;
}
