package com.zhuanyi.vjwealth.wallet.mobile.user.server.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;


import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.RegisterParamDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.UserShareInfoDTO;
import org.springframework.web.multipart.MultipartFile;

import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.UploadIdentityPhotosDTO;
import com.zhuanyi.vjwealth.wallet.mobile.user.dto.HelpCenterTypeDTO;
import com.zhuanyi.vjwealth.wallet.mobile.user.dto.HelpCenterTypeSubDetailDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBRechargeDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBTransReturnDTO;


/**
 * @author Administrator
 */
public interface IUserOperationService {

    /**
     * 用户登录--所有错误和异常都会抛出
     *
     * @param phone
     * @param password
     * @return user_id  //用户uid
     * uuid 	   //uuid(校验请求合法性会用到)
     */
    Map<String, String> userLogin(String phone, String password);


    /**
     * 发送注册文字短信--所有异常将直接抛出
     *
     * @param phone
     * @param password
     * @return remainTime //有效时间，120 字符串 秒单位
     */
    Map<String, String> sendRegisterSMSNotice(String phone, String password);


    /**
     * 发送注册语音短信--所有异常将直接抛出
     *
     * @param phone
     * @param password
     * @return remainTime //有效时间，120 字符串 秒单位
     */
    Map<String, String> sendRegisterToneNotice(String phone, String password);


    /**
     * 注册
     *
     * @param phone
     * @param password
     * @param code     //短信验证码
     * @return user_id  //用户uid
     * uuid 	   //uuid(校验请求合法性会用到)
     */
    Map<String, String> register(String phone, String password, String code);

    /**
     * 忘记密码修改
     *
     * @param phone
     * @param password
     * @param code     //短信验证码
     * @return user_id  //用户uid
     * uuid 	   //uuid(校验请求合法性会用到)
     */
    Map<String, String> updateForgetPassword(String phone, String password, String code);


    /**
     * 发送忘记密码语音短信--所有异常将直接抛出
     *
     * @param phone
     * @param password
     * @return remainTime //有效时间，120 字符串 秒单位
     */
    Map<String, String> sendForgetToneNotice(String phone, String password);


    /**
     * 发送忘记密码文字短信--所有异常将直接抛出
     *
     * @param phone
     * @param password
     * @return remainTime //有效时间，120 字符串 秒单位
     */
    Map<String, String> sendForgetSMSNotice(String phone, String password);


    /**
     * 激活发送语音验证码
     *
     * @param phone
     * @return 有效时间，120 字符串 秒单位
     */
    Map<String, String> sendActivateSMSNotice(String phone, String password);

    /**
     * 激活发送语音验证码
     *
     * @param phone
     * @param password 新密码
     * @return 有效时间，120 字符串 秒单位
     */
    Map<String, String> sendActivateToneNotice(String phone, String password);

    /**
     * 更新激活成功  失败都会异常抛出
     *
     * @param phone
     * @param password 新密码
     * @param code
     * @return
     */
    Map<String, String> updateActivateUserInfo(String phone, String password, String code);

    /**
     * 修改密码接口
     *
     * @param userId
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return user_id  //用户uid
     * uuid 	   //uuid(校验请求合法性会用到)
     */
    Map<String, String> updateUserPassword(String userId, String oldPassword, String newPassword);


    /**
     * 保存用户反馈信息
     *
     * @param userId
     * @param advice
     * @return tip : "感谢您的意见反馈"
     */
    Map<String, String> saveUserFeedback(String userId, String advice);


    /**
     * 查询用户是否购买过v+理财
     *
     * @param userId
     * @param advice
     * @return isPurchaseV1Flag : yes,no
     */
    Map<String, String> queryUserIsPurchaseV(String userId);

    /**
     * 用户绑卡时,查询客户默认信息(姓名,身份证号,手机号)
     *
     * @param userId
     * @return
     */
    public Map<String, Object> queryUserDefaultInfo(String userId);


    /**
     * 校验用户的userId,uuid是否合法
     */
    boolean validatorUserIdAndUuid(String userId, String uuid);


    /**
     * 主账户到e账户
     *
     * @param userId
     * @param money
     * @return
     */
    MBTransReturnDTO applyMaToEa(String userId, String money);

