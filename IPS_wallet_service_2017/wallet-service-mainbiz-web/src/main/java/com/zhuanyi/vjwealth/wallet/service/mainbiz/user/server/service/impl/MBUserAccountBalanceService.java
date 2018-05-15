package com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fab.core.annotation.Remote;
import com.fab.core.exception.service.AppException;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper.IMBUserAccountBalanceMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.IMBUserAccountBalanceService;
import com.zhuanyi.vjwealth.wallet.service.outer.email.server.service.ISendEmailService;

@Service
public class MBUserAccountBalanceService implements IMBUserAccountBalanceService {
	
	@Autowired
	private IMBUserAccountBalanceMapper mbAccountBalanceMapper;
	@Remote
	private ISendEmailService sendEmailService;
	
	
	@Override
	public Boolean checkUserAccountbalanceable(String userId) {
		//	1. 平帐查询
		BigDecimal balance=this.calculateUserAccountbalanceable(userId);
		
		//	1.1  账目不平
		if(balance.compareTo(new BigDecimal("0"))!=0){
			//发送邮件
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("content", this.getClass()+"[checkUserAccountbalanceable]用户ID["+userId+"]"+"<br> 账户金额不平,值为["+balance.doubleValue()+"],用户已锁定");
			sendEmailService.sendAsyncEmail("SYSTEM_ERROR_WARN", paramMap);
			//锁定账户
			mbAccountBalanceMapper.updateUserAccountLock(userId);
			throw new AppException("账户异常,已冻结,请联系客服人员");
		}
		return Boolean.TRUE;
	}
	
	
	@Override
	public BigDecimal calculateUserAccountbalanceable(String userId) {
		//	校验用户
		this.checkUserValidate(userId);
		
		//	返回平账结果
		return mbAccountBalanceMapper.queryUserAccountBalance(userId);
	}
	
	
	private void checkUserValidate(String userId){
		
		if(StringUtils.isBlank(userId))
			throw new AppException("用户id不能为空");
		
		if(mbAccountBalanceMapper.countUserExits(userId)<1){
			//用户不存在,发送邮件
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("content", this.getClass()+"[checkUserValidate]/n用户ID["+userId+"]不存在");
			sendEmailService.sendAsyncEmail("SYSTEM_ERROR_WARN", paramMap);
			throw new AppException("用户不存在");
		}
		
		// 检查用户是否上锁
//		if(mbAccountBalanceMapper.countUserTransLock(userId)>0)
//			throw new AppException("用户已锁定,请先解锁");
			
	}

	
}
