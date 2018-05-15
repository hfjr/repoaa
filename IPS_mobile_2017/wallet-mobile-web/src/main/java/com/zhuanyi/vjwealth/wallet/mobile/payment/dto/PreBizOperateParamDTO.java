package com.zhuanyi.vjwealth.wallet.mobile.payment.dto;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by wzf on 2016/9/26.
 */
public class PreBizOperateParamDTO implements Serializable{

    private String bizType;
    private String userId;
    private String cardId;
    private String loanCodes;
    private String principal;
    private String repaymentTotalMoney;

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getLoanCodes() {
        return loanCodes;
    }

    public void setLoanCodes(String loanCodes) {
        this.loanCodes = loanCodes;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getRepaymentTotalMoney() {
        return repaymentTotalMoney;
    }

    public void setRepaymentTotalMoney(String repaymentTotalMoney) {
        this.repaymentTotalMoney = repaymentTotalMoney;
    }
}
