package com.zhuanyi.vjwealth.wallet.service.mainbiz.rqjfmsg.server.service.impl;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fab.core.annotation.Remote;
import com.fab.core.exception.service.AppException;
import com.fab.core.template.ITemplateService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.rqjf.server.service.IPushMessageService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.rqjfmsg.server.dto.MessageDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.rqjfmsg.server.mapper.IMessageMapper;
import com.zhuanyi.vjwealth.wallet.service.outer.message.server.service.IPhoneMessageService;

@Service
public class PushMessageService implements IPushMessageService {

	@Autowired
	private IMessageMapper messageMapper;
	@Autowired
	private ITemplateService template;
	@Remote
    IPhoneMessageService phoneMessageService;



	@Override
	public void pushMessageByText(String templateNo, Map<String, Object> param) {
		if(messageMapper.countTemplate(templateNo)<1){
			throw new AppException("模板不可用或不存在[templateNo]"+templateNo);
		}
		//组装参数
		String userId = param.get("userId").toString();
		String messageType = param.get("messageType").toString();
		String subTitleType = IPushMessageService.SUB_TITLE_TYPE_TEXT;
		String contentType = IPushMessageService.CONTENT_TYPE_TEXT;
		String cornerUrl = "";
		Map<String,String> template=messageMapper.getMessageTemplateInfo(templateNo);
		//	标题
		String title =template.get("title");
		//	渲染发送文本 
		String content=processContent(template.get("content"), param);
		//	subTitle= contet
		String subTitle=content;
		
		this.pushMessage(templateNo, userId, messageType, title, subTitle, subTitleType, contentType, content,cornerUrl);
	}

	// 渲染模板
	public String processContent(String templateContet, Map<String, Object> param) {
		
		return template.processByContent(templateContet, param);
	}
	
	public void pushMessage(String templateNo, String userId, String messageType, String title, String subTitle, String subTitleType, String contentType, String content,String cornerUrl) {
		
		// 插入消息
		MessageDTO messageDTO=new MessageDTO(title, subTitle, subTitleType, contentType, content, cornerUrl, messageType);
		
		messageMapper.insertSelective(messageDTO);

		// 插入对应关系
		messageMapper.insertWjuserMessageRef(String.valueOf(messageDTO.getId()), userId);
	}
	
	
	@Override
	public void pushSMSMessageByText(String bizType, Map<String, Object> param) {
		if(StringUtils.isBlank(bizType)){
			throw new AppException("bizType不能为空");
		}
		if(StringUtils.isBlank(param.get("phone").toString())){
			throw new AppException("phone不能为空");
		}
			
		phoneMessageService.sendTextMessageByBizType(param.get("phone").toString(), bizType,param);
	}

}
