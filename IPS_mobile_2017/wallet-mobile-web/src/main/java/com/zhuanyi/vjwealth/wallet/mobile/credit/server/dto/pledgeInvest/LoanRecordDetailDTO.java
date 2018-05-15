package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.pledgeInvest;




/**
 * Created by wangzhangfei on 16/7/12.
 */
public class LoanRecordDetailDTO {

	private String loanCode;
	private String loanStatus;
	private String loanStatusTitle;
	private String topLoanAmountTitle;
	private String advanceRepayTitle;
	private String repayFinishTitle;
	private String noRepayPricipal;
	private String leftPeriodTip;
	private LoanRecordDetailInnerloanInfoDTO loanInfos;
	private LoanRecordDetailInnerrepaymentInfoDTO repaymentInfos;
	private LoanRecordDetailInnerpenalyInfoDTO penaltyInfos;

	private String isShowAdvancePaymentDesc;
	private String advancePaymentDesc;

	public LoanRecordDetailInnerpenalyInfoDTO getPenaltyInfos() {
		return penaltyInfos;
	}

	public void setPenaltyInfos(LoanRecordDetailInnerpenalyInfoDTO penaltyInfos) {
		this.penaltyInfos = penaltyInfos;
	}

	public String getLoanCode() {
		return loanCode;
	}
	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}
	public String getLoanStatus() {
		return loanStatus;
	}
	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}
	public String getLoanStatusTitle() {
		return loanStatusTitle;
	}
	public void setLoanStatusTitle(String loanStatusTitle) {
		this.loanStatusTitle = loanStatusTitle;
	}
	public String getTopLoanAmountTitle() {
		return topLoanAmountTitle;
	}
	public void setTopLoanAmountTitle(String topLoanAmountTitle) {
		this.topLoanAmountTitle = topLoanAmountTitle;
	}
	public String getAdvanceRepayTitle() {
		return advanceRepayTitle;
	}
	public void setAdvanceRepayTitle(String advanceRepayTitle) {
		this.advanceRepayTitle = advanceRepayTitle;
	}
	public String getRepayFinishTitle() {
		return repayFinishTitle;
	}
	public void setRepayFinishTitle(String repayFinishTitle) {
		this.repayFinishTitle = repayFinishTitle;
	}
	public LoanRecordDetailInnerloanInfoDTO getLoanInfos() {
		return loanInfos;
	}
	public void setLoanInfos(LoanRecordDetailInnerloanInfoDTO loanInfos) {
		this.loanInfos = loanInfos;
	}
	public LoanRecordDetailInnerrepaymentInfoDTO getRepaymentInfos() {
		return repaymentInfos;
	}
	public void setRepaymentInfos(
			LoanRecordDetailInnerrepaymentInfoDTO repaymentInfos) {
		this.repaymentInfos = repaymentInfos;
	}
	public String getNoRepayPricipal() {
		return noRepayPricipal;
	}
	public void setNoRepayPricipal(String noRepayPricipal) {
		this.noRepayPricipal = noRepayPricipal;
	}

	public String getLeftPeriodTip() {
		return leftPeriodTip;
	}

	public void setLeftPeriodTip(String leftPeriodTip) {
		this.leftPeriodTip = leftPeriodTip;
	}

	public String getIsShowAdvancePaymentDesc() {
		return isShowAdvancePaymentDesc;
	}

	public void setIsShowAdvancePaymentDesc(String isShowAdvancePaymentDesc) {
		this.isShowAdvancePaymentDesc = isShowAdvancePaymentDesc;
	}

	public String getAdvancePaymentDesc() {
		return advancePaymentDesc;
	}

	public void setAdvancePaymentDesc(String advancePaymentDesc) {
		this.advancePaymentDesc = advancePaymentDesc;
	}
}
