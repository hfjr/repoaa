package com.zhuanyi.vjwealth.wallet.mobile.user.server.service.impl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.NumberUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fab.core.annotation.Remote;
import com.fab.core.exception.service.AppException;
import com.fab.core.logger.BaseLogger;
import com.fab.core.util.ControllerUtils;
import com.fab.server.util.Format;
import com.rqb.ips.depository.platform.faced.IQueryDirectBank;
import com.vj.vbus.event.dto.EventParam;
import com.vj.vbus.event.dto.MessageParam;
import com.vj.vbus.service.IEventInstanceService;
import com.zhuanyi.vjwealth.loan.housingFundLoan.webservice.IApplyHousingFundLoanDubboService;
import com.zhuanyi.vjwealth.wallet.mobile.account.server.dto.V1ApplyDTO;
import com.zhuanyi.vjwealth.wallet.mobile.account.server.mapper.IV1ApplyMapper;
import com.zhuanyi.vjwealth.wallet.mobile.account.server.mapper.MAccountQueryMapper;
import com.zhuanyi.vjwealth.wallet.mobile.account.server.mapper.VAccountQueryMapper;
import com.zhuanyi.vjwealth.wallet.mobile.account.server.util.AccountDefaultMapUtils;
import com.zhuanyi.vjwealth.wallet.mobile.account.server.util.FileOperateUtil;
import com.zhuanyi.vjwealth.wallet.mobile.common.server.service.IBusessinessNoService;
import com.zhuanyi.vjwealth.wallet.mobile.common.server.service.ISendExceptionEmailService;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.UploadFileInfoDTO;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.UploadIdentityPhotosDTO;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.UploadPicFileDTO;
import com.zhuanyi.vjwealth.wallet.mobile.user.constant.UserValidationMethodConstant;
import com.zhuanyi.vjwealth.wallet.mobile.user.dto.HelpCenterTypeDTO;
import com.zhuanyi.vjwealth.wallet.mobile.user.dto.HelpCenterTypeSubDetailDTO;
import com.zhuanyi.vjwealth.wallet.mobile.user.dto.SHQBLoginLogDTO;
import com.zhuanyi.vjwealth.wallet.mobile.user.server.integration.mapper.ISHQBLoginLogMapper;
import com.zhuanyi.vjwealth.wallet.mobile.user.server.integration.mapper.IUserQueryMapper;
import com.zhuanyi.vjwealth.wallet.mobile.user.server.integration.mapper.UserOperateMapper;
import com.zhuanyi.vjwealth.wallet.mobile.user.server.service.IUserOperationService;
import com.zhuanyi.vjwealth.wallet.mobile.user.util.AES;
import com.zhuanyi.vjwealth.wallet.mobile.user.util.CryptUtils;
import com.zhuanyi.vjwealth.wallet.mobile.user.util.ParamValidUtil;
import com.zhuanyi.vjwealth.wallet.service.file.upload.server.service.ICommonAttachmentOperate;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBLoginUserDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBRechargeDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBTransReturnDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.SHQBUserInfoDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.UserTradeAccountInfoDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.IMBUserAccountService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.IMBUserInviteService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.IMBUserOperationService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.IMBUserService;

@Service
public class UserOperationService implements IUserOperationService {

    /**
     * 图片上传文件大小2M
     */
    public static final int FileSize = 2 * 1024 * 1024;
    @Autowired
    private IUserQueryMapper userQueryMapper;

    @Autowired
    private VAccountQueryMapper accountInfoMapper;
    @Autowired
    private MAccountQueryMapper mAccountQueryMapper;

    @Autowired
    private IV1ApplyMapper v1ApplyMapper;

    @Remote
    private IMBUserService mbUserService;

    @Remote
    IMBUserInviteService mbUserInviteService;

    @Remote
    private IMBUserOperationService mBUserOperationService;

    @Remote
    private ICommonAttachmentOperate commonAttachmentOperate;

    @Autowired
    private UserOperateMapper userOperateMapper;

    @Remote
    private IMBUserAccountService mbUserAccountService;

    @Autowired
    private IBusessinessNoService busessinessNoService;

    @Autowired
    private ISendExceptionEmailService sendExceptionEmailService;

    @Autowired
    private ISHQBLoginLogMapper shqbLoginLogMapper;
    @Autowired
    private IEventInstanceService eventInstanceService;

    @Autowired
    private IApplyHousingFundLoanDubboService applyHousingFundLoanDubboService;

	@Autowired
	private IQueryDirectBank iQueryDirectBank;
	
    @Value("${file.headpictur.path}")
    private String fileBathPath;


    @Value("${file.types}")
    private String fileTypes;

    @Value("${shqb_md5_key}")
    private String md5Key;

    @Value("${shqb_aes_key}")
    private String aesKey;

    @Value("${event.limit.amount}")
    private String eventLimitAmount;
    
    public static final String JINGDONG_RESP_STRING = "resp=";

    @Override
    public Map<String, String> userLogin(String phone, String password) {
        UserValidationMethodConstant.validateLoginInfo(phone, password);
        Map<String, String> returnMap = new HashMap<String, String>();
        MBLoginUserDTO user = mbUserService.loginForApp(phone, password);
        returnMap.put("userId", user.getUserId());
        returnMap.put("uuid", user.getUuid());
        returnMap.put("inviteCode", user.getInviteCode());
        returnMap.put("ips", iQueryDirectBank.isIPS(user.getUserId())?"00":"10");
        return returnMap;
    }

