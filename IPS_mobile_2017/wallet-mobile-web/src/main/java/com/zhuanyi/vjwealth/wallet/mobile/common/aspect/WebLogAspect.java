package com.zhuanyi.vjwealth.wallet.mobile.common.aspect;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fab.core.logger.BaseLogger;
import com.zhuanyi.vjwealth.wallet.mobile.common.dto.LogHttpRequestDTO;
import com.zhuanyi.vjwealth.wallet.mobile.common.server.service.ILogHttpRequestService;

/**
 * 配置日志AOP
 * 
 */
public class WebLogAspect {
	
	public static final String LOG_INTERFACE_TYPE = "http request";
	public static final String LOG_USER_ID = "userId";
	public static final String LOG_NO_DATA = "无数据";

	@Autowired
    private ILogHttpRequestService logHttpRequestService;

	/**
	 * 添加业务逻辑方法切入点
	 */
	@Pointcut("execution(public * com.zhuanyi.vjwealth.wallet.mobile.human.server.impl.HumanHttpRequestService.*(..))")
	public void serviceCall() {
	}

	/**
	 * 接口调用日志记录
	 * 
	 * @param joinPoint
	 * @param rtv
	 * @throws Throwable
	 */
	@AfterReturning(value = "serviceCall()", argNames = "rtv", returning = "rtv")
	public void saveServiceCalls(JoinPoint joinPoint, Object rtv) throws Throwable {
	List<Object> args = new ArrayList<>();
	JSONArray array = new JSONArray();
		for (Object object : joinPoint.getArgs()) {
			array.add(object);
		}
		if(!"getContract".equals(joinPoint.getSignature().getName())){
			BaseLogger.info("args:" + args.toString());
		}
	
		
		LogHttpRequestDTO logHttpRequestDTO = new LogHttpRequestDTO();
		logHttpRequestDTO.setInterfaceType(LOG_INTERFACE_TYPE);
		logHttpRequestDTO.setInterfaceName(joinPoint.getSignature().getName());
		logHttpRequestDTO.setInterfacePath(joinPoint.getSignature().getDeclaringTypeName());
		logHttpRequestDTO.setReqContent(array.toJSONString());
		logHttpRequestDTO.setResContent((rtv == null||"null".equals(rtv)) ? LOG_NO_DATA : JSONObject.toJSONString(rtv));
		
		
		String userId = "";
		if(array!=null&&array.size()>0){
			for(int i=0;i<array.size();i++){
				try {
					JSONObject object = array.getJSONObject(i);
					userId = object.getString(LOG_USER_ID);
					if(StringUtils.isNotBlank(userId))
						break;
				} catch (Exception e) {
				}
			}
		}
		logHttpRequestDTO.setUserId(userId);

		logHttpRequestService.addLogHttpRequest(logHttpRequestDTO);
	}

}

