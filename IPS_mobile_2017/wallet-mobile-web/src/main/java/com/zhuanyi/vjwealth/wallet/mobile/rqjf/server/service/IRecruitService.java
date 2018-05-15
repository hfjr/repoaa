package com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.service;

import java.util.Map;

public interface IRecruitService {

	/**
	 * 招聘列表
	 * @param page
	 * @return
	 * {
			"body": {
				"isMore":"false",
				"recruitList":[
				{"recruitId":"ZHIWEI2017090900501","recruitName":"厨师","recruitNumber":"若干人","recruitArea":"浦东新区","pushTime":"2017-07-28"},
				{"recruitId":"ZHIWEI2017090900502","recruitName":"程序员(java)","recruitNumber":"1人","recruitArea":"浦东新区","pushTime":"2017-07-18"},
				{"recruitId":"ZHIWEI2017090900503","recruitName":"会计","recruitNumber":"2人","recruitArea":"浦东新区","pushTime":"2017-05-28"}
				]
			},
		    "footer": {
		        "status": "200"
		    }
		}
	 */
	public Map<String,Object> queryRecruitList(String page);
	
	
	/**
	 * 招聘详情
	 * @param recruitId
	 * @return
	 * 
	 * {
			"body":{
				"recruitName":"java程序员",
		        "recruitNumber":"招若干人",
				"recruitExperience":"1年",
				"recruitEducation":"大专",
		        "recruitEnglishLevel":"英语一般",
		        "recruitAdress":"浦东新区张东路1387号3号楼",
		        "publishTime":"2017-08-08",
		        "recruitArea":"浦东新区",
		        "recruitDescribe":"职位描述123"
			},
			"footer":{
				"status":"200"
			}
		}
	 */
	public Map<String,Object> queryRecruitDetail(String recruitId);
	
	
	
	/**
	 * 招聘申请-初始化
	 * @param recruitId
	 * @param userId
	 * @return
	 * 
	 * {
			"body": {
				"phone":"18821880888",
				 "realName":"真实姓名"
			},
		    "footer": {
		        "status": "200"
		    }
		}
	 */
	public Map<String,Object> applyInit(String userId);
	
	
	/**
	 * 招聘申请-提交
	 * @param recruitId
	 * @param userId
	 * @param phone
	 * @param realName
	 * @return
	 * 
	 * {
			"body": {
				"message":"尊敬的用户|您的申请已提交成功"
			},
		    "footer": {
		        "status": "200",
		    }
		}
	 */
	public Map<String,Object> doApply(String recruitId, String userId, String realName, String applyPhone,String workExperience,String salaryExpectation);
	
	
	
}
