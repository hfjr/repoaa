package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan;

import java.util.List;
import java.util.Map;

/**
 * Created by wzf on 2016/10/31.
 */
public class ContactInfoInitDTO {

    private String directContactNameLabel;
    private String directContactName;//直系联系人姓名
    private String directContactPhoneLabel;//直系联系人label
    private String directContactPhone;//直系联系人手机
    private String directContactRelationLabel;
    private Map<String,String> defaultDirectContactRelation;
    private List<Map<String,String>> directContactRelationSelection;

    private String otherContactNameLabel;//其他联系人
    private String otherContactName;//其他联系人姓名
    private String otherContactPhoneLabel;//其他联系人手机
    private String otherContactPhone;//其他联系人手机
    private String otherContactRelationLabel;
    private Map<String,String> defaultOtherContactRelation;
    private List<Map<String,String>> otherContactRelationSelection;

    public String getDirectContactNameLabel() {
        return directContactNameLabel;
    }

    public void setDirectContactNameLabel(String directContactNameLabel) {
        this.directContactNameLabel = directContactNameLabel;
    }

    public String getDirectContactName() {
        return directContactName;
    }

    public void setDirectContactName(String directContactName) {
        this.directContactName = directContactName;
    }

    public String getDirectContactPhoneLabel() {
        return directContactPhoneLabel;
    }

    public void setDirectContactPhoneLabel(String directContactPhoneLabel) {
        this.directContactPhoneLabel = directContactPhoneLabel;
    }

    public String getDirectContactPhone() {
        return directContactPhone;
    }

    public void setDirectContactPhone(String directContactPhone) {
        this.directContactPhone = directContactPhone;
    }

    public String getOtherContactNameLabel() {
        return otherContactNameLabel;
    }

    public void setOtherContactNameLabel(String otherContactNameLabel) {
        this.otherContactNameLabel = otherContactNameLabel;
    }

    public String getOtherContactName() {
        return otherContactName;
    }

    public void setOtherContactName(String otherContactName) {
        this.otherContactName = otherContactName;
    }

    public String getOtherContactPhoneLabel() {
        return otherContactPhoneLabel;
    }

    public void setOtherContactPhoneLabel(String otherContactPhoneLabel) {
        this.otherContactPhoneLabel = otherContactPhoneLabel;
    }

    public String getOtherContactPhone() {
        return otherContactPhone;
    }

    public void setOtherContactPhone(String otherContactPhone) {
        this.otherContactPhone = otherContactPhone;
    }

    public Map<String, String> getDefaultDirectContactRelation() {
        return defaultDirectContactRelation;
    }

    public void setDefaultDirectContactRelation(Map<String, String> defaultDirectContactRelation) {
        this.defaultDirectContactRelation = defaultDirectContactRelation;
    }

    public List<Map<String, String>> getDirectContactRelationSelection() {
        return directContactRelationSelection;
    }

    public void setDirectContactRelationSelection(List<Map<String, String>> directContactRelationSelection) {
        this.directContactRelationSelection = directContactRelationSelection;
    }

    public Map<String, String> getDefaultOtherContactRelation() {
        return defaultOtherContactRelation;
    }

    public void setDefaultOtherContactRelation(Map<String, String> defaultOtherContactRelation) {
        this.defaultOtherContactRelation = defaultOtherContactRelation;
    }

    public List<Map<String, String>> getOtherContactRelationSelection() {
        return otherContactRelationSelection;
    }

    public void setOtherContactRelationSelection(List<Map<String, String>> otherContactRelationSelection) {
        this.otherContactRelationSelection = otherContactRelationSelection;
    }

    public String getDirectContactRelationLabel() {
        return directContactRelationLabel;
    }

    public void setDirectContactRelationLabel(String directContactRelationLabel) {
        this.directContactRelationLabel = directContactRelationLabel;
    }

    public String getOtherContactRelationLabel() {
        return otherContactRelationLabel;
    }

    public void setOtherContactRelationLabel(String otherContactRelationLabel) {
        this.otherContactRelationLabel = otherContactRelationLabel;
    }
}
