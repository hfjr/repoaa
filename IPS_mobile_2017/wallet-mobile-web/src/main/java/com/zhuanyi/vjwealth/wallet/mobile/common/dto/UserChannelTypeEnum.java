package com.zhuanyi.vjwealth.wallet.mobile.common.dto;

/**
 * 用户渠道来源枚举
 */
public enum UserChannelTypeEnum {
    WEIXIN("weixin", "微信"),
    IOS("IOS", "苹果"),
    ANDROID("Android", "安卓");

    private UserChannelTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private String code;
    private String desc;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
