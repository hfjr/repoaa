package com.zhuanyi.vjwealth.wallet.mobile.bill.server.dto;

import java.util.Date;

/**
 * 工资先享还款账单详情
 * 
 * @author caozhaoming 2016年10月10日
 */
public class WageRepayOrderDTO {

	private Date repayTime;// 还款时间
	private String repayTotal;// 还款总额
	private String counterFee;// 手续费
	private String repayPrincipal;// 还款本金
	private String repayInterest;// 还款利息
	private String penalty;// 罚息
	private String cardNo;// 银行卡号

	public String getPenalty() {
		return penalty;
	}

	public void setPenalty(String penalty) {
		this.penalty = penalty;
	}

	public Date getRepayTime() {
		return repayTime;
	}

	public void setRepayTime(Date repayTime) {
		this.repayTime = repayTime;
	}

	public String getRepayTotal() {
		return repayTotal;
	}

	public void setRepayTotal(String repayTotal) {
		this.repayTotal = repayTotal;
	}

	public String getCounterFee() {
		return counterFee;
	}

	public void setCounterFee(String counterFee) {
		this.counterFee = counterFee;
	}

	public String getRepayPrincipal() {
		return repayPrincipal;
	}

	public void setRepayPrincipal(String repayPrincipal) {
		this.repayPrincipal = repayPrincipal;
	}

	public String getRepayInterest() {
		return repayInterest;
	}

	public void setRepayInterest(String repayInterest) {
		this.repayInterest = repayInterest;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	@Override
	public String toString() {
		return "RepayOrderDTO [repayTime=" + repayTime + ", repayTotal=" + repayTotal + ", counterFee=" + counterFee
				+ ", repayPrincipal=" + repayPrincipal + ", repayInterest=" + repayInterest + ", penalty=" + penalty
				+ ", cardNo=" + cardNo + "]";
	}

}
