package com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.dto;

/**
 * Created by wzf on 2016/9/13.
 */
public class OrderInfoQueryDTO {

    private String channelTradeId;
    private String totalPrice;
    private String price;
    private String relOrderNo;
    private String orderStatus;
    private String cardId;

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getChannelTradeId() {
        return channelTradeId;
    }

    public void setChannelTradeId(String channelTradeId) {
        this.channelTradeId = channelTradeId;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRelOrderNo() {
        return relOrderNo;
    }

    public void setRelOrderNo(String relOrderNo) {
        this.relOrderNo = relOrderNo;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
