package com.zhuanyi.vjwealth.wallet.service.mainbiz.account.server.integration.mapper;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;

@Mapper
public interface ILoanUserBorrowOperationMapper {
	
	//是否已有借款下单记录
	public Integer countUserLoanOrderExitsForCana(@Param("loanId")String loanId);
	
	//Cana借款操作
	public void applyLoanOrder(@Param("userId")String userId,@Param("orderId")String orderId,@Param("borrowAmount")String borrowAmount,@Param("relOrderNo")String relOrderNo);
	
	//是否已有利息记录
	public int countUserLoanInterestExits(@Param("interestId")String interestId);
	
	//记录利息操作
	public void saveLoanUserInterest(@Param("userId")String userId,@Param("loanId")String loanId,@Param("interestId")String interestId,@Param("receivableInterestFee")String receivableInterestFee,
									 @Param("otherFee")String otherFee,@Param("interestDate")String interestDate,@Param("loanType")String loanType);
	
	//更新借款账户操作
	public void updateLoanAccount(@Param("userId")String userId,@Param("amount")String amount);
	
	
}
