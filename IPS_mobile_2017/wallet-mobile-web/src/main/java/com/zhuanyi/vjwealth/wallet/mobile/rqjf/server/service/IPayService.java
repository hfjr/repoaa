package com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.service;

public interface IPayService {
	
	//首次绑卡支付调用此校验
	public void preCheckDoPayCondition(String userId,String amt,String bankCard,String bankCode,String asidePhone,String name,String idNo);
	//pc绑卡校验
	public void bindPCCard(String userId,String bankCode,String bankCard,String asidePhone,String name,String idNo);
	
	//充值跳转
	public String goRechargegetWay(String userId,String amt,String bankCard,String bankCode,String asidePhone,String name,String idNo);
	
	//已绑卡充值跳转
	public String goRechargegetWay(String cardId,String userId,String amt);
	
	
}
