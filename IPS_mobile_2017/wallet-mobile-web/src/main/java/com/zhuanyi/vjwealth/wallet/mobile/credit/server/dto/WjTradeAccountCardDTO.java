package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto;

/**
 * @Copyright (C), 2015-2016,蔚捷互联网金融服务有限公司
 * @desc:账户绑卡DTO
 * @author: Tony Tang
 * @date: 2016-05-22 15:51
 */
public class WjTradeAccountCardDTO {
    private String bindCardStatus;
    private String asideBankPhone;
    private String bankCode;

    private String loanBankCode;

    private String bankName;

    public String getBindCardStatus() {
        return bindCardStatus;
    }

    public void setBindCardStatus(String bindCardStatus) {
        this.bindCardStatus = bindCardStatus;
    }

    public String getAsideBankPhone() {
        return asideBankPhone;
    }

    public void setAsideBankPhone(String asideBankPhone) {
        this.asideBankPhone = asideBankPhone;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getLoanBankCode() {
        return loanBankCode;
    }

    public void setLoanBankCode(String loanBankCode) {
        this.loanBankCode = loanBankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}
