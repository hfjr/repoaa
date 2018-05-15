package com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.service;

import java.util.List;
import java.util.Map;

import com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.dto.LoanApplyDTO;

public interface ILoanService {

	//贷款初始化
	/**{
	"body": {
		"applyStatus":"init", // 申请状态:init(可申请)/process(进行中)/fail(失败)
		"phone":"18821880888",
        "realName":"真实姓名",
		"idNo":"110106198802056353",
		"errorMessage":""
	},
    "footer": {
        "status": "200"
    }

}
	 * 
	 * @return
	 */
	public Map<String,Object> applyInit(String userId,String productType);
	
	
	
	/**
	 * 贷款申请
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
	public Map<String, Object>  doApply(LoanApplyDTO loanApplyDTO);
	
	
	
	/**
	 * 贷款申请
	 * 新增推荐人
	 * @param loanApplyDTO
	 * @return
	 */
	public Map<String, Object> doApplyV2(LoanApplyDTO loanApplyDTO);
	
	
	
	
	
	/**
	 * 
	 * 查询借款列表
	 * {
	"body": {
		"isMore":"true",
		"borrowList":[
		{"loanId":"loan2017090900501","borrowAmt":"200","term":"360天","startTime":"2017-05-28","endTime":"2017-05-28"},
		{"loanId":"loan2017090900201","borrowAmt":"200","term":"360天","startTime":"2017-05-28","endTime":"2017-05-28"},
		{"loanId":"loan2017090900031","borrowAmt":"200","term":"360天","startTime":"2017-05-28","endTime":"2017-05-28"},
		{"loanId":"loan2017090900002","borrowAmt":"200","term":"360天","startTime":"2017-05-28","endTime":"2017-05-28"},
		{"loanId":"loan2017090900111","borrowAmt":"200","term":"360天","startTime":"2017-05-28","endTime":"2017-05-28"},
		{"loanId":"loan2017090900011","borrowAmt":"200","term":"360天","startTime":"2017-05-28","endTime":"2017-05-28"}
		]
	},
    "footer": {
        "status": "200"
    }

}
	 * @return
	 */
	public Map<String,Object> queryBorrowList(String page,String userId);
	
	
	
	/**
	 * 查询还款计划
	 * {
	"body": {
		"borrowPrincipal":"6000",
		"borrowInterest":"3.6",
		"repayPlanList":[
		{"borrowAmt":"2000","interest":"1.2","term":"1","repayDate":"2017-05-28"},
		{"borrowAmt":"2000","interest":"1.2","term":"2","repayDate":"2017-05-28"},
		{"borrowAmt":"2000","interest":"1.2","term":"3","repayDate":"2017-05-28"}
		]
	},
    "footer": {
        "status": "200"
    }

}
	 * @return
	 */
	public Map<String,Object> queryRepayPlan(String userId,String loanId);
	
	
	public List<Map<String,Object>>  queryBannerInfo();
}
