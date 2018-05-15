package com.zhuanyi.vjwealth.wallet.service.mainbiz.rqjf.server.mapper;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;

@Mapper
public interface IAccumulateMapper {
	
	// 保存累积记录
	public void saveAccumulateDetail(@Param("tradeNo")String tradeNo,@Param("tradePrice")String tradePrice,@Param("tradeType")String tradeType);
	
	// 更新 理财 年化累积
	public void updateFinancesAccumulate(@Param("tradePrice")String tradePrice);
	
	// 更新 借款 年化 累积
	public void updateLoanAccumulate(@Param("tradePrice")String tradePrice);
	
	//累积成交金额
	public void updateAccumulateYearAccumulate(@Param("tradePrice")String tradePrice);
}
