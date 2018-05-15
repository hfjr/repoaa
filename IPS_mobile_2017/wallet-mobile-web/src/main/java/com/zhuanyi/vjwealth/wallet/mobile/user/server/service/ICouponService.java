package com.zhuanyi.vjwealth.wallet.mobile.user.server.service;

        import java.util.Map;

public interface ICouponService {
    Map<String, Object> queryUserCashCouponList(String userId, String page, String couponStatus, String sortBy);

    Map<String, Object> queryUserInterestCouponList(String userId, String page, String couponStatus);

    Map<String, Object> queryCanInvestCouponList(String userId, String productId, String clientType, String investAmount);

    Object couponRedeem(String userId, String redeemCode, String couponType);
}
