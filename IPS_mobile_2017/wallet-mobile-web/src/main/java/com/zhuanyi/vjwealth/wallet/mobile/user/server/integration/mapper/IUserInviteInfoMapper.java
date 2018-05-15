package com.zhuanyi.vjwealth.wallet.mobile.user.server.integration.mapper;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;

@Mapper
public interface IUserInviteInfoMapper {
	String queryRecommendUserIdByUserId(@Param("userId") String userId);
	
	
	// count >0 表示推荐人是自己
	Integer querytRecommenderIsSelfByPhone(@Param("phone") String phone,@Param("userId") String userId);
	
	// count >0 表示推荐人存在
	Integer queryRecommenderExitByPhone(@Param("phone") String phone);
	
	// 推荐人id
	String queryRecommendUserIdByPhone(@Param("phone") String phone);
	
	
}
