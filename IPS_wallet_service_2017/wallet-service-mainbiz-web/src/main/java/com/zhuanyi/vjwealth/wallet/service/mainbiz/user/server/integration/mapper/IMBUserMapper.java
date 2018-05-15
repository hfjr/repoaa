package com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper;

import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.BankCardBasicInfoDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBUserDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBUserDetailDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.ValidatorCardIsSecurityParamDTO;
import org.apache.ibatis.annotations.Param;
import com.fab.server.annotation.Mapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBLoginUserDTO;

import java.util.Map;

/**
 * @author xuejie
 */
@Mapper
public interface IMBUserMapper {

    MBLoginUserDTO loginForApp(@Param("phone") String phone);

    void updateLoginUUID(@Param("phone") String phone, @Param("encodePassword") String encodePassword, @Param("uuid") String uuid);

    int checkLoginForApp(@Param("phone") String phone, @Param("uuid") String uuid);

    int queryUserCountByPhone(@Param("phone") String phone);

    void createUserInfo(@Param("userId") String userId, @Param("phone") String phone, @Param("encodePassword") String encodePassword);

    //创建用户，包含渠道来源
    void createUserInfoByChannel(@Param("userId") String userId, @Param("phone") String phone, @Param("encodePassword") String encodePassword, @Param("channel") String channel);

    void createUserAccountInfo(@Param("userId") String userId, @Param("accountId") String accountId, @Param("tradeType") String tradeType);

    void updateUserPassword(@Param("phone") String phone, @Param("encodePassword") String encodePassword);

    void updateActivateUserInfo(@Param("phone") String phone, @Param("encodePassword") String encodePassword);

    void addUserTransLockInfo(@Param("userId") String userId);

    void updateLoginWXUUID(@Param("phone") String phone, @Param("uuid") String uuid, @Param("channel") String channel);

    void updateLoginWXUUIDByPassword(@Param("phone") String phone, @Param("uuid") String uuid, @Param("channel") String channel, @Param("encodePassword") String encodePassword);

    MBLoginUserDTO loginForWX(@Param("phone") String phone);

    String queryPhoneByUserId(@Param("userId") String userId);

    String queryUserIdByPhone(@Param("phone") String phone);

    /**
     * @param userId
     * @return
     * @author zhangyingxuan
     * @date 20160712
     * 查询用户信息
     */
    MBUserDTO queryUserById(@Param("userId") String userId);

    /**
     * @param userId
     * @author zhangyingxuan
     * @date 20160712
     * 更新用户是否开通T金所账户的状态位
     */
    void updateTaAccountStatus(@Param("userId") String userId);

    MBUserDTO queryUserByPhone(@Param("phone") String phone);

    MBUserDTO queryUserByIdentityNo(@Param("identityNo") String identityNo);

    void updateLoginUUIDFromSHQB(@Param("phone") String phone, @Param("uuid") String uuid);

    void createUnActiveUser(Map<String, Object> map);

    Map<String,Object> queryUserInviteInfoByUserId(@Param("userId") String userId);

    int createUserInviteInfo(@Param("userId") String userId, @Param("inviteCode") String inviteCode, @Param("inviteUrl") String inviteUrl);

    int updateUserInviteInfo(@Param("userId") String userId,@Param("inviteCode") String inviteCode);

    /**
     * fesco用户更新渠道和fesco_uuid
     * @param userId
     */
    void updateUserChannel(@Param("phone") String phone, @Param("encodePassword") String encodePassword, @Param("openId") String openId, @Param("channel") String channel);

    String queryHouseFundLoanIntentionSMSLink();

    void createUserInfoWithUUID(@Param("userId") String userId, @Param("phone") String phone, @Param("encodePassword") String encodePassword, @Param("uuid") String uuid);

    MBLoginUserDTO queryUserInfoByPhone(@Param("phone") String phone);

    void updateLoginUUIDByPhone(@Param("phone") String phone, @Param("uuid") String uuid);

    String queryENUserChannelNo(String phone);

    MBUserDetailDTO queryUserDetailByPhone(@Param("phone") String phone);

    void updateUserBasicInfo(@Param("userId")String userId, @Param("realName")String realName, @Param("certNo")String certNo);

    BankCardBasicInfoDTO queryUserBankCardInfoByCardId(@Param("userId")String userId,@Param("cardId") String cardId);

    Boolean validatorCardIsSecurity(ValidatorCardIsSecurityParamDTO paramDTO);

    boolean isExistCertNo(@Param("cerNo")String cerNo);
    
    Map<String,String> queryUserSecurityCardInfo(@Param("userId")String userId);
}
