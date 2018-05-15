package com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fab.core.exception.service.AppException;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.resDTO.ExpireRepayResDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.webservice.IApplyWageAdvanceDubboService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.dto.DueRepaymentBankCardWithholdDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.dto.payment.DueRepaymentCommonDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.mapper.IMBUserLoanOrderMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.service.IDueRepaymentService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.service.impl.support.AbstractDueRepaymentService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.dto.TradeSearchResultDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBOrderInfoDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper.IMBUserAccountMapper;

/**
 * vj自营信贷到期还款的实现类
 * Created by wzf on 2016/12/12.
 */
@Service("vjSelfLoanDueRepaymentServiceImpl")
public class VjSelfLoanDueRepaymentServiceImpl extends AbstractDueRepaymentService implements IDueRepaymentService {

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

    }

    /**
     * 从余额将钱扣入到冻结金额中，并生成还款中订单
     * @return
     */
    @Override
    @Transactional
    public String doCutMaAmountBalance(String userId,String loanCode,String planId,String repayAmount) {
        return null;
    }

    //
    @Override
    public TradeSearchResultDTO doCutBankcardAmount(final DueRepaymentBankCardWithholdDTO paramDto) {
        return super.invokingWithhold(paramDto,MBOrderInfoDTO.ORDER_TYPE_VJ_DUE_BANKCARD_WITHHOLD);
    }

    @Override
    public ExpireRepayResDTO updateLoanInfo(String userId,String loanCode,String planId,String repayAmount) {
        return null;
    }

    @Override
    @Transactional
    public void updateRepayOrderInfo(String userId,String loanCode,String planId,String repayAmount,String orderNo,ExpireRepayResDTO result) {

    }
}
