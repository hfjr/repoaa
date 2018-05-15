package com.zhuanyi.vjwealth.wallet.mobile.personalCenter.server.impl;

import com.fab.core.annotation.Remote;
import com.fab.core.exception.service.AppException;
import com.zhuanyi.vjwealth.loan.client.webservice.IClientInfoDubboService;
import com.zhuanyi.vjwealth.loan.order.dto.OrderLoanRepayDTO;
import com.zhuanyi.vjwealth.loan.order.webservice.IMyBorrowDubboService;
import com.zhuanyi.vjwealth.loan.util.DateUtil;
import com.zhuanyi.vjwealth.wallet.mobile.personalCenter.server.IPersonalCenterService;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.account.PersonalCenterDTO;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.mapper.IUserAccountQueryMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.plan.server.constant.PlanConstant;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.plan.server.dto.WjSalaryPlanRecordDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.plan.server.service.IWjSalaryPlanRecordService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

@Service
public class PersonalCenterService implements IPersonalCenterService {
    @Autowired
    private IUserAccountQueryMapper userAccountQueryMapper;
    @Remote
    private IWjSalaryPlanRecordService wjSalaryPlanRecordService;
    @Autowired
    private IMyBorrowDubboService myBorrowDubboService;
    /**
     * 1.个人中心-我的
     *
     * @param userId
     * @return
     * @since 3.6
     */
    public Map<String, Object> queryPersonalCenter(String userId) {
        validatorUserId(userId);
        PersonalCenterDTO personalCenterDTO=userAccountQueryMapper.queryPersonalCenterInfo(userId);
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("totalAssets",personalCenterDTO.getTotalAssets());
        returnMap.put("totalRevenue", personalCenterDTO.getTotalRevenue());
        returnMap.put("availableBalance",personalCenterDTO.getAvailableBalance());

        returnMap.put("isShowFreezingAmount", "false");
        returnMap.put("freezingAmount","");
        if(StringUtils.isNotBlank(personalCenterDTO.getFreezingAmount())&&
                new BigDecimal(personalCenterDTO.getFreezingAmount()).compareTo(BigDecimal.ZERO)>0){
            returnMap.put("isShowFreezingAmount", "true");
            returnMap.put("freezingAmount", MessageFormat.format("{0}元",personalCenterDTO.getFreezingAmount()));
        }
        returnMap.put("regularAmount","");
        if(StringUtils.isNotBlank(personalCenterDTO.getRegularAmount())&&
                new BigDecimal(personalCenterDTO.getRegularAmount()).compareTo(BigDecimal.ZERO)>0){
            returnMap.put("regularAmount", MessageFormat.format("待回款{0}元", personalCenterDTO.getRegularAmount()));
        }
        returnMap.put("currentAmount", "");
        if(StringUtils.isNotBlank(personalCenterDTO.getCurrentAmount())&&
                new BigDecimal(personalCenterDTO.getCurrentAmount()).compareTo(BigDecimal.ZERO)>0){
            returnMap.put("currentAmount", MessageFormat.format("{0}元", personalCenterDTO.getCurrentAmount()));
        }
        WjSalaryPlanRecordDTO wjSalaryPlanRecord=wjSalaryPlanRecordService.queryWjSalaryPlanInfo(userId,PlanConstant.PLAN_STATUS_COMPLETE.getCode());
        returnMap.put("salaryPlan", "");
        if(wjSalaryPlanRecord!=null){
            String operationStr=null;
            if(PlanConstant.PLAN_STATUS_COMPLETE.getCode().equals(wjSalaryPlanRecord.getRecordStatus())){
                operationStr="计划制定成功";
                returnMap.put("salaryPlan",operationStr);
            }else if(PlanConstant.PLAN_STATUS_EXECUTE_COMPLETE.getCode().equals(wjSalaryPlanRecord.getRecordStatus())){
                operationStr="转入";
                returnMap.put("salaryPlan", MessageFormat.format("{0}{1}{2}元",DateUtil.getMonthAndDay(wjSalaryPlanRecord.getRecordDate()),
                        operationStr,wjSalaryPlanRecord.getAmount()));
            }else{
                operationStr="转入中";
                returnMap.put("salaryPlan", MessageFormat.format("{0}{1}{2}元",DateUtil.getMonthAndDay(wjSalaryPlanRecord.getRecordDate()),
                        operationStr,wjSalaryPlanRecord.getAmount()));
            }
        }
        returnMap.put("loanInfo", "");
        OrderLoanRepayDTO orderLoanRepayDTO=myBorrowDubboService.queryLatelyRepaymentInfo(userId);
        if(orderLoanRepayDTO!=null){
            returnMap.put("loanInfo", MessageFormat.format("{0}还款",DateUtil.getMonthAndDay(orderLoanRepayDTO.getPlanTime())));
        }
        return returnMap;
    }
    private void validatorUserId(String userId){
        if(StringUtils.isBlank(userId)){
            throw new AppException("用户ID不能为空");
        }
    }
}
