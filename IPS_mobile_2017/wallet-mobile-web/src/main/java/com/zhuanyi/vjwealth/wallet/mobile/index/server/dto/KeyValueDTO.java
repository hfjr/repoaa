package com.zhuanyi.vjwealth.wallet.mobile.index.server.dto;

import java.io.Serializable;

/**
 * @Copyright (C), 2015-2016,蔚捷互联网金融服务有限公司
 * @desc: key  value实体
 * @author: Tony Tang
 * @date: 2016-08-31 17:08
 */
public class KeyValueDTO implements Serializable{
    private String code;
    private String name;
    private String value;
    private String desc;
    private String buttonText;

    public KeyValueDTO(String code, String name, String value) {
        this.code = code;
        this.name = name;
        this.value = value;
    }
    public KeyValueDTO(String code, String name, String value, String desc) {
        this.code = code;
        this.name = name;
        this.value = value;
        this.desc = desc;
    }
    public KeyValueDTO(String code, String name, String value, String desc, String buttonText) {
        this.code = code;
        this.name = name;
        this.value = value;
        this.desc = desc;
        this.buttonText = buttonText;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }
}
