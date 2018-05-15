package com.zhuanyi.vjwealth.wallet.mobile.rqjf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fab.web.controller.annotation.AppController;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.service.IHomeService;

@Controller
public class HomeController {

	@Autowired
	private IHomeService homeService;

	
	@RequestMapping("/api/home/index/queryHomeInit/v2.0")
	@AppController
	public Object queryHomeInitV2(String channelType) {
		
		return homeService.queryHomeInit(channelType);
	}

	@RequestMapping("/api/home/accumulate/queryAccumulateInit/v1.0")
	@AppController
	public Object queryAccumulateInit() {

		return homeService.queryAccumulateInit();
	}
	
	@RequestMapping("/api/home/accumulate/queryAccumulateInit/v2.1")
	@AppController
	public Object queryAccumulateInitV2() {
		
		return homeService.queryAccumulateInit();
	}
	
	@RequestMapping("/api/home/dynamic/queryLatestDynamicList/v1.0")
	@AppController
	public Object queryLatestDynamicList(String page) {
		
		return homeService.queryLatestDynamicList(page);
	}
	
	
	@RequestMapping("/api/home/home/history/v1.0")
	public String history() {
		
		return "/rqjf/history";
	}

	@RequestMapping("/api/home/report/queryReportList/v1.0")
	@AppController
	public Object queryReportList(String page) {
		
		return homeService.queryReportList(page);
	}
	
	@RequestMapping("/api/home/report/queryReportDetail/v1.0")
	public String queryReportDetail(String reportId,Model model) {
		model.addAttribute("detailContent",homeService.queryReportDetail(reportId));
		return "/rqjf/report";
	}
	
	   //	pc 版本主页

		//获取 （新手专区，推荐区，活动）标数据 wj_product_finace  /
		@RequestMapping("/api/home/getPageDataByBorrow/v1.0")
		@AppController
		public Object getPageDataByBorrow(String userId,String borrow) {
//			borrow=HJTYB;1,ACTIVITY;2,ORDINARY;3

//			bid_announcement 项目概述 "bidAnnouncement": "http://app.rongqiaobao.com/wallet-mobile/api/v3.6/product/bidAnnouncement?productId=1700003",
//			return homeService.queryHomeInit(borrowType);

			return homeService.getPageData(userId);
		}

		//获取（banner图片，平台用户数，媒体报道，公司公告）数据接口
		@RequestMapping("/api/home/getPageData/v1.0")
		@AppController
		public Object getPageData(String userId) {
//			pic&userNum&video=4&webnotice=4
//			return homeService.queryHomeInit(borrowType);
			return homeService.getPageData(userId);
		}


		@RequestMapping("/api/home/getHomeStatistics/v1.0")
		@AppController
		public Object getHomeStatistics() {

			return homeService.getHomeStatistics();
		}


		@RequestMapping("/api/platformData/getStatistics/v1.0")
		@AppController
		public Object getStatistics() {

//			return homeService.queryHomeInit(borrowType);
			return null;
		}

		@RequestMapping("/api/platformData/platformReport/v1.0")
		@AppController
		public Object platformReport() {

//			return homeService.queryHomeInit(borrowType);
			return null;
		}

		@RequestMapping("/api/borrow/getProductListJson/v1.0")
		@AppController
		public Object getProductListJson(String loanTypeArrays,String numPerPage) {

//			return homeService.queryHomeInit(borrowType);
			return null;
		}


		//	pc 版本主页 结束
}