    @Override
    public Map<String, String> sendRegisterSMSNotice(String phone, String password) {
        Map<String, String> returnMap = new HashMap<String, String>();
        returnMap.put("remainTime", mbUserService.sendRegisterSMSNotice(phone, password));
        return returnMap;
    }


    @Override
    public Map<String, String> sendRegisterToneNotice(String phone, String password) {
        Map<String, String> returnMap = new HashMap<String, String>();
        returnMap.put("remainTime", mbUserService.sendRegisterToneNotice(phone, password));
        return returnMap;
    }


    @Override
    public Map<String, String> register(String phone, String password, String code) {
        if (!mbUserService.register(phone, password, code))
            throw new AppException("注册繁忙,请稍后尝试");
        Map<String, String> resultMap = this.userLogin(phone, password);
        //add market event by tony tang 20160815
        try {
            eventInstanceService.addEventInstance("user_regist", String.valueOf(resultMap.get("userId")), null);
        } catch (Exception ex) {
            BaseLogger.error("增加营销事件信息异常!", ex);
        }
        return resultMap;
    }


    @Override
    public Map<String, String> updateForgetPassword(String phone, String password, String code) {
        if (!mbUserService.updateForgetPassword(phone, password, code))
            throw new AppException("系统繁忙,请稍后再试");
        return this.userLogin(phone, password);
    }


    @Override
    public Map<String, String> sendForgetToneNotice(String phone, String password) {
        Map<String, String> returnMap = new HashMap<String, String>();
        returnMap.put("remainTime", mbUserService.sendForgetToneNotice(phone, password));
        return returnMap;
    }


    @Override
    public Map<String, String> sendForgetSMSNotice(String phone, String password) {
        Map<String, String> returnMap = new HashMap<String, String>();
        returnMap.put("remainTime", mbUserService.sendForgetSMSNotice(phone, password));
        return returnMap;
    }


    @Override
    public Map<String, String> sendActivateToneNotice(String phone, String password) {
        Map<String, String> returnMap = new HashMap<String, String>();
        returnMap.put("remainTime", mbUserService.sendActivateToneNotice(phone, password));
        return returnMap;
    }


    @Override
    public Map<String, String> sendActivateSMSNotice(String phone, String password) {
        Map<String, String> returnMap = new HashMap<String, String>();
        returnMap.put("remainTime", mbUserService.sendActivateSMSNotice(phone, password));
        return returnMap;
    }


    @Override
    public Map<String, String> updateActivateUserInfo(String phone, String password, String code) {
        if (!mbUserService.updateActivateUserInfo(phone, password, code))
            throw new AppException("系统繁忙,请稍后再试");
        Map<String, String> resultMap = this.userLogin(phone, password);
        //add market event by cuidezhong tang 20160926
        try {
            eventInstanceService.addEventInstance("user_account_activate", String.valueOf(resultMap.get("userId")), null);
        } catch (Exception ex) {
            BaseLogger.error("增加营销事件信息异常!", ex);
        }
        return resultMap;
    }


    @Override
    public Map<String, String> updateUserPassword(String userId, String oldPassword, String newPassword) {
        if (!mBUserOperationService.updateUserPassword(userId, oldPassword, newPassword)) {
            throw new AppException("系统繁忙,请稍后再试");
        }
        String phone = userOperateMapper.queryUserPhoneByUserId(userId);//查询phone
        return this.userLogin(phone, newPassword);
    }


