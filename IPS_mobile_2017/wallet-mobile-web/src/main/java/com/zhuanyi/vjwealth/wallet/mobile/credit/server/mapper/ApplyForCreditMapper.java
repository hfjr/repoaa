package com.zhuanyi.vjwealth.wallet.mobile.credit.server.mapper;

import com.fab.server.annotation.Mapper;
import com.zhuanyi.vjwealth.loan.order.vo.BankCardsVo;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.FundLoanShareStatisticsDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.RechargeBindBankCardInitiDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.RechargeInitiDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.WjTradeAccountCardDTO;
import com.zhuanyi.vjwealth.wallet.service.balance.common.dto.BankBalanceLimitDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by yi on 16/5/20.
 */
@Mapper
public interface ApplyForCreditMapper {
	
	//查询支付密码状态
	RechargeInitiDTO rechargeIniti();
	
	//充值绑定银行卡初始化
	RechargeBindBankCardInitiDTO rechargeBindBankCardIniti(@Param("userId") String userId, @Param("bankCode") String bankCode);

	//查询银行卡支持的列表
	List<BankBalanceLimitDTO> queryAllSupportBankList();
    /**
     * 查询认证状态
     * @param userId
     * @return
     */
    String queryCertificationStatus(@Param("userId")String userId);
    /**
     * 绑卡状态 公积金贷
     * @param userId
     * @return
     */
    WjTradeAccountCardDTO queryBindCardStatus(@Param("userId")String userId,@Param("bankCardNo")String bankCardNo);
    /**
     * 绑卡状态 cana
     * @param userId
     * @return
     */
    WjTradeAccountCardDTO queryBindCardStatusV2(@Param("userId")String userId,@Param("bankCardNo")String bankCardNo);

    /**
     * 绑卡列表 公积金贷
     * @param userId
     * @return
     */
    List<BankCardsVo> queryBindCardList(@Param("userId")String userId);

    /**
     * 绑卡列表 cana
     * @param userId
     * @return
     */
    List<BankCardsVo> queryBindCardListV2(@Param("userId")String userId);

    Integer queryIsTieBankCardById(@Param("userId")String userId);

    void insertFundLoanShareInfo(FundLoanShareStatisticsDTO dto);
}
