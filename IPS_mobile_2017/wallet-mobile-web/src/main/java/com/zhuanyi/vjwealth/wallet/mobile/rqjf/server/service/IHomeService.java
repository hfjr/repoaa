package com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.service;

import java.util.Map;

public interface IHomeService {

	
	public Map<String,Object> queryHomeInit(String channelType);
	
	
	public Map<String, Object> queryAccumulateInit();
	
	
	public Map<String, Object> queryLatestDynamicList(String page);
	
	public Map<String, Object> queryReportList(String page);
	
	public String queryReportDetail(String reportId);
	
	public Map<String, Object> queryHistory();
	
	Object getPageData(String userId);

	Map<String, Object> getHomeStatistics();
	
}
