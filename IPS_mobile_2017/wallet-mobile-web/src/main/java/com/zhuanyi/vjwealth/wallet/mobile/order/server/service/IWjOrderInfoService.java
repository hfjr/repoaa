package com.zhuanyi.vjwealth.wallet.mobile.order.server.service;

import com.zhuanyi.vjwealth.wallet.mobile.order.dto.WjOrderInfoDTO;

public interface IWjOrderInfoService {
	
	WjOrderInfoDTO getOrderInfoByOrderNo(String orderNo);
}
