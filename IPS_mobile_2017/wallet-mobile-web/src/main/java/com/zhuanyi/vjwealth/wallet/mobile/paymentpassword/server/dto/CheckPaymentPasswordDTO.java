package com.zhuanyi.vjwealth.wallet.mobile.paymentpassword.server.dto;

import java.io.Serializable;

/**
 * 校验支付密码结果
 * @author wangzf 2016/04/18
 * @since 3.1.2
 */
public class CheckPaymentPasswordDTO implements Serializable {
	
    private static final long serialVersionUID = 1L;
	
	private String result;  //true(成功),false(失败)
	private Integer failures; //失败次数;
	private Integer lockFlag;//锁定账户标识（剩余剩余可尝试次数：0表示将锁定，大于0表示不锁定）
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public Integer getFailures() {
		return failures;
	}
	public void setFailures(Integer failures) {
		this.failures = failures;
	}
	public Integer getLockFlag() {
		return lockFlag;
	}
	public void setLockFlag(Integer lockFlag) {
		this.lockFlag = lockFlag;
	}
	
}
