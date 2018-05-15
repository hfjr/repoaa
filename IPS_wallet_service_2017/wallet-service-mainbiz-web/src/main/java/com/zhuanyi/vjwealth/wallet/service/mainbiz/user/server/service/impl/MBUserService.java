package com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.fab.core.annotation.Remote;
import com.fab.core.exception.service.AppException;
import com.fab.core.logger.BaseLogger;
import com.fab.core.util.EncodeSHAUtils;
import com.fab.core.util.PKUtils;
import com.zhuanyi.vjwealth.wallet.service.coupon.server.service.IUserCouponService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.common.server.service.ISequenceService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.dto.BindingCardDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.facade.IWithholdServiceFacade;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.constant.ValidationMethodConstant;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBLoginChannelEnum;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBLoginUserDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBUserDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.OutSupportResultDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.SHQBUserInfoDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.UserTradeAccountDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper.IMBUserBatchOperateMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper.IMBUserInviteMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper.IMBUserMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper.ISHQBUserMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.IMBUserService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.util.CheckIdCardUtils;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.util.JedisFactory;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.yingzt.utils.yingzt.ShareCodeUtil;
import com.zhuanyi.vjwealth.wallet.service.outer.email.server.service.ISendEmailService;
import com.zhuanyi.vjwealth.wallet.service.outer.message.dto.BusinessMessageTypeEnum;
import com.zhuanyi.vjwealth.wallet.service.outer.message.dto.MessageDTO;
import com.zhuanyi.vjwealth.wallet.service.outer.message.server.service.IPhoneMessageService;

@Service
public class MBUserService implements IMBUserService {

    @Autowired
    private IMBUserMapper userMapper;

    @Autowired
    private IMBUserBatchOperateMapper batchUserMapper;

    @Autowired
    private ISHQBUserMapper shqbUserMapper;

    @Autowired
    private ISequenceService sequenceService;

    @Autowired
    private JedisFactory jedisFactory;

    @Remote
    IPhoneMessageService phoneMessageService;

    @Remote
    ISendEmailService sendEmailService;

    @Autowired
    IUserCouponService userCouponService;

    @Autowired
    private IMBUserInviteMapper mbUserInviteMapper;

    @Value("${app.login.uuid.prefix}")
    private String appLoginPrefixKey;

    @Value("${app.login.uuid.timeout}")
    private int appLoginUUIDTimeOut;

    @Value("${h5.inviteRegisterUrl}")
    private String inviteRegisterUrl;

    @Autowired
    IMBUserInviteMapper userInviteMapper;
	@Autowired
	private IWithholdServiceFacade withholdServiceFacade;



    public MBLoginUserDTO loginForApp(String phone, String password) {
        // 1. 校验
        ValidationMethodConstant.validateLoginInfo(phone, password);
        // 2. 登录
        MBLoginUserDTO loginUserDTO = userMapper.loginForApp(phone);
        // 3. 检查账户信息
        String encodePassword = new EncodeSHAUtils().sha512Encode(password);
        ValidationMethodConstant.validateLoginUser(loginUserDTO, encodePassword);

        loginUserDTO.setPassword(null);
        String uuid = PKUtils.getPKID("UU");
        userMapper.updateLoginUUID(phone, encodePassword, uuid);
        loginUserDTO.setUuid(uuid);

//        try {
//            jedisFactory.set(appLoginPrefixKey + phone, uuid,
//                    appLoginUUIDTimeOut);
//        } catch (Exception ex) {
//            BaseLogger.warn("redis服务异常：" + ex);
//        }

        //添加返回字段：邀请码
        try {
            //从邀请表里查询
            Map userInviteInfo = userMapper.queryUserInviteInfoByUserId(loginUserDTO.getUserId());
            String inviteCode = null;
            if (userInviteInfo == null) {//不存在则创建用户邀请信息，避免用户登录时未进入我的邀请页面，导致信息不完整
                inviteCode = ShareCodeUtil.toSerialCode(Long.parseLong(phone));
                userMapper.createUserInviteInfo(loginUserDTO.getUserId(), inviteCode, inviteRegisterUrl + "?inviteCode=" + inviteCode);
            } else if (userInviteInfo.get("inviteCode") == null) {//存在，但邀请码为空(这种情况不应该出现，除非用户手动插入数据导致)
                //更新邀请码
                inviteCode = ShareCodeUtil.toSerialCode(Long.parseLong(phone));
                userMapper.updateUserInviteInfo(loginUserDTO.getUserId(), inviteCode);
            }
            loginUserDTO.setInviteCode(!StringUtils.isEmpty(inviteCode) ? inviteCode : userInviteInfo.get("inviteCode") + "");
        } catch (Exception e) {
            BaseLogger.error(e.getMessage());
            BaseLogger.error(String.format("生成邀请码错误,手机号：%s", phone));
        }

        return loginUserDTO;
    }

