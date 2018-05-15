package com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fab.core.annotation.Remote;
import com.fab.core.exception.service.AppException;
import com.fab.core.logger.BaseLogger;
import com.fab.core.util.EncodeSHAUtils;
import com.fab.core.util.PKUtils;
import com.vjwealth.event.api.dto.ExcuteServiceRequestDTO;
import com.vjwealth.event.api.service.IExcuteEventService;
import com.zhuanyi.vjwealth.loan.credit.webservice.ITaskDetailsInfoDubboService;
import com.zhuanyi.vjwealth.wallet.service.coupon.server.service.IUserCouponService;
import com.zhuanyi.vjwealth.wallet.service.file.upload.server.service.ICommonAttachmentOperate;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.common.server.service.ISequenceService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.constant.ValidationMethodConstant;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBLoginUserDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBUserInviteDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.RegisterParamDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.UserShareInfoDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.UserTradeAccountDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper.IMBUserBatchOperateMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper.IMBUserInviteMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper.IMBUserMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.IMBUserInviteService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.yingzt.utils.yingzt.QRCodeUtil;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.yingzt.utils.yingzt.ShareCodeUtil;
import com.zhuanyi.vjwealth.wallet.service.outer.email.server.service.ISendEmailService;
import com.zhuanyi.vjwealth.wallet.service.outer.message.dto.MessageDTO;
import com.zhuanyi.vjwealth.wallet.service.outer.message.server.service.IPhoneMessageService;

@Service
public class MBUserInviteService implements IMBUserInviteService {
    @Remote
    ISendEmailService sendEmailService;

    @Autowired
    private ISequenceService sequenceService;

    @Remote
    ICommonAttachmentOperate commonAttachmentOperate;

    @Remote
    IPhoneMessageService phoneMessageService;

    @Autowired
    private ITaskDetailsInfoDubboService taskDetailsInfoDubboService;

    @Autowired
    private IMBUserMapper userMapper;

    @Autowired
    IMBUserInviteMapper userInviteMapper;

    @Autowired
    private IMBUserBatchOperateMapper batchUserMapper;

    @Value("${h5.inviteRegisterUrl}")
    private String inviteRegisterUrl;

    @Value("${h5.inviteMarketUrl}")
    private String inviteMarketUrl;

    @Autowired
    IUserCouponService userCouponService;
    
	@Autowired
	private IExcuteEventService excuteEventService;

    /**
     * 根据注册码查询推荐人ID
     *
     * @param paramDTO
     * @return
     */
    public String queryRecommendUserIdByInviteCode(RegisterParamDTO paramDTO) {
        String recommendUserId = null;
        if (!StringUtils.isEmpty(paramDTO.getInviteCode())) {
            if (Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$").matcher(paramDTO.getInviteCode()).matches()) {//通过邀请人手机号注册
                recommendUserId = userMapper.queryUserIdByPhone(paramDTO.getInviteCode());
            } else {//通过邀请码注册
                recommendUserId = userInviteMapper.queryUserIdByInviteCode(paramDTO.getInviteCode().toLowerCase());
            }
        }
        return recommendUserId;
    }

