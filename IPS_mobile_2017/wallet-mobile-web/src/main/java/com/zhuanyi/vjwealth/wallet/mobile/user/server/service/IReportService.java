package com.zhuanyi.vjwealth.wallet.mobile.user.server.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 报表需求功能
 * 
 * @author xuejie
 *
 */
public interface IReportService {

	@SuppressWarnings("serial")
	public static final Map<String, Integer> DAYS_MAPING_COUNT_MAP = new HashMap<String, Integer>() {
		{
			put("7", 7);
			put("30", 31); // 5(间隔)*6(等份) + 1 = 31
			put("90", 91); // 15* 6 + 1 = 91
		}
	};
	@SuppressWarnings("serial")
	public static final Map<String, Integer> DAYS_MAPING_INTERVAL_MAP = new HashMap<String, Integer>() {
		{
			put("7", 1);
			put("30", 5);
			put("90", 15);
		}
	};

	@SuppressWarnings("serial")
	public static final Set<String> REPORT_TYPE_SET = new HashSet<String>() {
		{
			add("weekReceiveRate");
			add("wanReceive");
		}
	};

	public static final String MARKETTYPE_WEEKRECEIVERATE = "weekReceiveRate";
	public static final String MARKETTYPE_WANRECEIVE = "wanReceive";

	public Map<String, Object> queryChatInfoForEaReportData(String days, String reportType);

	public Map<String, Object> queryChatInfoForTaReportData(String days, String reportType);

	public Map<String, Object> queryV1InfoReport();

}