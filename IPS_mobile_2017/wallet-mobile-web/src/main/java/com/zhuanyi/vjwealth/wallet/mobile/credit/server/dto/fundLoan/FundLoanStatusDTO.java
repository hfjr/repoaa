package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan;

import java.util.List;

/**
 * Created by wzf on 2016/10/18.
 */
public class FundLoanStatusDTO {

    private String tipInformation;
    private String protocolName;
    private String protocolURL;
    private List<FundLoanStatusListDTO> FormList;

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

    public String getProtocolURL() {
        return protocolURL;
    }

    public void setProtocolURL(String protocolURL) {
        this.protocolURL = protocolURL;
    }

    public List<FundLoanStatusListDTO> getFormList() {
        return FormList;
    }

    public void setFormList(List<FundLoanStatusListDTO> formList) {
        FormList = formList;
    }
}
