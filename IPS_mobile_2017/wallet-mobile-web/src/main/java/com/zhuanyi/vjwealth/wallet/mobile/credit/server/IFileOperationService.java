package com.zhuanyi.vjwealth.wallet.mobile.credit.server;

import com.fab.core.exception.service.AppException;
import com.zhuanyi.vjwealth.loan.order.vo.UploadIdentitysVO;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.UploadIdentityPhotosDTO;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Administrator
 *
 */
public interface IFileOperationService {

	/**
	 * 加载图片
	 * 
	 * @param fileNo
	 * @return
	 */
	byte[] loadFile(String fileNo);

	/**
	 * 身份证正面文件上传
	 * 
	 * @param userId
	 * @param rightSideFile
	 * @param reverseSideFile
	 * @param uploadSuccessFileId
	 *            [只有一个文件上传成功，第二次上传使用]
	 * @return
	 */
	UploadIdentityPhotosDTO uploadIdentityPhotos(String userId, MultipartFile rightSideFile,
                                                 MultipartFile reverseSideFile, MultipartFile handheldIdentity, String uploadSuccessFileId,
                                                 String borrowCode) throws AppException;

	/**
	 * 身份证(正面，反面，手持)文件上传
	 *
	 * @param uploadIdentitysVO
	 *
	 * @return
	 */
	UploadIdentityPhotosDTO saveUploadIdentitys(UploadIdentitysVO uploadIdentitysVO) throws AppException;
}
