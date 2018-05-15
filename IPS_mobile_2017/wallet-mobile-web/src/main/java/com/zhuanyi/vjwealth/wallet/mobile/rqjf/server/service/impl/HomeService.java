package com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.service.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fab.core.logger.BaseLogger;
import com.zhuanyi.vjwealth.wallet.mobile.account.server.mapper.TAccountQueryMapper;
import com.zhuanyi.vjwealth.wallet.mobile.common.dto.UserChannelTypeEnum;
import com.zhuanyi.vjwealth.wallet.mobile.common.server.mapper.IAdvertisementMapper;
import com.zhuanyi.vjwealth.wallet.mobile.common.server.mapper.IWeixinHomeMapper;
import com.zhuanyi.vjwealth.wallet.mobile.common.server.service.ICommConfigsQueryService;
import com.zhuanyi.vjwealth.wallet.mobile.common.util.BizCommonConstant;
import com.zhuanyi.vjwealth.wallet.mobile.common.util.HttpClientUtil;
import com.zhuanyi.vjwealth.wallet.mobile.index.server.dto.HomeAPIVersionEnum;
import com.zhuanyi.vjwealth.wallet.mobile.index.server.dto.IndexFinancialAreaDTO;
import com.zhuanyi.vjwealth.wallet.mobile.index.server.dto.IndexLoanAreaDTO;
import com.zhuanyi.vjwealth.wallet.mobile.index.server.dto.WjActivityInfoDTO;
import com.zhuanyi.vjwealth.wallet.mobile.index.server.mapper.IWjActivityInfoMapper;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.IProductQueryService;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.mapper.IUserAccountQueryMapper;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.mapper.IHomeMapper;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.service.IContentService;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.service.IHomeService;
import com.zhuanyi.vjwealth.wallet.mobile.user.server.service.IUserOperationService;
@Service
public class HomeService implements IHomeService {

	@Autowired
	private IHomeMapper homeMapper;

	@Autowired
	private IUserOperationService userOperationService;
	@Autowired
	private TAccountQueryMapper tAccountQueryMapper;
	@Autowired
	private IUserAccountQueryMapper userAccountQueryMapper;

	@Autowired
	private IWjActivityInfoMapper wjActivityInfoMapper;

	@Autowired
	private IProductQueryService productQueryService;

	@Autowired
	private IWeixinHomeMapper weixinHomeMapper;

	@Autowired
	private IAdvertisementMapper advertisementMapper;

	@Value("${news.url}")
	private String newsUrl;

	@Autowired
	private ICommConfigsQueryService commConfigsQueryService;
	@Autowired
	private IContentService contentService;
	@Override
	public Map<String, Object> queryHomeInit(String channelType) {

		return queryV38Index(channelType);
	}

	@Override
	public Map<String, Object> queryAccumulateInit() {
		Map<String,Object> returnMap=new HashMap<String,Object>();
//		
		List<Map<String,Object>> accumulateList=new ArrayList<Map<String,Object>>();
//		//定期理财
//		Map<String,Object> financeMap=homeMapper.queryYearFinances();
//		//countList
//		List<Map<String,String>> financesCountList=new ArrayList<Map<String,String>>();
//		financesCountList.add(homeMapper.queryFinancesToday());
//		financesCountList.add(homeMapper.queryFinancesYesterday());
//		financesCountList.add(homeMapper.queryFinancesLastWeek());
//		financesCountList.add(homeMapper.queryFinancesLastMonth());
//		financeMap.put("countList", financesCountList);
//		accumulateList.add(financeMap);
//		//贷款
//		Map<String,Object> loanMap=homeMapper.queryYearLoan();
//		//countList
//		List<Map<String,String>> loanCountList=new ArrayList<Map<String,String>>();
//		loanCountList.add(homeMapper.queryLoanToday());
//		loanCountList.add(homeMapper.queryLoanYesterday());
//		loanCountList.add(homeMapper.queryLoanLastWeek());
//		loanCountList.add(homeMapper.queryLoanLastMonth());
//		loanMap.put("countList", loanCountList);
//		accumulateList.add(loanMap);
		//累计
		Map<String,Object> accumulate=homeMapper.queryAccumulate();
		//countList
		accumulate.put("countList", homeMapper.queryAccumulateList());
		accumulateList.add(accumulate);
		returnMap.put("accumulateList", accumulateList);
		return returnMap;
	}

