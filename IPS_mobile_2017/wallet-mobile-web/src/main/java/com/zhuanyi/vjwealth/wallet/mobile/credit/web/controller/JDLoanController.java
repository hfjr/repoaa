package com.zhuanyi.vjwealth.wallet.mobile.credit.web.controller;

import com.fab.web.controller.annotation.AppController;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.IEasyLoanService;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.IJDLoanService;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.easyLoan.EasyLoanSaveDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBRechargeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;


/**
 * 京东金融绑卡
 */
@Controller
@RequestMapping("/api/v4.0")
public class JDLoanController {

	@Autowired
	private IJDLoanService jDLoanService;


	/**
	 * 1.查询支持的银行卡列表
	 * @param
	 * @return
	 * @since 4.0
	 */
	@RequestMapping("/app/credit/jd/querySupportBankList")
	@AppController
	public Object querySupportBankList() {
		return jDLoanService.querySupportBankList();
	}


	/**
	 * 2.查询已经绑定的银行卡
	 * @param
	 * @return
	 * @since 4.0
	 */
	@RequestMapping("/app/credit/jd/queryBindedCardList")
	@AppController
	public Object queryBindedCardList(String userId) {
		return jDLoanService.queryBindedCardList(userId);
	}

	/**
	 * 3.绑卡初始化
	 * @param
	 * @return
	 * @since 4.0
	 */
	@RequestMapping("/app/credit/jd/bindCardInit")
	@AppController
	public Object bindCardInit(String userId) {
		return jDLoanService.bindCardInit(userId);
	}

	/**
	 * 4.根据卡号获取卡类型及银行卡名称
	 * @param
	 * @return
	 * @since 4.0
	 */
	@RequestMapping("/app/credit/jd/queryBankNameByCardNo")
	@AppController
	public Object queryBankNameByCardNo(String cardNo) {
		return jDLoanService.queryBankNameByCardNo(cardNo);
	}

	/**
	 * 5.绑卡发送验证码
	 * @param
	 * @return
	 * @since 4.0
	 */
	@RequestMapping("/app/credit/jd/bindCardSendSms")
	@AppController
	public Object bindCardSendSms(MBRechargeDTO dto) {
		return jDLoanService.bindCardSendSms(dto);
	}

	/**
	 * 6.绑卡提交
	 * @param
	 * @return
	 * @since 4.0
	 */
	@RequestMapping("/app/credit/jd/bindCardConfirm")
	@AppController
	public Object bindCardConfirm(MBRechargeDTO dto) {
		return jDLoanService.bindCardConfirm(dto);
	}

}
