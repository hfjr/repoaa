package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.financialLoan;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by hexy on 16/6/12.
 */
@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
public class ProductInvestmentRecordDTO implements Serializable {

    private String    phone ; // 手机号 [eg: 131***8765]
    private String    investmentTime  ; // 投资时间
    private String  investmentAmount ; // 投资金额

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getInvestmentTime() {
        return investmentTime;
    }

    public void setInvestmentTime(String investmentTime) {
        this.investmentTime = investmentTime;
    }

    public String getInvestmentAmount() {
        return investmentAmount;
    }

    public void setInvestmentAmount(String investmentAmount) {
        this.investmentAmount = investmentAmount;
    }
}
