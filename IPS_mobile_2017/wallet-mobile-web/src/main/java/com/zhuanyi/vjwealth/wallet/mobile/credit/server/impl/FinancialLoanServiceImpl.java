package com.zhuanyi.vjwealth.wallet.mobile.credit.server.impl;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zhuanyi.vjwealth.wallet.mobile.common.server.service.ICommConfigsQueryService;
import com.zhuanyi.vjwealth.wallet.mobile.common.util.BizCommonConstant;
import com.zhuanyi.vjwealth.wallet.mobile.common.util.MoneyCommonUtils;
import com.zhuanyi.vjwealth.wallet.mobile.user.util.ParamValidUtil;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fab.core.annotation.Remote;
import com.fab.core.exception.service.AppException;
import com.fab.core.logger.BaseLogger;
import com.zhuanyi.vjwealth.loan.client.webservice.IClientInfoDubboService;
import com.zhuanyi.vjwealth.loan.credit.webservice.ITaskDetailsInfoDubboService;
import com.zhuanyi.vjwealth.loan.order.vo.ContractVo;
import com.zhuanyi.vjwealth.loan.order.webservice.IApplyFinancingLoanDubboService;
import com.zhuanyi.vjwealth.loan.order.webservice.ILoanApplicationDubboService;
import com.zhuanyi.vjwealth.loan.product.webservice.IProductInfoDubboService;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.IFinancialLoanService;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.constants.CityLabelPkConstant;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.constants.FundAndSocialResultConstant;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.constants.RepaymentTypeConstant;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.constants.TaskImgKeyConstant;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.FundAndSocialSecurityAccountTaskDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.financialLoan.ApplyLaToLfResultDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.financialLoan.CommonResultDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.financialLoan.FinancialLoanCreditDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.financialLoan.FinancialLoanCreditDetailDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.financialLoan.FinancialLoanCreditProductInfoDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.financialLoan.FinancialLoanCreditRepayPlanDetailDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.financialLoan.FinancialLoanCreditTrialDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.financialLoan.FinancialLoanInitiDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.financialLoan.FinancialLoanInitiInnerProductDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.financialLoan.FinancialLoanInvestmentDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.financialLoan.FinancialLoanInvestmentDetailDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.financialLoan.FinancialLoanInvestmentInitDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.financialLoan.FinancialLoanInvestmentNewFlows;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.financialLoan.FinancialLoanInvestmentSummaryDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.financialLoan.FinancialLoanRepayPlanDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.financialLoan.InvestInitiDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.financialLoan.InvestProductInfoDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.financialLoan.LoanInformationConfirmationInitiDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.financialLoan.ProductTypeDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.financialLoan.TaskCommitResultDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.financialLoan.TaskDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.financialLoan.TaskDetailDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.financialLoan.UserInfoParamDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.mapper.FinancialLoanMapper;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.utils.MoneyUtil;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.IProductQueryService;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.ProductDetailQueryDTO;
import com.zhuanyi.vjwealth.wallet.mobile.user.server.integration.mapper.IUserQueryMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBRfReturnDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.IMBUserAccountService;

/**
 * Created by hexy on 16/6/12.
 */
@Service
public class FinancialLoanServiceImpl implements IFinancialLoanService {

	@Autowired
	private FinancialLoanMapper financialLoanMapper;

	@Autowired
	private ITaskDetailsInfoDubboService taskDetailsInfoDubboService;

	@Autowired
	private IProductQueryService productQueryService;

	@Autowired
	private IProductInfoDubboService loanProductInfoDubboService;

	@Autowired
	private IClientInfoDubboService clientInfoDubboService;

	@Autowired
	private IApplyFinancingLoanDubboService applyFinancingLoanDubboService;

	@Autowired
	private ILoanApplicationDubboService loanApplicationDubboService;

	@Remote
	private IMBUserAccountService mBUserAccountService;
	
	@Autowired
	private IUserQueryMapper userQueryMapper;
	@Autowired
	private ICommConfigsQueryService commConfigsQueryService;

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> productTypeList(String userId, String page) {
		// 参数验证
		if (StringUtils.isEmpty(userId)) {
			throw new AppException("用户编号不能为空");
		}
		if (StringUtils.isBlank(page) || !StringUtils.isNumeric(page) || Integer.parseInt(page) < 1) {
			throw new AppException("page参数不合法");
		}
		List<ProductTypeDTO> records = new ArrayList<ProductTypeDTO>();
		BaseLogger.info("对接信贷系统-查询信贷产品列表入参page：" + page);
		// 调用信贷接口：获取信贷产品列表
		String jsonStr = loanProductInfoDubboService.selectProductInfoByPage(Integer.parseInt(page));
		BaseLogger.info("对接信贷系统-查询信贷产品列表返回结果：" + jsonStr);
		if (StringUtils.isBlank(jsonStr)) {
			throw new AppException("系统异常,请稍后再试");
		}
		// 将jsonStr转成集合
		List<Map<String, String>> set = JSONArray.parseObject(jsonStr, List.class);

		for (Map<String, String> map : set) {
			ProductTypeDTO ptd = JSONObject.parseObject(JSONObject.toJSONString(map), ProductTypeDTO.class);
			ptd.setProductTypeCode(map.get("id"));
			Object tempValid = map.get("productStatus");
			ptd.setIsAvailable("1".equals(tempValid) ? "true" : "false");
			ptd.setButtonTextMessage("1".equals(tempValid) ? "立即使用" : "");
			ptd.setIsAvailableTipInformation(map.get("tipInformation")); //
			ptd.setProductTypeName(map.get("productName"));
			ptd.setProductTypeIntroduction(map.get("productDesc"));
			ptd.setProductTypeIntroductionPictureURL(map.get("pictureBackgroundUrl"));
			records.add(ptd);
		}

		// 判断是否有更多记录
		String isMore = "false";
		if (records.size() >= 10) {
			isMore = "true";
		}

		// 返回结果集
		Map<String, Object> pageMap = new HashMap<String, Object>();
		pageMap.put("records", records);
		pageMap.put("isMore", isMore);
		pageMap.put("timeWalletLabel", "详情");
		pageMap.put("timeWalletTitle", "时光钱包");
		pageMap.put("timeWalletURL", financialLoanMapper.getParamsValueByKeyAndGroup("finacial_loan_detail_url", "financial_loan"));
		
		return pageMap;
	}

	@Override
	public FinancialLoanInitiDTO financialLoanIniti(String userId, String page, String productTypeCode) {

		if (StringUtils.isBlank(userId) || StringUtils.isBlank(productTypeCode)) {
			throw new AppException("存在请求为空的参数");
		}

		if (StringUtils.isBlank(page) || !StringUtils.isNumeric(page) || Integer.parseInt(page) < 1) {
			throw new AppException("page参数不合法");
		}

		// 返回结果集
		FinancialLoanInitiDTO fid = new FinancialLoanInitiDTO();
		
		
		
		// 调用信贷系统的入参
		UserInfoParamDTO uid = financialLoanMapper.queryUserBaseInfoByUserId(userId);

		// 1,查询信贷任务列表
		String paramStr = JSONObject.toJSONString(uid);
		BaseLogger.info("对接信贷系统-查询任务列表入参：" + paramStr);
		String taskListJsonStr = taskDetailsInfoDubboService.selectTaskListSimple(paramStr);
		BaseLogger.info("对接信贷系统-查询任务列表返回：" + taskListJsonStr);

		if (StringUtils.isBlank(taskListJsonStr) || taskListJsonStr.equals("[]")) {
			throw new AppException("获取任务列表失败");
		}

		// 2,信贷系统额度判断 ,可用额度
		BaseLogger.info("对接信贷系统-查询用户信用额度入参userId，productTypeCode：" + userId + "," + productTypeCode);
		int availabelAmountTemp = clientInfoDubboService.queryClientCreditQuota(userId, productTypeCode);
		BaseLogger.info("对接信贷系统-查询用户信用额度：" + availabelAmountTemp);

		String availabelAmount = MoneyUtil.toYuan(String.valueOf(availabelAmountTemp));

		fid.setAvailableCreditLabel("鱼池");
		fid.setAvailableCredit(availabelAmount);
		fid.setAvailableCreditStatus("true");
		fid.setDailyInterestRate(financialLoanMapper.getParamsValueByKeyAndGroup("loan_rate", "financial_loan"));
		fid.setAvailableCreditDescription("按日计息，日利率低至");

		List<TaskDTO> taskList = handlerTaskDto(userId,taskListJsonStr, false);

		fid.setTaskList(taskList);

		// 判断信用额度是否可用
		if (availabelAmount.compareTo("0") <= 0) {
			fid.setAvailableCreditStatus("false");
			fid.setAvailableCredit(taskList.get(0).getTaskPrize());
            fid.setAvailableCreditDescription("完成以下任务，立即激活小金鱼");
			fid.setDailyInterestRate("");
		}

		fid.setIsHaveInvertmentRecord(financialLoanMapper.queryIfInvested(userId));

		// 3,查询定向产品列表
		List<FinancialLoanInitiInnerProductDTO> proList = financialLoanMapper
				.queryFinancialLoanProductList((Integer.parseInt(page) - 1) * 10);

		if (proList == null) {
			proList = new ArrayList<FinancialLoanInitiInnerProductDTO>();
		}

		fid.setRecords(proList);

		if (proList.size() >= 10) {
			fid.setIsMore("true");
		}

		return fid;
	}

