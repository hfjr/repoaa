package com.zhuanyi.vjwealth.wallet.service.mainbiz.plan.server.service.impl;

import com.zhuanyi.vjwealth.wallet.service.mainbiz.plan.server.dto.WjSalaryPlanRecordDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.plan.server.mapper.IWjSalaryPlanRecordMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.plan.server.service.IWjSalaryPlanRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WjSalaryPlanRecordService implements IWjSalaryPlanRecordService {

    @Autowired
    private IWjSalaryPlanRecordMapper wjSalaryPlanRecordMapper;

    @Override
    public List<WjSalaryPlanRecordDTO> queryWjSalaryPlanRecordByPlanId(String planId) {
        return wjSalaryPlanRecordMapper.queryWjSalaryPlanRecordByPlanId(planId);
    }

    @Override
    public WjSalaryPlanRecordDTO queryWjSalaryPlanInfo(String userId) {
        return wjSalaryPlanRecordMapper.queryWjSalaryPlanInfo(userId,null);
    }

    @Override
    public WjSalaryPlanRecordDTO queryWjSalaryPlanInfo(String userId, String planStatus) {
        return wjSalaryPlanRecordMapper.queryWjSalaryPlanInfo(userId,planStatus);
    }
}
