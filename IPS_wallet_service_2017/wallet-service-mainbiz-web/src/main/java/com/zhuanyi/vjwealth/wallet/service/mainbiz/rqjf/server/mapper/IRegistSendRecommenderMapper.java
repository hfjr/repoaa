package com.zhuanyi.vjwealth.wallet.service.mainbiz.rqjf.server.mapper;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;

@Mapper
public interface IRegistSendRecommenderMapper {

	public void insertRqbPackageInfo(@Param("userId")String userId,@Param("packageAmount")String packageAmount,@Param("startAmount")String startAmount,@Param("startTime")String startTime,@Param("endTime")String endTime);
	
}
