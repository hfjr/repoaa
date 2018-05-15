package com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.service;

public interface IPCPayService {

	
	
	
	//跳转网关
	public void saveRechargeOrder(String tradeNo,String userId,String amt);
	
	
	//前端调用
	
	//后台调用
	public void successRechargeOrder(String tradeNo);	
	
	
}
