package com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;

@Mapper
public interface IProductQueryMapper {
	
	// 查询产品起息类型
	public String queryProductInterestDateStr(@Param("productId")String productId);
	

}
