package com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.service;

import java.util.Map;

public interface IFinanceService {

	
	public Map<String,Object> queryPackageList(String userId,String page,String packageType);
	
	public Map<String,Object> queryCouponsList(String userId,String page,String couponType);
	
	
	
	
}
