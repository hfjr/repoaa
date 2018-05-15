package com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.service;

import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.dto.DueRepaymentBankCardWithholdDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.dto.payment.DueRepaymentCommonDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.dto.TradeSearchResultDTO;

import java.math.BigDecimal;

/**
 * Created by wzf on 2016/9/14.
 */
public interface IDueRepaymentService {

    /**
     * 到期扣款接口
     * @param paramDto
     */
    TradeSearchResultDTO doRepay(DueRepaymentCommonDTO paramDto);


    /**
     * 从银行卡中扣款
     * @param paramDto
     * @return
     */
    TradeSearchResultDTO doCutBankcardAmount(DueRepaymentBankCardWithholdDTO paramDto);


    /**
     * 从存钱罐中扣钱
     * @param userId
     * @param amount
     * @param loanCode
     * @param planId
     * @return
     */
    public boolean cutAmountFromTa(String userId, BigDecimal amount, String loanCode, String planId);



}