    /**
     * @param userId
     * @param money
     * @return
     * @author zhangyingxuan
     * @date 20160708
     * 主账户到t金所账户
     */
    MBTransReturnDTO applyMaToTa(String userId, String money);


    /**
     * e账户到主账户
     *
     * @param userId
     * @param money
     * @return
     */
    MBTransReturnDTO transferEaToMa(String userId, String money);

    /**
     * @param userId
     * @param money
     * @return
     * @author zhangyingxuan
     * @date 20160708
     * T金所账户到主账户
     */
    MBTransReturnDTO transferTaToMa(String userId, String money);


    /**
     * e账户提现
     *
     * @param userId
     * @param money
     * @return
     */
    MBTransReturnDTO withdrawEa(String userId, String money);

    /**
     * ta账户提现
     *
     * @param userId
     * @param money
     * @return
     * @author zhangyingxuan
     * @date 20160712
     */
    MBTransReturnDTO withdrawTa(String userId, String money);


    /**
     * v+账户到主账户
     *
     * @param userId
     * @param money
     * @return
     */
    MBTransReturnDTO transferV1ToMa(String userId, String money);


    /**
     * 主账户到v+账户
     *
     * @param userId
     * @param money
     * @return
     */
    MBTransReturnDTO applyMaToV1(String userId, String money);


    /**
     * 主账户提现
     *
     * @param userId
     * @param money
     * @return
     */
    Object withdrawMa(String userId,String source,String money);


    /**
     * 已绑卡的充值
     *
     * @param paramDto
     * @return
     */
    Map<String, Object> doRechargeToMaAlreadyBind(String userId, String cardId, String orderNo, String amount, String code);


    /**
     * 已帮卡 发送验证码
     *
     * @param paramDto
     * @return
     */
    MBRechargeDTO rechargeToMaAlreadyBindCardSendCode(String userId, String cardId, String amount);

    /**
     * 已帮卡 发送验证码V3.2
     *
     * @param paramDto
     * @return
     */
    MBRechargeDTO rechargeToMaAlreadyBindCardSendCodeV32(String userId, String cardId, String amount);

    /**
     * 未绑卡的发送验证码
     *
     * @param paramDto
     * @return
     */
    MBRechargeDTO rechargeToMaSendCode(MBRechargeDTO dto);

    /**
     * 未绑卡的发送验证码V3.2
     *
     * @param dto
     * @return
     */
    MBRechargeDTO rechargeToMaSendCodeV32(MBRechargeDTO dto);

    /**
     * 未绑卡的发送验证码V3.6
     *
     * @param dto
     * @return
     */
    MBRechargeDTO bindCardSendCode(MBRechargeDTO dto);


    /**
     * 未绑卡的充值
     *
     * @param dto
     * @return
     */
    Map<String, Object> doRechargeToMa(MBRechargeDTO dto);


    /**
     *  绑卡验证码确认V3.6
     *
     * @param dto
     * @return
     */
    Map<String, Object> confirmBindCardSendCode(MBRechargeDTO dto);

    /**
     * 查询用户总资产图数据
     *
     * @param userId
     * @return
     */
    List<Map<String, Object>> queryUserTotolAccountAmountReport(String userId);

    /**
     * 查询用户总资产图数据
     * V+下架
     * V理财(定期理财上线)
     *
     * @param userId
     * @return
     * @since V3.0
     */
    List<Map<String, Object>> queryUserTotolAccountAmountReportForV30(String userId);


    /**
     * 用户未读消息变为已读消息,任何异常直接抛出
     *
     * @param messageId --消息id
     * @param userId    --用户id
     */
    public void userHaveReadMessage(String userId, String messageId);

    /**
     * 获取客服热线电话
     * {
     * "phone_no": "4000078655",
     * "service_time": "热线服务时间:8:00~22:00"
     * }
     *
     * @return
     */
    public Object getServiceHotline();


    /**
     * 查询是否是第一次绑卡
     *
     * @param userId
     * @return flag: yes:第一次绑卡;no:不是第一次绑卡
     * tip:如果是第一次绑卡,则提示"第一次绑卡,该卡将成为您的安全卡和充值卡",如果不是,则为""
     */
    Map<String, String> queryIsFirstBindCard(String userId);


