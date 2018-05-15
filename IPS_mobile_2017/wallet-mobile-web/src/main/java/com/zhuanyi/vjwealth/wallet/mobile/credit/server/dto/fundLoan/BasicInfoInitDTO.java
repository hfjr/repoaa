package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan;

import java.util.List;
import java.util.Map;

/**
 * Created by wzf on 2016/10/31.
 */
public class BasicInfoInitDTO {

    private String educationLabel;
    private Map<String,String> defaultEducation;
    private List<Map<String,String>> educationSelection;
    private String collegeLabel;
    private String college;
    private String marriageLabel;
    private Map<String,String> defaultMarriage;
    private List<Map<String,String>> marriageSelection;
    private String addressProvinceAndCityLabel;
    private String addressProvinceAndCity;
    private String addressDetailLabel;
    private String addressDetail;
    private String addressDistrictLabel;
    private String addressDistrict;

    public String getEducationLabel() {
        return educationLabel;
    }

    public void setEducationLabel(String educationLabel) {
        this.educationLabel = educationLabel;
    }

    public Map<String, String> getDefaultEducation() {
        return defaultEducation;
    }

    public void setDefaultEducation(Map<String, String> defaultEducation) {
        this.defaultEducation = defaultEducation;
    }

    public List<Map<String, String>> getEducationSelection() {
        return educationSelection;
    }

    public void setEducationSelection(List<Map<String, String>> educationSelection) {
        this.educationSelection = educationSelection;
    }

    public String getCollegeLabel() {
        return collegeLabel;
    }

    public void setCollegeLabel(String collegeLabel) {
        this.collegeLabel = collegeLabel;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getMarriageLabel() {
        return marriageLabel;
    }

    public void setMarriageLabel(String marriageLabel) {
        this.marriageLabel = marriageLabel;
    }

    public Map<String, String> getDefaultMarriage() {
        return defaultMarriage;
    }

    public void setDefaultMarriage(Map<String, String> defaultMarriage) {
        this.defaultMarriage = defaultMarriage;
    }

    public List<Map<String, String>> getMarriageSelection() {
        return marriageSelection;
    }

    public void setMarriageSelection(List<Map<String, String>> marriageSelection) {
        this.marriageSelection = marriageSelection;
    }

    public String getAddressProvinceAndCityLabel() {
        return addressProvinceAndCityLabel;
    }

    public void setAddressProvinceAndCityLabel(String addressProvinceAndCityLabel) {
        this.addressProvinceAndCityLabel = addressProvinceAndCityLabel;
    }

    public String getAddressProvinceAndCity() {
        return addressProvinceAndCity;
    }

    public void setAddressProvinceAndCity(String addressProvinceAndCity) {
        this.addressProvinceAndCity = addressProvinceAndCity;
    }

    public String getAddressDetailLabel() {
        return addressDetailLabel;
    }

    public void setAddressDetailLabel(String addressDetailLabel) {
        this.addressDetailLabel = addressDetailLabel;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public String getAddressDistrictLabel() {
        return addressDistrictLabel;
    }

    public void setAddressDistrictLabel(String addressDistrictLabel) {
        this.addressDistrictLabel = addressDistrictLabel;
    }

    public String getAddressDistrict() {
        return addressDistrict;
    }

    public void setAddressDistrict(String addressDistrict) {
        this.addressDistrict = addressDistrict;
    }
}