    @Override
    public MBLoginUserDTO loginForWXByPassword(String phone, String password, MBLoginChannelEnum channelEnum) {
        // 1. 校验
        ValidationMethodConstant.validateLoginInfo(phone, password);
        // 2. 登录
        MBLoginUserDTO loginUserDTO = userMapper.loginForApp(phone);
        // 3. 检查账户信息
        String encodePassword = new EncodeSHAUtils().sha512Encode(password);
        ValidationMethodConstant.validateLoginUser(loginUserDTO, encodePassword);

        loginUserDTO.setPassword(null);
        String uuid = PKUtils.getPKID("UU");
        userMapper.updateLoginWXUUIDByPassword(phone, uuid, channelEnum.getValue(), encodePassword);
        loginUserDTO.setUuid(uuid);

        return loginUserDTO;
    }


    @Transactional
    public Boolean register(String phone, String password, String code) {
        this.checkPhoneIsRegister(phone, password);
        if (this.checkRegisterCode(phone, code, "user_regist")) {
            try {
                // 创建用户信息
                String userId = getUserNoId(ISequenceService.SEQ_NAME_USER_ID_SEQ);
                userMapper.createUserInfo(userId, phone, new EncodeSHAUtils().sha512Encode(password));
                //	创建各种账户
                for (UserTradeAccountDTO userTradeAccountDTO : queryAccountList()) {
                    userMapper.createUserAccountInfo(userId, userTradeAccountDTO.getAccountId(), userTradeAccountDTO.getAccountType());
                }
                userMapper.addUserTransLockInfo(userId);
                //用户加载到VJ的渠道下
                batchUserMapper.saveChannelForWallet();

                userCouponService.distributeRegisterCashCoupon(userId);
            } catch (Exception e) {
                BaseLogger.error(e);
                // 发送短信
                Map<String, Object> paramMap = new HashMap<String, Object>();
                paramMap.put("phone", phone);
                sendEmailService.sendNormalEmail("SYSTEM_ERROR_0007", paramMap);
                throw new AppException("创建用户失败，请稍后再试");
            }

        }

        return Boolean.TRUE;
    }


    /**
     * 创建新用户，包含渠道
     * @param phone
     * @param password
     * @param channelType
     * @return
     */
    @Transactional
    public OutSupportResultDTO registerWithoutCode(String phone, String password,String channelType) {
        //注册
        try {
            // 生成用户编号
            String userId = getUserNoId(ISequenceService.SEQ_NAME_USER_ID_SEQ);

            //创建用户(贷渠道)
            userMapper.createUserInfoByChannel(userId, phone, new EncodeSHAUtils().sha512Encode(password),channelType );

            //创建各种账户
            for (UserTradeAccountDTO userTradeAccountDTO : queryAccountList()) {
                userMapper.createUserAccountInfo(userId, userTradeAccountDTO.getAccountId(), userTradeAccountDTO.getAccountType());
            }
            //新增用户锁数据
            userMapper.addUserTransLockInfo(userId);

            //用户加载到VJ的渠道下（默认）
            batchUserMapper.saveChannelForWallet();

            //创建邀请好友信息
            createInvite(userId,phone,channelType);

            //分享
            userCouponService.distributeRegisterCashCoupon(userId);

            return new OutSupportResultDTO("0","创建用户成功",userId);

        } catch (Exception e) {
            BaseLogger.error(e);
            // 发送邮件
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("phone", phone);
            sendEmailService.sendNormalEmail("SYSTEM_ERROR_0007", paramMap);
            return new OutSupportResultDTO("503","创建用户失败");
        }

    }


