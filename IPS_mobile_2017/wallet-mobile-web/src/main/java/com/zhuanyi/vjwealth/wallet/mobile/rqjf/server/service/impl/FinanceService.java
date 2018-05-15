package com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.mapper.IFinanceMapper;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.service.IFinanceService;
@Service
public class FinanceService implements IFinanceService {

	@Autowired
	private IFinanceMapper financeMapper;
	
	@Override
	public Map<String, Object> queryCouponsList(String userId,String page,String packageType) {
		Map<String,Object> returnMap=new HashMap<String,Object>();
		//1.	返回列表
		List<Map<String,String>> auctionList =financeMapper.queryCouponsList((Integer.parseInt(page) - 1) * 10,userId,packageType);
		//	2. 包装分页信息
		returnMap.put("couponsList", auctionList);
		returnMap.put("isMore", false);
		if (auctionList != null && auctionList.size() >= 10) {
			returnMap.put("isMore", true);
		}
		return returnMap;
	}
	
	@Override
	public Map<String, Object> queryPackageList(String userId,String page,String couponType) {
		Map<String,Object> returnMap=new HashMap<String,Object>();
		//1.	返回列表
		List<Map<String,String>> auctionList =financeMapper.queryPackageList((Integer.parseInt(page) - 1) * 10,userId,couponType);
		//	2. 包装分页信息
		returnMap.put("packageList", auctionList);
		returnMap.put("isMore", false);
		if (auctionList != null && auctionList.size() >= 10) {
			returnMap.put("isMore", true);
		}
		return returnMap;
	}

}
