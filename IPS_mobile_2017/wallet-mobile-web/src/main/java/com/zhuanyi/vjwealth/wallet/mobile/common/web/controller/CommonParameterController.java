package com.zhuanyi.vjwealth.wallet.mobile.common.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fab.core.exception.service.AppException;
import com.fab.web.controller.annotation.AppController;
import com.zhuanyi.vjwealth.wallet.mobile.common.server.service.ICommonVariableService;

@Controller
public class CommonParameterController {

	@Autowired
	private ICommonVariableService commonVariableService;

	@AppController
	@RequestMapping("/mobile/common/variable/check")
	public Object commonVariableCheck(Long variableVersion) {
		if (variableVersion == null) {
			throw new AppException("请检查参数");
		}
		return commonVariableService.queryCommonVariableMap(variableVersion);
	}
}
