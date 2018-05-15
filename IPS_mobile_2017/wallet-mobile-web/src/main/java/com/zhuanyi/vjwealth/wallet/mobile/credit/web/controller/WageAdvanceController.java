package com.zhuanyi.vjwealth.wallet.mobile.credit.web.controller;

import com.fab.web.controller.annotation.AppController;
import com.zhuanyi.vjwealth.loan.order.webservice.ILoanApplicationDubboService;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.IWageAdvanceService;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.IInvestmentOrderService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * 工资先享
 * Created by hexy on 16/8/25.
 */

@Controller
@RequestMapping("/api/v3.6")
public class WageAdvanceController {

    @Autowired
    private IWageAdvanceService wageAdvanceService;

    @Autowired
    private ILoanApplicationDubboService loanApplicationDubboService;


    /**
     * 1.借款介绍接口
     *
     * @param userId
     * @param uuid
     * @return
     * @Deprecated
     * @since 3.5
     */
    @RequestMapping("/app/credit/creditIntroduce")
    @AppController
    public Object creditIntroduce(String userId) {

        return wageAdvanceService.creditIntroduce();
    }

    /**
     * 2.借款初始化接口
     *
     * @param userId
     * @return
     * @since 3.5
     */
    @RequestMapping("/app/credit/creditIniti")
    @AppController
    public Object creditIniti(String userId) {

        return wageAdvanceService.creditIniti();
    }


    /**
     * 3.借款征信方式接口
     *
     * @param userId
     * @param uuid
     * @param borrowAmount
     * @return
     * @since 3.5
     */
    @RequestMapping("/app/credit/creditInvestigationWay.security")
    @AppController
    public Object creditInvestigationWay(String userId,String borrowAmount) {

        return wageAdvanceService.creditInvestigationWay(userId,borrowAmount);
    }

    /**
     * 4.借款合同H5接口
     *
     * @param userId
     * @param uuid
     * @param loanCode
     * @param loanProductId
     * @return
     * @since 3.5
     */
    @RequestMapping("/app/credit/wageAdvance/wageAdvanceContract.security")
    public Object pledgeInvestContract(String userId,String loanCode,String loanProductId, Model model) {

        if (StringUtils.isEmpty(userId)  || StringUtils.isEmpty(loanProductId)) {
            return "app/credit/404Error";
        }

        Map<String,String> returnMap = wageAdvanceService.queryWageAdvanceContractContent(userId,loanCode,loanProductId);

        model.addAttribute("content", returnMap.get("content"));

        return "/app/credit/wageAdvance/wageAdvanceContract";
    }

    /**
     * 0.借款申请－初始化接口
     * @param userId
     * @return
     * @since 3.6
     */
    @RequestMapping("/app/credit/wageAdvance/route.security")
    @AppController
    public Object route(String userId) {

        return wageAdvanceService.queryUserLoanCheckStatus(userId);
    }


    /**
     * 1.借款申请－初始化接口
     * @param userId
     * @return
     * @since 3.6
     */
    @RequestMapping("/app/credit/wageAdvance/wageAdvanceIniti.security")
    @AppController
    public Object wageAdvanceIniti(String userId,String loanProductId,String borrowAmount) {

        return wageAdvanceService.wageAdvanceIniti(userId,loanProductId,borrowAmount);
    }


    /**
     * 2.借款申请－还款方式展示接口
     * @param userId
     * @return
     * @since 3.6
     */
    @RequestMapping("/app/credit/wageAdvance/dynamicallyGeneratedRepaymentPlan.security")
    @AppController
    public Object dynamicallyGeneratedRepaymentPlan(String userId,String borrowMode,String borrowAmount,String borrowPeriod) {

        return wageAdvanceService.dynamicallyGeneratedRepaymentPlan(userId,borrowMode,borrowAmount,borrowPeriod);
    }

    /**
     * 3.借款申请－利息计算接口
     * @param userId
     * @return
     * @since 3.6
     */
    @RequestMapping("/app/credit/wageAdvance/dynamicallyGeneratedInterest.security")
    @AppController
    public Object dynamicallyGeneratedInterest(String userId,String loanProductId,String borrowMode,String borrowAmount,String borrowPeriod) {

        return wageAdvanceService.dynamicallyGeneratedInterest(userId,loanProductId,borrowMode,borrowAmount,borrowPeriod);
    }


    /**
     * 5.1 获取文字验证码
     *
     * @param userId
     * @return
     * @since 3.6
     */
    @RequestMapping("/app/credit/wageAdvance/informationConfirmationSendSMSNotice.security")
    @AppController
    public Object informationConfirmationSendSMSNotice(String userId) {

        final String vaildeTime = wageAdvanceService.informationConfirmationSendSMSNotice(userId);
        return new HashMap<String,String>(){
            private static final long serialVersionUID = 1L;
            {put("remainTime",vaildeTime);}
        };

    }


    /**
     * 5.2 获取语音验证码
     *
     * @param userId
     * @return
     * @since 3.6
     */
    @RequestMapping("/app/credit/wageAdvance/informationConfirmationSendToneNotice.security")
    @AppController
    public Object informationConfirmationSendToneNotice(String userId) {

        final String vaildeTime = wageAdvanceService.informationConfirmationSendToneNotice(userId);
        return new HashMap<String,String>(){
            private static final long serialVersionUID = 1L;
            {put("remainTime",vaildeTime);}
        };
    }

