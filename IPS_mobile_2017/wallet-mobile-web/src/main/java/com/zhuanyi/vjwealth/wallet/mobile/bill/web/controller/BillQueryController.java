package com.zhuanyi.vjwealth.wallet.mobile.bill.web.controller;

import com.zhuanyi.vjwealth.wallet.mobile.bill.server.ILoanBillDetailQueryService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fab.core.exception.service.AppException;
import com.fab.web.controller.annotation.AppController;
import com.zhuanyi.vjwealth.loan.order.webservice.IMyBorrowDubboService;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.IBillDetailQueryService;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.IBillListQueryService;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.IFinancialLoanService;
import com.zhuanyi.vjwealth.wallet.mobile.order.dto.WjOrderInfoDTO;
import com.zhuanyi.vjwealth.wallet.mobile.order.server.service.IWjOrderInfoService;

@Controller
public class BillQueryController {
	
	@Autowired
	private IBillListQueryService billListQueryServicel;
	
	@Autowired
	private IBillDetailQueryService billDetailQueryService;
    @Autowired
    private IFinancialLoanService financialLoanService;
    @Autowired
    private IMyBorrowDubboService myBorrowDubboService;
    @Autowired
    private IWjOrderInfoService wjOrderInfoService;
    @Autowired
    private ILoanBillDetailQueryService loanBillDetailQueryService;
    
    public static final String ORDER_TYPE_XY_LOAN = "apply_loan";
	
	// 9.2根据账单类型获取账单列表
    @RequestMapping("/api/v3.0/app/bill/queryBillList.security")
    @AppController
    public Object queryBillList(String userId,String billType,String page) {
       return billListQueryServicel.getBillListByUserIdAndType(userId, billType, page);
    }


    // 9.3账单详情
    @RequestMapping("/api/v3.0/app/bill/queryBillDetail.security")
    @AppController
    public Object queryBillDetail(String orderId,String billType) {
    	return billDetailQueryService.getBillDetailByOrderIdAndOrderType(orderId, billType);
    }
    
 // 得到小金鱼的账单详情（账单类型为apply_la_to_lf）
    @RequestMapping("/api/v3.3/app/billDetail/queryFinacialLoanInvestmentDetail.security")
    @AppController
    public Object queryFinacialLoanInvestmentDetail(String orderId,String userId) {
    	
    	return financialLoanService.investmentDetail(userId, orderId);
    }
    
 // 归还小金鱼的账单详情（账单类型为apply_lf_to_la）
    @RequestMapping("/api/v3.3/app/billDetail/queryFinacialLoanRepaymentDetail.security")
    @AppController
    public Object queryFinacialLoanRepaymentDetail(String orderId,String userId) {
    	//注释：orderId为订单id
    	//查询贷款订单id
    	WjOrderInfoDTO order = wjOrderInfoService.getOrderInfoByOrderNo(orderId);
    	if(order!=null&&StringUtils.isNotBlank(order.getRelOrderNo())){
    		order = wjOrderInfoService.getOrderInfoByOrderNo(order.getRelOrderNo());
    	}else{
    		throw new AppException("查询账单详情，账单不存在");
    	}
    	
    	return financialLoanService.borrowRecordDetail(userId, order.getBorrowCode());
    }
    
 // 白领专享借、还款的账单详情（账单类型为apply_loan,repay_loan）
    @RequestMapping("/api/v3.3/app/billDetail/queryCreditLoanDetail.security")
    @AppController
    public Object queryCreditLoanDetail(String userId,String orderId) {
    	//注释：orderId为订单id
    	//查询贷款订单id
    	WjOrderInfoDTO order = wjOrderInfoService.getOrderInfoByOrderNo(orderId);
    	
    	if(ORDER_TYPE_XY_LOAN.equals(order.getOrderType())){
    		return myBorrowDubboService.writeBorrowDetail(userId, order.getBorrowCode());
    	}else{
    		return myBorrowDubboService.writeRepayDetail(userId, order.getBorrowCode());
    		
    	}
    	
//    	return myBorrowDubboService.borrowRecordDetail(userId, order.getBorrowCode());
//    	return financialLoanService.borrowRecordDetail(userId, order.getBorrowCode());
    	
    }



    // 流通宝借款（账单类型为apply_ltb_loan）
    @RequestMapping("api/v3.5/app/billDetail/pledgeLoanApplyDetail.security")
    @AppController
    public Object pledgeLoanApplyDetail(String userId,String orderId,String billType) {

       return loanBillDetailQueryService.pledgeLoanApplyDetail(userId,orderId,billType);
    }

    // 流通宝还款（账单类型为early_repay_ltb_loan，due_repay_ltb_loan）
    @RequestMapping("api/v3.5/app/billDetail/pledgeLoanRepayDetail.security")
    @AppController
    public Object pledgeLoanRepayDetail(String userId,String orderId,String billType) {

        return loanBillDetailQueryService.pledgeLoanRepayDetail(userId,orderId,billType);
    }


    // 锦囊借款（账单类型为 apply_cana_loan）
    @RequestMapping("api/v3.5/app/billDetail/canaLoanApplyDetail.security")
    @AppController
    public Object canaLoanApplyDetail(String userId,String orderId,String billType) {

        return loanBillDetailQueryService.canaLoanApplyDetail(userId,orderId,billType);
    }

    // 锦囊还款（账单类型为 due_repay_cana_loan）
    @RequestMapping("api/v3.5/app/billDetail/canaLoanRepayDetail.security")
    @AppController
    public Object canaLoanRepayDetail(String userId,String orderId,String billType) {

        return loanBillDetailQueryService.canaLoanRepayDetail(userId,orderId,billType);
    }

    // 定期理财冻结账单详情（账单类型为 transfer_rf_to_frozen）
    @RequestMapping("api/v3.5/app/billDetail/vFinacialFrozenDetail.security")
    @AppController
    public Object vFinacialFrozenDetail(String userId,String orderId,String billType) {

        return loanBillDetailQueryService.vFinacialFrozenDetail(userId,orderId,billType);
    }


    // 工资先享借款（账单类型为 apply_wage_loan）
    @RequestMapping("api/v3.6/app/billDetail/wageAdvanceApplyDetail.security")
    @AppController
    public Object wageAdvanceApplyDetail(String userId,String orderId,String billType) {

        return loanBillDetailQueryService.wageAdvanceApplyDetail(userId,orderId,billType);
    }

    // 工资先享还款（账单类型为 early_repay_wage_loan,due_repay_wage_loan,）
    @RequestMapping("api/v3.6/app/billDetail/wageAdvanceRepayDetail.security")
    @AppController
    public Object wageAdvanceRepayDetail(String userId,String orderId,String billType) {

        return loanBillDetailQueryService.wageAdvanceRepayDetail(userId,orderId,billType);
    }

}
