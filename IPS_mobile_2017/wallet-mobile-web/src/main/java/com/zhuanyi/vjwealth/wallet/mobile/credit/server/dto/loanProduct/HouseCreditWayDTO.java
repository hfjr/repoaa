package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.loanProduct;

import java.util.ArrayList;
import java.util.List;

/**
 * 房产征信方式DTO
 * Created by hexy on 16/8/25.
 */
public class HouseCreditWayDTO extends  BaseCreditWayDTO {

    private String otherTip;
    private String tipIcon;

    public String getOtherTip() {
        return otherTip;
    }

    public void setOtherTip(String otherTip) {
        this.otherTip = otherTip;
    }

    public String getTipIcon() {
        return tipIcon;
    }

    public void setTipIcon(String tipIcon) {
        this.tipIcon = tipIcon;
    }
}
