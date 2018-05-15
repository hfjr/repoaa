package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.loanProduct;

import java.io.Serializable;

/**
 * 征信方式基础DTO
 * Created by hexy on 16/8/25.
 */
public class BaseCreditWayDTO implements Serializable {

    private String name;// 公积金贷款,
    private String creditWayCode;// PFI,
    private String conditionDesc;// 需要公积金信用,
    private String isAvailable;// true,
    private Integer orderingWeightValue;// 1,
    private String loanAmountDesc;// 500~50000元,
    private String loanRate;// 0.035%,
    private String loanRateDesc;// 最低费率
    private String iconUrl;//图标地址
    private String loanStatus; //借款状态
    private String loanStatusDesc;//借款状态描述


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreditWayCode() {
        return creditWayCode;
    }

    public void setCreditWayCode(String creditWayCode) {
        this.creditWayCode = creditWayCode;
    }

    public String getConditionDesc() {
        return conditionDesc;
    }

    public void setConditionDesc(String conditionDesc) {
        this.conditionDesc = conditionDesc;
    }

    public String getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(String isAvailable) {
        this.isAvailable = isAvailable;
    }

    public Integer getOrderingWeightValue() {
        return orderingWeightValue;
    }

    public void setOrderingWeightValue(Integer orderingWeightValue) {
        this.orderingWeightValue = orderingWeightValue;
    }

    public String getLoanAmountDesc() {
        return loanAmountDesc;
    }

    public void setLoanAmountDesc(String loanAmountDesc) {
        this.loanAmountDesc = loanAmountDesc;
    }

    public String getLoanRate() {
        return loanRate;
    }

    public void setLoanRate(String loanRate) {
        this.loanRate = loanRate;
    }

    public String getLoanRateDesc() {
        return loanRateDesc;
    }

    public void setLoanRateDesc(String loanRateDesc) {
        this.loanRateDesc = loanRateDesc;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus(String loanStatus) {
        this.loanStatus = loanStatus;
    }

    public String getLoanStatusDesc() {
        return loanStatusDesc;
    }

    public void setLoanStatusDesc(String loanStatusDesc) {
        this.loanStatusDesc = loanStatusDesc;
    }
}
