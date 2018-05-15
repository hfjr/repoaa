package com.zhuanyi.vjwealth.wallet.mobile.paymentpassword.server.service.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fab.core.annotation.Remote;
import com.fab.core.exception.service.AppException;
import com.fab.core.util.EncodeSHAUtils;
import com.zhuanyi.vjwealth.wallet.mobile.paymentpassword.server.constants.PaymentPasswordResultCode;
import com.zhuanyi.vjwealth.wallet.mobile.paymentpassword.server.dto.CheckPaymentPasswordDTO;
import com.zhuanyi.vjwealth.wallet.mobile.paymentpassword.server.dto.FilterValidatorPaymentPasswrodDTO;
import com.zhuanyi.vjwealth.wallet.mobile.paymentpassword.server.dto.PaymentPasswordDTO;
import com.zhuanyi.vjwealth.wallet.mobile.paymentpassword.server.dto.PaymentPasswrodAuthorizeDTO;
import com.zhuanyi.vjwealth.wallet.mobile.paymentpassword.server.dto.ValidatorPaymentPasswrodAuthorizeDTO;
import com.zhuanyi.vjwealth.wallet.mobile.paymentpassword.server.mapper.PaymentPasswordMapper;
import com.zhuanyi.vjwealth.wallet.mobile.paymentpassword.server.service.IPaymentPasswordService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.IMBUserPaymentPasswordService;
import com.zhuanyi.vjwealth.wallet.service.outer.message.dto.MessageDTO;
import com.zhuanyi.vjwealth.wallet.service.outer.message.server.service.IPhoneMessageService;

@Service
public class PaymentPasswordService implements IPaymentPasswordService {
	
	@Autowired
	private PaymentPasswordMapper paymentPasswordMapper;
	
	@Remote   
	IMBUserPaymentPasswordService mBUserPaymentPasswordService;
	
	@Remote   
	IPhoneMessageService phoneMessageService;

	//拦截器，查询密码状态
	@Override
	public PaymentPasswordDTO queryPaymentPasswordStatus(String userId) {
		if(StringUtils.isBlank(userId)){
			return setPaymentPasswordDTO(PaymentPasswordResultCode.FILTER_PASSWORD_FAIL_OTHER,"用户ID不能为空");
		}
		//查询支付密码状态
		FilterValidatorPaymentPasswrodDTO fpd = paymentPasswordMapper.queryPaymentPasswordState(userId);
		//支付密码为空
		if(fpd == null){
			return setPaymentPasswordDTO(PaymentPasswordResultCode.FILTER_PASSWORD_FAIL_OTHER,"查询支付密码信息失败，请稍后再试");
		}
		//支付密码未开启
		if(fpd.getState().equals("close")){
			return setPaymentPasswordDTO(PaymentPasswordResultCode.FILTER_PASSWORD_FAIL_CLOSE,"支付密码未启用");
		}
		//支付密码被锁定-支付密码鉴权失败导致锁定
		if(fpd.getState().equals("lock") && fpd.getAuthorizationLeftTimes()==0){
			return setPaymentPasswordDTO(PaymentPasswordResultCode.FILTER_PASSWORD_FAIL_LOCKBy_AUTHORIZATION,"支付密码已经锁定 ，请联系客服 |  关闭   | 联系客服");
		}
		//支付密码被锁定-验证支付密码导致锁定
		if(fpd.getState().equals("lock") && fpd.getValidationLeftTimes()==0){
			return setPaymentPasswordDTO(PaymentPasswordResultCode.FILTER_PASSWORD_FAIL_LOCK_BY_CHECK,"支付密码已经锁定|  关闭  | 解锁支付密码");
		}
		//支付密码开启
		if(fpd.getState().equals("normal")){
			return setPaymentPasswordDTO(PaymentPasswordResultCode.FILTER_PASSWORD_SUCCESS,"请输入支付密码，你还有"+fpd.getValidationLeftTimes()+"次机会");
		}
		return setPaymentPasswordDTO(PaymentPasswordResultCode.FILTER_PASSWORD_FAIL_OTHER,"数据格式不合法");
	}

