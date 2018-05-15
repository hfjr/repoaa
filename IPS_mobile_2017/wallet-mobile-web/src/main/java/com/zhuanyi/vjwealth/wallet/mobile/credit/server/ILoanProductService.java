package com.zhuanyi.vjwealth.wallet.mobile.credit.server;

import com.zhuanyi.vjwealth.loan.order.vo.PersonalInformationVo;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan.FundLoanCityListDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.loanProduct.CreditInvestigationWayDTO;

import javax.servlet.http.HttpServletRequest;

/**
 * 贷款产品筛选
 * Created by wangzf on 16/10/14.
 */
public interface ILoanProductService {

    /**
     * 筛选符合要求的贷款产品
     * @param userId
     * @return
     * @since 4.0
     */
    CreditInvestigationWayDTO queryMatchedProductList(String userId,String loanMinAmount,String loanMaxAmount);


    /**
     * 查询城市列表
     */
    FundLoanCityListDTO queryLoanProductCityList(String productId);

}
