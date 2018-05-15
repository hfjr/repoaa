package com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.dto.LoanApplyDTO;

@Mapper
public interface ILoanMapper {

	

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
	public Map<String,Object> applyInit(@Param("userId")String userId,@Param("productType")String productType);
	
	
	
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
	public void doApply(LoanApplyDTO loanApplyDTO);
	
	
	/**
	 * 贷款申请,新增推荐人
	 * @param loanApplyDTO
	 */
	
	public void doApplyV2(LoanApplyDTO loanApplyDTO);
	
	
	
	
	
	//检查是否可预约
	public Integer checkCanApply(@Param("userId")String userId,@Param("productType")String productType);
	
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
	public List<Map<String,Object>> queryBorrowList(@Param("page")int page,@Param("userId")String userId);
	
	
	
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
	public List<Map<String,Object>> queryRepayPlan(@Param("loanId")String loanId);
	
	
	public List<Map<String,Object>> queryBannerInfo();

}
