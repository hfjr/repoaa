package com.zhuanyi.vjwealth.wallet.service.mainbiz.account.server.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fab.core.exception.service.AppException;
import com.fab.core.logger.BaseLogger;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.account.server.ennum.LoanTypeEnum;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.account.server.integration.mapper.ILoanUserBorrowOperationMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.account.server.service.ILoanUserBorrowAccountOperationService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.common.server.service.ISequenceService;

@Service("loanUserBorrowAccountOperationService")
public class LoanUserBorrowAccountOperationService implements ILoanUserBorrowAccountOperationService {
	@Autowired
	private ILoanUserBorrowOperationMapper loanUserBorrowOperationMapper;
	@Autowired
	private ISequenceService sequenceService;
	@Transactional
	public void borrowMoneyOperationForCana(String relOrderNo,  String userId, String borrowMoney) {
		//TODO 校验参数
		/***
		 * 	 apply_cana_loan    cana-申请贷款
	 	 *	 apply_cana_loan_confirm    cana-申请贷款确认
		 */
		if(loanUserBorrowOperationMapper.countUserLoanOrderExitsForCana(relOrderNo)>0){
			//TODO ..借款订单已存在
//			throw new AppException("借款订单已存在[relOrderNo]"+relOrderNo);
			BaseLogger.audit("借款订单已存在[relOrderNo]"+relOrderNo);
			return ;
		}
		// 下借款订单
		String orderId=getId("OR", ISequenceService.SEQ_NAME_ORDER_SEQ);
		loanUserBorrowOperationMapper.applyLoanOrder(userId, orderId, borrowMoney,relOrderNo);
		//	更新ln余额(注意为负数,在sql中修改.)
		loanUserBorrowOperationMapper.updateLoanAccount(userId, borrowMoney);
		
		
	}

	@Transactional
	public void interestsMoneyOperation(String userId, String loanId, String interestId, String interestDate, String receivableInterestFee, String otherFee, LoanTypeEnum loanType) {
		//TODO 参数校验
		if(loanUserBorrowOperationMapper.countUserLoanInterestExits(interestId)>0){
			throw new AppException("利息已存在[interestId]"+interestId);
		}
		//	下利息订单..
		loanUserBorrowOperationMapper.saveLoanUserInterest(userId, loanId, interestId, receivableInterestFee, otherFee, interestDate,LoanTypeEnum.getLoanChannelTypeEnum(loanType));
		//	更新ln余额(注意为负数)
		loanUserBorrowOperationMapper.updateLoanAccount(userId,new BigDecimal(receivableInterestFee).add(new BigDecimal(otherFee)).toEngineeringString());
		
	}
	
	public String getId(String prefix, String sequenceName) {
		return prefix + new SimpleDateFormat("yyyyMMdd").format(new Date()) + sequenceService.getNextStringValue(sequenceName, 10);
	}
}
