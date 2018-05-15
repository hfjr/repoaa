package com.zhuanyi.vjwealth.wallet.mobile.credit.server;

import java.util.Map;

public interface ICreditQueryService {
	
	
	//五边形
	public Map<String, Object> queryCreditScoreForAppPolygonByUserid(String userId);
	
	//码表
	public Map<String, Object> queryCreditScoreForAppStopwatchByUserid(String userId);
	
	
}
