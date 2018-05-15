package com.zhuanyi.vjwealth.wallet.mobile.user.server.service.impl;

import com.fab.core.annotation.Remote;
import com.fab.core.exception.service.AppException;
import com.zhuanyi.vjwealth.wallet.mobile.user.server.service.ICouponService;
import com.zhuanyi.vjwealth.wallet.mobile.user.util.ParamValidUtil;
import com.zhuanyi.vjwealth.wallet.service.coupon.dto.CouponForInvest;
import com.zhuanyi.vjwealth.wallet.service.coupon.dto.UserCouponDTO;
import com.zhuanyi.vjwealth.wallet.service.coupon.server.service.IUserCouponService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CouponService implements ICouponService {
    //@Remote
	@Autowired
    IUserCouponService userCouponService;

    @Override
    public Map<String, Object> queryUserCashCouponList(String userId, String page, String couponStatus, String sortBy) {
        ParamValidUtil.validatorUserId(userId);
        ParamValidUtil.validatorPage(page);
        validatorCouponStatus(couponStatus);

        List<UserCouponDTO> resultList = queryUserCashCouponRecord(userId, page, couponStatus, sortBy);
        //3.包装返回结果
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("records", resultList);
        returnMap.put("isMore", false);
        if (resultList != null && resultList.size() >= 10) {
            returnMap.put("isMore", true);
        }
        return returnMap;
    }

    private List<UserCouponDTO> queryUserCashCouponRecord(String userId, String page, String couponStatus, String sortBy) {
        Integer currentPage = (Integer.parseInt(page) - 1) * 10;
        if (couponStatus.equals("unUsed")) {
            return userCouponService.queryUserCashCouponUnUsedList(userId, currentPage, sortBy);
        }
        if (couponStatus.equals("history")) {
            return userCouponService.queryUserCashCouponHistoryList(userId, currentPage);
        }
        return null;
    }

    @Override
    public Map<String, Object> queryCanInvestCouponList(String userId, String productId, String clientType, String investAmount) {
        ParamValidUtil.validatorUserId(userId);
        if (StringUtils.isBlank(productId)) {
            throw new AppException("产品ID不能为空");
        }
        if (!StringUtils.isNumeric(investAmount)) {
            throw new AppException("投资金额不合法");
        }
        if (Integer.parseInt(investAmount) % 100 > 0) {
            throw new AppException("投资金额必须为100的整数倍");
        }
        List<CouponForInvest> cashCouponList = userCouponService.queryCanInvestCashCouponList(userId, productId, clientType, investAmount);
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("cashCouponList", cashCouponList);
        return returnMap;
    }

    @Override
    public Object couponRedeem(String userId, String redeemCode, String couponType) {
        ParamValidUtil.validatorUserId(userId);
        ParamValidUtil.validatorRedeemCode(redeemCode);
        ParamValidUtil.validatorCouponType(couponType);
        boolean flag = true;
        if (couponType.equals("cashCoupon")) {
            flag = userCouponService.cashCouponRedeem(userId, redeemCode);
        }
        if (couponType.equals("rateCoupon")) {
            flag = userCouponService.rateCouponRedeem(userId, redeemCode);
        }
        if (flag == false) {
            throw new AppException("兑换失败");
        }
        return flag;
    }

    @Override
    public Map<String, Object> queryUserInterestCouponList(String userId, String page, String couponStatus) {
        ParamValidUtil.validatorUserId(userId);
        ParamValidUtil.validatorPage(page);
        validatorCouponStatus(couponStatus);
        List<UserCouponDTO> resultList = queryUserInterestCouponRecord(userId, page, couponStatus);
        //3.包装返回结果
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("records", resultList);
        returnMap.put("isMore", false);
        if (resultList != null && resultList.size() >= 10) {
            returnMap.put("isMore", true);
        }
        return returnMap;
    }

    private List<UserCouponDTO> queryUserInterestCouponRecord(String userId, String page, String couponStatus) {
        Integer currentPage = (Integer.parseInt(page) - 1) * 10;
        if (couponStatus.equals("unUsed")) {
            return userCouponService.queryUserInterestCouponUnUsedList(userId, currentPage, "");
        }
        if (couponStatus.equals("history")) {
            return userCouponService.queryUserInterestCouponHistoryList(userId, currentPage);
        }
        return null;
    }

    private void validatorCouponStatus(String couponStatus) {
        if (StringUtils.isBlank(couponStatus)) {
            throw new AppException("红包状态不能为空");
        }
    }
}
