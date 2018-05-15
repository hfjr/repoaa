package com.rqb.ips.depository.platform.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fab.core.exception.service.AppException;
import com.fab.core.logger.BaseLogger;
import com.hf.comm.utils.DESUtil;
import com.hf.comm.utils.IpsVerifySign;
import com.hf.comm.utils.JSONUtils;
import com.rqb.ips.depository.platform.faced.IpsPacketFrozenService;


@Controller
public class IpsPacketReturnController {

	@Autowired
	private IpsPacketFrozenService ipsService;
	
	/**
	 * 冻结同步回调接口
	 * @param req
	 * @param resp
	 */
	@RequestMapping("/api/ips/freeze/IpsfreezeWebCallBack")
	public String IpsfreezeWebCallBack(HttpServletRequest req, HttpServletResponse resp,Model model) {
		

		String resultCode = req.getParameter("resultCode");
		String resultMsg = req.getParameter("resultMsg");
		String merchantID = req.getParameter("merchantID");
		String sign = req.getParameter("sign");
		String respons = req.getParameter("response");
		String source = req.getParameter("source");
		String flag="000000";
		model.addAttribute("source", source);
		try {
			Map<String, Object> respResult = JSONUtils.json2map(respons);
			
			//验签 
			if (IpsVerifySign.checkSign(sign, resultCode, resultMsg,
			respons)) {
			BaseLogger.audit("存管验签成功");
			if (flag.equals(resultCode)) {
				//解密
				String decryptKey = "r0uScmDuH5FLO37AJV2FN72J";
				String iv = "1eX24DCe";
				String response = DESUtil.decrypt3DES(respons, decryptKey, iv);
				//验签成功处理回调
				ipsService.packetCallBack(respResult);
				BaseLogger.audit("ips 同步回掉成功");
				
			} else {
				BaseLogger.audit("ips同步回掉失败");
			}
			}else{
				throw new  AppException("存管验签成功");
			}
			resp.getWriter().write("ipsCheckOk");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			BaseLogger.error("存管验签异常", e);
		}
		return "/app/model/ipsPackageFreezeCallBack.jsp";
	}



	
	/**
	 * 冻结异步回调接口
	 * @param req
	 * @param resp
	 */
	@RequestMapping("/api/ips/packetFrozen/s2sCallBackCallBack")
	public void packetFrozenS2sCallBack(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		String resultCode = req.getParameter("resultCode");
		String resultMsg = req.getParameter("resultMsg");
		String merchantID = req.getParameter("merchantID");
		String sign = req.getParameter("sign");
		String response = req.getParameter("response");
		String flag="000000";
		try {
			Map<String, Object> respResult = JSONUtils.json2map(response);
			
			//验签 
			//if (IpsVerifySign.checkSign(sign, resultCode, resultMsg,
			//response)) {
				//BaseLogger.audit("存管验签成功");
			if (flag.equals(resultCode)) {
				BaseLogger.audit("ips异步 回掉成功");
				// 查询状态
				// 查询同步状态 如果成果不做操作
			ipsService.queryStatus(respResult);
				
			} else {
				throw new AppException("ips异步回掉--------失败");
			}
			
			//}	else{BaseLogger.audit("存管验签失败");
			//}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			BaseLogger.error("存管验签异常", e);
		}
		resp.getWriter().write("ipsCheckOk");

	}
	
	
		
	}
	
