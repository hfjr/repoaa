package com.zhuanyi.vjwealth.wallet.mobile.product.server.dto;

/**
 * Created by yi on 16/3/5.
 */
public class KeyValueDTO {

    private String key;//账单编号
    private String value;//标题

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
}