    /**
     * 6.工资先享下单
     * @return
     * @since 3.6
     */
    @RequestMapping("/app/credit/wageAdvance/applySMSVerificationConfirm.security")
    @AppController
    public Object applySMSVerificationConfirm(String userId,String code,
                                              String borrowMode,String loanProductId,String borrowAmount,String borrowPeriod) {

        return wageAdvanceService.applySMSVerificationConfirm(userId, code, borrowMode,loanProductId, borrowAmount, borrowPeriod);
    }


    /**
     * 7.还款初始化
     * @param userId
     * @return
     */
    @RequestMapping("/app/credit/wageAdvance/repaymentIniti.security")
    @AppController
    public Object repaymentIniti(String userId) {
        return wageAdvanceService.repaymentIniti(userId);
    }

    /**
     * 8.到期还款初始化
     * @param userId
     * @param page
     * @return
     */
    @RequestMapping("/app/credit/wageAdvance/dueRepaymentIniti.security")
    @AppController
    public Object dueRepaymentIniti(String userId,String page) {

        return wageAdvanceService.dueRepaymentIniti(userId, page);
    }

    /**
     * 9.到期还款详情
     * @param userId
     * @param loanCode
     * @return
     */
    @RequestMapping("/app/credit/wageAdvance/dueRepaymentDetail.security")
    @AppController
    public Object dueRepaymentDetail(String userId,String loanCode) {

        return wageAdvanceService.dueRepaymentDetail(userId, loanCode);
    }


    /**
     * 12.提前还款初始化
     * @param userId
     * @return
     */
    @RequestMapping("/app/credit/wageAdvance/earlyRepaymentIniti.security")
    @AppController
    public Object earlyRepaymentIniti(String userId,String page) {

        return wageAdvanceService.earlyRepaymentIniti(userId,page);
    }


    /**
     * 13.提前还款明细
     * @param userId
     * @param loanCodes
     * @param repaymentMoney 还款本金
     * @return
     */
    @RequestMapping("/app/credit/wageAdvance/earlyRepaymentDetail.security")
    @AppController
    public Object earlyRepaymentDetail(String userId,String loanCodes,String repaymentMoney) {

        return wageAdvanceService.earlyRepaymentDetail(userId, loanCodes, repaymentMoney);
    }


    /**
     * 14.提前还款确认
     * @param userId
     * @param loanCodes
     * @param repaymentMoney
     * @return
     */
    @RequestMapping("/app/credit/wageAdvance/earlyRepaymentConfirmIniti.security")
    @AppController
    public Object earlyRepaymentConfirmIniti(String userId,String loanCodes,String repaymentMoney) {

        return wageAdvanceService.earlyRepaymentConfirmIniti(userId, loanCodes, repaymentMoney);
    }


    /**
     * 15.还款确认
     * @param userId
     * @param loanCodes
     * @param repaymentMoney
     * @return
     */
    @RequestMapping("/app/credit/wageAdvance/earlyRepaymentConfirm.security")
    @AppController
    public Object earlyRepaymentConfirm(String userId,String loanCodes,String principal,String repaymentMoney,String repaymentType,String repaymentWay) {

        return wageAdvanceService.earlyRepaymentConfirm(userId, loanCodes,principal, repaymentMoney,repaymentType,repaymentWay);
    }



    /**
     * 16.工资先享借款记录列表接口
     * @param userId
     * @param page
     * @return
     */
    @RequestMapping("/app/credit/wageAdvance/queryLoanRecordList.security")
    @AppController
    public Object queryLoanRecordList(String userId,String page) {

        return wageAdvanceService.queryLoanRecordList(userId, page);
    }


    /**
     * 17.工资先享借款记录列表详情接口
     * @param userId
     * @param loanCode
     * @return
     */
    @RequestMapping("/app/credit/wageAdvance/queryLoanRecordDetail.security")
    @AppController
    public Object queryLoanRecordDetail(String userId,String loanCode) {

        return wageAdvanceService.queryLoanRecordDetail(userId, loanCode);
    }


    /**
     * 18.工资先享还款记录列表接口
     * @param userId
     * @param loanCode
     * @param page
     * @return
     */
    @RequestMapping("/app/credit/wageAdvance/queryrepayRecordList.security")
    @AppController
    public Object queryrepayRecordList(String userId,String loanCode,String page) {

        return wageAdvanceService.queryrepayRecordList(userId, loanCode, page);
    }


    /**
     * 19.银行卡扣款
     * @param
     * @return
     */
    @RequestMapping("/app/credit/wageAdvance/withhold.security")
    @AppController
    public Object withhold(String userId,String cardNo,String bankCode ,String amount) {

        return wageAdvanceService.withhold(userId, cardNo, bankCode,amount);
    }

    /**
     * 19.银行卡扣款结果查询
     * @param
     * @return
     */
    @RequestMapping("/app/credit/wageAdvance/queryWithholdResult.security")
    @AppController
    public Object queryWithholdResult(String userId,String amount,String orderNo) {

        return wageAdvanceService.queryWithholdResult(userId, amount,orderNo);
    }


}
