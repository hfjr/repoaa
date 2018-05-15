package com.zhuanyi.vjwealth.wallet.mobile.index.server.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Map;

/**
 * @Copyright (C), 2015-2016,蔚捷互联网金融服务有限公司
 * @desc: 活动信息实体
 * @author: Tony Tang
 * @date: 2016-08-31 17:08
 */
public class WjActivityInfoDTO implements Serializable{
    private String title;
    private String subTitle;
    private String functionType;
    private String functionCode;
    private String paramsStr;
    private Map<String,String> params;
    private String detailUrl;
    private String publishTime;
    private String smallPic;
    private String weixinLinkUrl;
    private String weixinPicUrl;

    public String getWeixinLinkUrl() {
        return weixinLinkUrl;
    }

    public void setWeixinLinkUrl(String weixinLinkUrl) {
        this.weixinLinkUrl = weixinLinkUrl;
    }

    public String getWeixinPicUrl() {
        return weixinPicUrl;
    }

    public void setWeixinPicUrl(String weixinPicUrl) {
        this.weixinPicUrl = weixinPicUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getFunctionType() {
        return functionType;
    }

    public void setFunctionType(String functionType) {
        this.functionType = functionType;
    }

    public String getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode;
    }
    @JsonIgnore
    public String getParamsStr() {
        return paramsStr;
    }

    public void setParamsStr(String paramsStr) {
        this.paramsStr = paramsStr;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }


    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getSmallPic() {
        return smallPic;
    }

    public void setSmallPic(String smallPic) {
        this.smallPic = smallPic;
    }

}
