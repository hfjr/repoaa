package com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.fab.core.exception.service.AppException;
import com.fab.core.logger.BaseLogger;
import com.fab.server.util.Format;
import com.fuiou.Constants;
import com.fuiou.check.data.Check5PayConditionData;
import com.fuiou.check.data.CheckCardReqData;
import com.fuiou.mpay.encrypt.DESCoderFUIOU;
import com.fuiou.util.MD5;
import com.fuiou.utils.ConfigReader;
import com.fuiou.utils.HttpFormUtil;
import com.fuiou.utils.HttpPostUtil;
import com.zhuanyi.vjwealth.wallet.mobile.common.server.service.IBusessinessNoService;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.common.mapper.ISequenceMapper;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.common.utils.IdcardValidator;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.common.utils.PreciseCompute;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.common.utils.StaxonUtils;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.common.utils.ValidatorUtils;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.mapper.IPayMapper;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.service.IPayService;

@Service
public class PayService implements IPayService {

	
	@Autowired
	private IBusessinessNoService busessinessNoService;
	
	@Autowired
	private IPayMapper payMapper;
	
	@Autowired
	private ISequenceMapper  sequenceMapper;
	
	private static final String BACK_URL = "http://app.rongqiaobao.com/wallet-mobile/api/fuiou/pay/paysuccessCallBack/v1.0";
	private static final String HOME_URL = "http://app.rongqiaobao.com/wallet-mobile/page/fuiou/pay/pageSuccess/v1.0";
	private static final String RETURN_URL = "http://app.rongqiaobao.com/wallet-mobile/page/fuiou/pay/pageFail/v1.0";
	private static final String PAY_URL = ConfigReader.getString("h5.pay_url");
	
