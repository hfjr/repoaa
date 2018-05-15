package com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto;

import java.util.Date;

/**
 * Created by zyx on 2016/7/12.
 */
public class ThirdPartyLogDTO {
    private String id;
    private String thirdParty;
    private String interfaceUrl;
    private String interfaceDesc;
    private String inParameter;
    private String outParameter;
    private String errMsg;
    private Date createDate;
    private String createBy;
    private Date updateDate;
    private String updateBy;
    private String memo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThirdParty() {
        return thirdParty;
    }

    public void setThirdParty(String thirdParty) {
        this.thirdParty = thirdParty;
    }

    public String getInterfaceUrl() {
        return interfaceUrl;
    }

    public void setInterfaceUrl(String interfaceUrl) {
        this.interfaceUrl = interfaceUrl;
    }

    public String getInterfaceDesc() {
        return interfaceDesc;
    }

    public void setInterfaceDesc(String interfaceDesc) {
        this.interfaceDesc = interfaceDesc;
    }

    public String getInParameter() {
        return inParameter;
    }

    public void setInParameter(String inParameter) {
        this.inParameter = inParameter;
    }

    public String getOutParameter() {
        return outParameter;
    }

    public void setOutParameter(String outParameter) {
        this.outParameter = outParameter;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
