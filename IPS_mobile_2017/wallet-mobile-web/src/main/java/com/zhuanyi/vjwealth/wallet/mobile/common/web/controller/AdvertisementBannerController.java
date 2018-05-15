package com.zhuanyi.vjwealth.wallet.mobile.common.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fab.web.controller.annotation.AppController;
import com.zhuanyi.vjwealth.wallet.mobile.common.server.service.IAdvertisementService;

@Controller
@RequestMapping("/api/v3.0")
public class AdvertisementBannerController {

	@Autowired
	private IAdvertisementService advertisementService;
	
	/**
	 * @title 根据类型，查询对应的广告板子
	 * @param type  v_financial:小赢理财广告板子；home:app主页广告板子
	 * @return 广告板子列表
	 */
	@RequestMapping("/app/common/advertisement/queryInvestmentAdvertisementList")
	@AppController
	public Object queryAllAdvertisementInfo() {
		return advertisementService.queryAdvertisementBannerInfo();
	}

	/**
	 * 查询商户钱包首页广告板子
	 */
	@RequestMapping("/app/common/advertisement/queryHomeAdvListForSHQB")
	@AppController
	public Object queryHomeAdvertisementInfoForSHQB(String appType) {
		return advertisementService.queryHomeAdvBannerInfoForSHQB(appType);
	}

}
