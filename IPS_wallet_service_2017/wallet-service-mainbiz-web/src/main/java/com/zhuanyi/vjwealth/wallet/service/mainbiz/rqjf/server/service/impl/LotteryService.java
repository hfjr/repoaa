package com.zhuanyi.vjwealth.wallet.service.mainbiz.rqjf.server.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhuanyi.vjwealth.wallet.service.mainbiz.rqjf.server.mapper.ILotteryMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.rqjf.server.service.ILotteryService;

@Service
public class LotteryService implements ILotteryService {
	
	@Autowired
	private ILotteryMapper lotteryMapper;
	
	
	@Override
	public void regristSendLottery(String userId) {
		if(lotteryMapper.countLotteryRecord(userId)>0){
			return ;
		}
		lotteryMapper.saveLotteryRecord(userId);
	}

	@Override
	public void buyFinancesLottery(String userId, String tradePrice) {
		if(new BigDecimal(tradePrice).compareTo(new BigDecimal(1000))>=0){
			lotteryMapper.addLotteryTimes(userId);
		}
	}

}
