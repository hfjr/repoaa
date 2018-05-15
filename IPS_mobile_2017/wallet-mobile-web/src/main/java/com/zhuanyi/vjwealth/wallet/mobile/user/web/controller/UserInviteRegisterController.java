package com.zhuanyi.vjwealth.wallet.mobile.user.web.controller;

import com.fab.web.controller.annotation.AppController;
import com.zhuanyi.vjwealth.wallet.mobile.user.server.service.IUserInviteRegisterService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.RegisterParamDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserInviteRegisterController {
    @Autowired
    private IUserInviteRegisterService userInviteRegisterService;

    /**
     * 通过邀请码注册
     *
     * @param phone
     * @param password
     * @param code
     * @return
     */
    @RequestMapping("/app/user/registerWithCode")
    @AppController
    public Object register(String phone, String password, String code, RegisterParamDTO paramDTO, String userChannelType) {
        return userInviteRegisterService.register(phone, password, code, paramDTO, userChannelType);
    }

    @RequestMapping("/api/app/user/queryInviteQRCodePic.security")
    @AppController
    public Object queryInviteQRCodePic(String userId) {
        return userInviteRegisterService.queryInviteQRCodePic(userId);
    }
    @RequestMapping("/api/app/user/queryV2InviteQRCodePic.security")
    @AppController
    public Object queryV2InviteQRCodePic(String userId) {
        return userInviteRegisterService.queryInviteQRCodePic(userId,"market");
    }

    @RequestMapping("/api/app/user/queryRecommnedUserList.security")
    @AppController
    public Object queryRecommnedUserList(String userId, String page) {
        return userInviteRegisterService.queryRecommendUserList(userId, page);
    }

    @RequestMapping("/api/app/user/queryInviteQRCodePicByPhone")
    @AppController
    public Object queryInviteQRCodePicByPhone(String phone, String channelNo, String signature) {
        return userInviteRegisterService.queryInviteQRCodePicByPhone(phone, channelNo, signature);
    }

    @RequestMapping("/api/app/user/queryWeiXinShareInfo.security")
    @AppController
    public Object queryWeiXinShareInfo(String userId) {
        return userInviteRegisterService.queryWeiXinShareInfo(userId,null);
    }
    @RequestMapping("/api/app/user/queryV2WeiXinShareInfo.security")
    @AppController
    public Object queryV2WeiXinShareInfo(String userId) {
        return userInviteRegisterService.queryWeiXinShareInfo(userId,"market");
    }

    @RequestMapping("/api/app/user/queryMyRecommendUserInfo.security")
    @AppController
    public Object queryMyRecommendUserInfo(String userId) {
        return userInviteRegisterService.queryMyRecommendUserInfo(userId);
    }

    @RequestMapping("/api/app/user/queryMyInvitePageViewInfo.security")
    @AppController
    public Object query(String userId, String userChannelType){
        return userInviteRegisterService.queryMyInvitePageViewInfo(userId, userChannelType);
    }

}