    //创建邀请数据（只保存最基本数据）
    private void createInvite(String userId,String phone,String channelType){

        String myInviteCode = ShareCodeUtil.toSerialCode(Long.parseLong(phone));
        String myInviteUrl = inviteRegisterUrl + "?inviteCode=" + myInviteCode;

        userInviteMapper.createUserInviteInfo(userId, "", myInviteCode, myInviteUrl, null, null,null,null);

    }


    private String getUserNoId(String sequenceName) {
        return "US" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + sequenceService.getNextStringValue(sequenceName, 10);
    }


    private String getAccountId(String sequenceName) {
        return "AC" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + sequenceService.getNextStringValue(sequenceName, 10);
    }

    public Boolean checkLoginForApp(String phone, String uuid) {
        // 校验参数
        ValidationMethodConstant.validateCheckLoginForApp(phone, uuid);

        int loginCount = 0;
		try {
//			String redisUUID = jedisFactory.get(appLoginPrefixKey + phone);
//			loginCount = (org.apache.commons.lang.StringUtils.isNotBlank(redisUUID) && redisUUID
//					.toString().equals(uuid)) ? 1 : userMapper
//					.checkLoginForApp(phone, uuid);
			loginCount = userMapper.checkLoginForApp(phone, uuid);
		} catch (Exception ex) {
//			loginCount = userMapper.checkLoginForApp(phone, uuid);
			BaseLogger.error(ex);
		}
		if (loginCount != 1) {
			throw new AppException("登录超时或其他地方已经登录!");
		}
		return Boolean.TRUE;
    }

    public String sendRegisterToneNotice(String phone, String password) {
        this.checkPhoneIsRegister(phone, password);
        MessageDTO message = phoneMessageService.sendToneMessage(phone, "user_regist");
        if (!message.getSendFlag()) {
            throw new AppException(message.getSendFlagMessage());
        }
        return message.getVaildeTime();
    }

    public String sendRegisterSMSNotice(String phone, String password) {
        this.checkPhoneIsRegister(phone, password);
        	 MessageDTO message = phoneMessageService.sendTextMessage(phone, "user_regist");
             if (!message.getSendFlag()) {
                 throw new AppException(message.getSendFlagMessage());
             }
             return message.getVaildeTime();
        }

    public Boolean checkRegisterCode(String phone, String code, String bizType) {
        ValidationMethodConstant.validateCode(code);
        	MessageDTO message = phoneMessageService.checkValidationCode(phone, code, bizType);
            if (!message.getSendFlag()) {
               throw new AppException(message.getSendFlagMessage());
            }
        return Boolean.TRUE;
    }

    private void checkPhoneIsRegister(String phone, String password) {
        ValidationMethodConstant.validateLoginInfo(phone, password);
        int queryUserCount = userMapper.queryUserCountByPhone(phone);
        if (queryUserCount != 0) {
            throw new AppException("此手机号码已经注册");
        }
    }

    private void checkForgetPasswordIsRegister(String phone, String password) {
        ValidationMethodConstant.validateLoginInfo(phone, password);
        int queryUserCount = userMapper.queryUserCountByPhone(phone);
        if (queryUserCount != 1) {
            throw new AppException("此手机号码未注册");
        }
    }

    public String sendForgetSMSNotice(String phone, String password) {
        checkForgetPasswordIsRegister(phone, password);
        MessageDTO message = phoneMessageService.sendTextMessage(phone, "user_reset_password");
        if (!message.getSendFlag()) {
            throw new AppException(message.getSendFlagMessage());
        }
        return message.getVaildeTime();
    }

    public String sendForgetToneNotice(String phone, String password) {
        checkForgetPasswordIsRegister(phone, password);
        MessageDTO message = phoneMessageService.sendToneMessage(phone, "user_reset_password");
        if (!message.getSendFlag()) {
            throw new AppException(message.getSendFlagMessage());
        }
        return message.getVaildeTime();
    }

    public Boolean updateForgetPassword(String phone, String password, String code) {
        checkForgetPasswordIsRegister(phone, password);
        if (checkRegisterCode(phone, code, "user_reset_password")) {
            // 修改密码
            userMapper.updateUserPassword(phone, new EncodeSHAUtils().sha512Encode(password));
        }
        return Boolean.TRUE;
    }

