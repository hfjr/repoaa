package com.zhuanyi.vjwealth.wallet.mobile.account.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fab.web.controller.annotation.AppController;
import com.zhuanyi.vjwealth.wallet.mobile.account.server.service.IMAccountQueryService;


@Controller
public class UserMAccountQueryController {

	@Autowired
	private IMAccountQueryService mAccountQueryService;

	
	
	/**
	 * 总账户详情
	 * @param userId
	 * @param uuid
	 * @return
	 */
	@RequestMapping("/app/account/queryMAccountInfo.security")
	@AppController
	public Object queryMAccountInfo(String userId) {
		
		return mAccountQueryService.queryMAccountInfo(userId);
	}
	
	
	
	/**
	 * 总账户 账单
	 * @param userId
	 * @param uuid
	 * @param type
	 * @param page
	 * @return
	 */
	@RequestMapping("/app/account/totalAccount/queryTotalAccountBill.security")
	@AppController
	public Object queryTotalAcountBill(String userId,String page) {
		
		
		return mAccountQueryService.queryTotalAcountBill(userId,page);
	}
	
	/**
	 * 总账户 收益
	 * @param userId
	 * @param uuid
	 * @param type
	 * @param page
	 * @return
	 */
	@RequestMapping("/app/account/totalAccount/queryTotalAccountReceive.security")
	@AppController
	public Object queryTotalAccountReceive(String userId,String page) {
		
		
		return mAccountQueryService.queryTotalAcountReceive(userId,page);
	}
	
	
	
	/**
	 * 总账户余额
	 * @param userId
	 * @param uuid
	 * @return
	 */
	@RequestMapping("/app/account/queryTotalAccountBalance.security")
	@AppController
	public Object queryTotalAccountBalance(String userId) {
		return mAccountQueryService.queryTotalAccountBalance(userId);
	}
	
	/**
	 * 账户的冻结金额(包括主账户,e账户,v账户) 接口到2.0以后过期
	 * @param userId
	 * @param uuid
	 * @return
	 */
	@Deprecated
	@RequestMapping("/app/account/queryAccountFrozenBalance.security")
	@AppController
	public Object queryAccountFrozenBalance(String userId) {
		return mAccountQueryService.queryAccountFrozenBalance(userId);
	}
	
	/**
	 * 账户的冻结金额详细(包括主账户,e账户,v账户)
	 * @param userId
	 * @param uuid
	 * @return
	 */
	@RequestMapping("/app/account/queryAccountFrozenBalanceDetail.security")
	@AppController
	public Object queryAccountFrozenBalanceDetail(String userId,String page) {
		return mAccountQueryService.queryAccountFrozenBalanceDetail(userId,page);
	}
	
	
	//财富界面提现
	@RequestMapping("/app/account/queryWithdrawMaAccountAndEAccountInfo.security")
	@AppController
	public Object queryWithdrawMaAccountAndEAccountInfo(String userId) {
		//return mAccountQueryService.queryWithdrawMaAccountAndEAccountInfo(userId);
		return mAccountQueryService.queryWithdrawMaAccountAndTAccountInfo(userId);
	}
	
	
	
}
