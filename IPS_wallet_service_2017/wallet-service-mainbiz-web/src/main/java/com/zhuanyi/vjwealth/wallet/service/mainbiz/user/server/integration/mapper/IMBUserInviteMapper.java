package com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBUserInviteDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.UserShareInfoDTO;

@Mapper
public interface IMBUserInviteMapper {
    void createUserInviteInfo(@Param("userId") String userId, @Param("recommendUserId") String recommendUserId, @Param("inviteCode") String inviteCode, @Param("inviteUrl") String inviteUrl, @Param
            ("qrcodeNo") String qrcodeNo, @Param("channelNo") String channelNo, @Param("channelUserId") String channelUserId, @Param("activityCode") String activityCode);

    //根据邀请码查询推荐人userId
    String queryUserIdByInviteCode(@Param("inviteCode") String inviteCode);

    MBUserInviteDTO queryUserInviteInfoByUserId(@Param("userId") String userId);
    
    String queryRecommendUserIdByUserId(@Param("userId") String userId);
    
    Map<String,String> queryEventInfo(@Param("userId") String userId);

    void updateUserInviteInfo(@Param("userId") String userId, @Param("inviteCode") String inviteCode, @Param("inviteUrl") String inviteUrl, @Param("qrcodeNo") String qrcodeNo);

    List<Map<String, Object>> queryRecommendUserList(@Param("userId") String userId, @Param("page") Integer page);

    Integer queryRecommendUserCount(@Param("userId") String userId);

    UserShareInfoDTO queryUserShareInfo();

    String queryUserRealNameById(@Param("userId") String userId);

    int queryDistributeGoldFishUserNum();

    //查询用户是否已经绑卡
    boolean queryUserIsBankCard(@Param("userId") String userId);

    boolean queryInviteRegisterDistributeGoldFishSwitch();

    UserShareInfoDTO queryMarketUserShareInfo();

    /**
     * 查询好友帮分享链接
     *
     * @return
     */
    UserShareInfoDTO queryFriendHelpUserShareInfo();

    /**
     * 查询某个活动用户的推荐用户ID列表
     * @param userId
     * @param activityCode
     * @return
     */
    List<String> queryRecommendUserListByActivityCode(@Param("userId") String userId, @Param("activityCode") String activityCode);
}
