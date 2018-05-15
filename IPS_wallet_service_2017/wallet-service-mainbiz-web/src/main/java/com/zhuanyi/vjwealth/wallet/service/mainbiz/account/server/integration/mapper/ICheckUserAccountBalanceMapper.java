package com.zhuanyi.vjwealth.wallet.service.mainbiz.account.server.integration.mapper;

import java.math.BigDecimal;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;

@Mapper
public interface ICheckUserAccountBalanceMapper {

	BigDecimal calculateUserAccountbalanceable(@Param("userId")String userId);
	
	String queryCalculateBalanceSwitch();
	
	void updateUserStautsToException(@Param("userId")String userId);
}