	//设置支付密码
	@Override
	public PaymentPasswordDTO setPaymentPassword(String userId, String password) {
		//1.校验参数
		if(StringUtils.isBlank(userId) || StringUtils.isBlank(password)){
			return setPaymentPasswordDTO(PaymentPasswordResultCode.SET_PASSWORD_FAIL,"设置支付密码失败");
		}
		
		
		password =operatePaymentPassword(password);//解密,然后再重新加密
		
		//2.保存支付密码
		int count = mBUserPaymentPasswordService.updateUserPaymentPassword(userId,password);
		if(count == 0){
			mBUserPaymentPasswordService.saveUserPaymentPassword(userId, password);
		}
		
		return setPaymentPasswordDTO(PaymentPasswordResultCode.SET_PASSWORD_SUCCESS,"设置支付密码成功");
	}

	//关闭支付密码
	@Override
	public PaymentPasswordDTO closePaymentPassword(String userId,
			String password) {
		//1.校验参数
		if(StringUtils.isBlank(userId) || StringUtils.isBlank(password)){
			return setPaymentPasswordDTO(PaymentPasswordResultCode.CLOSE_PASSWORD_FAIL,"关闭支付密码失败");
		}
		
		password =operatePaymentPassword(password);//解密,然后再重新加密
		
		//2.验证支付密码
		PaymentPasswordDTO check = checkPaymentPassword(userId,password,false);
		//2.1 如果是数据异常
		if(check == null || check.getCode().equals(PaymentPasswordResultCode.CHECK_PASSWORD_FAIL)){
			return setPaymentPasswordDTO(PaymentPasswordResultCode.CLOSE_PASSWORD_FAIL,"关闭支付密码失败");
		}
		//2.2 如果是校验不通过
		if(check.getCode().equals(PaymentPasswordResultCode.CHECK_PASSWORD_FAIL_USE_UP) || 
				check.getCode().equals(PaymentPasswordResultCode.CHECK_PASSWORD_FAIL_WRONG)){
			return check;
		}
		//2.3如果校验通过
		if(check.getCode().equals(PaymentPasswordResultCode.CHECK_PASSWORD_SUCCESS)){
			//3 关闭支付密码（物理删除数据库数据）
			mBUserPaymentPasswordService.deleteUserPaymentPassword(userId,password);
			return setPaymentPasswordDTO(PaymentPasswordResultCode.CLOSE_PASSWORD_SUCCESS,"关闭支付密码成功");
		}
		//默认返回失败结果
		return setPaymentPasswordDTO(PaymentPasswordResultCode.CLOSE_PASSWORD_FAIL,"关闭支付密码失败");
	}

	//验证支付密码
	@Override
	public PaymentPasswordDTO checkPaymentPassword(String userId,
			String password) {
		return checkPaymentPassword(userId,password,true);
	}
	
	
	//验证支付密码 (是否做解密并重新加密)
	private PaymentPasswordDTO checkPaymentPassword(String userId,
			String password,boolean ifOperatePassword) {
		//1.校验参数
		if(StringUtils.isBlank(userId) || StringUtils.isBlank(password)){
			return setPaymentPasswordDTO(PaymentPasswordResultCode.CHECK_PASSWORD_FAIL,"校验支付密码失败");
		}
		
		if(ifOperatePassword){
			password =operatePaymentPassword(password);//解密,然后再重新加密
		}
		
		//2.校验
		CheckPaymentPasswordDTO checkResult = paymentPasswordMapper.checkPaymentPassword(userId,password);
		//2.1 如果为空，则返回校验失败提示
		if(checkResult == null){
			return setPaymentPasswordDTO(PaymentPasswordResultCode.CHECK_PASSWORD_FAIL,"校验支付密码失败");
		}
		//2.2 如果校验通过，而且失败尝试次数大于0，则需要将失败次数置为0
		if(checkResult.getResult().equals("true")){
			if(checkResult.getFailures() > 0){
				mBUserPaymentPasswordService.updateUserPaymentPasswordValidationFailures(userId, "0","");
			}
			return setPaymentPasswordDTO(PaymentPasswordResultCode.CHECK_PASSWORD_SUCCESS,"验证支付密码成功");
		}
		//2.3 如果校验不通过，且不需要锁定账户时（失败次数+1小于设置的可尝试次数），则失败次数+1
		if(checkResult.getResult().equals("false") && checkResult.getLockFlag() > 0){
			mBUserPaymentPasswordService.updateUserPaymentPasswordValidationFailures(userId, String.valueOf(checkResult.getFailures()+1),"输入支付密码错误");
			return setPaymentPasswordDTO(PaymentPasswordResultCode.CHECK_PASSWORD_FAIL_WRONG,"验证支付密码失败,你还有"+checkResult.getLockFlag()+"次机会 | 返回 | 确定 ");
		}
		//2.4 如果校验不通过，且需要锁定账户时（失败次数+1等于设置的可尝试次数）
		if(checkResult.getResult().equals("false") && checkResult.getLockFlag() == 0){
			mBUserPaymentPasswordService.lockUserPaymentPasswordForCheck(userId);
			return setPaymentPasswordDTO(PaymentPasswordResultCode.CHECK_PASSWORD_FAIL_USE_UP,"您已连续"+(checkResult.getFailures()+1)+"次输错支付密码，您的账户已被锁定 | 返回 | 解锁 ");
		}
		//2.5 如果校验不通过，且已锁定账户时
		if(checkResult.getResult().equals("false") && checkResult.getLockFlag() < 0){
			return setPaymentPasswordDTO(PaymentPasswordResultCode.CHECK_PASSWORD_FAIL_USE_UP,"您已连续"+checkResult.getFailures()+"次输错支付密码，您的账户已被锁定 | 返回 | 解锁 ");
		}
		//默认返回失败结果
		return setPaymentPasswordDTO(PaymentPasswordResultCode.CHECK_PASSWORD_FAIL,"校验支付密码失败");
	}

