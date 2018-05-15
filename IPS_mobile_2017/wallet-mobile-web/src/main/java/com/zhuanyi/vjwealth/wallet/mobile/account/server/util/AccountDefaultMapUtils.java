package com.zhuanyi.vjwealth.wallet.mobile.account.server.util;

import java.util.HashMap;
import java.util.Map;

public class AccountDefaultMapUtils {
	
	//e账户昨日收益
	public static Map<String,String> getEAccountYestodayReceiveMap(){
		Map<String,String> map=new HashMap<String,String>();
		map.put("yesterdayReceive", "0.00");
		return map;
	}
	
	//v账户昨日收益
	public static Map<String,String> getVAccountYestodayReceiveMap(){
		Map<String,String> map=new HashMap<String,String>();
		map.put("yesterdayReceive", "0.00");
		return map;
	}
	
	//用户是否购买过v+理财
	public static Map<String,String> getUserIsPurchaseVMap(){
		Map<String,String> map=new HashMap<String,String>();
		map.put("isPurchaseV1Flag", "no");
		return map;
	}
	
}
