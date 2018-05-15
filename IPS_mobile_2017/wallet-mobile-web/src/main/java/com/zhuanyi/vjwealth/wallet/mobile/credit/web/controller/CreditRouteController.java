package com.zhuanyi.vjwealth.wallet.mobile.credit.web.controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.fab.core.exception.service.AppException;
import com.fab.web.controller.annotation.AppController;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.ILoanBizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Copyright (C), 2015-2016,蔚捷互联网金融服务有限公司
 * @desc:信贷页面路由接口
 * @author: Tony Tang
 * @date: 2016-05-13 17:08
 */
@Controller
@RequestMapping("/api/v3.3")
public class CreditRouteController {

    @Autowired
    private ILoanBizService loanBizService;
    /**
     * 0.V信贷页面路由
     *
     * @param userId
     * @return
     * @since 3.3
     */
    @RequestMapping("/app/credit/route.security")
    @AppController
    public Object loanApplicationInit(String userId) {
        if(StringUtils.isEmpty(userId)){
            throw new AppException("用户编号不能为空");
        }
        return loanBizService.loanApplicationInit(userId,null);
    }

    /**
     *
     * @param phone
     * @return
     */
    @RequestMapping("/app/credit/cleanData.mock")
    @AppController
    public Object cleanData(String phone) {
        if(StringUtils.isEmpty(phone)){
            throw new AppException("手机号码不能为空");
        }
        return loanBizService.cleanData(phone);
    }
}
