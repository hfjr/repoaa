package com.zhuanyi.vjwealth.wallet.mobile.rqjf.common.utils;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class MapperUtils {

	// 以","切割,拼接新的字符串
	public static String splitQueryCondition(String queryCondition) {
		if (StringUtils.isBlank(queryCondition)) {
			return "";
		}
		String[] args = queryCondition.split(",");
		StringBuffer newQueryCondition = new StringBuffer();
		for (String temp : args) {
			newQueryCondition.append("'" + temp + "',");
		}
		return newQueryCondition.substring(0, newQueryCondition.length() - 1);
	}
	
	
	public static JSONArray parseJSONArrayByStr(Object obj){
		if(obj instanceof String){
			return JSONArray.parseArray((String)obj);
		}else{
			return JSONArray.parseArray(JSON.toJSONString(obj));
		}
	}
	
	
	public static JSONObject parseJSONByStr(Object obj){
		if(obj instanceof String){
			return JSON.parseObject((String)obj);
		}else{
			return JSON.parseObject(JSON.toJSONString(obj));
		}
	}
	
	
	public static void main(String[] args) {
		System.out.println(parseJSONArrayByStr("[\n" +
				"					{\n" +
				"						\"label\":\"晋级标准\",\n" +
				"						\"labelValue\":\"贷款>5万元\"\n" +
				"					},\n" +
				"					{\n" +
				"						\"label\":\"基本标准\",\n" +
				"						\"labelValue\":\"销售工具\"\n" +
				"					},\n" +
				"					{\n" +
				"						\"label\":\"贷款申请服务费\",\n" +
				"						\"labelValue\":\"0元\"\n" +
				"					},\n" +
				"					{\n" +
				"						\"label\":\"理财佣金\",\n" +
				"						\"labelValue\":\"1级佣金2.0/2\"\n" +
				"					},\n" +
				"					{\n" +
				"						\"label\":\"考核标准\",\n" +
				"						\"labelValue\":\"贷款>=2单或理财>=20万\"\n" +
				"					},\n" +
				"					{\n" +
				"						\"label\":\"业绩奖\",\n" +
				"						\"labelValue\":\"4000元\"\n" +
				"					},\n" +
				"					{\n" +
				"						\"label\":\"全勤奖金\",\n" +
				"						\"labelValue\":\"1000现金每周例会3次\"\n" +
				"					}\n" +
				"				]"));
	}

}
