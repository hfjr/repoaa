package com.zhuanyi.vjwealth.wallet.mobile.filter.util.rsa;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.net.URLDecoder;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;

import javax.crypto.Cipher;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

public class JSRSAUtils extends RSAFactory {

	/** 密钥文件名 */
	private static final String RSA_PAIR_FILENAME = "/_RSA_PAIR.security";

	/** js加密字段分隔符 */
	private static final String RSA_LONG_SPLIT = " ";

	@Override
	public KeyPair getKeyPair() throws Exception {
		File file = new File(getBaseContextPath() + File.separator
				+ RSA_PAIR_FILENAME);
		if(!file.exists() || file.isDirectory()){
			throw new Exception("javascript keystore is not exists...");
		}
		return readKeyPair(file);
	}

	@Override
	public String decryptByPrivateKey(RSAPrivateKey privateKey,
			String encryString) throws Exception {
		StringBuffer buffer = new StringBuffer();
		if (encryString.indexOf(RSA_LONG_SPLIT) > 0) {
			// 分段解密
			String[] fenduan = encryString.split(RSA_LONG_SPLIT);
			for (String encrypttext : fenduan) {
				String result = decryptString(encrypttext, privateKey);
				buffer.append(result);
			}
		} else {
			buffer.append(decryptString(encryString, privateKey));
		}
		return URLDecoder.decode(buffer.toString(), "UTF-8");
	}

	/**
	 * 解密
	 * @param encrypttext
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	private String decryptString(String encrypttext, PrivateKey privateKey)
			throws Exception {
		if (StringUtils.isBlank(encrypttext)) {
			return null;
		}
		StringBuffer buffer = new StringBuffer();

		byte[] en_data = hexString2Bytes(encrypttext);
		byte[] bs = decrypt(privateKey, en_data);
		buffer.append(new String(bs, "UTF-8"));
		return buffer.reverse().toString();
	}
	
	/**
	 * * 解密 *
	 * 
	 * @param key
	 *            解密的密钥 *
	 * @param raw
	 *            已经加密的数据 *
	 * @return 解密后的明文 *
	 * @throws Exception
	 */
	@SuppressWarnings({ "all" })
	private static byte[] decrypt(PrivateKey pk, byte[] raw) throws Exception {
		try {
			Cipher cipher = Cipher.getInstance("RSA",
					new org.bouncycastle.jce.provider.BouncyCastleProvider());
			cipher.init(cipher.DECRYPT_MODE, pk);
			int blockSize = cipher.getBlockSize();
			ByteArrayOutputStream bout = new ByteArrayOutputStream(64);
			int j = 0;

			while (raw.length - j * blockSize > 0) {
				bout.write(cipher.doFinal(raw, j * blockSize, blockSize));
				j++;
			}
			return bout.toByteArray();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * 读取密钥文件
	 * 
	 * @param file
	 * @return
	 */
	private static KeyPair readKeyPair(File file) throws Exception {
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		if (!file.exists() || file.isDirectory()) {
			throw new Exception("keypair files is not exists...");
		}
		try {
			fis = FileUtils.openInputStream(file);
			ois = new ObjectInputStream(fis);
			return (KeyPair) ois.readObject();
		} catch (Exception ex) {
			throw ex;
		} finally {
			IOUtils.closeQuietly(ois);
			IOUtils.closeQuietly(fis);
		}
	}

	/**
	 * 将String字符串转byte[]
	 * 
	 * @param src
	 * @return
	 */
	public static byte[] hexString2Bytes(String src) {
		int l = src.length() / 2;
		byte[] ret = new byte[l];
		for (int i = 0; i < l; i++) {
			ret[i] = (byte) Integer
					.valueOf(src.substring(i * 2, i * 2 + 2), 16).byteValue();
		}
		return ret;
	}

}
