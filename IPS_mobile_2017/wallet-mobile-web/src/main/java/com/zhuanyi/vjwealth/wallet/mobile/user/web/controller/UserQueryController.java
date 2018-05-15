package com.zhuanyi.vjwealth.wallet.mobile.user.web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fab.web.controller.annotation.AppController;
import com.hf.comm.utils.HttpClientUtils;
import com.rqb.ips.depository.platform.faced.IPSOpenUserService;
import com.zhuanyi.vjwealth.wallet.mobile.user.server.service.IUserQueryService;

@Controller
public class UserQueryController {

	@Autowired
	private IUserQueryService userQueryService;
	
	@Autowired
	private IPSOpenUserService openUserService;
	/**
	 * 查询提现卡
	 * @param userId
	 * @return
	 */
	@RequestMapping("/app/account/queryWithdrawCard.security")
	@AppController
	public Object queryWithdrawCard(String userId) {
		return userQueryService.queryWithdrawCard(userId);
	}
	
	/**
	 * 查询充值卡
	 * @param userId

	 * @return
	 */
	@RequestMapping("/app/account/queryRechargeCard.security")
	@AppController
	public Object queryRechargeCard(String userId) {
		return userQueryService.queryRechargeCard(userId);
	}
	
	/**
	 * 查询充值卡v3.2
	 * @param userId
	 * @return
	 */
	@RequestMapping("/api/v3.2/app/account/queryRechargeCard.security")
	@AppController
	public Object queryRechargeCardV32(String userId) {
		return userQueryService.queryRechargeCardV32(userId);
	}
	
	/**
	 * ips开户 
	 * @param userId
	 * @param userName
	 * @param identNo
	 * @param realName
	 * @return
	 */
@RequestMapping(value="/api/v3.2/app/account/queryUserIsOpen", method=RequestMethod.GET)
public String queryUserIsOpen(String userId,String identNo,String realName,Model model,String source) {
		Map<Object, Object> map= openUserService.queryUserIsOpen(userId, identNo, realName,source);
		model.addAttribute("sign", map.get("sign"));
		model.addAttribute("operationType", map.get("operationType"));
		model.addAttribute("merchantID", map.get("merchantID"));
		model.addAttribute("request", map.get("request"));
		model.addAttribute("urls",HttpClientUtils.ips_url);
		return "/app/model/kaihu";
	}
	
	
	/**
	 * 查询充值卡v3.6
	 * @param userId
	 * @return
	 */
	@RequestMapping("/api/v3.6/app/account/queryRechargeCardV36.security")
	@AppController
	public Object queryRechargeCardV36(String userId) {
		return userQueryService.queryRechargeCardV36(userId);
	}
	
	/**
	 * 查询我的银行卡
	 * @param userId
	 * @return
	 */
	@RequestMapping("/app/account/queryMyBankCards.security")
	@AppController
	public Object queryMyBankCards(String userId) {
		return userQueryService.queryMyBankCards(userId);
	}
	
	
	
	/**
	 * @title 查询用户消息
	 * @return
	 */
	@RequestMapping("/app/user/queryUserMessageList.security")
	@AppController
	public Object queryUserMessageList(String userId,String type,String page) {
		
		return userQueryService.queryUserMessageList(userId,type,page);
	}
	
	/**
	 * @title 查询单个消息详情
	 * @return
	 */
	@RequestMapping("/app/user/queryUserMessageById.security")
	public Object queryUserMessageById(String id,Model model) {
		model.addAttribute("content",userQueryService.queryUserMessageById(id));
		return "/app/message/message";
	}
	
	
	/**
	 * @title 账户中心显示接口
	 * @return
	 */
	@RequestMapping("/app/user/accountCenterInfo.security")
	@AppController
	public Object accountCenterInfo(String userId) {
		
		return userQueryService.accountCenterInfo(userId);
	}