	@Override
	public ProductDetailQueryDTO queryProductDetail(String userId, String productId) {
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(productId)) {
			throw new AppException("存在请求为空的参数");
		}

		// 调用V理财接口
		ProductDetailQueryDTO productDetailQueryDTO = productQueryService.queryProductDetail(userId, productId);
		return productDetailQueryDTO;
	}

	@Override
	public Map<String, Object> queryProductInvestmentRecordList(String userId, String productId, String page) {
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(productId)) {
			throw new AppException("存在请求为空的参数");
		}

		if (StringUtils.isBlank(page) || !StringUtils.isNumeric(page) || Integer.parseInt(page) < 1) {
			throw new AppException("page参数不合法");
		}

		return productQueryService.queryProductInvestmentRecordListV2(productId, page);
	}

	@Override
	public InvestInitiDTO investIniti(String userId, String productId, String investmentAmount) {

		if (StringUtils.isBlank(userId) || StringUtils.isBlank(productId) || StringUtils.isBlank(investmentAmount)) {
			throw new AppException("存在请求为空的参数");
		}

		if (!StringUtils.isNumeric(investmentAmount) || Integer.parseInt(investmentAmount) < 0) {
			throw new AppException("investmentAmount参数不合法");
		}

		InvestInitiDTO investInitiDTO = financialLoanMapper.queryProductIsCanBuy(productId);
		
		if(investInitiDTO == null){
			throw new AppException("产品不存在");
		}
		
		investInitiDTO.setButtonTextMessage("确认");

		// 查询用户的信用额度
		String loanProductId = financialLoanMapper.queryLoanProductIdById(productId);
		BaseLogger.info("对接信贷系统-查询用户信用额度入参userId，loanProductId：" + userId + "," + loanProductId);
		int availabelAmountTemp = clientInfoDubboService.queryClientCreditQuota(userId, loanProductId);
		BaseLogger.info("对接信贷系统-查询用户信用额度：" + availabelAmountTemp);

		String availabelAmount = MoneyUtil.toYuan(String.valueOf(availabelAmountTemp));

		investInitiDTO.setAvailableBalance(String.valueOf(availabelAmount));
		investInitiDTO.setAvailableBalancePlaceholderTip("可用余额" + availabelAmount + "元");
		if (availabelAmount.compareTo("0") <= 0) {
			investInitiDTO.setInformation("鱼池里还没有小金鱼，快去完成任务，获得价值10000元的理财小金鱼吧|再看看|去做任务");
			investInitiDTO.setIsCanBuy("false");
		} else {
			BigDecimal limitAmount = financialLoanMapper.queryGoldFishUpperLimitAmount();
			if (null == limitAmount) {
				limitAmount = new BigDecimal(50000);//用户使用小金鱼限额默认， 如果数据库没有配置限额， 则使用此限额5万元整
			}

			BigDecimal investGoingAmount = financialLoanMapper.queryInvestGoingAmount(userId);
			if (null == investGoingAmount) {
				investGoingAmount = new BigDecimal(0);
			}
			String info ="";
			if(investGoingAmount.compareTo(limitAmount)>=0) {//A.    如果用户持有的小金鱼投资未到期订单金额 >= 5万元，则不允许购买，提示用户“您已经购买了10万元小金鱼产品，每个用户同时最多购买5万元小金鱼产品，您可以等持有的小金鱼产品到期之后再购买，谢谢。”
				info="您已经购买了"+investGoingAmount.divide(new BigDecimal(10000)).intValue()+"万元小金鱼产品，每个用户同时最多购买"+limitAmount.divide(new BigDecimal(10000)).intValue()+"万元小金鱼产品，您可以等持有的小金鱼产品到期之后再购买，谢谢。|再看看|去做任务";
				investInitiDTO.setInformation(info);
				investInitiDTO.setIsCanBuy("false");
			}else if (investGoingAmount.add(new BigDecimal(investmentAmount)).compareTo(limitAmount)>0) {// B.    如果用户持有的小金鱼未到期订单金额 + 用户本次购买的金额 > 5万元，则不允许购买，并提示用户，最多可以购买金额为多少。“每个用户同时最多购买5万元小金鱼产品，您已经持有47000元未到期产品，本次最多购买3000元。”同时，微信版最好能把实际能购买的金额填写在金额输入框中。
				String canBuyAmount = String.valueOf(limitAmount.subtract(investGoingAmount).intValue());
				investInitiDTO.setCanBuyAmount(canBuyAmount);
				info="每个用户同时最多购买"+limitAmount.divide(new BigDecimal(10000)).intValue()+"万元小金鱼产品，您已经持有"+investGoingAmount.intValue()+"元未到期产品，本次最多购买"+canBuyAmount+"元。|再看看|去做任务";
				investInitiDTO.setInformation(info);
				investInitiDTO.setIsCanBuy("false");
			} else {
				if("false".equals(investInitiDTO.getIsCanBuy())){
					investInitiDTO.setInformation("商品已下架或卖光了!|再看看|去做任务");
				}else {
					investInitiDTO.setInformation(info);
				}
			}
		}

		List<Map<String, String>> detailInformation = new ArrayList<>();
		InvestProductInfoDTO pfd = investInitiDTO.getProduct();
		BigDecimal bigDecimal=new BigDecimal(pfd.getAnnualYield()).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP);
		detailInformation.add(buildLabelAndValueMap("年化收益",bigDecimal.toString()+ "%"));
		detailInformation.add(buildLabelAndValueMap("项目期限", pfd.getProjectTerm()));

		investmentAmount = MoneyUtil.toFen(investmentAmount);
		// 调用mainbiz，查询还款方式，还款时间，应还本金，应还利息
		BaseLogger.info("调用mainbiz-获取贷款信息入参productId，investmentAmount：" + productId + "," + investmentAmount);
		// 调用mainbiz：获取贷款信息
		String loanInfoJson = mBUserAccountService.trialRepayPlan(productId,Long.parseLong(investmentAmount));
		BaseLogger.info("调用mainbiz-获取贷款信息返回结果：" + loanInfoJson);

		FinancialLoanCreditTrialDTO fct = JSONObject.parseObject(loanInfoJson, FinancialLoanCreditTrialDTO.class);
		fct.setFinancialInterestTotal(MoneyUtil.toYuan(String.valueOf(fct.getFinancialInterestTotal())));
		fct.setLoanInterestTotal(MoneyUtil.toYuan(String.valueOf(fct.getLoanInterestTotal())));
		fct.setPrincipalTotal(MoneyUtil.toYuan(String.valueOf(fct.getPrincipalTotal())));

		// 计算投资预计收益
		String expectReceive = fct.getFinancialInterestTotal();
		investInitiDTO.setInputBottomTipContents("<font color=#5D7A8D>必须为" + investInitiDTO.getInputBottomTipContents()
				+ "的整数倍|预计收益</font><font color=#FFBD30>" + expectReceive + "</font><font color=#5D7A8D>元</font>");
		detailInformation.add(buildLabelAndValueMap("待收利息","￥"+expectReceive));
		detailInformation.add(buildLabelAndValueMap("还款方式", pfd.getPaymentWay()));
		detailInformation.add(buildLabelAndValueMap("还款时间", handlerDate(fct.getLastRepaymentDateStr())));
		detailInformation.add(buildLabelAndValueMap("应还本金", "￥"+fct.getPrincipalTotal()));
		detailInformation.add(buildLabelAndValueMap("待付利息", "￥"+fct.getLoanInterestTotal()));
		// 可得利息 = 预计收益-应还利息
		BigDecimal inter = new BigDecimal(expectReceive).subtract(new BigDecimal(fct.getLoanInterestTotal()));
		detailInformation.add(buildLabelAndValueMap("可得利息", "￥"+inter.toPlainString()));

		investInitiDTO.setDetailInformation(detailInformation);
		
		investInitiDTO.setContractLabel("查看投资协议");
