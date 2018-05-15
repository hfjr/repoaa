package com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.impl;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.fab.core.annotation.Remote;
import com.fab.core.exception.service.AppException;
import com.fab.core.logger.BaseLogger;
import com.fab.core.util.PKUtils;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.dto.BindingCardDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.facade.IWithholdServiceFacade;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.constant.UserChannelTypeConstant;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.BankCardBasicInfoDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.BankInfoDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.JDRegisterParamDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBLoginUserDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBRechargeDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBUserDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.OutSupportResultDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.QueryBankCardInfoParamDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.UserInfoDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.ValidatorCardIsSecurityParamDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper.IMBUserAccountMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper.IMBUserMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.IMBOutSupportService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.IMBUserAccountService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.IMBUserService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.IUserAccountTransactionService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.util.CheckIdCardUtils;
import com.zhuanyi.vjwealth.wallet.service.outer.message.dto.MessageDTO;
import com.zhuanyi.vjwealth.wallet.service.outer.message.server.service.IPhoneMessageService;

/**
 * Created by wzf on 2016/11/15.
 */
@Service
public class MBOutSupportServiceImpl implements IMBOutSupportService {

    @Autowired
    private IMBUserService mBUserService;

    @Autowired
    private IMBUserMapper userMapper;

    @Remote
    IPhoneMessageService phoneMessageService;
    @Autowired
    IMBUserAccountService userAccountService;
    @Autowired
	private IMBUserAccountMapper userAccountMapper;
	@Autowired
	private IUserAccountTransactionService userAccountTransactionService;
//	@Remote
//	private IAutoInsuranceLoanService autoInsuranceLoanService;
	@Autowired
	private IWithholdServiceFacade withholdServiceFacade;

    /**
     * @title 通过手机号注册
     * @tip 错误码到104
     * @param phone
     * @return
     */
    @Override
    public OutSupportResultDTO qndRegisterByPhone(String phone,String smsCode) {
        //校验是否是vj用户
        OutSupportResultDTO result = isVjUser(phone);

        boolean flag = (boolean)result.getData();

        //如果没有注册过，而且验证码为空，则错误
        if(!flag && StringUtils.isBlank(smsCode)){
            return new OutSupportResultDTO("502","验证码为空");
        }

        //如果未注册过，且验证码不为空，则进行注册操作
        if(!flag && !StringUtils.isBlank(smsCode)){
            //校验验证码是否正确
            OutSupportResultDTO validatorResult = validatorCode(phone,smsCode);
            if(validatorResult.isSuccess()){
                //进行操作注册
                return mBUserService.registerWithoutCode(phone,"123456", UserChannelTypeConstant.CHANNEL_TYPE_QND);
            }else{
                return validatorResult;
            }
        }

        return new OutSupportResultDTO("0","注册完成");

    }

    @Override
    public OutSupportResultDTO isVjUser(String phone) {
        //校验是否已经注册
        int queryUserCount = userMapper.queryUserCountByPhone(phone);
        if (queryUserCount != 0) {
            return new OutSupportResultDTO("0","已经注册过",true);
        }
        return new OutSupportResultDTO("0","未注册过",false);
    }


    /**
     *
     * @tip 错误码到501
     * @param phone
     * @param code
     * @return
     */
    @Override
    public OutSupportResultDTO validatorCode(String phone,String code) {
        try{
            mBUserService.checkRegisterCode(phone,code,"user_regist");
        }catch (AppException e){
            BaseLogger.error(e);
            return new OutSupportResultDTO("504",e.getMessage());
        }
        return new OutSupportResultDTO("0","验证通过");
    }

    @Override
    public OutSupportResultDTO queryUserInfo(String phone) {
        MBUserDTO userDTO = userMapper.queryUserByPhone(phone);
        UserInfoDTO infoDTO = new UserInfoDTO();
        infoDTO.setUserId(userDTO.getId());
        return new OutSupportResultDTO("0","",infoDTO);
    }

    /**
     *
     * @tip 错误码到401
     * @param phone
     * @return
     */
    @Override
    public OutSupportResultDTO sendRegisterSMS(String phone) {

        //校验手机号是否合法
        if (!Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$").matcher(phone).matches()) {
            return new OutSupportResultDTO("401","机号码不合法");
        }

        //校验是否已经注册
        int queryUserCount = userMapper.queryUserCountByPhone(phone);
        if (queryUserCount != 0) {
            return new OutSupportResultDTO("403","此手机号码已经注册");
        }

        String time = "";
        try{
            MessageDTO message = phoneMessageService.sendTextMessage(phone, "user_regist");
            if (!message.getSendFlag()) {
                return new OutSupportResultDTO("405",message.getSendFlagMessage(),time);
            }
            time = message.getVaildeTime();
        }catch (Exception e){
            BaseLogger.error(e);
            return new OutSupportResultDTO("406","发送短信验证码异常");
        }

        return new OutSupportResultDTO("0","验证码发送成功",time);
    }


