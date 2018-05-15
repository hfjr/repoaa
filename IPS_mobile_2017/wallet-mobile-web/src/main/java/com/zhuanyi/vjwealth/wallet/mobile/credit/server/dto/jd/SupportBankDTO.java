package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.jd;

/**
 * Created by wzf on 2016/12/12.
 */
public class SupportBankDTO {

    private String bankCode;// 10026,
    private String bankName;// 建设银行

    public SupportBankDTO() {
    }

    public SupportBankDTO(String bankCode, String bankName) {
        this.bankCode = bankCode;
        this.bankName = bankName;
    }

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
}
