package com.zhuanyi.vjwealth.wallet.mobile.rqjf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fab.web.controller.annotation.AppController;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.common.utils.ValidatorUtils;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.service.IRecruitService;

@Controller
public class RecruitController {

	@Autowired
	private IRecruitService recruitService;
	
	@RequestMapping("/api/recruit/recruit/queryRecruitList/v1.0")
	@AppController
	public Object queryRecruitList(String page) {
		ValidatorUtils.validatePage(page);
		return recruitService.queryRecruitList(page);
	}
	
	@RequestMapping("/api/recruit/recruit/queryRecruitDetail/v1.0.security")
	@AppController
	public Object queryRecruitDetail(String recruitId) {
		ValidatorUtils.validateNull(recruitId, "recruitId不能为空");
		return recruitService.queryRecruitDetail(recruitId);
	}
	
	@RequestMapping("/api/recruit/recruit/applyInit/v1.0.security")
	@AppController
	public Object applyInit(String userId) {
		return recruitService.applyInit(userId);
	}
	
	@RequestMapping("/api/recruit/recruit/doApply/v1.0.security")
	@AppController
	public Object doApply(String recruitId, String userId, String realName, String applyPhone,String workExperience,String salaryExpectation) {
		ValidatorUtils.validateNull(recruitId, "recruitId不能为空");
		ValidatorUtils.validateNull(realName, "realName不能为空");
		ValidatorUtils.validateNull(applyPhone, "applyPhone不能为空");
		ValidatorUtils.validateNull(workExperience, "工作年限不能为空");
		ValidatorUtils.validateNull(salaryExpectation, "期望薪资不能为空");
		ValidatorUtils.validateNumber(workExperience, "工作年限");
		ValidatorUtils.validateNumber(salaryExpectation, "期望薪资");
		ValidatorUtils.validatePhone(applyPhone);
		return recruitService.doApply(recruitId, userId, realName, applyPhone, workExperience, salaryExpectation);
	}

	
}
