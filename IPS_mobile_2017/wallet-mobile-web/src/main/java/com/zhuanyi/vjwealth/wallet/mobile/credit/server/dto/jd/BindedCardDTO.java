package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.jd;

/**
 * Created by wzf on 2016/12/12.
 */
public class BindedCardDTO {

    private String  bankCode;// 10026,
    private String  bankName;// 建设银行,
    private String  cadTypeName;//储蓄卡,
    private String  asideBankPhoneDesc;//手机尾号0885,
    private String  cardNoDesc;//*** *** 8877,
    private String  cardId;//CA94349384938494,
    private String  isValid;//Y

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCadTypeName() {
        return cadTypeName;
    }

    public void setCadTypeName(String cadTypeName) {
        this.cadTypeName = cadTypeName;
    }

    public String getAsideBankPhoneDesc() {
        return asideBankPhoneDesc;
    }

    public void setAsideBankPhoneDesc(String asideBankPhoneDesc) {
        this.asideBankPhoneDesc = asideBankPhoneDesc;
    }

    public String getCardNoDesc() {
        return cardNoDesc;
    }

    public void setCardNoDesc(String cardNoDesc) {
        this.cardNoDesc = cardNoDesc;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }
}