	//重置支付密码
	@Override
	public PaymentPasswordDTO resetPaymentPasswordForRememberPassword(
			String userId, String password) {
		//1.校验参数
		if(StringUtils.isBlank(userId) || StringUtils.isBlank(password)){
			return setPaymentPasswordDTO(PaymentPasswordResultCode.RESET_PASSWORD_FAIL,"重置支付密码失败");
		}
		password =operatePaymentPassword(password);//解密,然后再重新加密
		//2.重置支付密码
		mBUserPaymentPasswordService.updateUserPaymentPassword(userId, password);
		return setPaymentPasswordDTO(PaymentPasswordResultCode.RESET_PASSWORD_SUCCESS,"重置支付密码成功");
	}

	
	//忘记支付密码
	@Override
	public PaymentPasswordDTO resetPaymentPasswordForForgetPassword(
			PaymentPasswrodAuthorizeDTO paymentPasswrodAuthorizeDTO){
		
		String userId = paymentPasswrodAuthorizeDTO.getUserId();//用户ID
		String identityNo = paymentPasswrodAuthorizeDTO.getIdentityNo();//身份证号
		String securityCardNo = paymentPasswrodAuthorizeDTO.getPaymentCardNo();//安全卡卡号
		String code = paymentPasswrodAuthorizeDTO.getCode();//验证码
		
		//1.校验参数合法性
		//1.1 参数为空
		if(StringUtils.isBlank(userId) || 
				StringUtils.isBlank(identityNo) ||
				StringUtils.isBlank(securityCardNo) ||
				StringUtils.isBlank(code)){
			return setPaymentPasswordDTO(PaymentPasswordResultCode.FORGET_PASSWORD_FAIL_OTHER,"请输入完整信息");
		}
		//1.2校验身份证的合法性
		if(!validatorIndetityNo(identityNo)){
			return setPaymentPasswordDTO(PaymentPasswordResultCode.FORGET_PASSWORD_FAIL_INDENTITY_NO_WRONG,"请输入正确身份证号");
		}
		//1.3校验银行卡号的合法性
		if(!validatorSecurityCardNo(securityCardNo)){
			return setPaymentPasswordDTO(PaymentPasswordResultCode.FORGET_PASSWORD_FAIL_CARD_NO_WRONG,"请输入正确安全卡卡号");
		}
		
		 //2.逻辑校验		
		//校验值是否匹配
		ValidatorPaymentPasswrodAuthorizeDTO validatorResult = paymentPasswordMapper.validatorPaymentPasswrodAuthorize(userId,identityNo,securityCardNo);
		//2.1 如果数据异常
		if(validatorResult == null){
			return setPaymentPasswordDTO(PaymentPasswordResultCode.FORGET_PASSWORD_FAIL_OTHER,"重置支付密码失败");
		}
		//2.2如果没有通过校验，且还可以继续尝试（即不锁定账户），则将失败次数+1
		if(validatorResult.getResult().equals("false") && validatorResult.getLockFlag() > 0){
			mBUserPaymentPasswordService.updateUserPaymentPasswordAuthorizationFailures(userId, String.valueOf(validatorResult.getFailures()+1), "输入信息与用户信息不匹配");
			return setPaymentPasswordDTO(PaymentPasswordResultCode.FORGET_PASSWORD_FAIL_WRONG,"输入信息与用户信息不匹配,你还有"+validatorResult.getLockFlag()+"次机会 | 返回 | 确定 ");
		}
		//2.3 如果校验不通过，且将锁定账户时（失败次数+1等于设置的可尝试次数）
		if(validatorResult.getResult().equals("false") && validatorResult.getLockFlag() == 0){
			mBUserPaymentPasswordService.lockUserPaymentPasswordForAuthorization(userId);
			return setPaymentPasswordDTO(PaymentPasswordResultCode.FORGET_PASSWORD_FAIL_LOCK,"支付密码已锁定,请联系客服 | 返回 | 联系客服");
		}
		//2.4 如果校验不通过，且已锁定账户时
		if(validatorResult.getResult().equals("false") && validatorResult.getLockFlag() < 0){
			return setPaymentPasswordDTO(PaymentPasswordResultCode.FORGET_PASSWORD_FAIL_LOCK,"支付密码已锁定,请联系客服| 返回 | 联系客服 ");
		}
		//2.5 如果校验通过，将鉴权校验失败次数更新为零
		if(validatorResult.getResult().equals("true")){
			//3.校验验证码
			boolean flag = validatorCode(userId,code);
			if(flag){
				//更新支付密码状态，并清零失败次数
				mBUserPaymentPasswordService.unlockUserPaymentPassword(userId);
				return setPaymentPasswordDTO(PaymentPasswordResultCode.FORGET_PASSWORD_SUCCESS,"重置支付密码成功");
			}else{
				return setPaymentPasswordDTO(PaymentPasswordResultCode.FORGET_PASSWORD_FAIL_CODE_WRONG,"验证码错误或已超时");
			}
			
		}
		
		return setPaymentPasswordDTO(PaymentPasswordResultCode.UNLOCK_PASSWORD_FAIL_OTHER,"重置支付密码失败");
	
	}

