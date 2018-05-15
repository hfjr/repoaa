package com.zhuanyi.vjwealth.wallet.mobile.rqjf.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.fab.core.logger.BaseLogger;
import com.fab.web.controller.annotation.AppController;
import com.fuiou.Constants;
import com.fuiou.h5.bean.H5PayResult;
import com.fuiou.query.data.OrderQryByMSsn;
import com.fuiou.util.MD5;
import com.fuiou.utils.ConfigReader;
import com.fuiou.utils.HttpPostUtil;
import com.fuiou.utils.XmlBeanUtils;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.common.utils.StaxonUtils;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.service.IPayService;

@Controller
public class PayController {
	
	@Autowired
	private IPayService payService;	
	
	private static final String	ORDER_QRY_URL= ConfigReader.getString("order_qry_url_1");
	
	//绑卡校验
	@RequestMapping("/api/fuiou/pay/precheck/v1.0.security")
	@AppController
	public Object precheck(String userId,String amt,String bankCard,String bankCode,String asidePhone,String name,String idNo,HttpServletResponse response) {
		payService.preCheckDoPayCondition(userId, amt, bankCard, bankCode, asidePhone, name, idNo);
		return new Object();
	}
	
	//PC绑卡校验
	@RequestMapping("/api/pc/fuiou/pay/bindPCCard/v1.0.security")
	@AppController
	public Object bindPCCard(String userId,String bankCard,String bankCode,String asidePhone,String name,String idNo) {
		payService.bindPCCard(userId, bankCode,bankCard, asidePhone, name, idNo);
		return new Object();
	}
	
	
	
	//跳转支付
	@RequestMapping("/page/fuiou/pay/gateway/v1.0")
	public void doPay(String userId,String amt,String bankCard,String bankCode,String asidePhone,String name,String idNo,HttpServletResponse response) throws ServletException, IOException{
		String returnHtml=payService.goRechargegetWay(userId, amt, bankCard, bankCode, asidePhone, name, idNo);
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		response.getOutputStream().write(returnHtml.getBytes("utf8"));
	}
	
	//已绑卡跳转支付
	@RequestMapping("/page/fuiou/pay/bindCardGateway/v1.0")
	public void alreadyBindCardDoPay(String cardId,String userId,String amt,HttpServletResponse response) throws ServletException, IOException{
		String returnHtml=payService.goRechargegetWay(cardId, userId, amt);
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		response.getOutputStream().write(returnHtml.getBytes("utf8"));
	}
	
	
	//前台界面 成功返回
	@RequestMapping("/page/fuiou/pay/pageSuccess/v1.0")	
	public String  pageSuccess(HttpServletRequest req,Model model) throws ServletException, IOException{
		try{
			
			
			System.out.println("成功返回内容"+JSON.toJSON(req.getParameterMap()));
			
			BaseLogger.audit("成功返回内容"+JSON.toJSON(req.getParameterMap()));
			
			model.addAttribute("tradeNo",req.getParameter("MCHNTORDERID"));
			
		}catch(Exception e){
			e.printStackTrace();
			BaseLogger.error(e);
		}
		return "/rqjf/paySuccess";
	}
		
