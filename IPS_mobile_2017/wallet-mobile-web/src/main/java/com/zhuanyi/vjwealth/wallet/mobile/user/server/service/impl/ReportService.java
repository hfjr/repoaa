package com.zhuanyi.vjwealth.wallet.mobile.user.server.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fab.core.exception.service.AppException;
import com.zhuanyi.vjwealth.wallet.mobile.user.server.integration.dao.IReportInfoDAO;
import com.zhuanyi.vjwealth.wallet.mobile.user.server.service.IReportService;

@Service
public class ReportService implements IReportService {

	@Autowired
	private IReportInfoDAO reportInfoDAO;

	public Map<String, Object> queryChatInfoForEaReportData(String days, String reportType) {
		// 校验
		if (StringUtils.isBlank(days) || !IReportService.DAYS_MAPING_COUNT_MAP.containsKey(days) || StringUtils.isBlank(reportType)
				|| !IReportService.REPORT_TYPE_SET.contains(reportType)) {
			throw new AppException("参数不正确");
		}
		// 1. 初始化
		Map<String, Object> returnMap = getQueryChatInfoReportDataInit(days);
		// 查询天数
		List<Map<String, Object>> reportData = reportInfoDAO.queryEaWanReceiveListByCount(IReportService.DAYS_MAPING_COUNT_MAP.get(days));
		double maxNumber = 0;
		int index = 0;
		for (Map<String, Object> map : reportData) {
			((String[]) returnMap.get("xPlosts"))[index] = String.valueOf(map.get("wanReceiveDate")); // 获取日期
			((double[]) returnMap.get("yDotPlosts"))[index] = ((Double) map.get(reportType)).doubleValue(); // 获取收益率
			double number = ((Double) map.get(reportType)).doubleValue();
			maxNumber = number > maxNumber ? number : maxNumber; // 取最大数字
			index++;
		}
		// 处理高度问题
		maxNumber = maxNumber + (reportType.equals("weekReceiveRate") ? 0.5 : 0.1);
		// 处理yPlosts 四等分
		setYPlosts((new BigDecimal(maxNumber).setScale(1, BigDecimal.ROUND_UP).doubleValue() / 4.0), (String[]) returnMap.get("yPlosts"));
		return returnMap;

	}

	public Map<String, Object> queryChatInfoForTaReportData(String days, String reportType) {
		// 校验
		if (StringUtils.isBlank(days) || !IReportService.DAYS_MAPING_COUNT_MAP.containsKey(days) || StringUtils.isBlank(reportType)
				|| !IReportService.REPORT_TYPE_SET.contains(reportType)) {
			throw new AppException("参数不正确");
		}
		// 1. 初始化
		Map<String, Object> returnMap = getQueryChatInfoReportDataInit(days);
		// 查询天数
		List<Map<String, Object>> reportData = reportInfoDAO.queryTaWanReceiveListByCount(IReportService.DAYS_MAPING_COUNT_MAP.get(days));
		double maxNumber = 0;
		int index = 0;
		for (Map<String, Object> map : reportData) {
			((String[]) returnMap.get("xPlosts"))[index] = String.valueOf(map.get("wanReceiveDate")); // 获取日期
			((double[]) returnMap.get("yDotPlosts"))[index] = ((Double) map.get(reportType)).doubleValue(); // 获取收益率
			double number = ((Double) map.get(reportType)).doubleValue();
			maxNumber = number > maxNumber ? number : maxNumber; // 取最大数字
			index++;
		}
		// 处理高度问题
		maxNumber = maxNumber + (reportType.equals("weekReceiveRate") ? 0.5 : 0.1);
		// 处理yPlosts 四等分
		setYPlosts((new BigDecimal(maxNumber).setScale(1, BigDecimal.ROUND_UP).doubleValue() / 4.0), (String[]) returnMap.get("yPlosts"));
		return returnMap;

	}

	private Map<String, Object> getQueryChatInfoReportDataInit(String days) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String[] xPlosts = new String[IReportService.DAYS_MAPING_COUNT_MAP.get(days)];
		String[] yPlosts = new String[5];
		double[] yDotPlosts = new double[IReportService.DAYS_MAPING_COUNT_MAP.get(days)];

		returnMap.put("tickInterval", IReportService.DAYS_MAPING_INTERVAL_MAP.get(days));
		returnMap.put("xPlosts", xPlosts);
		returnMap.put("yPlosts", yPlosts);
		returnMap.put("yDotPlosts", yDotPlosts);
		return returnMap;
	}

	private void setYPlosts(double avageRate, String[] yPlosts) {
		// 四等份
		for (int i = 0; i < 5; i++) {
			yPlosts[i] = new BigDecimal(avageRate * i).setScale(3, BigDecimal.ROUND_FLOOR).toString();
		}
	}

	public Map<String, Object> queryV1InfoReport() {
		Map<String, Object> returnMap = new HashMap<String, Object>();

		List<Map<String, Object>> reportData = reportInfoDAO.queryEaWanReceiveListByCount(IReportService.DAYS_MAPING_COUNT_MAP.get("30"));
		double[] huobiPositions = new double[7];
		String[] categories = new String[7];

		int index = 0;
		int interval = 0;
		while (interval < reportData.size()) {
			huobiPositions[index] = ((Double) (reportData.get(interval).get(IReportService.MARKETTYPE_WEEKRECEIVERATE))).doubleValue();
			categories[index] = String.valueOf((reportData.get(interval).get("wanReceiveDate")));
			interval += IReportService.DAYS_MAPING_INTERVAL_MAP.get("30");
			index++;
		}

		returnMap.put("huobiPositions", huobiPositions);
		returnMap.put("categories", categories);

		return returnMap;
	}

}
