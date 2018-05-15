package com.zhuanyi.vjwealth.wallet.service.mainbiz.user.Enum;

/**
 * Created by Administrator on 2015/11/27 0027.
 * 
 * 调用实例 ,融桥宝APP后台管理系统邮件类型：
 */
public enum EmailTypeEnum {

    OPEN_ACCOUNT_AUDIT("GZQB_MANAGE_OPEN_ACCOUNT_AUDIT","开户审核"),

    APPLY_PAY_CONFIRM("GZQB_MANAGE_APPLY_PAY_CONFIRM","申购打款确认"),

    APPLY_AUDIT("GZQB_MANAGE_APPLY_AUDIT","申购审核");
	
    // 邮件类型
    private final String value;
    // 邮件类型描述
    private final String description;

    EmailTypeEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }
    
    public String getDescription() {
        return description;
    }

}
