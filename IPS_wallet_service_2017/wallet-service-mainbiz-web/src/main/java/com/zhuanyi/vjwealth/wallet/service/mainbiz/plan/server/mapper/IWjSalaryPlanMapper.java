package com.zhuanyi.vjwealth.wallet.service.mainbiz.plan.server.mapper;

import java.util.Date;
import java.util.Map;

import com.fab.server.annotation.Mapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.plan.server.dto.PlanSummaryInfoDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.plan.server.dto.WjSalaryPlanDTO;

import org.apache.ibatis.annotations.Param;

/**
 * @Copyright (C), 2015-2016,蔚捷互联网金融服务有限公司
 * @desc:我的工资计划
 * @author: Tony Tang
 * @date: 2016-08-31 17:08
 */
@Mapper
public interface IWjSalaryPlanMapper {

    WjSalaryPlanDTO queryWjSalaryPlan(@Param("userId") String userId,@Param("planStatus") String planStatus);

    PlanSummaryInfoDTO queryWjSalaryPlanSummaryInfo(@Param("userId")String userId);

    WjSalaryPlanDTO queryWjSalaryPlanById(@Param("id")String id);
    
    WjSalaryPlanDTO queryWjSalaryPlanByUserId(@Param("userId") String userId);

    int updateMySalaryPlan(@Param("id")String id,@Param("userId") String userId,@Param("nextExecuteTime") Date nextExecuteTime);
    
    void createWjSalaryPlan(Map<String, Object> map);
    
    WjSalaryPlanDTO queryUserCardInfo(@Param("userId") String userId,@Param("cardId") String cardId);
    
    int updateMySalaryPlanInit(@Param("userId") String userId,@Param("planCode") String planCode,@Param("cardId") String cardId,@Param("bankCardNo") String bankCardNo,@Param("depositDate") String depositDate,@Param("depositAmount") String depositAmount);
    
    int cancelMySalaryPlan(@Param("userId") String userId,@Param("planCode") String planCode);
    
    WjSalaryPlanDTO queryUserCardInfoByPlanCode(@Param("userId") String userId,@Param("planCode") String planCode);
    
    WjSalaryPlanDTO queryRechargeCardByUserId(@Param("userId") String userId);
    
    WjSalaryPlanDTO queryRechargeCardByCardId(@Param("cardId") String cardId);
    
    WjSalaryPlanDTO queryLimitAmountByBankCode(@Param("bankCode") String bankCode);
}
