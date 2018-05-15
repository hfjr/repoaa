package com.zhuanyi.vjwealth.wallet.mobile.bill.server.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.fab.core.exception.service.AppException;
import com.fab.core.logger.BaseLogger;
import com.zhuanyi.vjwealth.loan.order.webservice.IMyBorrowDubboService;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.reqDTO.LoanOrderDetailReqDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.resDTO.LoanOrderDetailResDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.webservice.IApplyWageAdvanceDubboService;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.ILoanBillDetailQueryService;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.dto.LoanApplyAndRepayDetailDTO;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.dto.VFinacialFrozenDetailDTO;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.dto.VFinacialFrozenDetailInnerDTO;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.dto.WageRepayOrderDTO;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.enums.OrderTypeEnum;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.mapper.IBillDetailQueryMapper;
import com.zhuanyi.vjwealth.wallet.mobile.common.util.DateCommonUtils;

@Service
public class LoanBillDetailQueryService implements ILoanBillDetailQueryService {
	@Autowired
	private IMyBorrowDubboService myBorrowDubboService;
	@Autowired
	private IBillDetailQueryMapper billDetailQueryMapper;
	@Autowired
	private IApplyWageAdvanceDubboService applyWageAdvanceDubboService;

	//通用方法
	private Map<String,String> bulidMap(String title, String value){
		Map<String,String> map = new HashMap<String,String>();
		map.put("title",title);
		map.put("value",value);
		return map;
	}

	@Override
	public LoanApplyAndRepayDetailDTO pledgeLoanApplyDetail(String userId, String orderId,String billType) {
		LoanApplyAndRepayDetailDTO la = new LoanApplyAndRepayDetailDTO();
		la.setAmoutTitle("金额");
        la.setPageTitle("流通宝借款详情");
		la.setSubContent("");
		la.setSubTitle("");

		//借款编号
		String loanId = billDetailQueryMapper.queryOrderTradeChannelId(userId,orderId);

		BaseLogger.info(String.format("查询流通宝借款账单详情，调用信贷系统入参：userId:%s,loanId:%s",userId,loanId));
		Map<String,String> resultMap = myBorrowDubboService.ltbBorrowDetail(userId,loanId);
		JSONObject resultObj = JSONObject.parseObject(JSONObject.toJSONString(resultMap));//日期用map接收后，不是時間戳，轉成json后，變成時間戳
		BaseLogger.info("查询流通宝借款账单详情，调用信贷系统返回结果："+resultObj.toJSONString() );

		if(resultMap.isEmpty()){
			throw new AppException("用户账单数据异常");
		}

		la.setAmount(resultObj.getString("loanAmount"));
		List<Map<String,String>> detailInfo = new ArrayList<Map<String,String>>();
		detailInfo.add(bulidMap("借款金额",resultObj.getString("loanAmount")));
		detailInfo.add(bulidMap("利息",resultObj.getString("planInterest")));
		detailInfo.add(bulidMap("合同期限", DateCommonUtils.handlerTimeStamp(resultObj.get("startDate"),"yyyy/MM/dd")+"-"+DateCommonUtils.handlerTimeStamp(resultObj.get("endDate"),"yyyy/MM/dd")));
		la.setDetailInfo(detailInfo);
		return la;
	}

