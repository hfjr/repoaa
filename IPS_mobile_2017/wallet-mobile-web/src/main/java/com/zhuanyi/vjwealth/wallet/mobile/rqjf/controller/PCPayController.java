package com.zhuanyi.vjwealth.wallet.mobile.rqjf.controller;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.fab.core.logger.BaseLogger;
import com.fuiou.Constants;
import com.fuiou.h5.bean.H5PayResult;
import com.fuiou.util.MD5;
import com.fuiou.utils.XmlBeanUtils;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.common.utils.StaxonUtils;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.common.utils.ValidatorUtils;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.service.ICommonSequenceService;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.service.IPCPayService;

@Controller
public class PCPayController {
	
	@Autowired
	private IPCPayService payService;	
	@Autowired
	private ICommonSequenceService commonSequenceService;
	
	
	
	//跳转支付
	@RequestMapping("/page/fuiou/pay/pc/gateway/v1.0")
	public String doPay(String userId, String uuid, String amt, HttpServletRequest request, Model model) {
		ValidatorUtils.validateNull2(userId, "请重新登录");
		ValidatorUtils.validateNull2(uuid, "请重新登录");
		
		try {
			// 参数预校验一下

			String mchnt_cd = "0002900F0422310";
			
			String order_id =commonSequenceService.getNextSequence("TR", "vj_trade");;
			String order_amt = String.valueOf(new BigDecimal(amt).multiply(new BigDecimal(100)).intValue());

			String order_pay_type = "B2C";

			String page_notify_url = "http://app.rongqiaobao.com/wallet-mobile/pc/page/fuiou/pay/pageSuccess/v1.0";// 前端回调

			String back_notify_url = "http://app.rongqiaobao.com/wallet-mobile/api/pc/fuiou/pay/paysuccessCallBack/v1.0";// 后台回调

			String order_valid_time = "10m";

			String iss_ins_cd = "0000000000";

			String goods_name = "融桥宝产品";

			String goods_display_url = "";

			String mchnt_key = "yhbquoqli3tloeb6hv6glvqoyklx2jv8"; // 32位的商户密钥

			String rem = "";

			String ver = "1.0.1";

			String signDataStr = mchnt_cd + "|" + order_id + "|" + order_amt + "|" + order_pay_type + "|" +

			page_notify_url + "|" + back_notify_url + "|" + order_valid_time + "|" +

			iss_ins_cd + "|" + goods_name + "|" + goods_display_url + "|" + rem + "|" + ver + "|" + mchnt_key;

			String md5 = MD5.MD5Encode(signDataStr);

			System.out.println("signDataStr===" + signDataStr);

			System.out.println("md5===" + md5);
			model.addAttribute("md5", md5);
			model.addAttribute("mchnt_cd", mchnt_cd);
			model.addAttribute("order_id", order_id);
			model.addAttribute("order_amt", order_amt);
			model.addAttribute("order_pay_type", order_pay_type);
			model.addAttribute("page_notify_url", page_notify_url);
			model.addAttribute("back_notify_url", back_notify_url);
			model.addAttribute("order_valid_time", order_valid_time);
			model.addAttribute("iss_ins_cd", iss_ins_cd);
			model.addAttribute("goods_name", goods_name);
			model.addAttribute("goods_display_url", goods_display_url);
			model.addAttribute("rem", rem);
			model.addAttribute("ver", ver);

			// 请求的订单保存到数据库中
			payService.saveRechargeOrder(order_id, userId, amt);
		} catch (Exception e) {
			BaseLogger.error("pc支付跳转网关组装参数异常:");
			BaseLogger.error(e);
		}

		return "/rqjf/pc/pcpaygateway";
	}
	
	
	//前台界面 成功返回
	@RequestMapping("/pc/page/fuiou/pay/pageSuccess/v1.0")	
	public String  pageSuccess(HttpServletRequest req,Model model) throws ServletException, IOException{
		try{
			
			
			System.out.println("成功返回内容"+JSON.toJSON(req.getParameterMap()));
			
			BaseLogger.audit("成功返回内容"+JSON.toJSON(req.getParameterMap()));
			
			model.addAttribute("tradeNo",req.getParameter("MCHNTORDERID"));
			
		}catch(Exception e){
			e.printStackTrace();
			BaseLogger.error(e);
		}
		return "/rqjf/pc/paySuccess";
	}
		
	//支付失败
	@RequestMapping("/pc//page/fuiou/pay/pageFail/v1.0")
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
	@RequestMapping("/api/pc/fuiou/pay/paysuccessCallBack/v1.0")
	public void paysuccessCallBack(String order_id,String order_amt,String mchnt_cd,String order_date,String order_st,String order_pay_code,String order_pay_error,String fy_ssn,String resv1,String md5) throws ServletException, IOException{
		try {

			String mchnt_key = "yhbquoqli3tloeb6hv6glvqoyklx2jv8"; // 32位的商户密钥

			String signDataStr = mchnt_cd + "|" + order_id + "|" + order_date + "|" + order_amt + "|" + order_st + "|" + order_pay_code + "|" + order_pay_error + "|" + resv1 + "|" + fy_ssn + "|" + mchnt_key;
			String md52 = MD5.MD5Encode(signDataStr);
			BaseLogger.audit("[signDataStr]" + signDataStr);
			BaseLogger.audit("[md5]" + md5);
			BaseLogger.audit("[md52]" + md52);

			if (md52.equals(md5)) {
				if (Constants.RESP_CODE_SUCCESS.equals(order_pay_code)) {

					BaseLogger.audit(order_id + "支付成功~");

					payService.successRechargeOrder(order_id);

				} else {
					BaseLogger.audit(order_id + "支付失败~" + order_pay_error);
				}
			} else {
				BaseLogger.audit(order_id + "验签失败~");
			}
		}catch (Exception e){
			BaseLogger.error("pc充值回调异常,异常如下");
			BaseLogger.error(e);
		}
		
	}
	
	
	public static void main(String[] args) {
		String xml="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><RESPONSE><VERSION>1.0</VERSION><RESPONSECODE>0000</RESPONSECODE><RESPONSEMSG>支付成功</RESPONSEMSG><MCHNTORDERID>FY20170627103945814</MCHNTORDERID><AMT/><SIGN>e18f7d35b1b96079eb8eb6c052803a8c</SIGN><USERID/></RESPONSE>";
		System.out.println("StaxonUtils转换之后的结果:"+StaxonUtils.xml2json(xml));
		System.out.println("parseObject转换json对象:"+JSON.parseObject(StaxonUtils.xml2json(xml)));
				
		
		
	}
	
}
