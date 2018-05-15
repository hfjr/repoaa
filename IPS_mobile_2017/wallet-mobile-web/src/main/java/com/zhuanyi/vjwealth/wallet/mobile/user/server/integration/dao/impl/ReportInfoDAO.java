package com.zhuanyi.vjwealth.wallet.mobile.user.server.integration.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.zhuanyi.vjwealth.wallet.mobile.user.server.integration.dao.IReportInfoDAO;
import com.zhuanyi.vjwealth.wallet.mobile.user.server.integration.mapper.IReportMapper;

@Repository
public class ReportInfoDAO implements IReportInfoDAO {

	@Autowired
	private IReportMapper reportMapper;

	@Cacheable(value = "twoHourCache")
	public List<Map<String, Object>> queryEaWanReceiveListByCount(Integer count) {
		return reportMapper.queryEaWanReceiveList(count);
	}

	@Override
	public List<Map<String, Object>> queryTaWanReceiveListByCount(Integer count) {
		return reportMapper.queryTaWanReceiveList(count);
	}

}