    /**
     * 京东金融-注册
     * @param paramDTO
     * @return
     */
    @Override
    public OutSupportResultDTO jdRegisterByPhone(JDRegisterParamDTO paramDTO ) {

        String phone = paramDTO.getPhone();

        try{
        //校验是否是vj用户
        OutSupportResultDTO result = isVjUser(phone);

        boolean flag = (boolean)result.getData();

        //如果未注册过,则进行注册操作
        if(!flag ){

            //校验身份证号是否已存在
            if(isExistCertNo(paramDTO.getCertNo())){
                return new OutSupportResultDTO("509","身份证号已被占用");
            }

            OutSupportResultDTO registerResult = mBUserService.registerWithoutCode(phone,"123456", UserChannelTypeConstant.CHANNEL_TYPE_JDJR);

            //如果注册成功后，则更新对应的用户信息
            if(registerResult.isSuccess()){
                String userId = (String) registerResult.getData();
                userMapper.updateUserBasicInfo(userId,paramDTO.getRealName(),paramDTO.getCertNo());
            }
            return registerResult;
        }else{
            MBUserDTO userDTO = userMapper.queryUserByPhone(phone);

            if((StringUtils.isBlank(userDTO.getRealName()) || paramDTO.getRealName().equals(userDTO.getRealName()) )
                    && (StringUtils.isBlank(userDTO.getIndentityNo()) || paramDTO.getCertNo().equals(userDTO.getIndentityNo()))){

                userMapper.updateUserBasicInfo(userDTO.getId(),paramDTO.getRealName(),paramDTO.getCertNo());
                return new OutSupportResultDTO("0","注册完成",userDTO.getId());
            }else{
                return new OutSupportResultDTO("508","用户已注册，但身份证号、手机号、姓名不匹配");
            }
        }

        }catch (Exception e){
            BaseLogger.error(e);
            return new OutSupportResultDTO("510","注册用户异常");
        }

    }

    private boolean isExistCertNo(String cerNo){
        return userMapper.isExistCertNo(cerNo);
    }

    /**
     * 查询银行卡基本信息
     * @param paramDTO
     * @return
     */
    @Override
    public OutSupportResultDTO queryBankCardInfo(QueryBankCardInfoParamDTO paramDTO) {
        BankCardBasicInfoDTO infoDTO = userMapper.queryUserBankCardInfoByCardId(paramDTO.getUserId(),paramDTO.getCardId());

        if(infoDTO == null){
            return new OutSupportResultDTO("-1","数据不存在");
        }

        if("01".equals(infoDTO.getCertificateType())){
            infoDTO.setCertificateType("0");//身份证
        }else{
            infoDTO.setCertificateType("7");//其他的证件类型
        }

        return new OutSupportResultDTO("0","查询成功",infoDTO);
    }

    @Override
    public OutSupportResultDTO validatorCardIsSecurity(ValidatorCardIsSecurityParamDTO paramDTO) {
        boolean flag = userMapper.validatorCardIsSecurity(paramDTO);
        return new OutSupportResultDTO("0","查询成功",flag);
    }

	public OutSupportResultDTO piccBindingValidate(
			MBRechargeDTO rechargeDTO) {
		//1.验证手机号，身份证号是否已注册 
		//校验是否是vj用户
		MBUserDTO userDTO = userMapper.queryUserByPhone(rechargeDTO.getCardBindMobilePhoneNo());
		if(userDTO!=null && !StringUtils.isBlank(userDTO.getPhone())){
			rechargeDTO.setUserId(userDTO.getId());
			//校验身份证号
			if(!StringUtils.isBlank(userDTO.getIndentityNo())
					&& !userDTO.getIndentityNo().equals(rechargeDTO.getCertNo())){
				throw new AppException("身份证号异常");
//				return new OutSupportResultDTO("602","身份证号异常");
			}
			//用户已存在，判断是否绑定京东代扣卡
			List<BindingCardDTO> listCard = null;//withholdServiceFacade.querySecurityWithholdCardV2(userDTO.getId(),rechargeDTO.getOrderType());
			if(listCard.size()>0
					&& !StringUtils.isBlank(listCard.get(0).getCardNo())
					&&"Y".equals(listCard.get(0).getIsSupportCard())
					&&"Y".equals(listCard.get(0).getIsBindingThird()))
			{
				throw new AppException("用户已注册");
			}else{
				//请绑定安全卡
				Map<String,String> cardInfo = userMapper.queryUserSecurityCardInfo(userDTO.getId());
				if(cardInfo!=null && !StringUtils.isBlank(cardInfo.get("cardNo"))){
					String cardNo = cardInfo.get("cardNo");
					if(!rechargeDTO.getCardNo().equals(cardNo)){
						String info = "经数据同步显示,您已在融桥宝平台绑定"+cardInfo.get("ea_bankSufCardNo")+"的" +cardInfo.get("ea_bankName")+"银行卡";
						return new OutSupportResultDTO("600001",info);
					}
				}
				
			}
				
//				return new OutSupportResultDTO("601","用户已注册");
		}else{
			 //校验身份证号是否已存在
	        if(isExistCertNo(rechargeDTO.getCertNo())){
	        	throw new AppException("身份证号已被占用");
//	            return new OutSupportResultDTO("602","身份证号已被占用");
	        }	
		}
		
		//2.调用京东代扣绑卡验证短信
        
        rechargeDTO.setCertType("01");
        MBRechargeDTO mBRechargeDTO = null;//userAccountService.bindCardSendCodeNoRegister(rechargeDTO);
		
		return new OutSupportResultDTO("200","验证码发送成功",mBRechargeDTO);
	}

