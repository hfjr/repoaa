package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.pledgeInvest;




/**
 * Created by wangzhangfei on 16/7/12.
 */
public class EarlyRepaymentDetailDTO {

	private String repaymentMoneyLabel;// 还款金额,
	
	private String noRepaymentCapitalLabel;// 未还本金,
	private String noRepaymentCapital;// 100,000,
	private String capitalLabel;// 本金,
	private String capital;// 9,472.00,
	private String interestLabel;// 利息,
	private String interest;// 528.00,
	private String counterFeeLabel;// 手续费,
	private String counterFee;// 0.00,
	private String penaltyLabel;//罚息label
	private String penalty;//罚息
	private String repaymentTotalMoneyLabel;// 还款总额,
	
	private String repaymentTotalMoney;//还款总额
	private String repaymentMoney;// 100.00,//还款本金
	private String repaymentTotalMoneyDesc;//100,000.00
	private String repaymentMoneyDesc;//100,000.00
	
	private String availableBalanceMoneyLabel;// "可用余额",
	private String availableBalanceMoney;// "100000.00",
	private String availableBalanceMoneyDesc;// "100,000.00"


	public String getPenaltyLabel() {
		return penaltyLabel;
	}

	public void setPenaltyLabel(String penaltyLabel) {
		this.penaltyLabel = penaltyLabel;
	}

	public String getPenalty() {
		return penalty;
	}

	public void setPenalty(String penalty) {
		this.penalty = penalty;
	}

	public String getRepaymentMoneyLabel() {
		return repaymentMoneyLabel;
	}
	public void setRepaymentMoneyLabel(String repaymentMoneyLabel) {
		this.repaymentMoneyLabel = repaymentMoneyLabel;
	}
	public String getRepaymentMoney() {
		return repaymentMoney;
	}
	public void setRepaymentMoney(String repaymentMoney) {
		this.repaymentMoney = repaymentMoney;
	}
	public String getNoRepaymentCapitalLabel() {
		return noRepaymentCapitalLabel;
	}
	public void setNoRepaymentCapitalLabel(String noRepaymentCapitalLabel) {
		this.noRepaymentCapitalLabel = noRepaymentCapitalLabel;
	}
	public String getNoRepaymentCapital() {
		return noRepaymentCapital;
	}
	public void setNoRepaymentCapital(String noRepaymentCapital) {
		this.noRepaymentCapital = noRepaymentCapital;
	}
	public String getCapitalLabel() {
		return capitalLabel;
	}
	public void setCapitalLabel(String capitalLabel) {
		this.capitalLabel = capitalLabel;
	}
	public String getCapital() {
		return capital;
	}
	public void setCapital(String capital) {
		this.capital = capital;
	}
	public String getInterestLabel() {
		return interestLabel;
	}
	public void setInterestLabel(String interestLabel) {
		this.interestLabel = interestLabel;
	}
	public String getInterest() {
		return interest;
	}
	public void setInterest(String interest) {
		this.interest = interest;
	}
	public String getCounterFeeLabel() {
		return counterFeeLabel;
	}
	public void setCounterFeeLabel(String counterFeeLabel) {
		this.counterFeeLabel = counterFeeLabel;
	}
	public String getCounterFee() {
		return counterFee;
	}
	public void setCounterFee(String counterFee) {
		this.counterFee = counterFee;
	}
	public String getRepaymentTotalMoneyLabel() {
		return repaymentTotalMoneyLabel;
	}
	public void setRepaymentTotalMoneyLabel(String repaymentTotalMoneyLabel) {
		this.repaymentTotalMoneyLabel = repaymentTotalMoneyLabel;
	}
	public String getRepaymentTotalMoney() {
		return repaymentTotalMoney;
	}
	public void setRepaymentTotalMoney(String repaymentTotalMoney) {
		this.repaymentTotalMoney = repaymentTotalMoney;
	}
	public String getRepaymentTotalMoneyDesc() {
		return repaymentTotalMoneyDesc;
	}
	public void setRepaymentTotalMoneyDesc(String repaymentTotalMoneyDesc) {
		this.repaymentTotalMoneyDesc = repaymentTotalMoneyDesc;
	}
	public String getRepaymentMoneyDesc() {
		return repaymentMoneyDesc;
	}
	public void setRepaymentMoneyDesc(String repaymentMoneyDesc) {
		this.repaymentMoneyDesc = repaymentMoneyDesc;
	}
	public String getAvailableBalanceMoneyLabel() {
		return availableBalanceMoneyLabel;
	}
	public void setAvailableBalanceMoneyLabel(String availableBalanceMoneyLabel) {
		this.availableBalanceMoneyLabel = availableBalanceMoneyLabel;
	}
	public String getAvailableBalanceMoney() {
		return availableBalanceMoney;
	}
	public void setAvailableBalanceMoney(String availableBalanceMoney) {
		this.availableBalanceMoney = availableBalanceMoney;
	}
	public String getAvailableBalanceMoneyDesc() {
		return availableBalanceMoneyDesc;
	}
	public void setAvailableBalanceMoneyDesc(String availableBalanceMoneyDesc) {
		this.availableBalanceMoneyDesc = availableBalanceMoneyDesc;
	}
	
}
