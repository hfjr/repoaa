package com.zhuanyi.vjwealth.wallet.mobile.filter.util.rsa;

import java.io.File;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;

public abstract class RSAFactory {

	/** 非对称加密密钥算法(默认加密方式) */
	public static final String NORMAL_RSA = "RSA";

	private static final String FLOWER_NAME = "keystore";

	/**
	 * 获取密钥对象（包含公钥和私钥）
	 * 
	 * @return
	 */
	public abstract KeyPair getKeyPair() throws Exception;
	
	/**
	 * 根据私钥进行解密（需支持大数据的分段解密）
	 * 
	 * @param privateKey
	 * @param encryString
	 * @return
	 */
	public abstract String decryptByPrivateKey(RSAPrivateKey privateKey,
			String encryString) throws Exception;

	/**
	 * 获取基本的文件存储目录
	 * 
	 * @return
	 */
	public String getBaseContextPath() {
		String urlPath = RSAFactory.class.getResource("/").getPath();
		return (new File(urlPath).getParent() + File.separator + FLOWER_NAME);
	}

	/**
	 * 获得加解密的实例对象
	 * 
	 * @param encryString
	 * @param source
	 * @return
	 */
	public static RSAFactory getInstance(String source) {
		RSAFactory factory = null;
		if (source.equals("wap")) { // weixin
			factory = new JSRSAUtils();
		} else if (source.equals("native")) { // android
			factory = new AndroidRSAUtils();
		} else if (source.equals("ios")) {
			factory = new IOSRSAUtils();
		}
		return factory;
	}
}
