package com.zhuanyi.vjwealth.wallet.mobile.account.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fab.web.controller.annotation.AppController;
import com.zhuanyi.vjwealth.wallet.mobile.account.server.service.IVAccountQueryService;


@Controller
public class UserVAccountQueryController {

	@Autowired
	private IVAccountQueryService vaAccountQueryService;
	
	
	/**
	 * 获取V理财资产和累计收益
	 * @param userId
	 * @param uuid
	 * @return
	 */
	@RequestMapping("/app/account/queryVAccountInfo.security")
	@AppController
	public Object queryVAccountInfo(String userId) {
		
		return vaAccountQueryService.queryVAccountInfo(userId);
	}
	
	
	
	/**
	 * V+理财初始化页面
	 * @param userId
	 * @param uuid
	 * @return
	 */
	@RequestMapping("/app/account/vplus/queryVplusAccount.security")
	@AppController
	public Object queryVplusAccount(String userId) {
		return vaAccountQueryService.queryVplusAccount(userId);
	}
	
	
	
	/**
	 * v+转出余额查询
	 * @param userId
	 * @param uuid
	 * @return
	 */
	@RequestMapping("/app/account/queryVAccountToBalance.security")
	@AppController
	public Object queryVAccountToBalance(String userId) {
		
		return  vaAccountQueryService.queryVAccountToBalance(userId);
	}
	
	/**
	 * v理财累计收益列表
	 * @param userId
	 * @param uuid
	 * @return
	 */
	@RequestMapping("/app/account/queryVAccountReceiveDetail.security")
	@AppController
	public Object queryVAccountReceiveDetail(String userId,String page) {
		
		return  vaAccountQueryService.queryVAccountReceiveDetail(userId,page);
	}
	
	

	/**
	 * v+七日年化利率及万分收益
	 * @return
	 */
	@RequestMapping("/app/account/queryVAccountFundWanInfo")
	@AppController
	public Object queryVAccountFundWanInfo() {
		
		return vaAccountQueryService.queryVAccountFundWanInfo();
	}
	
	
	
	
	
	/**
	 * v+账单
	 * @param userId
	 * @param uuid
	 * @param type
	 * @param page
	 * @return
	 */
	@RequestMapping("/app/account/queryVAccountBill.security")
	@AppController
	public Object queryVAcountBill(String userId,String page) {
		
		
		return vaAccountQueryService.queryVAcountBill(userId,page);
	}
	
	/**
	 * v+冻结额列表
	 * @param userId
	 * @param uuid
	 * @param page
	 * @return
	 */
	@RequestMapping("/app/account/queryVAccountFrozenDetail.security")
	@AppController
	public Object queryVAccountFrozenDetail(String userId,String page) {
		
		
		return vaAccountQueryService.queryVAccountFrozenDetail(userId,page);
	}
	
	
	
	/**
	 * 查询v1可购买额度
	 * @return
	 */
	@RequestMapping("/app/account/applyMaToV1Limit.security")
	@AppController
	public Object applyMaToV1Limit(String userId) {
		
		return vaAccountQueryService.applyMaToV1Limit(userId);
	}
	
	
	
	/**
	 * v1理财初始化页面
	 * @return
	 */
	@RequestMapping("/app/account/queryVAccountInit.security")
	@AppController
	public Object queryVAccountInitPage(String userId) {
		
		return vaAccountQueryService.queryVAccountInitPage(userId);
	}
	
	
	/**
	 * v1理财转入初始化页面
	 * @return
	 */
	@RequestMapping("/app/account/applyMaToV1Init.security")
	@AppController
	public Object applyMaToV1InitPage(String userId) {
		
		return vaAccountQueryService.applyMaToV1InitPage(userId);
	}
	
	
}
