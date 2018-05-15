package com.zhuanyi.vjwealth.wallet.mobile.rqjf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fab.web.controller.annotation.AppController;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.common.utils.ValidatorUtils;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.dto.LoanApplyDTO;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.service.ILoanService;

@Controller
public class LoanController {

	@Autowired
	private ILoanService loanService;

	@RequestMapping("/api/loan/loan/applyInit/v1.0.security")
	@AppController
	public Object applyInit(String userId, String productType) {
		ValidatorUtils.validateNull(productType, "productType");
		return loanService.applyInit(userId, productType);
	}

	@RequestMapping("/api/loan/loan/doapply/v1.0.security")
	@AppController
	public Object doapply(LoanApplyDTO loanApplyDTO) {
		validateDoApply(loanApplyDTO);
		return loanService.doApply(loanApplyDTO);
	}
	
	//新增推荐人
	@RequestMapping("/api/loan/loan/doapply/v2.0.security")
	@AppController
	public Object doapplyV2(LoanApplyDTO loanApplyDTO) {
		validateDoApply(loanApplyDTO);
		return loanService.doApplyV2(loanApplyDTO);
	}

	@RequestMapping("/api/loan/borrow/queryBorrowList/v1.0.security")
	@AppController
	public Object queryBorrowList(String page,String userId) {
		ValidatorUtils.validatePage(page);	
		return loanService.queryBorrowList(page,userId);
	}

	@RequestMapping("/api/loan/borrow/queryRepayPlan/v1.0.security")
	@AppController
	public Object queryRepayPlan(String userId, String loanId) {
		
		ValidatorUtils.validateNull(loanId, "loanId");
		
		return loanService.queryRepayPlan(userId, loanId);
	}

	@RequestMapping("/api/loan/banner/queryBannerInfo/v1.0")
	@AppController
	public Object queryBannerInfo() {

		return loanService.queryBannerInfo();
	}
	
	
	private void validateDoApply(LoanApplyDTO loanApplyDTO){
		String realName=loanApplyDTO.getRealName();
		String phone=loanApplyDTO.getPhone();
		String idNo=loanApplyDTO.getIdNo();
		String amt=loanApplyDTO.getLoanAmt();
		ValidatorUtils.validateAmt(amt);
		ValidatorUtils.validateNull(realName, "realName");
		ValidatorUtils.validateNull(phone, "phone");
		ValidatorUtils.validateIDCard(idNo);
	}

}
