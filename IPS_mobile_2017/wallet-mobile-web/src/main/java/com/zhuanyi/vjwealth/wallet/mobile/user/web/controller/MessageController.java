package com.zhuanyi.vjwealth.wallet.mobile.user.web.controller;

import com.fab.core.exception.service.AppException;
import com.fab.core.logger.BaseLogger;
import com.fab.web.controller.annotation.AppController;
import com.zhuanyi.vjwealth.wallet.mobile.user.dto.MessageDTO;
import com.zhuanyi.vjwealth.wallet.mobile.user.server.service.IMessageService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
//@RequestMapping("/api/v3.0")
public class MessageController {

    @Autowired
    private IMessageService messageService;

//    //22.查询用户消息列表
//    @RequestMapping("/api/v3.0/app/user/queryUserMessageList.security")
//    @AppController
//    public Object queryUserMessageList(String userId, String messageType, String page) {
//
//        validator(messageType,page);
//        return messageService.queryMessageListByUserIdAndMessageType(userId, messageType, page);
//    }
//
//    //23.查询单个消息详情且把消息置为已读［消息阅读及内容接口］
//    @RequestMapping("/api/v3.0/app/user/queryUserMessageDetailByMessageId.security")
//    public String queryUserMessageDetailByMessageId(String userId,String messageId,Model model) {
//
//        //查询消息内容
//        MessageDTO messageDetail =  messageService.queryUserMessageDetailByMessageId(userId, messageId);
//        //更新消息阅读标示
//        messageService.updateUserMessageReadTypeByUserIdAndMessageId(userId, messageId);
//
//        String contentType  = messageDetail.getContentType();
//        String content  = messageDetail.getContent();
//        BaseLogger.audit("图片内容content: " + content);
//        if(StringUtils.equals(contentType,"text")){
//            model.addAttribute("content",messageDetail);
//            return "/app/message/msg";
//        }
//        return "redirect:"+ content;
//
//    }
//
//    //24.消息删除接口
//    @RequestMapping(value = "/api/v3.0/app/user/deleteUserMessage.security")
//    @AppController
//    public Object deleteUserMessage(String userId, String messageIds) {
//        Map<String, String> result = new HashMap<String, String>();
//        try {
//            if (StringUtils.isBlank(messageIds))
//                throw new AppException("请选择消息");
//
//            if (StringUtils.isBlank(userId))
//                throw new AppException("请重新登录");
//
//            messageService.deleteMessageByUserIdAndMessageIds(userId, messageIds);
//            result.put("code", "200200");
//            result.put("message", "消息删除成功");
//        } catch (Exception e) {
//            result.put("code", "200201");
//            result.put("message", "删除消息失败");
//            BaseLogger.error("删除消息失败", e);
//        }
//        return result;
//    }

    private void validator(String messageType, String page) {
        if (StringUtils.isBlank(messageType)) {
            throw new AppException("消息类型不能为空");
        }

        if (StringUtils.isBlank(page)) {
            throw new AppException("页码不能为空");
        }

        if (!StringUtils.isNumeric(page) || Integer.parseInt(page) < 1 || Integer.parseInt(page) % 1 > 0) {
            throw new AppException("页码数值不合法，必须为大于0的整数");
        }
    }

    //V32版本--start
    //查询用户消息列表
    @RequestMapping("/api/v3.0/app/user/queryUserMessageList.security")
    @AppController
    public Object queryUserMessageListV32(String userId, String messageType, String page) {

        validator(messageType, page);
        return messageService.queryMessageListByUserIdAndMessageTypeV32(userId, messageType, page);
    }

    //23.查询单个消息详情且把消息置为已读［消息阅读及内容接口］
    @RequestMapping("/api/v3.0/app/user/queryUserMessageDetailByMessageId.security")
    public String queryUserMessageDetailByMessageIdV32(String userId, String messageId, Model model) {

        //查询消息内容
        MessageDTO messageDetail = messageService.queryUserMessageDetailByMessageIdV32(userId, messageId);
        //更新消息阅读标示
        messageService.updateUserMessageReadTypeByUserIdAndMessageIdV32(userId, messageId);

        String contentType = messageDetail.getContentType();
        String content = messageDetail.getContent();
        BaseLogger.audit("图片内容content: " + content);
        if (StringUtils.equals(contentType, "text")) {
            model.addAttribute("content", messageDetail);
            return "/app/message/msg";
        }
//        return "redirect:"+ messageDetail.getCornerUrl();

        return "redirect:" + content;

    }

    //24.消息删除接口
    @RequestMapping(value = "/api/v3.0/app/user/deleteUserMessage.security")
    @AppController
    public Object deleteUserMessageV32(String userId, String messageIds) {
        Map<String, String> result = new HashMap<String, String>();
        try {
            if (StringUtils.isBlank(messageIds))
                throw new AppException("请选择消息");

            if (StringUtils.isBlank(userId))
                throw new AppException("请重新登录");

            messageService.deleteMessageByUserIdAndMessageIdsV32(userId, messageIds);
            result.put("code", "200200");
            result.put("message", "消息删除成功");
        } catch (Exception e) {
            result.put("code", "200201");
            result.put("message", "删除消息失败");
            BaseLogger.error("删除消息失败", e);
        }
        return result;
    }

    @RequestMapping("/api/v3.0/app/user/hasNewMessage.security")
    @AppController
    public Object queryUserHasNewMsg(String userId) {
        return messageService.queryUserHasNewMsg(userId);
    }

    //V32版本--end
}
