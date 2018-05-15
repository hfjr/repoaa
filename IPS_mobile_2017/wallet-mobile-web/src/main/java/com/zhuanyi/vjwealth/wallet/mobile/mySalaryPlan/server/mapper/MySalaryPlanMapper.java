package com.zhuanyi.vjwealth.wallet.mobile.mySalaryPlan.server.mapper;

import java.util.Map;

import com.fab.server.annotation.Mapper;

/**
 * @Copyright (C), 2015-2016,蔚捷互联网金融服务有限公司
 * @desc:我的工资计划
 * @author: Tony Tang
 * @date: 2016-08-31 17:08
 */
@Mapper
public interface MySalaryPlanMapper {

	//查询ta账户的万分收益率
	Map<String,Object> queryTAccountFundWanInfo();
}
