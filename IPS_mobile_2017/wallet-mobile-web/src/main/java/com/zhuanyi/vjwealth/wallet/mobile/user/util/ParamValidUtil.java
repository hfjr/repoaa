package com.zhuanyi.vjwealth.wallet.mobile.user.util;

import com.fab.core.exception.service.AppException;
import org.apache.commons.lang.StringUtils;

import java.util.regex.Pattern;

public class ParamValidUtil {
    public static void validatorUserId(String userId) {
        if (StringUtils.isBlank(userId)) {
            throw new AppException("用户ID不能为空");
        }
    }

    public static void validatorPage(String page) {
        if (StringUtils.isBlank(page)) {
            throw new AppException("页码不能为空");
        }

        if (!StringUtils.isNumeric(page) || Integer.parseInt(page) < 1 || Integer.parseInt(page) % 1 > 0) {
            throw new AppException("页码数值不合法，必须为大于0的整数");
        }
    }

    public static void validatorPhone(String phone) {
        if (StringUtils.isBlank(phone)) {
            throw new AppException("手机号不能为空");
        }
        if (!Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$").matcher(phone).matches()) {
            throw new AppException("请输入正确的手机号");
        }
    }

    public static void validatorInviteCode(String inviteCode) {
        if (!StringUtils.isBlank(inviteCode) && inviteCode.length() > 100) {
            throw new AppException("非法邀请码");
        }
    }

    public static void validatorChannelNo(String channelNo) {
        if (!StringUtils.isBlank(channelNo) && channelNo.length() > 100) {
            throw new AppException("非法渠道编码");
        }
    }

    public static void validatorChannelUserId(String channelUserId) {
        if (!StringUtils.isBlank(channelUserId) && channelUserId.length() > 100) {
            throw new AppException("非法渠道用户ID");
        }
    }

    public static void validatorActivityCode(String activityCode) {
        if (!StringUtils.isBlank(activityCode) && activityCode.length() > 100) {
            throw new AppException("非法活动编码");
        }
    }

    public static void validatorRedeemCode(String redeemCode) {
        if (StringUtils.isBlank(redeemCode)) {
            throw new AppException("兑换码不能为空");
        }
        if (redeemCode.length() > 30 || redeemCode.length() < 10) {
            throw new AppException("兑换码格式错误");
        }
    }

    public static void validatorCouponType(String couponType) {
        if (StringUtils.isBlank(couponType)) {
            throw new AppException("优惠券类型不能为空");
        }
        if (!couponType.equals("cashCoupon") && !couponType.equals("rateCoupon")) {
            throw new AppException("优惠券类型错误");
        }
    }

    public static void validatorIdentityNo(String idNo) {
        // 非空校验
        if (StringUtils.isBlank(idNo)) {
            throw new AppException("证件号码不能为空");
        }
        // 格式校验
        if (!IDCard.IDCardValidate(idNo)) {
            throw new AppException("身份证号码格式错误");
        }
    }

    public static void validatorRealName(String realName) {
        if (StringUtils.isBlank(realName)) {
            throw new AppException("用户姓名不能为空");
        }
    }

    public static void validatorBankCardNo(String realName) {
        if (StringUtils.isBlank(realName)) {
            throw new AppException("银行卡不能为空");
        }
    }

    public static void validatorChannelType(String channelType) {
        if (StringUtils.isBlank(channelType)) {
            throw new AppException("渠道类型不能为空");
        }
    }

    public static void validatorDeviceType(String deviceType) {
        if (StringUtils.isBlank(deviceType)) {
            throw new AppException("设备类型不能为空");
        }
        if (!deviceType.equals("IOS") && !deviceType.equals("ANDROID")) {
            throw new AppException("非法设备类型");
        }
    }

    public static void validatorAppType(String appType) {
        if (StringUtils.isBlank(appType)) {
            throw new AppException("APP类型不能为空");
        }
        if (!appType.trim().equals("MICRO") && !appType.trim().equals("CLOUD")) {
            throw new AppException("非法APP类型");
        }
    }

    public static void validatorAppVersion(String channelType) {
        if (StringUtils.isBlank(channelType)) {
            throw new AppException("版本号不能为空");
        }
    }

    public static void validatorNotEmpty(String value) {
        if (StringUtils.isBlank(value)) {
            throw new AppException("参数不能为空");
        }
    }

    public static void validatorNotEmpty(String value,String msg) {
        if (StringUtils.isBlank(value)) {
            throw new AppException(msg);
        }
    }
}
