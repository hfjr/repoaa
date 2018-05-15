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
 * @desc:信贷页面路由相关Dubbo服务
 * @author: Tony Tang
 * @date: 2016-05-13 17:08
 */
@Controller
@RequestMapping("/api/v3.3.1")
public class CreditRouteV1Controller {

    @Autowired
    private ILoanBizService loanBizService;
    /**
     * 0.V信贷页面路由
     *
     * @param userId
     * @param productTypeCode 产品类型编号
     * @return
     * @since 3.3
     */
    @RequestMapping("/app/credit/route.security")
    @AppController
    public Object loanApplicationInit(String userId,String productTypeCode) {
        if(StringUtils.isEmpty(userId)){
            throw new AppException("用户编号不能为空");
        }
        if(StringUtils.isEmpty(productTypeCode)){
            throw new AppException("产品类型编号不能为空");
        }
        return loanBizService.loanApplicationInit(userId,productTypeCode);
    }
}