	public Map<String, Object> queryV38Index(String channelType) {
		Map<String, Object> returnMap = new HashMap<String, Object>();

		Map<String, String> commConfigMap = commConfigsQueryService.queryConfigKeyByGroup(BizCommonConstant.CONFIG_GROUP_WALLET_MOBILE);
//		returnMap.put("aboutZhonganH5Url", commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) + "/api/v3.6/app/index/aboutZhongan");
//
//		returnMap.put("topTitle", "融桥宝");
//		returnMap.put("topZhonganIcon", commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) + "/static/index/zhongan.png");
//		List<KeyValueDTO> salarySocialSecurityList = new ArrayList<KeyValueDTO>() {
//		};
//		salarySocialSecurityList.add(new KeyValueDTO("204900", "查工资单", commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) + "/static/index/salary_bill_sd.png"));
//		salarySocialSecurityList.add(new KeyValueDTO("204901", "查社保", commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) + "/static/index/social_security_sd.png"));
//		salarySocialSecurityList.add(new KeyValueDTO("204902", "查公积金", commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) + "/static/index/housing_fund_sd.png"));
//		// salarySocialSecurityList.add(new KeyValueDTO("204903", "工资定存",
//		// commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) +
//		// "/static/index/salary_financing_sd.png"));
//		returnMap.put("salarySocialSecurity", salarySocialSecurityList);

		// 设置贷款活动区信息
		Map loanConfigMap = commConfigsQueryService.queryConfigKeyByGroupAndKey("house_fund_loan", null);
		IndexLoanAreaDTO loanAreaDTO = packIndexLoanArea(commConfigMap, loanConfigMap, channelType);
		loanAreaDTO.setNewIcon(commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) + "/static/index/new_common.png");
//		returnMap.put("loanActivity", loanAreaDTO);

		returnMap.put("latestActivityLabel", "最新活动");
		returnMap.put("latestActivityIcon", commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) + "/static/index/latest_activity.png");
		returnMap.put("latestActivityList", queryHomeActivityList(commConfigMap, HomeAPIVersionEnum.V38.getVersion()));

		returnMap.put("latestDynamicLabel", "最新动态");
		returnMap.put("latestDynamicIcon", commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) + "/static/index/latest_dynamic.png");
//		// 最新动态（获取最新3条）
		returnMap.put("latestDynamicList", homeMapper.queryLatestDynamicHomeList());

		// 设置理财区域信息
		Map financialConfigMap = commConfigsQueryService.queryConfigKeyByGroupAndKey("financial", "financial_new_hand_icon");
		IndexFinancialAreaDTO financialAreaDTO = packIndexFinancialArea(commConfigMap, financialConfigMap, channelType);
		if (financialAreaDTO.getNewHandIcon() != null) {
			financialAreaDTO.setNewHandIcon(commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) + "/static/index/new_hand_common.png");
		}
		Map<String, String> financialAreaMap=new HashMap<String,String>();
		financialAreaMap.put("grennProductId", financialAreaDTO.getProductId());
		returnMap.put("financialArea", financialAreaMap);
		//queryAccumulateTotal
//		returnMap.put("accumulateAmt", homeMapper.queryAccumulateTotal());
		
		// 设置hommeBanner
		returnMap.put("homeBannerList", homeMapper.queryHomeBannerList());
		// 设置起投金额
		returnMap.put("greenStartMoney", commConfigMap.get(BizCommonConstant.GREEN_START_MONEY));
		returnMap.put("jiaxiStartMoney", commConfigMap.get(BizCommonConstant.JIAXI_START_MONEY));

		return returnMap;
	}

	private IndexFinancialAreaDTO packIndexFinancialArea(Map<String, String> commConfigMap, Map<String, String> financialConfigMap, String channelType) {
		IndexFinancialAreaDTO financialAreaDTO = new IndexFinancialAreaDTO();
		financialAreaDTO.setFinancialLabel("定期理财");
		if (UserChannelTypeEnum.WEIXIN.getCode().equals(channelType)) {
			financialAreaDTO.setFinancialTipIcon("百元起投");
		} else {
			financialAreaDTO.setFinancialTipIcon(commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) + "/static/index/financial_tip.png");
		}

		financialAreaDTO.setFinancialIcon(commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) + "/static/index/financial.png");
		Map productMap = userAccountQueryMapper.queryNewHandFinancialReceiveRate();
		financialAreaDTO.setRate(formatRate(productMap.get("rate") + ""));
		financialAreaDTO.setDesc("年化收益");
		financialAreaDTO.setNewHandIcon(productMap.get("productFlag").equals("greenhorn") ? commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) + financialConfigMap.get("financial_new_hand_icon") : null);
		financialAreaDTO.setButtonText("去理财");
		financialAreaDTO.setCode("204904");
		financialAreaDTO.setProductId((String) productMap.get("productId"));
		financialAreaDTO.setProductName((String) productMap.get("productName"));
