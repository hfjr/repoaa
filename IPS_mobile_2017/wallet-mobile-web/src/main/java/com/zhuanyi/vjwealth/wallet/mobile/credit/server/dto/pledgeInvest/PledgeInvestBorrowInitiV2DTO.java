package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.pledgeInvest;//

import java.io.Serializable;

/**
 * Created by hexy on 16/7/12.
 */
public class PledgeInvestBorrowInitiV2DTO implements Serializable{

    private String  borrowOrderLabel ;//  关联理财产品信息Label

    private String  borrowAmountLabel ;//  借款额度Label
    private String  borrowAmountTip   ;//  借款额度Tip
    private String  canBorrowMaxAmount   ;//  可借最大额度（最大可借额度）
    private String  canBorrowDayLabel ;// 可借天数Label
    private String  canBorrowMaxDay ;// 可借最大天数
    private String  canBorrowMaxDayTip ;// 可借最大天数Tip
    private String  borrowModeLabel ;// 借款方式(怎么还)Label
    private String  borrowMode  ;// 借款方式(怎么还：默认：==到期，还本付息==)
    private String  canBorrowAmountInterest  ;// 可借额度计息(eg:0.0035)
    private String  totalInterestLabel   ;// 总利息Label
    private String totalInterest   ;// 总利息
    private String repaymentDateLabel ;// 还款日期Label
    private String repaymentDate ;// 还款日期
    private String repaymentPrincipalAndInterestLabel ;// 还款金额（本金+利息）Label
    private String repaymentPrincipalAndInterest ;// 还款金额（本金+利息）
    private String receivableBankCardLabel ;// 收款银行卡号Label
    private String receivableBankCard ;// 收款银行卡号
    private String borrowTipDescription   ;// 借款描述
    private String contractLabel ;// 合同查看Label
    private String contractTitle ;// 合同查看Title
    private String contractURL ;// 合同查看URL
    private String canBorrowMinAmount   ;//  可借最小额度
    private String canBorrowAddAmount  ;//  可借递增额度
    private String phone  ;//
    private String isSendSMS  ;// 是否需要短信验证（true，需要，false不需要）
    private String helpURLTitle;// 常见问题URLTitle
    private String helpURL ;//常见问题URL
    private String helpURLLabel ;// 常见问题

    private String loanAmountTip;//借款金额提示信息
    private String guaranteeProjectLabel;//担保项目label
    private String guaranteeAmountLabel;//担保金额label
    private String guaranteeProject;
    private String guaranteeAmount;

    public String getHelpURLTitle() {
        return helpURLTitle;
    }

    public void setHelpURLTitle(String helpURLTitle) {
        this.helpURLTitle = helpURLTitle;
    }

    public String getHelpURL() {
        return helpURL;
    }

    public void setHelpURL(String helpURL) {
        this.helpURL = helpURL;
    }

    public String getHelpURLLabel() {
        return helpURLLabel;
    }

    public void setHelpURLLabel(String helpURLLabel) {
        this.helpURLLabel = helpURLLabel;
    }

    public String getBorrowOrderLabel() {
        return borrowOrderLabel;
    }

    public void setBorrowOrderLabel(String borrowOrderLabel) {
        this.borrowOrderLabel = borrowOrderLabel;
    }

    public String getLoanAmountTip() {
        return loanAmountTip;
    }

    public void setLoanAmountTip(String loanAmountTip) {
        this.loanAmountTip = loanAmountTip;
    }

    public String getGuaranteeProject() {
        return guaranteeProject;
    }

    public void setGuaranteeProject(String guaranteeProject) {
        this.guaranteeProject = guaranteeProject;
    }

    public String getGuaranteeAmount() {
        return guaranteeAmount;
    }

    public void setGuaranteeAmount(String guaranteeAmount) {
        this.guaranteeAmount = guaranteeAmount;
    }

    public String getBorrowAmountLabel() {
        return borrowAmountLabel;
    }

    public void setBorrowAmountLabel(String borrowAmountLabel) {
        this.borrowAmountLabel = borrowAmountLabel;
    }

    public String getBorrowAmountTip() {
        return borrowAmountTip;
    }

    public void setBorrowAmountTip(String borrowAmountTip) {
        this.borrowAmountTip = borrowAmountTip;
    }

    public String getCanBorrowMaxAmount() {
        return canBorrowMaxAmount;
    }

    public void setCanBorrowMaxAmount(String canBorrowMaxAmount) {
        this.canBorrowMaxAmount = canBorrowMaxAmount;
    }

    public String getCanBorrowDayLabel() {
        return canBorrowDayLabel;
    }

    public void setCanBorrowDayLabel(String canBorrowDayLabel) {
        this.canBorrowDayLabel = canBorrowDayLabel;
    }

