package com.zhuanyi.vjwealth.wallet.service.mainbiz.user.constant;

import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.fab.core.exception.service.AppException;
import com.fab.core.logger.BaseLogger;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBLoginChannelEnum;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBLoginUserDTO;

public class ValidationMethodConstant {

	public static void validateLoginInfo(String phone, String password) {
		validatePhone(phone);
		if (StringUtils.isBlank(password)) {
			throw new AppException("密码不能为空!");
		}
		if (password.length() < 6) {
			throw new AppException("密码不能少于6位!");
		}
	}
	
	public static void validateCheckLoginForApp(String phone, String uuid) {
		validatePhone(phone);
		if (StringUtils.isBlank(uuid)) {
			throw new AppException("uuid不能为空!");
		}
	}
	
	public static void validatePhone(String phone) {
		if (StringUtils.isBlank(phone)) {
			throw new AppException("手机号码不能为空!");
		}
		if (!Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$").matcher(phone).matches()) {
			throw new AppException("手机号码不合法!");
		}
	}
	
	
	public static void validateWXUserLogin(String phone,MBLoginChannelEnum channel) {
		validatePhone(phone);
		//	校验渠道
		if(channel==null||StringUtils.isBlank(channel.getValue())){
			BaseLogger.error("微信登录渠道为空,[phone]"+phone);
			throw new AppException("微信登录渠道不能为空");
		}
	}
	
	
	public static void validateLoginUser(MBLoginUserDTO loginUserDTO, String encodePassword) {
		if (loginUserDTO == null) {
			throw new AppException("用户未注册，请先注册!");
		}
		if (!MBLoginUserDTO.USERSTATUS_SUCCESS_00.equals(loginUserDTO.getUserStatus())) {
			throw new AppException("用户账户被锁定，请联系官方客服!");
		}
		if (MBLoginUserDTO.USERSIGN_FAIL_0.equals(loginUserDTO.getSign())) {
			throw new AppException("此账户未激活，请先激活账户!");
		}
		if (StringUtils.isBlank(loginUserDTO.getPhone()) || StringUtils.isBlank(loginUserDTO.getSign()) || StringUtils.isBlank(loginUserDTO.getUserId())) {
			throw new AppException("账户信息存在异常，请联系官方客服!");
		}
		if (!loginUserDTO.getPassword().equals(encodePassword)) {
			throw new AppException("密码错误，请重新输入!");
		}
	}

	public static void validateCode(String code) {
		if (StringUtils.isBlank(code)) {
			throw new AppException("验证码不能为空!");
		}
	}
	
	public static void validateUpdatePassword(String userId, String oldPassword, String newPassword) {
		if (StringUtils.isBlank(userId)) {
			throw new AppException("用户标示不能为空!");
		}
		if (StringUtils.isBlank(oldPassword)) {
			throw new AppException("旧密码不能为空!");
		}
		if (oldPassword.length() < 6) {
			throw new AppException("旧密码不能少于6位!");
		}
		if (StringUtils.isBlank(newPassword)) {
			throw new AppException("新密码不能为空!");
		}
		if (newPassword.length() < 6) {
			throw new AppException("新密码不能少于6位!");
		}
	}
	
	public static void validateLoginUserNoPassword(MBLoginUserDTO loginUserDTO) {
		if (loginUserDTO == null) {
			throw new AppException("用户未注册，请先注册!");
		}
		if (!MBLoginUserDTO.USERSTATUS_SUCCESS_00.equals(loginUserDTO.getUserStatus())) {
			throw new AppException("用户账户被锁定，请联系官方客服!");
		}
		if (MBLoginUserDTO.USERSIGN_FAIL_0.equals(loginUserDTO.getSign())) {
			throw new AppException("此账户未激活，请先激活账户!");
		}
		if (StringUtils.isBlank(loginUserDTO.getPhone()) || StringUtils.isBlank(loginUserDTO.getSign()) || StringUtils.isBlank(loginUserDTO.getUserId())) {
			throw new AppException("账户信息存在异常，请联系官方客服!");
		}
	}
	
}