//		if (UserChannelTypeEnum.WEIXIN.getCode().equals(channelType)) {
//		}
		return financialAreaDTO;
	}

	private String formatRate(String rate) {
		if (rate.indexOf(".") > 0) {
			// 正则表达
			rate = rate.replaceAll("0+?$", "");// 去掉后面无用的零
			rate = rate.replaceAll("[.]$", "");// 如小数点后面全是零则去掉小数点
		}
		return rate;
	}

	private JSONArray getTop3LatestDynamic() {
		JSONArray jsonArray = com.alibaba.fastjson.JSON.parseArray(com.alibaba.fastjson.JSON.toJSONString(((Map) queryDynamicList(null, null, "0")).get("records")));
		JSONArray top3JsonArray = new JSONArray();
		if (jsonArray != null && jsonArray.size() > 0) {
			for (int i = 0; i < 3 && i < jsonArray.size(); i++) {
				Object object = jsonArray.get(i);
				if (object != null) {
					top3JsonArray.add(object);
				}
			}
		}
		return top3JsonArray;
	}

	/**
	 * 4.2动态列表
	 * 
	 * @param userId
	 * @return
	 * @since 3.6
	 */
	public Object queryDynamicList(String userId, String uuid, String page) {
		String url = newsUrl;
		if (Integer.parseInt(page) > 1) {
			url = MessageFormat.format(url, "_" + page);
		} else {
			url = MessageFormat.format(url, "");
		}
		JSONObject infoJsonObject = JSONObject.parseObject(HttpClientUtil.sendGetRequest(url));
		Map<String, Object> map = JSONObject.toJavaObject(infoJsonObject, Map.class);
		return map.get("body");
	}

	private List<WjActivityInfoDTO> queryHomeActivityList(Map<String, String> commConfigMap, String version) {
		List<WjActivityInfoDTO> records = wjActivityInfoMapper.queryAppHomeActivityList(version);
		String productId = wjActivityInfoMapper.queryNewestProductFinace();
		List<WjActivityInfoDTO> recordsList = new ArrayList<WjActivityInfoDTO>();
		for (WjActivityInfoDTO dto : records) {
			if ("F0003".equals(dto.getFunctionCode())) {
				if (StringUtils.isEmpty(productId)) {
					continue;
				}
				dto.setParamsStr(MessageFormat.format(dto.getParamsStr(), productId));
			} else if ("F0000".equals(dto.getFunctionCode())) {
				dto.setParamsStr(MessageFormat.format(dto.getParamsStr(), commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL)));
			} else if ("linkUrl".equals(dto.getFunctionType())) {
				dto.setDetailUrl(MessageFormat.format(dto.getDetailUrl(), commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL)));
			}
			if (!StringUtils.isEmpty(dto.getParamsStr())) {
				try {
					dto.setParams(JSON.parse(MessageFormat.format("{0}{1}{2}", "{", dto.getParamsStr(), "}"), Map.class));
				} catch (Exception ex) {
					BaseLogger.error(ex);
				}
			}
