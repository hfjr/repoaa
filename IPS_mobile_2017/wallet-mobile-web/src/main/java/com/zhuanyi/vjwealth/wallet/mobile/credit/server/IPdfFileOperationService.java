package com.zhuanyi.vjwealth.wallet.mobile.credit.server;

import java.io.IOException;

/**
 * @author  tony tang
 *
 */
public interface IPdfFileOperationService {

	/**
	 * 加载pdf
	 * @param fileNo
	 * @return
     */
	byte[] loadFile(String fileNo);
	/**
	 * 上传pdf文件
	 * @param fileBytes
	 * @param fileName
	 * @throws IOException
	 */
	String uploadFile(String fileName, byte[] fileBytes) throws IOException;
}
