package com.zhuanyi.vjwealth.wallet.mobile.credit.server.impl;


import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.fab.core.exception.service.AppException;
import com.fab.core.logger.BaseLogger;
import com.zhuanyi.vjwealth.loan.order.webservice.IMyBorrowDubboService;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.AccumulationOrderPlanDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.AccumulationOrderRepayListDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.LoanOrderDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.LoanProductsDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.reqDTO.AccumulationOrderDetailReqDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.reqDTO.AccumulationOrderRepayDetailReqDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.reqDTO.AllLoanOrderReqDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.resDTO.*;
import com.zhuanyi.vjwealth.loan.wageAdvance.webservice.IApplyWageAdvanceDubboService;
import com.zhuanyi.vjwealth.wallet.mobile.common.util.DateCommonUtils;
import com.zhuanyi.vjwealth.wallet.mobile.common.util.MoneyCommonUtils;
import com.zhuanyi.vjwealth.wallet.mobile.common.util.NumberCommonUtils;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.IMyAllLoanQueryService;

import com.zhuanyi.vjwealth.wallet.mobile.credit.server.constants.LoanOrderStatusConstant;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.constants.LoanProductIdConstant;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.myloan.*;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.mapper.FinancialLoanMapper;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.mapper.IMyAllLoanQueryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 我的借款模块
 *
 */
@Service
public class MyAllLoanQueryServiceImpl implements IMyAllLoanQueryService {

	@Autowired
	private IMyAllLoanQueryMapper myAllLoanQueryMapper;

	@Autowired
	private IApplyWageAdvanceDubboService applyWageAdvanceDubboService;

	@Autowired
	private FinancialLoanMapper financialLoanMapper;

	@Autowired
	private IMyBorrowDubboService myBorrowDubboService;

