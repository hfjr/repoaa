package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.myloan;

import java.util.List;
import java.util.Map;

/**
 * Created by wzf on 2016/9/8.
 */
public class MyLoanRepayDueDetailDTO {

    private String calculationMethodDescription;
    private List<Map<String,String>> paymentInfos;

    public String getCalculationMethodDescription() {
        return calculationMethodDescription;
    }

    public void setCalculationMethodDescription(String calculationMethodDescription) {
        this.calculationMethodDescription = calculationMethodDescription;
    }

    public List<Map<String, String>> getPaymentInfos() {
        return paymentInfos;
    }

    public void setPaymentInfos(List<Map<String, String>> paymentInfos) {
        this.paymentInfos = paymentInfos;
    }
}
