package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.loanProduct;

/**
 * 征信方式枚举
 * Created by hexy on 16/8/31.
 */
public enum CreditWayCodeEnum {

    CREDIT_FUND("P2016061200010000","公积金信用贷款"),
    CREDIT_PFI("P2016061200010002","PFI信用(信贷产品-工资先享)"),
    CREDIT_HOUSE("P2016061200010003","工资易贷(房抵贷)"),
    CREDIT_PLEDGE_INVEST("P2016061200010005","理财资产(信贷产品-流通宝)"),
    CREDIT_JY_HOUSE("P2016061200010006","工资易贷(工薪族房抵贷)"),
    CREDIT_S_HOUSE("P2016061200010007","工资易贷(赎楼贷)");

    private String value;
    private String desc;

    CreditWayCodeEnum(String value, String desc){
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
