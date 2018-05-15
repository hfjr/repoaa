package com.zhuanyi.vjwealth.wallet.mobile.credit.web.controller;

import com.fab.web.controller.annotation.AppController;
import com.zhuanyi.vjwealth.loan.order.vo.IntentionPersonalInformationVo;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.IFundLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 随手贷
 * Created by wzf on 2016/11/18.
 */
@Controller
@RequestMapping("/api/v4.0")
public class IntentionPersonalController {
    @Autowired
    private IFundLoanService fundLoanService;


    /**
     * 申请公积金贷款意向单
     *
     * @param vo
     * @return
     */
    @RequestMapping("/app/credit/fund/applyHouseFundLoanIntention")
    @AppController
    public Object applyHouseFundLoanIntention(IntentionPersonalInformationVo vo, String code) {
        return fundLoanService.applyHouseFundLoanIntention(vo, code);
    }

    /**
     * 更新公积金贷款意向相关字段（身份证号码、公积金账号、密码）
     *
     * @param vo
     * @return
     */
    @RequestMapping("/app/credit/fund/updateHouseFundLoanIntention")
    @AppController
    public Object updateHouseFundLoanIntention(IntentionPersonalInformationVo vo) {
        return fundLoanService.updateHouseFundLoanIntention(vo);
    }

    /**
     * 更新随手贷来源客户公积金信息
     *
     * @param vo
     * @return
     */
    @RequestMapping("/app/credit/fund/updateHouseFundLoanIntentionForSSD")
    @AppController
    public Object updateHouseFundLoanIntentionForSSD(IntentionPersonalInformationVo vo, String code) {
        return fundLoanService.updateHouseFundLoanIntentionForSSD(vo, code);
    }
}
