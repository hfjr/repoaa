package com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;

@Mapper
public interface IPayMapper {

	public void saveRechargeHistory(@Param("cardId") String cardId, @Param("cardNo") String cardNo, @Param("realName") String realName, @Param("certNo") String certNo, @Param("tradeNo") String tradeNo, @Param("amount") String amount,@Param("bankCode") String bankCode, @Param("asidePhone") String asidePhone, @Param("result")String result, @Param("message")String message,@Param("requestJson")String requestJson,@Param("responseJson") String responseJson, @Param("userId")String userId);

	public void saveTradeRecord( @Param("tradeNo") String tradeNo,  @Param("totalPrice") String totalPrice, @Param("orderNo")  String orderNo, @Param("bankCardNo")  String bankCardNo);

	public void saveRechargeOrder(@Param("userId") String userId, @Param("orderNo") String orderNo, @Param("totalPrice") String totalPrice,@Param("cardId")  String cardId);
	
	public List<String> queryUserBankCardList(@Param("userId")String userId);
	
	public String queryUserBankCardId(@Param("userId")String userId,@Param("bankCardNo")  String bankCardNo);
	
	public Map<String,String> queryUserPayInfo(@Param("userId")String userId,@Param("cardId")  String cardId);
	
	public void saveUserCards(@Param("cardOwer") String cardOwer, @Param("userId") String userId, @Param("bankCardNo") String bankCardNo,@Param("asideBankPhone")  String asideBankPhone,@Param("bankCode")  String bankCode,@Param("cardType")  String cardType);
	
	public void completeUserInfo(@Param("realName") String realName, @Param("indentityNo") String indentityNo, @Param("userId") String userId);
}
