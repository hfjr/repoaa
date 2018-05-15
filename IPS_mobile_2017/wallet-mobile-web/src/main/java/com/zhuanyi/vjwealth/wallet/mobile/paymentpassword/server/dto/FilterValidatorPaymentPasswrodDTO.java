package com.zhuanyi.vjwealth.wallet.mobile.paymentpassword.server.dto;

import java.io.Serializable;

/**
 * 支付密码鉴权表单的校验结果
 * @author wangzf 2016/04/18
 * @since 3.1.2
 */
public class FilterValidatorPaymentPasswrodDTO implements Serializable {
	
    private static final long serialVersionUID = 1L;
	
	private String state;  //'close','normal','lock'
	private Integer validationLeftTimes; //校验支付密码剩余可尝试次数;
	private Integer authorizationLeftTimes;//支付密码鉴权剩余可尝试次数
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Integer getValidationLeftTimes() {
		return validationLeftTimes;
	}
	public void setValidationLeftTimes(Integer validationLeftTimes) {
		this.validationLeftTimes = validationLeftTimes;
	}
	public Integer getAuthorizationLeftTimes() {
		return authorizationLeftTimes;
	}
	public void setAuthorizationLeftTimes(Integer authorizationLeftTimes) {
		this.authorizationLeftTimes = authorizationLeftTimes;
	}
	
	
	
	
}