	//忘记支付密码-获取语音验证码
	@Override
	public String sendForgetToneNoticeForForgetPassword(
			PaymentPasswrodAuthorizeDTO paymentPasswrodAuthorizeDTO) {
		//1.校验身份证号和卡号
		validatorPaymentPasswrodAuthorizeForSendNotice(paymentPasswrodAuthorizeDTO);
		//2.获取验证码
		return sendToneNotice(paymentPasswrodAuthorizeDTO.getUserId());
		
	}

	//忘记支付密码-获取短信验证码
	@Override
	public String sendForgetSMSNoticeForForgetPassword(
			PaymentPasswrodAuthorizeDTO paymentPasswrodAuthorizeDTO) {
		//1.校验身份证号和卡号
		validatorPaymentPasswrodAuthorizeForSendNotice(paymentPasswrodAuthorizeDTO);
		// 获取验证码
		return sendSMSNotice(paymentPasswrodAuthorizeDTO.getUserId());
	}

	//解锁支付密码
	@Override
	public PaymentPasswordDTO unlockPaymentPassword(
			PaymentPasswrodAuthorizeDTO paymentPasswrodAuthorizeDTO) {
		
		String userId = paymentPasswrodAuthorizeDTO.getUserId();//用户ID
		String identityNo = paymentPasswrodAuthorizeDTO.getIdentityNo();//身份证号
		String securityCardNo = paymentPasswrodAuthorizeDTO.getPaymentCardNo();//安全卡卡号
		String code = paymentPasswrodAuthorizeDTO.getCode();//验证码
		
		//1.校验参数合法性
		//1.1 参数为空
		if(StringUtils.isBlank(userId) || 
				StringUtils.isBlank(identityNo) ||
				StringUtils.isBlank(securityCardNo) ||
				StringUtils.isBlank(code)){
			return setPaymentPasswordDTO(PaymentPasswordResultCode.UNLOCK_PASSWORD_FAIL_OTHER,"请输入完整信息");
		}
		//1.2校验身份证的合法性
		if(!validatorIndetityNo(identityNo)){
			return setPaymentPasswordDTO(PaymentPasswordResultCode.UNLOCK_PASSWORD_FAIL_INDENTITY_NO_WRONG,"请输入正确身份证号");
		}
		//1.3校验银行卡号的合法性
		if(!validatorSecurityCardNo(securityCardNo)){
			return setPaymentPasswordDTO(PaymentPasswordResultCode.UNLOCK_PASSWORD_FAIL_CARD_NO_WRONG,"请输入正确安全卡卡号");
		}
		
        //2.逻辑校验		
		//校验值是否匹配
		ValidatorPaymentPasswrodAuthorizeDTO validatorResult = paymentPasswordMapper.validatorPaymentPasswrodAuthorize(userId,identityNo,securityCardNo);
		//2.1 如果数据异常
		if(validatorResult == null){
			return setPaymentPasswordDTO(PaymentPasswordResultCode.UNLOCK_PASSWORD_FAIL_OTHER,"解锁支付密码失败");
		}
		//2.2如果没有通过校验，且还可以继续尝试（即不锁定账户），则将失败次数+1
		if(validatorResult.getResult().equals("false") && validatorResult.getLockFlag() > 0){
			mBUserPaymentPasswordService.updateUserPaymentPasswordAuthorizationFailures(userId, String.valueOf(validatorResult.getFailures()+1), "输入信息与用户信息不匹配");
			return setPaymentPasswordDTO(PaymentPasswordResultCode.UNLOCK_PASSWORD_FAIL_WRONG,"输入信息与用户信息不匹配,你还有"+validatorResult.getLockFlag()+"次机会 | 返回 | 确定 ");
		}
		//2.3 如果校验不通过，且将锁定账户时（失败次数+1等于设置的可尝试次数）
		if(validatorResult.getResult().equals("false") && validatorResult.getLockFlag() == 0){
			mBUserPaymentPasswordService.lockUserPaymentPasswordForAuthorization(userId);
			return setPaymentPasswordDTO(PaymentPasswordResultCode.UNLOCK_PASSWORD_FAIL_LOCK,"支付密码已锁定,请联系客服| 返回 | 联系客服 ");
		}
		//2.4 如果校验不通过，且已锁定账户时
		if(validatorResult.getResult().equals("false") && validatorResult.getLockFlag() < 0){
			return setPaymentPasswordDTO(PaymentPasswordResultCode.UNLOCK_PASSWORD_FAIL_LOCK,"支付密码已锁定,请联系客服 | 返回 | 联系客服");
		}
		//2.5 如果校验通过，将鉴权校验失败次数更新为零
		if(validatorResult.getResult().equals("true")){
			//3.校验验证码
			boolean flag = validatorCode(userId,code);
			if(flag){
				//更新支付密码状态，并清零失败次数
				mBUserPaymentPasswordService.unlockUserPaymentPassword(paymentPasswrodAuthorizeDTO.getUserId());
				return setPaymentPasswordDTO(PaymentPasswordResultCode.UNLOCK_PASSWORD_SUCCESS,"解锁支付密码成功");
			}else{
				return setPaymentPasswordDTO(PaymentPasswordResultCode.UNLOCK_PASSWORD_FAIL_CODE_WRONG,"验证码错误或已超时");
			}
			
		}
		
		return setPaymentPasswordDTO(PaymentPasswordResultCode.UNLOCK_PASSWORD_FAIL_OTHER,"解锁支付密码失败");
		
	}

	
	//解锁支付密码-获取短信验证码
	@Override
	public String sendForgetSMSNoticeForUnlockPaymentPassword(
			PaymentPasswrodAuthorizeDTO paymentPasswrodAuthorizeDTO) {
		//1.校验身份证号和卡号
		validatorPaymentPasswrodAuthorizeForSendNotice(paymentPasswrodAuthorizeDTO);
		//2.获取验证码
		return sendSMSNotice(paymentPasswrodAuthorizeDTO.getUserId());
	}

