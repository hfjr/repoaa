package com.zhuanyi.vjwealth.wallet.mobile.filter;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fab.core.logger.BaseLogger;
import com.fab.core.util.ControllerUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhuanyi.vjwealth.wallet.mobile.common.util.IPTools;
import com.zhuanyi.vjwealth.wallet.mobile.filter.util.rsa.RSAFactory;
import com.zhuanyi.vjwealth.wallet.mobile.filter.util.sign.PairsUtils;
import com.zhuanyi.vjwealth.wallet.mobile.filter.util.sign.SignUtils;
import com.zhuanyi.vjwealth.wallet.mobile.user.util.AES;

/**
 * App端数据RSA解密，以及MD5签名的校验
 * 
 * @author jiangkaijun
 * 
 */
public class UserSecurityFilter extends OncePerRequestFilter {

	protected List<String> filterPaths = Lists.newArrayList();

	private static final ThreadLocal<String> threadLocal = new ThreadLocal<String>();

	private static final String CONFIG_PATH = "configPath";

	private static final String MATCH_URL = "match_url";

	private static final String FILTER_KEY_WORD = "data_key_body";

	private static final String APP_KEY_NAME = "appKey";

	private static final String APP_SIGN_NAME = "sign";
	//
	private static final String APP_SOURCE_TYPE = "source";

	private static final String APP_VER_KEY = "ver";

	public static final String APP_AES_SECRET = "42def83434u7asdfu345e82345n73453453";

	private static final Map<String, String> appSecret = Maps.newHashMap();

	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

	static {
//		appSecret.put("appkey0000001", "c2eewecc00d2az4f8majeeifmohfz3er");
		appSecret.put("appkey0000001", "cd59w63d3g4yh22b5gss68r3f2hfz3er");
		appSecret.put("appkey0000002", "5asdf54lsuemdc863ksda73kdx73u73j");
	}

	public static String getThreadLocal() {
		return threadLocal.get();
	}

	private static final String CHARSET_ENCODING = "UTF-8";

	public MultiValueMap<String, String> convert(String inputString, boolean needDecode) throws IOException, HttpMessageNotReadableException {

		String[] pairs = PairsUtils.tokenizeToStringArray(inputString, "&");

		MultiValueMap<String, String> result = new LinkedMultiValueMap<String, String>(pairs.length);
		for (String pair : pairs) {
			int idx = pair.indexOf('=');
			if (idx == -1) {
				result.add(URLDecoder.decode(pair, CHARSET_ENCODING), null);
			} else {
				String name = URLDecoder.decode(pair.substring(0, idx), CHARSET_ENCODING);
				String value = needDecode ? URLDecoder.decode(pair.substring(idx + 1), CHARSET_ENCODING) : pair.substring(idx + 1);
				result.add(name, value);
			}
		}
		return result;
	}

	/**
	 * 解密参数
	 * 
	 * @param encryText
	 * @return
	 */
	private String decryRequestParamString(String encryString, String sourceType) throws Exception {
		if (sourceType.equals("wap")) { // weixin
			return encryString;
		} else {
			RSAFactory factory = RSAFactory.getInstance(sourceType);
			if (factory == null) {
				throw new Exception("错误的sourceType");
			}
			KeyPair keyPair = factory.getKeyPair();
			return factory.decryptByPrivateKey((RSAPrivateKey) keyPair.getPrivate(), encryString);
		}
	}

	public void initResourceMatchURI(FilterConfig filterConfig) throws ServletException {
		String resourcePath = filterConfig.getInitParameter(CONFIG_PATH);
		if (StringUtils.isBlank(resourcePath)) {
			throw new ServletException(CONFIG_PATH);
		}

		Properties properties = loadPropertiesFromPath(resourcePath);
		if (properties != null) {
			if (properties.containsKey(MATCH_URL)) {
				String matchurls = properties.getProperty(MATCH_URL);
				if (StringUtils.isNotBlank(matchurls)) {
					if (matchurls.indexOf(",") > 0) {
						String[] split = matchurls.split(",");
						Collections.addAll(filterPaths, split);
					} else {
						filterPaths.add(matchurls);
					}
				}
			}
		}
	}

	protected Properties loadPropertiesFromPath(String path) throws ServletException {
		Properties properties = null;
		if (path != null) {
			try {
				properties = getServletContextPropertiesResource(path);
			} catch (IOException ex) {
				throw new ServletException("读取属性文件出现异常..." + ex);
			}
		}
		return properties;
	}

	protected Properties getServletContextPropertiesResource(String path) throws IOException {
		if (getServletContext() != null) {
			InputStream is = getServletContext().getResourceAsStream(path);
			if (is == null) {
				is = UserSecurityFilter.class.getClassLoader().getResourceAsStream(path);
				// InputStream is2 =
				// UserSecurityFilter.class.getClassLoader().getResourceAsStream("urlmatch.properties");
			}
			if (is != null) {
				Properties properties = new Properties();
				properties.load(is);
				if (properties.isEmpty()) {
					BaseLogger.warn("初始化UserSecurityFilter过滤器,properties配置文件内容为空...");
				}
				return properties;
			}
		}
		return null;
	}

