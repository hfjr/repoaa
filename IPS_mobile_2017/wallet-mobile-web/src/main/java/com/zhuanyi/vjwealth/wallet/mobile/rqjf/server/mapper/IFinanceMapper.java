package com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;

@Mapper
public interface IFinanceMapper {
	
	
	public List<Map<String, String>> queryPackageList(@Param("page")Integer page,@Param("userId")String userId,@Param("packageType")String packageType);
	
	public List<Map<String, String>> queryCouponsList(@Param("page")Integer page,@Param("userId")String userId,@Param("couponType")String couponType);
	
	
}
