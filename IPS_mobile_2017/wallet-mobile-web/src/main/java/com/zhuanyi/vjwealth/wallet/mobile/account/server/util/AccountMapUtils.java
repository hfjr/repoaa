package com.zhuanyi.vjwealth.wallet.mobile.account.server.util;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class AccountMapUtils {
	

	public static Map<String,String> changeMapDataToMapString(Map<String,String> map){
		if(map==null){
			return map;
		}
		for(Map.Entry<String, String> temp:map.entrySet()){
			String key=temp.getKey();
			Object obj=temp.getValue();
			obj=obj==null?"":obj;
			if(obj instanceof Double){
				obj=new BigDecimal(String.valueOf(obj)).toPlainString();
			}
			map.put(key, String.valueOf(obj));
		}
		return map;
	}
	
	public static List<Map<String,String>> changeListMapDataToLisMapString(List<Map<String,String>> list){
		if(list==null ||list.size()<1)
			return list;
		for(Map<String,String> map:list){
			changeMapDataToMapString(map);
		}
		return list;
	}
	
}
