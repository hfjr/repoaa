package com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto;

import java.math.BigDecimal;
import java.util.Date;

public class RfRepayPlanDTO {
	
	private String repaymentPlanId;
	private String userId;
	private String orderNo;
	private String repaymentType;
	private String isRepay;
	
	private int period;// 期数
	private int totalPeriod;// 总期数
	private Date planRepayDateTime;// 计划还款日期
	private long planRepayDate;// 计划还款日期
	private BigDecimal planRepayLoanInterest; // 计划信贷产品利息
	private BigDecimal planRepayInterest; // 计划还款利息
	private BigDecimal planRepayPrincipal; // 计划还款本金
	
	private long repayDate; // 还款日期
	private BigDecimal repayLoanInterest; // 信贷利息
	private BigDecimal repayInterest; // 还款利息
	private BigDecimal repayPrincipal; // 还款本金
	
	private String productFlag; // 产品表示，普通标、新手标
	private BigDecimal greenhornInterest; // 新手标补贴利息
	
	private BigDecimal otherFee; // 小赢平台补贴用户

	private String isPreExpire;//是否提前到期
	private String preExpireStatus;//提前到期状态

	private String ifForzen;//是否冻结
	private String productCategory;

	public int getPeriod() {
		return period;
	}
	
	public void setPeriod(int period) {
		this.period = period;
	}
	
	public long getPlanRepayDate() {
		return planRepayDate;
	}
	
	public void setPlanRepayDate(long planRepayDate) {
		this.planRepayDate = planRepayDate;
	}
	
	public BigDecimal getPlanRepayInterest() {
		return planRepayInterest;
	}
	
	public void setPlanRepayInterest(BigDecimal planRepayInterest) {
		this.planRepayInterest = planRepayInterest;
	}
	
	public BigDecimal getPlanRepayPrincipal() {
		return planRepayPrincipal;
	}
	
	public void setPlanRepayPrincipal(BigDecimal planRepayPrincipal) {
		this.planRepayPrincipal = planRepayPrincipal;
	}
	
	public long getRepayDate() {
		return repayDate;
	}
	
	public void setRepayDate(long repayDate) {
		this.repayDate = repayDate;
	}
	
	public BigDecimal getRepayInterest() {
		return repayInterest;
	}
	
	public void setRepayInterest(BigDecimal repayInterest) {
		this.repayInterest = repayInterest;
	}
	
	public BigDecimal getRepayPrincipal() {
		return repayPrincipal;
	}
	
	public void setRepayPrincipal(BigDecimal repayPrincipal) {
		this.repayPrincipal = repayPrincipal;
	}

	public String getRepaymentPlanId() {
		return repaymentPlanId;
	}

	public void setRepaymentPlanId(String repaymentPlanId) {
		this.repaymentPlanId = repaymentPlanId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getRepaymentType() {
		return repaymentType;
	}

	public void setRepaymentType(String repaymentType) {
		this.repaymentType = repaymentType;
	}

	public String getIsRepay() {
		return isRepay;
	}

	public void setIsRepay(String isRepay) {
		this.isRepay = isRepay;
	}

	public Date getPlanRepayDateTime() {
		return planRepayDateTime;
	}

	public void setPlanRepayDateTime(Date planRepayDateTime) {
		this.planRepayDateTime = planRepayDateTime;
	}

	public String getProductFlag() {
		return productFlag;
	}

	public void setProductFlag(String productFlag) {
		this.productFlag = productFlag;
	}

	public BigDecimal getGreenhornInterest() {
		return greenhornInterest;
	}

	public void setGreenhornInterest(BigDecimal greenhornInterest) {
		this.greenhornInterest = greenhornInterest;
	}

	public BigDecimal getOtherFee() {
		return otherFee;
	}

	public void setOtherFee(BigDecimal otherFee) {
		this.otherFee = otherFee;
	}

	public int getTotalperiod() {
		return totalPeriod;
	}

	public void setTotalperiod(int totalperiod) {
		this.totalPeriod = totalperiod;
	}

	public BigDecimal getPlanRepayLoanInterest() {
		return planRepayLoanInterest;
	}

	public void setPlanRepayLoanInterest(BigDecimal planRepayLoanInterest) {
		this.planRepayLoanInterest = planRepayLoanInterest;
	}

	public BigDecimal getRepayLoanInterest() {
		return repayLoanInterest;
	}

	public void setRepayLoanInterest(BigDecimal repayLoanInterest) {
		this.repayLoanInterest = repayLoanInterest;
	}

	public String getIsPreExpire() {
		return isPreExpire;
	}

	public void setIsPreExpire(String isPreExpire) {
		this.isPreExpire = isPreExpire;
	}

	public String getPreExpireStatus() {
		return preExpireStatus;
	}

	public void setPreExpireStatus(String preExpireStatus) {
		this.preExpireStatus = preExpireStatus;
	}

	public String getIfForzen() {
		return ifForzen;
	}

	public void setIfForzen(String ifForzen) {
		this.ifForzen = ifForzen;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	@Override
	public String toString() {
		return "RfRepayPlanDTO{" +
				"repaymentPlanId='" + repaymentPlanId + '\'' +
				", userId='" + userId + '\'' +
				", orderNo='" + orderNo + '\'' +
				", repaymentType='" + repaymentType + '\'' +
				", isRepay='" + isRepay + '\'' +
				", period=" + period +
				", totalPeriod=" + totalPeriod +
				", planRepayDateTime=" + planRepayDateTime +
				", planRepayDate=" + planRepayDate +
				", planRepayLoanInterest=" + planRepayLoanInterest +
				", planRepayInterest=" + planRepayInterest +
				", planRepayPrincipal=" + planRepayPrincipal +
				", repayDate=" + repayDate +
				", repayLoanInterest=" + repayLoanInterest +
				", repayInterest=" + repayInterest +
				", repayPrincipal=" + repayPrincipal +
				", productFlag='" + productFlag + '\'' +
				", greenhornInterest=" + greenhornInterest +
				", otherFee=" + otherFee +
				", isPreExpire='" + isPreExpire + '\'' +
				", preExpireStatus='" + preExpireStatus + '\'' +
				", ifForzen='" + ifForzen + '\'' +
				'}';
	}
}
