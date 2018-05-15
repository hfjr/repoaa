package com.zhuanyi.vjwealth.wallet.mobile.user.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhuanyi.vjwealth.wallet.mobile.user.server.service.IReportService;

@Controller
public class ReportInfoController {

	@Autowired
	private IReportService reportService;

	@RequestMapping("/report/weekReceiveRate/{days}")
	public String queryWeekReceiveRateReport(@PathVariable String days, Model model) {
		//model.addAllAttributes(reportService.queryChatInfoForEaReportData(days, IReportService.MARKETTYPE_WEEKRECEIVERATE));
		model.addAllAttributes(reportService.queryChatInfoForTaReportData(days, IReportService.MARKETTYPE_WEEKRECEIVERATE));
		return "/report/highcharts/marketInfoReport";
	}

	@RequestMapping("/report/wanReceive/{days}")
	public String queryWanReceiveMarketInfoReport(@PathVariable String days, Model model) {
		//model.addAllAttributes(reportService.queryChatInfoForEaReportData(days, IReportService.MARKETTYPE_WANRECEIVE));
		model.addAllAttributes(reportService.queryChatInfoForTaReportData(days, IReportService.MARKETTYPE_WANRECEIVE));
		return "/report/highcharts/wanReceiveMarketInfoReport";
	}
	
	@RequestMapping("/report/v1")
	public String queryV1InfoReport(Model model) {
		model.addAllAttributes(reportService.queryV1InfoReport());
		return "/report/highcharts/v1licai";
	}

}
