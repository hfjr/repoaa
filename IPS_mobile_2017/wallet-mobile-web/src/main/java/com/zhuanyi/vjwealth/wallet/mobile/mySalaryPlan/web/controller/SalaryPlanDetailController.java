package com.zhuanyi.vjwealth.wallet.mobile.mySalaryPlan.web.controller;

import com.fab.web.controller.annotation.AppController;
import com.zhuanyi.vjwealth.wallet.mobile.mySalaryPlan.server.IMySalaryPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Copyright (C), 2015-2016,蔚捷互联网金融服务有限公司
 * @desc:我的工资计划
 * @author: Tony Tang
 * @date: 2016-08-31 17:08
 */
@Controller
@RequestMapping("/api/v3.6")
public class SalaryPlanDetailController {
    @Autowired
    private IMySalaryPlanService mySalaryPlanService;
    /**
     * 1.我的工资计划(已有工资计划)
     * @param userId
     * @since 3.6
     * @return
     */
    @RequestMapping("/app/mySalaryPlan/planDetail/queryMySalaryPlan.security")
    @AppController
    public Object queryMySalaryPlan(String userId) {
        return mySalaryPlanService.queryMySalaryPlan(userId);
    }

}
