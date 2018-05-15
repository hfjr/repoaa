package com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;

@Mapper
public interface ICouponMapper {
	
	//  1. 校验红包
	public Map<String,Object> checkRedPackageValid(@Param("userId")String userId,@Param("investmentAmt")String investmentAmt,@Param("packageId")String packageId,@Param("productId")String productId);
	
	//	2. 校验卡券
	public Map<String,Object> checkCouponsValid(@Param("userId")String userId,@Param("couponsId")String couponsId,@Param("productId")String productId);
	
	//  3. 使用红包
	public void updatePackageIsUse(@Param("packageId")String packageId,@Param("relOrderNo")String relOrderNo);
	
	//	4. 使用卡券
	public void updateCouponsIsUse(@Param("couponsId")String couponsId,@Param("relOrderNo")String relOrderNo);
	

}
