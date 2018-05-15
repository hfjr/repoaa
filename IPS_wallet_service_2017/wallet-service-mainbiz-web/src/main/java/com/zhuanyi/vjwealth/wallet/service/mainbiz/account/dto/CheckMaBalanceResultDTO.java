package com.zhuanyi.vjwealth.wallet.service.mainbiz.account.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by wzf on 2016/9/4.
 */
public class CheckMaBalanceResultDTO implements Serializable{

    private boolean isEnough;//是否充足
    private BigDecimal amountBalance;//账户余额
    private BigDecimal shortMoney;//差多少

    public CheckMaBalanceResultDTO() {
    }

    public CheckMaBalanceResultDTO(boolean isEnough, BigDecimal amountBalance, BigDecimal shortMoney) {
        this.isEnough = isEnough;
        this.amountBalance = amountBalance;
        this.shortMoney = shortMoney;
    }

    public boolean isEnough() {
        return isEnough;
    }

    public void setEnough(boolean enough) {
        isEnough = enough;
    }

    public BigDecimal getShortMoney() {
        return shortMoney;
    }

    public void setShortMoney(BigDecimal shortMoney) {
        this.shortMoney = shortMoney;
    }

    public BigDecimal getAmountBalance() {
        return amountBalance;
    }

    public void setAmountBalance(BigDecimal amountBalance) {
        this.amountBalance = amountBalance;
    }
}
