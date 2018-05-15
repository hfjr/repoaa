package com.zhuanyi.vjwealth.wallet.mobile.credit.server.constants;

import java.util.HashMap;
import java.util.Map;

public class LoanProductIdConstant {

	public static final String BLZX = "P2016061200010000";//白领专享
	public static final String XJY = "P2016061200010001";//小金鱼
	public static final String GZXX = "P2016061200010002";//工资先享
	public static final String GZYD = "P2016061200010003";//工资易贷
	public static final String JN = "P2016061200010004";//锦囊
	public static final String LTB = "P2016061200010005";//流通宝

	private static final Map<String,String> loanProductMap = new HashMap<String,String>();

	static {
		loanProductMap.put(BLZX, "公积金贷");
		loanProductMap.put(XJY, "小金鱼");
		loanProductMap.put(GZXX, "工资先享");
		loanProductMap.put(GZYD, "工资易贷");
		loanProductMap.put(JN, "锦囊");
		loanProductMap.put(LTB, "流通宝");
	}

	public static String getValue(String key){
		return loanProductMap.get(key);
	}


}
