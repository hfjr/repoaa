package com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.integration.mapper;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.bo.RechargeCallbackBO;

@Mapper
public interface IRechargeCallBackMapper {

	
	RechargeCallbackBO queryRechargeOrderInfo(@Param("orderNo")String orderNo);
	
	RechargeCallbackBO queryPCRechargeOrderInfo(@Param("orderNo")String orderNo);
	
	int updateRrechargeOrderSuccess(@Param("orderNo")String orderNo);
	
	int updatePCRrechargeOrderSuccess(@Param("orderNo")String orderNo);
	
	int updateRrechargeOrderFail(@Param("orderNo")String orderNo);
	
	int updatePCRrechargeOrderFail(@Param("orderNo")String orderNo);
	
	
	/**
	 * 根据充值订单增加主账户余额
	 * @param orderNo
	 * @return
	 */
	int addMaAccountAmountByRecharge(@Param("orderNo")String orderNo);
	
	int addMaAccountAmountByPCRecharge(@Param("orderNo")String orderNo);
	
	
	
	public String queryTradeNoByOrderNo(@Param("orderNo")String orderNo);
	
	public int checkBankCardExit(@Param("tradeNo")String tradeNo);
	
	public int countBankCard(@Param("userId")String userId);
	
	public void insertCardSecurity(@Param("tradeNo")String tradeNo);
	
	public void insertCardRecharge(@Param("tradeNo")String tradeNo);
	
	public void insertCardRechargeForNewCard(@Param("tradeNo")String tradeNo);
	
	public void completeUserInfo(@Param("tradeNo")String tradeNo);
	
	
}
