package com.zhuanyi.vjwealth.wallet.mobile.credit.web.controller;

import java.util.HashMap;
import java.util.Map;

import com.zhuanyi.vjwealth.loan.order.webservice.ILoanApplicationDubboService;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.IInvestmentOrderService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fab.web.controller.annotation.AppController;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.IPledgeInvestService;

/**
 * 流通宝
 * Created by hexy on 16/7/12.
 */
@Controller
@RequestMapping("/api/v3.5")
public class PledgeInvestController {
	
	@Autowired
	private IPledgeInvestService pledgeInvestService;

    @Autowired
    private ILoanApplicationDubboService loanApplicationDubboService;

    @Autowired
    private IInvestmentOrderService investmentOrderService;


    /**
     * 1.流通宝首页初始化接口
     *
     * @param userId
     * @param uuid
     * @param page
     * @return
     * @since 3.5
     */
    @RequestMapping("/app/credit/pledgeInvest/pledgeInvestIniti.security")
    @AppController
    public Object pledgeInvestIniti(String userId,String loanProductId,String page) {

        return pledgeInvestService.pledgeInvestIniti(userId, loanProductId,page);
    }

    /**
     * 2.流通宝借款初始化接口
     * @param userId
     * @param uuid
     * @param orderId
     * @return
     * @since 3.5
     */
    @RequestMapping("/app/credit/pledgeInvest/pledgeInvestBorrowIniti.security")
    @AppController
    public Object pledgeInvestBorrowIniti(String userId,String loanProductId,String orderId) {

        return pledgeInvestService.pledgeInvestBorrowIniti(userId,loanProductId, orderId);
    }

    /**
     * 2.1 流通宝借款申请－利息计算接口
     *
     * @param userId
     * @param uuid
     * @param orderId
     * @param borrowAmount
     * @param borrowDay
     * @return
     * @since 3.5
     */
    @RequestMapping("/app/credit/pledgeInvest/dynamicallyGeneratedInterest.security")
    @AppController
    public Object dynamicallyGeneratedInterest(String loanProductId,String borrowAmount,String borrowDay) {

        return pledgeInvestService.dynamicallyGeneratedInterest(loanProductId, borrowAmount, borrowDay);
    }

    /**
     * 2.2 流通宝借款申请－天数计算接口
     *
     * @param userId
     * @param uuid
     * @param orderId
     * @param borrowAmount
     * @param borrowDay
     * @return
     * @since 3.5
     */
    @RequestMapping("/app/credit/pledgeInvest/dynamicallyGeneratedBorrowDay.security")
    @AppController
    public Object dynamicallyGeneratedBorrowDay(String userId,String orderId,String loanProductId,String borrowAmount) {

        return pledgeInvestService.dynamicallyGeneratedBorrowDay(userId,orderId,loanProductId, borrowAmount);
    }


    /**
     * 3.借款合同H5接口
     *
     * @param userId
     * @param uuid
     * @param loanCode
     * @param loanProductId
     * @return
     * @since 3.5
     */
    @RequestMapping("/app/credit/pledgeInvest/pledgeInvestContract.security")
    public Object pledgeInvestContract(String userId,String loanCode,String loanProductId, Model model) {

        if (StringUtils.isEmpty(userId)  || StringUtils.isEmpty(loanProductId)) {
            return "app/credit/404Error";
        }

        // 获取理财合同编号
        String vContractNo = "";
        if(StringUtils.isNotBlank(loanCode)){
            vContractNo  = investmentOrderService.queryInvestmentContractNoByUserIdAndLoanCode(userId,loanCode);
        }

        Map<String,String> returnMap = loanApplicationDubboService.investmentContractTemplateForPledgeInvest(userId,loanCode,loanProductId,vContractNo);
        model.addAttribute("content", returnMap.get("content"));

        return "/app/credit/pledgeInvest/pledgeInvestContract";
    }


    /**
     * 5.1 获取文字验证码
     *
     * @param userId
     * @param uuid
     * @param phone
     * @return
     * @since 3.5
     */
    @RequestMapping("/app/credit/pledgeInvest/informationConfirmationSendSMSNotice.security")
    @AppController
    public Object informationConfirmationSendSMSNotice(String userId) {
    	
    	final String vaildeTime = pledgeInvestService.informationConfirmationSendSMSNotice(userId);
    	return new HashMap<String,String>(){
			private static final long serialVersionUID = 1L;
		    {put("remainTime",vaildeTime);}
		};

    }


    /**
     * 5.2 获取语音验证码
     *
     * @param userId
     * @param uuid
     * @param phone
     * @return
     * @since 3.5
     */
    @RequestMapping("/app/credit/pledgeInvest/informationConfirmationSendToneNotice.security")
    @AppController
    public Object informationConfirmationSendToneNotice(String userId) {

        final String vaildeTime = pledgeInvestService.informationConfirmationSendToneNotice(userId);
    	return new HashMap<String,String>(){
			private static final long serialVersionUID = 1L;
		    {put("remainTime",vaildeTime);}
		};
    }
    
