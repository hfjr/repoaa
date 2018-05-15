package com.zhuanyi.vjwealth.wallet.mobile.product.server.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;


@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
public class UserCertificationDTO {
	
	private String userId;//动态title
	private String realName;//时间
	private String identityType;//动态详细描述
	private String identityNo;
	private String bankCardNo;
	private String certificationContent;
	private String certificationContentTip;
	private String certificationDescription;
	private String isUploadIdentityCard;//结果码200301 可认证 ；200302 认证失败 ；200303 实名认证审核中 ;200304 已完成实名认证 
	
	public String getBankCardNo() {
		return bankCardNo;
	}
	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getIdentityType() {
		return identityType;
	}
	public void setIdentityType(String identityType) {
		this.identityType = identityType;
	}
	public String getIdentityNo() {
		return identityNo;
	}
	public void setIdentityNo(String identityNo) {
		this.identityNo = identityNo;
	}
	public String getCertificationContent() {
		return certificationContent;
	}
	public void setCertificationContent(String certificationContent) {
		this.certificationContent = certificationContent;
	}
	public String getCertificationContentTip() {
		return certificationContentTip;
	}
	public void setCertificationContentTip(String certificationContentTip) {
		this.certificationContentTip = certificationContentTip;
	}
	public String getCertificationDescription() {
		return certificationDescription;
	}
	public void setCertificationDescription(String certificationDescription) {
		this.certificationDescription = certificationDescription;
	}
	public String getIsUploadIdentityCard() {
		return isUploadIdentityCard;
	}
	public void setIsUploadIdentityCard(String isUploadIdentityCard) {
		this.isUploadIdentityCard = isUploadIdentityCard;
	}
	
	
	

}
