package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan;

import java.util.List;
import java.util.Map;

/**
 * Created by wzf on 2016/11/3.
 */
public class ImproveApplyInfoCommitResultDTO extends FundLoanApplyRepayTrialDTO {

    private String loanPicUrl;
    private String loanBigTip;
    private String loanSmallTip;
    private List<Map<String,String>> applyDetailInfo;
    private String repayDetailLabel;
    private String repayDetail;

    public List<Map<String, String>> getApplyDetailInfo() {
        return applyDetailInfo;
    }

    public void setApplyDetailInfo(List<Map<String, String>> applyDetailInfo) {
        this.applyDetailInfo = applyDetailInfo;
    }

    public String getRepayDetailLabel() {
        return repayDetailLabel;
    }

    public void setRepayDetailLabel(String repayDetailLabel) {
        this.repayDetailLabel = repayDetailLabel;
    }

    public String getRepayDetail() {
        return repayDetail;
    }

    public void setRepayDetail(String repayDetail) {
        this.repayDetail = repayDetail;
    }

    public String getLoanPicUrl() {
        return loanPicUrl;
    }

    public void setLoanPicUrl(String loanPicUrl) {
        this.loanPicUrl = loanPicUrl;
    }

    public String getLoanBigTip() {
        return loanBigTip;
    }

    public void setLoanBigTip(String loanBigTip) {
        this.loanBigTip = loanBigTip;
    }

    public String getLoanSmallTip() {
        return loanSmallTip;
    }

    public void setLoanSmallTip(String loanSmallTip) {
        this.loanSmallTip = loanSmallTip;
    }
}
