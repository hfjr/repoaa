package com.zhuanyi.vjwealth.wallet.mobile.credit.server.constants;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class RepaymentTypeConstant {
	
	private static final Map<String,String> repayTypeMap = new HashMap<String,String>();
	//(按月付息:monthly_interest, 到期还本付息:repay_maturity,等额本息:principal_and_interest_equal,等额本金:principal_equal,等本等息:principal_equal_and_interest_equal)
	static{
		repayTypeMap.put("monthly_interest", "按月付息");
		repayTypeMap.put("repay_maturity", "到期还本付息");
		repayTypeMap.put("principal_and_interest_equal", "等额本息");
		repayTypeMap.put("principal_equal", "等额本金");
		repayTypeMap.put("principal_equal_and_interest_equal", "等本等息");
	}
	
	public static String getValue(String key){
		String value = repayTypeMap.get(key);
		if(StringUtils.isBlank(value)){
			value = "";
		}
		return value;
	}

}
