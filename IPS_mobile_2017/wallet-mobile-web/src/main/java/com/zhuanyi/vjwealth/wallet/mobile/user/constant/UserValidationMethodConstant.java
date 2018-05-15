package com.zhuanyi.vjwealth.wallet.mobile.user.constant;

import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.fab.core.exception.service.AppException;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBLoginUserDTO;

public class UserValidationMethodConstant {
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
}
