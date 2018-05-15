package com.zhuanyi.vjwealth.wallet.mobile.help.web.controller;

import com.zhuanyi.vjwealth.wallet.mobile.help.server.IHelpQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @ClassName HelpCenterController
 * @Description 帮助中心图片查看Controller
 * @Date 2016年9月30日 上午9:51:26
 * @author hanwei
 */
@Controller
@RequestMapping("/api/v3.3")
public class HelpCenterController {

	@Autowired
	private IHelpQueryService iHelpQueryService;

	/**
	 * 
	 * @Title queryHelpIcon
	 * @Description 用于显示帮助中心图片
	 * @Date 2016年9月30日 上午9:51:34
	 * @author hanwei
	 * @param iconUrl
	 * @return
	 */
	@RequestMapping(value="/help/down/{iconUrl}",produces="image/png")
	@ResponseBody
	public byte[] queryHelpIcon(@PathVariable("iconUrl") String iconUrl) {
		byte[] data = null;
		data = iHelpQueryService.queryAppHelpIcon(iconUrl);
		return data;
	}

}