    public String sendActivateSMSNotice(String phone, String password) {
        checkActivateIsRegister(phone, password);
        MessageDTO message = phoneMessageService.sendTextMessage(phone, "user_regist");
        if (!message.getSendFlag()) {
            throw new AppException(message.getSendFlagMessage());
        }
        return message.getVaildeTime();
    }

    public String sendActivateToneNotice(String phone, String password) {
        checkActivateIsRegister(phone, password);
        MessageDTO message = phoneMessageService.sendToneMessage(phone, "user_regist");
        if (!message.getSendFlag()) {
            throw new AppException(message.getSendFlagMessage());
        }
        return message.getVaildeTime();
    }

    public Boolean updateActivateUserInfo(String phone, String password, String code) {
        checkActivateIsRegister(phone, password);
        if (checkRegisterCode(phone, code, "user_regist")) {
            // 修改密码
            userMapper.updateActivateUserInfo(phone, new EncodeSHAUtils().sha512Encode(password));
        }
        //企业用户激活时，添加用户邀请信息并取企业的渠道号
        try {
            String channelNo = userMapper.queryENUserChannelNo(phone);
            String inviteCode = ShareCodeUtil.toSerialCode(Long.parseLong(phone));
            String userId = userMapper.queryUserIdByPhone(phone);
            mbUserInviteMapper.createUserInviteInfo(userId, null, inviteCode, inviteRegisterUrl + "?inviteCode=" + inviteCode, null, channelNo, null, null);
        }
        catch (Exception e) {
            BaseLogger.error(e.getMessage());
        }
        return Boolean.TRUE;
    }

    private void checkActivateIsRegister(String phone, String password) {
        ValidationMethodConstant.validateLoginInfo(phone, password);
        int queryUserCount = userMapper.queryUserCountByPhone(phone);

        if (queryUserCount != 1) {
            throw new AppException("此手机号码未注册");
        }
        MBLoginUserDTO loginUserDTO = userMapper.loginForApp(phone);
        if (!MBLoginUserDTO.USERSIGN_FAIL_0.equals(loginUserDTO.getSign())) {
            throw new AppException("此手机号码不需要激活或者已经激活");
        }
    }


    private void checkPhoneIsRegister(String phone) {
        ValidationMethodConstant.validatePhone(phone);
        int queryUserCount = userMapper.queryUserCountByPhone(phone);
        if (queryUserCount != 1) {
            throw new AppException("此手机号码未注册");
        }
    }


    //易才 登录,获取验证码
    public String sendLoginSMSNotice(String phone) {
        checkPhoneIsRegister(phone);
        MessageDTO message = phoneMessageService.sendTextMessage(phone, BusinessMessageTypeEnum.YC_LOGIN_MESSAGE.getValue());
        if (!message.getSendFlag()) {
            throw new AppException(message.getSendFlagMessage());
        }
        return message.getVaildeTime();
    }


//	private Boolean validatorLoginSMSNotioce(String phone, String code) {
//		checkPhoneIsRegister(phone);
//		checkRegisterCode(phone, code, BusinessMessageTypeEnum.YC_LOGIN_MESSAGE.getValue());
//		return Boolean.TRUE;
//	}


    @Override
    public MBLoginUserDTO loginForWX(String phone, String code, MBLoginChannelEnum channelEnum, String messageType) {

        //	校验短信
        validatorSMSNotioce(phone, code, messageType);

        BaseLogger.audit("微信登录      用户名 " + phone);

        //	登录
        return this.WXUserLogin(phone, channelEnum);
    }

    //fesco 登录,获取验证码
    public String sendLoginSMSNoticeForFesco(String phone) {
        checkPhoneIsRegister(phone);
        MessageDTO message = phoneMessageService.sendTextMessage(phone, BusinessMessageTypeEnum.FESCO_LOGIN_MESSAGE.getValue());
        if (!message.getSendFlag()) {
            throw new AppException(message.getSendFlagMessage());
        }
        return message.getVaildeTime();
    }

    //vj 登录,获取验证码
    public String sendLoginSMSNoticeForVj(String phone) {
        checkPhoneIsRegister(phone);
        MessageDTO message = phoneMessageService.sendTextMessage(phone, BusinessMessageTypeEnum.GZQB_WECHAT_LOGIN_MESSAGE.getValue());
        if (!message.getSendFlag()) {
            throw new AppException(message.getSendFlagMessage());
        }
        return message.getVaildeTime();
    }

