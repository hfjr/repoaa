package com.zhuanyi.vjwealth.wallet.mobile.user.server.integration.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;
import com.zhuanyi.vjwealth.wallet.mobile.user.dto.HelpCenterTypeDTO;
import com.zhuanyi.vjwealth.wallet.mobile.user.dto.HelpCenterTypeSubDetailDTO;

@Mapper
public interface UserOperateMapper {
	//保持意见反馈
	void saveUserFeedback(@Param("userId")String userId,@Param("advice")String advice);
	
	//根据用户ID查询用户的手机号
	String queryUserPhoneByUserId(@Param("userId")String userId);
	
	//查询用户是否购买过v+理财
	int countUserPurchaseV1(@Param("userId")String userId);
	
	//用户绑卡时,查询客户默认信息(姓名,身份证号,手机号)
	Map<String,String> queryUserDefaultInfo(@Param("userId")String userId);

	//查询图形报表
	List<Map<String, Object>> queryUserTotolAccountAmountReportData(@Param("userId")String userId);
	
	//更新消息变为已读
	void updateUserMessageHaveRead(@Param("userId")String userId,@Param("messageId")String messageId);
	
	//查询客服热线
	String queryServiceHotLine();
	
	//购买v1理财,协议中需要的用户信息(姓名,证件类型,身份证号,手机号)
	Map<String,String> queryV1AgreementUserInfo(@Param("userId")String userId);
	
	//保持头像图片
	void saveHeadPicture(Map<String,Object> paramMap);
	
	//更新用户的headSculptureId
	void updateUserHeadPicture(@Param("userId")String userId,@Param("headSculptureId")String headSculptureId);
	
	//查询文件信息
	Map<String,String> queryHeadPicturById(@Param("userId")String userId);
	
	//删除消息
	void deleteUserMessage(@Param("messageId")String messageId);

	//保存手机日志信息
	void saveUserDeviceLog(Map<String, String> paramMaps);
	
	//注册用户只能绑定一张卡
	int countRegisterCard(@Param("userId")String userId);
	
	//注册用户只能绑定一张卡
	String checkCountRegisterCard();
	
	//新增预约标识
	void addIsAppointFinancialFlag(@Param("userId")String userId);
	//更新预约标识
	int updatesAppointFinancialFlag(@Param("userId")String userId);
	//是否成功预约
	int countUserAppiontFinancial(@Param("userId")String userId);
	
	
	
	/**
	 * @deprecated 属于过度接口,应当在所有用户v1理财迁移完成后,销毁此接口
	 * @since 3.0
	 * @author Eric
	 * @param userId
	 * @return
	 */
	int countUserV1ExitForV30(@Param("userId")String userId);
	
	/**
	 * 查询图形报表V3.0
	 * V+下架
	 * V理财(定期理财上线)
	 * @param userId
	 * @since 3.0
	 */
	List<Map<String, Object>> queryUserTotolAccountAmountReportDataForV30(@Param("userId")String userId,@Param("v1ExitFlag")Boolean v1ExitFlag);

	
	/**
	 * 获取帮助中心列表信息
	 * @since 3.3
	 * @return
	 */
	List<HelpCenterTypeDTO> getHelpCenterType();
	
	/**
	 * 获取帮助中心条目详情
	 * @return
	 * @since 3.3
	 */
	HelpCenterTypeSubDetailDTO getHelpCenterTypeSubDetail(@Param("id")String id);
	
	//保存用户设备Id信息
	void saveUserAppDevice(@Param("userId")String userId,@Param("deviceId")String deviceId,@Param("deviceType")String deviceType,@Param("otherData")String otherData);
	//查询用户设备Id状态
	String getUserAppDeviceStatus(@Param("userId")String userId,@Param("deviceId")String deviceId,@Param("deviceType")String deviceType);
	//更新用户设备状态 为有效
	void updateUserAppDeviceEffective(@Param("userId")String userId,@Param("deviceId")String deviceId,@Param("deviceType")String deviceType,@Param("otherData")String otherData);
	//更新用户设备状态为无效
	void updateUserAppDeviceInvalid(@Param("userId")String userId,@Param("deviceId")String deviceId,@Param("deviceType")String deviceType);
}