    /**
     * 查询v1理财协议中需要的数据
     *
     * @param userId
     * @return
     */
    Map<String, String> queryV1AgreementUserInfo(String userId);


    //================================================操作=================================================

    /**
     * @return 成功或失败
     * @title 上传头像
     */
    String headPicturUpload(String userId, MultipartFile picFile);

    /**
     * @title 获取头像文件信息
     */
    Map<String, String> queryHeadPicturById(String fileId);

    /**
     * 删除消息
     *
     * @param messageIds
     */
    void deleteUserMessage(String[] messageIds) throws Exception;


    /**
     * 安卓手机设备日志上传
     *
     * @param userId
     * @param value
     * @return
     */
    Map<String, String> uploadDeviceLog(String userId, MultipartFile value);

    /**
     * 联动付款确认后,异步接受通知回调
     *
     * @param map
     * @return String
     */
    String payNotice(Map<String, Object> map);

    /**
     * 上传图片
     *
     * @param fileName
     * @param fileBytes
     * @return
     * @throws IOException
     */
    String uploadPic(String fileName, byte[] fileBytes) throws IOException;

    /**
     * 微信上传图片
     * @param file
     * @return
     * @throws IOException
     */
    Object uploadPicFile( MultipartFile file) throws IOException;

    /**
     * 加载图片
     *
     * @param fileNo
     * @return
     */
    byte[] loadFile(String fileNo);

    /**
     * 身份证正面文件上传
     *
     * @param userId
     * @param rightSideFile
     * @param reverseSideFile
     * @param uploadSuccessFileId [只有一个文件上传成功，第二次上传使用]
     * @return
     */
    UploadIdentityPhotosDTO uploadIdentityPhotos(String userId, MultipartFile rightSideFile
            , MultipartFile reverseSideFile, String uploadSuccessFileId) throws IOException;


    //======================================预约定期理财功能==========================================

    //1.查询是否预约过定期理财
    public Boolean hasAppointedFinancial(String userId);


    /**
     * 预约定期理财
     * 任何异常直接抛出
     *
     * @param userId
     */
    public void appointmentFinancial(String userId);

    /**
     * 易宝付款确认后,异步接受通知回调
     *
     * @param map
     * @return String
     */
    String yeePayNotice(String data, String encryptkey);

    /**
     * 充值回调后查询订单状态
     * 如果充值回调未成功并做处理
     *
     * @param userId
     * @param orderId
     * @return
     */
    Map<String, String> queryRechargeNotice(String userId, String orderId);


    /**
     * 帮助中心列表
     *
     * @return HelpCenterTypeDTO
     * @since 3.3
     */
    List<HelpCenterTypeDTO> getHelpCenterType();

    /**
     * 获取帮助中心条目详情
     *
     * @param detailId
     * @return
     * @since 3.3
     */
    HelpCenterTypeSubDetailDTO getHelpCenterTypeSubDetail(String detailId);

    String userLoginForSHQB(String paramStr);

    /**
     * 保存用户设备Id信息
     *
     * @param userId
     * @param deviceId
     * @param deviceType
     * @param otherData
     * @return
     */
   void saveUserAppDevice(String userId,String deviceId,String deviceType,String otherData) throws Exception;

    /**
     * 据传入的policyNo,
     * 去产品表查询相应大保单图片绝对路径
     *
     * @param policyNo
     * @return policyPicUrl
     */
    String queryPolicyPicUrlByPolicyNo(String policyNo);

    Map<String, String> sendHouseFundLoanIntentionSMSNotice(String phone);

    Map<String, String> sendHouseFundLoanIntentionSMSNoticeV2(String phone);
    
    
    /**
     * 易宝付款确认后,异步接受通知回调
     * @param resp
     * @return
     */
    String jingdongNotice(String resp);

    /**
     * 批量转出用户ta账户余额至银行卡(T金所产品下架)
     * @return
     */
    String withdrawTaForTjsOffTheShelf();

    /**
     * 京东支付代扣异步回调
     * @param resp
     * @return
     */
    String jdPayWithholdNotice(String resp);
}