	private void validateRechargeParam(String userId,String amt,String bankCard,String bankCode,String asidePhone,String name,String idNo){
		if(StringUtils.isBlank(userId)){
			throw new AppException("用户id不能为空");
		}
		
		if(StringUtils.isBlank(amt)){
			throw new AppException("金额不能为空");
		}
			
		if(!NumberUtils.isNumber(amt)){
			throw new AppException("请输入正确金额");	
		}
		
		if(Double.valueOf(amt)<=0){
			throw new AppException("金额必须大于0");	
		}
			
		
		if(StringUtils.isBlank(bankCard)){
			throw new AppException("银行卡不能为空");
		}
		
		List<String> cardNos=payMapper.queryUserBankCardList(userId);
		
		// 判定用户是否绑定新的银行卡(同卡进出问题)
		
		if(cardNos.size()>0){
			Boolean oneCard=false;
			for(String cardNo:cardNos){
				if(cardNo.equals(bankCard)){
					oneCard=Boolean.TRUE;
				}
			}
			// 新的银行卡
			if(!oneCard){
				throw new AppException("每个用户只能绑定一张银行卡");
			}
			
		}
		
			
		if(StringUtils.isBlank(bankCode)){
			throw new AppException("bankCode不能为空");
		}
		
		if(StringUtils.isBlank(asidePhone)){
			throw new AppException("预留手机号不能为空");
		}
		
		if(!Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$").matcher(asidePhone).matches()){
			// 验证手机号  
			throw new AppException("请输入正确手机号");
		}
		
		
		if(StringUtils.isBlank(name)){
			throw new AppException("姓名不能为空");
		}
		
		if(StringUtils.isBlank(idNo)){
			throw new AppException("身份证不能为空");
		}
		
		if(!IdcardValidator.isValidatedAllIdcard(idNo)){
			throw new AppException("身份证号不正确");
		}
		
	}
	private void validatePCRechargeParam(String userId,String bankCard,String asidePhone,String name,String idNo){
		if(StringUtils.isBlank(userId)){
			throw new AppException("用户id不能为空");
		}
		
		
		if(StringUtils.isBlank(bankCard)){
			throw new AppException("银行卡不能为空");
		}
		
		List<String> cardNos=payMapper.queryUserBankCardList(userId);
		
		// 判定用户是否绑定新的银行卡(同卡进出问题)
		
		if(cardNos.size()>0){
			Boolean oneCard=false;
			for(String cardNo:cardNos){
				if(cardNo.equals(bankCard)){
					oneCard=Boolean.TRUE;
				}
			}
			// 新的银行卡
			if(!oneCard){
				throw new AppException("每个用户只能绑定一张银行卡");
			}
			
		}
		
		
		if(StringUtils.isBlank(asidePhone)){
			throw new AppException("预留手机号不能为空");
		}
		
		if(!Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$").matcher(asidePhone).matches()){
			// 验证手机号  
			throw new AppException("请输入正确手机号");
		}
		
		
		if(StringUtils.isBlank(name)){
			throw new AppException("姓名不能为空");
		}
		
		if(StringUtils.isBlank(idNo)){
			throw new AppException("身份证不能为空");
		}
		
		if(!IdcardValidator.isValidatedAllIdcard(idNo)){
			throw new AppException("身份证号不正确");
		}
		
	}
	
	
	//跳转支付
	public String goRechargegetWay(String userId,String amt,String bankCard,String bankCode,String asidePhone,String name,String idNo){
		this.validateRechargeParam(userId, amt, bankCard, bankCode, asidePhone, name, idNo);
		try{
			
			
			
			amt=String.valueOf(new BigDecimal(amt).intValue()*100);
			
			//生成订单
			String orderNo = busessinessNoService.getVjOrderNo();
			
			String tradeNo=sequenceMapper.getFullSequence("TR", "vj_trade");
			
			// cardId ,如果已有该银行卡就用已有的cardId
			String cardId=payMapper.queryUserBankCardId(userId, bankCard);
			if(StringUtils.isBlank(cardId)){
				cardId=sequenceMapper.getFullSequence("CA", "vj_user_card");
			}
			
			String idType = "0";
			
			String type="10";
			
			StringBuffer orderPlain = new StringBuffer();
			String signPlain = type+"|"+"2.0"+"|"+Constants.H5_MCHNT_CD+"|"+tradeNo+"|"+userId
					+"|"+amt+"|"+bankCard+"|"+BACK_URL+"|"+name+"|"+idNo+"|"+idType+"|"+"0"+"|"
					+ HOME_URL+"|"+RETURN_URL+"|"+Constants.H5_MCHNT_KEY;
			String sign=MD5.MD5Encode(signPlain);
			System.out.println("[签名明文:]"+signPlain);
			orderPlain.append("<ORDER>")
			.append("<VERSION>2.0</VERSION>")
			.append("<LOGOTP>0</LOGOTP>")
			.append("<MCHNTCD>").append(Constants.H5_MCHNT_CD).append("</MCHNTCD>")
			.append("<TYPE>").append(type).append("</TYPE>")
			.append("<MCHNTORDERID>").append(tradeNo).append("</MCHNTORDERID>")
			.append("<USERID>").append(userId).append("</USERID>")
			.append("<AMT>").append(amt).append("</AMT>")
			.append("<BANKCARD>").append(bankCard).append("</BANKCARD>")
			.append("<BACKURL>").append(BACK_URL).append("</BACKURL>")
			.append("<HOMEURL>").append(HOME_URL).append("</HOMEURL>")
			.append("<REURL>").append(RETURN_URL).append("</REURL>")
			.append("<NAME>").append(name).append("</NAME>")
			.append("<IDTYPE>").append(idType).append("</IDTYPE>")
			.append("<IDNO>").append(idNo).append("</IDNO>")
			.append("<REM1>").append(userId).append("</REM1>")
			.append("<REM2>").append(userId).append("</REM2>")
			.append("<REM3>").append(userId).append("</REM3>")
			.append("<SIGNTP>").append("md5").append("</SIGNTP>")
			.append("<SIGN>").append(sign).append("</SIGN>")
			.append("</ORDER>");
			System.out.println("[订单信息:]"+orderPlain.toString());
			Map<String,String> param = new HashMap<String, String>();
			param.put("VERSION", "2.0");
			param.put("ENCTP", "1");
			param.put("LOGOTP", "0");
			param.put("MCHNTCD", Constants.H5_MCHNT_CD);
			param.put("FM",orderPlain.toString());
			param.put("FM", DESCoderFUIOU.desEncrypt(orderPlain.toString(), DESCoderFUIOU.getKeyLength8(Constants.H5_MCHNT_KEY)));
			System.out.println("[请求信息:]"+param);
			
			String returnHtml=HttpFormUtil.formForward(PAY_URL, param);
			
			String actualAmt=String.valueOf(PreciseCompute.div(new BigDecimal(amt).doubleValue(), 100));
			// 2. TDOO  记录 查询
			// wj_ebatong_trade_history
			payMapper.saveRechargeHistory(cardId, bankCard, name, idNo, tradeNo, actualAmt, bankCode, asidePhone, "T", "请求成功", JSON.toJSONString(param), returnHtml, userId);
			
			// 插入交易记录
			// wj_payment_trade_record
			payMapper.saveTradeRecord(tradeNo, actualAmt, orderNo, bankCard);
			
			// 插入待支付订单
			// wj_order
			payMapper.saveRechargeOrder(userId, orderNo, actualAmt, cardId);
			
			return returnHtml;
		}catch (Exception e){
			
			BaseLogger.error("系统异常！"+e);
			
			return "支付繁忙,请重新尝试";
		}
	}
	
	
	public void preCheckDoPayCondition(String userId,String amt,String bankCard,String bankCode,String asidePhone,String name,String idNo){
		this.validateRechargeParam(userId, amt, bankCard, bankCode, asidePhone, name, idNo);
		this.validate5PayCondition(bankCard, name, idNo, asidePhone);
	}
	
