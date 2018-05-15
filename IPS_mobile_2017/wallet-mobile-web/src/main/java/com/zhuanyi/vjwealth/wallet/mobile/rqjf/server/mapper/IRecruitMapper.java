package com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;

@Mapper
public interface IRecruitMapper {

	//查询职位列表
	public List<Map<String,Object>> queryRecruitList(@Param("page")int page);
	
	
	//查询职位详情
	public Map<String,Object> queryRecruitDetail(@Param("recruitId")String recruitId);
	
	//检查是否可预约
	public Integer checkCanApply(@Param("userId")String userId,@Param("recruitId")String recruitId);
	
	
	/**
	 * 招聘申请初始化
	 * 
	 * @return
	 */
	public Map<String,Object> applyInit(@Param("userId")String userId);
	
	
	/**
	 * 招聘申请

	 * @return
	 */
	public void doApply(@Param("recruitId")String recruitId,@Param("userId")String userId,@Param("realName")String realName,@Param("applyPhone")String applyPhone,@Param("workExperience")String workExperience,@Param("salaryExpectation")String salaryExpectation);
	
	

	
}
