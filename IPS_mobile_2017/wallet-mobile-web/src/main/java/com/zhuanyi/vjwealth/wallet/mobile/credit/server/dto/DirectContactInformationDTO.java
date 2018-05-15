package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto;

import java.io.Serializable;

/**
 * Created by hexy on 16/5/12.
 */
public class DirectContactInformationDTO implements Serializable {

    private String userId;
    private String directContactName; // 直接联系人姓名
    private String directContactRelationship; //直接联系人关系
    private String directContactPhone; //直接联系人手机
    private String borrowCode;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDirectContactName() {
        return directContactName;
    }

    public void setDirectContactName(String directContactName) {
        this.directContactName = directContactName;
    }

    public String getDirectContactRelationship() {
        return directContactRelationship;
    }

    public void setDirectContactRelationship(String directContactRelationship) {
        this.directContactRelationship = directContactRelationship;
    }

    public String getDirectContactPhone() {
        return directContactPhone;
    }

    public void setDirectContactPhone(String directContactPhone) {
        this.directContactPhone = directContactPhone;
    }

    public String getBorrowCode() {
        return borrowCode;
    }

    public void setBorrowCode(String borrowCode) {
        this.borrowCode = borrowCode;
    }
}
