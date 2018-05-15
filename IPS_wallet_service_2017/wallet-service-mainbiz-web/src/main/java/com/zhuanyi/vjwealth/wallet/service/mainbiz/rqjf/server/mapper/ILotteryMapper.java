package com.zhuanyi.vjwealth.wallet.service.mainbiz.rqjf.server.mapper;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;

@Mapper
public interface ILotteryMapper {

	Integer countLotteryRecord(@Param("userId")String userId);
	
	void saveLotteryRecord(@Param("userId")String userId);
	
	void addLotteryTimes(@Param("userId")String userId);
	
}
