package com.zhuanyi.vjwealth.wallet.mobile.filter.util.sign;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.MultiValueMap;

import com.zhuanyi.vjwealth.wallet.mobile.user.util.CryptUtils;

/**
 * 数字签名工具类
 * 
 * @author jiangkaijun
 * 
 */
public class SignUtils {

	public static String getSignForMultiValueMap(
			MultiValueMap<String, String> paramMap, String privateKey) {

		Properties properties = new Properties();
		for (String key : paramMap.keySet()) {
			if (key == null || key.equalsIgnoreCase("sign")) {
				continue;
			}
			properties.setProperty(key, paramMap.getFirst(key));
		}
		String content = getSignContent(properties);

		return sign(content, privateKey);
	}

	public static String getSign(Map<String, String> paramMap, String privateKey) {

		Properties properties = new Properties();

		for (String key : paramMap.keySet()) {

			if (key == null || key.equalsIgnoreCase("sign")) {

				continue;
			}
			properties.setProperty(key, paramMap.get(key));
		}
		String content = getSignContent(properties);

		return sign(content, privateKey);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String getSignContent(Properties properties) {

		StringBuffer content = new StringBuffer();
		List<String> keys = new ArrayList(properties.keySet());
		Collections.sort(keys);

		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			content.append((i == 0 ? "" : "&") + key + "="
					+ properties.getProperty(key));
		}
		return content.toString();
	}

	private static String sign(String content, String privateKey) {

		if (content == null)
			return null;

		content = content + privateKey;
		return CryptUtils.encryptToMD5(content);
	}

	public static String changeMd5(Map<String, String> paramMap,
			String privateKey) {

		String md5 = getSign(paramMap, privateKey);
		return changeMd5(md5);
	}

	/**
	 * MD5移位处理
	 * 
	 * @param md5Value
	 * @return
	 */
	public static String changeMd5(String md5Value) {

		if (StringUtils.isEmpty(md5Value) || md5Value.length() != 32) {
			return md5Value;
		}

		int length = md5Value.length() / 2;
		// 1.32位编码中间拆分两段；
		String firstMd5 = md5Value.substring(0, length);
		String secondFirst = md5Value.substring(length, md5Value.length());

		// 2.分别将每段16位编码首尾元素互换；
		StringBuffer sbfirstMd5 = new StringBuffer(firstMd5);
		sbfirstMd5.insert(0, sbfirstMd5.substring(length - 1, length));
		sbfirstMd5.delete(1, 2);
		sbfirstMd5.insert(length, firstMd5.substring(0, 1));
		sbfirstMd5.delete(length - 1, length);

		StringBuffer sbsecondFirst = new StringBuffer(secondFirst);
		sbsecondFirst.insert(0, sbsecondFirst.substring(length - 1, length));
		sbsecondFirst.delete(1, 2);
		sbsecondFirst.insert(length, secondFirst.substring(0, 1));
		sbsecondFirst.delete(length - 1, length);

		// 3.将两段16位编码分别对半分割得到4段八位编码；
		int sbfirstMd5_length = sbfirstMd5.length() / 2;
		String sbfirstMd5_1 = sbfirstMd5.toString().substring(0,
				sbfirstMd5_length);
		String sbfirstMd5_2 = sbfirstMd5.toString().substring(
				sbfirstMd5_length, length);

		int sbsecondFirst_length = sbsecondFirst.length() / 2;
		String sbsecondMd5_1 = sbsecondFirst.toString().substring(0,
				sbsecondFirst_length);
		String sbsecondMd5_2 = sbsecondFirst.toString().substring(
				sbsecondFirst_length, sbsecondFirst.length());
		// 4.将4段八位编码第一与第三段互换、第二与第四段互换，再按此顺序拼接成32位编码；
		// sbfirstMd5_1，sbfirstMd5_2，sbsecondMd5_1，sbsecondMd5_2

		StringBuffer sb_append32 = new StringBuffer(sbsecondMd5_1);
		sb_append32.append(sbsecondMd5_2);
		sb_append32.append(sbfirstMd5_1);
		sb_append32.append(sbfirstMd5_2);

		return sb_append32.toString();
	}

}
