package com.zhuanyi.vjwealth.wallet.mobile.credit.server.constants;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class LoanRepaymentTypeConstant {
	public static final String PRINCIPAL_AND_INTEREST_EQUAL = "1";
	public static final String REPAY_MATURITY = "3";

	private static final Map<String,String> repayTypeMap = new HashMap<String,String>();
	//(等额本息:1,按月付息，到期还本：3)
	static{
		repayTypeMap.put(REPAY_MATURITY, "按月付息,到期还本");
		repayTypeMap.put(PRINCIPAL_AND_INTEREST_EQUAL, "每期还款金额相同");
	}

	public static String getValue(String key){
		String value = repayTypeMap.get(key);
		if(StringUtils.isBlank(value)){
			value = "";
		}
		return value;
	}

}