//		investInitiDTO.setIsShowInvestmentIip(financialLoanMapper.getIsShowInvestmentIip(userId));
		investInitiDTO.setIsShowInvestmentIip("no");
		investInitiDTO.setContractTitle("融桥宝债权转让合同");
		investInitiDTO.setContractURL(financialLoanMapper.getParamsValueByKeyAndGroup("investment_contract_template", "financial_loan"));

		return investInitiDTO;
	}
	
	
	private String handlerDate(String dateStr){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Long datess = Long.parseLong(dateStr);
		Date date = new Date(datess);
		return format.format(date);
	}

	@Override
	public CommonResultDTO investSave(String userId, String productId, String investmentAmount, String token) {

		if (StringUtils.isBlank(userId) || StringUtils.isBlank(productId) || StringUtils.isBlank(investmentAmount)
				|| StringUtils.isBlank(token)) {
			throw new AppException("存在请求为空的参数");
		}

		if (!StringUtils.isNumeric(investmentAmount) || Integer.parseInt(investmentAmount) < 0) {
			throw new AppException("investmentAmount参数不合法");
		}

		if(!financialLoanMapper.checkIsHaveSecurityCard(userId)){
            throw new AppException("请绑卡后再投资小金鱼");
		}
		// 空逻辑处理,总是成功
		CommonResultDTO commonResultDTO = new CommonResultDTO();
		commonResultDTO.setCode("202700");
		commonResultDTO.setMessage("立即投资保存成功");
		return commonResultDTO;
	}

	@Override
	public Boolean taskQuotaJudgment(String userId) {

		return true;
	}

	@Override
	public LoanInformationConfirmationInitiDTO loanInformationConfirmationIniti(String userId, String productId,
			String investmentAmount) {

		if (StringUtils.isBlank(userId) || StringUtils.isBlank(productId) || StringUtils.isBlank(investmentAmount)) {
			throw new AppException("存在请求为空的参数");
		}

		if (!StringUtils.isNumeric(investmentAmount) || Integer.parseInt(investmentAmount) < 0) {
			throw new AppException("investmentAmount参数不合法");
		}

		// 获取用户基本信息
		Map<String, String> userInfo = financialLoanMapper.queryUserNameAndIdentyNoByUserId(userId);

		LoanInformationConfirmationInitiDTO loanInformationConfirmationInitiDTO = new LoanInformationConfirmationInitiDTO();
		loanInformationConfirmationInitiDTO.setButtonTextMessage("确认");
		loanInformationConfirmationInitiDTO.setProtocolSpecification("请仔细阅读本协议，点击确认提交表示您同意遵守");
		List<Map<String, String>> loadDetailInformation = new ArrayList<>();
		loadDetailInformation.add(buildLabelAndValueMap("姓名", handlerUserName(userInfo.get("userName"))));
		loadDetailInformation.add(buildLabelAndValueMap("身份证号", userInfo.get("indentityNo")));

		loanInformationConfirmationInitiDTO.setPersonalInformation(loadDetailInformation);
		List<Map<String, String>> personalInformation = new ArrayList<>();


		String paramInvestmentAmount = MoneyUtil.toFen(investmentAmount);
		BaseLogger.info("调用mainbiz-获取贷款信息入参productId，investmentAmount：" + productId + "," + paramInvestmentAmount);
		// 调用信贷系统：获取贷款信息
		String loanInfoJson = mBUserAccountService.trialRepayPlan(productId,Long.parseLong(paramInvestmentAmount));
		BaseLogger.info("调用mainbiz-获取贷款信息返回结果：" + loanInfoJson);
		FinancialLoanCreditTrialDTO fct = JSONObject.parseObject(loanInfoJson, FinancialLoanCreditTrialDTO.class);
		fct.setPrincipalTotal(MoneyUtil.toYuan(fct.getPrincipalTotal()));
		fct.setLoanInterestTotal(MoneyUtil.toYuan(fct.getLoanInterestTotal()));

		personalInformation.add(buildLabelAndValueMap("借款金额", "￥"+investmentAmount));

		// 格式化日期
		Calendar cd = Calendar.getInstance();
		cd.setTime(new Date());
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		String startDate = format.format(new Date());
		
		Long datess = Long.parseLong(fct.getLastRepaymentDateStr());
		Date endDate = new Date(datess);
		String endDateStr = format.format(endDate);

		personalInformation.add(buildLabelAndValueMap("起止时间", startDate + "~" + endDateStr));
		
		personalInformation.add(buildLabelAndValueMap("借款期限", financialLoanMapper.queryProductPeriodByProductId(productId)));
		personalInformation.add(buildLabelAndValueMap("借款方式", RepaymentTypeConstant.getValue(fct.getRepaymentMethod())));
		personalInformation.add(buildLabelAndValueMap("还款日", handlerDate(fct.getLastRepaymentDateStr())));

		personalInformation.add(buildLabelAndValueMap("应还本金", "￥"+fct.getPrincipalTotal()));
		personalInformation.add(buildLabelAndValueMap("应还利息", "￥"+fct.getLoanInterestTotal()));

		loanInformationConfirmationInitiDTO.setLoadDetailInformation(personalInformation);
		
		loanInformationConfirmationInitiDTO.setContractLabel("小金鱼相关合同");
		loanInformationConfirmationInitiDTO.setContractTitle("小金鱼相关合同");
		loanInformationConfirmationInitiDTO.setContractURL(financialLoanMapper.getParamsValueByKeyAndGroup("loan_contract_template", "financial_loan"));

		return loanInformationConfirmationInitiDTO;
	}

	private String handlerUserName(String userName) {
		if (!StringUtils.isBlank(userName)) {
			String returnName = userName.substring(0, 1);
			String secondName = userName.substring(1, userName.length());
			if (secondName.length() > 0) {
				for (int i = 0; i < secondName.length(); i++) {
					returnName = returnName + "*";
				}
			}
			return returnName;
		}
		return "";
	}

	private Map<String, String> buildLabelAndValueMap(final String label, final String value) {
		return new HashMap<String, String>() {
			private static final long serialVersionUID = 1L;

			{

				put("label", label);
				put("value", value);
			}
		};
	}

	@Override
	public Object loanInformationConfirmationSave(String userId, String productId, String investmentAmount,
			String token) {

		if (StringUtils.isBlank(userId) || StringUtils.isBlank(productId) || StringUtils.isBlank(investmentAmount)
				|| StringUtils.isBlank(token)) {
			throw new AppException("存在请求为空的参数");
		}

		if (!StringUtils.isNumeric(investmentAmount) || Integer.parseInt(investmentAmount) < 0) {
			throw new AppException("investmentAmount参数不合法");
		}

		BaseLogger.audit(
				String.format("下单开始  userId :%s ，productId :%s  金额  : %s  ", userId, productId, investmentAmount));
		try {
			BigDecimal limitAmount = financialLoanMapper.queryGoldFishUpperLimitAmount();
			if (null == limitAmount) {
				limitAmount = new BigDecimal(50000);//用户使用小金鱼限额默认， 如果数据库没有配置限额， 则使用此限额5万元整
			}

			BigDecimal investGoingAmount = financialLoanMapper.queryInvestGoingAmount(userId);
			if (null == investGoingAmount) {
				investGoingAmount = new BigDecimal(0);
			}
			String info ="";
			if(investGoingAmount.compareTo(limitAmount)>=0) {//A.    如果用户持有的小金鱼投资未到期订单金额 >= 5万元，则不允许购买，提示用户“您已经购买了10万元小金鱼产品，每个用户同时最多购买5万元小金鱼产品，您可以等持有的小金鱼产品到期之后再购买，谢谢。”
				throw new AppException("您已经购买了"+investGoingAmount.divide(new BigDecimal(10000)).intValue()+"万元小金鱼产品，每个用户同时最多购买"+limitAmount.divide(new BigDecimal(10000)).intValue()+"万元小金鱼产品，您可以等持有的小金鱼产品到期之后再购买，谢谢。");

			}else if (investGoingAmount.add(new BigDecimal(investmentAmount)).compareTo(limitAmount)>0) {// B.    如果用户持有的小金鱼未到期订单金额 + 用户本次购买的金额 > 5万元，则不允许购买，并提示用户，最多可以购买金额为多少。“每个用户同时最多购买5万元小金鱼产品，您已经持有47000元未到期产品，本次最多购买3000元。”同时，微信版最好能把实际能购买的金额填写在金额输入框中。
				String canBuyAmount = String.valueOf(limitAmount.subtract(investGoingAmount).intValue());
				throw new AppException("每个用户同时最多购买"+limitAmount.divide(new BigDecimal(10000)).intValue()+"万元小金鱼产品，您已经持有"+investGoingAmount.intValue()+"元未到期产品，本次最多购买"+canBuyAmount+"元。");
			}
			MBRfReturnDTO mbRfReturnDTO = mBUserAccountService.applyLaToLf(userId, investmentAmount, productId, token);
			if (null != mbRfReturnDTO) {
				BaseLogger.audit(String.format(
						"下单结束 返回信息为 code : %s  ,message :%s ,remainingInvestment :%s ,paymentInformation :%s ",
						mbRfReturnDTO.getCode(), mbRfReturnDTO.getMessage(), mbRfReturnDTO.getRemainingInvestment(),
						mbRfReturnDTO.getPaymentInformation()));
			} else {
				BaseLogger.audit("下单结束 返回信息为  MBRfReturnDTO: 为空");
			}

			return handlerResult(mbRfReturnDTO,productId);
		} catch (Exception e) {
			if (e instanceof AppException) {
				Integer code = ((AppException) e).getKey();
				String message = e.getMessage();
				if (null != code) {
					BaseLogger.audit(code + " : " + e.getMessage());
					return new ApplyLaToLfResultDTO(code.toString(), message);
				}
				BaseLogger.error(e.getMessage());
				throw new AppException(message);
			}
			BaseLogger.error(e.getMessage());
			throw new AppException("系统繁忙，请稍后再试");
		}

		// //TODO MOCK
		// MBRfReturnDTO dto = new MBRfReturnDTO("200100", "投资成功", null, "");
		// return dto;
	}
	
	private ApplyLaToLfResultDTO handlerResult(MBRfReturnDTO mbRfReturnDTO,String productId){
		ApplyLaToLfResultDTO ar = new ApplyLaToLfResultDTO();
		
		ar.setCode(mbRfReturnDTO.getCode());
        ar.setMessage("小金鱼使用成功");

        Map<String,String> productInfo = financialLoanMapper.queryProductNameAndPeriod(productId);
        String productName = productInfo.get("productName");
        String endDate = productInfo.get("endDate");
        
        String info = "已成功投资理财贷产品"+productName+"|"+endDate+"到期";
		
        ar.setFeedbackInformation(info);
        ar.setIconURL(financialLoanMapper.getParamsValueByKeyAndGroup("loan_apply_success_icon", "financial_loan"));
        
		return ar;
	}

	@Override
	public Map<String, Object> contractList(String userId, String productId, String borrowCode) {
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(productId)) {
			throw new AppException("存在请求为空的参数");
		}

		// 根据V理财产品ID获取贷款产品编号
		String loanProductId = financialLoanMapper.queryLoanProductIdById(productId);

		// 获取信贷产品的合同列表
		List<ContractVo> contractVoList = loanApplicationDubboService.loanProductContractList(userId, loanProductId);

		// TODO 是否获取V理财产品的合同列表拼接
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("contractList", contractVoList);

		return returnMap;
	}

	@Override
	public Object contractDetail(String userId, String borrowCode, String contractCode) {
		// TODO 参数验证
		// contractCode 模版
		// borrowCode 详情
		return null;
	}

	@Override
	public Map<String, Object> taskList(String userId, String page) {

		if (StringUtils.isBlank(userId) || StringUtils.isBlank(page)) {
			throw new AppException("存在请求为空的参数");
		}

		if (!StringUtils.isNumeric(page) || Integer.parseInt(page) < 1) {
			throw new AppException("page参数不合法");
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();
		BaseLogger.info("对接信贷系统-获取任务列表入参userId：" + userId);
		// 调用信贷系统：获取任务列表信息
		String taskListJsonStr = taskDetailsInfoDubboService.selectTaskListDetailed(userId);
		BaseLogger.info("对接信贷系统-获取任务列表返回结果：" + taskListJsonStr);

		List<TaskDTO> taskList = handlerTaskDto(userId,taskListJsonStr, true);

		// 组装结果集
		if (CollectionUtils.isEmpty(taskList) || taskList.size() < 10) {
			resultMap.put("isMore", "false");
		} else {
			resultMap.put("isMore", "true");
		}
		resultMap.put("records", taskList);
		return resultMap;
	}

	@SuppressWarnings("unchecked")
	private List<TaskDTO> handlerTaskDto(String userId,String taskListJsonStr, boolean flag) {
		List<TaskDTO> taskList = new ArrayList<TaskDTO>();

		List<Map<String, String>> listdd = JSONArray.parseObject(taskListJsonStr, List.class);

		// 查询贷款任务列表的图标
		List<Map<String, String>> commList = financialLoanMapper.queryLoanCommonIcon();
		Map<String, String> commMap = new HashMap<String, String>();

		if (CollectionUtils.isNotEmpty(commList)) {
			for (Map<String, String> map : commList) {
				commMap.put(map.get("paramKey"), map.get("paramValue"));
			}
		}

		for (Map<String, String> map : listdd) {

			TaskDTO tt = JSONObject.parseObject(JSONObject.toJSONString(map), TaskDTO.class);
			tt.setIsRepeatTask(tt.getIsRepeatTask().equals("0") ? "false" : "true");

			// 绑卡任务
			if (TaskInfo.TASK_BIND_CARD.equals(tt.getTaskCode())) {
				// 任务完成
				if (TaskInfo.COMPLETION.equals(tt.getTaskState())) {
					tt.setTaskIcon(commMap.get(TaskImgKeyConstant.CARD_NO.getKey()));
					// 取任务达成的大图标
					if (flag) {
						tt.setTaskCompletionMarkURL(commMap.get(TaskImgKeyConstant.CARD_COMPELETE_BIG.getKey()));
					} else {
						tt.setTaskCompletionMarkURL(commMap.get(TaskImgKeyConstant.CARD_COMPELETE_SMALL.getKey()));
					}

				} else {
					// 任务未完成
					tt.setTaskIcon(commMap.get(TaskImgKeyConstant.CARD_YES.getKey()));
					tt.setTaskCompletionMarkURL("");
				}

			}
			// 投资任务
			else if (TaskInfo.TASK_INVESTMENT.equals(tt.getTaskCode())) {
				if (TaskInfo.COMPLETION.equals(tt.getTaskState())) {
					tt.setTaskIcon(commMap.get(TaskImgKeyConstant.INVEST_NO.getKey()));
				} else {
					tt.setTaskIcon(commMap.get(TaskImgKeyConstant.INVEST_YES.getKey()));
				}
				tt.setTaskCompletionMarkURL("");
				
				//调用mainbiz系统，查询用户投资进度
				BaseLogger.info("调用mainbiz系统-，查询用户投资进度userId：" + userId);
				String scheduleJsonStr = mBUserAccountService.rfInvestProgress(userId);
				JSONObject scheduleJson = JSONObject.parseObject(scheduleJsonStr);
				BaseLogger.info("调用mainbiz系统-，查询用户投资进度返回结果" + scheduleJsonStr);
				
				tt.setTaskSchedule(scheduleJson.getString("schedule"));
				tt.setTaskScheduleDescription("距离获得下条小金鱼还差"+MoneyUtil.toYuan(scheduleJson.getString("needInvestmentAmount"))+"元投资");
			}
			// 公积金社保任务
			else if (TaskInfo.TASK_SOCIAL.equals(tt.getTaskCode())) {
				if (TaskInfo.COMPLETION.equals(tt.getTaskState())) {
					tt.setTaskIcon(commMap.get(TaskImgKeyConstant.SOCIAL_NO.getKey()));
					tt.setTaskCompletionMarkURL(commMap.get(TaskImgKeyConstant.CARD_COMPELETE_SMALL.getKey()));
				} else {
					tt.setTaskIcon(commMap.get(TaskImgKeyConstant.SOCIAL_YES.getKey()));
					if (TaskInfo.COMPLETE_FAIL.equals(tt.getTaskState())){
						tt.setFailInformation("您的社保公积金信息有误\n请重新填写|返回|去修改");
					}
					if (TaskInfo.COMPLETE_MEDIUM.equals(tt.getTaskState())){
						tt.setFailInformation("正在审核中|返回|去修改");
					}
					tt.setTaskCompletionMarkURL("");
				}
            } else if (TaskInfo.INVITE_REGISTER.equals(tt.getTaskCode())) {//邀请好友注册任务
                tt.setTaskIcon(commMap.get(TaskImgKeyConstant.INVITE_REGISTER_YES.getKey()));
//                if (TaskInfo.COMPLETION.equals(tt.getTaskState())) {
//                    tt.setTaskIcon(commMap.get(TaskImgKeyConstant.INVITE_REGISTER_NO.getKey()));
//                } else {
//                    tt.setTaskIcon(commMap.get(TaskImgKeyConstant.INVITE_REGISTER_YES.getKey()));
//                }
                tt.setTaskCompletionMarkURL("");
				int num = financialLoanMapper.querySurplusSpaceForInviteRegisterUserNum();
				tt.setTaskScheduleDescription(String.format("当天剩余名额:%s人", num));
			}

			// 转换任务状态
			tt.setTaskState(TaskInfo.getStatusMap().get(tt.getTaskState()));
			if(flag){
				tt.setTaskPrize("￥"+tt.getTaskPrize());
			}

			taskList.add(tt);
		}

		return taskList;
	}

	// 内部类，用来转换任务状态
	static class TaskInfo {
		// 任务状态
		private static Map<String, String> statusMap = new HashMap<String, String>();

		// 任务状态-未完成
		public static final String NOT_COMPLETION = "1";
		// 任务状态-完成中
		public static final String COMPLETE_MEDIUM = "2";
		// 任务状态-任务失败
		public static final String COMPLETE_FAIL = "3";
		// 任务状态-任务完成
		public static final String COMPLETION = "4";

		// 绑卡任务编号
		public static final String TASK_BIND_CARD = "bing_card";
		// 投资任务编号
		public static final String TASK_INVESTMENT = "total_investment";
		// 公积金社保任务编号
		public static final String TASK_SOCIAL = "social_security_fund";
        //邀请好友注册任务编号
        public static final String INVITE_REGISTER = "invite_register";

		static {
			statusMap.put(NOT_COMPLETION, "notCompletion");
			statusMap.put(COMPLETE_MEDIUM, "completeMedium");
			statusMap.put(COMPLETE_FAIL, "completeFail");
			statusMap.put(COMPLETION, "completion");
		}

		public static Map<String, String> getStatusMap() {
			return statusMap;
		}

	}
	
	

	@Override
	public TaskDetailDTO taskDetail(String userId, String taskCode, String productTypeCode,String cityCode,String cityName) {
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(productTypeCode)) {
			throw new AppException("存在请求为空的参数");
		}
		
		if(StringUtils.isNotBlank(cityCode) && StringUtils.isBlank(cityName)){
			throw new AppException("cityCode不为空时，cityName也不能为空");
		}
//		//验证是否绑过卡
//		Map<String,String> user = userQueryMapper.queryUserSecurityCardInfo(userId);
//		if(user==null || StringUtils.isBlank(user.get("cardId"))){
//			throw new AppException("请先完成绑卡任务");
//		}
		
		
		// 调用信贷系统，获取任务详细信息
		BaseLogger.info("对接信贷系统-获取任务详细信息入参userId,productTypeCode：" + userId + "," + productTypeCode);
		String taskDetailJsonStr = clientInfoDubboService.queryClientCreditAccountInfo(userId, productTypeCode);
		BaseLogger.info("对接信贷系统-获取任务详细信息返回结果：" + taskDetailJsonStr);

		TaskDetailDTO tdd = JSONObject.parseObject(taskDetailJsonStr, TaskDetailDTO.class);
		
		
		if(StringUtils.isBlank(cityCode)){
            cityCode = tdd.getCityCode();
            
            if(StringUtils.isBlank(cityCode)){
            	String value = financialLoanMapper.getParamsValueByKeyAndGroup("fund_and_social_default_citycode","financial_loan");
            	String[] values = value.split("-");
            	cityCode = values[0];
            	tdd.setCityCode(cityCode);
            	tdd.setCityName(values[1]);
            }
		}else{
			tdd.setCityCode(cityCode);
        	tdd.setCityName(cityName);
		}
		
		List<Map<String,String>> listMap = financialLoanMapper.queryCityLabelPkByCityCode(cityCode);
		
		if(listMap.size()>0){
			Map<String,String> listMapAll = new HashMap<String,String>();
			for(Map<String,String> map:listMap){
				listMapAll.put(map.get("labelName"),map.get("labelValue"));
			}
			
			tdd.setCityLabel("社保公积金所在地");
			tdd.setFundAccountQueryTitle("个人公积金查询信息");
			tdd.setSocialSecurityAccountQueryTitle("个人社保查询信息");
			tdd.setHelpLabel("关于社保公积金用户名密码");
			tdd.setHelpMessage("温馨提示 |如您遗忘社保、公积金账户及密码，请至当地社保局或社保查询网站进行相关咨询。|确认");
			
			tdd.setFundAccountLabel(listMapAll.get(CityLabelPkConstant.FUND_ACCOUNT_LABEL.getLabelName()));
			tdd.setFundAccountInputTip(listMapAll.get(CityLabelPkConstant.FUND_ACCOUNT_INPUT_TIP.getLabelName()));
			tdd.setFundAccountHelpInfo(JSONArray.parseArray(listMapAll.get(CityLabelPkConstant.FUND_ACCOUNT_HELP_CONTENT.getLabelName())));
			tdd.setFundAccountTipInfo(JSONObject.parseObject(listMapAll.get(CityLabelPkConstant.FUND_ACCOUNT_TIP_CONTENT.getLabelName())));
			tdd.setFundAccountPasswordLabel(listMapAll.get(CityLabelPkConstant.FUND_ACCOUNT_PASSWORD_LABEL.getLabelName()));
			tdd.setFundAccountPasswordInputTip(listMapAll.get(CityLabelPkConstant.FUND_ACCOUNT_PASSWORD_INPUT_TIP.getLabelName()));
			
			tdd.setSocialSecurityAccountLabel(listMapAll.get(CityLabelPkConstant.SOCIAL_SECURITY_ACCOUNT_LABEL.getLabelName()));
			tdd.setSocialSecurityAccountInputTip(listMapAll.get(CityLabelPkConstant.SOCIAL_SECURITY_ACCOUNT_INPUT_TIP.getLabelName()));
			tdd.setSocialSecurityAccountPasswordLabel(listMapAll.get(CityLabelPkConstant.SOCIAL_SECURITY_ACCOUNT_PASSWORD_LABEL.getLabelName()));
			tdd.setSocialSecurityAccountPasswordInputTip(listMapAll.get(CityLabelPkConstant.SOCIAL_SECURITY_ACCOUNT_PASSWORD_INPUT_TIP.getLabelName()));
			tdd.setSocialSecurityAccountHelpInfo(JSONArray.parseArray(listMapAll.get(CityLabelPkConstant.SOCIAL_SECURITY_ACCOUNT_HELP_CONTENT.getLabelName())));
			tdd.setSocialSecurityAccountTipInfo(JSONObject.parseObject(listMapAll.get(CityLabelPkConstant.SOCIAL_SECURITY_ACCOUNT_TIP_CONTENT.getLabelName())));
			
		}
		
		return tdd;

	}
	

	@SuppressWarnings("unchecked")
	@Override
	public TaskCommitResultDTO fundAndSocialSecurityAccountTaskSave(
			FundAndSocialSecurityAccountTaskDTO fundAndSocialSecurityAccountTaskDTO) {

		// 校验参数
		validatorFundAndSocialParams(fundAndSocialSecurityAccountTaskDTO);

		TaskCommitResultDTO tcr = new TaskCommitResultDTO();

		String jsonParam = JSONObject.toJSONString(fundAndSocialSecurityAccountTaskDTO);
		BaseLogger.info("对接信贷系统-保存公积金社保信息入参：" + jsonParam);
		// 调用信贷系统： 保存公积金社保信息
		String result = taskDetailsInfoDubboService.housingfundTaskFinish(jsonParam);
		BaseLogger.info("对接信贷系统-保存公积金社保信息结果：" + result);

		if (StringUtils.isNotBlank(result)) {
			Map<String, String> resultMap = JSONObject.parseObject(result, Map.class);
			if (resultMap.get("code").equals("200")) {
				tcr.setCode(FundAndSocialResultConstant.SAVED_SUCCESS.getCode());
				tcr.setFeedbackInformation(FundAndSocialResultConstant.SAVED_SUCCESS.getMessage());
				// 在数据库comm_params里添加对应数据
				tcr.setIconURL(financialLoanMapper.getParamsValueByKeyAndGroup("fund_and_social_save_success",
						"financial_loan"));
			} else if (resultMap.get("code").equals("600")) {
				tcr.setCode(FundAndSocialResultConstant.SAVED_FAIL.getCode());
				tcr.setMessage(resultMap.get("message"));
				tcr.setFeedbackInformation("");
				tcr.setIconURL("");

			}
		} else {
			throw new AppException("调用信贷系统，出现异常");
		}

		return tcr;
	}

	private void validatorFundAndSocialParams(FundAndSocialSecurityAccountTaskDTO paramDto) {
		if (StringUtils.isBlank(paramDto.getCityCode())) {
			throw new AppException("cityCode不能为空");
		}
		if (StringUtils.isBlank(paramDto.getFundAccount())) {
			throw new AppException("fundAccount不能为空");
		}
		if (StringUtils.isBlank(paramDto.getFundAccountPassword())) {
			throw new AppException("fundAccountPassword不能为空");
		}
		if (StringUtils.isBlank(paramDto.getSocialSecurityAccount())) {
			throw new AppException("socialSecurityAccount不能为空");
		}
		if (StringUtils.isBlank(paramDto.getSocialSecurityAccountPassword())) {
			throw new AppException("socialSecurityAccountPassword不能为空");
		}
		if (StringUtils.isBlank(paramDto.getTaskCode())) {
			throw new AppException("taskCode不能为空");
		}
		if (StringUtils.isBlank(paramDto.getUserId())) {
			throw new AppException("userId不能为空");
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public FinancialLoanInvestmentInitDTO borrowRecordList(String userId, String investmentStatus, String page) {
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(investmentStatus) || StringUtils.isBlank(page)) {
			throw new AppException("存在请求为空的参数");
		}

		if (!StringUtils.isNumeric(page) || Integer.parseInt(page) < 1) {
			throw new AppException("page参数不合法");
		}

		if (!"investment".equals(investmentStatus) && !"borrow".equals(investmentStatus)) {
			throw new AppException("investmentStatus参数不合法");
		}

		FinancialLoanInvestmentInitDTO fli = new FinancialLoanInvestmentInitDTO();
		// 1. 借款记录汇总
		FinancialLoanInvestmentSummaryDTO fs = financialLoanMapper.queryFinancialLoanInvestmentSummary(userId);

		// 1.1.设置汇总信息
		Map<String, String> map1 = setInvestmentSummaryInfo("在投金额(元)", fs.getInPrincipalCast());
		// 1.2.设置已还信息
		String leftReceive = fs.getReceive();//待收收益

		String resultObjStr = applyFinancingLoanDubboService.totalInterest(userId);//待还利息

		Map<String, String> map2 = new HashMap<String,String>();
		JSONObject resultObj = JSON.parseObject(resultObjStr) ;
		if("200".equals(resultObj.getString("code"))){
			String leftInterest = resultObj.getString("totalInterest");
			BigDecimal investmentSummary=new BigDecimal(leftReceive).subtract(new BigDecimal(MoneyCommonUtils.handlerMoneyStr(leftInterest)));
			if(investmentSummary.compareTo(BigDecimal.ZERO)<0){
				investmentSummary=BigDecimal.ZERO;
			}
			map2 = setInvestmentSummaryInfo("待收利差(元)",investmentSummary.toPlainString());//待收利差 = 待收收益-待还利息
		}else{
			throw new AppException("借款数据异常");
		}

		List<Map<String, String>> summaryList = new ArrayList<Map<String, String>>();
		summaryList.add(map1);
		summaryList.add(map2);
		fli.setBorrowRecordSummary(summaryList);

		List<Object> list = null;
		String isMore = "";
		// 2.投资记录列表
		if (investmentStatus.equals("investment")) {
			Map<String, Object> investmentList = queryUserInvestmentList(userId, page);
			list = (List<Object>) investmentList.get("records");
			isMore = (String) investmentList.get("isMore");
		}
		// 借款记录列表
		else if (investmentStatus.equals("borrow")) {
			Map<String, Object> borrowList = queryUserLoanList(userId, page);
			list = (List<Object>) borrowList.get("records");
			isMore = (String) borrowList.get("isMore");
		}

		fli.setRecords(list);
		fli.setIsMore(isMore);

		return fli;
	}

	private Map<String, String> setInvestmentSummaryInfo(final String label, final String amount) {
		return new HashMap<String, String>() {
			private static final long serialVersionUID = 1L;

			{

				put("label", label);
				put("value", amount);
			}
		};
	}

	/**
	 * 根据investmentStatus查询投资记录和借款记录
	 * 
	 * @param userId
     * @param investmentStatus （投资记录：investment，借款记录：borrow）
	 * @param page
	 * @return
	 */
	@Override
	public Map<String, Object> borrowRecordListForRefresh(String userId, String investmentStatus, String page) {

		if (StringUtils.isBlank(userId) || StringUtils.isBlank(investmentStatus) || StringUtils.isBlank(page)) {
			throw new AppException("存在请求为空的参数");
		}

		if (!StringUtils.isNumeric(page) || Integer.parseInt(page) < 1) {
			throw new AppException("page参数不合法");
		}

		if (!"investment".equals(investmentStatus) && !"borrow".equals(investmentStatus)) {
			throw new AppException("investmentStatus参数不合法");
		}

		if (investmentStatus.equals("investment")) {
			return queryUserInvestmentList(userId, page);
		}

		else if (investmentStatus.equals("borrow")) {
			return queryUserLoanList(userId, page);
		}

		else {
			throw new AppException("参数investmentStatus的值不正确");
		}

	}

	// 查询投资记录
	private Map<String, Object> queryUserInvestmentList(String userId, String page) {

		if (StringUtils.isBlank(userId) || StringUtils.isBlank(page)) {
			throw new AppException("存在请求为空的参数");
		}

		if (!StringUtils.isNumeric(page) || Integer.parseInt(page) < 1) {
			throw new AppException("page参数不合法");
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();
		// .投资记录列表
		List<FinancialLoanInvestmentDTO> list = financialLoanMapper.queryFinancialLoanInvestmentList(userId, (Integer.parseInt(page) - 1) * 10);

		if (CollectionUtils.isNotEmpty(list) && list.size() >= 10) {
			resultMap.put("isMore", "true");
		} else {
			resultMap.put("isMore", "false");
		}
		if (CollectionUtils.isEmpty(list)) {
			list = new ArrayList<FinancialLoanInvestmentDTO>();
		}
		resultMap.put("records", list);

		return resultMap;
	}

	// 查询借款记录
	@SuppressWarnings("unchecked")
	private Map<String, Object> queryUserLoanList(String userId, String page) {

		if (StringUtils.isBlank(userId) || StringUtils.isBlank(page)) {
			throw new AppException("存在请求为空的参数");
		}

		if (!StringUtils.isNumeric(page) || Integer.parseInt(page) < 1) {
			throw new AppException("page参数不合法");
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();

		// 贷款记录列表
		List<FinancialLoanCreditDTO> list = new ArrayList<FinancialLoanCreditDTO>();

		// 获取用户投资产品对应的贷款产品信息
		List<FinancialLoanCreditProductInfoDTO> dfpList = financialLoanMapper.queryUserLoanProductInfo(userId,(Integer.parseInt(page) - 1) * 10);

		if (CollectionUtils.isEmpty(dfpList)) {
			resultMap.put("isMore", "false");
			resultMap.put("records", new ArrayList<FinancialLoanCreditProductInfoDTO>());
			return resultMap;
		}

		Map<String, String> tempMap = new HashMap<String, String>();

		// 调用信贷系统时,需要传递的参数
		String[] borrowCodes = new String[dfpList.size()];

		if (dfpList.size() > 0) {
			for (int i = 0; i < dfpList.size(); i++) {
				FinancialLoanCreditProductInfoDTO fcp = dfpList.get(i);
				String borrowCode = fcp.getBorrowCode();
				tempMap.put(borrowCode, fcp.getProductName());
				borrowCodes[i] = borrowCode;
			}
		}

		if (borrowCodes.length == 0) {
			resultMap.put("isMore", "false");
			resultMap.put("records", new ArrayList<FinancialLoanCreditProductInfoDTO>());
			return resultMap;
		}

		BaseLogger.info(
				"对接信贷系统-查询借款记录入参userId,borrowCodes,page：" + userId + "," + borrowCodes.toString() + "," + page);
		// 调用信贷系统：借款记录列表
		String recordJson = applyFinancingLoanDubboService.queryLoanList(borrowCodes);
		BaseLogger.info("对接信贷系统-查询借款记录返回结果：" + recordJson);

		JSONObject resultObj = JSONObject.parseObject(recordJson);
		String recordJsonList = resultObj.getString("data");

		//将json转成list
		List<Map<String, String>> listdd = JSONArray.parseObject(recordJsonList, List.class);
		Map<String, String> commConfigMap = commConfigsQueryService.queryConfigKeyByGroup(BizCommonConstant.CONFIG_GROUP_WALLET_MOBILE);
		for (Map<String, String> map : listdd) {
			FinancialLoanCreditDTO fld = JSONObject.parseObject(JSONObject.toJSONString(map),
					FinancialLoanCreditDTO.class);
			fld.setBorrowAmountDescription("借款金额");
			fld.setBorrowAmount(MoneyUtil.toYuan(fld.getBorrowAmount())+"元");

			fld.setInvestProjectDescription("投资项目 " + tempMap.get(fld.getBorrowCode()));
			fld.setBorrowDateLabel("借款时间");

			String borrowDateStr = fld.getBorrowDate();
			if (StringUtils.isNotBlank(borrowDateStr)) {
				fld.setBorrowDate(handlerDate(borrowDateStr));
			}

			String allCompleteDateStr = fld.getAllCompleteRepaymentDate();
			if (StringUtils.isNotBlank(allCompleteDateStr)) {
				fld.setAllCompleteRepaymentDate(handlerDate(allCompleteDateStr));
			}

			fld.setPaidAmountLabel("已还");
			fld.setPaidAmount(MoneyUtil.toYuan(fld.getPaidAmount()));
			fld.setAllCompleteRepaymentDateLabel("还款时间");

			String repayDesc = fld.getRepaymentStatus().equals("F") ? "已还清" : "";
			String repayStatusUrl = fld.getRepaymentStatus().equals("F") ? "loan_record_status_repayed"
					: "loan_record_status_repaying";
			// 根据还款状态，显示对应的描述
			fld.setRepaymentStateDescription(repayDesc);
			// 根据还款状态，获取不同的图标 (在数据库comm_params里添加对应数据:还款状态图标)
			if(StringUtils.isNotBlank(fld.getAdvExpireDate())&&"F".equals(fld.getRepaymentStatus())){
				fld.setRepaymentStatusMarkURL(MessageFormat.format("{0}/static/pic/investment/{1}",commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL),"State_EarlyReturn.png"));
			}else {
				fld.setRepaymentStatusMarkURL(financialLoanMapper.getParamsValueByKeyAndGroup(repayStatusUrl, "financial_loan"));
			}
			list.add(fld);
		}

		if (CollectionUtils.isNotEmpty(list) && list.size() >= 10) {
			resultMap.put("isMore", "true");
		} else {
			resultMap.put("isMore", "false");
		}
		if (CollectionUtils.isEmpty(list)) {
			list = new ArrayList<FinancialLoanCreditDTO>();
		}
		resultMap.put("records", list);

		return resultMap;
	}

	// 投资记录详情
	@Override
	public FinancialLoanInvestmentDetailDTO investmentDetail(String userId, String orderId) {
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(orderId)) {
			throw new AppException("存在请求为空的参数");
		}

		FinancialLoanInvestmentDetailDTO fid = financialLoanMapper.queryInvestmentDetailByOrderId(orderId, userId);

		if (fid == null) {
			throw new AppException("查询投资记录详情失败");
		}
		return fid;
	}

	// 投资动态
	@Override
	public FinancialLoanInvestmentNewFlows investmentNewsFlow(String userId, String orderId) {
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(orderId)) {
			throw new AppException("存在请求为空的参数");
		}

		FinancialLoanInvestmentNewFlows fin = financialLoanMapper.queryInvestmentNewsFlow(orderId);

		if (fin == null) {
			throw new AppException("查询投资动态详情失败");
		}

		return fin;
	}

	// 投资还款计划
	@Override
	public Object repaymentPlan(String userId, String orderId) {
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(orderId)) {
			throw new AppException("存在请求为空的参数");
		}

		FinancialLoanRepayPlanDTO flp = financialLoanMapper.queryInvestmentRepayPlan(orderId);

		if (flp == null) {
			throw new AppException("查询投资还款计划详情失败");
		}

		return flp;
	}

	// 借款记录详情
	@Override
	public FinancialLoanCreditDetailDTO borrowRecordDetail(String userId, String borrowCode) {
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(borrowCode)) {
			throw new AppException("存在请求为空的参数");
		}

		BaseLogger.info("对接信贷系统-查询借款记录详情入参userId,borrowCode：" + userId + "," + borrowCode);
		// 调用信贷系统，获取借款记录详情
		String loanDetailJson =  applyFinancingLoanDubboService.queryLoanDetailed(borrowCode);
		BaseLogger.info("对接信贷系统-查询借款记录详情返回结果：" + loanDetailJson);

		FinancialLoanCreditDetailDTO flc = JSONObject.parseObject(loanDetailJson, FinancialLoanCreditDetailDTO.class);

		// 基本信息
		flc.setBorrowAmountLabel("借款金额");
		flc.setBorrowAmount(MoneyUtil.toYuan(flc.getBorrowAmount())+"元");
		flc.setPaymentRecordsLabel("到期应还");

		// 贷款信息
		List<Map<String, String>> loanInfo = new ArrayList<Map<String, String>>();
		loanInfo.add(buildLabelAndValueMap("借款期限", financialLoanMapper.queryLoanPeriodByBorrowCode(borrowCode)));
		loanInfo.add(buildLabelAndValueMap("应付利息","￥"+ MoneyUtil.toYuan(flc.getSumInterest())));
		flc.setLoanInfos(loanInfo);

		// 还款记录
		List<FinancialLoanCreditRepayPlanDetailDTO> fcpList = flc.getPaymentPlanRecords();
		if (CollectionUtils.isNotEmpty(fcpList)) {
			for (FinancialLoanCreditRepayPlanDetailDTO fcp : fcpList) {
				// 根据还款状态，获取不同的图标 (在数据库comm_params里添加对应数据:还款状态图标)
				String urlKey = fcp.getIsRepay().equals("0") ? "loan_repayplan_status_no" : "loan_repayplan_status_yes";
				String isRepayFlag = fcp.getIsRepay().equals("0") ? "false" : "true";
				fcp.setRepaymentStatusMarkURL(
						financialLoanMapper.getParamsValueByKeyAndGroup(urlKey, "financial_loan"));
				fcp.setIsRepaymentComplete(isRepayFlag);
				fcp.setRepaymentAmount("￥"+MoneyUtil.toYuan(fcp.getRepaymentAmount()));

				fcp.setRepaymentDate(handlerDate(fcp.getPlanTime()));
			}
			flc.setPaymentRecords(fcpList);
		} else {
			flc.setPaymentRecords(new ArrayList<FinancialLoanCreditRepayPlanDetailDTO>());
		}
		
		flc.setContractLabel("小金鱼相关合同");
		flc.setContractTitle("小金鱼相关合同");
		flc.setContractURL(financialLoanMapper.getParamsValueByKeyAndGroup("investment_contract_detail", "financial_loan"));
		flc.setContractCode(financialLoanMapper.getParamsValueByKeyAndGroup("financial_contract_code", "financial_loan"));
		
		flc.setBorrowCode(borrowCode);
		return flc;
	}

	@Override
	public Object queryCityList(String loanProductId) {

		if (StringUtils.isBlank(loanProductId)) {
			throw new AppException("贷款产品编号不能为空");
		}

		BaseLogger.info("对接信贷系统-查询城市列表入参loanProductId：" + loanProductId);
		String cityJsonStr = loanProductInfoDubboService.selectProductCityInfo(loanProductId);

		BaseLogger.info("对接信贷系统-查询城市列表返回结果：" + cityJsonStr);

		if (StringUtils.isBlank(cityJsonStr)) {
			throw new AppException("获取城市列表失败");
		}
		JSONObject objJson = JSONObject.parseObject(cityJsonStr);
		objJson.put("message", "请选择您所在的城市");
		return objJson;
	}

	
	public static void main(String[] args){
		String str = "[{'title':'如何获取用户名和密码：','content':'请至上海市公积金官网进行注册，然后使用注册时所填写的账号和密码进行登录。','url':'注册网址：|https://persons.shgjj.com/usertext.htm'}]";
		JSONArray obj = JSONObject.parseArray(str);
		System.out.println(obj.toJSONString());
	}
	
    @Override
    public FinancialLoanInitiDTO newFinancialLoanInit(String userId, String page, String productTypeCode) {
        if (StringUtils.isBlank(productTypeCode)) {
            throw new AppException("存在请求为空的参数");
        }
        ParamValidUtil.validatorUserId(userId);
        ParamValidUtil.validatorPage(page);

        // 返回结果集
        FinancialLoanInitiDTO fid = new FinancialLoanInitiDTO();


        // 调用信贷系统的入参
        UserInfoParamDTO uid = financialLoanMapper.queryUserBaseInfoByUserId(userId);

        // 1,查询信贷任务列表
        String paramStr = JSONObject.toJSONString(uid);
        BaseLogger.info("对接信贷系统-查询任务列表入参：" + paramStr);
        String taskListJsonStr = taskDetailsInfoDubboService.selectValidTaskListSimple(paramStr);
        BaseLogger.info("对接信贷系统-查询任务列表返回：" + taskListJsonStr);

        if (StringUtils.isBlank(taskListJsonStr) || taskListJsonStr.equals("[]")) {
            throw new AppException("获取任务列表失败");
        }

        // 2,信贷系统额度判断 ,可用额度
        BaseLogger.info("对接信贷系统-查询用户信用额度入参userId，productTypeCode：" + userId + "," + productTypeCode);
        int availableAmountTemp = clientInfoDubboService.queryClientCreditQuota(userId, productTypeCode);
        BaseLogger.info("对接信贷系统-查询用户信用额度：" + availableAmountTemp);

        String availableAmount = MoneyUtil.toYuan(String.valueOf(availableAmountTemp));

        fid.setAvailableCreditLabel("鱼池");
        fid.setAvailableCredit(availableAmount);
        fid.setAvailableCreditStatus("true");
        fid.setDailyInterestRate(financialLoanMapper.getParamsValueByKeyAndGroup("loan_rate", "financial_loan"));
        fid.setAvailableCreditDescription("按日计息，日利率低至");

        List<TaskDTO> taskList = handlerTaskDto(userId, taskListJsonStr, false);

        fid.setTaskList(taskList);

        // 判断信用额度是否可用
        if (availableAmount.compareTo("0") <= 0) {
            fid.setAvailableCreditStatus("false");
            fid.setAvailableCredit(taskList.get(0).getTaskPrize());
            fid.setAvailableCreditDescription("完成以下任务，立即激活小金鱼");
            fid.setDailyInterestRate("");
        }

        fid.setIsHaveInvertmentRecord(financialLoanMapper.queryIfInvested(userId));

        // 3,查询定向产品列表
        List<FinancialLoanInitiInnerProductDTO> proList = financialLoanMapper
                .queryNewFinancialLoanProductList((Integer.parseInt(page) - 1) * 10);

        if (proList == null) {
            proList = new ArrayList<FinancialLoanInitiInnerProductDTO>();
        }

        fid.setRecords(proList);

        if (proList.size() >= 10) {
            fid.setIsMore("true");
        }

        return fid;
    }

    @Override
    public Map<String, Object> newTaskList(String userId, String page) {
        ParamValidUtil.validatorUserId(userId);
        ParamValidUtil.validatorPage(page);

        Map<String, Object> resultMap = new HashMap<String, Object>();
        BaseLogger.info("对接信贷系统-获取任务列表入参userId：" + userId);
        // 调用信贷系统：获取任务列表信息
        String taskListJsonStr = taskDetailsInfoDubboService.selectValidTaskListDetailed(userId);
        BaseLogger.info("对接信贷系统-获取任务列表返回结果：" + taskListJsonStr);

        List<TaskDTO> taskList = handlerTaskDto(userId, taskListJsonStr, true);

        // 组装结果集
        if (CollectionUtils.isEmpty(taskList) || taskList.size() < 10) {
            resultMap.put("isMore", "false");
        } else {
            resultMap.put("isMore", "true");
        }
        resultMap.put("records", taskList);
        return resultMap;
    }
}
