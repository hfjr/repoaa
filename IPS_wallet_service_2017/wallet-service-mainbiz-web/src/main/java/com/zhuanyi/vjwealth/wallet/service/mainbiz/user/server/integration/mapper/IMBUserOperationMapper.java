package com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;

/**
 * @author xuejie
 *
 */
@Mapper
public interface IMBUserOperationMapper {

	String queryPasswordByUserId(@Param("userId") String userId);

	void updateModifyPassword(@Param("userId")String userId, @Param("encodePassword")String encodePassword);
	
	int countNextWorkDayExit(@Param("targetDate") String targetDate);
	
	String queryNextWorkDay(@Param("targetDate") String targetDate);
	
	
	
	
}
