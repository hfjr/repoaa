package com.zhuanyi.vjwealth.wallet.mobile.credit.server.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhuanyi.vjwealth.wallet.mobile.credit.server.ICreditQueryService;
import com.zhuanyi.vjwealth.wallet.service.creditscore.credit.server.service.ICreditScoreService;

@Service
public class CreditQueryService implements ICreditQueryService{
	//@Remote
	@Autowired
	ICreditScoreService creditScoreService;
	
	
	public Map<String, Object> queryCreditScoreForAppPolygonByUserid(String userId){
		
		return creditScoreService.queryCreditScoreForAppPolygonByUserid(userId);
	}

	public Map<String, Object> queryCreditScoreForAppStopwatchByUserid(String userId){
		
		return creditScoreService.queryCreditScoreForAppStopwatchByUserid(userId);
	}
	
	
	

}
