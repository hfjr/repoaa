package com.zhuanyi.vjwealth.wallet.mobile.common.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhuanyi.vjwealth.wallet.mobile.common.server.mapper.ICommSafeMapper;

@Controller
public class TradePaySafeController {

	@Autowired
	private ICommSafeMapper commSafeMapper;


	// 交易中心是否重启指令
	@RequestMapping("/api/safe/trade/server/beat")
	@ResponseBody
	public Object tradeBeat(String jsonData) {

		return commSafeMapper.queryTtradeSafeSwitch();
	}

}
