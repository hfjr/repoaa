package com.zhuanyi.vjwealth.wallet.mobile.index.server.dto;

/**
 * Created by csy on 2016/12/5.
 */
public class MyAssetsDTO {
    private String amount;//余额
    private String icon;//图标key
    private String label;//标签值
    private String color;//颜色十六进制值

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