	//解锁支付密码-获取语音验证码
	@Override
	public String sendForgetToneNoticeForUnlockPaymentPassword(
			PaymentPasswrodAuthorizeDTO paymentPasswrodAuthorizeDTO) {
		//1.校验身份证号和卡号
		validatorPaymentPasswrodAuthorizeForSendNotice(paymentPasswrodAuthorizeDTO);
		// 获取验证码
		return sendToneNotice(paymentPasswrodAuthorizeDTO.getUserId());
	}

	//获取手机号
	@Override
	public String resetPaymentPasswordForForgetPasswordIniti(String userId) {
		String phone = paymentPasswordMapper.queryUserPhoneByUserId(userId);
		return phone.substring(0,3)+"****"+phone.substring(7,11);
	}

	//是否开启支付密码
	@Override
	public String paymentPasswordIniti(String userId) {
		return paymentPasswordMapper.queryUserPaymentPasswordSwitch(userId);
	}

	
	//人工解锁支付密码
	@Override
	public PaymentPasswordDTO unlockInitializationForArtificial(String userId) {
		if(StringUtils.isBlank(userId)){
			return setPaymentPasswordDTO(PaymentPasswordResultCode.CS_UNLOCK_PASSWORD_FAIL,"userId不能为空"); 
		}
		//更新支付密码状态，并清零失败次数
		int count = mBUserPaymentPasswordService.unlockUserPaymentPassword(userId);
		if(count >0 ){
			return setPaymentPasswordDTO(PaymentPasswordResultCode.CS_UNLOCK_PASSWORD_SUCCESS,"人工解锁支付密码成功"); 
		}else{
			return setPaymentPasswordDTO(PaymentPasswordResultCode.CS_UNLOCK_PASSWORD_FAIL,"人工解锁支付密码失败"); 
		}
	}
	
