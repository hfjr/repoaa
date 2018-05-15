package com.zhuanyi.vjwealth.wallet.mobile.index.server.dto;

/**
 * APP首页工资计划区域字段
 */
public class IndexSalaryPlanAreaDTO {
    private String salaryPlanLabel;
    private String salaryPlanTipCon;
    private String salaryPlanIcon;
    private String rate;
    private String desc;
    private String buttonText;

    public String getSalaryPlanLabel() {
        return salaryPlanLabel;
    }

    public void setSalaryPlanLabel(String salaryPlanLabel) {
        this.salaryPlanLabel = salaryPlanLabel;
    }

    public String getSalaryPlanTipCon() {
        return salaryPlanTipCon;
    }

    public void setSalaryPlanTipCon(String salaryPlanTipCon) {
        this.salaryPlanTipCon = salaryPlanTipCon;
    }

    public String getSalaryPlanIcon() {
        return salaryPlanIcon;
    }

    public void setSalaryPlanIcon(String salaryPlanIcon) {
        this.salaryPlanIcon = salaryPlanIcon;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }
}
