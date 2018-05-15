package com.zhuanyi.vjwealth.wallet.mobile.packagehr.dto;

import java.io.Serializable;

/**
 * Created by yi on 16/3/28.
 */
public class ResponseDTO implements Serializable {


    private String key;
    private String content;
    private String sign;
    private String code;
    private String message;


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ResponseDTO{" +
                "key='" + key + '\'' +
                ", content='" + content + '\'' +
                ", sign='" + sign + '\'' +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
