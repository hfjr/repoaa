package com.zhuanyi.vjwealth.wallet.mobile.help.server;

/**
 * 
 * @ClassName IHelpQueryService
 * @Description TODO (这里用一句话描述这个类的作用)
 * @Date 2016年9月30日 上午9:51:16
 * @author hanwei
 */
public interface IHelpQueryService {

	/**
	 * 
	 * @Title queryAppHelpIcon
	 * @Description 查询icon图片信息
	 * @Date 2016年9月30日 上午9:51:49
	 * @author hanwei
	 * @param iconUrl
	 * @return
	 */
	byte[] queryAppHelpIcon(String iconUrl);
}

