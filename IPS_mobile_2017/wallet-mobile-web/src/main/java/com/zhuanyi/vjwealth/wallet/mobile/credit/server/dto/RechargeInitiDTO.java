package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto;

import java.io.Serializable;

/**
 * Created by hexy on 16/5/20.
 */
public class RechargeInitiDTO implements Serializable{

    private String bankCardLabel;
    private String bankCard;
    private String rechargeAmountLabel;
    private String rechargeAmount;
    private String tipInformation;
    private String buttonTextMessage;

    public String getBankCardLabel() {
        return bankCardLabel;
    }

    public void setBankCardLabel(String bankCardLabel) {
        this.bankCardLabel = bankCardLabel;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getRechargeAmountLabel() {
        return rechargeAmountLabel;
    }

    public void setRechargeAmountLabel(String rechargeAmountLabel) {
        this.rechargeAmountLabel = rechargeAmountLabel;
    }

    public String getRechargeAmount() {
        return rechargeAmount;
    }

    public void setRechargeAmount(String rechargeAmount) {
        this.rechargeAmount = rechargeAmount;
    }

    public String getTipInformation() {
        return tipInformation;
    }

    public void setTipInformation(String tipInformation) {
        this.tipInformation = tipInformation;
    }

    public String getButtonTextMessage() {
        return buttonTextMessage;
    }

    public void setButtonTextMessage(String buttonTextMessage) {
        this.buttonTextMessage = buttonTextMessage;
    }
}
