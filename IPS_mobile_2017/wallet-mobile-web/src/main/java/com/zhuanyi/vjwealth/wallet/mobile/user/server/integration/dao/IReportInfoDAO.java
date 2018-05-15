package com.zhuanyi.vjwealth.wallet.mobile.user.server.integration.dao;

import java.util.List;
import java.util.Map;

/**
 * 报表功能
 * 
 * @author xuejie
 *
 */
public interface IReportInfoDAO {

	List<Map<String, Object>> queryEaWanReceiveListByCount(Integer count);
	List<Map<String, Object>> queryTaWanReceiveListByCount(Integer count);

}
