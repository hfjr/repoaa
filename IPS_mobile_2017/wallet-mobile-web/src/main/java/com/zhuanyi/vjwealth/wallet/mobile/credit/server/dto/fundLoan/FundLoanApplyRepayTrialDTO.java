package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wzf on 2016/10/27.
 */
public class FundLoanApplyRepayTrialDTO implements Serializable{

    private String  firstRepayment;// ¥1050.00（4月22日）,
    private String  repaymentDate;// 每月22日
    private String  monthlyPrincipalAndInterestLabel;//每月应还本息
    private String  monthlyPrincipalAndInterest;//借满5个月总利息￥200
    private List<String>  generatedRepaymentDetailLabels;//[还款时间,应还本金,应还利息]
    private List<FundLoanRepaymentDTO> generatedRepaymentDetailList;

    public String getFirstRepayment() {
        return firstRepayment;
    }

    public void setFirstRepayment(String firstRepayment) {
        this.firstRepayment = firstRepayment;
    }

    public String getRepaymentDate() {
        return repaymentDate;
    }

    public void setRepaymentDate(String repaymentDate) {
        this.repaymentDate = repaymentDate;
    }

    public String getMonthlyPrincipalAndInterestLabel() {
        return monthlyPrincipalAndInterestLabel;
    }

    public void setMonthlyPrincipalAndInterestLabel(String monthlyPrincipalAndInterestLabel) {
        this.monthlyPrincipalAndInterestLabel = monthlyPrincipalAndInterestLabel;
    }

    public String getMonthlyPrincipalAndInterest() {
        return monthlyPrincipalAndInterest;
    }

    public void setMonthlyPrincipalAndInterest(String monthlyPrincipalAndInterest) {
        this.monthlyPrincipalAndInterest = monthlyPrincipalAndInterest;
    }

    public List<String> getGeneratedRepaymentDetailLabels() {
        return generatedRepaymentDetailLabels;
    }

    public void setGeneratedRepaymentDetailLabels(List<String> generatedRepaymentDetailLabels) {
        this.generatedRepaymentDetailLabels = generatedRepaymentDetailLabels;
    }

    public List<FundLoanRepaymentDTO> getGeneratedRepaymentDetailList() {
        return generatedRepaymentDetailList;
    }

    public void setGeneratedRepaymentDetailList(List<FundLoanRepaymentDTO> generatedRepaymentDetailList) {
        this.generatedRepaymentDetailList = generatedRepaymentDetailList;
    }
}
