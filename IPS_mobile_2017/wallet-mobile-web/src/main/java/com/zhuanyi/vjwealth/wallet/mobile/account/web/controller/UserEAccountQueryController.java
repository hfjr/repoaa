package com.zhuanyi.vjwealth.wallet.mobile.account.web.controller;

import com.zhuanyi.vjwealth.wallet.mobile.account.server.service.ITAccountQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fab.web.controller.annotation.AppController;
import com.zhuanyi.vjwealth.wallet.mobile.account.server.service.IEAccountQueryService;


@Controller
public class UserEAccountQueryController {

	//@Autowired
	//private IEAccountQueryService eAccountQueryService;

	@Autowired
	private ITAccountQueryService tAccountQueryService;
	
	
	/**
	 * e账户详情
	 * @param userId
	 * @param uuid
	 * @return
	 */
	@RequestMapping("/app/account/queryEAccountInfo.security")
	@AppController
	public Object queryEAccountInfo(String userId) {
		//无冻结金额的场景
		//return eAccountQueryService.queryEAccountInfo(userId);
        return tAccountQueryService.queryTAccountInfo(userId);
	}
	
	/**
	 * e账户账单明细
	 * @param userId
	 * @param uuid
	 * @param page
	 * @return
	 */
	@RequestMapping("/app/account/queryEAccountDetail.security")
	@AppController
	public Object queryEAccountDetail(String userId,String page) {
		
		
		//return eAccountQueryService.queryEAccountDetail(userId,page);
        return tAccountQueryService.queryTAccountDetail(userId, page);
	}
	
	
	/**
	 * e账户昨日收益列表
	 * @param userId
	 * @param uuid
	 * @param type 转入或是转出
	 * @param page
	 * @return
	 */
	@RequestMapping("/app/account/queryEAccountReciveDetail.security")
	@AppController
	public Object queryEAccountReciveDetail(String userId,String page) {
		
		
		//return eAccountQueryService.queryEAccountReciveDetail(userId, page);
		return tAccountQueryService.queryTAccountReciveDetail(userId, page);
	}
	
	/**
	 * 昨日的万分收益与七日年化利率
	 * @param userId
	 * @param uuid
	 * @return
	 */
	@RequestMapping("/app/account/queryEAccountFundWanInfo")
	@AppController
	public Object queryEAccountFundWanInfo() {
		
		//return eAccountQueryService.queryEAccountFundWanInfo();
		return tAccountQueryService.queryTAccountFundWanInfo();
	}
	
	/**
	 * e账户转出查询
	 * @param userId
	 * @param uuid
	 * @return
	 */
	@RequestMapping("/app/account/queryEAccountOutComeBalance.security")
	@AppController
	public Object queryEAccountOutComeBalance(String userId) {
		
		//return eAccountQueryService.queryEAccountOutComeBalance(userId);
		return tAccountQueryService.queryTAccountOutComeBalance(userId);
	}
	
	
	/**
	 * e账户余额转入查询
	 * @param userId
	 * @param uuid
	 * @return
	 */
	@RequestMapping("/app/account/queryEAccountInComeBalance.security")
	@AppController
	public Object queryEAccountInComeBalance(String userId) {
		
		//return eAccountQueryService.queryEAccountInComeBalance(userId);
		return tAccountQueryService.queryTAccountInComeBalance(userId);
	}
	
	/**
	 * e账户冻结额列表
	 * @param userId
	 * @param uuid
	 * @param page
	 * @return
	 */
	@RequestMapping("/app/account/queryEAccountFrozenDetail.security")
	@AppController
	public Object queryEAccountFrozenDetail(String userId,String page) {
		
		
		//return eAccountQueryService.queryEAccountFrozenDetail(userId,page);
		return tAccountQueryService.queryTAccountFrozenDetail(userId,page);
	}
	
	
	/**
	 * e账户初始化页面
	 * @param userId
	 * @param uuid
	 * @param page
	 * @return
	 */
	@RequestMapping("/app/account/queryEAccountInit.security")
	@AppController
	public Object queryEAccountInitPage(String userId) {
		
		//return eAccountQueryService.queryEAccountInitPage(userId);
		return tAccountQueryService.queryTAccountInitPage(userId);
	}

}
