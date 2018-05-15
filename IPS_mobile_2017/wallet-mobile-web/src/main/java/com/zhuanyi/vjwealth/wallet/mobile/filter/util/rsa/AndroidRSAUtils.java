package com.zhuanyi.vjwealth.wallet.mobile.filter.util.rsa;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class AndroidRSAUtils extends RSAFactory {

	private static final String FILENAME = "_APP_RSA_PAIR.security";
	private static final String ECB_PKCS1_PADDING = "RSA/ECB/PKCS1Padding";// 加密填充方式
	private static final byte[] DEFAULT_SPLIT = "#PART#".getBytes(); // 当要加密的内容超过bufferSize，则采用partSplit进行分块加密

	@Override
	public KeyPair getKeyPair() throws Exception {
		// TODO Auto-generated method stub
		File file = new File(getBaseContextPath() + File.separator + FILENAME);
		if (!file.exists() || file.isDirectory()) {
			throw new Exception("android key file is not exists....");
		}
		return readKeyPair(file);
	}

	@Override
	public String decryptByPrivateKey(RSAPrivateKey priKey, String encryString)
			throws Exception {
		byte[] privateKey = priKey.getEncoded();

		byte[] encrypted = Base64.decodeBase64(encryString.getBytes("UTF-8"));

		int splitLen = DEFAULT_SPLIT.length;
		if (splitLen <= 0) {
			return new String(decryptByPrivateKey(encrypted, privateKey));
		}
		int dataLen = encrypted.length;
		List<Byte> allBytes = new ArrayList<Byte>(1024);
		int latestStartIndex = 0;
		for (int i = 0; i < dataLen; i++) {
			byte bt = encrypted[i];
			boolean isMatchSplit = false;
			if (i == dataLen - 1) {
				// 到data的最后了
				byte[] part = new byte[dataLen - latestStartIndex];
				System.arraycopy(encrypted, latestStartIndex, part, 0,
						part.length);
				byte[] decryptPart = decryptByPrivateKey(part, privateKey);
				for (byte b : decryptPart) {
					allBytes.add(b);
				}
				latestStartIndex = i + splitLen;
				i = latestStartIndex - 1;
			} else if (bt == DEFAULT_SPLIT[0]) {
				// 这个是以split[0]开头
				if (splitLen > 1) {
					if (i + splitLen < dataLen) {
						// 没有超出data的范围
						for (int j = 1; j < splitLen; j++) {
							if (DEFAULT_SPLIT[j] != encrypted[i + j]) {
								break;
							}
							if (j == splitLen - 1) {
								// 验证到split的最后一位，都没有break，则表明已经确认是split段
								isMatchSplit = true;
							}
						}
					}
				} else {
					// split只有一位，则已经匹配了
					isMatchSplit = true;
				}
			}
			if (isMatchSplit) {
				byte[] part = new byte[i - latestStartIndex];
				System.arraycopy(encrypted, latestStartIndex, part, 0,
						part.length);
				byte[] decryptPart = decryptByPrivateKey(part, privateKey);
				for (byte b : decryptPart) {
					allBytes.add(b);
				}
				latestStartIndex = i + splitLen;
				i = latestStartIndex - 1;
			}
		}
		byte[] bytes = new byte[allBytes.size()];
		{
			int i = 0;
			for (Byte b : allBytes) {
				bytes[i++] = b.byteValue();
			}
		}
		return new String(bytes, "UTF-8");
	}

	/**
	 * 使用私钥进行解密
	 */
	private byte[] decryptByPrivateKey(byte[] encrypted, byte[] privateKey)
			throws Exception {
		// 得到私钥
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKey);
		KeyFactory kf = KeyFactory.getInstance(NORMAL_RSA);
		PrivateKey keyPrivate = kf.generatePrivate(keySpec);

		// 解密数据
		Cipher cp = Cipher.getInstance(ECB_PKCS1_PADDING);
		cp.init(Cipher.DECRYPT_MODE, keyPrivate);
		byte[] arr = cp.doFinal(encrypted);
		return arr;
	}

	/**
	 * 同步读出保存的密钥对
	 * 
	 * @param file
	 * @return
	 */
	private synchronized KeyPair readKeyPair(File file) {
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = FileUtils.openInputStream(file);
			ois = new ObjectInputStream(fis);
			return (KeyPair) ois.readObject();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			IOUtils.closeQuietly(ois);
			IOUtils.closeQuietly(fis);
		}
		return null;
	}
}
