package com.zhuanyi.vjwealth.wallet.mobile.credit.web.controller;

import com.fab.web.controller.annotation.AppController;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.IEasyLoanService;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.easyLoan.EasyLoanSaveDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;


@Controller
@RequestMapping("/api/v4.0")
public class EasyLoanController {

	@Autowired
	private IEasyLoanService easyLoanService;


	/**
	 * 1. 工资易贷产品初始化
	 * @param userId
	 * @return
	 * @since 3.6
	 */
	@RequestMapping("/app/credit/easyLoan/easyLoanInit")
	@AppController
	public Object easyLoanInit(String userId,String loanProductId) {

		return easyLoanService.easyLoanInit(userId,loanProductId);

	}



	/**
	 * 2. 工资易贷产品初始化
	 * @return
	 * @since 3.6
	 */
	@RequestMapping("/app/credit/easyLoan/queryCityList")
	@AppController
	public Object queryCityList(String loanProductId) {

		return easyLoanService.queryCityList(loanProductId);

	}



	/**
	 * 3.工资易贷保存
	 * @since 3.6
	 * @param
	 * @return
	 */
	@RequestMapping("/app/credit/easyLoan/easyLoanSave")
	@AppController
	public Object easyLoanSave(EasyLoanSaveDTO easyLoanSaveDTO) {
		return easyLoanService.EasyLoanApplySave(easyLoanSaveDTO);
	}


	/**
	 * 4.根据城市展示借款期限
	 * @since 3.6
	 * @param
	 * @return
	 */
	@RequestMapping("/app/credit/easyLoan/queryPeriodByCity")
	@AppController
	public Object queryPeriodByCity(String cityCode) {
		return easyLoanService.queryPeriodByCity(cityCode);
	}

}
