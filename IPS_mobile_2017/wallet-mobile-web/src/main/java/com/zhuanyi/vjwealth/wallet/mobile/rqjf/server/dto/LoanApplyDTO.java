package com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.dto;



public class LoanApplyDTO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	// 交易号
	 private java.lang.String tradeNo; 
	// 用户Id
	 private java.lang.String userId; 
	// 贷款产品类型(house/car/cash)
	 private java.lang.String productType; 
	// 申请时间
	private String applyTime;
	// 真实姓名
	 private java.lang.String realName; 
	// 证件号
	 private java.lang.String idNo; 
	// 手机号
	 private java.lang.String phone; 
	// 贷款金额
	 private java.lang.String loanAmt; 
	// 申请状态
	 private java.lang.String applyStatus; 
	// 推荐人id
	 private java.lang.String recommenderId; 
	// 失败原因
	 private java.lang.String errorMessage; 
	// 备注
	 private java.lang.String remark; 
	 // 推荐人号码
	 private String recommendPhone;
	 
	
	public String getRecommendPhone() {
		return recommendPhone;
	}

	public void setRecommendPhone(String recommendPhone) {
		this.recommendPhone = recommendPhone;
	}

	public java.lang.String getTradeNo() {
		return tradeNo;
	}
	
	public void setTradeNo(java.lang.String tradeNo) {
		this.tradeNo=tradeNo;
	}
	public java.lang.String getUserId() {
		return userId;
	}
	
	public void setUserId(java.lang.String userId) {
		this.userId=userId;
	}
	public java.lang.String getProductType() {
		return productType;
	}
	
	public void setProductType(java.lang.String productType) {
		this.productType=productType;
	}
	public String getApplyTime() {
		return applyTime;
	}
	
	public void setApplyTime(String applyTime) {
		this.applyTime=applyTime;
	}
	public java.lang.String getRealName() {
		return realName;
	}
	
	public void setRealName(java.lang.String realName) {
		this.realName=realName;
	}
	public java.lang.String getIdNo() {
		return idNo;
	}
	
	public void setIdNo(java.lang.String idNo) {
		this.idNo=idNo;
	}
	public java.lang.String getPhone() {
		return phone;
	}
	
	public void setPhone(java.lang.String phone) {
		this.phone=phone;
	}
	public java.lang.String getLoanAmt() {
		return loanAmt;
	}
	
	public void setLoanAmt(java.lang.String loanAmt) {
		this.loanAmt=loanAmt;
	}
	public java.lang.String getApplyStatus() {
		return applyStatus;
	}
	
	public void setApplyStatus(java.lang.String applyStatus) {
		this.applyStatus=applyStatus;
	}
	public java.lang.String getRecommenderId() {
		return recommenderId;
	}
	
	public void setRecommenderId(java.lang.String recommenderId) {
		this.recommenderId=recommenderId;
	}
	public java.lang.String getErrorMessage() {
		return errorMessage;
	}
	
	public void setErrorMessage(java.lang.String errorMessage) {
		this.errorMessage=errorMessage;
	}
	public java.lang.String getRemark() {
		return remark;
	}
	
	public void setRemark(java.lang.String remark) {
		this.remark=remark;
	}
}
