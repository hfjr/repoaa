package com.zhuanyi.vjwealth.wallet.mobile.mySalaryPlan.web.controller;

import com.fab.web.controller.annotation.AppController;
import com.zhuanyi.vjwealth.wallet.mobile.mySalaryPlan.server.IMySalaryPlanService;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.IInvestmentOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @Copyright (C), 2015-2016,蔚捷互联网金融服务有限公司
 * @desc:我的工资计划
 * @author: Tony Tang
 * @date: 2016-08-31 17:08
 */
@Controller
@RequestMapping("/api/v3.6")
public class SalaryPlanRouteController {

    @Autowired
    private IMySalaryPlanService mySalaryPlanService;
    @Autowired
    private IInvestmentOrderService investmentOrderService;
    /**
     * 0.页面路由接口(首页入口)
     *
     * @param userId
     * @return
     * @since 3.6
     */
    @RequestMapping("/app/mySalaryPlan/route.security")
    @AppController
    public Object route(String userId) {
        return mySalaryPlanService.route(userId);
    }

    /**
     * 8.融桥宝平台服务协议H5接口
     *
     * @return
     * @since 3.6
     */
    @RequestMapping("/app/mySalaryPlan/client-platform")
    public String productDetails() {
        return "/app/mySalaryPlan/client-platform";
    }

    /**
     * 9.《T金所收益权产品认购协议》H5接口
     *
     * @return
     * @since 3.6
     */
    @RequestMapping("/app/mySalaryPlan/user-agreement")
    public String financingDetails(Model model) {
        String rate=investmentOrderService.queryTaReceiveRate();
        model.addAttribute("rate",rate+"%");
        return "/app/mySalaryPlan/user-agreement";
    }
    /**
     * 10.《资产收益权转让协议》H5接口
     *
     * @return
     * @since 3.6
     */
    @RequestMapping("/app/mySalaryPlan/agreement-transfer")
    public String agreementTransfer() {
        return "/app/mySalaryPlan/agreement-transfer";
    }
}
