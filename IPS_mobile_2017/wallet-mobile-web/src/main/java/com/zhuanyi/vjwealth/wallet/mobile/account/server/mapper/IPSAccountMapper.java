package com.zhuanyi.vjwealth.wallet.mobile.account.server.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;

@Mapper
public interface IPSAccountMapper {
	
	//获取IPS账户信息  
	Map<String,String> queryIPSAccountInfo(@Param("userId")String userId,@Param("tradeType")String tradeType,@Param("tradeAccountStatus")String tradeAccountStatus);

	//获取平台手续费
	List<Map<String,String>> queryMerType(@Param("paramGroup")String paramGroup);

	//储存记录
	 void saveRechargeHistoryIPS(@Param("cardId") String cardId,@Param("cardNo") String cardNo,@Param("tradeNo") String tradeNo, @Param("amount") String amount,@Param("bankCode") String bankCode, @Param("asidePhone") String asidePhone, @Param("result")String result, @Param("message")String message,@Param("requestJson")String requestJson,@Param("responseJson") String responseJson, @Param("userId")String userId,@Param("status")String status,@Param("source")String source,@Param("operationType")String operationType);

	 void  updateHistoryIPS(@Param("sendJson") String sendJson,@Param("status") String status,@Param("tradeNo") String tradeNo,@Param("orderNo") String orderNo,@Param("message") String message);

	 //存订单表
	void saveRechargeOrder(@Param("userId") String userId, @Param("orderNo") String orderNo, @Param("totalPrice") String totalPrice,@Param("ipsAcctNo")  String ipsAcctNo,@Param("cardId")  String cardId);

	 //根据充值订单增加主账户余额
	 int addMaAccountAmountByRecharge(@Param("orderNo")String orderNo);

	 int updateRrechargeOrderFail(@Param("tradeNo")String tradeNo,@Param("orderNo")String orderNo,@Param("userId")String userId);
	 
		//更新订单状态
	 void updatePCRechargeOrderSuccess(@Param("tradeNo")String tradeNo,@Param("orderNo")String orderNo,@Param("userId")String userId);

	 void updatePaymentTradeStatusFail(@Param("tradeNo")String tradeNo,@Param("orderNo")String orderNo);
	 void updatePaymentTradeStatusSuccess(@Param("tradeNo")String tradeNo,@Param("orderNo")String orderNo);
	 
	List<Map<String,String>> queryBanks();

}
