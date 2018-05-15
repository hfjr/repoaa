package com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.dto.CallbackBizResultDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.service.IMBUserOrderService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.dto.TradeSearchResultDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.service.IWithholdService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.plan.server.dto.WjSalaryPlanDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBOrderInfoDTO;

/**
 * 工资计划 - 银行卡扣款实现类
 * Created by wzf on 2016/9/13.
 */
@Service
public class SalaryPlanWithholdService implements IWithholdService {

    private static final String ORDER_TYPE = MBOrderInfoDTO.ORDER_TYPE_SALARY_PLAN_BANKCARD_WITHHOLD;

    @Autowired
    private IMBUserOrderService mBUserOrderService;

    @Override
    public boolean isSupportOrderType(String orderType) {
        if(this.ORDER_TYPE.equals(orderType)){
            return true;
        }
        return false;
    }

    @Override
    public Object preBizOperateParameter(Object preParmeter) {
        String preParmeterStr = (String)preParmeter;
        WjSalaryPlanDTO paramDto = JSONObject.parseObject(preParmeterStr,WjSalaryPlanDTO.class);
        try{
            return mBUserOrderService.preOperateForSalaryPlan(paramDto.getUserId(),paramDto.getCardId(),paramDto.getRecordId(),paramDto.getAmount());
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Object processOperator(String userId,String bizNo) {
        mBUserOrderService.callbackForSalaryPlanWithholdProcess(userId,bizNo);
        return null;
    }

    @Override
    public CallbackBizResultDTO callBackBizOperateSuccess(String userId,String bizNo,BigDecimal amount) {
        mBUserOrderService.callbackForSalaryPlanWithholdSuccess(userId,bizNo,amount);
    	return CallbackBizResultDTO.returnCallBackSucces("处理成功");
    }

    @Override
    public CallbackBizResultDTO callBackBizOperateFail(String userId,String bizNo,BigDecimal amount) {
        mBUserOrderService.callbackForSalaryPlanWithholdFail(userId,bizNo,amount);
        return CallbackBizResultDTO.returnCallBackFail("处理失败");
    }

    @Override
    public TradeSearchResultDTO queryBizOperateResult(String userId,String bizNo) {
        return null;
    }
    public static void main(String[] args){

    }
}
