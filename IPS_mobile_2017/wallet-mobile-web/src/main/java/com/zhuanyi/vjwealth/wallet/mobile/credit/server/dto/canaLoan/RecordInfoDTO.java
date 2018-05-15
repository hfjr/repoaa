package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.canaLoan;

import java.io.Serializable;
import java.util.Date;

/**
 * @Copyright (C), 2015-2016,蔚捷互联网金融服务有限公司
 * @desc: 借款记录
 * @author: Tony Tang
 * @date: 2016-05-16 19:43
 */
public class RecordInfoDTO implements Serializable {
    private String repaymentDate;
    private String repaymentAmount;
    private String auditResultDesc;
    private String repaymentStatusMarkURL;
    private String debitType;
    private String repaymentPeriodCode;
    private String borrowCode;

    public String getRepaymentDate() {
        return repaymentDate;
    }

    public void setRepaymentDate(String repaymentDate) {
        this.repaymentDate = repaymentDate;
    }

    public String getRepaymentAmount() {
        return repaymentAmount;
    }

    public void setRepaymentAmount(String repaymentAmount) {
        this.repaymentAmount = repaymentAmount;
    }

    public String getAuditResultDesc() {
        return auditResultDesc;
    }

    public void setAuditResultDesc(String auditResultDesc) {
        this.auditResultDesc = auditResultDesc;
    }

    public String getRepaymentStatusMarkURL() {
        return repaymentStatusMarkURL;
    }

    public void setRepaymentStatusMarkURL(String repaymentStatusMarkURL) {
        this.repaymentStatusMarkURL = repaymentStatusMarkURL;
    }

    public String getDebitType() {
        return debitType;
    }

    public void setDebitType(String debitType) {
        this.debitType = debitType;
    }

    public String getRepaymentPeriodCode() {
        return repaymentPeriodCode;
    }

    public void setRepaymentPeriodCode(String repaymentPeriodCode) {
        this.repaymentPeriodCode = repaymentPeriodCode;
    }

    public String getBorrowCode() {
        return borrowCode;
    }

    public void setBorrowCode(String borrowCode) {
        this.borrowCode = borrowCode;
    }
}
