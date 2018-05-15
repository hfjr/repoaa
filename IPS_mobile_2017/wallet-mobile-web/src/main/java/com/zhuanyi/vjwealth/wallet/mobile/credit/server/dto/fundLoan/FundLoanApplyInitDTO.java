package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan;

import com.zhuanyi.vjwealth.loan.order.vo.LoanInfoVo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by wzf on 2016/10/27.
 */
public class FundLoanApplyInitDTO extends FundLoanApplyRepayTrialDTO implements Serializable {

    private String canBorrowMaxAmount;// 50000,
    private String  borrowAmount;// 50000,
    private String  canBorrowMinAmount;// 2000,
    private String  loanProductDesc;// 按日计息，日利率低至 0.035%,
    private String  quotaDescription;//

    private String repaymentPeriodLabel;// 还款期数,
    private Map<String,String> defaultRepaymentPeriod;// {key;//1,value;//1个月},
    private List<Map<String,String>> repaymentPeriodSelection;//
    private String repaymentMethodLabel;// 还款方式,
    private String repaymentMethod;// 等额本息,
    private List<Map<String,String>> repaymentMethodSelection;//
    private String  firstRepaymentLabel;// 首次还款,
    private String  repaymentDateLabel;// 还款日,
    private String  repaymentDetailLabel;// 还款详情,
    private String  repaymentDetail;// 点击查看,
    private String  isClickRepaymentDetail;// true,

    private String loanNotesDetail;//借款须知title
    private List<LoanInfoVo> loanNotesDetailContent;//内容

    private String loanNotes;

    public String getCanBorrowMaxAmount() {
        return canBorrowMaxAmount;
    }

    public void setCanBorrowMaxAmount(String canBorrowMaxAmount) {
        this.canBorrowMaxAmount = canBorrowMaxAmount;
    }

    public String getBorrowAmount() {
        return borrowAmount;
    }

    public void setBorrowAmount(String borrowAmount) {
        this.borrowAmount = borrowAmount;
    }

    public String getCanBorrowMinAmount() {
        return canBorrowMinAmount;
    }

    public void setCanBorrowMinAmount(String canBorrowMinAmount) {
        this.canBorrowMinAmount = canBorrowMinAmount;
    }

    public String getLoanProductDesc() {
        return loanProductDesc;
    }

    public void setLoanProductDesc(String loanProductDesc) {
        this.loanProductDesc = loanProductDesc;
    }

    public String getQuotaDescription() {
        return quotaDescription;
    }

    public void setQuotaDescription(String quotaDescription) {
        this.quotaDescription = quotaDescription;
    }

    public String getRepaymentPeriodLabel() {
        return repaymentPeriodLabel;
    }

    public void setRepaymentPeriodLabel(String repaymentPeriodLabel) {
        this.repaymentPeriodLabel = repaymentPeriodLabel;
    }

    public Map<String, String> getDefaultRepaymentPeriod() {
        return defaultRepaymentPeriod;
    }

    public void setDefaultRepaymentPeriod(Map<String, String> defaultRepaymentPeriod) {
        this.defaultRepaymentPeriod = defaultRepaymentPeriod;
    }

    public List<Map<String, String>> getRepaymentPeriodSelection() {
        return repaymentPeriodSelection;
    }

    public void setRepaymentPeriodSelection(List<Map<String, String>> repaymentPeriodSelection) {
        this.repaymentPeriodSelection = repaymentPeriodSelection;
    }

    public String getRepaymentMethodLabel() {
        return repaymentMethodLabel;
    }

    public void setRepaymentMethodLabel(String repaymentMethodLabel) {
        this.repaymentMethodLabel = repaymentMethodLabel;
    }

    public String getRepaymentMethod() {
        return repaymentMethod;
    }

    public void setRepaymentMethod(String repaymentMethod) {
        this.repaymentMethod = repaymentMethod;
    }

    public List<Map<String, String>> getRepaymentMethodSelection() {
        return repaymentMethodSelection;
    }

    public void setRepaymentMethodSelection(List<Map<String, String>> repaymentMethodSelection) {
        this.repaymentMethodSelection = repaymentMethodSelection;
    }

    public String getFirstRepaymentLabel() {
        return firstRepaymentLabel;
    }

    public void setFirstRepaymentLabel(String firstRepaymentLabel) {
        this.firstRepaymentLabel = firstRepaymentLabel;
    }

    public String getRepaymentDateLabel() {
        return repaymentDateLabel;
    }

    public void setRepaymentDateLabel(String repaymentDateLabel) {
        this.repaymentDateLabel = repaymentDateLabel;
    }

    public String getRepaymentDetailLabel() {
        return repaymentDetailLabel;
    }

    public void setRepaymentDetailLabel(String repaymentDetailLabel) {
        this.repaymentDetailLabel = repaymentDetailLabel;
    }

    public String getRepaymentDetail() {
        return repaymentDetail;
    }

    public void setRepaymentDetail(String repaymentDetail) {
        this.repaymentDetail = repaymentDetail;
    }

    public String getIsClickRepaymentDetail() {
        return isClickRepaymentDetail;
    }

    public void setIsClickRepaymentDetail(String isClickRepaymentDetail) {
        this.isClickRepaymentDetail = isClickRepaymentDetail;
    }

    public String getLoanNotesDetail() {
        return loanNotesDetail;
    }

    public void setLoanNotesDetail(String loanNotesDetail) {
        this.loanNotesDetail = loanNotesDetail;
    }

    public List<LoanInfoVo> getLoanNotesDetailContent() {
        return loanNotesDetailContent;
    }

    public void setLoanNotesDetailContent(List<LoanInfoVo> loanNotesDetailContent) {
        this.loanNotesDetailContent = loanNotesDetailContent;
    }

    public String getLoanNotes() {
        return loanNotes;
    }

    public void setLoanNotes(String loanNotes) {
        this.loanNotes = loanNotes;
    }
}
