package com.zhuanyi.vjwealth.wallet.mobile.mySalaryPlan.server;


import com.zhuanyi.vjwealth.wallet.mobile.mySalaryPlan.server.dto.MySalaryPlanQueryDTO;

import java.util.Map;

/**
 * @Copyright (C), 2015-2016,蔚捷互联网金融服务有限公司
 * @desc:我的工资计划
 * @author: Tony Tang
 * @date: 2016-08-31 17:08
 */
public interface IMySalaryPlanService {
    /**
     * 0.页面路由接口(首页入口)
     *
     * @param userId
     * @return
     * @since 3.6
     */
    Map<String,Object> route(String userId);
    /**
     * 1.我的工资计划(已有工资计划)
     * @param userId
     * @since 3.6
     * @return
     */
    Map<String,Object> queryMySalaryPlan(String userId);
    /**
     * 2.定制工资计划(初始化)
     * @param userId
     * @since 3.6
     * @return
     */
    Map<String,Object> addMySalaryPlanInit(String userId);
    /**
     * 3.定制工资计划(提交)
     * @param query
     * @since 3.6
     * @return
     */
    Map<String, Object> addMySalaryPlan(MySalaryPlanQueryDTO query);
    /**
     *  4.定制工资计划-SMS(初始化)
     * @param userId
     * @param planCode
     * @param bizType
     * @return
     */
    Map<String, Object> mySalaryPlanSendSmsInit(String userId, String planCode, String bizType);
    /**
     * 5.发送短信验证码-SMS(获取文字验证码)
     * @param userId
     * @param planCode
     * @param bizType
     * @since 3.6
     * @return
     */
    Map<String, Object> mySalaryPlanSendSms(String userId, String planCode, String bizType);
    /**
     * 6.定制工资计划-SMS(提交)
     * @param userId
     * @param planCode
     * @param code
     * @since 3.6
     * @return
     */
    Map<String, Object> addMySalaryPlanSendSmsSave(String userId, String planCode,String orderNo, String code);
    /**
     * 7.取消工资计划-SMS
     * @param userId
     * @param planCode
     * @param code
     * @since 3.6
     * @return
     */
    Map<String, Object> cancelMySalaryPlanSendSms(String userId, String planCode, String code);
    /**
     * 12.定制工资计划-初始化-动态提示文案
     * @param depositAmount
     * @param depositDate
     * @since 3.6
     * @return
     */
    Map<String, Object> dynamicTipsMySalaryPlanInit(String depositAmount, String depositDate);
}
