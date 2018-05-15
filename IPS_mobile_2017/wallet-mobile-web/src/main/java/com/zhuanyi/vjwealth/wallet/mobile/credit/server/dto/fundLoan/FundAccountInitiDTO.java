package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan;

import java.util.List;
import java.util.Map;

/**
 * Created by wzf on 2016/10/16.
 */
public class FundAccountInitiDTO {

    private String fundAccountQueryTitle;
    private String cityLabel;
    private Map<String,String> defaultCity;
    private String loginTypeLabel;
    private FundAccountInitiTypeSelectDTO defaultLoginType;
    private List<FundAccountInitiTypeSelectDTO> loginTypeSelections;
//    private String fundAccount;
//    private String fundAccountPassword;
    private String fundAccountOpenHelpLabel;
    private String fundAccountOpenHelpMessage;

    public String getFundAccountQueryTitle() {
        return fundAccountQueryTitle;
    }

    public void setFundAccountQueryTitle(String fundAccountQueryTitle) {
        this.fundAccountQueryTitle = fundAccountQueryTitle;
    }

    public String getCityLabel() {
        return cityLabel;
    }

    public void setCityLabel(String cityLabel) {
        this.cityLabel = cityLabel;
    }

    public Map<String, String> getDefaultCity() {
        return defaultCity;
    }

    public void setDefaultCity(Map<String, String> defaultCity) {
        this.defaultCity = defaultCity;
    }

    public String getLoginTypeLabel() {
        return loginTypeLabel;
    }

    public void setLoginTypeLabel(String loginTypeLabel) {
        this.loginTypeLabel = loginTypeLabel;
    }

    public List<FundAccountInitiTypeSelectDTO> getLoginTypeSelections() {
        return loginTypeSelections;
    }

    public void setLoginTypeSelections(List<FundAccountInitiTypeSelectDTO> loginTypeSelections) {
        this.loginTypeSelections = loginTypeSelections;
    }

    public String getFundAccountOpenHelpLabel() {
        return fundAccountOpenHelpLabel;
    }

    public void setFundAccountOpenHelpLabel(String fundAccountOpenHelpLabel) {
        this.fundAccountOpenHelpLabel = fundAccountOpenHelpLabel;
    }

    public String getFundAccountOpenHelpMessage() {
        return fundAccountOpenHelpMessage;
    }

    public void setFundAccountOpenHelpMessage(String fundAccountOpenHelpMessage) {
        this.fundAccountOpenHelpMessage = fundAccountOpenHelpMessage;
    }

    public FundAccountInitiTypeSelectDTO getDefaultLoginType() {
        return defaultLoginType;
    }

    public void setDefaultLoginType(FundAccountInitiTypeSelectDTO defaultLoginType) {
        this.defaultLoginType = defaultLoginType;
    }
}
