package com.zhuanyi.vjwealth.wallet.mobile.order.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhuanyi.vjwealth.wallet.mobile.order.dto.WjOrderInfoDTO;
import com.zhuanyi.vjwealth.wallet.mobile.order.server.mapper.IOrderInfoQueryMapper;
import com.zhuanyi.vjwealth.wallet.mobile.order.server.service.IWjOrderInfoService;

@Service
public class WjOrderInfoService implements IWjOrderInfoService {
	@Autowired
	private IOrderInfoQueryMapper orderInfoQueryMapper;

	@Override
	public WjOrderInfoDTO getOrderInfoByOrderNo(String orderNo) {
		return orderInfoQueryMapper.getOrderInfoByOrderNo(orderNo);
	}

}
