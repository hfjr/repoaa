package com.zhuanyi.vjwealth.wallet.mobile.account.server.mapper;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;
import com.zhuanyi.vjwealth.wallet.mobile.account.server.dto.V1ApplyDTO;

@Mapper
public interface IV1ApplyMapper {
	
	// v1理财减少真实份额用到的参数 真实份额和阀值
	V1ApplyDTO queryV1RealApplyParamInfo();
	
	//更新真实份额为0
	void updateRealRemainAmountToZero();
	
	//更新mock份额为0
	void updateMockRemainAmountToZero();
	
	//减少真实份额
	void subtractionRealRemainAmount(@Param("realApplyAmount")Double realApplyAmount);
	
	
}
