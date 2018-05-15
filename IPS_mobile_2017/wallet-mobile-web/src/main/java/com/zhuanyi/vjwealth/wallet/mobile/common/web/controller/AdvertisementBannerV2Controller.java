package com.zhuanyi.vjwealth.wallet.mobile.common.web.controller;

import com.fab.web.controller.annotation.AppController;
import com.zhuanyi.vjwealth.wallet.mobile.common.server.service.IAdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v3.6")
public class AdvertisementBannerV2Controller {

	@Autowired
	private IAdvertisementService advertisementService;
	
	/**
	 * @title 根据类型，查询对应的广告板子
	 * @param type  v_financial:小赢理财广告板子；home:app主页广告板子
	 * @return 广告板子列表
	 */
	@RequestMapping("/app/common/advertisement/queryV2InvestmentAdvertisementList")
	@AppController
	public Object queryV2InvestmentAdvertisementList() {
		return advertisementService.queryV2AdvertisementBannerInfo();
	}

}