	/**
	 * @title 账户中心显示接口 v3
	 * @return
	 */
	@RequestMapping("/api/v3.0/app/user/accountCenterInfo.security")
	@AppController
	public Object accountCenterInfoV3(String userId) {

		return userQueryService.accountCenterInfoV3(userId);
	}
	
	/**
	 * @title 账户中心显示接口 v3.1
	 * @return
	 */
	@RequestMapping("/api/v3.1/app/user/accountCenterInfo.security")
	@AppController
	public Object accountCenterInfoV31(String userId) {

		return userQueryService.accountCenterInfoV31(userId);
	}
	
	
	/**
	 * @title 账户中心显示接口 v3.2
	 * @return
	 */
	@RequestMapping("/api/v3.2/app/user/accountCenterInfo.security")
	@AppController
	public Object accountCenterInfoV32(String userId) {

		return userQueryService.accountCenterInfoV32(userId);
	}

	/**
	 * @title 账户中心显示接口 v3.3
	 * @return
	 */
	@RequestMapping("/api/v3.3/app/user/accountCenterInfo.security")
	@AppController
	public Object accountCenterInfoV33(String userId) {
		return userQueryService.accountCenterInfoV33(userId);
	}
	
	/**
	 * @title 消息数
	 * @return
	 */
	@RequestMapping("/app/user/userMessageNum.security")
	@AppController
	public Object userMessageNum(String userId) {
		
		return userQueryService.queryUserMessageNum(userId);
	}
	
	
	
	/**
	 * 查询支持银行卡列表
	 * @return
	 */
	@RequestMapping("/app/user/queryAllSupportBankList")
	@AppController
	public Object queryAllSupportBankList() {
		
		return userQueryService.queryAllSupportBankList();
	}

	/**
	 * 查询支持银行卡列表v3.2
	 * @return
	 */
	@RequestMapping("/api/v3.2/app/user/queryAllSupportBankList")
	@AppController
	public Object queryAllSupportBankListV32() {
		
		return userQueryService.queryAllSupportBankListV32();
	}
	/**
	 * 查询支持银行卡列表(工资计划)
	 * @return
	 */
	@RequestMapping("/api/v3.6/app/user/queryAllSupportBankListV36")
	@AppController
	public Object queryAllSupportBankListV36() {
		return userQueryService.queryAllSupportBankListV36();
	}
	

	//v1理财介绍界面
	@RequestMapping("/app/user/introduceV1")
	public String introduceV1() {
		return "/app/finance/introduceV1";
	}
	
	
	
	//关于我们
	@RequestMapping("/app/user/aboutus")
	public String aboutus(String userId,String uuid) {
		
		return "/app/aboutus/aboutus";
	}
	
	
	//支付
	@RequestMapping("/app/user/payAgreement")
	public String payAgreement() {
		return "/app/agreement/zhifu";
	}

	//线下充值服务协议
	@RequestMapping("/app/user/payOfflineService")
	public String payOfflineService() {
		return "/app/agreement/payOfflineService";
	}
		
	
	//注册 废弃
	@RequestMapping("/app/user/registeredAgreement")
	public String registeredAgreement() {
		return "/app/agreement/zhuce";
	}

	//注册(融桥宝个人客户平台服务协议)
	@RequestMapping("/app/user/individualClientService")
	public String individualClientService() {
		return "/app/agreement/individualClientService";
	}
	
	//我的银行卡说明
	@RequestMapping("/app/user/mybankRule")
	public String mybankRule() {
		return "/app/agreement/mybank_rule";
	}
	
	
	
	//账户介绍
	@RequestMapping("/app/user/introduceAccount")
	public String introduceAccount() {
		return "/app/finance/introduceAccount";
	}

	//注册(商户钱包个人客户平台服务协议)
	@RequestMapping("/app/user/individualClientService_shqb")
	public String individualClientServiceForSHQB() {
		return "/app/agreement/individualClientService_shqb";
	}

}
