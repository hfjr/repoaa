package com.zhuanyi.vjwealth.wallet.mobile.user.server.service;

import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.HouseFundLoanApplyInfoDTO;

/**
 * Created by csy on 2016/11/2.
 */
public interface IFriendHelpService {
    public Object queryShareQRCodePic(String userId, String type);

    public Object queryWeiXinShareInfo(String userId, String type);

    public Object queryHouseFundCityLoginInfo(String cityCode);

    Object applyHouseFundLoanCreditLimit(HouseFundLoanApplyInfoDTO dto);

    Object queryMyCommission(String userId);
}
