package com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;

@Mapper
public interface IGreenMapper {
	
	public Integer countPackageRecord(String userId);
	
	public Integer countCouponsRecord(String userId);
	
	
	public void insertRqbPackageInfo(@Param("userId")String userId,@Param("packageAmount")String packageAmount,@Param("startAmount")String startAmount,@Param("startTime")String startTime,@Param("endTime")String endTime,@Param("channelFrom")String channelFrom);
	
	public void insertCouponsInfo(@Param("userId")String userId,@Param("addProfit")String addProfit,@Param("startPeriodStart")String startPeriodStart,@Param("startTime")String startTime,@Param("endTime")String endTime,@Param("channelFrom")String channelFrom);

	
	public Integer countLuckTimesRecord(String userId);
	
	public List<Map<String, Integer>> queryLotteryList();
	
	public void reduceLuckTimesByUserId(String userId);
	
	public void insertLotteryRecord(@Param("userId")String userId,@Param("lotteryNumber")String lotteryNumber);
	
}
