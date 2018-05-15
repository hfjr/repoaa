package com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.service.impl;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.fab.core.exception.service.AppException;
import com.fab.core.logger.BaseLogger;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.reqDTO.ExpireRepayReqDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.resDTO.ExpireRepayResDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.webservice.IApplyWageAdvanceDubboService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.common.server.service.ISequenceService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.dto.DueRepaymentBankCardWithholdDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.dto.OrderRepayHistory;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.dto.payment.DueRepaymentCommonDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.mapper.IMBUserLoanOrderMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.service.IDueRepaymentService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.service.impl.support.AbstractDueRepaymentService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.dto.TradeSearchResultDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBOrderInfoDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper.IMBUserAccountMapper;

/**
 * 工资先享到期还款的实现类
 * Created by wzf on 2016/9/14.
 */
@Service("wageAdvanceDueRepaymentServiceImpl")
public class WageAdvanceDueRepaymentServiceImpl extends AbstractDueRepaymentService implements IDueRepaymentService {

    @Autowired
    private IMBUserLoanOrderMapper mBUserLoanOrderMapper;

    @Autowired
    private IMBUserAccountMapper userAccountMapper;

    @Autowired
    private IApplyWageAdvanceDubboService applyWageAdvanceDubboService;

    @Override
    public void validatorParams(DueRepaymentCommonDTO paramDto){
        if(StringUtils.isBlank(paramDto.getUserId()) || StringUtils.isBlank(paramDto.getLoanCode())|| StringUtils.isBlank(paramDto.getPlanId())
                || StringUtils.isBlank(paramDto.getRepaymentTotalMoney())){

            throw new AppException("存在参数为空的参数");
        }
    }
    
    @Override
    public void validatorBusiness(DueRepaymentCommonDTO paramDto) {
        if(mBUserLoanOrderMapper.checkWageAdvanceIsExistProcessOrder(paramDto.getUserId(),paramDto.getPlanId())){
            throw new AppException("存在还款中的订单");
        }
    }

    /**
     * 从余额将钱扣入到冻结金额中，并生成还款中订单
     * @return
     */
    @Override
    @Transactional
    public String doCutMaAmountBalance(String userId,String loanCode,String planId,String repayAmount) {
        BigDecimal repaymentTotalMoney = new BigDecimal(repayAmount);
        //1.将需要还款的金额从ma账户转入到冻结金额
        userAccountMapper.updateFreezeMaAmount(userId,repaymentTotalMoney);
        //2.生成还款中的订单
        String orderNo = super.getId("OR", ISequenceService.SEQ_NAME_ORDER_SEQ);
        String relOrderNo = mBUserLoanOrderMapper.queryApplyLoanOrderId(userId,loanCode, MBOrderInfoDTO.ORDER_TYPE_WAGE_ADVANCE_LOAN);//查询对应的借款订单编号
        mBUserLoanOrderMapper.addPledgeLoanOrder(MBOrderInfoDTO.getWageAdvanceDueRepay(userId, orderNo, repaymentTotalMoney,planId,relOrderNo));
        return orderNo;
    }

    //
    @Override
    public TradeSearchResultDTO doCutBankcardAmount(final DueRepaymentBankCardWithholdDTO paramDto) {
        return super.invokingWithhold(paramDto,MBOrderInfoDTO.ORDER_TYPE_DUE_BANKCARD_WITHHOLD);
    }

    @Override
    public ExpireRepayResDTO updateLoanInfo(String userId,String loanCode,String planId,String repayAmount) {
        ExpireRepayReqDTO reqDTO = new ExpireRepayReqDTO(userId,loanCode,repayAmount,planId,ExpireRepayReqDTO.PAY_CHANNEL_OO,"");
        BaseLogger.info("工资先享到期还款调用信贷系统入参："+ JSONObject.toJSONString(reqDTO));
        ExpireRepayResDTO resDTO = applyWageAdvanceDubboService.expireUpdateRepay(reqDTO);
        BaseLogger.info("工资先享到期还款调用信贷系统查询结果："+ JSONObject.toJSONString(resDTO));
        return resDTO;
    }

    @Override
    @Transactional
    public void updateRepayOrderInfo(String userId,String loanCode,String planId,String repayAmount,String orderNo,ExpireRepayResDTO result) {
        BigDecimal actualTotal = new BigDecimal(repayAmount);
        // 1. 从ma冻结金额中扣除金额
        userAccountMapper.deductionFreezeMaAmount(userId, actualTotal);
        //2.更新订单状态
        mBUserLoanOrderMapper.updateApplyLoanOrder(userId,orderNo,MBOrderInfoDTO.ORDER_STATUS_WAGE_DUE_REPAY_CONFIRM,"工资先享到期还款(余额)",null);
        //3.将ma的冻结金额的钱，转入ln账户
        userAccountMapper.updateAddLnAmount(userId,actualTotal);

        //4.生成ma的冻结金额到ln账户的账单
        String orderNo1 = getId("OR", ISequenceService.SEQ_NAME_ORDER_SEQ);
        userAccountMapper.addtOrderInfo(MBOrderInfoDTO.getMaFrozen2LnOrder(userId, orderNo1, actualTotal,loanCode));

        //生成还款历史记录
        mBUserLoanOrderMapper.addOrderRepayHistory(new OrderRepayHistory(orderNo,result.getPrincipal(),result.getInterest(),result.getPoundage(),result.getPenalty(),OrderRepayHistory.IS_VALID_YES));
    }
}
