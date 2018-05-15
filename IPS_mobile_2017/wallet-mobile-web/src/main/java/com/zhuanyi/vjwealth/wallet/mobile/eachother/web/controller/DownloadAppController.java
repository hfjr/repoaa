package com.zhuanyi.vjwealth.wallet.mobile.eachother.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DownloadAppController {

	@RequestMapping("/app/download")
	public String downloadApp(){
		return "redirect:http://a.app.qq.com/o/simple.jsp?pkgname=com.zhuanyi.gzb";
	}
}