    public String sendForgetGesturePwdSMSNoticeForFesco(String phone) {
        checkPhoneIsRegister(phone);
        MessageDTO message = phoneMessageService.sendTextMessage(phone, BusinessMessageTypeEnum.FESCO_RESET_SIGN_PASSWORD.getValue());
        if (!message.getSendFlag()) {
            throw new AppException(message.getSendFlagMessage());
        }
        return message.getVaildeTime();
    }

    public MBLoginUserDTO WXUserAutoLogin(String phone, MBLoginChannelEnum channelEnum) {

        BaseLogger.audit("自动登录      用户名:" + phone);

        return this.WXUserLogin(phone, channelEnum);
    }


    private MBLoginUserDTO WXUserLogin(String phone, MBLoginChannelEnum channelEnum) {
        //	1. 校验登录
        ValidationMethodConstant.validateWXUserLogin(phone, channelEnum);

        //	2. 登录
        MBLoginUserDTO loginUserDTO = userMapper.loginForWX(phone);

        //	3. 修改对应渠道uuid
        String uuid = PKUtils.getPKID("UU");
        userMapper.updateLoginWXUUID(phone, uuid, channelEnum.getValue());
        loginUserDTO.setUuid(uuid);
        BaseLogger.audit("已更新用户uuid");
        return loginUserDTO;
    }


    /**
     * 校验短信
     */
    public Boolean validatorSMSNotioce(String phone, String code, String channel) {
        checkPhoneIsRegister(phone);
        checkRegisterCode(phone, code, channel);
        return Boolean.TRUE;
    }

    public List<UserTradeAccountDTO> queryAccountList() {

        // 所有账户类型都在这里添加
        String accountTypes[] = {"ma", "ea", "v1", "rf", "ln", "la", "lf", "ta", "rp"};

        List<UserTradeAccountDTO> reutrnList = new ArrayList<UserTradeAccountDTO>();

        //迭代账户类型:账户id和账户类型
        for (String accountType : accountTypes) {
            UserTradeAccountDTO userTradeAccountDTO = new UserTradeAccountDTO();
            userTradeAccountDTO.setAccountId(getAccountId(ISequenceService.SEQ_NAME_USER_ACCOUNT_SEQ));
            userTradeAccountDTO.setAccountType(accountType);
            reutrnList.add(userTradeAccountDTO);
        }
        return reutrnList;
    }

    @Override
    @Transactional
    public MBLoginUserDTO loginForSHQB(String phone, SHQBUserInfoDTO paramDto) {
        SHQBUserValidator(phone, paramDto);
        VJUserValidator(phone, paramDto);
        MBLoginUserDTO dto = userMapper.loginForApp(phone);

        String uuid = PKUtils.getPKID("UU");
        dto.setUuid(uuid);
        userMapper.updateLoginUUIDFromSHQB(phone, uuid);
        return dto;
    }

    private void SHQBUserValidator(String phone, SHQBUserInfoDTO paramDto) {
        SHQBUserInfoDTO exist = shqbUserMapper.queryUserInfoByPhone(phone);
        String noMatchTip = shqbUserMapper.queryNoMatchTip();
        if (exist == null) {//用户不存在
            //判断身份证是否已存在
            if (shqbUserMapper.queryUserInfoByIdentityNo(paramDto.getIdentityNo()) != null) {
//                throw new AppException(601, "商户钱包：手机号不存在,信息已存在");
                throw new AppException(601, noMatchTip);
            }
            shqbUserMapper.createUserInfo(paramDto);
        } else {//用户已存在
            //校验信息是否匹配
            if (!exist.getRealName().equals(paramDto.getRealName()) || !exist.getIdentityNo().equals(paramDto.getIdentityNo())) {
//                throw new AppException(601, "商户钱包：手机号已存在,信息不匹配");
                throw new AppException(601, noMatchTip);
            }
            //没有银行卡信息
            if (StringUtils.isEmpty(paramDto.getBankCardNo())) {
                return;
            }
            //更新银行卡信息
            if (!exist.getBankCardNo().equals(paramDto.getBankCardNo())) {
                shqbUserMapper.updateUserBankCardNo(exist.getPhone(), paramDto.getBankCardNo());
            }
        }
    }

