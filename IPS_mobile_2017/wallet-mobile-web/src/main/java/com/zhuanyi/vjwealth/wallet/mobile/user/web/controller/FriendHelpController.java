package com.zhuanyi.vjwealth.wallet.mobile.user.web.controller;

import com.fab.web.controller.annotation.AppController;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.HouseFundLoanApplyInfoDTO;
import com.zhuanyi.vjwealth.loan.order.vo.IntentionPersonalInformationVo;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.IFundLoanService;
import com.zhuanyi.vjwealth.wallet.mobile.user.server.service.IFriendHelpService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.IMBUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by csy on 2016/11/2.
 */
@Controller
public class FriendHelpController {
    @Autowired
    IFriendHelpService friendHelpService;

    @Autowired
    private IFundLoanService fundLoanService;

    @RequestMapping("/api/app/user/queryHYBInviteQRCodePic.security")
    @AppController
    public Object queryHYBInviteQRCodePic(String userId) {
        return friendHelpService.queryShareQRCodePic(userId, "haoyoubang");
    }

    @RequestMapping("/api/app/user/queryHYBWeiXinShareInfo.security")
    @AppController
    public Object queryHYBWeiXinShareInfo(String userId) {
        return friendHelpService.queryWeiXinShareInfo(userId, "haoyoubang");
    }

    /**
     * 查询公积金城市支持的登录方式
     *
     * @param cityCode
     * @return
     */
    @RequestMapping("/api/v4.0/app/credit/fund/queryHouseFundCityLoginInfo")
    @AppController
    public Object queryHouseFundCityLoginInfo(String cityCode) {
        return friendHelpService.queryHouseFundCityLoginInfo(cityCode);
    }

    /**
     * 好友帮：申请公积金贷款意向单
     *
     * @param vo
     * @param code
     * @param inviteCode
     * @param activityCode
     * @return
     */
    @RequestMapping("/api/v4.0/app/credit/fund/applyHouseFundLoanIntentionForHYB")
    @AppController
    public Object applyHouseFundLoanIntentionFromHYB(IntentionPersonalInformationVo vo, String code, String inviteCode, String activityCode) {
        return fundLoanService.applyHouseFundLoanIntention(vo, code, inviteCode, activityCode);
    }

    /**
     * 好友帮：提交公积金贷首页额度申请
     *
     * @return
     */
    @RequestMapping("/api/v4.0/app/credit/fund/applyHouseFundLoanCreditLimit.security")
    @AppController
    public Object applyHouseFundLoanCreditLimit(HouseFundLoanApplyInfoDTO dto) {
        return friendHelpService.applyHouseFundLoanCreditLimit(dto);
    }

    /**
     * 好友帮：查询我的佣金
     * @param userId
     * @return
     */
    @RequestMapping("/api/v4.0/app/user/queryMyCommission.security")
    @AppController
    public Object queryMyCommission(String userId) {
        return friendHelpService.queryMyCommission(userId);
    }
}