	//支付失败
	@RequestMapping("/page/fuiou/pay/pageFail/v1.0")
	public String pageFail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		try{
			InputStream in = req.getInputStream();
			byte[] buffer = new byte[req.getContentLength()];
			in.read(buffer);
			String respStr = new String(buffer,"UTF-8");
			System.out.println("失败-前台通知内容："+respStr);
			H5PayResult payResult = XmlBeanUtils.convertXml2Bean(respStr, H5PayResult.class);
			System.out.println("失败返回内容"+JSON.toJSON(payResult));
			resp.getOutputStream().write(buffer);
		}catch(Exception e){
			resp.getOutputStream().write(e.getMessage().getBytes("UTF-8"));
		}
		return "/rqjf/payFail";
	}
	
	
	//支付成功回调
	@RequestMapping("/api/fuiou/pay/paysuccessCallBack/v1.0")
	public void paysuccessCallBack(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		String version = req.getParameter("VERSION");
		String type = req.getParameter("TYPE");
		String responseCode = req.getParameter("RESPONSECODE");
		String responseMsg = req.getParameter("RESPONSEMSG");
		String mchntCd = req.getParameter("MCHNTCD");
		String mchntOrderId = req.getParameter("MCHNTORDERID");
		String orderId = req.getParameter("ORDERID");
		String bankCard = req.getParameter("BANKCARD");
		String amt = req.getParameter("AMT");
		String sign = req.getParameter("SIGN");
		String key = Constants.H5_MCHNT_KEY;
		Map<String, String> keyMap = new HashMap<String, String>();
		keyMap.put(Constants.H5_MCHNT_CD, Constants.H5_MCHNT_KEY);
		key = keyMap.get(mchntCd);
		// 校验签名
		String signPain = new StringBuffer().append(type).append("|").append(version).append("|").append(responseCode)
				.append("|").append(mchntCd).append("|").append(mchntOrderId).append("|").append(orderId).append("|")
				.append(amt).append("|").append(bankCard).append("|").append(key).toString();
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		if (MD5.MD5Encode(signPain).equals(sign))
		{
			if (Constants.RESP_CODE_SUCCESS.equals(responseCode))
			{
				
				System.out.println(mchntOrderId +"支付成功~");
				BaseLogger.audit(mchntOrderId +"支付成功~");
				resp.getWriter().write("支付成功~");
			}
			else
			{
				BaseLogger.audit(mchntOrderId +"支付失败~"+responseMsg);
				System.out.println(mchntOrderId +"支付失败~"+responseMsg);
				resp.getWriter().write("支付失败~");
			}
		}
		else
		{
			System.out.println(mchntOrderId +"验签失败~");
			BaseLogger.audit(mchntOrderId +"验签失败~");
			resp.getWriter().write("验签失败~");
		}
	}
	
	
	
	
	
	
	
	@RequestMapping("/page/fuiou/pay/queryorder/v1.0")	
	public void queryOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		try
		{
			String version = req.getParameter("version");
			String mchntCd = req.getParameter("mchntCd");
			String mchntKey = req.getParameter("mchntKey");
			String mchntOrderId = req.getParameter("mchntOrderId");
			String rem1 = req.getParameter("rem1");
			String rem2 = req.getParameter("rem2");
			String rem3 = req.getParameter("rem3");
			OrderQryByMSsn data = new OrderQryByMSsn();
			data.setMchntCd(mchntCd);
			data.setMchntOrderId(mchntOrderId);
			data.setRem1(rem1);
			data.setRem2(rem2);
			data.setRem3(rem3);
			data.setVersion(version);
			Map<String, String> params = new HashMap<String,String>();
			params.put("FM", data.buildXml(mchntKey));
			String respStr = HttpPostUtil.postForward(ORDER_QRY_URL, params);
//			H5PayResult payResult = XmlBeanUtils.convertXml2Bean(respStr, H5PayResult.class);
			System.out.println("返回内容"+StaxonUtils.xml2json(respStr));
			
			System.out.println("获取其中对象:"+JSON.toJSONString(JSON.parseObject(StaxonUtils.xml2json(respStr)).getObject("RESPONSE", H5PayResult.class)));
			resp.getWriter().write(respStr);
		}
		catch (Exception e)
		{
			resp.getWriter().write("系统异常：" + e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		String xml="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><RESPONSE><VERSION>1.0</VERSION><RESPONSECODE>0000</RESPONSECODE><RESPONSEMSG>支付成功</RESPONSEMSG><MCHNTORDERID>FY20170627103945814</MCHNTORDERID><AMT/><SIGN>e18f7d35b1b96079eb8eb6c052803a8c</SIGN><USERID/></RESPONSE>";
		System.out.println("StaxonUtils转换之后的结果:"+StaxonUtils.xml2json(xml));
		System.out.println("parseObject转换json对象:"+JSON.parseObject(StaxonUtils.xml2json(xml)));
				
		
		
	}
	
}
