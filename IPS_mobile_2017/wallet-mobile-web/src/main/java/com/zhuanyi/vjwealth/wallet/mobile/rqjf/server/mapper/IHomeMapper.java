package com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.mapper;

import java.util.List;
import java.util.Map;

import com.fab.server.annotation.Mapper;

@Mapper
public interface IHomeMapper {
	
	public String queryAccumulateTotal();
	
	public Map<String,Object> queryYearFinances();
	
	public Map<String,String> queryFinancesToday();
	public Map<String,String> queryFinancesYesterday();
	public Map<String,String> queryFinancesLastWeek();
	public Map<String,String> queryFinancesLastMonth();
	
	
	public Map<String,Object> queryYearLoan();
	
	public Map<String,String> queryLoanToday();
	public Map<String,String> queryLoanYesterday();
	public Map<String,String> queryLoanLastWeek();
	public Map<String,String> queryLoanLastMonth();
	
	
	
	public Map<String,Object> queryAccumulate();
	
	public List<Map<String,Object>> queryAccumulateCountList();
	
	

	public List<Map<String,Object>> queryLatestDynamicHomeList();
	
	
	public List<Map<String,Object>> queryLatestDynamicList(int page);
	
	public List<Map<String,Object>> queryReportList(int page);
	
	public String queryReportDetail(String reportId);
	
	
	public List<Map<String,Object>> queryAccumulateList();
	
	
	public List<Map<String,Object>> queryHommerBannerList();
	
	public List<Map<String,Object>> queryHomeBannerList();
	
	
	
}
