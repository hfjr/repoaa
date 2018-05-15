package com.rqb.ips.depository.platform.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fab.core.logger.BaseLogger;
import com.fab.web.controller.annotation.AppController;
import com.hf.comm.utils.DESUtil;
import com.hf.comm.utils.IpsVerifySign;
import com.hf.comm.utils.JSONUtils;
import com.rqb.ips.depository.platform.faced.IpsWithDrawService;
import com.zhuanyi.vjwealth.wallet.mobile.user.server.service.IUserOperationService;

/**
 * 
 * @author whb 用户提现
 *
 */
@Controller
public class IpsWithDrawController {

	@Autowired
	private IpsWithDrawService ipsWithDrawService;

	@Autowired
	private IUserOperationService operationService;


	
	/**
	 * 用户提现初始化
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping("/app/ips/userWithDrawinit")
	@AppController
	public Object userWithDrawinit(String userId) {

		return ipsWithDrawService.userWithDraw(userId);
	}

	/**
	 * 用户申请提现
	 * 
	 * @param userId
	 * @param money
	 * @return
	 */
	@RequestMapping(value="/app/ips/userWithDrawApply",method=RequestMethod.GET)
	//@AppController
	public String userWithDraw(String userId,String source, String money,Model model) {
		
		
		Map<String, String> map = (Map<String, String>)operationService.withdrawMa(userId,source, money);
		
		
		model.addAttribute("operationType", map.get("operationType"));
		model.addAttribute("merchantID", map.get("merchantID"));
		model.addAttribute("request", map.get("request"));
		model.addAttribute("sign", map.get("sign"));
		model.addAttribute("url", map.get("url"));
		
		
		
		return "/app/model/ipsWithDraw";
	}

	
	

	/**
	 * 环迅web同步回调
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/api/ips/withdraw/webCallBack")
	public String withDrawwebCallBack(HttpServletRequest req, HttpServletResponse resp,Model model)
			throws ServletException, IOException {

		String resultCode = req.getParameter("resultCode");
		String resultMsg = req.getParameter("resultMsg");
		String merchantID = req.getParameter("merchantID");
		String sign = req.getParameter("sign");
		String res = req.getParameter("response");
		String source = req.getParameter("source");
		model.addAttribute("source", source);
		//========测试时注释掉========
		String response=DESUtil.decrypt3DES(res, "r0uScmDuH5FLO37AJV2FN72J", "1eX24DCe");

		try {
			//===============测试用===========
			Map<String, Object> respResult = JSONUtils.json2map(response);
			
			//验签
			if (IpsVerifySign.checkSign(sign, resultCode, resultMsg, response)) {
				/*if (true) {*/
					if("000000".equals(resultCode)){
						
						
						ipsWithDrawService.updateWithDraw(respResult);
						BaseLogger.audit("存管响应成功");
					}else {
						BaseLogger.audit("存管响应失败");
						
					}
				
				BaseLogger.audit("存管验签成功");
			}else {
				BaseLogger.audit("存管验签失败");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			BaseLogger.error("存管验签异常",e);
		}
		
		
		return "/app/model/ipsWithCallBack";

	}

	/**
	 * 环迅提现s2SUrl异步回调
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/api/ips/withdraw/s2sCallBack")
	public void withDrawS2sCallBack(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String resultCode = req.getParameter("resultCode");
		String resultMsg = req.getParameter("resultMsg");
		String merchantID = req.getParameter("merchantID");
		String sign = req.getParameter("sign");
		String res = req.getParameter("response");
		//========测试时注释掉========
		String response=DESUtil.decrypt3DES(res, "r0uScmDuH5FLO37AJV2FN72J", "1eX24DCe");

		try {
			//Map<String, Object> respResult = JSONUtils.json2map(res);
		   Map<String, Object> respResult = JSONUtils.json2map(response);
			
			//验签
			if (IpsVerifySign.checkSign(sign, resultCode, resultMsg, response)) {
				//if (true) {
					if("000000".equals(resultCode)){
						
						boolean flag=ipsWithDrawService.queryWithDrawStatus((String)respResult.get("merBillNo"));
						
						if(!flag){
							ipsWithDrawService.updateWithDraw(respResult);
						}
						
						BaseLogger.audit("存管响应成功");
					}else {
						BaseLogger.audit("存管响应失败");
						
					}
					
				BaseLogger.audit("存管验签成功");
			}else {
				BaseLogger.audit("存管验签失败");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			BaseLogger.error("存管验签异常",e);
			
		}

		resp.getWriter().write("ipsCheckOk");

	}

	
	
	
	
	
	
	
	
	

}
