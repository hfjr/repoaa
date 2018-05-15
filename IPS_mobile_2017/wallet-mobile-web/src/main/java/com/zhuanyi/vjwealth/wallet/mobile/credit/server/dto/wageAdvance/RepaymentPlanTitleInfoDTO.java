package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.wageAdvance;

/**
 * 工资先享-借款申请初始化
 * Created by wangzf on 16/5/20.
 */
public class RepaymentPlanTitleInfoDTO {

    private String key;//还款方式
    private String value;//等额本息..
    private String isSelected;//是否被选中
    private String desc;//描述


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(String isSelected) {
        this.isSelected = isSelected;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
