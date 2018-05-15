package com.zhuanyi.vjwealth.wallet.mobile.credit.server.impl;

import com.fab.core.annotation.Remote;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.IPdfFileOperationService;
import com.zhuanyi.vjwealth.wallet.service.file.upload.server.service.ICommonAttachmentOperate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
public class PdfFileOperationService implements IPdfFileOperationService {

	@Remote
	private ICommonAttachmentOperate commonAttachmentOperate;
	/**
	 *	pdf文件上传
	 * @param fileName
	 * @param fileBytes
	 * @return
	 * @throws IOException
     */
	public String uploadFile(String fileName, byte[] fileBytes) throws IOException {
		return commonAttachmentOperate.saveAttachementAndReturnFileId(fileName, fileBytes, "contract-pdf");
	}

	/**
	 * 加载pdf文件
	 * @param fileNo
     * @return
     */
	public byte[] loadFile(String fileNo) {
		return commonAttachmentOperate.downloadFile(fileNo);
	}

}
