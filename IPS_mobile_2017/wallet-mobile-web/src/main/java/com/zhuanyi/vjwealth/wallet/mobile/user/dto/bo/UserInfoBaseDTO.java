package com.zhuanyi.vjwealth.wallet.mobile.user.dto.bo;

import com.fab.core.entity.dto.BaseDTO;

public class UserInfoBaseDTO extends BaseDTO {
	private static final long serialVersionUID = 1L;
	
	// 手机号
	 private java.lang.String phone; 
	// 密码
	 private java.lang.String password; 
	// 真实姓名
	 private java.lang.String realName; 
	// 证件类型
	 private java.lang.String indentityType; 
	// 证件号
	 private java.lang.String indentityNo; 
	// 性别：M 男； F 女
	 private java.lang.String sex; 
	// 用户状态(S^00:正常,10:冻结$)
	 private java.lang.String userStatus; 
	// 电子邮件
	 private java.lang.String email; 
	// 是否签约(S^0:非签约,1:签约,2:转签约$)
	 private java.lang.String isSign; 
	// 融桥宝卡号
	 private java.lang.String walletBankCardNo; 
	// 渠道号(废除字段)
	 private java.lang.String channelNo; 
	// 企业编号
	 private java.lang.String enterpriseNo; 
	// 登录uuid
	 private java.lang.String loginUuid; 
	// 上次登录时间
	private String lastLoginTime;
	
	public java.lang.String getPhone() {
		return phone;
	}
	
	public void setPhone(java.lang.String phone) {
		this.phone=phone;
	}
	public java.lang.String getPassword() {
		return password;
	}
	
	public void setPassword(java.lang.String password) {
		this.password=password;
	}
	public java.lang.String getRealName() {
		return realName;
	}
	
	public void setRealName(java.lang.String realName) {
		this.realName=realName;
	}
	public java.lang.String getIndentityType() {
		return indentityType;
	}
	
	public void setIndentityType(java.lang.String indentityType) {
		this.indentityType=indentityType;
	}
	public java.lang.String getIndentityNo() {
		return indentityNo;
	}
	
	public void setIndentityNo(java.lang.String indentityNo) {
		this.indentityNo=indentityNo;
	}
	public java.lang.String getSex() {
		return sex;
	}
	
	public void setSex(java.lang.String sex) {
		this.sex=sex;
	}
	public java.lang.String getUserStatus() {
		return userStatus;
	}
	
	public void setUserStatus(java.lang.String userStatus) {
		this.userStatus=userStatus;
	}
	public java.lang.String getEmail() {
		return email;
	}
	
	public void setEmail(java.lang.String email) {
		this.email=email;
	}
	public java.lang.String getIsSign() {
		return isSign;
	}
	
	public void setIsSign(java.lang.String isSign) {
		this.isSign=isSign;
	}
	public java.lang.String getWalletBankCardNo() {
		return walletBankCardNo;
	}
	
	public void setWalletBankCardNo(java.lang.String walletBankCardNo) {
		this.walletBankCardNo=walletBankCardNo;
	}
	public java.lang.String getChannelNo() {
		return channelNo;
	}
	
	public void setChannelNo(java.lang.String channelNo) {
		this.channelNo=channelNo;
	}
	public java.lang.String getEnterpriseNo() {
		return enterpriseNo;
	}
	
	public void setEnterpriseNo(java.lang.String enterpriseNo) {
		this.enterpriseNo=enterpriseNo;
	}
	public java.lang.String getLoginUuid() {
		return loginUuid;
	}
	
	public void setLoginUuid(java.lang.String loginUuid) {
		this.loginUuid=loginUuid;
	}
	public String getLastLoginTime() {
		return lastLoginTime;
	}
	
	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime=lastLoginTime;
	}
}
