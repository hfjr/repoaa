package com.zhuanyi.vjwealth.wallet.mobile.credit.server;



/**
 * 流通宝Service
 * Created by hexy on 16/7/12.
 */
public interface IPledgeInvestService {
	
	 /**
     * 1.流通宝首页初始化接口
     *
     * @param userId
     * @param page
     * @return
     * @since 3.5
     */
    public Object pledgeInvestIniti(String userId,String loanProductId,String page) ;
	
	
	 /**
     * 2.流通宝借款初始化接口
     * @param userId
     * @param orderId
     * @return
     * @since 3.5
     */
    public Object pledgeInvestBorrowIniti(String userId,String loanProductId,String orderId) ;
	
	/**
     * 2.1 流通宝借款申请－利息计算接口
     *
     * @param userId
     * @param loanProductId
     * @param borrowAmount
     * @param borrowDay
     * @return
     * @since 3.5
     */
   
    public Object dynamicallyGeneratedInterest(String loanProductId,String borrowAmount,String borrowDay) ;

    /**
     * 2.1 流通宝借款申请－天数计算接口
     *
     * @param userId
     * @param loanProductId
     * @param borrowAmount
     * @return
     * @since 3.5
     */

    public Object dynamicallyGeneratedBorrowDay(String userId,String orderId,String loanProductId,String borrowAmount) ;
	

    /**
     * 5.1 获取文字验证码
     *
     * @param userId
     * @return
     * @since 3.5
     */
    public String informationConfirmationSendSMSNotice(String userId) ;
	
	 /**
     * 5.2 获取语音验证码
     *
     * @param userId
     * @param phone
     * @return
     * @since 3.5
     */
    public String informationConfirmationSendToneNotice(String userId) ;
	
	 /**
     * 6.流通宝借款申请SMS确认（下单）接口
     *
     * @param userId
     * @param code
     * @param orderId
     * @param borrowAmount
     * @param borrowDay
     * @return
     * @since 3.5
     */
   
    public Object applySMSVerificationConfirm(String userId,String code,
                                              String orderId,String loanProductId,String borrowAmount,String borrowDay) ;
    
	 /**
     * 7.还款初始化
     * @param userId
     * @return
     */
  
    public Object repaymentIniti(String userId) ;
	
	 /**
     * 8.到期还款初始化
     * @param userId
     * @param page
     * @return
     */
    public Object dueRepaymentIniti(String userId,String page) ;
	
	/**
     * 9.到期还款详情
     * @param userId
     * @param loanCode
     * @return
     */
    public Object dueRepaymentDetail(String userId,String loanCode) ;
	
	 /**
     * 12.提前还款初始化
     * @param userId
     * @return
     */
    public Object earlyRepaymentIniti(String userId,String page);
	
	 /**
     * 13.提前还款明细
     * @param userId
     * @param loanCodes
     * @param repaymentMoney
     * @return
     */
    public Object earlyRepaymentDetail(String userId,String loanCodes,String repaymentMoney);
	
    
	/**
     * 14.提前还款确认
     * @param userId
     * @param loanCode
     * @param repaymentMoney
     * @return
     */
    
    
    public Object earlyRepaymentConfirmIniti(String userId,String loanCodes,String repaymentMoney) ;
	
	 /**
     * 15.还款确认
     * @param userId
     * @param loanCode
     * @param repaymentMoney
     * @return
     */
    public Object earlyRepaymentConfirm(String userId,String loanCodes,String principal,String repaymentMoney,String repaymentType,String repaymentWay) ;
	
    
	 /**
     * 16.流通宝借款记录列表接口
     * @param userId
     * @param loanProductId
     * @param page
     * @return
     * 
     */
    public Object queryLoanRecordList(String userId,String page) ;
	
    
	/**
     * 17.流通宝借款记录列表接口
     * @param userId
     * @param loanCode
     * @return
     */
    public Object queryLoanRecordDetail(String userId,String loanCode);
	
    
	/**
     * 18.流通宝借款记录列表接口
     * @param userId
     * @param loanCode
     * @param page
     * @return
     */
    public Object queryrepayRecordList(String userId,String loanCode,String page);

    /**
     * 升级接口-
     * @param userId
     * @param loanProductId
     * @param orderId
     * @return
     */
    Object pledgeInvestBorrowInitiV2(String userId, String loanProductId, String orderId);
}
