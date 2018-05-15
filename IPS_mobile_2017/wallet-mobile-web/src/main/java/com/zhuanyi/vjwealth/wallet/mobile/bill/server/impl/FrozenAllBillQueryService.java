package com.zhuanyi.vjwealth.wallet.mobile.bill.server.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.fab.core.logger.BaseLogger;
import com.zhuanyi.vjwealth.loan.order.webservice.IMyBorrowDubboService;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.dto.BatchApplyDetailDTO;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.dto.LoanRepayBillDetailDTO;
import com.zhuanyi.vjwealth.wallet.mobile.common.util.MoneyCommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fab.core.exception.service.AppException;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.IBillTemplateService;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.dto.BillListQueryDTO;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.dto.RfBillDetailDTO;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.enums.OrderTypeEnum;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.impl.support.AbstractBillTemplateService;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.mapper.IBillDetailQueryMapper;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.mapper.IBillListQueryMapper;
import com.zhuanyi.vjwealth.wallet.mobile.common.server.service.IOrderDateHelperService;

@Service("frozenAllBillQueryService")
public class FrozenAllBillQueryService extends AbstractBillTemplateService implements IBillTemplateService{
	
	@Autowired
	private IBillListQueryMapper billListQueryMapper;
	
	@Autowired
	private IBillDetailQueryMapper billDetailQueryMapper;
	
	@Autowired
	private IOrderDateHelperService orderDateHelperService;

	@Autowired
	private IMyBorrowDubboService myBorrowDubboService;
	
	@Override
	public Map<String, Object> getBillList(String userId, String page) {
		
		return super.getBillPageList(userId, page);
	}
	
	public List<BillListQueryDTO> getListRows(String userId, int pageIndex) {
		return billListQueryMapper.getFrozenAllBillListByUserIdAndType(userId,pageIndex);
	}
	
	public Object getBillDetail(String orderId, String billType) {
		
		//.ma申购v理财
		if(billType.equals(OrderTypeEnum.APPLY_MA_TO_RF.getValue())){
			return getMaToRfBillDetail(orderId);
		}

		//.流通宝还款,锦囊还款,增加工资先享查询
		if(billType.equals(OrderTypeEnum.DUE_REPAY_LTB_LOAN.getValue())||billType.equals(OrderTypeEnum.EARLY_REPAY_LTB_LOAN.getValue())
				||billType.equals(OrderTypeEnum.DUE_REPAY_CANA_LOAN.getValue())
				||billType.equals(OrderTypeEnum.DUE_REPAY_WAGE_LOAN.getValue())//工资先享银行卡
				||billType.equals(OrderTypeEnum.EARLY_REPAY_WAGE_LOAN.getValue())
				||billType.equals(OrderTypeEnum.DUE_REPAY_WAGE_LOAN_WITHHOLD.getValue())
				||billType.equals(OrderTypeEnum.EARLY_REPAY_WAGE_LOAN_WITHHOLD.getValue())){
			return getLoanRepayBillDetail(orderId);
		}

		return null;
	}

	//ma转到v1
	private RfBillDetailDTO getMaToRfBillDetail(String orderId) {
		//1.获取账单基本信息
		RfBillDetailDTO wdd = billDetailQueryMapper.getMaToRfBillDetail(orderId);
		if(wdd == null){
			throw new AppException("账单信息不存在");
		}
		//2.获取操作流程信息
		List<Map<String,String>> processInfoList = this.getOperateProcessInfo(wdd.getCreateDate());
		wdd.setOperateStateInfos(processInfoList);
		//3.返回结果
		return wdd;

	}

    //锦囊，流通宝还款
	private LoanRepayBillDetailDTO getLoanRepayBillDetail(String orderId) {
		//1.获取账单基本信息
		LoanRepayBillDetailDTO ld = billDetailQueryMapper.getLoanRepayBillDetail(orderId);
		if(ld == null){
			throw new AppException("账单信息不存在");
		}

		String userId = ld.getUserId();
		String planId = ld.getBizId();
		String orderType = ld.getOrderType();

		BaseLogger.info(String.format("查询流通寶、錦囊借款账单详情，调用信贷系统入参：userId:%s,bizId:%s",ld.getUserId(),ld.getBizId()));
		String amount = "";
		if(orderType.equals(OrderTypeEnum.DUE_REPAY_LTB_LOAN.getValue())||orderType.equals(OrderTypeEnum.EARLY_REPAY_LTB_LOAN.getValue())){
			ld.setPageTitle("流通宝还款详情");
			Map<String,String> resultMap = myBorrowDubboService.ltbRepayDetail(userId,planId);
			BaseLogger.info("查询Cana借款账单详情，调用信贷系统返回结果："+ JSONObject.toJSONString(resultMap));
			if(resultMap.isEmpty()){
				throw new AppException("账单信息不存在");
			}
			amount = resultMap.get("loanPrincipal");
		}else if(orderType.equals(OrderTypeEnum.DUE_REPAY_CANA_LOAN.getValue())){
			ld.setPageTitle("锦囊还款详情");
			Map<String,String> resultMap = myBorrowDubboService.canaRepayDetail(userId,planId);
			BaseLogger.info("查询Cana借款账单详情，调用信贷系统返回结果："+ JSONObject.toJSONString(resultMap));
			if(resultMap.isEmpty()){
				throw new AppException("账单信息不存在");
			}
			amount = resultMap.get("loanPrincipal");
		}else if(orderType.equals(OrderTypeEnum.DUE_REPAY_WAGE_LOAN.getValue())
				||orderType.equals(OrderTypeEnum.EARLY_REPAY_WAGE_LOAN.getValue())
				||orderType.equals(OrderTypeEnum.DUE_REPAY_WAGE_LOAN_WITHHOLD.getValue())//工资先享银行卡
				||orderType.equals(OrderTypeEnum.EARLY_REPAY_WAGE_LOAN_WITHHOLD.getValue())){//工资先享银行卡提前
			amount = billDetailQueryMapper.queryLoanApplyAmountByOrderNo(ld.getRelOrderNo());
		}

		ld.setLoanAmountLabel("借款本金");
		ld.setLoanAmount(MoneyCommonUtils.handlerMoneyStr(amount));//借款金额

		String createDate = ld.getCreateDate();
		//当日处理完成
		String confirmDate = orderDateHelperService.getCurrentDay(createDate);

		//2.获取操作流程信息
		List<Map<String,String>> processInfoList = super.getOperateProcessInfo(ld.getCreateDate(),"还款完成",confirmDate,"no");
		ld.setOperateStateInfos(processInfoList);
		//3.返回结果
		return ld;
		
	}
	
	public List<Map<String,String>> getOperateProcessInfo(String orderDate){
		List<Map<String,String>> processList = new ArrayList<Map<String,String>>();
		//添加三个操作节点的信息
		processList.add(getProcessMap("系统繁忙",orderDate,null));
		processList.add(getProcessMap("处理中","",null));
		processList.add(getProcessMap("处理完成","","no"));
		
		return processList;
		
	}
	
	
	
}
