package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.loanProduct;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hexy on 16/8/26.
 */
public class CreditInvestigationWayDTO implements Serializable {
    private  String  creditWayIntroduceDesc;
    private List<BaseCreditWayDTO> creditWays = new ArrayList<BaseCreditWayDTO>();
    private String noMatchProductTip;

    public String getCreditWayIntroduceDesc() {
        return creditWayIntroduceDesc;
    }

    public void setCreditWayIntroduceDesc(String creditWayIntroduceDesc) {
        this.creditWayIntroduceDesc = creditWayIntroduceDesc;
    }

    public List<BaseCreditWayDTO> getCreditWays() {
        return creditWays;
    }

    public void setCreditWays(List<BaseCreditWayDTO> creditWays) {
        this.creditWays = creditWays;
    }

    public String getNoMatchProductTip() {
        return noMatchProductTip;
    }

    public void setNoMatchProductTip(String noMatchProductTip) {
        this.noMatchProductTip = noMatchProductTip;
    }
}
