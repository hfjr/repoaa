package com.zhuanyi.vjwealth.wallet.mobile.user.server.integration.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;

@Mapper
public interface IReportMapper {
	/**
	 * 查询近count天数据
	 * 
	 * @param count
	 * @return
	 */
	List<Map<String, Object>> queryEaWanReceiveList(@Param("count") int count);

	/**
	 * @author zhangyingxuan
	 * @date 20160726
	 * 存钱罐首页图表
	 * @param count
	 * @return
     */
	List<Map<String, Object>> queryTaWanReceiveList(@Param("count") int count);
}