	@Override
	public Object queryLoanRecordByConditions(String userId, String borrowStatus, String productSearch, String page) {
		if(StringUtils.isBlank(userId) || StringUtils.isBlank(borrowStatus) || StringUtils.isBlank(productSearch) || StringUtils.isBlank(page)){
			throw new AppException("参数不完整");
		}

		//返回结果
		MyAllLoanInitDTO initDTO = new MyAllLoanInitDTO();

		ProductsInfoResDTO productInfo = applyWageAdvanceDubboService.mineCreditInvestigationWay();
		BaseLogger.info("调用信贷系统查询信贷产品列表结果:"+ JSONObject.toJSONString(productInfo));

		List<LoanProductsDTO> productList = productInfo.getProductsInfoList();

		//产品筛选项列表
		List<Map<String,String>>  productSearchList = new ArrayList<>();
		productSearchList.add(buildKeyAndValueMap("all","所有产品"));
		if(!CollectionUtils.isEmpty(productList)){
			for(LoanProductsDTO pd:productList){
				String productId = pd.getProductId();
				if(productId.equals(LoanProductIdConstant.JN)){
					productSearchList.add(buildKeyAndValueMap(pd.getProductId(),pd.getProductName()));
				}else if(productId.equals(LoanProductIdConstant.BLZX)){
					productSearchList.add(buildKeyAndValueMap(pd.getProductId(),pd.getProductName()));
				}else if(productId.equals(LoanProductIdConstant.LTB)){
					productSearchList.add(buildKeyAndValueMap(pd.getProductId(),pd.getProductName()));
				}else if(productId.equals(LoanProductIdConstant.GZXX)){
					productSearchList.add(buildKeyAndValueMap(pd.getProductId(),pd.getProductName()));
				}
			}
		}

		//设置产品条件选项
		initDTO.setProductSearch(productSearchList);

		AllLoanOrderInfoResDTO allOrderAmount = applyWageAdvanceDubboService.getAllLoanOrderInfo(userId);
		BaseLogger.info("调用信贷系统查询用户的所有未还本金及未还利息:"+ JSONObject.toJSONString(allOrderAmount));
		List<Map<String,String>>  amountInfoList = new ArrayList<>();
		amountInfoList.add(buildLabelAndValueMap("待还本金(元)",allOrderAmount.getNoRepayPrincipal()));
		amountInfoList.add(buildLabelAndValueMap("待还利息(元)",allOrderAmount.getNoRepayInterest()));

		//设置未还金额的汇总信息
		initDTO.setBorrowRecordSummary(amountInfoList);

		//入参
		AllLoanOrderReqDTO reqDTO = new AllLoanOrderReqDTO(userId,Integer.parseInt(page),borrowStatus,productSearch);
		BaseLogger.info("调用信贷系统查询用户的所有借款列表入参:"+ JSONObject.toJSONString(reqDTO));
		AllLoanOrderResDTO orderInfo = applyWageAdvanceDubboService.getAllLoanOrderList(reqDTO);
		BaseLogger.info("调用信贷系统查询用户的所有借款列表查询结果:"+ JSONObject.toJSONString(orderInfo));

		List<LoanOrderDTO> orderList = orderInfo.getLoanOrderList();
		initDTO.setIsMore("false");
		if((!CollectionUtils.isEmpty(orderList)) && orderList.size()==10){
			initDTO.setIsMore("true");
		}

		//设置返回订单信息列表
		List<MyAllLoanListDTO> orderInfoList = new ArrayList<>();
		if(!CollectionUtils.isEmpty(orderList)){
			for(LoanOrderDTO lod:orderList){
				MyAllLoanListDTO loan = new MyAllLoanListDTO();
				loan.setProductCode(lod.getProductId());
				loan.setBorrowCode(lod.getBorrowCode());
				loan.setBorrowAmount(MoneyCommonUtils.handlerMoneyStr(lod.getBorrowAmount()));
				loan.setBorrowAmountDescription("借款<font color=#4AC0F0 >"+lod.getBorrowAmount()+"</font>元");
				loan.setRepaymentStatus(LoanOrderStatusConstant.getValue(lod.getRepaymentStatus()));
				loan.setRepaymentStatusMarkURL("");
				loan.setProjectTermLabel("期限");
				loan.setProjectTerm("<font color=#FFBD30 >"+lod.getProjectTerm()+"</font>");

				loan.setYearRateLabel("日利率");
				String dayRateDesc = "<font color=#FFBD30 >"+ NumberCommonUtils.handleNumberStr(lod.getDayRate()+"%")+"</font>";
				if(lod.getProductId().equals(LoanProductIdConstant.BLZX)){
					dayRateDesc = "低至"+dayRateDesc;
				}
				loan.setYearRate(dayRateDesc);

				loan.setFromInterestTimeLabel("起息时间");
				loan.setFromInterestTime(lod.getFromInterestTime());
				loan.setExpirationDateLabel("到期时间");
				loan.setExpirationDate(lod.getExpirationDate());
				loan.setProductName(LoanProductIdConstant.getValue(lod.getProductId()));
				loan.setPlanId(lod.getPlanId());

				//如果是流通宝，则获取对应的理财信息
				if(!StringUtils.isBlank(lod.getProductCode())){
					String productName = myAllLoanQueryMapper.queryProductNameByOrderNo(lod.getProductCode());
					loan.setProductName(loan.getProductName()+"("+productName+")");
				}
				orderInfoList.add(loan);
			}
		}

		initDTO.setRecords(orderInfoList);

		return initDTO;
	}