    private void VJUserValidator(String phone, SHQBUserInfoDTO paramDto) {
        MBUserDTO userDto = userMapper.queryUserByPhone(phone);
        String noMatchTip = shqbUserMapper.queryNoMatchTip();
        if (userDto == null) {//用户不存在
            //判断身份证是否已存在
            if (userMapper.queryUserByIdentityNo(paramDto.getIdentityNo()) != null) {
//                throw new AppException(601, "融桥宝：手机号不存在,信息已存在");
                throw new AppException(601, noMatchTip);
            }

            String userId = getUserNoId(ISequenceService.SEQ_NAME_USER_ID_SEQ);
            Map<String, Object> map = new HashMap<>();
            map.put("userId", userId);
            map.put("phone", phone);
            map.put("realName", paramDto.getRealName());
            map.put("identityNo", paramDto.getIdentityNo());
            map.put("sex", CheckIdCardUtils.getGenderByIdCard(paramDto.getIdentityNo()));
            map.put("enterpriseNo", shqbUserMapper.querySHQBEnterpriseId());
            //创建未激活账户
            userMapper.createUnActiveUser(map);

            if (!StringUtils.isEmpty(paramDto.getBankCardNo())) {
                //根据银行卡前6位或8位匹配银行信息
                Map<String, String> bankInfo = shqbUserMapper.queryBankInfoByCardBin(paramDto.getBankCardNo().substring(0, 6), paramDto.getBankCardNo().substring(0, 8));
                if (bankInfo == null || bankInfo.isEmpty()) {
                    BaseLogger.info("商户钱包用户银行卡匹配失败");
                    Map<String, Object> params = new HashMap<>();
                    params.put("realName", paramDto.getRealName());
                    params.put("phone", phone);
                    params.put("bankCardNo", paramDto.getBankCardNo());
                    sendEmailService.sendAsyncEmail("WALLET_SHQB_BANK_ERROR", params);
                } else {
                    //创建安全卡
                    String cardId = getCardId(ISequenceService.SEQ_NAME_CARD_SEQ);
                    batchUserMapper.createSecurityAccountCard(userId, cardId, paramDto.getRealName(), paramDto.getBankCardNo(), phone, bankInfo.get("bankName"), bankInfo.get("bankCode"));
                }
            }

            //	创建各种账户
            for (UserTradeAccountDTO userTradeAccountDTO : queryAccountList()) {
                userMapper.createUserAccountInfo(userId, userTradeAccountDTO.getAccountId(), userTradeAccountDTO.getAccountType());
            }
            userMapper.addUserTransLockInfo(userId);

            //用户加载到VJ的渠道下
            batchUserMapper.saveChannelForWallet();
            //用户加载到指定商户钱包渠道下
            shqbUserMapper.saveChannelForSHQB(userId);
        } else {
            //信息匹配
            if (!userDto.getRealName().equals(paramDto.getRealName()) || !userDto.getIndentityNo().equals(paramDto.getIdentityNo())) {
                //throw new AppException(601, "融桥宝：手机号已存在,信息不匹配");
                throw new AppException(601, noMatchTip);
            }
            //没有银行卡，无需后续操作
            if (StringUtils.isEmpty(paramDto.getBankCardNo())) {
                return;
            }
            String securityCardNo = shqbUserMapper.queryUserSecurityCardNo(userDto.getId());
            if (StringUtils.isEmpty(securityCardNo)) {//用户已存在,但无安全卡,则插入安全卡
                batchUserMapper.createSecurityAccountCard(userDto.getId(), getCardId(ISequenceService.SEQ_NAME_CARD_SEQ), paramDto.getRealName(), paramDto.getBankCardNo(), phone, "", "");
            } else {//已存在安全卡,如果已变更，需要同时更新安全卡、充值卡
                if (!paramDto.getBankCardNo().equals(securityCardNo)) {
                    //根据银行卡前6位或8位匹配银行信息
                Map<String, String> bankInfo = shqbUserMapper.queryBankInfoByCardBin(paramDto.getBankCardNo().substring(0, 6), paramDto.getBankCardNo().substring(0, 8));
                    shqbUserMapper.updateUserSecurityCardNo(userDto.getId(), paramDto.getBankCardNo(), securityCardNo, bankInfo.get("bankName"), bankInfo.get("bankCode"));
                }
            }
        }
    }

