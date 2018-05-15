package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.pledgeInvest;

import java.util.List;


/**
 * Created by wangzhangfei on 16/7/12.
 */
public class PledgeInvestInitiDTO {

	private String canBorrowAmountTitle;
	private String canBorrowAmount;
	private String totalCanBorrowAmountLabel;
	private String totalCanBorrowAmount;
	
	private String canBorrowAmountInterestDescription;
	private String canBorrowAmountInterest;
	private String isShowRepaymentButton;
	private String repayDescriptionLabel;
	private String borrowInvestTipTitle;
	private String noBorrowInvestTip;
	private String isMore;
	private List<PledgeInvestInitiInnerOrderInfoDTO> records;
	
	private String helpURLTitle;// 常见问题URLTitle
	private String helpURL ;//常见问题URL
	private String helpURLLabel ;// 常见问题
	
	public String getCanBorrowAmountTitle() {
		return canBorrowAmountTitle;
	}
	public void setCanBorrowAmountTitle(String canBorrowAmountTitle) {
		this.canBorrowAmountTitle = canBorrowAmountTitle;
	}
	public String getCanBorrowAmount() {
		return canBorrowAmount;
	}
	public void setCanBorrowAmount(String canBorrowAmount) {
		this.canBorrowAmount = canBorrowAmount;
	}
	public String getTotalCanBorrowAmountLabel() {
		return totalCanBorrowAmountLabel;
	}
	public void setTotalCanBorrowAmountLabel(String totalCanBorrowAmountLabel) {
		this.totalCanBorrowAmountLabel = totalCanBorrowAmountLabel;
	}
	public String getTotalCanBorrowAmount() {
		return totalCanBorrowAmount;
	}
	public void setTotalCanBorrowAmount(String totalCanBorrowAmount) {
		this.totalCanBorrowAmount = totalCanBorrowAmount;
	}
	public String getCanBorrowAmountInterestDescription() {
		return canBorrowAmountInterestDescription;
	}
	public void setCanBorrowAmountInterestDescription(
			String canBorrowAmountInterestDescription) {
		this.canBorrowAmountInterestDescription = canBorrowAmountInterestDescription;
	}
	public String getCanBorrowAmountInterest() {
		return canBorrowAmountInterest;
	}
	public void setCanBorrowAmountInterest(String canBorrowAmountInterest) {
		this.canBorrowAmountInterest = canBorrowAmountInterest;
	}
	public String getIsShowRepaymentButton() {
		return isShowRepaymentButton;
	}
	public void setIsShowRepaymentButton(String isShowRepaymentButton) {
		this.isShowRepaymentButton = isShowRepaymentButton;
	}
	public String getIsMore() {
		return isMore;
	}
	public void setIsMore(String isMore) {
		this.isMore = isMore;
	}
	public List<PledgeInvestInitiInnerOrderInfoDTO> getRecords() {
		return records;
	}
	public void setRecords(List<PledgeInvestInitiInnerOrderInfoDTO> records) {
		this.records = records;
	}
	public String getRepayDescriptionLabel() {
		return repayDescriptionLabel;
	}
	public void setRepayDescriptionLabel(String repayDescriptionLabel) {
		this.repayDescriptionLabel = repayDescriptionLabel;
	}
	public String getBorrowInvestTipTitle() {
		return borrowInvestTipTitle;
	}
	public void setBorrowInvestTipTitle(String borrowInvestTipTitle) {
		this.borrowInvestTipTitle = borrowInvestTipTitle;
	}
	public String getNoBorrowInvestTip() {
		return noBorrowInvestTip;
	}
	public void setNoBorrowInvestTip(String noBorrowInvestTip) {
		this.noBorrowInvestTip = noBorrowInvestTip;
	}
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
	
}
