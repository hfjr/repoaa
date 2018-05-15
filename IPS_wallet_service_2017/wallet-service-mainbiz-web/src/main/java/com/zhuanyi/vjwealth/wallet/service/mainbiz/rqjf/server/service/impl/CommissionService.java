package com.zhuanyi.vjwealth.wallet.service.mainbiz.rqjf.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhuanyi.vjwealth.wallet.service.mainbiz.rqjf.server.mapper.ICommissionMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.rqjf.server.service.ICommissionService;

@Service
public class CommissionService  implements ICommissionService{
	
	@Autowired
	private ICommissionMapper commissionMapper;
	
	@Override
	public void saveCommissionApply(String userId,String tradeNo,String tradeType,String tradePrice,String recommendUserId) {
		// 记录交易金额
		commissionMapper.saveCommissionApply(userId, tradeNo, tradeType, tradePrice, recommendUserId);
	}

}
