package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan;

import java.util.List;
import java.util.Map;

/**
 * Created by wzf on 2016/10/31.
 */
public class ImproveApplyInfoInitDTO {

    private String improveInfoTip;
    private String tipInformation;
    private String protocolName;
//    private String protocolURL;
    private String protocolURLTitle;
    private List<ImproveApplyInfoInitInnerDTO> personalInfo;
    private List<Map<String,String>> protocolURLs;

    public String getImproveInfoTip() {
        return improveInfoTip;
    }

    public void setImproveInfoTip(String improveInfoTip) {
        this.improveInfoTip = improveInfoTip;
    }

    public String getTipInformation() {
        return tipInformation;
    }

    public void setTipInformation(String tipInformation) {
        this.tipInformation = tipInformation;
    }

    public String getProtocolName() {
        return protocolName;
    }

    public void setProtocolName(String protocolName) {
        this.protocolName = protocolName;
    }

    public List<ImproveApplyInfoInitInnerDTO> getPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo(List<ImproveApplyInfoInitInnerDTO> personalInfo) {
        this.personalInfo = personalInfo;
    }

    public String getProtocolURLTitle() {
        return protocolURLTitle;
    }

    public void setProtocolURLTitle(String protocolURLTitle) {
        this.protocolURLTitle = protocolURLTitle;
    }

    public List<Map<String, String>> getProtocolURLs() {
        return protocolURLs;
    }

    public void setProtocolURLs(List<Map<String, String>> protocolURLs) {
        this.protocolURLs = protocolURLs;
    }
}