	@Override
	public MyLoanDetailDTO repaymentDetailForFund(String userId, String borrowCode, String loanProductId) {

		if(StringUtils.isBlank(userId) || StringUtils.isBlank(borrowCode) || StringUtils.isBlank(loanProductId)){
			throw new AppException("参数不完整");
		}

		//入参
		AccumulationOrderDetailReqDTO reqDTO = new AccumulationOrderDetailReqDTO(userId,borrowCode,loanProductId);
		BaseLogger.info("调用信贷系统查询我的借款的借款详情入参"+JSONObject.toJSONString(reqDTO));
		AccumulationOrderDetailResDTO resDTO = applyWageAdvanceDubboService.getAccumulationOrderDetail(reqDTO);
		BaseLogger.info("调用信贷系统查询我的借款的借款详情结果"+JSONObject.toJSONString(resDTO));

		//返回结果
		MyLoanDetailDTO returnResultDto = new MyLoanDetailDTO();

		returnResultDto.setBorrowCode(borrowCode);
		returnResultDto.setRepaymentStatusDesc(resDTO.getOrderStatusDesc());
		returnResultDto.setExpirationDateDesc(resDTO.getDueDate()+"到期");

		//设置还款信息
		List<Map<String,String>> repayMap = new ArrayList<>();
		repayMap.add(buildLabelAndValueMap("待还本金：",resDTO.getNoRepayPrincipal()));
		repayMap.add(buildLabelAndValueMap("待还息费：",resDTO.getNoRepayExpenses()));
		returnResultDto.setRepaymentInfos(repayMap);

		//设置贷款产品信息
		List<Map<String,String>> productMap = new ArrayList<>();
		String dayRateDesc = resDTO.getDayRate()+"%";
		if(loanProductId.equals(LoanProductIdConstant.BLZX)){
			dayRateDesc = "低至"+dayRateDesc;
		}
		productMap.add(buildLabelAndValueMap("日利率",dayRateDesc));
		productMap.add(buildLabelAndValueMap("借款期限",resDTO.getProjectTerm()));
		productMap.add(buildLabelAndValueMap("还款方式",resDTO.getRepayTypeDesc()));
		returnResultDto.setBorrowProductInfos(productMap);

		//设置还款日期信息
		List<Map<String,String>> repayDteMap = new ArrayList<>();
		repayDteMap.add(buildLabelAndValueMap("起止日期",resDTO.getStartAndEndDate()));
		repayDteMap.add(buildLabelAndValueMap("还款日","每月"+resDTO.getRepayDay()+"号"));
		returnResultDto.setBorrowDateInfos(repayDteMap);

		//设置还款计划头信息列表
		List<String> titleList = new ArrayList<>();
		titleList.add("还款日");
		titleList.add("还款金额");
		titleList.add("状态");
		returnResultDto.setPaymentRecordTitles(titleList);


		boolean isRepayDetail = false;

		//设置还款计划信息
		List<MyLoanDetailInnerListDTO> repayPlan = new ArrayList<>();
		List<AccumulationOrderPlanDTO> repayPlanList = resDTO.getRepayPlanList();
		if(!CollectionUtils.isEmpty(repayPlanList)){
			for(AccumulationOrderPlanDTO ap:repayPlanList){
				MyLoanDetailInnerListDTO md = new MyLoanDetailInnerListDTO();
				md.setRepaymentAmount("￥"+ ap.getPlanRepayAmount());
				md.setRepaymentDate(ap.getPlanRepayDate());

				String planStatus = ap.getPlanStatus();

				md.setRepaymentStatus(LoanOrderStatusConstant.getValue(planStatus));

				if(planStatus.equals(LoanOrderStatusConstant.REPAY_END) || planStatus.equals(LoanOrderStatusConstant.REPAY_CLEAR)
						|| planStatus.equals(LoanOrderStatusConstant.REPAY_OVERDUE_END)){
					isRepayDetail = true;
				}

				repayPlan.add(md);
			}
		}

		returnResultDto.setPaymentRecords(repayPlan);

		returnResultDto.setContractLabel("查看合同");
		returnResultDto.setContractTitle("借款合同");

		String contractUrl = "";
		if(loanProductId.equals(LoanProductIdConstant.BLZX)){
			String commUrl =  financialLoanMapper.getParamsValueByKeyAndGroup("loan_common_url","loan_comm");
			contractUrl = commUrl+"/api/v4.0/app/credit/fund/contract"+"?borrowCode="+borrowCode;
		}else if(loanProductId.equals(LoanProductIdConstant.JN)){
			contractUrl = financialLoanMapper.getParamsValueByKeyAndGroup("cana_contract_url","cana_loan_setting");
		}
		returnResultDto.setContractURL(contractUrl);

		returnResultDto.setIsRepayDetail(isRepayDetail?"Y":"N");

		return returnResultDto;
	}