	@Override
	public void bindPCCard(String userId, String bankCode,String bankCard, String asidePhone, String name, String idNo) {
		this.validatePCRechargeParam(userId, bankCard,asidePhone, name, idNo);
		this.validate5PayCondition(bankCard, name, idNo, asidePhone);
		//插入银行卡
		this.bindCardNo(userId, bankCode, bankCard, asidePhone, name, idNo);
	}
	
	public void bindCardNo(String userId,String bankCode,String bankCard,String asidePhone, String name, String idNo){
		// 校验银行卡是否存在
		List<String> cardNos=payMapper.queryUserBankCardList(userId);
		if(cardNos!=null&&cardNos.size()>0){
			throw new AppException("每个用户只能绑定一张银行卡");
		}
		// 插入 安全卡
		payMapper.saveUserCards(name, userId, bankCard, asidePhone, bankCode, "security");
		// 插入充值
		payMapper.saveUserCards(name, userId, bankCard, asidePhone, bankCode, "recharge");
		// 更新用户信息
		payMapper.completeUserInfo(name, idNo, userId);
		
	}
	
	private void validate5PayCondition(String cardNo, String realName, String idNo, String asidePhone) {
		Check5PayConditionData payResult=new Check5PayConditionData();
		try {
			BaseLogger.audit("富有支付5要素校验 in...");
			String mno = asidePhone;
			String oCerNo = idNo;
			String oCerTp = "0";
			String onm = realName;
			String ono = cardNo;
			String oSsn = Format.dateToString(new Date());
			String ver = "1.30";
			CheckCardReqData data = new CheckCardReqData();
			data.setMno(mno);
			data.setOCerNo(oCerNo);
			data.setOCerTp(oCerTp);
			data.setOnm(onm);
			data.setOno(ono);
			data.setOSsn(oSsn);
			data.setVer(ver);
			String url = Constants.CHECK_CARD_1_REQ_URL;
			Map<String, String> param = new HashMap<String, String>();
			param.put("FM", data.buildReqXml());
			BaseLogger.audit("富有支付5要素校验请求消息：" + JSON.toJSONString(param));
			String respStr = HttpPostUtil.postForward(url, param);
			payResult = JSON.parseObject(StaxonUtils.xml2json(respStr).toLowerCase()).getObject("fm", Check5PayConditionData.class);
			BaseLogger.audit("富有支付5要素校验返回结果:" + StaxonUtils.xml2json(respStr));
		
		} catch (Exception e) {
			BaseLogger.error(e);
			throw new AppException("系统繁忙,请稍后再试");
		}
		if (!payResult.getRcd().equals("0000")) {
			throw new AppException(payResult.getRdesc());
		}
	}
	
	public String goRechargegetWay(String cardId,String userId,String amt){	
		ValidatorUtils.validateNull(cardId, "cardId");
		Map<String,String> payInfo=payMapper.queryUserPayInfo(userId, cardId);
		String bankCard=payInfo.get("bankCard");
		String bankCode=payInfo.get("bankCode");
		String asidePhone=payInfo.get("asidePhone");
		String name=payInfo.get("name");
		String idNo=payInfo.get("idNo");
		return goRechargegetWay(userId, amt, bankCard, bankCode, asidePhone, name, idNo);
	}
}
