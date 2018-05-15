package com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.dto.CkbApplyDTO;

@Mapper
public interface ICKBMapper {

	/**
	 * 
	 * 查询创客会员初始化状态
	 * {	
	"body": {
		"applyStatus":"init",
		"phone":"18821880888",
		"errorMessage":""
	},
    "footer": {
        "status": "200"
		
    }

}
	 * @return
	 */
	public Map<String,Object> queryCKBStatus(@Param("userId")String userId);
	
	
	/**
	 * 创客会员申请
	 * 
	 * {	
	"body": {
	
	},
    "footer": {
        "status": "200",
		"message":"尊敬的用户|您的申请已提交成功"
    }

}
	 * @return
	 */
	public void doApply(CkbApplyDTO ckbApplyDTO);
	
	
	/**
	 * {
	"body": {
		"career":"主任",
		"treatmentList":[
			{"title":"基本福利","description":"销售工具,xxx","picUrl":"https://image.baidu.com/search/detail?ct=503316480&z=&tn=baiduimagedetail&ipn=d&word=%E5%B9%BF%E5%91%8Aicon&step_word=&ie=utf-8&in=&cl=2&lm=-1&st=-1&cs=3397979760,2326161216&os=769222609,420453638&simid=153068445,818039949&pn=81&rn=1&di=164246595810&ln=1966&fr=&fmq=1495963827003_R&ic=0&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&is=0,0&istype=2&ist=&jit=&bdtype=0&spn=0&pi=0&gsm=1e&objurl=http%3A%2F%2Fpic35.nipic.com%2F20131118%2F13674497_173235095373_2.jpg&rpstart=0&rpnum=0&adpicid=0"},
			{"title":"全勤奖","description":"销售工具,xxx","picUrl":"https://image.baidu.com/search/detail?ct=503316480&z=&tn=baiduimagedetail&ipn=d&word=%E5%B9%BF%E5%91%8Aicon&step_word=&ie=utf-8&in=&cl=2&lm=-1&st=-1&cs=3397979760,2326161216&os=769222609,420453638&simid=153068445,818039949&pn=81&rn=1&di=164246595810&ln=1966&fr=&fmq=1495963827003_R&ic=0&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&is=0,0&istype=2&ist=&jit=&bdtype=0&spn=0&pi=0&gsm=1e&objurl=http%3A%2F%2Fpic35.nipic.com%2F20131118%2F13674497_173235095373_2.jpg&rpstart=0&rpnum=0&adpicid=0"},
			{"title":"理财佣金","description":"销售工具,xxx","picUrl":"https://image.baidu.com/search/detail?ct=503316480&z=&tn=baiduimagedetail&ipn=d&word=%E5%B9%BF%E5%91%8Aicon&step_word=&ie=utf-8&in=&cl=2&lm=-1&st=-1&cs=3397979760,2326161216&os=769222609,420453638&simid=153068445,818039949&pn=81&rn=1&di=164246595810&ln=1966&fr=&fmq=1495963827003_R&ic=0&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&is=0,0&istype=2&ist=&jit=&bdtype=0&spn=0&pi=0&gsm=1e&objurl=http%3A%2F%2Fpic35.nipic.com%2F20131118%2F13674497_173235095373_2.jpg&rpstart=0&rpnum=0&adpicid=0"},
			{"title":"业绩奖","description":"销售工具,xxx","picUrl":"https://image.baidu.com/search/detail?ct=503316480&z=&tn=baiduimagedetail&ipn=d&word=%E5%B9%BF%E5%91%8Aicon&step_word=&ie=utf-8&in=&cl=2&lm=-1&st=-1&cs=3397979760,2326161216&os=769222609,420453638&simid=153068445,818039949&pn=81&rn=1&di=164246595810&ln=1966&fr=&fmq=1495963827003_R&ic=0&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&is=0,0&istype=2&ist=&jit=&bdtype=0&spn=0&pi=0&gsm=1e&objurl=http%3A%2F%2Fpic35.nipic.com%2F20131118%2F13674497_173235095373_2.jpg&rpstart=0&rpnum=0&adpicid=0"}
		]
	},
    "footer": {
        "status": "200"
   }

}
	 * @return
	 */
	public Map<String,Object> queryCKDetail(@Param("userId")String userId);
	
	
	/**
	 * {	
	"body": {
		
		"careerList":
		[
			{	"careerName":"会员",
				"standardList":[
					{"label":"晋级标准","labelValue":"贷款>5万元"},
					{"label":"基本标准","labelValue":"销售工具"},
					{"label":"贷款申请服务费","labelValue":"0元"},
					{"label":"理财佣金","labelValue":"1级佣金2.0/2"},
					{"label":"考核标准","labelValue":"贷款>=2单或理财>=20万"},
					{"label":"业绩奖","labelValue":"4000元"},
					{"label":"全勤奖金","labelValue":"1000现金每周例会3次"}	
				]
			},
			{	"careerName":"专员",
				"standardList":[
					{"label":"晋级标准","labelValue":"贷款>5万元"},
					{"label":"基本标准","labelValue":"销售工具"},
					{"label":"贷款申请服务费","labelValue":"0元"},
					{"label":"理财佣金","labelValue":"1级佣金2.0/2"},
					{"label":"考核标准","labelValue":"贷款>=2单或理财>=20万"},
					{"label":"业绩奖","labelValue":"4000元"},
					{"label":"全勤奖金","labelValue":"1000现金每周例会3次"}	
				]
			},
			{	"careerName":"主任",
				"standardList":[
					{"label":"晋级标准","labelValue":"贷款>5万元"},
					{"label":"基本标准","labelValue":"销售工具"},
					{"label":"贷款申请服务费","labelValue":"0元"},
					{"label":"理财佣金","labelValue":"1级佣金2.0/2"},
					{"label":"考核标准","labelValue":"贷款>=2单或理财>=20万"},
					{"label":"业绩奖","labelValue":"4000元"},
					{"label":"全勤奖金","labelValue":"1000现金每周例会3次"}	
				]
			},
			{	"careerName":"经理",
				"standardList":[
					{"label":"晋级标准","labelValue":"贷款>5万元"},
					{"label":"基本标准","labelValue":"销售工具"},
					{"label":"贷款申请服务费","labelValue":"0元"},
					{"label":"理财佣金","labelValue":"1级佣金2.0/2"},
					{"label":"考核标准","labelValue":"贷款>=2单或理财>=20万"},
					{"label":"业绩奖","labelValue":"4000元"},
					{"label":"全勤奖金","labelValue":"1000现金每周例会3次"}	
				]
			},
			{	"careerName":"总监",
				"standardList":[
					{"label":"晋级标准","labelValue":"贷款>5万元"},
					{"label":"基本标准","labelValue":"销售工具"},
					{"label":"贷款申请服务费","labelValue":"0元"},
					{"label":"理财佣金","labelValue":"1级佣金2.0/2"},
					{"label":"考核标准","labelValue":"贷款>=2单或理财>=20万"},
					{"label":"业绩奖","labelValue":"4000元"},
					{"label":"全勤奖金","labelValue":"1000现金每周例会3次"}	
				]
			}
			
			
		
		]
		
	
		
	},
    "footer": {
        "status": "200"
		
		
    }

}
	 * @return
	 */
	public List<Map<String,Object>> queryIntroduceCareerList();
	
