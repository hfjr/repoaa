package com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.dto;


public class CkbApplyDTO  implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	// 交易号
	 private java.lang.String tradeNo; 
	// 真实姓名
	 private java.lang.String realName; 
	// 申请时间
	private String applyTime;
	// 用户Id
	 private java.lang.String userId; 
	// 证件号
	 private java.lang.String idNo; 
	// 手机号
	 private java.lang.String phone; 
	// 申请状态
	 private java.lang.String applyStatus; 
	// 备注
	 private java.lang.String remark; 
	// 失败原因
	 private java.lang.String errorMessage; 
	
	public java.lang.String getTradeNo() {
		return tradeNo;
	}
	
	public void setTradeNo(java.lang.String tradeNo) {
		this.tradeNo=tradeNo;
	}
	public java.lang.String getRealName() {
		return realName;
	}
	
	public void setRealName(java.lang.String realName) {
		this.realName=realName;
	}
	public String getApplyTime() {
		return applyTime;
	}
	
	public void setApplyTime(String applyTime) {
		this.applyTime=applyTime;
	}
	public java.lang.String getUserId() {
		return userId;
	}
	
	public void setUserId(java.lang.String userId) {
		this.userId=userId;
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
	public java.lang.String getApplyStatus() {
		return applyStatus;
	}
	
	public void setApplyStatus(java.lang.String applyStatus) {
		this.applyStatus=applyStatus;
	}
	public java.lang.String getRemark() {
		return remark;
	}
	
	public void setRemark(java.lang.String remark) {
		this.remark=remark;
	}
	public java.lang.String getErrorMessage() {
		return errorMessage;
	}
	
	public void setErrorMessage(java.lang.String errorMessage) {
		this.errorMessage=errorMessage;
	}
}
