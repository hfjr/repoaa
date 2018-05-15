package com.zhuanyi.vjwealth.wallet.mobile.order.server.mapper;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;
import com.zhuanyi.vjwealth.wallet.mobile.order.dto.WjOrderInfoDTO;

@Mapper
public interface IOrderInfoQueryMapper {
	
	/**
	 * 查询订单
	 * @param orderNo
	 * @return
	 */
	WjOrderInfoDTO getOrderInfoByOrderNo(@Param("orderNo")String orderNo);
	
	
}
