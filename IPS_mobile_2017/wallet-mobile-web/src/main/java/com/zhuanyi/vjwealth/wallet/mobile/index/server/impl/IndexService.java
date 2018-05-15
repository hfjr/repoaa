package com.zhuanyi.vjwealth.wallet.mobile.index.server.impl;

import java.text.DecimalFormat;
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
import com.fab.core.exception.service.AppException;
import com.fab.core.logger.BaseLogger;
import com.zhuanyi.vjwealth.wallet.mobile.account.server.mapper.TAccountQueryMapper;
import com.zhuanyi.vjwealth.wallet.mobile.common.dto.AdvertisementBannerDTO;
import com.zhuanyi.vjwealth.wallet.mobile.common.dto.UserChannelTypeEnum;
import com.zhuanyi.vjwealth.wallet.mobile.common.server.mapper.IAdvertisementMapper;
import com.zhuanyi.vjwealth.wallet.mobile.common.server.mapper.IWeixinHomeMapper;
import com.zhuanyi.vjwealth.wallet.mobile.common.server.service.ICommConfigsQueryService;
import com.zhuanyi.vjwealth.wallet.mobile.common.util.BizCommonConstant;
import com.zhuanyi.vjwealth.wallet.mobile.index.server.IIndexService;
import com.zhuanyi.vjwealth.wallet.mobile.index.server.dto.HomeAPIVersionEnum;
import com.zhuanyi.vjwealth.wallet.mobile.index.server.dto.IndexFinancialAreaDTO;
import com.zhuanyi.vjwealth.wallet.mobile.index.server.dto.IndexLoanAreaDTO;
import com.zhuanyi.vjwealth.wallet.mobile.index.server.dto.IndexSalaryPlanAreaDTO;
import com.zhuanyi.vjwealth.wallet.mobile.index.server.dto.KeyValueDTO;
import com.zhuanyi.vjwealth.wallet.mobile.index.server.dto.LoanActivityDTO;
import com.zhuanyi.vjwealth.wallet.mobile.index.server.dto.MyAssetsDTO;
import com.zhuanyi.vjwealth.wallet.mobile.index.server.dto.WjActivityInfoDTO;
import com.zhuanyi.vjwealth.wallet.mobile.index.server.mapper.IWjActivityInfoMapper;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.IProductQueryService;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.mapper.IUserAccountQueryMapper;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.mapper.IHomeMapper;
import com.zhuanyi.vjwealth.wallet.mobile.user.server.service.IUserOperationService;

@Service
public class IndexService implements IIndexService {
	@Autowired
	private IUserOperationService userOperationService;
	@Autowired
	private ICommConfigsQueryService commConfigsQueryService;
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
	
	@Autowired
	private IHomeMapper homeMapper;

	@Value("${news.url}")
	private String newsUrl;

