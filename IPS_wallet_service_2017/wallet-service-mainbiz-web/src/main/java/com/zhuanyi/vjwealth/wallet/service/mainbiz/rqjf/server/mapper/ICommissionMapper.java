package com.zhuanyi.vjwealth.wallet.service.mainbiz.rqjf.server.mapper;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;

@Mapper
public interface ICommissionMapper {

	
	public void saveCommissionApply(@Param("userId")String userId,@Param("relTradeNo")String tradeNo,@Param("tradeType")String tradeType,@Param("tradePrice")String tradePrice,@Param("recommenderUserId")String recommenderUserId);
	

}
