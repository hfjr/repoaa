package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.wageAdvance;

import java.util.List;
import java.util.Map;

/**
 * 工资先享-借款申请初始化
 * Created by wangzf on 16/5/20.
 */
public class RepaymentPlanDTO {

    private String yearGroup;//年分组
    private String tip;//提示
    private String date;//日期(月和日)
    private String totalAmount;//还款总额
    private String detail;//还款总额的详细描述

    private String isCurrentPeriod;//是否是当期
    private String isOverDue;//是否是逾期

    public String getYearGroup() {
        return yearGroup;
    }

    public void setYearGroup(String yearGroup) {
        this.yearGroup = yearGroup;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getIsCurrentPeriod() {
        return isCurrentPeriod;
    }

    public void setIsCurrentPeriod(String isCurrentPeriod) {
        this.isCurrentPeriod = isCurrentPeriod;
    }

    public String getIsOverDue() {
        return isOverDue;
    }

    public void setIsOverDue(String isOverDue) {
        this.isOverDue = isOverDue;
    }
}
