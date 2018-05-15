package com.rqb.ips.depository.platform.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fab.core.logger.BaseLogger;
import com.hf.comm.utils.DESUtil;
import com.hf.comm.utils.IpsVerifySign;
import com.hf.comm.utils.JSONUtils;
import com.rqb.ips.depository.platform.beans.IpsResponseDTO;
import com.rqb.ips.depository.platform.faced.IpsTransferAccountsService;


@Controller
public class IpsTransferCallBackController {

	@Autowired
	private IpsTransferAccountsService ipsTransferAccountsService;

	
	// ips转账s2s 回调
	@RequestMapping("/api/ips/transfer/s2sCallBack")
	public void transferS2sCallBack(HttpServletRequest req, HttpServletResponse resp) {

		String resultCode = req.getParameter("resultCode");
		String resultMsg = req.getParameter("resultMsg");
		String merchantID = req.getParameter("merchantID");
		String sign = req.getParameter("sign");
		String res = req.getParameter("response");
		//解密
		String response=DESUtil.decrypt3DES(res, "r0uScmDuH5FLO37AJV2FN72J", "1eX24DCe");

		try {
			//json转对象
			IpsResponseDTO responseDTO = JSONUtils.json2pojo(res, IpsResponseDTO.class);
			

			// 验签
			 if (IpsVerifySign.checkSign(sign, resultCode, resultMsg,
			 response)) {
			//if (true) {
				// 验签成功处理回调
               if("000000".equals(resultCode)){
            	   
            	  
            	   //检验转账同步结果
            	 boolean flag=ipsTransferAccountsService.queryTransferStatus( responseDTO.getBatchNo(), responseDTO.getProjectNo(), responseDTO.getTransferAccDetail());
            	   if(!flag){
            		   ipsTransferAccountsService.transferS2sCallBack(responseDTO);
            		   
            	   }
            	   
            	   BaseLogger.audit("存管响应成功");
               }else{
            	   BaseLogger.audit("存管响应失败"); 
               }
				

				BaseLogger.audit("存管验签成功");
			} else {
				BaseLogger.audit("存管验签失败");
			}

			resp.getWriter().write("ipsCheckOk");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			BaseLogger.error("存管验签异常", e);
		}

	}

}