	public MBLoginUserDTO piccConfirmBindCardAndLogin(MBRechargeDTO rechargeDTO) throws Exception {
		//1.第三方确认绑卡 
		//userAccountService.confirmBindCardSendCodeNoRegister(rechargeDTO);//失败抛异常
		boolean flag = false;
		//2.绑卡成功则，注册用户
		//用户已注册就不注册只绑卡
		MBUserDTO userDTO = userMapper.queryUserByPhone(rechargeDTO.getCardBindMobilePhoneNo());
		if(userDTO==null || StringUtils.isBlank(userDTO.getPhone())){
			flag = this.registerAuthentication(rechargeDTO);
			if(!flag)
				throw new AppException("系统繁忙请稍后再试");
		}
		
		//新增卡信息并实名认证 
		this.bandingCard(rechargeDTO);
		
		//3.同步用户信息给信贷系统
		try {
//			autoInsuranceLoanService.piccUserRegister(rechargeDTO);
		} catch (Exception e) {
			BaseLogger.error("picc用户注册成功同步数据到信贷系统失败",e);
			throw new AppException("信息异常请联系客服人员");
		}
		
		//4.登录
	    MBLoginUserDTO loginUserDTO = userMapper.loginForApp(rechargeDTO.getCardBindMobilePhoneNo());
	    
	    String uuid = PKUtils.getPKID("UU");
	    userMapper.updateLoginUUID(rechargeDTO.getCardBindMobilePhoneNo(), loginUserDTO.getPassword(), uuid);
	    loginUserDTO.setPassword(null);
	    loginUserDTO.setUuid(uuid);
	    
	    return loginUserDTO;
	}
	
	private boolean registerAuthentication(MBRechargeDTO rechargeDTO) throws Exception{
//		OutSupportResultDTO registerResult = mBUserService.bandingRegisterWithoutCode(rechargeDTO.getCardBindMobilePhoneNo(),"123456", UserChannelTypeConstant.CHANNEL_TYPE_PICC,rechargeDTO.getUserId());

        //如果注册成功后，则更新对应的用户信息
//        if(registerResult.isSuccess()){
//            String userId = (String) registerResult.getData();
//            userMapper.updateUserBasicInfo(userId,rechargeDTO.getRealName(),rechargeDTO.getCertNo());
//            
//            return true;
//        }else{
//        	return false;
//        }
        	return false;
	}
	
	private void bandingCard(MBRechargeDTO rechargeDTO){
		int bindRepeatCardCount = userAccountMapper.queryBindRepeatCardCountBycardNo(rechargeDTO.getUserId(), rechargeDTO.getCardNo());
	    if (bindRepeatCardCount > 0) {
	     	return ;
	    }
		// 银行名称查询
		List<BankInfoDTO> bankInfo = userAccountMapper.findBankInfoList(rechargeDTO.getBankCode());
		if (bankInfo != null && bankInfo.size() > 0) {
			rechargeDTO.setBankName(bankInfo.get(0).getBankNameShow());
		} 
		// 判断性别
        if (!StringUtils.isBlank(rechargeDTO.getCertType()) && rechargeDTO.getCertType().equals("01")) {// 身份证
			rechargeDTO.setSex(CheckIdCardUtils.getGenderByIdCard(rechargeDTO.getCertNo()));
        }
        	
        //用户绑卡
		userAccountTransactionService.doBindCard(rechargeDTO);
        
	}

	public List<BindingCardDTO> querySecurityWithholdCard(String userId,
			String bizType) throws Exception {
		
		return null;//withholdServiceFacade.querySecurityWithholdCardV2(userId,bizType);
		
	}
}
