package com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fab.core.annotation.Remote;
import com.fab.core.exception.service.AppException;
import com.fab.core.util.EncodeSHAUtils;
import com.fab.server.util.Format;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.constant.ValidationMethodConstant;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper.IMBUserOperationMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.IMBUserOperationService;
import com.zhuanyi.vjwealth.wallet.service.outer.email.server.service.ISendEmailService;

@Service
public class MBUserOperationService implements IMBUserOperationService {

	@Autowired
	private IMBUserOperationMapper userOperationMapper;

	@Remote
	ISendEmailService sendEmailService;

	public Boolean updateUserPassword(String userId, String oldPassword, String newPassword) {
		ValidationMethodConstant.validateUpdatePassword(userId, oldPassword, newPassword);
		// 1. 查询旧密码是否正确
		String oldEncodePassword = userOperationMapper.queryPasswordByUserId(userId);
		if (StringUtils.isBlank(oldEncodePassword)) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("content", "修改密码-userId[" + userId + "]未查询到此账户");
			sendEmailService.sendAsyncEmail("SYSTEM_ERROR_WARN", paramMap);
			throw new AppException("未查询此账户");
		}
		if (!new EncodeSHAUtils().sha512Encode(oldPassword).equals(oldEncodePassword)) {
			throw new AppException("旧密码不正确");
		}
		userOperationMapper.updateModifyPassword(userId, new EncodeSHAUtils().sha512Encode(newPassword));

		return Boolean.TRUE;
	}

	
	public String getNextWorkDay(Date targetDate) {
		//	校验参数
		if(targetDate==null)
			throw new AppException("传入日期不能为空");
		
		String workDay=Format.dateToString(targetDate, "yyy-MM-dd");
		// 1. 校验下一个工作日是否存在
		if(userOperationMapper.countNextWorkDayExit(workDay)<1){
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("content", "未找到下一个工作日,传入日期为:"+workDay);
			sendEmailService.sendAsyncEmail("SYSTEM_ERROR_WARN", paramMap);
			throw new AppException("未查询到下一个工作日");
		}
		
		//	2. 返回工作日
		return userOperationMapper.queryNextWorkDay(workDay);
	}

}
