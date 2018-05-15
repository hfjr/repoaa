package com.zhuanyi.vjwealth.wallet.mobile.common.server.service;



public interface ISendExceptionEmailService {
	
	
	
	/**
	 * 账户发送异常邮件
	 * @param userId	  --用户名
	 * @param errorMessage --错误明细
	 * @param cls		  --this.getClass
	 * @param method	  --方法名
	 */
	public void sendAccountExceptionEmail(String userId,String errorMessage,Class<?> cls,String method);
	
	/**
	 * 用户发送异常邮件
	 * @param userId
	 * @param errorMessage
	 * @param cls
	 * @param method
	 */
	public void sendUserExceptionEmail(String userId, String errorMessage,Class<?> cls, String method);
	
}
