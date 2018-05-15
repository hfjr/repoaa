package com.zhuanyi.vjwealth.wallet.mobile.index.server.dto;

/**
 * 首页贷款区域字段
 */
public class IndexLoanAreaDTO {
    private String loanProductName;
    private String maxLoanAmountIcon;
    private String loanMinRate;
    private String loanPeriod;
    private String buttonText;
    private String loanActivityIcon;
    private String newIcon;

    public String getLoanProductName() {
        return loanProductName;
    }

    public void setLoanProductName(String loanProductName) {
        this.loanProductName = loanProductName;
    }

    public String getMaxLoanAmountIcon() {
        return maxLoanAmountIcon;
    }

    public void setMaxLoanAmountIcon(String maxLoanAmountIcon) {
        this.maxLoanAmountIcon = maxLoanAmountIcon;
    }

    public String getLoanMinRate() {
        return loanMinRate;
    }

    public void setLoanMinRate(String loanMinRate) {
        this.loanMinRate = loanMinRate;
    }

    public String getLoanPeriod() {
        return loanPeriod;
    }

    public void setLoanPeriod(String loanPeriod) {
        this.loanPeriod = loanPeriod;
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    public String getLoanActivityIcon() {
        return loanActivityIcon;
    }

    public void setLoanActivityIcon(String loanActivityIcon) {
        this.loanActivityIcon = loanActivityIcon;
    }

    public String getNewIcon() {
        return newIcon;
    }

    public void setNewIcon(String newIcon) {
        this.newIcon = newIcon;
    }
}
