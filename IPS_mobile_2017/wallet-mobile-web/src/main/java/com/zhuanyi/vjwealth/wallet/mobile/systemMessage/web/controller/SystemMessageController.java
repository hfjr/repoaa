package com.zhuanyi.vjwealth.wallet.mobile.systemMessage.web.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fab.core.exception.service.AppException;
import com.fab.core.template.ITemplateService;
import com.fab.web.controller.annotation.AppController;
import com.zhuanyi.vjwealth.wallet.mobile.systemMessage.server.dto.WjSystemMessageDTO;
import com.zhuanyi.vjwealth.wallet.mobile.systemMessage.server.service.ISystemMessageService;

/**
 * 系统消息查询 Created by CE
 */
@Controller
public class SystemMessageController {

	@Autowired
	private ITemplateService template;

	@Autowired
	private ISystemMessageService systemMessageService;

	// 1.获取系统消息
	@RequestMapping("/api/V3.2/app/message/querySystemMessageList")
	@AppController
	public Object queryProductList(String userId,String channelType) {

		return systemMessageService.querySystemMessageList(userId,channelType);
	}

	// 2.获取系统消息详情
	@RequestMapping("/api/V3.2/app/message/querySystemMessageDetail")
	public String queryProductDetail(String messageNo,String userId, Model model) {
		if(StringUtils.isBlank(messageNo)){
			throw new AppException("系统消息不存在");
		}
		WjSystemMessageDTO messageDetail = systemMessageService
				.queryUserMessageDetailByMessageNo(messageNo,userId);

		String editType = messageDetail.getEditType();
		if ("URL".equals(editType)) {
			return "redirect:" + messageDetail.getContentUrl();
		}
		model.addAttribute("content", messageDetail);
		return "/app/systemMessage/message";

	}

}
