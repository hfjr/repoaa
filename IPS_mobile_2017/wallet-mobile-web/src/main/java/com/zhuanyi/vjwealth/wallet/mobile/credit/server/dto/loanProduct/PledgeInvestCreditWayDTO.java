package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.loanProduct;

import java.util.List;

/**
 * 理财资产征信方式DTO
 * Created by hexy on 16/8/25.
 */
public class PledgeInvestCreditWayDTO extends  BaseCreditWayDTO {

    private  String  canBorrowAmount;//15890.00",
    private  String  inverstOrderIntroduceDesc;//请选择一个在投理财产品",
    private  String  inverstOrderTotalDesc;//共(2)笔可用作担保",
    private List<InverstOrder> inverstOrders;

    public String getCanBorrowAmount() {
        return canBorrowAmount;
    }

    public void setCanBorrowAmount(String canBorrowAmount) {
        this.canBorrowAmount = canBorrowAmount;
    }

    public String getInverstOrderIntroduceDesc() {
        return inverstOrderIntroduceDesc;
    }

    public void setInverstOrderIntroduceDesc(String inverstOrderIntroduceDesc) {
        this.inverstOrderIntroduceDesc = inverstOrderIntroduceDesc;
    }

    public String getInverstOrderTotalDesc() {
        return inverstOrderTotalDesc;
    }

    public void setInverstOrderTotalDesc(String inverstOrderTotalDesc) {
        this.inverstOrderTotalDesc = inverstOrderTotalDesc;
    }

    public List<InverstOrder> getInverstOrders() {
        return inverstOrders;
    }

    public void setInverstOrders(List<InverstOrder> inverstOrders) {
        this.inverstOrders = inverstOrders;
    }
}