	@Override
	public LoanApplyAndRepayDetailDTO pledgeLoanRepayDetail(String userId, String orderId, String orderType) {
		LoanApplyAndRepayDetailDTO la = new LoanApplyAndRepayDetailDTO();
		la.setPageTitle("流通宝还款详情");
		la.setAmoutTitle("金额");
		la.setSubTitle("流通宝");

		String planId = billDetailQueryMapper.queryOrderTradeChannelId(userId,orderId);

		BaseLogger.info(String.format("查询流通宝還款账单详情，调用信贷系统入参：userId:%s,planId:%s",userId,planId));
		Map<String,String> resultMap = myBorrowDubboService.ltbRepayDetail(userId,planId);
		BaseLogger.info("查询流通宝還款账单详情，调用信贷系统返回结果："+ JSONObject.toJSONString(resultMap));

		if(resultMap.isEmpty()){
			throw new AppException("用户账单数据异常");
		}
		la.setAmount(resultMap.get("repayTotal"));

		List<Map<String,String>> detailInfo = new ArrayList<Map<String,String>>();

		//提前还款
		if(("early_repay_ltb_loan").equals(orderType)){
			la.setSubContent("提前还款");
			detailInfo.add(bulidMap("付款方式","余额"));
		}else if(("due_repay_ltb_loan").equals(orderType)){
			//到期还款
			la.setSubContent("到期还款");
			detailInfo.add(bulidMap("付款方式","到期余额自动扣款"));
		}

		detailInfo.add(bulidMap("本金",resultMap.get("repayPrincipal")));
		detailInfo.add(bulidMap("利息",resultMap.get("repayInterest")));
		detailInfo.add(bulidMap("手续费",resultMap.get("poundage")));
		la.setDetailInfo(detailInfo);

		return la;
	}

	@Override
	public LoanApplyAndRepayDetailDTO canaLoanApplyDetail(String userId, String orderId, String billType) {
		LoanApplyAndRepayDetailDTO la = new LoanApplyAndRepayDetailDTO();
		la.setPageTitle("锦囊借款详情");
		la.setAmoutTitle("金额");
		la.setSubContent("");
		la.setSubTitle("");

		String loanId = billDetailQueryMapper.queryOrderTradeChannelId(userId,orderId);

		BaseLogger.info(String.format("查询Cana借款账单详情，调用信贷系统入参：userId:%s,planId:%s",userId,loanId));
		Map<String,String> resultMap = myBorrowDubboService.canaBorrowDetail(userId,loanId);
		JSONObject resultObj = JSONObject.parseObject(JSONObject.toJSONString(resultMap));//日期用map接收后，不是時間戳，轉成json后，變成時間戳
		BaseLogger.info("查询Cana借款账单详情，调用信贷系统返回结果："+ resultObj.toJSONString());

		if(resultMap.isEmpty()){
			throw new AppException("用户账单数据异常");
		}

		la.setAmount(resultObj.getString("planPrincipal"));

		List<Map<String,String>> detailInfo = new ArrayList<Map<String,String>>();
		detailInfo.add(bulidMap("借款金额",resultObj.getString("planPrincipal")));
		detailInfo.add(bulidMap("利息",resultObj.getString("planInterest")));
		detailInfo.add(bulidMap("借款期限", DateCommonUtils.handlerTimeStamp(resultObj.get("startDate"),"yyyy/MM/dd")+"-"+DateCommonUtils.handlerTimeStamp(resultObj.get("endDate"),"yyyy/MM/dd")));
		la.setDetailInfo(detailInfo);
		return la;
	}

	@Override
	public LoanApplyAndRepayDetailDTO canaLoanRepayDetail(String userId, String orderId, String billType) {
		LoanApplyAndRepayDetailDTO la = new LoanApplyAndRepayDetailDTO();
		la.setPageTitle("锦囊还款详情");
		la.setAmoutTitle("金额");
		la.setSubTitle("");
		la.setSubContent("");

		String planId = billDetailQueryMapper.queryOrderTradeChannelId(userId,orderId);

		BaseLogger.info(String.format("查询Cana還款账单详情，调用信贷系统入参：userId:%s,planId:%s",userId,planId));
		Map<String,String> resultMap = myBorrowDubboService.canaRepayDetail(userId,planId);
		BaseLogger.info("查询Cana還款账单详情，调用信贷系统返回结果："+ JSONObject.toJSONString(resultMap));

		if(resultMap.isEmpty()){
			throw new AppException("用户账单数据异常");
		}

		la.setAmount(resultMap.get("repayTotal"));

		List<Map<String,String>> detailInfo = new ArrayList<Map<String,String>>();

		detailInfo.add(bulidMap("付款方式","余额"));
		detailInfo.add(bulidMap("本金",resultMap.get("repayPrincipal")));
		detailInfo.add(bulidMap("利息",resultMap.get("repayInterest")));
		la.setDetailInfo(detailInfo);

		return la;
	}

