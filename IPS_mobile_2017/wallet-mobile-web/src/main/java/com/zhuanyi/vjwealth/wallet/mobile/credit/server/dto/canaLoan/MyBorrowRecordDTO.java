package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.canaLoan;

import com.zhuanyi.vjwealth.loan.order.cana.vo.MyBorrowRecordVo;

import java.io.Serializable;
import java.util.Date;

/**
 * @Copyright (C), 2015-2016,蔚捷互联网金融服务有限公司
 * @desc:借款申请入参实体
 * @author: Tony Tang
 * @date: 2016-05-13 17:08
 */
public class MyBorrowRecordDTO implements Serializable {

    private String borrowCode;
    private String borrowAmount;
    private String borrowDate;
    private String repaymentDate;
    private String planRepaymentDate;
    private String paidAmount;

    private String borrowAmountDescription;
    private String repaymentStatusMarkURL;
    private String statusMarkURL;
    private String borrowDateLabel;
    private String repaymentDateLabel;
    private String planRepaymentDateLabel;

    private String paidAmountLabel;
    private String repaymentStateDescription;

    public String getBorrowCode() {
        return borrowCode;
    }

    public void setBorrowCode(String borrowCode) {
        this.borrowCode = borrowCode;
    }

    public String getBorrowAmount() {
        return borrowAmount;
    }

    public void setBorrowAmount(String borrowAmount) {
        this.borrowAmount = borrowAmount;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }

    public String getRepaymentDate() {
        return repaymentDate;
    }

    public void setRepaymentDate(String repaymentDate) {
        this.repaymentDate = repaymentDate;
    }

    public String getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getBorrowAmountDescription() {
        return borrowAmountDescription;
    }

    public void setBorrowAmountDescription(String borrowAmountDescription) {
        this.borrowAmountDescription = borrowAmountDescription;
    }

    public String getRepaymentStatusMarkURL() {
        return repaymentStatusMarkURL;
    }

    public void setRepaymentStatusMarkURL(String repaymentStatusMarkURL) {
        this.repaymentStatusMarkURL = repaymentStatusMarkURL;
    }

    public String getStatusMarkURL() {
        return statusMarkURL;
    }

    public void setStatusMarkURL(String statusMarkURL) {
        this.statusMarkURL = statusMarkURL;
    }

    public String getBorrowDateLabel() {
        return borrowDateLabel;
    }

    public void setBorrowDateLabel(String borrowDateLabel) {
        this.borrowDateLabel = borrowDateLabel;
    }

    public String getRepaymentDateLabel() {
        return repaymentDateLabel;
    }

    public void setRepaymentDateLabel(String repaymentDateLabel) {
        this.repaymentDateLabel = repaymentDateLabel;
    }

    public String getPaidAmountLabel() {
        return paidAmountLabel;
    }

    public void setPaidAmountLabel(String paidAmountLabel) {
        this.paidAmountLabel = paidAmountLabel;
    }

    public String getRepaymentStateDescription() {
        return repaymentStateDescription;
    }

    public void setRepaymentStateDescription(String repaymentStateDescription) {
        this.repaymentStateDescription = repaymentStateDescription;
    }

    public String getPlanRepaymentDate() {
        return planRepaymentDate;
    }

    public void setPlanRepaymentDate(String planRepaymentDate) {
        this.planRepaymentDate = planRepaymentDate;
    }

    public String getPlanRepaymentDateLabel() {
        return planRepaymentDateLabel;
    }

    public void setPlanRepaymentDateLabel(String planRepaymentDateLabel) {
        this.planRepaymentDateLabel = planRepaymentDateLabel;
    }
}
