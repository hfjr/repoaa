package com.zhuanyi.vjwealth.wallet.mobile.common.server.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fab.core.logger.BaseLogger;
import com.zhuanyi.vjwealth.wallet.mobile.common.dto.UserLogsDTO;
import com.zhuanyi.vjwealth.wallet.mobile.common.server.mapper.IUserLogsMapper;

@Component("userVistorLogServer")
public class UserVistorLogServer {

	@Autowired
	private IUserLogsMapper userLogsMapper;

	@Value("${common.user.logs.thread.pool}")
	private String threadPool;

	private volatile static ExecutorService executor;

	
	public ExecutorService getSingleton() {
		if (executor == null) {
			synchronized (ExecutorService.class) {
				if (executor == null) {
					if (StringUtils.isNotBlank(threadPool) && NumberUtils.isNumber(threadPool)) {
						executor = Executors.newFixedThreadPool(Integer.parseInt(threadPool));
						return executor;
					}
					// 默认10个
					executor = Executors.newFixedThreadPool(10);
				}
			}
		}
		return executor;
	}

	/**
	 * 主要记录接口请求信息:请求参数，消息头，接口耗时，以及异常信息
	 * TODO .. 记录接口返回结果
	 * @param userLogsDTO
	 */
	public void userRequestLog(final UserLogsDTO userLogsDTO) {
		try {
			
			// 异步保存日志
			this.getSingleton().execute(new Runnable() {
				public void run() {
					saveLogs(userLogsDTO);
				}
			});
		} catch (Exception e) {
			BaseLogger.error("请求接口日志保存异常:" + e);
		}
	}

	public void saveLogs(UserLogsDTO userLogsDTO) {
		// 保存日志数据
		userLogsMapper.saveUserOperateLogs(userLogsDTO);
	}

	

	// private void showParams(HttpServletRequest request) {
	// Map map = new HashMap();
	// Enumeration paramNames = request.getParameterNames();
	// while (paramNames.hasMoreElements()) {
	// String paramName = (String) paramNames.nextElement();
	//
	// String[] paramValues = request.getParameterValues(paramName);
	// if (paramValues.length == 1) {
	// String paramValue = paramValues[0];
	// if (paramValue.length() != 0) {
	// map.put(paramName, paramValue);
	// }
	// }
	// }
	//
	// System.out.println("------------------------------");
	//
	// System.out.println(map);
	// System.out.println("------------------------------");
	//
	// Set<Map.Entry<String, String>> set = map.entrySet();
	// System.out.println("------------------------------");
	// for (Map.Entry entry : set) {
	// System.out.println(entry.getKey() + ":" + entry.getValue());
	// }
	// System.out.println("------------------------------");
	// }

	// public static void main(String[] args) {
	// new UserVistorLogServer();
	// for (int i = 0; i < 30; i++) {
	// final String URL =
	// "http://localhost/wallet-mobile//app/account/queryMyBankCards.security"
	// + i;
	// final String uri =
	// "/wallet-mobile//app/account/queryMyBankCards.security"
	// + i;
	// final String paramMap =
	// "param{\"uuid\":[\"123123sdlkfj\"],\"other\":[\"try_more,again\",\"try_more,again\"],\"test\":[\"sdlfjlkjsdf23\"],\"thread\":[\"1\"],\"userId\":[\"123989sfdoifu2938u\"]}"
	// + i;
	// executor.execute(new Runnable() {
	// @Override
	// public void run() {
	// saveLo
	// }
	// });
	// }
	// }
}