	@Override
	public VFinacialFrozenDetailDTO vFinacialFrozenDetail(String userId, String planId, String billType) {
		VFinacialFrozenDetailDTO vd = new VFinacialFrozenDetailDTO();
        vd.setPageTitle("定期理财冻结详情");
		vd.setSubTitle("类型");
		vd.setSubContent("冻结(定期理财)");

		//获取冻结金额的汇总信息
		VFinacialFrozenDetailInnerDTO frozenInfo = billDetailQueryMapper.queryOrderPlanInfo(userId,planId);
		if(frozenInfo == null){
			throw new AppException("账单信息数据异常");
		}
		vd.setAmoutTitle("金额");
		vd.setAmount(frozenInfo.getTotalAmount());//冻结本息

		//理财信息
		vd.setFinacialTitle(frozenInfo.getProductCode());
		List<Map<String,String>> finacialInfo = new ArrayList<Map<String,String>>();
		finacialInfo.add(bulidMap("冻结本金",frozenInfo.getPrincipal()));
		finacialInfo.add(bulidMap("冻结收益",frozenInfo.getInterest()));
		finacialInfo.add(bulidMap("投资期限",frozenInfo.getInvestmentPeriods()));
		finacialInfo.add(bulidMap("还款方式",frozenInfo.getRepaymentType()));
		vd.setFinacialInfo(finacialInfo);

		//贷款信息
		String loanId = billDetailQueryMapper.queryLoanIdByOrderNo(userId,frozenInfo.getOrderNo());
		BaseLogger.info(String.format("查询流通宝借款账单详情，调用信贷系统入参：userId:%s,loanId:%s",userId,loanId));
		Map<String,String> resultMap = myBorrowDubboService.ltbBorrowDetail(userId,loanId);
		JSONObject resultObj = JSONObject.parseObject(JSONObject.toJSONString(resultMap));//日期用map接收后，不是時間戳，轉成json后，變成時間戳
		BaseLogger.info("查询流通宝借款账单详情，调用信贷系统返回结果："+resultObj.toJSONString());

		vd.setLoanTitle("流通宝");
		List<Map<String,String>> loanInfo = new ArrayList<Map<String,String>>();
		loanInfo.add(bulidMap("借款金额",resultObj.getString("loanAmount")));
		loanInfo.add(bulidMap("借款利息",resultObj.getString("planInterest")));
		loanInfo.add(bulidMap("已还本金",resultObj.getString("sumRepayPrincipal")));
		loanInfo.add(bulidMap("已还利息",resultObj.getString("sumRepayInterest")));
		loanInfo.add(bulidMap("合同期限",DateCommonUtils.handlerTimeStamp(resultObj.get("startDate"),"yyyy/MM/dd")+"-"+DateCommonUtils.handlerTimeStamp(resultObj.get("endDate"),"yyyy/MM/dd")));
		loanInfo.add(bulidMap("还款方式",resultObj.getString("repayTypeName")));
		vd.setLoanInfo(loanInfo);

        //返回结果
		return vd;
	}


	@Override
	public LoanApplyAndRepayDetailDTO wageAdvanceApplyDetail(String userId, String orderId, String billType) {
		LoanApplyAndRepayDetailDTO la = new LoanApplyAndRepayDetailDTO();
		la.setPageTitle("工资先享借款详情");
		la.setAmoutTitle("金额");
		la.setSubContent("");
		la.setSubTitle("");

		String loanId = billDetailQueryMapper.queryOrderTradeChannelId(userId,orderId);

		BaseLogger.info(String.format("查询工资先享账单详情，调用信贷系统入参：userId:%s,planId:%s",userId,loanId));
		LoanOrderDetailReqDTO reqDTO = new LoanOrderDetailReqDTO();
		reqDTO.setUserId(userId);
		reqDTO.setLoanId(loanId);
		LoanOrderDetailResDTO resDTO = applyWageAdvanceDubboService.getLoanOrderDetail(reqDTO);
		BaseLogger.info("查询工资先享借款账单详情，调用信贷系统返回结果："+ JSONObject.toJSONString(resDTO));

		if(resDTO == null){
			throw new AppException("用户账单数据异常");
		}

		la.setAmount(resDTO.getLoanAmount()+"元");

		List<Map<String,String>> detailInfo = new ArrayList<Map<String,String>>();
		detailInfo.add(bulidMap("借款金额",resDTO.getLoanAmount()+"元"));
		detailInfo.add(bulidMap("利息",resDTO.getPlanInterest()+"元"));
		detailInfo.add(bulidMap("借款期限", DateCommonUtils.handlerDate(resDTO.getStartDate(),"yyyy/MM/dd")+"-"+DateCommonUtils.handlerDate(resDTO.getEndDate(),"yyyy/MM/dd")));
		la.setDetailInfo(detailInfo);
		return la;
	}



