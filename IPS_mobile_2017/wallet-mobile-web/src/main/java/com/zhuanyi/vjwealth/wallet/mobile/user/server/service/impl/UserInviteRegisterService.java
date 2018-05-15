package com.zhuanyi.vjwealth.wallet.mobile.user.server.service.impl;

import com.fab.core.annotation.Remote;
import com.fab.core.exception.service.AppException;
import com.fab.core.logger.BaseLogger;
import com.fab.core.util.MD5;

import com.vj.vbus.event.dto.EventParam;
import com.vj.vbus.event.dto.MessageParam;
import com.vj.vbus.service.IEventInstanceService;
import com.zhuanyi.vjwealth.wallet.mobile.user.constant.UserValidationMethodConstant;
import com.zhuanyi.vjwealth.wallet.mobile.user.server.integration.mapper.IUserQueryMapper;
import com.zhuanyi.vjwealth.wallet.mobile.user.server.service.IUserInviteRegisterService;
import com.zhuanyi.vjwealth.wallet.mobile.user.util.ParamValidUtil;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBLoginUserDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.RegisterParamDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.UserShareInfoDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.IMBUserInviteService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.IMBUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserInviteRegisterService implements IUserInviteRegisterService {
    @Autowired
    private IUserQueryMapper userQueryMapper;

    @Remote
    IMBUserInviteService mbUserInviteService;

    @Remote
    IMBUserService mbUserService;

    @Autowired
    private IEventInstanceService eventInstanceService;

    private String webKey = "123456";

    @Override
    public Map<String, String> register(String phone, String password, String code, RegisterParamDTO paramDTO, String userChannelType) {
        ParamValidUtil.validatorPhone(phone);
        ParamValidUtil.validatorInviteCode(paramDTO.getInviteCode());
        ParamValidUtil.validatorChannelNo(paramDTO.getChannelNo());
        ParamValidUtil.validatorChannelUserId(paramDTO.getChannelUserId());
        ParamValidUtil.validatorActivityCode(paramDTO.getActivityCode());
        if (StringUtils.isBlank(paramDTO.getChannelNo())) {
            paramDTO.setChannelNo(userChannelType);
        }
        if (!mbUserInviteService.register(phone, password, code, paramDTO))
            throw new AppException("注册繁忙,请稍后尝试");
        Map<String, String> resultMap = this.userLogin(phone, password);
        //add market event by tony tang 20160815
        MessageParam messageParam = new MessageParam();
        EventParam eventParam = new EventParam();
        try {
            messageParam.setEventParam(eventParam);
            if (!StringUtils.isEmpty(paramDTO.getInviteCode())) {
                eventParam.setRecommendUserId(String.valueOf(resultMap.get("userId")));
                // edit market event by cuidezhong 20160923
                String userId = mbUserInviteService.queryRecommendUserIdByInviteCode(paramDTO);
                if (StringUtils.isNotEmpty(userId)) {
                    eventInstanceService.addEventInstance("user_recommend_regist", userId, messageParam);
                }
            }
            eventInstanceService.addEventInstance("user_regist", String.valueOf(resultMap.get("userId")), messageParam);
        } catch (Exception ex) {
            BaseLogger.error("增加营销事件信息异常!", ex);
        }
        return resultMap;
    }

    public Map<String, String> userLogin(String phone, String password) {
        UserValidationMethodConstant.validateLoginInfo(phone, password);
        Map<String, String> returnMap = new HashMap<String, String>();
        MBLoginUserDTO user = mbUserService.loginForApp(phone, password);
        returnMap.put("userId", user.getUserId());
        returnMap.put("uuid", user.getUuid());
        returnMap.put("inviteCode", user.getInviteCode());
        return returnMap;
    }

    @Override
    public Map<String, Object> queryInviteQRCodePic(String userId) {
        ParamValidUtil.validatorUserId(userId);
        return mbUserInviteService.queryInviteQRCodePic(userId, null);
    }

    @Override
    public Map<String, Object> queryInviteQRCodePic(String userId, String type) {
        ParamValidUtil.validatorUserId(userId);
        return mbUserInviteService.queryInviteQRCodePic(userId, type);
    }

    @Override
    public Map<String, Object> queryInviteQRCodePicByPhone(String phone, String channelNo, String signature) {
        ParamValidUtil.validatorPhone(phone);
        if (StringUtils.isBlank(channelNo)) {
            throw new AppException("渠道编码不能为空");
        }
        if (StringUtils.isBlank(signature)) {
            throw new AppException("签名不能为空");
        }
        ParamValidUtil.validatorChannelNo(channelNo);
        String signatureMy = "";
        try {
            String signatureStr = phone + ":" + channelNo + ":" + webKey;
            signatureMy = MD5.get32MD5(signatureStr);
        } catch (Exception e) {
            BaseLogger.error(e.getMessage(), e);
        }

        if (!signatureMy.equals(signature)) {
            throw new AppException("签名错误");
        }

        return mbUserInviteService.queryInviteQRCodePicByPhone(phone, channelNo);
    }

    @Override
    public Map<String, Object> queryRecommendUserList(String userId, String page) {
        ParamValidUtil.validatorUserId(userId);
        ParamValidUtil.validatorPage(page);

        Integer currentPage = (Integer.parseInt(page) - 1) * 10;
        List<Map<String, Object>> resultList = mbUserInviteService.queryRecommendUserList(userId, currentPage);
        //3.包装返回结果
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("records", resultList);
        returnMap.put("isMore", false);
        if (resultList != null && resultList.size() >= 10) {
            returnMap.put("isMore", true);
        }
        if (page.equals("1")) {//第一次查询时返回总数
            returnMap.put("count", mbUserInviteService.queryRecommendUserCount(userId));
        }
        return returnMap;
    }

    @Override
    public UserShareInfoDTO queryWeiXinShareInfo(String userId, String type) {
        return mbUserInviteService.queryWeiXinShareInfo(userId, type);
    }

    @Override
    public Map<String, Object> queryMyRecommendUserInfo(String userId) {
        return mbUserInviteService.queryMyRecommendUserInfo(userId);
    }

    @Override
    public Map<String, String> queryMyInvitePageViewInfo(String userId, String userChannelType) {
        ParamValidUtil.validatorUserId(userId);
        if (StringUtils.isBlank(userChannelType)) {
            throw new AppException("客户端类型不能为空");
        }
        if (!userChannelType.equals("IOS") && !userChannelType.equals("Android")) {
            throw new AppException("非法客户端类型");
        }
        Map<String, String> result = userQueryMapper.queryMyInvitePageInfo(userChannelType);
        String text = result.get("recommendText");
        if (!StringUtils.isBlank(text)) {
            text = text.replace("{count}", mbUserInviteService.queryRecommendUserCount(userId) + "");
        }
        result.put("recommendText", text);
        //APP端进入我的邀请页面不会请求以下接口，导致不会在wj_user_invite_info表里初始化数据，会导致分享出去的邀请码无效
        mbUserInviteService.queryInviteQRCodePic(userId, null);
        return result;
    }
}
