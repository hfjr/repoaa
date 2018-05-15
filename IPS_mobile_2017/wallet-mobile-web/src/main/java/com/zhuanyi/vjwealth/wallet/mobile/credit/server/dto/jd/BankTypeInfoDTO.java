package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.jd;

/**
 * Created by wzf on 2016/12/12.
 */
public class BankTypeInfoDTO {

    private String cardTypeName;// 储蓄卡,
    private String         bankName;// 广发银行,
    private String         bankCode;// 10090

    public String getCardTypeName() {
        return cardTypeName;
    }

    public void setCardTypeName(String cardTypeName) {
        this.cardTypeName = cardTypeName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }
}
