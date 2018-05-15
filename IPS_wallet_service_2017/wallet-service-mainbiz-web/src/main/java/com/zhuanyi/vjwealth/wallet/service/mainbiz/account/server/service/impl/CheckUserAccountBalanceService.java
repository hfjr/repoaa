package com.zhuanyi.vjwealth.wallet.service.mainbiz.account.server.service.impl;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fab.core.annotation.Remote;
import com.fab.core.exception.service.AppException;
import com.fab.core.logger.BaseLogger;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.account.server.integration.mapper.ICheckUserAccountBalanceMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.account.server.service.ICheckUserAccountBalanceService;
import com.zhuanyi.vjwealth.wallet.service.outer.email.server.service.ISendSystemErrorEmailService;

@Service("checkUserAccountBalanceService")
public class CheckUserAccountBalanceService implements	ICheckUserAccountBalanceService {

	@Autowired
	private ICheckUserAccountBalanceMapper checkUserAccountBalanceMapper;
	@Remote
	private ISendSystemErrorEmailService sendBizEmailService;
	
	/**
	 * 
	 * <p> 用户在提现操作前,调用该接口<p/>
	 * 
	 * 检查用户订单流水以及资金账户余额是否匹配
	 * 
	 * @param userId
	 * @return 
	 * 资金账户匹配可以提现 ,返回true <br/>
	 * 资金账户不匹配,不能够提现,用户账户将会被锁定(不会返回false,将会直接抛出异常)
	 * 
	 * @throws Appexception <br/>
	 * 
	 * 	1.用户资金不匹配,锁定账户,抛出资金账户异常,请联系客服<br/>
	 *  2.其它任意错误,抛出系统繁忙<br/>
	 *  一切异常将会发送邮件
	 * @author xuewentao
	 * @version 1.0
	 * 
	 */
	public boolean checkUserAccountBalance(String userId) {
		BaseLogger.audit(String.format(String.format("CheckUserAccountBalanceService.checkUserAccountBalance userId[%s]调用平账开始[%s]",userId,System.currentTimeMillis())));
		//	校验参数
		this.checkParamValidate(userId);
		//	平账开关,避免发生大规模平账错误
		if("open".equals(checkUserAccountBalanceMapper.queryCalculateBalanceSwitch())){
			BaseLogger.audit(String.format(String.format("平帐功能是否打开[%s]","open")));
			// 1.账目不平,锁定账户,发送预警
			BigDecimal balance=checkUserAccountBalanceMapper.calculateUserAccountbalanceable(userId);
			if(balance.compareTo(new BigDecimal("0"))!=0){
				BaseLogger.audit(String.format(String.format("userId[%s],账户不平,不平金额为[%s]",userId,balance)));
				this.ifUserAccountNotBalance(userId);
				throw new AppException("账户异常,请联系客服");
			}
		}
		BaseLogger.audit(String.format(String.format("CheckUserAccountBalanceService.checkUserAccountBalance userId[%s]调用平账结束[%s]",userId,System.currentTimeMillis())));
		return true;
	}
	
	@Override
	public Double queryUserAccountBalance(String userId) {
		// 校验参数
		this.checkParamValidate(userId);
		return checkUserAccountBalanceMapper.calculateUserAccountbalanceable(userId).doubleValue();
	}
	
	private void checkParamValidate(String userId){
		if(StringUtils.isBlank(userId)){
			BaseLogger.audit("userId为空");
			throw new AppException("系统繁忙,请稍后再试");
		}
	}
	
	private void ifUserAccountNotBalance(String userId){
		// 1.1  冻结账户 清除 login_uuid 状态改为 20
		checkUserAccountBalanceMapper.updateUserStautsToException(userId);
		BaseLogger.audit(String.format("userId[%s],用户被冻结", userId));
		sendBizEmailService.sendSystemErrorHigh(String.format("userID[%s],平帐失败,用户被冻结,需要人工介入", userId));
	}
	
	public static void main(String[] args) {
		System.out.println(String.format(String.format("账户不平[%s],不平金额为[%s]","US22001321564",1.23)));
	}

}