    @Override
    public Map<String, String> saveUserFeedback(String userId, String advice) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            userOperateMapper.saveUserFeedback(userId, advice);
            map.put("tip", "感谢您的意见反馈");
        } catch (Exception e) {
            throw new AppException("系统繁忙,请稍后再试");
        }

        return map;
    }


    @Override
    public Map<String, String> queryUserIsPurchaseV(String userId) {
        //没有购买记录
        if (userOperateMapper.countUserPurchaseV1(userId) < 1) {
            return AccountDefaultMapUtils.getUserIsPurchaseVMap();
        }
        //有购买记录
        Map<String, String> returnMap = new HashMap<String, String>();
        returnMap.put("isPurchaseV1Flag", "yes");
        return returnMap;
    }


    @Override
    public Map<String, Object> queryUserDefaultInfo(String userId) {

        Map<String, Object> returnMap = new HashMap<String, Object>();
        Map<String, String> res = userOperateMapper.queryUserDefaultInfo(userId);
        //用户首次绑卡
        if (res == null) {
            returnMap.put("isBandCard", "no");
            returnMap.put("card", new HashMap<String, String>());
            returnMap.put("tipContent", "首次绑卡,身份信息将不可更改");
            return returnMap;
        }

        returnMap.put("tipContent", "");
        returnMap.put("isBandCard", "yes");
        returnMap.put("card", res);

        return returnMap;
    }


    @Override
    public boolean validatorUserIdAndUuid(String userId, String uuid) {
        String phone = userOperateMapper.queryUserPhoneByUserId(userId);//查询phone
        try {
            return mbUserService.checkLoginForApp(phone, uuid);
        } catch (Exception e) {
        	BaseLogger.error(e);
            return false;
        }
    }


    @Override
    public MBTransReturnDTO applyMaToEa(String userId, String money) {
        //        return mbUserAccountService.applyMaToEa(userId, money);
        return mbUserAccountService.applyMaToTa(userId, money);
    }

    @Override
    public MBTransReturnDTO applyMaToTa(String userId, String money) {
        return mbUserAccountService.applyMaToTa(userId, money);
    }

    @Override
    public MBTransReturnDTO transferEaToMa(String userId, String money) {
        //return mbUserAccountService.transferEaToMa(userId, money);
        return mbUserAccountService.transferTaToMa(userId, money);
    }

    @Override
    public MBTransReturnDTO transferTaToMa(String userId, String money) {
        return mbUserAccountService.transferTaToMa(userId, money);
    }

    @Override
    public MBTransReturnDTO withdrawEa(String userId, String money) {
        //return mbUserAccountService.withdrawEaToBank(userId, money);
        return mbUserAccountService.withdrawTaToBank(userId, money);
    }

    @Override
    public MBTransReturnDTO withdrawTa(String userId, String money) {
        return mbUserAccountService.withdrawTaToBank(userId, money);
    }

    @Override
    public MBTransReturnDTO transferV1ToMa(String userId, String money) {
        return mbUserAccountService.transferV1ToMa(userId, money);
    }

    @Override
    public MBTransReturnDTO applyMaToV1(String userId, String money) {
        // 校验参数合法性
        BigDecimal remainAmount = confirmUserIsExitAndMoneyIsValid(userId, money);
        // 申购前真实份额确认
        this.checkApplyV1ValidateBefore(userId, remainAmount);
        MBTransReturnDTO transReturnDTO = mbUserAccountService.applyMaToV1(userId, money);
        // 申购后更新份额
        this.checkApplyV1ValidateAfter(userId, remainAmount);
        return transReturnDTO;
    }


    public void checkApplyV1ValidateBefore(String userId, BigDecimal remainAmount) {
        //校验真实份额是否异常
        this.checkRealReaminAmountValidate(userId);
        //校验用户真实可购买的份额
        this.checkApplyMaToV1LimitByUserId(userId, remainAmount);
    }


    /**
     * 校验该用户可申购份额
     *
     * @param userId
     * @return
     */
    public void checkApplyMaToV1LimitByUserId(String userId, BigDecimal targetValue) {
        //主账户可用余额
        BigDecimal investmentAmount = mAccountQueryMapper.queryMAccountInvestmentAmount(userId);

        //实际可申购份额
        BigDecimal infaceCanApply = investmentAmount;

        //最小投资额
        BigDecimal minApplyB = accountInfoMapper.queryVAccountMinApplyAmount();


        // 查询个人V理财剩余可购买份额
        BigDecimal canApplyRemainAmountBigDecimal = accountInfoMapper.queryVAccountCanApplyRemainAmount(userId);


        // 1. 个人实际可购买份额 ( 应当小于个人V理财剩余可购买额度)
        if (investmentAmount.compareTo(canApplyRemainAmountBigDecimal) > 0) {
            infaceCanApply = canApplyRemainAmountBigDecimal;
        }


        // 2. 个人实际可购买份额 (应当大于起投金额)
        if (infaceCanApply.compareTo(minApplyB) < 0)
            throw new AppException("已无可申购份额,下次再来吧");

        // 3. 用户购买份额(应当大于起投金额)
        if (targetValue.compareTo(minApplyB) < 0) {
            throw new AppException(Format.getBigDecimalFormat("##0.00").format(minApplyB) + "元起投");
        }

        // 4. 用户购买份额(应当小于真实可购买份额)
        if (targetValue.compareTo(infaceCanApply) > 0) {
            throw new AppException("本次最多可申购" + Format.getBigDecimalFormat("##0.00").format(infaceCanApply) + "元");
        }


    }

    //获取v1理财mock参数
    private V1ApplyDTO getV1RealApplyParamInfo() {
        return v1ApplyMapper.queryV1RealApplyParamInfo();
    }

    //获取最小限额
    private BigDecimal getMinApply() {
        return accountInfoMapper.queryVAccountMinApplyAmount();
    }


    //申购后校验并减少份额
    public void checkApplyV1ValidateAfter(String userId, BigDecimal realRemainAmount) {
        //减少份额 (先减少份额,在检查金额)
        v1ApplyMapper.subtractionRealRemainAmount(realRemainAmount.doubleValue());
        //再次检查,避免并发
        this.checkRealReaminAmountValidate(userId);
    }


    //校验真实份额合法性
    public void checkRealReaminAmountValidate(String userId) {
        V1ApplyDTO v1Apply = getV1RealApplyParamInfo();
        //真实份额
        BigDecimal V1RealApplyRemainAmount = v1Apply.getV1RealApplyRemainAmount();
        BigDecimal applyThresholdLimit = v1Apply.getApplyThresholdLimit();

        //校验真实份额合法性
        if (V1RealApplyRemainAmount.compareTo(getMinApply()) < 0) {
            // 更新真实份额为0
            v1ApplyMapper.updateRealRemainAmountToZero();
            //更新mock值为0
            v1ApplyMapper.updateMockRemainAmountToZero();
            //发送邮件
            if (V1RealApplyRemainAmount.doubleValue() < 0)
                sendExceptionEmailService.sendUserExceptionEmail(userId, "真实份额为负数[V1RealApplyRemainAmount]:" + V1RealApplyRemainAmount, this.getClass(), "checkRealReaminAmountValidate");
            return;
        }

        //真实剩余份额小于阀值
        if (V1RealApplyRemainAmount.compareTo(applyThresholdLimit) < 0) {
            //更新mock值为0
            v1ApplyMapper.updateMockRemainAmountToZero();
        }

    }

    /**
     * 校验交易金额参数是否合法
     *
     * @param userId
     * @param money
     */
    private BigDecimal confirmUserIsExitAndMoneyIsValid(String userId, String money) {
        if (StringUtils.isBlank(userId)) {
            throw new AppException(601, "请重新登录");
        }
        if (StringUtils.isBlank(money)) {
            throw new AppException("金额不能为空");
        }

        if (!NumberUtils.isNumber(money) || new BigDecimal(money).compareTo(new BigDecimal(0)) <= 0) {
            throw new AppException("金额不合法");
        }

        return new BigDecimal(money).setScale(2, BigDecimal.ROUND_FLOOR);
    }


    @Override
    public Object withdrawMa(String userId,String source, String money) {
    	
        return  mbUserAccountService.withdrawMaToBank(userId, source,money);
    }


    /**
     * 已经绑过卡的充值
     */
    @Override
    public Map<String, Object> doRechargeToMaAlreadyBind(String userId, String cardId, String orderNo, String amount, String code) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("result", mbUserAccountService.doRechargeToMaAlreadyBind(userId, cardId, orderNo, amount, code) == true ? "true" : "false");
        return map;
    }

    /**
     * 已经绑过卡的发送验证码
     */
    @Override
    public MBRechargeDTO rechargeToMaAlreadyBindCardSendCode(String userId, String cardId, String amount) {

        return mbUserAccountService.rechargeToMaAlreadyBindCardSendCode(userId, cardId, amount);
    }

    /**
     * 已经绑过卡的发送验证码V3.2
     */
    @Override
    public MBRechargeDTO rechargeToMaAlreadyBindCardSendCodeV32(String userId, String cardId, String amount) {
        MBRechargeDTO mBRechargeDTO = mbUserAccountService.rechargeToMaAlreadyBindCardSendCodeV32(userId, cardId, amount);
        return mBRechargeDTO;
    }


    /**
     * 未绑卡的,发送验证码
     */
    @Override
    public MBRechargeDTO rechargeToMaSendCode(MBRechargeDTO dto) {
        this.checkCountRegisterCard(dto.getUserId());
        return mbUserAccountService.rechargeToMaSendCode(dto);
    }

    /**
     * 未绑卡的,发送验证码V3.2
     */
    @Override
    public MBRechargeDTO rechargeToMaSendCodeV32(MBRechargeDTO dto) {
        this.checkCountRegisterCard(dto.getUserId());
        MBRechargeDTO mBRechargeDTO = mbUserAccountService.rechargeToMaSendCodeV32(dto);
        return mBRechargeDTO;
    }

    /**
     * 未绑卡的,发送验证码V3.6
     */
    @Override
    public MBRechargeDTO bindCardSendCode(MBRechargeDTO dto) {
        this.checkCountRegisterCard(dto.getUserId());
        MBRechargeDTO mBRechargeDTO = mbUserAccountService.bindCardSendCode(dto);
        return mBRechargeDTO;
    }

    private void checkCountRegisterCard(String userId) {
        if (userOperateMapper.checkCountRegisterCard().equals("open")) {
            //注册用户只允许绑定一张卡
            if (userOperateMapper.countRegisterCard(userId) > 0) {
                throw new AppException("抱歉,每个用户只能绑定一张银行卡");
            }
        }
    }

    /**
     * 未绑卡的,充值
     */
    @Override
    public Map<String, Object> doRechargeToMa(MBRechargeDTO dto) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("result", mbUserAccountService.doRechargeToMa(dto) == true ? "true" : "false");
        return map;
    }

    /**
     * 绑卡验证码确认V3.6
     *
     * @param dto
     * @return
     */
    @Override
    public Map<String, Object> confirmBindCardSendCode(MBRechargeDTO dto) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("result", mbUserAccountService.confirmBindCardSendCode(dto) == true ? "true" : "false");
        return map;
    }

    public List<Map<String, Object>> queryUserTotolAccountAmountReport(String userId) {
        return userOperateMapper.queryUserTotolAccountAmountReportData(userId);
    }

    public List<Map<String, Object>> queryUserTotolAccountAmountReportForV30(String userId) {

        return userOperateMapper.queryUserTotolAccountAmountReportDataForV30(userId, userOperateMapper.countUserV1ExitForV30(userId) > 0);

    }

    @Override
    public void userHaveReadMessage(String userId, String messageId) {
        userOperateMapper.updateUserMessageHaveRead(userId, messageId);
    }


    @Override
    public Object getServiceHotline() {
        String hotline = userOperateMapper.queryServiceHotLine();
        if (StringUtils.isBlank(hotline)) {
            BaseLogger.audit("服务热线为空,请在wj_mobile_variable_info添加变量");
            return new HashMap<String, String>();
        }
        return JSONObject.parseObject(hotline);
    }

    @Override
    public Map<String, String> queryIsFirstBindCard(String userId) {
        Map<String, String> returnMap = new HashMap<String, String>();
        Map<String, String> withdrawCard = userQueryMapper.queryUserSecurityCardInfo(userId);
        String flag = "no";
        String tip = "";
        if (withdrawCard == null) {
            flag = "yes";
            tip = "第一次绑卡,该卡将成为您的安全卡和充值卡";
        }
        returnMap.put("flag", flag);
        returnMap.put("tip", tip);
        return returnMap;
    }


    @Override
    public Map<String, String> queryV1AgreementUserInfo(String userId) {
        Map<String, String> returnMap = new HashMap<String, String>();
        Map<String, String> userInfo = userOperateMapper.queryV1AgreementUserInfo(userId);
        if (userInfo == null)
            throw new AppException("系统异常,请稍后再试");
        returnMap.putAll(userInfo);
        returnMap.put("agreementDate", Format.dateToString(new Date(), "yyyy-MM-dd"));
        returnMap.put("agreementNo", "CON" + userId.substring(2));
        return returnMap;
    }

    //上传头像
    @Override
    public String headPicturUpload(String userId, MultipartFile mf) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        String ctxPath = fileBathPath;
        // 原始文件名
        String filleOriginalName = mf.getOriginalFilename();
        String businessNo = busessinessNoService.getVjFileId();
        //进行特殊字符过滤
        filleOriginalName = filleOriginalName.replaceAll(" ", "");
        String rename = FileOperateUtil.rename(filleOriginalName);
        // 文件类型
        String fileType = " ";
        if (filleOriginalName.contains(".")) {
            fileType = filleOriginalName.substring(filleOriginalName.lastIndexOf("."));
        }
        String relativeFilePath = businessNo + "/";
        //只存相对路径

        paramMap.put("businessNo", businessNo);
        paramMap.put("bizType", "用户头像");
        paramMap.put("relativeFilePath", relativeFilePath);
        paramMap.put("filleOriginalName", filleOriginalName);
        paramMap.put("fileRename", rename);
        paramMap.put("fileType", fileType);

        File file = new File(ctxPath + businessNo);
        if (!file.exists()) {
            file.mkdirs();
        }
        // 完整路径 = /carloan/file/编号(application_contract_no)/文件重命名
        String fullPath = ctxPath + businessNo + "/" + rename;
        File uploadFile = new File(fullPath);
        try {
            //保持文件到服务器
            FileCopyUtils.copy(mf.getBytes(), uploadFile);
            //保持文件到数据库
            userOperateMapper.saveHeadPicture(paramMap);
            //更新用户的头像ID
            userOperateMapper.updateUserHeadPicture(userId, businessNo);
        } catch (IOException e) {
            BaseLogger.error("文件头像上传保存失败");
            return "fail";
        }
        return "success";
    }


    //获取头像信息
    @Override
    public Map<String, String> queryHeadPicturById(String userId) {
        Map<String, String> map = userOperateMapper.queryHeadPicturById(userId);
//		if(map==null){
//			sendExceptionEmailService.sendUserExceptionEmail(userId, "头像文件信息不存在",this.getClass(),"queryHeadPicturById");
//			throw new AppException("系统繁忙,请稍后再试");
//		}
        return map;
    }

    @Override
    public void deleteUserMessage(String[] messageIds) throws Exception {
        for (String messageId : messageIds) {
            userOperateMapper.deleteUserMessage(messageId);
        }

    }

    /**
     * 安卓手机设备日志上传
     */

    public Map<String, String> uploadDeviceLog(String userId, MultipartFile value) {
        Map<String, String> returnMap = new HashMap<String, String>();
        Map<String, String> paramMaps = new HashMap<String, String>();

        String fileId = "";
        //TODO 调用文件上传接口,并返回文件的fileId
        if (value != null) {
            try {
                fileId = commonAttachmentOperate.saveAttachementAndReturnFileId(value.getOriginalFilename(), value.getBytes(), "phoneLogFile");
            } catch (IOException e) {
                BaseLogger.error("上传手机日志失败" + e);
                throw new AppException("上传手机日志失败");
            }
        }
        paramMaps.put("userId", userId);
        paramMaps.put("fileId", fileId);

        //保存日志信息
        userOperateMapper.saveUserDeviceLog(paramMaps);

        returnMap.put("result", "success");
        return returnMap;
    }

    public String payNotice(Map<String, Object> map) {
        return mbUserAccountService.payNotice(map);
    }


    //上传图片
    public String uploadPic(String fileName, byte[] fileBytes) throws IOException {
        String fileNo = commonAttachmentOperate.saveAttachementAndReturnFileId(fileName, fileBytes, "identity-pic");
        return fileNo;
    }

    @Override
    public Object uploadPicFile(MultipartFile file) throws IOException {
        UploadPicFileDTO uploadPicFileDTO = new UploadPicFileDTO();
        // 判断文件是否为空
        if (null != file && !file.isEmpty()) {
            //判断文件类型 ,过滤文件
            String[] types = StringUtils.split(fileTypes, ",");
            //验证文件格式
            if (!FilenameUtils.isExtension(file.getOriginalFilename(), types)) {
                BaseLogger.audit("上传文件格式不对");
                uploadPicFileDTO.setCode("200402");
                uploadPicFileDTO.setMessage("上传文件格式不对");
                uploadPicFileDTO.setFileId("");
                uploadPicFileDTO.setFileNameCode("");
                return uploadPicFileDTO;
            }
            byte[] bytes = file.getBytes();
            //TODO 限制文件上传大小为2M
            //2M = 2*1024kb =2*1024*1024Byte
            if (file.getSize() > FileSize) {
                uploadPicFileDTO.setCode("200401");
                uploadPicFileDTO.setMessage("上传文件过大");
                uploadPicFileDTO.setFileId("");
                uploadPicFileDTO.setFileNameCode("");
                return uploadPicFileDTO;
            }

            String fileId = this.uploadPic(file.getOriginalFilename(), bytes);
            String fileNameCode = file.getOriginalFilename();

            uploadPicFileDTO.setCode("200400");
            uploadPicFileDTO.setMessage("上传成功");
            uploadPicFileDTO.setFileId(fileId);
            uploadPicFileDTO.setFileNameCode(fileNameCode);
        } else {
            uploadPicFileDTO.setCode("200401");
            uploadPicFileDTO.setMessage("上传文件为空");
            uploadPicFileDTO.setFileId("");
            uploadPicFileDTO.setFileNameCode("");
        }
        return uploadPicFileDTO;
    }

    //加载图片
    public byte[] loadFile(String fileNo) {
        return commonAttachmentOperate.downloadFile(fileNo);
    }


    @Transactional
    public UploadIdentityPhotosDTO uploadIdentityPhotos(String userId, MultipartFile rightSideFile, MultipartFile reverseSideFile, String uploadSuccessFileId) throws IOException {


        UploadIdentityPhotosDTO uploadIdentityPhotosDTO = new UploadIdentityPhotosDTO();
        List<UploadFileInfoDTO> fileList = new ArrayList<UploadFileInfoDTO>();

        //参数验证
        if (StringUtils.isBlank(userId)) {
            BaseLogger.audit("上传身份证，用户ID不能为空");
            throw new AppException("上传身份证，用户ID不能为空");
        }

        //判断文件类型 ,过滤文件
        String[] types = StringUtils.split(fileTypes, ",");
        for (MultipartFile file : new MultipartFile[]{rightSideFile, reverseSideFile}) {
            UploadFileInfoDTO uploadFileInfoDTO = new UploadFileInfoDTO();
            if (null != file && !file.isEmpty()) {
                //验证文件格式
                if (FilenameUtils.isExtension(file.getOriginalFilename(), types)) {
                    byte[] bytes = file.getBytes();
                    String fileId = this.uploadPic(file.getOriginalFilename(), bytes);
                    String fileNameCode = FilenameUtils.getBaseName(file.getOriginalFilename());
                    uploadFileInfoDTO.setFileId(fileId);
                    uploadFileInfoDTO.setFileNameCode(fileNameCode);
                    fileList.add(uploadFileInfoDTO);
                } else {
                    BaseLogger.audit("上传文件格式不对");
                }
            }
        }
        uploadIdentityPhotosDTO.setFileList(fileList);

        if ("0".equals(fileList.size())) {
            uploadIdentityPhotosDTO.setCode("200401");
            uploadIdentityPhotosDTO.setMessage("上传文件失败");
            BaseLogger.audit("上传文件失败");
        } else {
            uploadIdentityPhotosDTO.setCode("200400");
            uploadIdentityPhotosDTO.setMessage("上传成功");
            BaseLogger.audit("上传成功");
            if ("2".equals(fileList.size())) {


            } else if ("1".equals(fileList.size())) {

                if (StringUtils.isBlank(uploadSuccessFileId)) {
                    BaseLogger.audit("上传身份证，回传成功文件ID不能为空");
                    throw new AppException("上传身份证，回传成功文件ID不能为空");
                } else {
                    if ("200410".equals(uploadIdentityPhotosDTO.getFileList().get(0).getFileNameCode())) {
                        //正面
                    } else {
                        //反面  uploadSuccessFileId
                    }
                }
            }

            //TODO 更新用户身份证认证标识 认证表 wj_user_certification
            //清除认证记录，再保存记录

        }

        return uploadIdentityPhotosDTO;
    }


    public Boolean hasAppointedFinancial(String userId) {

        return userOperateMapper.countUserAppiontFinancial(userId) > 0;
    }


    public void appointmentFinancial(String userId) {
        int count = userOperateMapper.updatesAppointFinancialFlag(userId);
        if (count == 0) {
            userOperateMapper.addIsAppointFinancialFlag(userId);
        }
    }

    @Override
    public String yeePayNotice(String data, String encryptkey) {
        return mbUserAccountService.yeePayNotice(data, encryptkey);
    }

    @Override
    public Map<String, String> queryRechargeNotice(String userId, String orderId) {
        Map<String, String> resultMap = mbUserAccountService.queryRechargeNotice(userId, orderId);
        //add market event by tony tang 20160815
        if ("recharge_ma_confirm".equals(resultMap.get("code"))) {
            BaseLogger.audit("queryRechargeNotice-->recharge_ma_confirm--in,price:" + resultMap.get("price"));
            //获取充值金额
            if (!StringUtils.isEmpty(resultMap.get("price"))) {
                BigDecimal bigDecimal = new BigDecimal(resultMap.get("price"));
                if (new BigDecimal(eventLimitAmount).compareTo(bigDecimal) <= 0) {
                    MessageParam messageParam = new MessageParam();
                    EventParam eventParam = new EventParam();
                    try {
                        eventParam.setRechargeAmount(bigDecimal.toPlainString());
                        messageParam.setEventParam(eventParam);
                        eventInstanceService.addEventInstance("user_recharge", userId, messageParam);
                    } catch (Exception ex) {
                        BaseLogger.error("充值增加营销事件信息异常!", ex);
                    }
                }
            }
        }
        return resultMap;
    }

    @Override
    public List<HelpCenterTypeDTO> getHelpCenterType() {
        return userOperateMapper.getHelpCenterType();
    }

    @Override
    public HelpCenterTypeSubDetailDTO getHelpCenterTypeSubDetail(String detailId) {
        return userOperateMapper.getHelpCenterTypeSubDetail(detailId);
    }

    @Override
    public String userLoginForSHQB(String paramStr) {
        SHQBLoginLogDTO loginLogDTO = new SHQBLoginLogDTO();
        loginLogDTO.setInParamEncrypt(paramStr);
        try {
            String paramJson = "";
            try {
                paramJson = AES.decryptString1(paramStr, aesKey);
            } catch (Exception e) {
                updateLoginLog(loginLogDTO, "解密异常");
                shqbLoginLogMapper.insertLoginLog(loginLogDTO);
                return loginLogDTO.getOutParamEncrypt();
            }
            JSONObject jsonObject = JSON.parseObject(paramJson);

            loginLogDTO.setInParam(jsonObject.toJSONString());

            String phone = jsonObject.get("phone") + "";
            SHQBUserInfoDTO paramDto = JSON.parseObject(jsonObject.get("userInfo") + "", SHQBUserInfoDTO.class);
            paramDto.setPhone(phone);

            //入参校验
            try {
                UserValidationMethodConstant.validatePhone(phone);
                ParamValidUtil.validatorRealName(paramDto.getRealName());
                ParamValidUtil.validatorChannelType(paramDto.getBankCardNo());
                ParamValidUtil.validatorIdentityNo(paramDto.getIdentityNo());
            } catch (Exception e) {
                updateLoginLog(loginLogDTO, e.getMessage());
                shqbLoginLogMapper.insertLoginLog(loginLogDTO);
                return loginLogDTO.getOutParamEncrypt();
            }

            //替换转义字符问题，避免签名校验失败
            String sign = jsonObject.get("sign") + "";
            //ios传递的json字符串里会有换行符、空格
            String userInfoStr = (jsonObject.get("userInfo") + "").replace("\n", "").replace(" ", "").replace("  ", "");
            if (!sign.equals(CryptUtils.encryptToMD5("phone=" + phone + "userInfo=" + userInfoStr + md5Key))) {
                updateLoginLog(loginLogDTO, "签名错误");
                shqbLoginLogMapper.insertLoginLog(loginLogDTO);
                return loginLogDTO.getOutParamEncrypt();
            }

            Map<String, String> returnMap = new HashMap<String, String>();
            MBLoginUserDTO user = mbUserService.loginForSHQB(phone, paramDto);
            returnMap.put("userId", user.getUserId());
            returnMap.put("uuid", user.getUuid());
            returnMap.put("isSign", user.getSign().equals("1") ? "yes" : "no");
            returnMap.put("ips", iQueryDirectBank.isIPS(user.getUserId())?"00":"10");
            
            String returnJson = JSON.toJSONString(ControllerUtils.getSuccessfulResponse(returnMap));
            String returnEncryptStr = AES.encryptString1(returnJson, aesKey);
            loginLogDTO.setOutParam(returnJson);
            loginLogDTO.setOutParamEncrypt(returnEncryptStr);
            shqbLoginLogMapper.insertLoginLog(loginLogDTO);

            return returnEncryptStr;
        } catch (Exception e) {
            if (e instanceof AppException) {
                updateLoginLog(loginLogDTO, e.getMessage());
            } else {
                updateLoginLog(loginLogDTO, "服务异常");
            }
            shqbLoginLogMapper.insertLoginLog(loginLogDTO);
            return loginLogDTO.getOutParamEncrypt();
        }
    }
    /*
    @Override
    public void saveUserAppDevice(String userId,String deviceId,String deviceType) throws Exception {
    	String status = userOperateMapper.getUserAppDeviceStatus(userId, deviceId, deviceType);
    	if(StringUtils.isEmpty(status)) {
    		userOperateMapper.saveUserAppDevice(userId, deviceId, deviceType);
    		userOperateMapper.updateUserAppDeviceInvalid(userId, deviceId, deviceType);
    	} else if("01".equals(status)) {
    		userOperateMapper.updateUserAppDeviceEffective(userId, deviceId, deviceType);
    		userOperateMapper.updateUserAppDeviceInvalid(userId, deviceId, deviceType);
    	}
    }*/

    private void updateLoginLog(SHQBLoginLogDTO loginLogDTO, String errMsg) {
        String returnJson = JSON.toJSONString(ControllerUtils.getErrorResponseWithMessageAndObject(errMsg, null));
        String returnEncryptStr = AES.encryptString1(returnJson, aesKey);
        loginLogDTO.setOutParam(returnJson);
        loginLogDTO.setOutParamEncrypt(returnEncryptStr);
    }

    @Override
    public void saveUserAppDevice(String userId, String deviceId, String deviceType, String otherData) throws Exception {
        String status = userOperateMapper.getUserAppDeviceStatus(userId, deviceId, deviceType);
        if (StringUtils.isEmpty(status)) {
            userOperateMapper.saveUserAppDevice(userId, deviceId, deviceType, otherData);
            userOperateMapper.updateUserAppDeviceInvalid(userId, deviceId, deviceType);
        } else if ("01".equals(status)) {
            userOperateMapper.updateUserAppDeviceEffective(userId, deviceId, deviceType, otherData);
            userOperateMapper.updateUserAppDeviceInvalid(userId, deviceId, deviceType);
        }
    }

    //根据传入的policyNo,去产品表查询相应大保单图片绝对路径
    @Override
    public String queryPolicyPicUrlByPolicyNo(String policyNo) {
        return userQueryMapper.queryPolicyPicUrlByPolicyNo(policyNo);
    }

    @Override
    public Map<String, String> sendHouseFundLoanIntentionSMSNotice(String phone) {
        ParamValidUtil.validatorPhone(phone);
//        if (applyHousingFundLoanDubboService.existUserHouseFundIntentionInfo(phone)) {//用户已申请过
//            throw new AppException("您的借款申请已经提交，请勿重复提交！");
//        }

        if (userQueryMapper.existUserByPhone(phone)) {
            throw new AppException(601, "您已是融桥宝用户|请直接登录申请");
        }

        Map<String, String> returnMap = new HashMap<String, String>();
        returnMap.put("remainTime", mbUserInviteService.sendSMSNoticeForHouseFundLoanIntention(phone));
        return returnMap;
    }

    @Override
    public Map<String, String> sendHouseFundLoanIntentionSMSNoticeV2(String phone) {
        ParamValidUtil.validatorPhone(phone);
        Map<String, String> returnMap = new HashMap<String, String>();
        returnMap.put("remainTime", mbUserInviteService.sendSMSNoticeForHouseFundLoanIntention(phone));
        return returnMap;
    }

	@Override
	public String jingdongNotice(String resp) {
		//1.处理字符串
		resp = JINGDONG_RESP_STRING+resp;
		
		//2.调用mainbiz
		return mbUserAccountService.jingdongNotice(resp);
	}

    @Override
    public String withdrawTaForTjsOffTheShelf() {
        Executors.newFixedThreadPool(1).execute(new Runnable() {
            public void run() {
                runWithdrawTa();
            }
        });
        return "run ..";
    }

    public  void  runWithdrawTa(){
        List<UserTradeAccountInfoDTO> taInfoList=mbUserAccountService.queryUserIdAndAvailableAmountList("ta","0");
        if(taInfoList==null||taInfoList.size()==0){
            throw new AppException("无需要提现的用户!");
        }
        int failCount=0;
        List<UserTradeAccountInfoDTO> eventList=new ArrayList<UserTradeAccountInfoDTO>();
        for(UserTradeAccountInfoDTO userInfo:taInfoList){
            try {
                BaseLogger.audit("ta余额转出至银行卡:userId:"+userInfo.getUserId()+" availableAmount:"+userInfo.getAvailableAmount());
                mbUserAccountService.withdrawTaToBank(userInfo.getUserId(), userInfo.getAvailableAmount());
                BaseLogger.audit("ta余额转出至银行卡成功:userId:"+userInfo.getUserId());
                eventList.add(userInfo);
                //交易中心接口需要30秒等待时间
                Thread.sleep(30000);
            }catch (Exception ex){
                failCount++;
                ex.printStackTrace();
                BaseLogger.error("ta余额转出至银行卡异常:userId:"+userInfo.getUserId());
            }
        }
        BaseLogger.error("ta余额转出至银行结束,总数:"+taInfoList.size()+" 失败记录数:"+failCount);
        addBathEventMapInstance(eventList);
    }
    /**
     * 推送营销事件
     * @param messageEvents
     */
    public void addBathEventMapInstance(List<UserTradeAccountInfoDTO> messageEvents) {
        if(messageEvents==null||messageEvents.isEmpty()){
            return;
        }
        List<Map<String, Object>> eventInstParams=new ArrayList<Map<String, Object>>();
        for(UserTradeAccountInfoDTO messageEvent:messageEvents) {
            Map<String, Object> eventParam=new HashMap<String, Object>();
            eventParam.put("userId",messageEvent.getUserId());
            eventParam.put("code","piggy_bank_product_off_shelve");
            eventParam.put("_income",messageEvent.getAvailableAmount());
            eventInstParams.add(eventParam);
        }
        try{
            eventInstanceService.addBathEventMapInstance(eventInstParams);
        }catch (Exception ex){
            BaseLogger.error("推送营销事件异常!",ex);
        }
    }


    @Override
    public String jdPayWithholdNotice(String resp) {
        BaseLogger.info(String.format("京东支付代扣回调入参：%s", resp));
        return mbUserAccountService.jdPayWithholdNotice(resp);
    }
}
