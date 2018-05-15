package com.zhuanyi.vjwealth.wallet.mobile.user.web.controller;

import com.fab.web.controller.annotation.AppController;
import com.zhuanyi.vjwealth.wallet.mobile.user.server.service.ICouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CouponController {
    @Autowired
    private ICouponService couponService;

    /**
     * 查询用户红包列表
     *
     * @param userId
     * @param page
     * @param couponStatus
     * @return
     */
    @RequestMapping("/api/app/user/queryUserCashCouponList.security")
    @AppController
    public Object queryUserCashCouponList(String userId, String page, String couponStatus, String sortBy) {
        return couponService.queryUserCashCouponList(userId, page, couponStatus, sortBy);
    }

    /**
     * 投资时查询可用的红包列表
     *
     * @param userId
     * @param productId
     * @param clientType
     * @return
     */
    @RequestMapping("/api/app/user/queryCanInvestCouponList.security")
    @AppController
    public Object queryCanInvestCouponList(String userId, String productId, String clientType, String investAmount) {
        return couponService.queryCanInvestCouponList(userId, productId, clientType, investAmount);
    }

    /**
     * 兑换优惠券
     *
     * @param userId
     * @param redeemCode
     * @param couponType
     * @return
     */
    @RequestMapping("/api/app/user/couponRedeem.security")
    @AppController
    public Object couponRedeem(String userId, String redeemCode, String couponType) {
        return couponService.couponRedeem(userId, redeemCode, couponType);
    }


//    /**
//     * 查询用户加息券列表
//     *
//     * @param userId
//     * @param page
//     * @param couponStatus
//     * @return
//     */
//    @RequestMapping("/api/app/user/queryUserInterestCouponList.security")
//    @AppController
//    public Object queryUserInterestCouponList(String userId, String page, String couponStatus) {
//        return couponService.queryUserInterestCouponList(userId, page, couponStatus);
//    }
}
