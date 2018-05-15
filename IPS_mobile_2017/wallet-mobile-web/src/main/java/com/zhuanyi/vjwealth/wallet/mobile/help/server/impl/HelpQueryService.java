package com.zhuanyi.vjwealth.wallet.mobile.help.server.impl;

import com.fab.core.annotation.Remote;
import com.zhuanyi.vjwealth.wallet.mobile.help.server.IHelpQueryService;
import com.zhuanyi.vjwealth.wallet.service.file.upload.server.service.ICommonAttachmentOperate;
import org.springframework.stereotype.Service;

/**
 * 
 * @ClassName HelpQueryService
 * @Description TODO (这里用一句话描述这个类的作用)
 * @Date 2016年9月30日 上午9:51:06
 * @author hanwei
 */
@Service
public class HelpQueryService implements IHelpQueryService {

	@Remote
	private ICommonAttachmentOperate commonAttachmentOperate;

	/**
	 * 
	 * @Title queryAppHelpIcon
	 * @Description 查询icon图片信息
	 * @Date 2016年9月30日 上午10:54:04
	 * @author hanwei
	 * @param iconUrl
	 * @return
	 */
	@Override
	public byte[] queryAppHelpIcon(String iconUrl) {
		return commonAttachmentOperate.downloadFile(iconUrl);
	}
}

