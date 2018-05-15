package com.zhuanyi.vjwealth.wallet.mobile.credit.server;

import com.zhuanyi.vjwealth.loan.order.vo.BankCardsVo;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.RechargeBindBankCardInitiDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.RechargeInitiDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.WjTradeAccountCardDTO;
import com.zhuanyi.vjwealth.wallet.service.balance.common.dto.BankBalanceLimitDTO;

import java.util.List;

/**
 * Created by hexy on 16/5/20.
 */
public interface IApplyForCreditService {

    /**
     * 充值初始化
     * @return  RechargeInitiDTO
     * @since 3.3
     */
    RechargeInitiDTO rechargeIniti();

    /**
     * @title 查询支持的银行卡列表
     * @return
     *  bankCode
        bankName
        balancePerLimit
        balanceDayLimit
        status	  		--状态
        status_desc		--状态描述
        limitTip		--限额描述
     * @since 3.3
     */
    List<BankBalanceLimitDTO> queryAllSupportBankList();

    /**
     * 充值绑定银行卡初始化
     * @param userId
     * @param bankCode
     * @return
     * @since 3.3
     */
    RechargeBindBankCardInitiDTO rechargeBindBankCardIniti(String userId, String bankCode);

    /**
     * 查询认证状态
     * @param userId
     * @return
     */
    String queryCertificationStatus(String userId);

    /**
     * 查询绑卡状态 公积金贷
     * @param userId
     * @return
     */
    WjTradeAccountCardDTO queryBindCardStatus(String userId,String bankCardNo);

    /**
     * 查询绑卡状态 cana
     * @param userId
     * @return
     */
    WjTradeAccountCardDTO queryBindCardStatusV2(String userId,String bankCardNo);

    List<BankCardsVo> queryBindCardList(String userId);

    //for cana
    List<BankCardsVo> queryBindCardListV2(String userId);

    /**
     * 获取小赢支持的银行卡列表
     * @param userId
     * @return
     */
    Integer queryIsTieBankCardById(String userId);
}
