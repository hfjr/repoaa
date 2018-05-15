package com.zhuanyi.vjwealth.wallet.mobile.credit.web.controller;

import com.fab.web.controller.annotation.AppController;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.IMyAllLoanQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 我的借款模块
 * Created by wangzf on 16/9/8.
 */
@Controller
@RequestMapping("/api/v3.6")
public class MyAllLoanQueryController {
    @Autowired
    private IMyAllLoanQueryService myAllLoanQueryService;
    /**
     * 1.我的借款 -- 初始化
     *
     * @param userId
     * @param page
     * @return
     * @since 3.6
     */
    @RequestMapping("/app/credit/myBorrows/borrowRecordList.security")
    @AppController
    public Object borrowRecordList(String userId, String page, String borrowStatus,String productSearch){

        return myAllLoanQueryService.queryLoanRecordByConditions(userId, borrowStatus,productSearch, page);
    }



    /**
     * 2.我的借款 -- 贷款详细
     *
     * @param userId
     * @return
     * @since 3.6
     */
    @RequestMapping("/app/credit/myBorrows/repaymentDetailForFund.security")
    @AppController
    public Object repaymentDetailForFund(String userId, String borrowCode,String loanProductId){

        return myAllLoanQueryService.repaymentDetailForFund(userId, borrowCode,loanProductId);
    }

    /**
     * 3.我的借款 -- 还款明细
     *
     * @param userId
     * @return
     * @since 3.6
     */
    @RequestMapping("/app/credit/myBorrows/repaymentDetailForCana.security")
    @AppController
    public Object repaymentDetailForCana(String userId, String borrowCode,String loanProductId){

        return myAllLoanQueryService.repaymentDetailForCana(userId, borrowCode,loanProductId);
    }

}