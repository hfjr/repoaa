package com.zhuanyi.vjwealth.wallet.mobile.common.dto;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.time.DateFormatUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fab.core.logger.BaseLogger;
import com.zhuanyi.vjwealth.wallet.mobile.common.util.IPTools;


public class UserLogsDTO implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String USER_ATTRIBUTE_DTO="prdHandle_UserLogsDTO";
	private String uri;
	private String url;
	private String requestParams;
	//头部信息
	private String requestHeader;
	//handler 访问方法信息
	private String handlerMessage;
	//异常信息
	private String exceptionMeesage;
	//借口耗时
	private String consumingTime;
	private String vistorTime;
	private String userChannelType;
	private String ip;
	//访问开始时间
	private Long startTime;
	//结束时间,计算借口耗时
	private Long endTime;
	
	
	// 解析 request中的参数
	public void parseRequestParam(HttpServletRequest request){
		try {
			String url = request.getRequestURL().toString();
			String uri = request.getRequestURI();
			String paramMap = JSONObject.toJSON(request.getParameterMap()).toString();
			String vistorTime = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
			Object userChannelTypeObj = request.getParameterMap().get("userChannelType");
			String userChannelType = userChannelTypeObj == null ? "" : Arrays.toString((String[]) userChannelTypeObj);
			String ip = IPTools.getIpAddress(request);
			String requestHeaders=parseRequestHeader(request);
			setStartTime(System.currentTimeMillis());
			setLogs(uri, url, paramMap, vistorTime, userChannelType, ip,requestHeaders);
		} catch (IOException e) {
			BaseLogger.error("UserLogsDTO-->parseRequest-->解析request消息失败"+e);
		}
	}
	
	private String parseRequestHeader(HttpServletRequest request){
		Enumeration enu=request.getHeaderNames();//取得全部头信息
		Map<String,Object> resultMap=new HashMap<String,Object>();
		while(enu.hasMoreElements()){//以此取出头信息
			String headerName=(String)enu.nextElement();
			String headerValue=request.getHeader(headerName);//取出头信息内容
			resultMap.put(headerName, headerValue);
		}
		return JSON.toJSONString(resultMap);
	}
	
	
	public void setLogs(String uri,String url,String requestParams,String vistorTime,String userChannelType,String ip,String requestHeader){
		setUri(uri);
		setUrl(url);
		setRequestParams(requestParams);
		setUserChannelType(userChannelType);
		setVistorTime(vistorTime);
		setIp(ip);
		setRequestHeader(requestHeader);
	}
	
	public Long getStartTime() {
		return startTime;
	}


	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}


	public Long getEndTime() {
		return endTime;
	}




	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}




	public String getRequestHeader() {
		return requestHeader;
	}

	public void setRequestHeader(String requestHeader) {
		requestHeader=requestHeader.length()>1000?requestHeader.substring(0,1000):requestHeader;
		this.requestHeader = requestHeader;
	}

	public String getHandlerMessage() {
		return handlerMessage;
	}

	public void setHandlerMessage(String handlerMessage) {
		handlerMessage=handlerMessage.length()>5100?handlerMessage.substring(0,5100):handlerMessage;
		this.handlerMessage = handlerMessage;
	}

	public String getExceptionMeesage() {
		return exceptionMeesage;
	}

	public void setExceptionMeesage(String exceptionMeesage) {
		exceptionMeesage=exceptionMeesage.length()>8000?exceptionMeesage.substring(0,8000):exceptionMeesage;
		this.exceptionMeesage = exceptionMeesage;
	}

	public String getConsumingTime() {
		return consumingTime;
	}

	public void setConsumingTime(String consumingTime) {
		this.consumingTime = consumingTime;
	}
	

	public String getIp() {
		return ip;
	}


	public void setIp(String ip) {
		this.ip = ip;
	}


	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getRequestParams() {
		return requestParams;
	}
	public void setRequestParams(String requestParams) {
		requestParams=requestParams.length()>2000?requestParams.substring(0,2000):requestParams;
		this.requestParams = requestParams;
	}
	public String getVistorTime() {
		return vistorTime;
	}
	public void setVistorTime(String vistorTime) {
		this.vistorTime = vistorTime;
	}
	public String getUserChannelType() {
		return userChannelType;
	}
	public void setUserChannelType(String userChannelType) {
		this.userChannelType = userChannelType;
	}
}
