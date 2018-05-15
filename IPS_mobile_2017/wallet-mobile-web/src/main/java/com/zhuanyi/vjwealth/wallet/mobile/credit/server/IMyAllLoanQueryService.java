package com.zhuanyi.vjwealth.wallet.mobile.credit.server;


import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.easyLoan.EasyLoanSaveDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.myloan.MyLoanDetailDTO;

/**
 * 我的借款模块接口
 */
public interface IMyAllLoanQueryService {


	/**
	 *
	 * @param userId  用户编号
	 * @param borrowStatus 全部(默认)：all，还款中：repaymentIn，还款完毕：repaymentEnd，逾期：overdue
	 * @param productSearch all(默认),取productSearch中key，即贷款产品编号
	 * @param page
     * @return
     */
	Object queryLoanRecordByConditions(String userId, String borrowStatus, String productSearch, String page);

	/**
	 * 查询贷款详情
	 * @param userId
	 * @param borrowCode
	 * @param loanProductId
     * @return
     */
	MyLoanDetailDTO repaymentDetailForFund(String userId, String borrowCode, String loanProductId);

	/**
	 * 贷款还款详情
	 * @param userId
	 * @param borrowCode
     * @return
     */
	Object repaymentDetailForCana(String userId, String borrowCode,String loanProductId);
}
