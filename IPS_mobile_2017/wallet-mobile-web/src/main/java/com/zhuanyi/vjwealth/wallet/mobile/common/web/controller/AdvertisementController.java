package com.zhuanyi.vjwealth.wallet.mobile.common.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fab.web.controller.annotation.AppController;
import com.zhuanyi.vjwealth.wallet.mobile.common.server.service.IAdvertisementService;

@Controller
public class AdvertisementController {

	@Autowired
	private IAdvertisementService advertisementService;
	
	
	@RequestMapping("/app/common/advertisement/queryAllAdvertisementInfo")
	@AppController
	public Object queryAllAdvertisementInfo() {
		return advertisementService.queryAdvertisementInfo();
	}

   //todo 1.获取投资理财Banner
	@RequestMapping("/mock-stop/app/common/advertisement/queryInvestmentAdvertisementList")
	@AppController
	public Object queryInvestmentAdvertisementList() {
		return advertisementService.queryAdvertisementInfo();
	}
}
