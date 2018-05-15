package com.rqb.ips.depository.platform.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fab.core.exception.service.AppException;
import com.fab.core.logger.BaseLogger;
import com.hf.comm.utils.DESUtil;
import com.hf.comm.utils.IpsVerifySign;
import com.hf.comm.utils.JSONUtils;
import com.rqb.ips.depository.platform.faced.IPsFreezeService;
import com.rqb.ips.depository.platform.faced.IpsInvestmentOrderService;



@Controller
public class IpsFreezeController {
       
	@Autowired
	private IpsInvestmentOrderService ipsInvestmentOrderService;
  
	/*@Remote
	private IMBUserAccountService mbUserService;
	*/
	@Autowired
	private IPsFreezeService iPsFreezeService;
	/**
	 * 冻结下单
	 * @param userId
	 * @param productId
	 * @param investmentAmount
	 * @param packageId
	 * @param couponId
	 * @param clientType
	 * @param token
	 * @param recommendPhone
	 * @return
	 */
	@RequestMapping(value="api/ips/finance/product.payment",method=RequestMethod.GET)
	//@AppController
	public String placeOrderForPaymentPasswordV2(Model model,String userId, String productId, String investmentAmount,
			String packageId, String couponId, String clientType, String token, String recommendPhone,String source) {
		if (!org.apache.commons.lang.math.NumberUtils.isNumber(investmentAmount)) {
			throw new AppException("投资金额不合法");
		}
		
		Map<String, String> map=(Map<String, String>)ipsInvestmentOrderService.placeOrderIps(userId, productId, investmentAmount, packageId, couponId, clientType,
				token, recommendPhone,source);
		
		
		model.addAttribute("operationType", map.get("operationType"));
		model.addAttribute("merchantID", map.get("merchantID"));
		model.addAttribute("request", map.get("request"));
		model.addAttribute("sign", map.get("sign"));
		model.addAttribute("url", map.get("url"));
		
		System.out.println(map);
		
		/*return ipsInvestmentOrderService.placeOrderIps(userId, productId, investmentAmount, packageId, couponId, clientType,
				token, recommendPhone);*/
		 return "/app/model/order";
		 
	}

	

	
	
	/**
	 *  下单冻结s2s异步回调
	 * @param req
	 * @param resp
	 */
	@RequestMapping("/api/ips/freeze/s2sCallBack")
	public void freezeS2sCallBack(HttpServletRequest req, HttpServletResponse resp) {
		String resultCode = req.getParameter("resultCode");
		String resultMsg = req.getParameter("resultMsg");
		String merchantID = req.getParameter("merchantID");
		String sign = req.getParameter("sign");
		String res = req.getParameter("response");
		//======测试用暂时注释========
		String response=DESUtil.decrypt3DES(res, "r0uScmDuH5FLO37AJV2FN72J", "1eX24DCe");

		try {
			Map<String, Object> respResult = JSONUtils.json2map(response);
			//Map<String, Object> respResult = JSONUtils.json2map(res);

			//验签 
			if (IpsVerifySign.checkSign(sign, resultCode, resultMsg,
			 response)) {
			//if (true) {
				//验签成功处理回调
				if("000000".equals(resultCode)){
					//校验判断同步
					//boolean flag=iPsFreezeService.queryFreezeStatus((String)respResult.get("merBillNo"),(String)respResult.get("projectNo"));
					String  merBillNo=(String)respResult.get("merBillNo");
					String  projectNo=(String)respResult.get("projectNo");
					
					//boolean flag=mbUserService.queryFreezeStatus(merBillNo,projectNo);
					boolean flag=iPsFreezeService.queryFreezeStatus(merBillNo,projectNo);
					
					if(flag){
						//iPsFreezeService.freezeCallBack(respResult);
						//mbUserService.freezeCallBack(respResult);
						iPsFreezeService.freezeCallBack(respResult);
					}
					
					BaseLogger.audit("=======存管响应成功=========");
				}else {
					BaseLogger.audit("=======存管响应失败=========");
					
				}
				
				
				
				BaseLogger.audit("==========存管验签成功=========");
			} else {
				BaseLogger.audit("==========存管验签失败=======");
			}

			resp.getWriter().write("ipsCheckOk");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			BaseLogger.error("=====存管验签异常=====", e);
		}

	}
	
	
	