    private String getCardId(String sequenceName) {
        return "CA" + new SimpleDateFormat("yyyyMMdd").format(new Date())
                + sequenceService.getNextStringValue(sequenceName, 10);
    }

    @Override
    public MBUserDTO queryUserByIdentityNo(String identityNo) {
        return userMapper.queryUserByIdentityNo(identityNo);
    }

	public Map<String,String> sendLoginSMSNoticeForPicc(String phone,String bizType)throws Exception {
		Map<String,String> map = new HashMap<String,String>();
		
        checkPhoneIsRegister(phone);
        
        MBUserDTO userDTO = userMapper.queryUserByPhone(phone);
        map.put("userId", userDTO.getId());
      //查询用户是否绑定的银行卡
		List<BindingCardDTO> listCard =null;// withholdServiceFacade.querySecurityWithholdCardV2(userDTO.getId(),bizType);
	
		if(listCard.size()==0
				|| StringUtils.isEmpty(listCard.get(0).getCardNo())
				|| !"Y".equals(listCard.get(0).getIsBindingThird())
				|| !"Y".equals(listCard.get(0).getIsSupportCard()))
		{
			map.put("code", "F601");//没绑定代扣银行卡
			return map;
		}
			
        
        MessageDTO message =null;// phoneMessageService.sendTextMessage(phone, BusinessMessageTypeEnum.PICC_LOGIN_MESSAGE.getValue());
        if (!message.getSendFlag()) {
            throw new AppException(message.getSendFlagMessage());
        }
        
        map.put("code", "S200");//没绑定代扣银行卡
		map.put("vaildeTime", message.getVaildeTime());
		return map;
	}
	
	
	public MBLoginUserDTO loginForPiccWx(String phone,MBLoginChannelEnum channelEnum,String dynamicCode)throws Exception {
		// 1. 校验
	    ValidationMethodConstant.validatePhone(phone);
	    // 2. 检验验证码
//	    checkRegisterCode(phone, dynamicCode, BusinessMessageTypeEnum.PICC_LOGIN_MESSAGE.getValue());
	    
	    // 4. 登录
	    MBLoginUserDTO loginUserDTO = userMapper.loginForApp(phone);
	    // 5. 检查账户信息
	    ValidationMethodConstant.validateLoginUserNoPassword(loginUserDTO);
	    
	    String uuid = PKUtils.getPKID("UU");
	    userMapper.updateLoginUUID(phone, loginUserDTO.getPassword(), uuid);
	    loginUserDTO.setPassword(null);
	    loginUserDTO.setUuid(uuid);

	    return loginUserDTO;
	}
    
	/**
     * 先绑卡成功后创建新用户，包含渠道
     * @param phone
     * @param password
     * @param channelType
     * @return
     */
    @Transactional
    public OutSupportResultDTO bandingRegisterWithoutCode(String phone, String password,String channelType,String userId)throws Exception {
        //注册
        try {

            //创建用户(贷渠道)
            userMapper.createUserInfoByChannel(userId, phone, new EncodeSHAUtils().sha512Encode(password),channelType );

            //创建各种账户
            for (UserTradeAccountDTO userTradeAccountDTO : queryAccountList()) {
                userMapper.createUserAccountInfo(userId, userTradeAccountDTO.getAccountId(), userTradeAccountDTO.getAccountType());
            }
            //新增用户锁数据
            userMapper.addUserTransLockInfo(userId);

            //用户加载到VJ的渠道下（默认）
            batchUserMapper.saveChannelForWallet();

            //创建邀请好友信息
            createInvite(userId,phone,channelType);

            //分享
            userCouponService.distributeRegisterCashCoupon(userId);

            return new OutSupportResultDTO("0","创建用户成功",userId);

        } catch (Exception e) {
            BaseLogger.error(e);
            // 发送邮件
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("phone", phone);
            sendEmailService.sendNormalEmail("SYSTEM_ERROR_0007", paramMap);
            return new OutSupportResultDTO("503","创建用户失败");
        }

    }
}
