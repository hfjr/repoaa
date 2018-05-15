package com.zhuanyi.vjwealth.wallet.mobile.account.server.dto;

import java.io.Serializable;

/**
 * @Copyright (C), 2015-2016,蔚捷互联网金融服务有限公司
 * @desc: key  value实体
 * @author: Tony Tang
 * @date: 2016-08-31 17:08
 */
public class KeyValueDTO implements Serializable{
    private String label;
    private String value;

    public KeyValueDTO(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