	//初始化返回结果
	private PaymentPasswordDTO setPaymentPasswordDTO(String code,String message){
		return new PaymentPasswordDTO(code,message);
	}
	
	//获取验证码时的校验
	private void validatorPaymentPasswrodAuthorizeForSendNotice(PaymentPasswrodAuthorizeDTO dto){
		//1.校验参数是否为空
		if(StringUtils.isBlank(dto.getUserId()) || 
				StringUtils.isBlank(dto.getIdentityNo()) ||
				StringUtils.isBlank(dto.getPaymentCardNo())){
			throw new AppException("请输入完整信息");
		}
		//2.校验身份证的合法性
		if(!validatorIndetityNo(dto.getIdentityNo())){
			throw new AppException("请输入正确身份证号");
		}
		//3.校验银行卡号的合法性
		if(!validatorSecurityCardNo(dto.getPaymentCardNo())){
			throw new AppException("请输入正确安全卡卡号");
		}
		//4.校验值是否匹配
		ValidatorPaymentPasswrodAuthorizeDTO validatorResult = paymentPasswordMapper.validatorPaymentPasswrodAuthorize(dto.getUserId(),dto.getIdentityNo(),dto.getPaymentCardNo());
		//4.1 如果数据异常
		if(validatorResult == null){
			throw new AppException("用户信息异常");
		}
		//4.2鉴权失败
		if(validatorResult.getResult().equals("false") && validatorResult.getLockFlag() >= 0){
			throw new AppException("输入信息与用户信息不匹配");
		}
		//4.3账户锁定
		if(validatorResult.getResult().equals("false") && validatorResult.getLockFlag() < 0){
			throw new AppException("支付密码已锁定,请联系客服");
		}
	}
	
	//校验身份证号合法性
    private boolean validatorIndetityNo(String indetityNo){
    	Pattern p = Pattern.compile("^(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[Xx])$)$");
    	Matcher mh = p.matcher(indetityNo);
    	return mh.matches();
    }
    
    //校验安全卡卡号合法性:16或19位，且为数字
    private boolean validatorSecurityCardNo(String securityCardNo){
    	Pattern p = Pattern.compile("^[0-9]{16,19}$");
    	Matcher mh = p.matcher(securityCardNo);
    	return mh.matches();
    }
    
    //获取短信验证码
    private String sendSMSNotice(String userId){
		String phone = paymentPasswordMapper.queryUserPhoneByUserId(userId);
    	MessageDTO md = phoneMessageService.sendTextMessage(phone, "user_reset_password");
    	if (!md.getSendFlag()) {
			throw new AppException(md.getSendFlagMessage());
		}
    	return md.getVaildeTime();
    }
    
    //获取语音验证码
    private String sendToneNotice(String userId){
    	String phone = paymentPasswordMapper.queryUserPhoneByUserId(userId);
    	MessageDTO md = phoneMessageService.sendToneMessage(phone, "user_reset_password");
    	if (!md.getSendFlag()) {
			throw new AppException(md.getSendFlagMessage());
		}
    	return md.getVaildeTime();
    }
    
    //校验验证码
    private boolean validatorCode(String userId,String code){
    	String phone = paymentPasswordMapper.queryUserPhoneByUserId(userId);
    	MessageDTO message = phoneMessageService.checkValidationCode(phone, code, "user_reset_password");
		if (!message.getSendFlag()) {
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
    }
    
    private String operatePaymentPassword(String password){
    	//TODO 1.解密（确定是手机端访问，而非其他途径访问）
    	//TODO 2.加密（重新加密保存到数据库）
    	return encrypt(password);
    }
    
    
    private String encrypt(String password){
    	return new EncodeSHAUtils().sha512Encode(password);
    }
    
}