    @Override
    public Boolean register(String phone, String password, String code, RegisterParamDTO paramDTO) {
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

                createUserInviteInfo(userId, phone, paramDTO);
                
                //注册成功事件
                regirstSuccessEvent(userId);
            } catch (Exception e) {
                BaseLogger.error(e);
                // 发送邮件
                Map<String, Object> paramMap = new HashMap<String, Object>();
                paramMap.put("phone", phone);
                sendEmailService.sendNormalEmail("SYSTEM_ERROR_0007", paramMap);
                throw new AppException("创建用户失败，请稍后再试");
            }
        }

        return true;
    }

    

	
	private void regirstSuccessEvent(String userId){
		try{
			ExcuteServiceRequestDTO excuteServiceRequestDTO = new ExcuteServiceRequestDTO();
			Map<String, String>	paramMap=new HashMap<String,String>();
			paramMap.put("userId", userId);
			paramMap.put("recommendUserId",userInviteMapper.queryRecommendUserIdByUserId(userId));
			paramMap.putAll(userInviteMapper.queryEventInfo(userId));
			excuteServiceRequestDTO.setParamJsonObject(excuteServiceRequestDTO.parseObject(paramMap));
			excuteEventService.excuteAsyncEvent("EV_0005", excuteServiceRequestDTO);
		}catch (Exception ex){
			BaseLogger.error("事情平台EV_0005失败",ex);
		}
	}
	
	
    private void distributeGoldFish(String phone, RegisterParamDTO paramDTO) {
        boolean sh = userInviteMapper.queryInviteRegisterDistributeGoldFishSwitch();
        if (sh == false) {//开关关闭
            BaseLogger.audit("邀请注册发放小金鱼开关已关闭");
            return;
        }

        String recommendUserId = null;
        try {
            if (!StringUtils.isEmpty(paramDTO.getInviteCode())) {
                if (Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$").matcher(paramDTO.getInviteCode()).matches()) {//通过邀请人手机号注册
                    recommendUserId = userMapper.queryUserIdByPhone(paramDTO.getInviteCode());
                } else {//通过邀请码注册
                    recommendUserId = userInviteMapper.queryUserIdByInviteCode(paramDTO.getInviteCode().toLowerCase());
                }
            }
            if (StringUtils.isEmpty(recommendUserId)) {
                BaseLogger.error(String.format("好友注册送小金鱼失败：推荐人不存在，邀请码：%s", paramDTO.getInviteCode()));
                return;
            }

            //判断用户是否已绑卡(充值卡)
            boolean flag = userInviteMapper.queryUserIsBankCard(recommendUserId);
            if (flag == false) {
                BaseLogger.error(String.format("好友注册送小金鱼失败：推荐人未绑卡，推荐人ID：%s", recommendUserId));
                return;
            }

            int maxNum = userInviteMapper.queryDistributeGoldFishUserNum();
            long userNum = sequenceService.getNextLongValue("invite_register_user_num");
            if (userNum > maxNum) {
                BaseLogger.info(String.format("好友注册送小金鱼失败：已超过每日用户限制，当日注册顺序第：%s, 注册手机号：%s", userNum, phone));
                return;
            }

            String result = taskDetailsInfoDubboService.inviteRegisterTaskFinish(recommendUserId);
            BaseLogger.audit(String.format("推荐人ID：%s，好友注册送小金鱼结果：%s", recommendUserId, result));
        } catch (Exception e) {
            BaseLogger.error(e.getMessage());
            Map<String, Object> param = new HashMap<>();
            param.put("userId", recommendUserId);
            param.put("phone", phone);
            sendEmailService.sendAsyncEmail("WALLET_REGISTER_DISTRIBUTE_GOLDFISH_ERROR", param);
        }
    }

    //创建用户邀请信息失败时，不应该导致用户注册失败
    private void createUserInviteInfo(String userId, String phone, RegisterParamDTO paramDTO) {
        try {
            String recommendUserId = null;
            if (paramDTO != null) {
                if (!StringUtils.isEmpty(paramDTO.getInviteCode())) {
                    if (Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$").matcher(paramDTO.getInviteCode()).matches()) {//通过邀请人手机号注册
                        recommendUserId = userMapper.queryUserIdByPhone(paramDTO.getInviteCode());
                    } else {//通过邀请码注册
                        recommendUserId = userInviteMapper.queryUserIdByInviteCode(paramDTO.getInviteCode().toLowerCase());
                    }
                }
            }

            String myInviteCode = ShareCodeUtil.toSerialCode(Long.parseLong(phone));
            String myInviteUrl = getMyInviteUrl(myInviteCode);
            //邀请链接加了时间戳，没必要再将二维码入库，每次需要时临时生成
            //byte[] qrCodeBytes = QRCodeUtil.generateQRCode(myInviteUrl, QRCodeUtil.SIZE_30);
            //String qrCodeNo = uploadQRCode(myInviteCode + ".png", qrCodeBytes);

            userInviteMapper.createUserInviteInfo(userId, recommendUserId, myInviteCode, myInviteUrl, null, paramDTO != null ? paramDTO.getChannelNo() : null, paramDTO != null ? paramDTO.getChannelUserId() : null, paramDTO != null ? paramDTO.getActivityCode() : null);
        } catch (Exception e) {
            BaseLogger.error(e.getMessage(), e);
        }
    }

    private byte[] updateUserInviteInfo(MBUserInviteDTO dto, String userId, String phone) {
        String myInviteCode = StringUtils.isEmpty(dto.getInviteCode()) ? ShareCodeUtil.toSerialCode(Long.parseLong(phone)) : dto.getInviteCode();
        String myInviteUrl = StringUtils.isEmpty(dto.getInviteUrl()) ? getMyInviteUrl(myInviteCode) : dto.getInviteUrl();
        byte[] bytes = QRCodeUtil.generateQRCode(myInviteUrl, QRCodeUtil.SIZE_30);
        String qrCodeNo = uploadQRCode(myInviteCode + ".png", bytes);
        //更新用户邀请信息
        userInviteMapper.updateUserInviteInfo(userId, myInviteCode, myInviteUrl, qrCodeNo);
        return bytes;
    }

    private String getMyInviteUrl(String myInviteCode) {
        return inviteRegisterUrl + "?inviteCode=" + myInviteCode;
    }

    private String getMyInviteMarketUrl(String myInviteCode) {
        return inviteMarketUrl + "?inviteCode=" + myInviteCode;
    }

    private String uploadQRCode(String fileName, byte[] bytes) {
        String busNo = null;
        try {
            busNo = commonAttachmentOperate.saveAttachementAndReturnFileId(fileName, bytes, "inviteQRCode-pic");
        } catch (Exception e) {
            BaseLogger.error(e);
        }
        return busNo;
    }

    private byte[] downloadQRCode(String qrcodeNO) {
        byte[] bytes = null;
        try {
            return commonAttachmentOperate.downloadFile(qrcodeNO);
        } catch (Exception e) {
            BaseLogger.error(e);
        }
        return bytes;
    }

    private String getUserNoId(String sequenceName) {
        return "US" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + sequenceService.getNextStringValue(sequenceName, 10);
    }

    private String getAccountId(String sequenceName) {
        return "AC" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + sequenceService.getNextStringValue(sequenceName, 10);
    }

    private void checkPhoneIsRegister(String phone, String password) {
        ValidationMethodConstant.validateLoginInfo(phone, password);
        int queryUserCount = userMapper.queryUserCountByPhone(phone);
        if (queryUserCount != 0) {
            throw new AppException("此手机号码已经注册");
        }
    }

    public Boolean checkRegisterCode(String phone, String code, String bizType) {
        ValidationMethodConstant.validateCode(code);
        MessageDTO message = phoneMessageService.checkValidationCode(phone, code, bizType);
        if (!message.getSendFlag()) {
            throw new AppException(message.getSendFlagMessage());
        }
        return Boolean.TRUE;
    }

    @Override
    public Map<String, Object> queryInviteQRCodePic(String userId, String type) {
        Map<String, Object> result = new HashMap<>();
        byte[] bytes = null;
        MBUserInviteDTO inviteDto = userInviteMapper.queryUserInviteInfoByUserId(userId);
        String phone = userMapper.queryPhoneByUserId(userId);
        String myInviteCode = inviteDto != null ? (!StringUtils.isEmpty(inviteDto.getInviteCode()) ? inviteDto.getInviteCode() : ShareCodeUtil.toSerialCode(Long.parseLong(phone))) : ShareCodeUtil
                .toSerialCode(Long.parseLong(phone));
        if (inviteDto == null) {//不存在，则初始化用户邀请信息
            //myInviteCode = ShareCodeUtil.toSerialCode(Long.parseLong(phone));
            String existUserId = userInviteMapper.queryUserIdByInviteCode(myInviteCode);
            if (!StringUtils.isEmpty(existUserId)) {//避免重复邀请码
                myInviteCode += "1";
            }
            String myInviteUrl = getMyInviteUrl(myInviteCode);
            bytes = QRCodeUtil.generateQRCode(myInviteUrl + "&t=" + System.currentTimeMillis(), QRCodeUtil.SIZE_30);
            //String qrCodeNo = uploadQRCode(myInviteCode + ".png", bytes);

            userInviteMapper.createUserInviteInfo(userId, null, myInviteCode, myInviteUrl, null, null, null, null);
        } else {//用户邀请码为空，重新生成并上传至服务器
            String inviteUrlTmp = StringUtils.isEmpty(inviteDto.getInviteUrl()) ? getMyInviteUrl(myInviteCode) : inviteDto.getInviteUrl();
            bytes = QRCodeUtil.generateQRCode(inviteUrlTmp + "&t=" + System.currentTimeMillis(), QRCodeUtil.SIZE_30);
            //myInviteCode = inviteDto.getInviteCode();
        }
//        } else {
//            bytes = downloadQRCode(inviteDto.getQrcodePath());
//            if (bytes == null) {//避免文件服务器被清空
//                bytes = updateUserInviteInfo(inviteDto, userId, phone);
//            }
//        }
        if ("haoyoubang".equals(type)) {
            UserShareInfoDTO userShareInfoDTO = userInviteMapper.queryFriendHelpUserShareInfo();
            bytes = QRCodeUtil.generateQRCode(userShareInfoDTO.getShareLink() + "?inviteCode=" + myInviteCode + "&t=" + System.currentTimeMillis(), QRCodeUtil.SIZE_30);
            result.put("inviteQRCodePic", bytes);
        } else if ("market".equals(type)) {
            String myInviteUrl = getMyInviteMarketUrl(ShareCodeUtil.toSerialCode(Long.parseLong(phone)));
            bytes = QRCodeUtil.generateQRCode(myInviteUrl + "&t=" + System.currentTimeMillis(), QRCodeUtil.SIZE_30);
            result.put("inviteQRCodePic", bytes);
        } else {
            result.put("inviteQRCodePic", bytes);
        }
        result.put("format", "png");
        result.put("inviteCode", !StringUtils.isEmpty(myInviteCode) ? myInviteCode : ShareCodeUtil.toSerialCode(Long.parseLong(phone)));
        return result;
    }

    @Override
    public List<Map<String, Object>> queryRecommendUserList(String userId, Integer page) {
        try {
            return userInviteMapper.queryRecommendUserList(userId, page);
        } catch (Exception e) {
            BaseLogger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public Integer queryRecommendUserCount(String userId) {
        try {
            return userInviteMapper.queryRecommendUserCount(userId);
        } catch (Exception e) {
            BaseLogger.error(e.getMessage(), e);
            return 0;
        }
    }

    @Override
    public Map<String, Object> queryInviteQRCodePicByPhone(String phone, String channelNo) {
        Map<String, Object> map = new HashMap<>();
        map.put("url", inviteRegisterUrl + "?channelNo=" + channelNo + "&channelUserId=" + phone);
//        String userId = userMapper.queryUserIdByPhone(phone);
//        if (StringUtils.isEmpty(userId)) {
//            //通过手机号生成邀请二维码时，如果用户未注册，则提供一个默认的已注册用户
//            userId = userMapper.queryUserIdByPhone("13816703032");
//            if (StringUtils.isEmpty(userId)) {
//                throw new AppException("手机号未注册");
//            }
//        }
        return map;
    }

    @Override
    public UserShareInfoDTO queryWeiXinShareInfo(String userId, String type) {
        String phone = userMapper.queryPhoneByUserId(userId);
//        String inviteCode = ShareCodeUtil.toSerialCode(Long.parseLong(phone));
        UserShareInfoDTO shareInfoDTO = null;
        if ("haoyoubang".equals(type)) {
            shareInfoDTO = userInviteMapper.queryFriendHelpUserShareInfo();
        } else if ("market".equals(type)) {
            shareInfoDTO = userInviteMapper.queryMarketUserShareInfo();
        } else {
            shareInfoDTO = userInviteMapper.queryUserShareInfo();
        }
        if (StringUtils.isEmpty(shareInfoDTO.getShareTitle()) || StringUtils.isEmpty(shareInfoDTO.getShareDesc()) || StringUtils.isEmpty(shareInfoDTO.getShareLink())) {
            throw new AppException("获取微信分享参数异常，请联系运营人员");
        }
        shareInfoDTO.setShareLink(shareInfoDTO.getShareLink() + "?inviteCode=" + phone + "&t=" + System.currentTimeMillis());
        return shareInfoDTO;
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
    public Map<String, Object> queryMyRecommendUserInfo(String userId) {
        MBUserInviteDTO dto = userInviteMapper.queryUserInviteInfoByUserId(userId);
        Map<String, Object> result = new HashMap<>();
        if (dto != null && !StringUtils.isEmpty(dto.getRecommendUserId())) {
            try {
                result.put("realName", userInviteMapper.queryUserRealNameById(dto.getRecommendUserId()));
                result.put("userList", this.queryRecommendUserList(dto.getRecommendUserId(), 0));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public String sendSMSNoticeForHouseFundLoanIntention(String phone) {
        MessageDTO message = phoneMessageService.sendTextMessage(phone, "user_house_fund_loan_intention");
        if (!message.getSendFlag()) {
            throw new AppException(message.getSendFlagMessage());
        }
        return message.getVaildeTime();
    }

    @Override
    public Map<String, Object> registerByHouseFundLoanIntention(String phone, String code, RegisterParamDTO registerParamDTO, String name) {
        Map<String, Object> result = new HashMap<>();
        if (this.checkRegisterCode(phone, code, "user_house_fund_loan_intention")) {
            int queryUserCount = userMapper.queryUserCountByPhone(phone);
            Map msgParamMap = new HashMap();
            msgParamMap.put("link", userMapper.queryHouseFundLoanIntentionSMSLink());
            msgParamMap.put("name", StringUtils.isEmpty(name) ? "用户" : name);
            if (queryUserCount <= 0) {//用户未注册
                String userId = getUserNoId(ISequenceService.SEQ_NAME_USER_ID_SEQ);
                String pwd = this.getRandomPwd(6);
                String uuid = PKUtils.getPKID("UU");
                userMapper.createUserInfoWithUUID(userId, phone, new EncodeSHAUtils().sha512Encode(pwd), uuid);
                //	创建各种账户
                for (UserTradeAccountDTO userTradeAccountDTO : queryAccountList()) {
                    userMapper.createUserAccountInfo(userId, userTradeAccountDTO.getAccountId(), userTradeAccountDTO.getAccountType());
                }
                userMapper.addUserTransLockInfo(userId);
                //用户加载到VJ的渠道下
                batchUserMapper.saveChannelForWallet();

                createUserInviteInfo(userId, phone, registerParamDTO);

                msgParamMap.put("password", pwd);
                phoneMessageService.sendTextMessageByBizType(phone, "user_house_fund_loan_intention_remind_register", msgParamMap);
                result.put("userId", userId);
                result.put("uuid", uuid);
            } else {//用户已注册
                MBLoginUserDTO loginUserDTO = userMapper.queryUserInfoByPhone(phone);
                if (StringUtils.isEmpty(loginUserDTO.getUuid())) {
                    loginUserDTO.setUuid(PKUtils.getPKID("UU"));
                    userMapper.updateLoginUUIDByPhone(phone, loginUserDTO.getUuid());
                }
                result.put("userId", loginUserDTO.getUserId());
                result.put("uuid", loginUserDTO.getUuid());
                phoneMessageService.sendTextMessageByBizType(phone, "user_house_fund_loan_intention_remind", msgParamMap);
            }
        }
        return result;
    }

    public static String getRandomPwd(int length) { //length表示生成字符串的长度
        String base = "abcdefghijkmnpqrstuvwxyzABCDEFGHIJKLMNUVWXYZ23456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    @Override
    public List<String> queryRecommendUserListByActivityCode(String userId, String activityCode) {
        return userInviteMapper.queryRecommendUserListByActivityCode(userId, activityCode);
    }


    @Override
    public Map<String, Object> queryHLBInviteQRCodePic(String userId) {
        Map<String, Object> result = new HashMap<>();
        byte[] bytes = null;
        String phone = userMapper.queryPhoneByUserId(userId);
        String myInviteCode = ShareCodeUtil.toSerialCode(Long.parseLong(phone));

        UserShareInfoDTO userShareInfoDTO = userInviteMapper.queryFriendHelpUserShareInfo();
        bytes = QRCodeUtil.generateQRCode(userShareInfoDTO.getShareLink().replace("wxh5", "h5") + "?inviteCode=" + myInviteCode + "&t=" + System.currentTimeMillis(), QRCodeUtil.SIZE_30);
        result.put("inviteQRCodePic", bytes);
        result.put("format", "png");
        result.put("inviteCode", !StringUtils.isEmpty(myInviteCode) ? myInviteCode : ShareCodeUtil.toSerialCode(Long.parseLong(phone)));
        return result;
    }

    @Override
    public UserShareInfoDTO queryHLBWeiXinShareInfo(String userId) {
        String phone = userMapper.queryPhoneByUserId(userId);
        String inviteCode = ShareCodeUtil.toSerialCode(Long.parseLong(phone));
        UserShareInfoDTO shareInfoDTO = userInviteMapper.queryFriendHelpUserShareInfo();

        if (StringUtils.isEmpty(shareInfoDTO.getShareTitle()) || StringUtils.isEmpty(shareInfoDTO.getShareDesc()) || StringUtils.isEmpty(shareInfoDTO.getShareLink())) {
            throw new AppException("获取微信分享参数异常，请联系运营人员");
        }
        shareInfoDTO.setShareLink(shareInfoDTO.getShareLink().replace("wxh5", "h5") + "?inviteCode=" + inviteCode + "&t=" + System.currentTimeMillis());
        return shareInfoDTO;
    }
}
