package com.zhuanyi.vjwealth.wallet.mobile.credit.server.mapper;

import com.fab.server.annotation.Mapper;
import com.zhuanyi.vjwealth.loan.client.dto.ClientBasicInfoDTO;
import com.zhuanyi.vjwealth.loan.order.vo.BankCardsVo;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.RechargeBindBankCardInitiDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.RechargeInitiDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.WjTradeAccountCardDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan.CheckBingdingBankcardDTO;
import com.zhuanyi.vjwealth.wallet.service.balance.common.dto.BankBalanceLimitDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 公积金贷款(白领专享)
 * Created by wangzf on 16/10/14.
 */
@Mapper
public interface IFundLoanMapper {

    String queryUserPhoneByUserId(@Param("userId")String userId);

    int insertBankCardBingdingRecord(CheckBingdingBankcardDTO saveDto);

    ClientBasicInfoDTO queryUserBasicInfo(@Param("userId")String userId);
}