    public String getCanBorrowMaxDay() {
        return canBorrowMaxDay;
    }

    public void setCanBorrowMaxDay(String canBorrowMaxDay) {
        this.canBorrowMaxDay = canBorrowMaxDay;
    }

    public String getCanBorrowMaxDayTip() {
        return canBorrowMaxDayTip;
    }

    public void setCanBorrowMaxDayTip(String canBorrowMaxDayTip) {
        this.canBorrowMaxDayTip = canBorrowMaxDayTip;
    }

    public String getBorrowModeLabel() {
        return borrowModeLabel;
    }

    public void setBorrowModeLabel(String borrowModeLabel) {
        this.borrowModeLabel = borrowModeLabel;
    }

    public String getBorrowMode() {
        return borrowMode;
    }

    public void setBorrowMode(String borrowMode) {
        this.borrowMode = borrowMode;
    }

    public String getCanBorrowAmountInterest() {
        return canBorrowAmountInterest;
    }

    public void setCanBorrowAmountInterest(String canBorrowAmountInterest) {
        this.canBorrowAmountInterest = canBorrowAmountInterest;
    }

    public String getTotalInterestLabel() {
        return totalInterestLabel;
    }

    public void setTotalInterestLabel(String totalInterestLabel) {
        this.totalInterestLabel = totalInterestLabel;
    }

    public String getTotalInterest() {
        return totalInterest;
    }

    public void setTotalInterest(String totalInterest) {
        this.totalInterest = totalInterest;
    }

    public String getRepaymentDateLabel() {
        return repaymentDateLabel;
    }

    public void setRepaymentDateLabel(String repaymentDateLabel) {
        this.repaymentDateLabel = repaymentDateLabel;
    }

    public String getRepaymentDate() {
        return repaymentDate;
    }

    public void setRepaymentDate(String repaymentDate) {
        this.repaymentDate = repaymentDate;
    }

    public String getRepaymentPrincipalAndInterestLabel() {
        return repaymentPrincipalAndInterestLabel;
    }

    public void setRepaymentPrincipalAndInterestLabel(String repaymentPrincipalAndInterestLabel) {
        this.repaymentPrincipalAndInterestLabel = repaymentPrincipalAndInterestLabel;
    }

    public String getRepaymentPrincipalAndInterest() {
        return repaymentPrincipalAndInterest;
    }

    public void setRepaymentPrincipalAndInterest(String repaymentPrincipalAndInterest) {
        this.repaymentPrincipalAndInterest = repaymentPrincipalAndInterest;
    }

    public String getReceivableBankCardLabel() {
        return receivableBankCardLabel;
    }

    public void setReceivableBankCardLabel(String receivableBankCardLabel) {
        this.receivableBankCardLabel = receivableBankCardLabel;
    }

    public String getReceivableBankCard() {
        return receivableBankCard;
    }

    public void setReceivableBankCard(String receivableBankCard) {
        this.receivableBankCard = receivableBankCard;
    }

    public String getBorrowTipDescription() {
        return borrowTipDescription;
    }

    public void setBorrowTipDescription(String borrowTipDescription) {
        this.borrowTipDescription = borrowTipDescription;
    }

    public String getContractLabel() {
        return contractLabel;
    }

    public void setContractLabel(String contractLabel) {
        this.contractLabel = contractLabel;
    }

    public String getContractTitle() {
        return contractTitle;
    }

    public void setContractTitle(String contractTitle) {
        this.contractTitle = contractTitle;
    }

    public String getContractURL() {
        return contractURL;
    }

    public void setContractURL(String contractURL) {
        this.contractURL = contractURL;
    }

    public String getCanBorrowMinAmount() {
        return canBorrowMinAmount;
    }

    public void setCanBorrowMinAmount(String canBorrowMinAmount) {
        this.canBorrowMinAmount = canBorrowMinAmount;
    }

    public String getCanBorrowAddAmount() {
        return canBorrowAddAmount;
    }

    public void setCanBorrowAddAmount(String canBorrowAddAmount) {
        this.canBorrowAddAmount = canBorrowAddAmount;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIsSendSMS() {
        return isSendSMS;
    }

    public void setIsSendSMS(String isSendSMS) {
        this.isSendSMS = isSendSMS;
    }

    public String getGuaranteeProjectLabel() {
        return guaranteeProjectLabel;
    }

    public void setGuaranteeProjectLabel(String guaranteeProjectLabel) {
        this.guaranteeProjectLabel = guaranteeProjectLabel;
    }

    public String getGuaranteeAmountLabel() {
        return guaranteeAmountLabel;
    }

    public void setGuaranteeAmountLabel(String guaranteeAmountLabel) {
        this.guaranteeAmountLabel = guaranteeAmountLabel;
    }
}
