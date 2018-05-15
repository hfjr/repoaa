package com.zhuanyi.vjwealth.wallet.mobile.user.server.service;

import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.RegisterParamDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.UserShareInfoDTO;

import java.util.Map;

public interface IUserInviteRegisterService {
    /**
     * 通过邀请码注册
     *
     * @param phone
     * @param password
     * @param code
     * @param paramDTO 注册参数
     * @return
     */
    Map<String, String> register(String phone, String password, String code, RegisterParamDTO paramDTO, String userChannelType);

    /**
     * 查询用户邀请二维码信息
     *
     * @param userId
     * @return
     */
    Map<String, Object> queryInviteQRCodePic(String userId);

    /**
     * 查询用户邀请二维码信息
     *
     * @param userId
     * @return
     */
    Map<String, Object> queryInviteQRCodePic(String userId,String type);

    /**
     * 通过手机号查询二维码图片
     *
     * @param phone
     * @return
     */
    Map<String, Object> queryInviteQRCodePicByPhone(String phone, String channelNo, String signature);

    /**
     * 查询我推荐的用户列表
     *
     * @param userId
     * @param page
     * @return
     */
    Map<String, Object> queryRecommendUserList(String userId, String page);

    /**
     * 查询微信分享信息
     * @param userId
     * @param type
     * @return
     */
    UserShareInfoDTO queryWeiXinShareInfo(String userId, String type);

    /**
     * 查询我的推荐人信息
     * @param userId
     * @return
     */
    Map<String,Object> queryMyRecommendUserInfo(String userId);

    /**
     * 查询我的邀请页面元素信息 for app
     * @param userId
     * @return
     */
    Map<String, String> queryMyInvitePageViewInfo(String userId, String userChannelType);
}
