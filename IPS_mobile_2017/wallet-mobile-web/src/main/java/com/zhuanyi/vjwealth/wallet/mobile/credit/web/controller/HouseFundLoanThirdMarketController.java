package com.zhuanyi.vjwealth.wallet.mobile.credit.web.controller;

import com.fab.web.controller.annotation.AppController;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.IHouseFundLoanThirdMarketService;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan.MarketHouseFundApplyInfoDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan.SDHouseFundLoanApplyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HouseFundLoanThirdMarketController {
    @Autowired
    private IHouseFundLoanThirdMarketService houseFundLoanThirdMarketService;

    @RequestMapping("/api/market/credit/houseFundLoan/SendSMSNotice")
    @AppController
    public Object sendSMSNotice(String phone) {
        return houseFundLoanThirdMarketService.sendSMSNotice(phone);
    }

    @RequestMapping("/api/market/credit/houseFundLoan/applyCredit")
    @AppController
    public Object applyCredit(MarketHouseFundApplyInfoDTO dto) {
        return houseFundLoanThirdMarketService.applyCredit(dto);
    }

    @RequestMapping("api/market/sd/credit/houseFundLoan/sendSMSNotice")
    @AppController
    public Object sendSMSNoticeHouseFundLoanApply(String phone) {
        return houseFundLoanThirdMarketService.sdSendSMSNotice(phone);
    }

    @RequestMapping("api/market/sd/credit/houseFundLoan/applyCredit")
    @AppController
    public Object sdHouseFundLoanApply(SDHouseFundLoanApplyDTO loanApplyDTO, HttpServletRequest request) {
        return houseFundLoanThirdMarketService.sdLoanApply(loanApplyDTO, request);
    }
}
