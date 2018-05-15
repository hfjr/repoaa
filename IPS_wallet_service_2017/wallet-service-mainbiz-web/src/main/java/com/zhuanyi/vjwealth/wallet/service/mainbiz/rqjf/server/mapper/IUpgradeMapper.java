package com.zhuanyi.vjwealth.wallet.service.mainbiz.rqjf.server.mapper;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;

@Mapper
public interface IUpgradeMapper {

	//统计佣金否记录
	public Integer countCommissionRecord(@Param("userId")String userId);
	
//	//统计 理财 累积金额/借款次数
//	public Double sumFinancesCommissionAmt(@Param("userId")String userId);
//	
//	//统计借款次数
//	public Integer countLoanCommissionRecord(@Param("userId")String userId);
//	
//	//查询 等级id
//	public Map<String,Object> queryLevelId(@Param("tradeType")String tradeType,@Param("sumAmt")Double sumAmt);
	
	//查询 等级id
	public String queryCKLevelId(@Param("userId")String userId);
	
	// 更新等级id
	public void updateUserLevelId(@Param("userId")String userId,@Param("levelId")String levelId);
	
	
}
