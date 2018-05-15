package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto;

import java.io.Serializable;

/**
 * Created by hexy on 16/5/20.
 */
public class RechargeBindBankCardInitiDTO implements Serializable {
    private String amount;
    private String realName;
    private String identityNo;
    private String bankName;
    private String cardBindMobilePhoneNoHelpURL;
    private String protocolName;
    private String protocolDetailURLTitle;
    private String protocolDetailURL;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCardBindMobilePhoneNoHelpURL() {
        return cardBindMobilePhoneNoHelpURL;
    }

    public void setCardBindMobilePhoneNoHelpURL(String cardBindMobilePhoneNoHelpURL) {
        this.cardBindMobilePhoneNoHelpURL = cardBindMobilePhoneNoHelpURL;
    }

    public String getProtocolName() {
        return protocolName;
    }

    public void setProtocolName(String protocolName) {
        this.protocolName = protocolName;
    }

    public String getProtocolDetailURLTitle() {
        return protocolDetailURLTitle;
    }

    public void setProtocolDetailURLTitle(String protocolDetailURLTitle) {
        this.protocolDetailURLTitle = protocolDetailURLTitle;
    }

    public String getProtocolDetailURL() {
        return protocolDetailURL;
    }

    public void setProtocolDetailURL(String protocolDetailURL) {
        this.protocolDetailURL = protocolDetailURL;
    }
}
