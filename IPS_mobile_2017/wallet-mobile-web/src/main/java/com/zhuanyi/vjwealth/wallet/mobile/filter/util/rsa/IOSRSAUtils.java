package com.zhuanyi.vjwealth.wallet.mobile.filter.util.rsa;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

/**
 * 支持IOS端的RSA加解密（包括分段解密）
 * 
 * @author jiangkaijun
 * 
 */
public class IOSRSAUtils extends RSAFactory {

	/** 私钥文件名 */
	private static final String privateKeyName = "rsa_public_key.pem";

	/** 公钥文件名 */
	private static final String publicKeyName = "pkcs8_private_key.pem";

	/**
	 * 检查私密文件是否存在
	 * 
	 * @throws FileNotFoundException
	 */
	private void checkKeyFileExists(File privateKeyFile, File publicKeyFile)
			throws FileNotFoundException {
		if (!privateKeyFile.exists() || privateKeyFile.isDirectory()) {
			throw new FileNotFoundException("privateKey file is not exist...");
		}
		if (!publicKeyFile.exists() || publicKeyFile.isDirectory()) {
			throw new FileNotFoundException("publicKey file is not exist...");
		}
	}

	@Override
	public KeyPair getKeyPair() throws Exception {
		KeyPair keyPair = null;
		try {
			File publicKeyFile = new File(getBaseContextPath() + File.separator
					+ privateKeyName);
			File privateKeyFile = new File(getBaseContextPath()
					+ File.separator + publicKeyName);
			checkKeyFileExists(privateKeyFile, publicKeyFile);

			PublicKey publicKey = loadPublicKey(getKeyFromFile(publicKeyFile
					.getPath()));

			PrivateKey privateKey = loadPrivateKey(getKeyFromFile(privateKeyFile
					.getPath()));

			keyPair = new KeyPair(publicKey, privateKey);

		} catch (Exception ex) {
			throw ex;
		}
		return keyPair;
	}

	@SuppressWarnings("static-access")
	@Override
	public String decryptByPrivateKey(RSAPrivateKey privateKey,
			String encryString) throws Exception {
		try {
			byte[] rawData = Base64.decodeBase64(encryString.getBytes("UTF-8"));
			Cipher cipher = Cipher.getInstance(NORMAL_RSA);
			cipher.init(cipher.DECRYPT_MODE, privateKey);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int inputLen = rawData.length;
			int offSet = 0;
			byte[] cache;
			int i = 0;
			int max = 128;
			// 对数据分段解密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > max) {
					cache = cipher.doFinal(rawData, offSet, max);
				} else {
					cache = cipher.doFinal(rawData, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * max;
			}
			byte[] decryptedData = out.toByteArray();
			out.close();
			return new String(decryptedData, "UTF-8");
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * 从字符串中加载公钥
	 * 
	 * @param publicKeyStr
	 *            公钥数据字符串
	 * @throws Exception
	 *             加载公钥时产生的异常
	 */
	private RSAPublicKey loadPublicKey(String publicKeyStr) throws Exception {
		try {
			byte[] buffer = Base64.decodeBase64(publicKeyStr.getBytes());
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
			return (RSAPublicKey) keyFactory.generatePublic(keySpec);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			throw new Exception("公钥非法");
		} catch (NullPointerException e) {
			throw new Exception("公钥数据为空");
		}
	}

	/**
	 * 获取私钥
	 * 
	 * @param privateKeyStr
	 * @return
	 * @throws Exception
	 */
	public RSAPrivateKey loadPrivateKey(String privateKeyStr) throws Exception {
		try {
			byte[] buffer = Base64.decodeBase64(privateKeyStr.getBytes());
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
			throw new Exception("私钥非法");
		} catch (NullPointerException e) {
			throw new Exception("私钥数据为空");
		}
	}

	/**
	 * 根据文件路径获取key
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public String getKeyFromFile(String filePath) throws Exception {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(
				filePath));
		String line = null;
		List<String> list = new ArrayList<String>();
		while ((line = bufferedReader.readLine()) != null) {
			list.add(line);
		}
		bufferedReader.close();
		// remove the firt line and last line
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 1; i < list.size() - 1; i++) {
			stringBuilder.append(list.get(i)).append("\r");
		}
		String key = stringBuilder.toString();
		return key;
	}

}