//			if (!StringUtils.isEmpty(dto.getSmallPic())) {
//				dto.setSmallPic(MessageFormat.format(dto.getSmallPic(), commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL)));
//			}
			recordsList.add(dto);
		}
		return recordsList;
	}

	private IndexLoanAreaDTO packIndexLoanArea(Map<String, String> commConfigMap, Map<String, String> loanConfigMap, String channelType) {
		IndexLoanAreaDTO loanAreaDTO = new IndexLoanAreaDTO();
		loanAreaDTO.setLoanProductName("公积金贷");
		if (UserChannelTypeEnum.WEIXIN.getCode().equals(channelType)) {
			loanAreaDTO.setMaxLoanAmountIcon("最高50000元");
		} else {
			loanAreaDTO.setMaxLoanAmountIcon(commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) + "/static/index/max_loan_amount.png");
		}

		loanAreaDTO.setLoanMinRate(loanConfigMap.get("house_fund_loan_day_rate"));
		loanAreaDTO.setLoanPeriod("借款周期" + loanConfigMap.get("house_fund_loan_period") + "期");
		loanAreaDTO.setButtonText("去贷款");
		loanAreaDTO.setLoanActivityIcon(commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) + "/static/index/loan_activity.png");

		return loanAreaDTO;
	}

	@Override
	public Map<String, Object> queryLatestDynamicList(String page) {
		Map<String,Object> returnMap=new HashMap<String,Object>();
		//1.	返回列表
		List<Map<String,Object>> latestDynamicList =homeMapper.queryLatestDynamicList((Integer.parseInt(page) - 1) * 10);
		//	2. 包装分页信息
		returnMap.put("records", latestDynamicList);
		returnMap.put("isMore", false);
		if (latestDynamicList != null && latestDynamicList.size() >= 10) {
			returnMap.put("isMore", true);
		}
		return returnMap;
	}
	
	@Override
	public Map<String, Object> queryHistory() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Map<String, Object> queryReportList(String page) {
		Map<String,Object> returnMap=new HashMap<String,Object>();
		//1.	返回列表
		List<Map<String,Object>> latestDynamicList =homeMapper.queryReportList((Integer.parseInt(page) - 1) * 10);
		//	2. 包装分页信息
		returnMap.put("reportList", latestDynamicList);
		returnMap.put("isMore", false);
		if (latestDynamicList != null && latestDynamicList.size() >= 10) {
			returnMap.put("isMore", true);
		}
		return returnMap;
	}
	
	@Override
	public String queryReportDetail(String reportId) {
		
		return homeMapper.queryReportDetail(reportId);
	}
	
	@Override
	public Object getPageData(String userId) {

		//borrow=HJTYB;1,ACTIVITY;2,ORDINARY;3
        //新手
		Map<String,Object> ORDINARY = new HashMap<String,Object>();
		ORDINARY =	productQueryService.queryHomeNewPersonProductList(userId,"","");
		//推荐区取普通标的前两个
		Map<String,Object> ACTIVITY = new HashMap<String,Object>();
		ACTIVITY = productQueryService.queryCommonProductList(userId,"","");

		Map<String,Object> Borrow = new HashMap<String,Object>();
		Borrow.put("ORDINARY",ORDINARY);
		Borrow.put("ACTIVITY",ACTIVITY);


       //pic&userNum&video=4&webnotice=4
		List<Map<String,Object>> pic = new ArrayList<>();
		pic = contentService.queryList("1","15",null);

		List<Map<String,Object>> video = new ArrayList<>();
		video =contentService.queryList("1","27",null);

		List<Map<String,Object>> webnotice = new ArrayList<>();
		webnotice = contentService.queryList("1","20",null);

		Map<String,Object> PageDataDTO = new HashMap<String,Object>();
		PageDataDTO.put("borrow",Borrow);
		PageDataDTO.put("userNum","1240559"); //TODO
		PageDataDTO.put("totalMoney","6837816700.83");  //TODO

		PageDataDTO.put("pic",pic);
		PageDataDTO.put("video",video);
		PageDataDTO.put("webnotice",webnotice);

		return PageDataDTO;
	}

	@Override
	public Map<String, Object> getHomeStatistics() {

		Map<String,Object> platformTradeData = new HashMap<String,Object>();
		platformTradeData.put("endDate","2017-08-29");
		platformTradeData.put("tenderUserCount","1240559"); //TODO
		platformTradeData.put("totalInterestAmount","133802535.41"); //TODO
		platformTradeData.put("totalTenderAmount","6837816700.83");  //TODO
		platformTradeData.put("totalTenderCount","479701");  //TODO
		return platformTradeData;
	}


}
