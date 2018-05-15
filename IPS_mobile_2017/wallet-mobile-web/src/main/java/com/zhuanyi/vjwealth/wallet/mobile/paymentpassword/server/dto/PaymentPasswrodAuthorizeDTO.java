package com.zhuanyi.vjwealth.wallet.mobile.paymentpassword.server.dto;

import java.io.Serializable;

/**
 * 支付密码鉴权表单DTO
 * Created by yi on 16/4/13.
 * modified by wangzhangfei on 2016/04/18
 */
public class PaymentPasswrodAuthorizeDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//用户ID
	private String userId; 
	//安全卡卡号
	private String paymentCardNo;
	//身份证
	private String identityNo;
	//验证码
	private String code;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPaymentCardNo() {
		return paymentCardNo;
	}
	public void setPaymentCardNo(String paymentCardNo) {
		this.paymentCardNo = paymentCardNo;
	}
	public String getIdentityNo() {
		return identityNo;
	}
	public void setIdentityNo(String identityNo) {
		this.identityNo = identityNo;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
	
}