	/**
	 * 1.首页初始化界面
	 * 
	 * @param userId
	 * @return
	 * @since 3.6
	 */
	public Map<String, Object> queryIndex(String userId, String uuid) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("availableBalanceLabel", "可用余额(元)");
		returnMap.put("subTitle", "让工资涨起来，赶快充值");
		Map<String, String> commConfigMap = commConfigsQueryService.queryConfigKeyByGroup(BizCommonConstant.CONFIG_GROUP_WALLET_MOBILE);
		returnMap.put("aboutCoopH5Url", commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) + "/api/v3.6/app/index/aboutCoop");
		returnMap.put("aboutZhonganH5Url", commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) + "/api/v3.6/app/index/aboutZhongan");

		returnMap.put("salarySocialSecurityLabel", "工资社保查询");
		returnMap.put("salarySocialSecurityTips", "融桥宝科企云提供");
		List<KeyValueDTO> salarySocialSecuritys = new ArrayList<KeyValueDTO>() {
		};
		salarySocialSecuritys.add(new KeyValueDTO("204900", "查工资单", commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) + "/static/index/salary_bill.png"));
		salarySocialSecuritys.add(new KeyValueDTO("204901", "查社保", commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) + "/static/index/social_security.png"));
		salarySocialSecuritys.add(new KeyValueDTO("204902", "查公积金", commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) + "/static/index/housing_fund.png"));
		returnMap.put("salarySocialSecurity", salarySocialSecuritys);

		returnMap.put("salaryFinancingLabel", "工资理财");
		List<KeyValueDTO> salaryFinancing = new ArrayList<KeyValueDTO>() {
		};
		salaryFinancing.add(new KeyValueDTO("204903", "<font color='#FFBD30'>工资计划</font> <font color='#B8CDD6'>|</font> <font color='#5D7A8D'>定存工资</font>", commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) + "/static/index/salary_plan.png", "攒够一笔钱 完成一个愿望"));
		salaryFinancing.add(new KeyValueDTO("204904", "<font color='#FFBD30'>增值计划</font> <font color='#B8CDD6'>|</font> <font color='#5D7A8D'>定期理财</font>", commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) + "/static/index/salary_financing.png", "保险公司提供100%本息保障"));
		returnMap.put("salaryFinancing", salaryFinancing);

		returnMap.put("currentFinancingLabel", "活期理财");
		returnMap.put("currentFinancingTips", "实时提现");
		List<KeyValueDTO> currentFinancing = new ArrayList<KeyValueDTO>() {
		};
		currentFinancing.add(new KeyValueDTO("204907", "存钱罐", commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) + "/static/index/current_financing.png", MessageFormat.format("<font color='#B8CDD6'>收益</font><font color='#FFBD30'>{0}%</font><font color='#B8CDD6'>|</font><font color='#FFBD30'>100元</font><font color='#B8CDD6'>起投</font>", userAccountQueryMapper.queryTaReceiveRate()), "去存钱"));
		returnMap.put("currentFinancing", currentFinancing);

		returnMap.put("activityDomainLabel", "活动专区");
		List<KeyValueDTO> activityDomain = new ArrayList<KeyValueDTO>() {
		};
		activityDomain.add(new KeyValueDTO("204905", "新手专享", commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) + "/static/index/new_hand.png", "专享标、红包、福利多多"));
		activityDomain.add(new KeyValueDTO("204906", "推荐好友", commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) + "/static/index/recommend_friend.png", "推荐好友送万元理财金"));
		returnMap.put("activityDomain", activityDomain);

		returnMap.put("showType", commConfigMap.get(BizCommonConstant.CONFIG_ITEM_SHOW_TYPE));// 显示交易额:dualAmount:显示服务人数:user
		returnMap.put("sumDualAmountLabel", "融桥宝累计交易额(元)");
		returnMap.put("sumDualAmount", commConfigMap.get(BizCommonConstant.CONFIG_ITEM_SUM_DUAL_AMOUNT));

		returnMap.put("sumUserLabel", "融桥宝累计服务人数(人)");
		returnMap.put("sumUser", userAccountQueryMapper.queryCountUserNum());
		returnMap.put("isCanQuerySocialSecurity", "false");
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(uuid) || !userOperationService.validatorUserIdAndUuid(userId, uuid)) {
			returnMap.put("code", "204801");
			returnMap.put("message", "未登录用户");
			returnMap.put("availableBalance", "");
		} else {
			returnMap.put("code", "204800");
			returnMap.put("message", "已登录用户");
			returnMap.put("availableBalance", tAccountQueryMapper.queryTAccountCanUseAmountV36(userId));
			String userStatus = null;// humanService.userQuery(userId);
			returnMap.put("isCanQuerySocialSecurity", userStatus);
		}

		// 设置贷款活动区信息
		returnMap.put("loanActivity", buildLoanActivity());

		return returnMap;
	}

	private LoanActivityDTO buildLoanActivity() {
		LoanActivityDTO lad = new LoanActivityDTO();
		lad.setLoanProductName("公积金贷");
		lad.setConditionDesc("仅需公积金信息");
		lad.setDetailDesc("秒级审批 极速放款");
		lad.setLoanAmount("500~50000");
		lad.setLoanMinRate("0.035%");
		lad.setLoanRateDesc("最低费率");
		return lad;
	}

	/**
	 * 4.1最新活动
	 * 
	 * @param userId
	 * @return
	 * @since 3.6
	 */
	@Override
	public Object queryActivityList(String userId, String uuid, String page) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Integer currentPage = (Integer.parseInt(page) - 1) * 10;
		List<WjActivityInfoDTO> records = wjActivityInfoMapper.queryActivityList(currentPage);
		String productId = wjActivityInfoMapper.queryNewestProductFinace();
		Map<String, String> commConfigMap = commConfigsQueryService.queryConfigKeyByGroup(BizCommonConstant.CONFIG_GROUP_WALLET_MOBILE);
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
			if (!StringUtils.isEmpty(dto.getSmallPic())) {
				dto.setSmallPic(MessageFormat.format(dto.getSmallPic(), commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL)));
			}
			recordsList.add(dto);
		}
		resultMap.put("records", recordsList);
		resultMap.put("isMore", "false");
		if (recordsList != null && recordsList.size() == 10) {
			resultMap.put("isMore", "true");
		}
		return resultMap;
	}

	/**
	 * 4.2动态列表
	 * 
	 * @param userId
	 * @return
	 * @since 3.6
	 */
	@Override
	public Map<String,Object> queryDynamicList(String userId, String uuid, String page) {
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
	public Map<String, Object> querySdIndex(String channelType) {
		Map<String, Object> returnMap = new HashMap<String, Object>();

		Map<String, String> commConfigMap = commConfigsQueryService.queryConfigKeyByGroup(BizCommonConstant.CONFIG_GROUP_WALLET_MOBILE);
		returnMap.put("aboutZhonganH5Url", commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) + "/api/v3.6/app/index/aboutZhongan");

		returnMap.put("topTitle", "融桥宝");
		returnMap.put("topZhonganIcon", commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) + "/static/index/zhongan.png");
		List<KeyValueDTO> salarySocialSecuritys = new ArrayList<KeyValueDTO>() {
		};
		salarySocialSecuritys.add(new KeyValueDTO("204900", "查工资单", commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) + "/static/index/salary_bill_sd.png"));
		salarySocialSecuritys.add(new KeyValueDTO("204901", "查社保", commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) + "/static/index/social_security_sd.png"));
		salarySocialSecuritys.add(new KeyValueDTO("204902", "查公积金", commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) + "/static/index/housing_fund_sd.png"));
		salarySocialSecuritys.add(new KeyValueDTO("204903", "工资定存", commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) + "/static/index/salary_financing_sd.png"));
		returnMap.put("salarySocialSecurity", salarySocialSecuritys);

		// 设置贷款活动区信息
		Map loanConfigMap = commConfigsQueryService.queryConfigKeyByGroupAndKey("house_fund_loan", null);
		IndexLoanAreaDTO loanAreaDTO = packIndexLoanArea(commConfigMap, loanConfigMap, channelType);
		if ("yes".equals(loanConfigMap.get("house_fund_loan_is_new"))) {// 是否显示new
																		// icon
			loanAreaDTO.setNewIcon(commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) + "/static/index/new_festival.png");
		}
		returnMap.put("loanActivity", loanAreaDTO);

		returnMap.put("latestActivityLabel", "最新活动");
		returnMap.put("latestActivityIcon", commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) + "/static/index/latest_activity.png");
		returnMap.put("latestActivityList", queryHomeActivityList(commConfigMap, HomeAPIVersionEnum.V37.getVersion()));

		returnMap.put("latestDynamicLabel", "最新动态");
		// 最新动态（获取最新3条）
		returnMap.put("latestDynamicIcon", commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) + "/static/index/latest_dynamic.png");
		// 最新动态（获取最新3条）
		returnMap.put("latestDynamicList", getTop3LatestDynamic());

		// 设置理财区域信息
		Map financialConfigMap = commConfigsQueryService.queryConfigKeyByGroupAndKey("financial", "financial_new_hand_icon");
		IndexFinancialAreaDTO financialAreaDTO = packIndexFinancialArea(commConfigMap, financialConfigMap, channelType);
		if (financialAreaDTO.getNewHandIcon() != null) {
			financialAreaDTO.setNewHandIcon(commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) + "/static/index/financial_new_hand_festival.png");
		}
		returnMap.put("financialArea", financialAreaDTO);

		Map salaryPlanMap = new HashMap();
		salaryPlanMap.put("salaryPlanLabel", "工资计划");
		salaryPlanMap.put("salaryPlanTipCon", commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) + "/static/index/salary_plan_tip.png");
		salaryPlanMap.put("salaryPlanIcon", commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) + "/static/index/salary_plan_sd.png");
		salaryPlanMap.put("rate", userAccountQueryMapper.queryTaReceiveRate());
		salaryPlanMap.put("desc", "近7日年化收益");
		salaryPlanMap.put("buttonText", "去定制");

		returnMap.put("salaryPlanArea", salaryPlanMap);
		return returnMap;
	}

	private String formatRate(String rate) {
		if (rate.indexOf(".") > 0) {
			// 正则表达
			rate = rate.replaceAll("0+?$", "");// 去掉后面无用的零
			rate = rate.replaceAll("[.]$", "");// 如小数点后面全是零则去掉小数点
		}
		return rate;
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
			if (!StringUtils.isEmpty(dto.getSmallPic())) {
				dto.setSmallPic(MessageFormat.format(dto.getSmallPic(), commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL)));
			}
			recordsList.add(dto);
		}
		return recordsList;
	}

	@Override
	public Map queryMyAssets(String userId) {
		Map paramMap = commConfigsQueryService.queryConfigKeyByGroup("wallet_assets");
		Map returnMap = new HashMap();
		List<MyAssetsDTO> myAssetsDTOList = userAccountQueryMapper.queryMyAssets(userId);
		DecimalFormat df = new DecimalFormat("#######0.00");
		Double totalAmount = 0d;
		for (MyAssetsDTO dto : myAssetsDTOList) {
			totalAmount += Double.parseDouble(dto.getAmount());
			dto.setIcon(paramMap.get(dto.getIcon()) + "");
			dto.setColor(paramMap.get(dto.getColor()) + "");
		}
		returnMap.put("totalAmount", df.format(totalAmount));
		returnMap.put("items", myAssetsDTOList);
		return returnMap;
	}

	@Override
	public Map<String, Object> queryHumanService(String userId) {
		Map returnMap = new HashMap();
		String userStatus = null;// humanService.userQuery(userId);
		returnMap.put("isCanQuerySocialSecurity", userStatus);
		return returnMap;
	}

	@Override
	public Map queryProductListV37(String userId, String uuid, String page, String userChannelType) {
		if (!StringUtils.isBlank(userId) && !StringUtils.isBlank(uuid)) {
			if (!userOperationService.validatorUserIdAndUuid(userId, uuid)) {
				throw new AppException(610, "请重新登录");
			}
		}
		Map returnMap = null;//productQueryService.queryProductListV6(userId, uuid, page, null);

		Map<String, String> commConfigMap = commConfigsQueryService.queryConfigKeyByGroup(BizCommonConstant.CONFIG_GROUP_WALLET_MOBILE);
		Map<String, String> currentMap = new HashMap<>();
		currentMap.put("currentIcon", commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) + "/static/icon/current_financing.png");
		currentMap.put("productName", "存钱罐");
		currentMap.put("investmentType", "活期");
		currentMap.put("startInvestmentDesc", "起投金额");
		currentMap.put("startInvestmentMoney", "100");
		currentMap.put("yearRateDesc", "年化收益");
		currentMap.put("yearRate", userAccountQueryMapper.queryTaReceiveRate() + "%");
		currentMap.put("code", "204907");

		// if (("weixin").equals(userChannelType) && StringUtils.isBlank(userId)
		// && StringUtils.isBlank(uuid)) {//微信端未登录时理财首页屏蔽存钱罐模块
		// return returnMap;
		// }
		// if (StringUtils.isBlank(userId) && StringUtils.isBlank(uuid))
		// {//APP未登陆时需要返回存钱罐模块（兼容老版本）
		// returnMap.put("currentFinancing", currentMap);
		// }

		// if (!StringUtils.isBlank(userId) && !StringUtils.isBlank(uuid) &&
		// userAccountQueryMapper.queryUserHasTaAmount(userId)) {
		// returnMap.put("currentFinancing", currentMap);
		// }

		return returnMap;
	}

	@Override
	public Map<String, Object> queryWeiXinHome() {
		Map<String, Object> returnMap = new HashMap<String, Object>();

		returnMap.put("bannerList", weixinHomeMapper.bannerQueryList());

		Map<String, String> commConfigMap = commConfigsQueryService.queryConfigKeyByGroup(BizCommonConstant.CONFIG_GROUP_WALLET_MOBILE);

		// 设置贷款活动区信息
		Map loanConfigMap = commConfigsQueryService.queryConfigKeyByGroupAndKey("house_fund_loan", null);
		IndexLoanAreaDTO loanAreaDTO = packIndexLoanArea(commConfigMap, loanConfigMap, UserChannelTypeEnum.WEIXIN.getCode());
		// loanAreaDTO.setNewIcon(commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL)
		// + "/static/index/new_common.png");
		String newIconUrl = "";
		if ("yes".equals(loanConfigMap.get("house_fund_loan_is_new"))) {
			newIconUrl = commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) + loanConfigMap.get("house_fund_loan_new_icon");
		}
		loanAreaDTO.setNewIcon(newIconUrl);
		returnMap.put("loanActivity", loanAreaDTO);

		returnMap.put("latestActivityLabel", "最新活动");
		returnMap.put("latestActivityIcon", commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) + "/static/index/latest_activity.png");
		returnMap.put("latestActivityList", queryWeiXinHomeActivityList(commConfigMap));

		returnMap.put("latestDynamicLabel", "最新动态");
		// 最新动态（获取最新3条）
		returnMap.put("latestDynamicIcon", commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) + "/static/index/latest_dynamic.png");
		// 最新动态（获取最新3条）
		returnMap.put("latestDynamicList", getTop3LatestDynamic());

		// 设置理财区域信息
		Map financialConfigMap = commConfigsQueryService.queryConfigKeyByGroupAndKey("financial", "financial_new_hand_icon");
		IndexFinancialAreaDTO financialAreaDTO = packIndexFinancialArea(commConfigMap, financialConfigMap, UserChannelTypeEnum.WEIXIN.getCode());
		// if (financialAreaDTO.getNewHandIcon() != null) {
		// financialAreaDTO.setNewHandIcon(commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL)
		// + "/static/index/new_hand_common.png");
		// }
		returnMap.put("financialArea", financialAreaDTO);

		Map salaryPlanMap = new HashMap();
		salaryPlanMap.put("salaryPlanLabel", "工资计划");
		salaryPlanMap.put("salaryPlanTipCon", "定存工资");
		salaryPlanMap.put("salaryPlanIcon", commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) + "/static/index/salary_plan_sd.png");
		salaryPlanMap.put("rate", userAccountQueryMapper.queryTaReceiveRate());
		salaryPlanMap.put("desc", "近7日年化收益");
		salaryPlanMap.put("buttonText", "去定制");

		returnMap.put("salaryPlanArea", salaryPlanMap);
		return returnMap;
	}

	private List<WjActivityInfoDTO> queryWeiXinHomeActivityList(Map<String, String> commConfigMap) {
		List<WjActivityInfoDTO> records = wjActivityInfoMapper.queryAppHomeActivityList(HomeAPIVersionEnum.V37.getVersion());
		List<WjActivityInfoDTO> recordsList = new ArrayList<WjActivityInfoDTO>();
		for (WjActivityInfoDTO dto : records) {
			if (!StringUtils.isBlank(dto.getWeixinPicUrl())) {
				dto.setSmallPic(MessageFormat.format(dto.getWeixinPicUrl(), commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL)));
			}
			if (!StringUtils.isBlank(dto.getWeixinLinkUrl())) {
				dto.setDetailUrl(dto.getWeixinLinkUrl());
			}
			dto.setSmallPic(MessageFormat.format(dto.getSmallPic(), commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL)));
			dto.setDetailUrl(MessageFormat.format(dto.getDetailUrl(), commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL)));
			recordsList.add(dto);
		}
		return recordsList;
	}

	@Override
	public List<AdvertisementBannerDTO> queryV2AdvertisementBannerInfo() {
		return advertisementMapper.queryV3AdvertisementBannerInfo();
	}

	@Override
	public Map<String, Object> queryV38Index(String channelType) {
		Map<String, Object> returnMap = new HashMap<String, Object>();

		Map<String, String> commConfigMap = commConfigsQueryService.queryConfigKeyByGroup(BizCommonConstant.CONFIG_GROUP_WALLET_MOBILE);
		returnMap.put("aboutZhonganH5Url", commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) + "/api/v3.6/app/index/aboutZhongan");

		returnMap.put("topTitle", "融桥宝");
		returnMap.put("topZhonganIcon", commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) + "/static/index/zhongan.png");
		List<KeyValueDTO> salarySocialSecurityList = new ArrayList<KeyValueDTO>() {
		};
		salarySocialSecurityList.add(new KeyValueDTO("204900", "查工资单", commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) + "/static/index/salary_bill_sd.png"));
		salarySocialSecurityList.add(new KeyValueDTO("204901", "查社保", commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) + "/static/index/social_security_sd.png"));
		salarySocialSecurityList.add(new KeyValueDTO("204902", "查公积金", commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) + "/static/index/housing_fund_sd.png"));
		// salarySocialSecurityList.add(new KeyValueDTO("204903", "工资定存",
		// commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) +
		// "/static/index/salary_financing_sd.png"));
		returnMap.put("salarySocialSecurity", salarySocialSecurityList);

		// 设置贷款活动区信息
		Map loanConfigMap = commConfigsQueryService.queryConfigKeyByGroupAndKey("house_fund_loan", null);
		IndexLoanAreaDTO loanAreaDTO = packIndexLoanArea(commConfigMap, loanConfigMap, channelType);
		loanAreaDTO.setNewIcon(commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) + "/static/index/new_common.png");
		returnMap.put("loanActivity", loanAreaDTO);

		returnMap.put("latestActivityLabel", "最新活动");
		returnMap.put("latestActivityIcon", commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) + "/static/index/latest_activity.png");
		returnMap.put("latestActivityList", queryHomeActivityList(commConfigMap, HomeAPIVersionEnum.V38.getVersion()));

		returnMap.put("latestDynamicLabel", "最新动态");
		returnMap.put("latestDynamicIcon", commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) + "/static/index/latest_dynamic.png");
		// 最新动态（获取最新3条）
		returnMap.put("latestDynamicList", getTop3LatestDynamic());

		// 设置理财区域信息
		Map financialConfigMap = commConfigsQueryService.queryConfigKeyByGroupAndKey("financial", "financial_new_hand_icon");
		IndexFinancialAreaDTO financialAreaDTO = packIndexFinancialArea(commConfigMap, financialConfigMap, channelType);
		if (financialAreaDTO.getNewHandIcon() != null) {
			financialAreaDTO.setNewHandIcon(commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) + "/static/index/new_hand_common.png");
		}
		returnMap.put("financialArea", financialAreaDTO);

		// returnMap.put("salaryPlanArea",
		// packIndexSalaryPlanArea(commConfigMap, channelType));

		return returnMap;
	}

	private IndexLoanAreaDTO packIndexLoanArea(Map<String, String> commConfigMap, Map<String, String> loanConfigMap, String channelType) {
		IndexLoanAreaDTO loanAreaDTO = new IndexLoanAreaDTO();
		loanAreaDTO.setLoanProductName("公积金贷");
		if (UserChannelTypeEnum.WEIXIN.getCode().equals(channelType)) {
			loanAreaDTO.setMaxLoanAmountIcon("最高50000元");
		} else {
			loanAreaDTO.setMaxLoanAmountIcon(commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) + "/static/index/max_loan_amount.png");
		}

		// String newIconUrl = "";
		// if ("yes".equals(loanConfigMap.get("house_fund_loan_is_new"))) {
		// newIconUrl =
		// commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) +
		// loanConfigMap.get("house_fund_loan_new_icon");
		// }

		// loanAreaDTO.setNewIcon(newIconUrl);
		loanAreaDTO.setLoanMinRate(loanConfigMap.get("house_fund_loan_day_rate"));
		loanAreaDTO.setLoanPeriod("借款周期" + loanConfigMap.get("house_fund_loan_period") + "期");
		loanAreaDTO.setButtonText("去贷款");
		loanAreaDTO.setLoanActivityIcon(commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) + "/static/index/loan_activity.png");

		return loanAreaDTO;
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

		if (UserChannelTypeEnum.WEIXIN.getCode().equals(channelType)) {
			financialAreaDTO.setProductName((String) productMap.get("productName"));
		}
		return financialAreaDTO;
	}

	private IndexSalaryPlanAreaDTO packIndexSalaryPlanArea(Map<String, String> commConfigMap, String channelType) {
		IndexSalaryPlanAreaDTO salaryPlanAreaDTO = new IndexSalaryPlanAreaDTO();
		salaryPlanAreaDTO.setSalaryPlanLabel("工资计划");
		if (UserChannelTypeEnum.WEIXIN.getCode().equals(channelType)) {
			salaryPlanAreaDTO.setSalaryPlanTipCon("定存工资");
		} else {
			salaryPlanAreaDTO.setSalaryPlanTipCon(commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) + "/static/index/salary_plan_tip.png");
		}
		salaryPlanAreaDTO.setSalaryPlanIcon(commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) + "/static/index/salary_plan_sd.png");
		salaryPlanAreaDTO.setRate(userAccountQueryMapper.queryTaReceiveRate());
		salaryPlanAreaDTO.setDesc("近7日年化收益");
		salaryPlanAreaDTO.setButtonText("去定制");

		return salaryPlanAreaDTO;
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
}
