package com.zhuanyi.vjwealth.wallet.mobile.credit.server.impl;

import com.zhuanyi.vjwealth.loan.order.vo.BankCardsVo;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.IApplyForCreditService;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.RechargeBindBankCardInitiDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.RechargeInitiDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.WjTradeAccountCardDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.mapper.ApplyForCreditMapper;
import com.zhuanyi.vjwealth.wallet.service.balance.common.dto.BankBalanceLimitDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hexy on 16/5/20.
 */
@Service
public class ApplyForCreditServiceImpl implements IApplyForCreditService {

    @Autowired
    ApplyForCreditMapper applyForCreditMapper;

    @Override
    public RechargeInitiDTO rechargeIniti() {
        return applyForCreditMapper.rechargeIniti();
    }

    @Override
    public List<BankBalanceLimitDTO> queryAllSupportBankList() {
        return applyForCreditMapper.queryAllSupportBankList();
    }

    @Override
    public RechargeBindBankCardInitiDTO rechargeBindBankCardIniti(String userId, String bankCode) {
        return applyForCreditMapper.rechargeBindBankCardIniti(userId,bankCode);
    }
    /**
     * 查询认证状态
     * @param userId
     * @return
     */
    @Override
    public String queryCertificationStatus(String userId) {
        return applyForCreditMapper.queryCertificationStatus(userId);
    }

    /**
     * 绑卡状态
     * @param userId
     * @return
     */
    @Override
    public WjTradeAccountCardDTO queryBindCardStatus(String userId,String bankCardNo) {
        return applyForCreditMapper.queryBindCardStatus(userId,bankCardNo);
    }

    /**
     * 绑卡状态
     * @param userId
     * @return
     */
    @Override
    public WjTradeAccountCardDTO queryBindCardStatusV2(String userId,String bankCardNo) {
        return applyForCreditMapper.queryBindCardStatusV2(userId,bankCardNo);
    }

    @Override
    public List<BankCardsVo> queryBindCardList(String userId) {
        return applyForCreditMapper.queryBindCardList(userId);
    }

    @Override
    public List<BankCardsVo> queryBindCardListV2(String userId) {
        return applyForCreditMapper.queryBindCardListV2(userId);
    }

    @Override
    public Integer queryIsTieBankCardById(String userId) {
        return applyForCreditMapper.queryIsTieBankCardById(userId);
    }
}
