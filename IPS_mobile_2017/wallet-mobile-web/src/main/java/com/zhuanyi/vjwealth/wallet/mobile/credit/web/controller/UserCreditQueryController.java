package com.zhuanyi.vjwealth.wallet.mobile.credit.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fab.web.controller.annotation.AppController;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.ICreditQueryService;


@Controller
public class UserCreditQueryController {

	@Autowired
	private ICreditQueryService creditQueryService;
	
	
	/**
	 * 五边形
	 * @param userId
	 * @return
	 */
	@RequestMapping("/app/account/queryCreditScoreForAppPolygon.security")
	@AppController
	public Object queryCreditScoreForAppPolygon(String userId) {
		
		return creditQueryService.queryCreditScoreForAppPolygonByUserid(userId);
	}
	
	/**
	 * 码表
	 * @param userId
	 * @return
	 */
	@RequestMapping("/app/account/queryCreditScoreForAppStopwatch.security")
	@AppController
	public Object queryCreditScoreForAppStopwatch(String userId) {
		
		return creditQueryService.queryCreditScoreForAppStopwatchByUserid(userId);
	}
	
	
	//合法申明
	@RequestMapping("/app/credit/legalStatement")
	public String legalStatement() {
		
		return "/app/credit/hefashengming";
	}
	
	//提升信用
	@RequestMapping("/app/credit/addCredit")
	public String addCredit() {
		
		return "/app/credit/tishenxinyong";
	}
	
}
