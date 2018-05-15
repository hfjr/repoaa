package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.pledgeInvest;

/**
 * Created by hexy on 16/7/23.
 */
public class PledegInvestCalcBorrowMaxDayDTO {

    private String  canBorrowMaxDay ;// 可借最大天数
    private String  canBorrowMaxDayTip ;// 可借最大天数Tip

    public String getCanBorrowMaxDay() {
        return canBorrowMaxDay;
    }

    public void setCanBorrowMaxDay(String canBorrowMaxDay) {
        this.canBorrowMaxDay = canBorrowMaxDay;
    }

    public String getCanBorrowMaxDayTip() {
        return canBorrowMaxDayTip;
    }

    public void setCanBorrowMaxDayTip(String canBorrowMaxDayTip) {
        this.canBorrowMaxDayTip = canBorrowMaxDayTip;
    }
}
