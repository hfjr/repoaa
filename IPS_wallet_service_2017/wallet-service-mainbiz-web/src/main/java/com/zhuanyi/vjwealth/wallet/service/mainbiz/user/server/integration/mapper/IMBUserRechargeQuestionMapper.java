package com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;

/**
 * Created by ce on 16/5/4.
 */
@Mapper
public interface IMBUserRechargeQuestionMapper {
	
	String queryErrorMessageByCodeAndPlateformId(@Param("code")String code,@Param("plateformId")String plateformId);
		
	
}
