package com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.dto;

/**
 * Created by wzf on 2016/10/9.
 */
public class OrderRepayHistory {

    public static final String IS_VALID_YES="Y";
    public static final String IS_VALID_NO="N";

    private String orderNo;
    private String principal;
    private String interest;
    private String fee;
    private String penalty;
    private String externalBizNo;
    private String isValid;

    public OrderRepayHistory() {
    }

    public OrderRepayHistory(String orderNo) {
        this.orderNo = orderNo;
        this.isValid = IS_VALID_YES;
    }

    public OrderRepayHistory(String orderNo, String principal, String interest, String fee, String penalty,String isValid) {
        this.orderNo = orderNo;
        this.principal = principal;
        this.interest = interest;
        this.fee = fee;
        this.penalty = penalty;
        this.isValid = isValid;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getPenalty() {
        return penalty;
    }

    public void setPenalty(String penalty) {
        this.penalty = penalty;
    }

    public String getExternalBizNo() {
        return externalBizNo;
    }

    public void setExternalBizNo(String externalBizNo) {
        this.externalBizNo = externalBizNo;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }
}
