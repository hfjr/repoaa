package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.wageAdvance;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 工资先享-借款申请初始化
 * Created by wangzf on 16/5/20.
 */
public class WageAdvanceInitiDTO{

    private String helpURLTitle;
    private String helpURL;
    private String helpURLLabel;
    private String borrowAmountLabel;
    private String borrowAmountInputTip;
    private String canBorrowMaxAmountLabel;

    private String canBorrowMaxAmountDesc;
    private String canBorrowAmountLabel;
    private String canBorrowAmount;
    private String canBorrowAmountDesc;
    private String canBorrowDayLabel;
    private Map<String,String> canBorrowDefaultPeriod;
    private List<Map<String,String>> canBorrowPeriods;
    private String canBorrowMinAmount;
    private String canBorrowAddAmount;
    private String borrowModeLabel;


    private Map<String,String> borrowDefaultMode;
    private List<Map<String,String>> borrowModes;
    private String canBorrowAmountInterest;
    private String totalInterestLabel;
    private String totalInterest;


    private String repaymentDateLabel;
    private String repaymentDate;
    private String receivableBankCardLabel;
    private String receivableBankCard;
    private String borrowTipDescription;
    private String contractLabel;

    private String contractTitle;
    private String contractURL;
    private String isSendSMS;
    private String isShowRepaymentButton;


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

    public String getBorrowAmountLabel() {
        return borrowAmountLabel;
    }

    public void setBorrowAmountLabel(String borrowAmountLabel) {
        this.borrowAmountLabel = borrowAmountLabel;
    }

    public String getBorrowAmountInputTip() {
        return borrowAmountInputTip;
    }

    public void setBorrowAmountInputTip(String borrowAmountInputTip) {
        this.borrowAmountInputTip = borrowAmountInputTip;
    }

    public String getCanBorrowMaxAmountLabel() {
        return canBorrowMaxAmountLabel;
    }

    public void setCanBorrowMaxAmountLabel(String canBorrowMaxAmountLabel) {
        this.canBorrowMaxAmountLabel = canBorrowMaxAmountLabel;
    }

    public String getCanBorrowMaxAmountDesc() {
        return canBorrowMaxAmountDesc;
    }

    public void setCanBorrowMaxAmountDesc(String canBorrowMaxAmountDesc) {
        this.canBorrowMaxAmountDesc = canBorrowMaxAmountDesc;
    }

    public String getCanBorrowAmountLabel() {
        return canBorrowAmountLabel;
    }

    public void setCanBorrowAmountLabel(String canBorrowAmountLabel) {
        this.canBorrowAmountLabel = canBorrowAmountLabel;
    }

    public String getCanBorrowAmount() {
        return canBorrowAmount;
    }

    public void setCanBorrowAmount(String canBorrowAmount) {
        this.canBorrowAmount = canBorrowAmount;
    }

    public String getCanBorrowAmountDesc() {
        return canBorrowAmountDesc;
    }

    public void setCanBorrowAmountDesc(String canBorrowAmountDesc) {
        this.canBorrowAmountDesc = canBorrowAmountDesc;
    }

    public String getCanBorrowDayLabel() {
        return canBorrowDayLabel;
    }

    public void setCanBorrowDayLabel(String canBorrowDayLabel) {
        this.canBorrowDayLabel = canBorrowDayLabel;
    }

    public Map<String, String> getCanBorrowDefaultPeriod() {
        return canBorrowDefaultPeriod;
    }

    public void setCanBorrowDefaultPeriod(Map<String, String> canBorrowDefaultPeriod) {
        this.canBorrowDefaultPeriod = canBorrowDefaultPeriod;
    }

    public List<Map<String, String>> getCanBorrowPeriods() {
        return canBorrowPeriods;
    }

    public void setCanBorrowPeriods(List<Map<String, String>> canBorrowPeriods) {
        this.canBorrowPeriods = canBorrowPeriods;
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

    public String getBorrowModeLabel() {
        return borrowModeLabel;
    }

    public void setBorrowModeLabel(String borrowModeLabel) {
        this.borrowModeLabel = borrowModeLabel;
    }

    public Map<String, String> getBorrowDefaultMode() {
        return borrowDefaultMode;
    }

    public void setBorrowDefaultMode(Map<String, String> borrowDefaultMode) {
        this.borrowDefaultMode = borrowDefaultMode;
    }

    public List<Map<String, String>> getBorrowModes() {
        return borrowModes;
    }

    public void setBorrowModes(List<Map<String, String>> borrowModes) {
        this.borrowModes = borrowModes;
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

    public String getIsSendSMS() {
        return isSendSMS;
    }

    public void setIsSendSMS(String isSendSMS) {
        this.isSendSMS = isSendSMS;
    }

    public String getIsShowRepaymentButton() {
        return isShowRepaymentButton;
    }

    public void setIsShowRepaymentButton(String isShowRepaymentButton) {
        this.isShowRepaymentButton = isShowRepaymentButton;
    }
}
