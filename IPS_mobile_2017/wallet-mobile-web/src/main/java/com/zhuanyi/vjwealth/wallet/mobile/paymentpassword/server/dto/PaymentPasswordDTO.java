package com.zhuanyi.vjwealth.wallet.mobile.paymentpassword.server.dto;

import java.io.Serializable;

/**
 * 支付密码模块返回结果:结果code及对应的message
 * @author wangzf 2016/04/18
 * @since 3.1.2
 */
public class PaymentPasswordDTO implements Serializable {
	
    private static final long serialVersionUID = 1L;
	
	private String code;  //返回码：成功、失败
	private String message;//结果信息
	
	
	public PaymentPasswordDTO() {
		super();
	}
	
	public PaymentPasswordDTO(String code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
