package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.loanProduct;

import java.io.Serializable;

/**
 * Created by hexy on 16/8/26.
 */
public class InverstOrder implements Serializable {

    private String orderId;//20160116880001"
    private String guaranteedAmountDesc;//100,000元(可担保额度)",
    private String desc;//到期时间：2016-11-15  待收本金: 1,100 元"

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getGuaranteedAmountDesc() {
        return guaranteedAmountDesc;
    }

    public void setGuaranteedAmountDesc(String guaranteedAmountDesc) {
        this.guaranteedAmountDesc = guaranteedAmountDesc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
