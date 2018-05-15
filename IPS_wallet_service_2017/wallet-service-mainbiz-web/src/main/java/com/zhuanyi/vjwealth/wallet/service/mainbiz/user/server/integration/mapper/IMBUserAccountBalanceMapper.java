package com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper;

import java.math.BigDecimal;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;

/**
 * @author xuewentao
 *
 */
@Mapper
public interface IMBUserAccountBalanceMapper {

	BigDecimal queryUserAccountBalance(@Param("userId")String userId);
	
	void updateUserAccountLock(@Param("userId")String userId);
	
	Integer countUserExits(@Param("userId")String userId);
	
	Integer countUserTransLock(@Param("userId")String userId);

}
