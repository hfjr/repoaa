package com.zhuanyi.vjwealth.wallet.mobile.mySalaryPlan.web.controller;

import com.fab.core.exception.service.AppException;
import com.fab.web.controller.annotation.AppController;
import com.zhuanyi.vjwealth.wallet.mobile.mySalaryPlan.server.IMySalaryPlanService;
import com.zhuanyi.vjwealth.wallet.mobile.mySalaryPlan.server.dto.MySalaryPlanQueryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Copyright (C), 2015-2016,蔚捷互联网金融服务有限公司
 * @desc:我的工资计划
 * @author: Tony Tang
 * @date: 2016-08-31 17:08
 */
@Controller
@RequestMapping("/api/v3.6")
public class SalaryPlanEditController {
    @Autowired
    private IMySalaryPlanService mySalaryPlanService;
    /**
     * 2.定制工资计划(初始化)
     * @param userId
     * @since 3.6
     * @return
     */
    @RequestMapping("/app/mySalaryPlan/planEdit/addMySalaryPlanInit.security")
    @AppController
    public Object addMySalaryPlanInit(String userId) {
        return mySalaryPlanService.addMySalaryPlanInit(userId);
    }
    /**
     * 3.定制工资计划(提交)
     * @param query
     * @since 3.6
     * @return
     */
    @RequestMapping("/app/mySalaryPlan/planEdit/addMySalaryPlan.security")
    @AppController
    public Object addMySalaryPlan(MySalaryPlanQueryDTO query) {
        if(StringUtils.isEmpty(query.getCardId())){
            throw new AppException("工资卡编号不能为空!");
        }
        if(StringUtils.isEmpty(query.getDepositAmount())){
            throw new AppException("存多少不能为空!");
        }
        if(StringUtils.isEmpty(query.getDepositDate())){
            throw new AppException("几号存不能为空!");
        }
        if(StringUtils.isEmpty(query.getPlanCode())){
            throw new AppException("计划编号不能为空!");
        }
        return mySalaryPlanService.addMySalaryPlan(query);
    }

    /**
     *  4.定制工资计划-SMS(初始化)
     * @param userId
     * @param planCode
     * @param bizType 添加计划:addPlan;删除计划:cancelPlan
     * @return
     */
    @RequestMapping("/app/mySalaryPlan/planEdit/mySalaryPlanSendSmsInit.security")
    @AppController
    public Object mySalaryPlanSendSmsInit(String userId,String planCode,String bizType) {
        if(StringUtils.isEmpty(planCode)){
            throw new AppException("计划编号不能为空!");
        }else if(!("addPlan".equals(bizType) || "cancelPlan".equals(bizType))){
            throw new AppException("业务类型不合法!");
        }
        return mySalaryPlanService.mySalaryPlanSendSmsInit(userId,planCode,bizType);
    }
    /**
     * 5.发送短信验证码-SMS(获取文字验证码)
     * @param userId
     * @param planCode
     * @param bizType 业务类型(添加计划:addPlan;取消计划:cancelPlan)
     * @since 3.6
     * @return
     */
    @RequestMapping("/app/mySalaryPlan/planEdit/mySalaryPlanSendSms.security")
    @AppController
    public Object mySalaryPlanSendSms(String userId,String planCode,String bizType) {
        if(StringUtils.isEmpty(planCode)){
            throw new AppException("计划编号不能为空!");
        }else if(!("addPlan".equals(bizType) || "cancelPlan".equals(bizType))){
            throw new AppException("业务类型不合法!");
        }
        return mySalaryPlanService.mySalaryPlanSendSms(userId,planCode,bizType);
    }
    /**
     * 6.定制工资计划-SMS(提交)
     * @param userId
     * @param planCode
     * @param code
     * @since 3.6
     * @return
     */
    @RequestMapping("/app/mySalaryPlan/planEdit/addMySalaryPlanSendSmsSave.security")
    @AppController
    public Object addMySalaryPlanSendSmsSave(String userId, String planCode,String bizOrderNo,String code) {
        checkParamEmpty(planCode,code);
        return mySalaryPlanService.addMySalaryPlanSendSmsSave(userId,planCode,bizOrderNo,code);
    }
    /**
     * 7.取消工资计划-SMS
     * @param userId
     * @param planCode
     * @param code
     * @since 3.6
     * @return
     */
    @RequestMapping("/app/mySalaryPlan/planEdit/cancelMySalaryPlanSendSms.security")
    @AppController
    public Object cancelMySalaryPlanSendSms(String userId,String planCode,String code) {
        checkParamEmpty(planCode,code);
        return mySalaryPlanService.cancelMySalaryPlanSendSms(userId,planCode,code);
    }

    private void checkParamEmpty(String planCode,String code) throws AppException{
        if(StringUtils.isEmpty(planCode)){
            throw new AppException("计划编号不能为空!");
        }
        if(StringUtils.isEmpty(code)){
            throw new AppException("短信验证码不能为空!");
        }
    }

    /**
     * 12.定制工资计划-初始化-动态提示文案
     * @param depositAmount
     * @param depositDate
     * @since 3.6
     * @return
     */
    @RequestMapping("/app/mySalaryPlan/planEdit/dynamicTipsMySalaryPlanInit")
    @AppController
    public Object dynamicTipsMySalaryPlanInit(String depositAmount,String depositDate) {
        return mySalaryPlanService.dynamicTipsMySalaryPlanInit(depositAmount,depositDate);
    }

}