	/**
	 * 下单冻结同步回调接口
	 * @param req
	 * @param resp
	 */
	@RequestMapping("/api/ips/freeze/webCallBack")
	public String freezeWebCallBack(HttpServletRequest req, HttpServletResponse resp,Model model) {
		
		String resultCode = req.getParameter("resultCode");
		String resultMsg = req.getParameter("resultMsg");
		String merchantID = req.getParameter("merchantID");
		String sign = req.getParameter("sign");
		String res = req.getParameter("response");
		String source = req.getParameter("source");
		model.addAttribute("source",source);
		
		//======测试用暂时注释========
		String response=DESUtil.decrypt3DES(res, "r0uScmDuH5FLO37AJV2FN72J", "1eX24DCe");
		
		try {
			Map<String, Object> respResult = JSONUtils.json2map(response);
			//Map<String, Object> respResult = JSONUtils.json2map(res);
			
			//验签 
			 if (IpsVerifySign.checkSign(sign, resultCode, resultMsg,
			 response)) {
			//if (true) {
				//验签成功处理回调
				if("000000".equals(resultCode)){
					//iPsFreezeService.freezeCallBack(respResult);
					//mbUserService.freezeCallBack(respResult);
					iPsFreezeService.freezeCallBack(respResult);
					BaseLogger.audit("============存管响应成功===========");
				}else {
					BaseLogger.audit("===========存管响应失败===========");
					
				}
				
				
				
				BaseLogger.audit("==========存管验签成功============");
			} else {
				BaseLogger.audit("==========存管验签失败============");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			BaseLogger.error("===========存管验签常============", e);
		}
		return "/app/model/ipsFreezeCallBack.jsp";
		
	}
	
	/*************************************************************************************************************/
	
	
	/**
	 * 后管还款冻结异步回调接口
	 * @param req
	 * @param resp
	 */
	@RequestMapping("/api/ips/freezeRepayment/s2sCallBack")
	public void freezeRepaymentS2s(HttpServletRequest req, HttpServletResponse resp) {
		
		String resultCode = req.getParameter("resultCode");
		String resultMsg = req.getParameter("resultMsg");
		String merchantID = req.getParameter("merchantID");
		String sign = req.getParameter("sign");
		String res = req.getParameter("response");
		//======测试用暂时注释========
		String response=DESUtil.decrypt3DES(res, "r0uScmDuH5FLO37AJV2FN72J", "1eX24DCe");

		try {
			Map<String, Object> respResult = JSONUtils.json2map(response);
			//Map<String, Object> respResult = JSONUtils.json2map(res);

			//验签 
			if (IpsVerifySign.checkSign(sign, resultCode, resultMsg,
			 response)) {
			//if (true) {
				//验签成功处理回调
				if("000000".equals(resultCode)){
					//校验判断同步
					
					//boolean flag=mbUserService.queryFreezeRepaymentStatus((String)respResult.get("merBillNo"));
					boolean flag=iPsFreezeService.queryFreezeRepaymentStatus((String)respResult.get("merBillNo"));
					if(!flag){
						iPsFreezeService.freezeRepayment(respResult);
					}
					
					BaseLogger.audit("=======存管响应成功=========");
				}else {
					BaseLogger.audit("=======存管响应失败=========");
					
				}
				
				
				
				BaseLogger.audit("==========存管验签成功=========");
			} else {
				BaseLogger.audit("==========存管验签失败=======");
			}

			resp.getWriter().write("ipsCheckOk");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			BaseLogger.error("=====存管验签异常=====", e);
		}
		
	}
	
	
	
	
	/**
	 * 后管还款冻结同步回调接口
	 * @param req
	 * @param resp
	 */
	@RequestMapping("/api/ips/freezeRepayment/webCallBack")
	public void freezeRepaymentWeb(HttpServletRequest req, HttpServletResponse resp) {
		
		String resultCode = req.getParameter("resultCode");
		String resultMsg = req.getParameter("resultMsg");
		String merchantID = req.getParameter("merchantID");
		String sign = req.getParameter("sign");
		String res = req.getParameter("response");
		//======测试用暂时注释========
		String response=DESUtil.decrypt3DES(res, "r0uScmDuH5FLO37AJV2FN72J", "1eX24DCe");
		
		try {
			Map<String, Object> respResult = JSONUtils.json2map(response);
			//Map<String, Object> respResult = JSONUtils.json2map(res);
			
			//验签 
			 if (IpsVerifySign.checkSign(sign, resultCode, resultMsg,
			 response)) {
			//if (true) {
				//验签成功处理回调
				if("000000".equals(resultCode)){
					//mbUserService.freezeRepayment(respResult);
					iPsFreezeService.freezeRepayment(respResult);
					BaseLogger.audit("============存管响应成功===========");
				}else {
					BaseLogger.audit("===========存管响应失败===========");
					
				}
				
				
				
				BaseLogger.audit("==========存管验签成功============");
			} else {
				BaseLogger.audit("==========存管验签失败============");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			BaseLogger.error("===========存管验签常============", e);
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
}
