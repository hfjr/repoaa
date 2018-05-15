package com.rqb.ips.depository.platform.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;

@Mapper
public interface IpsTransferMapper {
	
	Map<String,String> transdetail(@Param("orderNo") String orderNo);
	
	//获取平台手续费
	List<Map<String,String>> queryMerType(@Param("paramGroup")String paramGroup);

	void updateRedPacket(@Param("orderNo")String orderNo);
	
	void updateCoupons(@Param("couponsId")String couponsId);
	
	Map<String,Object> queryPacket(@Param("orderNo") String orderNo);
}
