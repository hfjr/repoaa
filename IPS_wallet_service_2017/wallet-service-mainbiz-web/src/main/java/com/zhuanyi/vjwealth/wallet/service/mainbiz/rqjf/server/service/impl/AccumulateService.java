package com.zhuanyi.vjwealth.wallet.service.mainbiz.rqjf.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhuanyi.vjwealth.wallet.service.mainbiz.rqjf.server.mapper.IAccumulateMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.rqjf.server.service.IAccumulateService;

@Service
public class AccumulateService implements IAccumulateService{

	@Autowired
	private IAccumulateMapper accumulateMapper;
	
	@Override
	public void accumulateService(String tradeNo, String amt, String tradeType) {
		
		accumulateMapper.saveAccumulateDetail(tradeNo, amt, tradeType);
		if("loan".equals(tradeType)){
			//loan
			accumulateMapper.updateLoanAccumulate(amt);
		}
		//finances
		if("finances".equals(tradeType)){
			accumulateMapper.updateFinancesAccumulate(amt);
		}
		
		accumulateMapper.updateAccumulateYearAccumulate(amt);
		
		
	}

}
