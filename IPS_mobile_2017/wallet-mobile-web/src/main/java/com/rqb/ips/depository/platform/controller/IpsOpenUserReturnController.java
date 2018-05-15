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
import com.rqb.ips.depository.platform.faced.IpsOpenUserReturnService;

/**
 * 
 * title:ips回掉
 * 
 * @author sunxiaolei
 *
 */
@Controller
public class IpsOpenUserReturnController {

	@Autowired
	private IpsOpenUserReturnService ipsService;

	/**
	 * ips同步回掉开户
	 * 
	 * @param req
	 * @param resp
	 */
	@RequestMapping("api/ips/openUser/webCallBack")
	public void openUserReturn(HttpServletRequest req, HttpServletResponse resp,Model model) {
		String resultCode = req.getParameter("resultCode");
		String resultMsg = req.getParameter("resultMsg");
		String merchantID = req.getParameter("merchantID");
		String sign = req.getParameter("sign");
		String respons = req.getParameter("response");
		String flag = "000000";
		try {
			// 验证签名
			if (IpsVerifySign.checkSign(sign, resultCode, resultMsg, respons)) {

				BaseLogger.audit("存管验签成功");
				//解密
				String decryptKey = "r0uScmDuH5FLO37AJV2FN72J";
				String iv = "1eX24DCe";
				String response = DESUtil.decrypt3DES(respons, decryptKey, iv);
				Map<String, Object> respResult = JSONUtils.json2map(response);
				if (flag.equals(resultCode)) {
					ipsService.openUser(respResult);
					BaseLogger.audit("ips回掉------成功");
				} else {
					System.out.println(resultCode);
					BaseLogger.audit("ips回掉------失败");
				}

			} else {
				BaseLogger.audit("存管验签失败");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			BaseLogger.error("存管验签异常", e);
		}

	}

	/**
	 * ips异步回掉开户
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	@RequestMapping("/api/ips/openUser/s2sCallBack")
	public void openUserS2sReturn(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		String resultCode = req.getParameter("resultCode");
		String resultMsg = req.getParameter("resultMsg");
		String merchantID = req.getParameter("merchantID");
		String sign = req.getParameter("sign");
		String response = req.getParameter("response");
		String flag = "000000";
		try {
			// 验证签名
			//if (IpsVerifySign.checkSign(sign, resultCode, resultMsg, respons)) {
/*				String decryptKey = "r0uScmDuH5FLO37AJV2FN72J";
				String iv = "1eX24DCe";
				String response = DESUtil.decrypt3DES(respons, decryptKey, iv);*/
				Map<String, Object> respResult = JSONUtils.json2map(response);
				if (flag.equals(resultCode)) {
					BaseLogger.audit("ips异步回掉--------成功");
					// 查询状态
					// 查询同步状态 如果成果不做操作
					 ipsService.queryOpenStatus(respResult);
				} else {
					throw new AppException("ips异步回掉--------失败");
				}
				BaseLogger.audit("存管验签成功");
			/*} else {
				BaseLogger.audit("存管验签失败");
			}*/
		} catch (Exception e) {
			// TODO Auto-generated catch block 
			BaseLogger.error("存管验签异常", e);
		}

		resp.getWriter().write("ipsCheckOk");

	}
}