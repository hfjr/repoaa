package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.myloan;

/**
 * Created by wzf on 2016/9/8.
 */
public class MyLoanDetailInnerListDTO {

    private String repaymentAmount;// 还款金额,
    private String repaymentStatus;// 还款状态,
    private String repaymentDate;// 还款日期,
    private String repaymentStatusMarkURL;// 还款状态图标

    public String getRepaymentAmount() {
        return repaymentAmount;
    }

    public void setRepaymentAmount(String repaymentAmount) {
        this.repaymentAmount = repaymentAmount;
    }

    public String getRepaymentStatus() {
        return repaymentStatus;
    }

    public void setRepaymentStatus(String repaymentStatus) {
        this.repaymentStatus = repaymentStatus;
    }

    public String getRepaymentDate() {
        return repaymentDate;
    }

    public void setRepaymentDate(String repaymentDate) {
        this.repaymentDate = repaymentDate;
    }

    public String getRepaymentStatusMarkURL() {
        return repaymentStatusMarkURL;
    }

    public void setRepaymentStatusMarkURL(String repaymentStatusMarkURL) {
        this.repaymentStatusMarkURL = repaymentStatusMarkURL;
    }
}