	protected boolean isMatchUrl(HttpServletRequest request) {
		return filterPaths.contains(request.getRequestURI());
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		if (isMultipart(request)) {

		} else {
			// 看是否做AES加密，还是放在第一步
			String tokenFlag = request.getParameter(FILTER_KEY_WORD);

			boolean doFilter = StringUtils.isBlank(tokenFlag) ? false : true;

			// 如果是匹配的URL,则必须走过滤
			if (isMatchUrl(request)) {
				doFilter = true;
			}

			if (doFilter) {

				String sourceType = request.getParameter(APP_SOURCE_TYPE);

				if (StringUtils.isBlank(sourceType)) {
					writeExcepetion(response, "660", "错误的请求参数");
					return;
				}
				String decryptString = null;
				// RSA参数解密
				try {
					decryptString = decryRequestParamString(tokenFlag, sourceType);
				} catch (Exception e) {
					BaseLogger.audit("解密参数出现错误：元参数[" + tokenFlag + "]，解密后：[" + decryptString + "],IP:[" + IPTools.getIpAddress(request) + "]");
					writeExcepetion(response, "660", "错误的请求参数");
					return;
				}

				MultiValueMap<String, String> parameters = null;

				if (!sourceType.equals("wap")) {
					String version = request.getParameter(APP_VER_KEY);
					parameters = convert(decryptString, (StringUtils.isNotBlank(version) && version.equals("1.0.0")));
				} else {
					Enumeration e = request.getParameterNames();
					parameters = new LinkedMultiValueMap<String, String>();
					while (e.hasMoreElements()) {
						String paramName = (String) e.nextElement();
						String paramValue = request.getParameter(paramName);
						if (!isEngiore(paramName)) {
							// System.out.println(paramName+":"+paramValue);
							parameters.add(paramName, paramValue);
						}
					}
				}

				String appKey = parameters.getFirst(APP_KEY_NAME);

				if (StringUtils.isBlank(appKey)) {
					// 必须有效的appName
					BaseLogger.audit("Miss App Key,元参数[" + tokenFlag + "]，解密后：[" + decryptString + "]");
					writeExcepetion(response, "661", "Miss App Key!");
					return;
				}

				String appKeySecret = appSecret.get(appKey);
				if (StringUtils.isBlank(appKeySecret)) {
					// 无效的appKeySecret
					BaseLogger.audit("Invalid App Key,元参数[" + tokenFlag + "]，解密后：[" + decryptString + "]");
					writeExcepetion(response, "662", "Invalid App Key");
					return;
				}

				String sign = parameters.getFirst(APP_SIGN_NAME);
				if (StringUtils.isBlank(sign)) {
					// 无效的签名字段
					BaseLogger.audit("Missing Sign,元参数[" + tokenFlag + "]，解密后：[" + decryptString + "]");
					writeExcepetion(response, "663", "Missing Sign");
					return;
				}

				if (sourceType.equals("wap")) { // weixin
					// 2、验证签名
					String mySign = SignUtils.getSignForMultiValueMap(parameters, appKeySecret);

					if (!mySign.equals(sign)) {
						BaseLogger.audit("Invalid Sign Key,元参数[" + tokenFlag + "]，解密后：[" + decryptString + "]");
						// 无效的数据
						writeExcepetion(response, "664", "Invalid Sign Key");
						return;
					}
				}

				if (!sourceType.equals("wap")) {
					// 3、将新值重新set到request里面
					request = new HttpPutFormContentRequestWrapper((HttpServletRequest) request, parameters);
				}

			}
		}
		filterChain.doFilter(request, response);
	}

	private boolean isEngiore(String key) {
		if (key.equals("data_key_body") || key.equals("source")) {
			return true;
		}
		return false;
	}

	public boolean isMultipart(HttpServletRequest request) {
		return (request != null && ServletFileUpload.isMultipartContent(request));
	}

	public String filterDangerString(String value) {
		if (value == null) {
			return null;
		}
		value = new String(AES.encrypt(value, APP_AES_SECRET));
		// 这里做AES解密操作
		return value;
	}

	private static class HttpPutFormContentRequestWrapper extends HttpServletRequestWrapper {

		private MultiValueMap<String, String> formParameters;

		public HttpPutFormContentRequestWrapper(HttpServletRequest request, MultiValueMap<String, String> parameters) {
			super(request);
			this.formParameters = (parameters != null) ? parameters : new LinkedMultiValueMap<String, String>();
		}

		@Override
		public String getParameter(String name) {
			String queryStringValue = super.getParameter(name);
			String formValue = this.formParameters.getFirst(name);
			return (queryStringValue != null) ? queryStringValue : formValue;
		}

		@Override
		public Map<String, String[]> getParameterMap() {
			Map<String, String[]> result = new LinkedHashMap<String, String[]>();
			Enumeration<String> names = this.getParameterNames();
			while (names.hasMoreElements()) {
				String name = names.nextElement();
				result.put(name, this.getParameterValues(name));
			}
			return result;
		}

		@SuppressWarnings("unchecked")
		@Override
		public Enumeration<String> getParameterNames() {
			Set<String> names = new LinkedHashSet<String>();
			names.addAll(Collections.list(super.getParameterNames()));
			names.addAll(this.formParameters.keySet());
			return Collections.enumeration(names);
		}

		@Override
		public String[] getParameterValues(String name) {
			String[] queryStringValues = super.getParameterValues(name);
			List<String> formValues = this.formParameters.get(name);
			if (formValues == null) {
				return queryStringValues;
			} else if (queryStringValues == null) {
				return formValues.toArray(new String[formValues.size()]);
			} else {
				List<String> result = new ArrayList<String>();
				result.addAll(Arrays.asList(queryStringValues));
				result.addAll(formValues);
				return result.toArray(new String[result.size()]);
			}
		}
	}

	private void writeExcepetion(HttpServletResponse response, String code, String message) throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter pw = response.getWriter();
		pw.print(new ObjectMapper().writeValueAsString(ControllerUtils.getErrorResponseWithKeyCodeAndMessageBody(code, message, null)));
		pw.flush();
		pw.close();
	}

	@Override
	public void initFilterBean() throws ServletException {
		// TODO Auto-generated method stub
		initResourceMatchURI(getFilterConfig());
		super.initFilterBean();
	}

}
