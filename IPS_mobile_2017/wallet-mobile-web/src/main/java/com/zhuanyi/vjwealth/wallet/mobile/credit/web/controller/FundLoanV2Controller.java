package com.zhuanyi.vjwealth.wallet.mobile.credit.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fab.web.controller.annotation.AppController;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.IFundLoanService;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan.FundInfoSaveDTO;


@Controller
public class FundLoanV2Controller {
	@Autowired
	private IFundLoanService fundLoanService;


	/**
	 * 9.1. 申请额度-公积金信息保存接口-会判断是否需要验证码
	 * @param
	 * @return
	 * 1.不需要用户输入验证码的城市，返回程序执行结果
	 * 2.需要用户输入验证码的城市，返回验证码图片
	 * @since 4.1
	 */
	@RequestMapping("/api/v4.1/app/credit/fund/applyCreditPersonalInfoCommitV2.security")
	@AppController
	public Object applyCreditPersonalInfoCommitV2(FundInfoSaveDTO query, HttpServletRequest request) {
		return fundLoanService.applyCreditPersonalInfoCommitV2(query,request);
	}
	
	/**
	 * 9.2. 申请额度-公积金信息保存接口-公积金登录验证码刷新
	 * @param
	 * @return
	 * @since 4.1
	 */
	@RequestMapping("/api/v4.1/app/credit/fund/refreshValidateCode.security")
	@AppController
	public Object refreshValidateCode(FundInfoSaveDTO query, HttpServletRequest request) {
		return fundLoanService.refreshValidateCode(query,request);
	}

}