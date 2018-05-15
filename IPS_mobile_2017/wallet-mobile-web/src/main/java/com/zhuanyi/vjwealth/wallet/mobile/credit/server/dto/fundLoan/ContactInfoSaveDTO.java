package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan;

import java.util.List;
import java.util.Map;

/**
 * Created by wzf on 2016/10/31.
 */
public class ContactInfoSaveDTO {

    private String userId;
    private String borrowCode;
    private String directContactName;//直系联系人姓名
    private String directContactRelation;//直系联系人关系
    private String directContactPhone;//直系联系人手机
    private String otherContactName;//其他联系人姓名
    private String otherContactRelation;//其他联系人关系
    private String otherContactPhone;//其他联系人手机

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBorrowCode() {
        return borrowCode;
    }

    public void setBorrowCode(String borrowCode) {
        this.borrowCode = borrowCode;
    }

    public String getDirectContactName() {
        return directContactName;
    }

    public void setDirectContactName(String directContactName) {
        this.directContactName = directContactName;
    }

    public String getDirectContactPhone() {
        return directContactPhone;
    }

    public void setDirectContactPhone(String directContactPhone) {
        this.directContactPhone = directContactPhone;
    }

    public String getDirectContactRelation() {
        return directContactRelation;
    }

    public void setDirectContactRelation(String directContactRelation) {
        this.directContactRelation = directContactRelation;
    }

    public String getOtherContactName() {
        return otherContactName;
    }

    public void setOtherContactName(String otherContactName) {
        this.otherContactName = otherContactName;
    }

    public String getOtherContactPhone() {
        return otherContactPhone;
    }

    public void setOtherContactPhone(String otherContactPhone) {
        this.otherContactPhone = otherContactPhone;
    }

    public String getOtherContactRelation() {
        return otherContactRelation;
    }

    public void setOtherContactRelation(String otherContactRelation) {
        this.otherContactRelation = otherContactRelation;
    }
}
