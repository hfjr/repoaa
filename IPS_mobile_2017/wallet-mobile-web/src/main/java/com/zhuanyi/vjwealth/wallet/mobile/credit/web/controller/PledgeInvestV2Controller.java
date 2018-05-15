package com.zhuanyi.vjwealth.wallet.mobile.credit.web.controller;

import com.fab.web.controller.annotation.AppController;
import com.zhuanyi.vjwealth.loan.order.webservice.ILoanApplicationDubboService;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.IPledgeInvestService;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.IInvestmentOrderService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * 流通宝
 * Created by hexy on 16/7/12.
 */
@Controller
@RequestMapping("/api/v3.6")
public class PledgeInvestV2Controller {
	
	@Autowired
	private IPledgeInvestService pledgeInvestService;



    /**
     * 2.流通宝借款初始化接口
     * @param userId
     * @param orderId
     * @return
     * @since 3.5
     */
    @RequestMapping("/app/credit/pledgeInvest/pledgeInvestBorrowIniti.security")
    @AppController
    public Object pledgeInvestBorrowIniti(String userId,String loanProductId,String orderId) {

        return pledgeInvestService.pledgeInvestBorrowInitiV2(userId,loanProductId, orderId);
    }



}
