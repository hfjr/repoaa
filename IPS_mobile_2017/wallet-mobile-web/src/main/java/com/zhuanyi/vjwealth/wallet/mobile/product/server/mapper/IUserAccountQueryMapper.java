package com.zhuanyi.vjwealth.wallet.mobile.product.server.mapper;

import com.fab.server.annotation.Mapper;
import com.zhuanyi.vjwealth.wallet.mobile.index.server.dto.MyAssetsDTO;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.account.PersonalCenterDTO;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.account.UserEAccountHomeDTO;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.account.UserMAccountHomeDTO;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.account.UserTAccountHomeDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface IUserAccountQueryMapper {
	
	/**
	 * 财富主页初始化信息
	 * @param userId
	 * @return
	 */
	UserMAccountHomeDTO queryMAccountInfo(@Param("userId")String userId);

	/**
	 * 个人中心
	 * @param userId
	 * @return
	 */
	PersonalCenterDTO queryPersonalCenterInfo(@Param("userId")String userId);

	/**
	 * e账户主页初始化信息
	 * @param userId
	 * @return
	 */
	UserEAccountHomeDTO queryEAccountInfo(@Param("userId")String userId);

	/**
	 * @author zhangyingxuan
	 * @date 20160708
	 * T金所页面初始化信息
	 * @param userId
	 * @return
     */
	UserTAccountHomeDTO queryTAccountInfo(@Param("userId") String userId);

	/**
	 * @auth tony tang
	 * @获取T金所产品年化利率
	 * @return
     */
	String queryTaReceiveRate();
	/**
	 * @auth tony tang
	 * @服务总人数
	 * @return
	 */
	String queryCountUserNum();


	/**
	 * @auth tony tang
	 * @获取首页定期理财展示产品信息
	 * @return
	 */
	Map queryNewHandFinancialReceiveRate();

	List<MyAssetsDTO> queryMyAssets(String userId);

	Boolean queryUserHasTaAmount(@Param("userId") String userId);
	
	Map<String,String> queryEventInfo(@Param("orderNo")String orderNo);
}