	@Override
	public LoanApplyAndRepayDetailDTO wageAdvanceRepayDetail(String userId, String orderId, String billType) {

		BaseLogger.info(String.format("查询工资先享還款账单详情入参：userId:%s,bizId:%s", userId, orderId));

		List<Map<String, String>> detailInfo = new ArrayList<Map<String, String>>();
		// String bizId = billDetailQueryMapper.queryOrderTradeChannelId(userId,orderId);

		WageRepayOrderDTO repayDto = billDetailQueryMapper.queryWageRepayBillDetail(userId, orderId);
		if (repayDto == null) {
			throw new AppException("用户账单数据异常");
		}

		LoanApplyAndRepayDetailDTO la = new LoanApplyAndRepayDetailDTO();
		la.setPageTitle("还款详情");
		la.setAmoutTitle("金额");
		la.setSubTitle("工资先享");

		if(billType.equals(OrderTypeEnum.DUE_REPAY_WAGE_LOAN.getValue())){
			//余额到期还款
			//repayDto = applyWageAdvanceDubboService.repayOrderDetailEarly(userId,bizId);
			la.setSubContent("到期还款");
			detailInfo.add(bulidMap("付款方式","到期余额自动扣款"));
		}else if(billType.equals(OrderTypeEnum.DUE_REPAY_WAGE_LOAN_WITHHOLD.getValue())){
			//银行卡到期还款
			//repayDto = applyWageAdvanceDubboService.repayOrderDetailEarly(userId,bizId);
			la.setSubContent("到期还款");
			detailInfo.add(bulidMap("付款方式","到期银行卡自动扣款"));
		}else if(billType.equals(OrderTypeEnum.EARLY_REPAY_WAGE_LOAN.getValue())){
			//余额提前还款
//			int count = billDetailQueryMapper.queryRepayCountByOrderNo(userId,orderId);
//			repayDto = applyWageAdvanceDubboService.repayOrderDetailEarly(userId,bizId+"-"+(count+1));
			la.setSubContent("提前还款");
			detailInfo.add(bulidMap("付款方式","余额"));
		}else if(billType.equals(OrderTypeEnum.EARLY_REPAY_WAGE_LOAN_WITHHOLD.getValue())){
			//银行卡提前还款
			/*int count = billDetailQueryMapper.queryRepayCountByOrderNo(userId,orderId);
			repayDto = applyWageAdvanceDubboService.repayOrderDetailEarly(userId,bizId+"-"+(count+1));*/
			la.setSubContent("提前还款");
			detailInfo.add(bulidMap("付款方式","银行卡"));
		}


		la.setAmount(repayDto.getRepayTotal());

		detailInfo.add(bulidMap("本金",repayDto.getRepayPrincipal()));
		detailInfo.add(bulidMap("利息",repayDto.getRepayInterest()));
		detailInfo.add(bulidMap("手续费",repayDto.getCounterFee()));
		if(StringUtils.isNotBlank(repayDto.getPenalty()) && !"0.00元".equals(repayDto.getPenalty())){
			detailInfo.add(bulidMap("罚息",repayDto.getPenalty()));
		}
		if(StringUtils.isNotBlank(repayDto.getCardNo())){
			detailInfo.add(bulidMap("还款卡号",repayDto.getCardNo()));
		}

		la.setDetailInfo(detailInfo);

		BaseLogger.info("查询工资先享還款账单详情，调用信贷系统返回结果："+ JSONObject.toJSONString(repayDto));

		return la;
	}
}
