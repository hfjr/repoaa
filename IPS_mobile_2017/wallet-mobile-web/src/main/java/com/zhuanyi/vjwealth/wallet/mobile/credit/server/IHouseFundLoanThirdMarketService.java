package com.zhuanyi.vjwealth.wallet.mobile.credit.server;

import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan.MarketHouseFundApplyInfoDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan.SDHouseFundLoanApplyDTO;

import javax.servlet.http.HttpServletRequest;

public interface IHouseFundLoanThirdMarketService {
    public Object sendSMSNotice(String phone);

    public Object applyCredit(MarketHouseFundApplyInfoDTO dto);

    public Object sdSendSMSNotice(String phone);

    public Object sdLoanApply(SDHouseFundLoanApplyDTO loanApplyDTO, HttpServletRequest request);
}
