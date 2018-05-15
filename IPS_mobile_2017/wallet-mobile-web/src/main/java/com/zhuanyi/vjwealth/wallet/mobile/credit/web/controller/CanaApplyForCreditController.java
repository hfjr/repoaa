package com.zhuanyi.vjwealth.wallet.mobile.credit.web.controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.fab.core.exception.service.AppException;
import com.fab.web.controller.annotation.AppController;
import com.zhuanyi.vjwealth.loan.order.vo.*;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.IApplyForCreditService;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.ILoanBizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by hexy on 16/5/12.
 */
@Controller
@RequestMapping("/api/v3.4")
public class CanaApplyForCreditController {
    @Autowired
    private IApplyForCreditService applyForCreditService;

    @Autowired
    private ILoanBizService loanBizService;
    /**
     * 1.申请额度初始化
     *
     * @param userId
     * @param productTypeCode
     * @return
     * @since 3.3
     */
    @RequestMapping("/app/credit/applyForCredit/creditApplicationIniti.security")
    @AppController
    public Object creditApplicationInit(String userId,String productTypeCode) {
        if (StringUtils.isEmpty(userId)) {
            throw new AppException("用户编号不能为空");
        }
        return loanBizService.creditApplicationInit(userId,productTypeCode);
    }

    /**
     * 4.充值初始化
     *
     * @return
     * @since 3.3
     */
    @RequestMapping("/app/credit/applyForCredit/rechargeIniti.security")
    @AppController
    public Object rechargeIniti() {
        return applyForCreditService.rechargeIniti();
    }

    /**
     * 5.充值支持银行列表
     *
     * @return
     * @since 3.3
     */
    @RequestMapping("/app/credit/applyForCredit/queryAllSupportBankList.security")
    @AppController
    public Object queryAllSupportBankList() {
        return applyForCreditService.queryAllSupportBankList();
    }

    /**
     * 6.充值绑定银行卡初始化
     *
     * @param userId
     * @param bankCode
     * @return
     * @since 3.3
     */
    @RequestMapping("/app/credit/applyForCredit/rechargeBindBankCardIniti.security")
    @AppController
    public Object rechargeBindBankCardIniti(String userId, String bankCode) {
        if (StringUtils.isEmpty(userId)) {
            throw new AppException("用户编号不能为空");
        }
        if (StringUtils.isBlank(bankCode)) {
            throw new AppException("参数异常bankCode不能为空");
        }
        return applyForCreditService.rechargeBindBankCardIniti(userId, bankCode);
    }

    /**
     * 10.申请额度-完善个人信息初始化
     *
     * @param userId
     * @return
     * @since 3.3
     */
    @RequestMapping("/app/credit/applyForCredit/improvePersonalInformationIniti.security")
    @AppController
    public Object improvePersonalInformationIniti(String userId,String productTypeCode) {
        if (StringUtils.isEmpty(userId)) {
            throw new AppException("用户编号不能为空");
        }
        return loanBizService.improvePersonalInformationIniti(userId,productTypeCode);
    }

    /**
     * 10.1 申请额度-完善个人信息初始化-公积金初始化
     *
     * @param userId
     * @param cityCode
     * @param borrowCode
     * @return
     * @since 3.3
     */
    @RequestMapping("/app/credit/applyForCredit/fundAccountIniti.security")
    @AppController
    public Object fundAccountIniti(String userId, String cityCode, String borrowCode) {
        if (StringUtils.isEmpty(userId)) {
            throw new AppException("用户编号不能为空");
        }
        if (StringUtils.isEmpty(cityCode)) {
            throw new AppException("城市编号不能为空");
        }
        if (StringUtils.isEmpty(borrowCode)) {
            throw new AppException("借款编号不能为空");
        }
        return loanBizService.fundAccountIniti(userId, cityCode, borrowCode);
    }

    /**
     * 10.2 申请额度-完善个人信息初始化-社保初始化
     *
     * @param userId
     * @param cityCode
     * @param borrowCode
     * @return
     * @since 3.3
     */
    @RequestMapping("/app/credit/applyForCredit/socialSecurityAccountIniti.security")
    @AppController
    public Object socialSecurityAccountIniti(String userId, String cityCode, String borrowCode) {
        if (StringUtils.isEmpty(userId)) {
            throw new AppException("用户编号不能为空");
        }
        if (StringUtils.isEmpty(cityCode)) {
            throw new AppException("城市编号不能为空");
        }
        if (StringUtils.isEmpty(borrowCode)) {
            throw new AppException("借款编号不能为空");
        }
        return loanBizService.socialSecurityAccountIniti(userId, cityCode, borrowCode);
    }
    /**
     * 11.申请额度-完善个人信息确认（保存）
     *
     * @param query
     * @return
     * @since 3.3
     */
    @RequestMapping("/app/credit/applyForCredit/improvePersonalInformationSave.security")
    @AppController
    public Object improvePersonalInformationSave(HttpServletRequest request,PersonalInformationVo query) {
        loanBizService.checkPersonalInformation(request,query);
        return loanBizService.improvePersonalInformationSave(request, query);
    }
}
