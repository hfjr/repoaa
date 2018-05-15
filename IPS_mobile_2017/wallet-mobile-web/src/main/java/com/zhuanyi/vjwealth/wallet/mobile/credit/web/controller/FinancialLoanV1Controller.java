package com.zhuanyi.vjwealth.wallet.mobile.credit.web.controller;

import com.zhuanyi.vjwealth.wallet.mobile.credit.server.IFinancialLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Copyright (C), 2015-2016,蔚捷互联网金融服务有限公司
 * @desc:金融信贷
 * @author: Tony Tang
 * @date: 2016-07-13 17:08
 */
@Controller
@RequestMapping("/api/v3.4")
public class FinancialLoanV1Controller {

    @Autowired
    IFinancialLoanService financialLoanService;
    /**
     * 2.2 工资随享介绍（什么是工资随享） H5
     * @since 3.4
     * @return
     */
    @RequestMapping("/app/credit/aboutSalarySX")
    public String aboutSalaryYD() {
        return "/app/credit/aboutSalarySX";
    }
    /**
     * 2.3 工资随享审批步骤
     * @since 3.4
     * @return
     */
    @RequestMapping("/app/credit/applyStepPictureSX")
    public String applyStepPictureSX() {
        return "/app/credit/applyStepPictureSX";
    }

}
