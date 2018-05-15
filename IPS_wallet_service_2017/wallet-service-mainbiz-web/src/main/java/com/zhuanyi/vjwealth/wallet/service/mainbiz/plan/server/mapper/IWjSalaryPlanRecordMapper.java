package com.zhuanyi.vjwealth.wallet.service.mainbiz.plan.server.mapper;

import com.fab.server.annotation.Mapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.plan.server.dto.WjSalaryPlanRecordDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Copyright (C), 2015-2016,蔚捷互联网金融服务有限公司
 * @desc:我的工资计划
 * @author: Tony Tang
 * @date: 2016-08-31 17:08
 */
@Mapper
public interface IWjSalaryPlanRecordMapper {

    List<WjSalaryPlanRecordDTO> queryWjSalaryPlanRecordByPlanId(@Param("planId") String planId);

    void insertWjSalaryPlanRecordByPlanId(@Param("param")WjSalaryPlanRecordDTO param);

    WjSalaryPlanRecordDTO queryWjSalaryPlanInfo(@Param("userId")String userId,@Param("planStatus")String planStatus);

    void updateSalaryPlanRecordStatus(@Param("param")WjSalaryPlanRecordDTO wjSalaryPlanRecordDTO);
}