	//创客会员佣金列表
	public List<Map<String,Object>> queryUserCommission(@Param("page")int page,@Param("userId")String userId);
	
	// 创客会员业绩
	public List<Map<String,Object>> queryAchievementCommission(@Param("userId")String userId);
	
	
	
	//查询 创客会员分数
	public Map<String,Object> queryUserCKScore(@Param("userId")String userId);
	
	
	
	//个人中心
	public Integer queryUserCKVIP(@Param("userId")String userId);
	
	//查询头像红包等信息
	public Map<String,String> queryUserExtraInfo(@Param("userId")String userId);
	
	public Map<String,Object> queryMyinvite(@Param("userId")String userId);
	
	public void updateHeadCulptureUrl(@Param("userId")String userId,@Param("url")String url);
	
	public List<Map<String,Object>> queryAddressList(@Param("page")int page,@Param("userId")String userId);
	
	public void insertRqbAddressInfo(@Param("userId")String userId,@Param("receiveName")String receiveName,@Param("phone")String phone,@Param("province")String province,@Param("city")String city,@Param("area")String area,@Param("detailAddress")String detailAddress,@Param("firstChoice")String firstChoice);
	
	public void updateRqbAddressInfo(@Param("receiveName")String receiveName,@Param("addressId")String addressId,@Param("userId")String userId,@Param("phone")String phone,@Param("province")String province,@Param("city")String city,@Param("area")String area,@Param("detailAddress")String detailAddress,@Param("firstChoice")String firstChoice);
	
	public void updateUnFirstChioce(@Param("addressId")String addressId,@Param("userId")String userId);
	
	public void updateFirstChioce(@Param("addressId")String addressId,@Param("userId")String userId);
	
	public void deleteAddress(@Param("addressId")String addressId,@Param("userId")String userId);
	
	
	public List<Map<String,Object>> queryAllInviteList(@Param("userId")String userId);
	
}
