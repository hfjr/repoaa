package com.zhuanyi.vjwealth.wallet.mobile.account.web.controller;

import com.fab.web.controller.annotation.AppController;
import com.zhuanyi.vjwealth.wallet.mobile.account.server.service.ITAccountQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class UserTAccountQueryController {

	@Autowired
	private ITAccountQueryService tAccountQueryService;
	
	
	/**
	 * ta账户详情
	 * @param userId
	 * @return
	 */
//	@RequestMapping("/app/account/queryTAccountInfo.security")
//	@AppController
//	public Object queryEAccountInfo(String userId) {
//		//无冻结金额的场景
//		return tAccountQueryService.queryTAccountInfo(userId);
//	}
	
	/**
	 * ta账户账单明细
	 * @param userId
	 * @param page
	 * @return
	 */
	@RequestMapping("/app/account/queryTAccountDetail.security")
	@AppController
	public Object queryTAccountDetail(String userId,String page) {
		
		
		return tAccountQueryService.queryTAccountDetail(userId,page);
	}
	
	
	/**
	 * ta账户昨日收益列表
	 * @param userId
	 * @param page
	 * @return
	 */
	@RequestMapping("/app/account/queryTAccountReciveDetail.security")
	@AppController
	public Object queryTAccountReciveDetail(String userId,String page) {
		
		
		return tAccountQueryService.queryTAccountReciveDetail(userId, page);
	}
	
	/**
	 * 昨日的万分收益与七日年化利率
	 * @return
	 */
	@RequestMapping("/app/account/queryTAccountFundWanInfo")
	@AppController
	public Object queryTAccountFundWanInfo() {
		
		return tAccountQueryService.queryTAccountFundWanInfo();
	}
	
	/**
	 * ta账户转出查询
	 * @param userId
	 * @return
	 */
	@RequestMapping("/app/account/queryTAccountOutComeBalance.security")
	@AppController
	public Object queryTAccountOutComeBalance(String userId) {
		
		return tAccountQueryService.queryTAccountOutComeBalance(userId);
	}
	
	
	/**
	 * ta账户余额转入查询
	 * @param userId
	 * @return
	 */
	@RequestMapping("/app/account/queryTAccountInComeBalance.security")
	@AppController
	public Object queryTAccountInComeBalance(String userId) {
		
		return tAccountQueryService.queryTAccountInComeBalance(userId);
	}
	
	/**
	 * ta账户冻结额列表
	 * @param userId
	 * @param page
	 * @return
	 */
	@RequestMapping("/app/account/queryTAccountFrozenDetail.security")
	@AppController
	public Object queryTAccountFrozenDetail(String userId,String page) {
		
		return tAccountQueryService.queryTAccountFrozenDetail(userId,page);
	}
	
	
	/**
	 * ta账户初始化页面
	 * @param userId
	 * @return
	 */
	@RequestMapping("/app/account/queryTAccountInit.security")
	@AppController
	public Object queryTAccountInitPage(String userId) {
		
		return tAccountQueryService.queryTAccountInitPage(userId);
	}

}
