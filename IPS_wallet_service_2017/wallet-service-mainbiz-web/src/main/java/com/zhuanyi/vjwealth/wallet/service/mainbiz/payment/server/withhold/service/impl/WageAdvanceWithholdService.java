package com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import com.fab.core.logger.BaseLogger;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fab.core.exception.service.AppException;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.dto.CallbackBizResultDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.dto.OrderInfoQueryDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.dto.payment.WageAdvancePreparePaymentDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.mapper.IMBUserLoanOrderMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.service.IMBUserOrderService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.dto.TradeSearchResultDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.service.IWithholdService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBOrderInfoDTO;

/**
 * 工资先享 - 银行卡扣款实现类
 * Created by wzf on 2016/9/13.
 */
@Service("wageAdvanceWithholdService")
public class WageAdvanceWithholdService implements IWithholdService {

    private static final String ORDER_TYPE = MBOrderInfoDTO.ORDER_TYPE_EARLY_BANKCARD_WITHHOLD;

    @Autowired
    private IMBUserOrderService mBUserOrderService;

    @Autowired
    private IMBUserLoanOrderMapper mBUserLoanOrderMapper;

    @Override
    public boolean isSupportOrderType(String orderType) {
        if(StringUtils.isNotBlank(orderType) && orderType.equals(this.ORDER_TYPE)){
            return true;
        }
        return false;
    }

    @Override
    public Object preBizOperateParameter(Object preParmeter) {
        Map<String,String> map = (Map)preParmeter;
        WageAdvancePreparePaymentDTO paramDto = JSONObject.parseObject(JSONObject.toJSONString(map),WageAdvancePreparePaymentDTO.class);
        try{
            return  mBUserOrderService.preOperateForWageAdvance(paramDto.getUserId(),paramDto.getCardId(),paramDto.getLoanCodes(),paramDto.getPrincipal(),paramDto.getRepaymentTotalMoney());
        }catch(Exception ex){
            BaseLogger.error("工资先享preOperateForWageAdvance异常",ex);
            throw new AppException("预处理异常");
        }
    }



    @Override
    public Object processOperator(String userId,String bizNo) {

        mBUserOrderService.callbackForWithholdProcess(userId,bizNo,MBOrderInfoDTO.ORDER_STATUS_EARLY_BANKCARD_WITHHOLD_PROCESS,
                MBOrderInfoDTO.ORDER_STATUS_WAGE_REPAY_BANKCARD_PROCESS,"工资先享提前还款(银行卡)");
        return null;
    }

    @Override
    public CallbackBizResultDTO callBackBizOperateSuccess(String userId,String bizNo,BigDecimal amount) {
        BaseLogger.info("工资先享银行卡扣款成功回调");

        mBUserOrderService.callbackForWithholdSuccess(userId,bizNo,amount.toString(),MBOrderInfoDTO.ORDER_STATUS_EARLY_BANKCARD_WITHHOLD_CONFIRM,
                                              MBOrderInfoDTO.ORDER_STATUS_WAGE_REPAY_BANKCARD_CONFIRM,"工资先享提前还款(银行卡)");

    	return CallbackBizResultDTO.returnCallBackSucces("处理成功");
    }

    @Override
    public CallbackBizResultDTO callBackBizOperateFail(String userId,String bizNo,BigDecimal amount) {

        mBUserOrderService.callbackForWithholdFail(userId,bizNo,MBOrderInfoDTO.ORDER_STATUS_EARLY_BANKCARD_WITHHOLD_FAIL,
                                                          MBOrderInfoDTO.ORDER_STATUS_WAGE_REPAY_BANKCARD_FAIL,"工资先享提前还款(银行卡)");

        return CallbackBizResultDTO.returnCallBackSucces("处理成功");
    }

    @Override
    public TradeSearchResultDTO queryBizOperateResult(String userId,String bizNo) {

        //校验
        if(StringUtils.isBlank(bizNo) ||StringUtils.isBlank(userId)){
            throw new AppException("查询扣款结果的参数为空");
        }

            //查询账单状态（在扣款接口的回调方法中，成功或失败之后，会更新账单的状态，所有如果成功：表示扣款成功，失败：扣款失败；处理中：还没有返回结果）
        String orderStatus = mBUserLoanOrderMapper.queryOrderStatusByOrderNo(userId,bizNo);
            //如果充值成功，则直接返回true
        if(orderStatus.equals(MBOrderInfoDTO.ORDER_STATUS_EARLY_BANKCARD_WITHHOLD_CONFIRM)){
                return TradeSearchResultDTO.buildSuccess("扣款成功");
            }
        else if(orderStatus.equals(MBOrderInfoDTO.ORDER_STATUS_EARLY_BANKCARD_WITHHOLD_FAIL)){
                return TradeSearchResultDTO.buildFailed("扣款失败");
            }
        else if(orderStatus.equals(MBOrderInfoDTO.ORDER_STATUS_EARLY_BANKCARD_WITHHOLD_PROCESS)) {
                //当状态为处理中时，则sleep一段时间后再次查询是否有处理结果；如果循环查询总耗时超过设定时间，则退出循环查询,返回处理中的结果
                    return TradeSearchResultDTO.buildProcess("扣款处理中");

        }
        return TradeSearchResultDTO.buildProcess("扣款处理中");
    }


    public static void main(String[] args){

        Map<String,String> map = new HashMap<>();
        map.put("userId","1234");
        WageAdvancePreparePaymentDTO paramDto = JSONObject.parseObject(JSONObject.toJSONString(map),WageAdvancePreparePaymentDTO.class);
        System.out.print(paramDto.getUserId());
    }
}