	@Override
	public Object repaymentDetailForCana(String userId, String borrowCode,String loanProductId) {

		if(StringUtils.isBlank(userId) || StringUtils.isBlank(borrowCode) || StringUtils.isBlank(loanProductId)){
			throw new AppException("参数不完整");
		}

		//入参
		AccumulationOrderRepayDetailReqDTO reqDTO = new AccumulationOrderRepayDetailReqDTO(userId,borrowCode,loanProductId);
		BaseLogger.info("调用信贷系统查询我的借款的还款明细入参"+JSONObject.toJSONString(reqDTO));
		AccumulationOrderRepayDetailResDTO resDTO = applyWageAdvanceDubboService.getAccumulationOrderRepayDetail(reqDTO);
		BaseLogger.info("调用信贷系统查询我的借款的的还款明细结果"+JSONObject.toJSONString(resDTO));

		//返回结果
		MyLoanRepayDetailDTO repayDetailDTO = new MyLoanRepayDetailDTO();

		repayDetailDTO.setBorrowAmountLabel("已还本金");
		repayDetailDTO.setBorrowAmount(resDTO.getRepayPrincipal());

		//设置借款信息
		List<Map<String,String>> borrowMap = new ArrayList<>();
		borrowMap.add(buildLabelAndValueMap("类型","借款"));
		borrowMap.add(buildLabelAndValueMap("还款期数",resDTO.getRepayTerm()+"期"));
		borrowMap.add(buildLabelAndValueMap("已还利息",resDTO.getRepayExpenses()+"元"));
		repayDetailDTO.setBorrowInfo(borrowMap);

		//设置还款记录信息
		List<Map<String,String>> repayRecord = new ArrayList<>();
		List<AccumulationOrderRepayListDTO> repayList = resDTO.getRepayList();
		if(!CollectionUtils.isEmpty(repayList)){
			for(AccumulationOrderRepayListDTO al:repayList){
				repayRecord.add(buildLabelAndValueMap(al.getActualRepayDate(),al.getActualRepayAmount()+"元"));
			}
		}
		repayDetailDTO.setPaymentRecords(repayRecord);

		//是否显示罚息
		if(resDTO.getOverdueDays().compareTo("0") > 0){
			repayDetailDTO.setIsShowCheckPaymentDetail("true");
		}else{
			repayDetailDTO.setIsShowCheckPaymentDetail("false");
		}

		repayDetailDTO.setCheckPaymentDetailTitle("查看补缴详情");

		//设置罚息信息
		List<MyLoanRepayDueDetailDTO> repayDueInfoList = new ArrayList<>();
		MyLoanRepayDueDetailDTO dueDetailDTO = new MyLoanRepayDueDetailDTO();
		dueDetailDTO.setCalculationMethodDescription("");
		List<Map<String,String>> dueMap = new ArrayList<>();
		dueMap.add(buildLabelAndValueMap("逾期时间",resDTO.getOverdueDays()+"天"));
		dueMap.add(buildLabelAndValueMap("逾期罚息","￥"+resDTO.getPenalty()));
		dueDetailDTO.setPaymentInfos(dueMap);
		repayDueInfoList.add(dueDetailDTO);

		repayDetailDTO.setPaymentDetailList(repayDueInfoList);

		return repayDetailDTO;
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

	private Map<String, String> buildKeyAndValueMap(final String key, final String value) {
		return new HashMap<String, String>() {
			private static final long serialVersionUID = 1L;

			{
				put("key", key);
				put("value", value);
			}
		};
	}

}