    /**
     * 6.流通宝借款申请SMS确认（下单）接口
     *
     * @param userId
     * @param uuid
     * @param code
     * @param orderId
     * @param borrowAmount
     * @param borrowDay
     * @return
     * @since 3.5
     */
    @RequestMapping("/app/credit/pledgeInvest/applySMSVerificationConfirm.security")
    @AppController
    public Object applySMSVerificationConfirm(String userId,String code,
                                              String orderId,String loanProductId,String borrowAmount,String borrowDay) {

        return pledgeInvestService.applySMSVerificationConfirm(userId, code, orderId,loanProductId, borrowAmount, borrowDay);
    }

    
    /**
     * 7.还款初始化
     * @param userId
     * @return
     */
    @RequestMapping("/app/credit/pledgeInvest/repaymentIniti.security")
    @AppController
    public Object repaymentIniti(String userId) {

        return pledgeInvestService.repaymentIniti(userId);
    }
    
    /**
     * 8.到期还款初始化
     * @param userId
     * @param page
     * @return
     */
    @RequestMapping("/app/credit/pledgeInvest/dueRepaymentIniti.security")
    @AppController
    public Object dueRepaymentIniti(String userId,String page) {

        return pledgeInvestService.dueRepaymentIniti(userId, page);
    }
    
    /**
     * 9.到期还款详情
     * @param userId
     * @param loanCode
     * @return
     */
    @RequestMapping("/app/credit/pledgeInvest/dueRepaymentDetail.security")
    @AppController
    public Object dueRepaymentDetail(String userId,String loanCode) {

        return pledgeInvestService.dueRepaymentDetail(userId, loanCode);
    }
    
    /**
     * 12.提前还款初始化
     * @param userId
     * @return
     */
    @RequestMapping("/app/credit/pledgeInvest/earlyRepaymentIniti.security")
    @AppController
    public Object earlyRepaymentIniti(String userId,String page) {

        return pledgeInvestService.earlyRepaymentIniti(userId,page);
    }
    
    /**
     * 13.提前还款明细
     * @param userId
     * @param loanCode
     * @param repaymentMoney 还款本金
     * @return
     */
    @RequestMapping("/app/credit/pledgeInvest/earlyRepaymentDetail.security")
    @AppController
    public Object earlyRepaymentDetail(String userId,String loanCodes,String repaymentMoney) {

        return pledgeInvestService.earlyRepaymentDetail(userId, loanCodes, repaymentMoney);
    }
    
    /**
     * 14.提前还款确认
     * @param userId
     * @param loanCode
     * @param repaymentMoney
     * @return
     */
    @RequestMapping("/app/credit/pledgeInvest/earlyRepaymentConfirmIniti.security")
    @AppController
    public Object earlyRepaymentConfirmIniti(String userId,String loanCodes,String repaymentMoney) {

        return pledgeInvestService.earlyRepaymentConfirmIniti(userId, loanCodes, repaymentMoney);
    }
    
    /**
     * 15.还款确认
     * @param userId
     * @param loanCode
     * @param repaymentMoney
     * @return
     */
    @RequestMapping("/app/credit/pledgeInvest/earlyRepaymentConfirm.security")
    @AppController
    public Object earlyRepaymentConfirm(String userId,String loanCodes,String principal,String repaymentMoney,String repaymentType,String repaymentWay) {

        return pledgeInvestService.earlyRepaymentConfirm(userId, loanCodes,principal, repaymentMoney,repaymentType,repaymentWay);
    }
    
    
    /**
     * 16.流通宝借款记录列表接口
     * @param userId
     * @param loanProductId
     * @param page
     * @return
     */
    @RequestMapping("/app/credit/pledgeInvest/queryLoanRecordList.security")
    @AppController
    public Object queryLoanRecordList(String userId,String page) {

        return pledgeInvestService.queryLoanRecordList(userId, page);
    }
    
    
    /**
     * 17.流通宝借款记录列表接口
     * @param userId
     * @param loanCode
     * @return
     */
    @RequestMapping("/app/credit/pledgeInvest/queryLoanRecordDetail.security")
    @AppController
    public Object queryLoanRecordDetail(String userId,String loanCode) {

        return pledgeInvestService.queryLoanRecordDetail(userId, loanCode);
    }
    
    
    /**
     * 18.流通宝借款记录列表接口
     * @param userId
     * @param loanCode
     * @param page
     * @return
     */
    @RequestMapping("/app/credit/pledgeInvest/queryrepayRecordList.security")
    @AppController
    public Object queryrepayRecordList(String userId,String loanCode,String page) {

        return pledgeInvestService.queryrepayRecordList(userId, loanCode, page);
    }
    
    
    /**
     * 19.帮助中心H5页面接口
     * @param userId
     * @param loanCode
     * @param page
     * @return
     */
    @RequestMapping("/app/credit/pledgeInvest/helpCoreH5")
    public String helpCoreH5() {

        return "";
    }
    
    
    /**
     * 20.产品介绍H5页面接口
     * @param userId
     * @param loanCode
     * @param page
     * @return
     */
    @RequestMapping("/app/credit/pledgeInvest/loanProductIntroduce")
    public String loanProductIntroduce() {

        return "";
    }
    

}
