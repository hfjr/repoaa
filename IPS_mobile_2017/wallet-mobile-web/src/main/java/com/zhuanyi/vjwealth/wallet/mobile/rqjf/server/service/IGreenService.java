package com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.service;

import java.util.Map;

public interface IGreenService {

	
	public Map<String,Object> redPackageInit(String userId);
	
	public Map<String,Object> redPackageDoApply(String userId);
	
	public Map<String,Object> couponDoApply(String userId);
	
	public Map<String, Object> lotteryInit(String userId); 
	
	public Map<String,Object> lotteryDoApply(String userId);
	
	
	
}
