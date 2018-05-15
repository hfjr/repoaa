package com.zhuanyi.vjwealth.wallet.mobile.bill.server.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.dto.BillListQueryDTO;

@Mapper
public interface IBillListQueryMapper {
	
	/**
	 * @title 查询用户的全部账单
	 * @param userId 用户ID 
	 * @param page 页码
	 * @return
	 */
	List<BillListQueryDTO> getAllBillListByUserIdAndType(@Param("userId")String userId,@Param("page")Integer page);
	
	/**
	 * @title 查询用户的'充值'账单
	 * @param userId 用户ID 
	 * @param page 页码
	 * @return
	 */
	List<BillListQueryDTO> getRechargeBillListByUserIdAndType(@Param("userId")String userId,@Param("page")Integer page);
	
	/**
	 * @title 查询用户的'工资'账单
	 * @param userId 用户ID 
	 * @param page 页码
	 * @return
	 */
	List<BillListQueryDTO> getWageBillListByUserIdAndType(@Param("userId")String userId,@Param("page")Integer page);
	
	/**
	 * @title 查询用户的'e账户'账单
	 * @param userId 用户ID 
	 * @param page 页码
	 * @return
	 */
	List<BillListQueryDTO> getEaBillListByUserIdAndType(@Param("userId")String userId,@Param("page")Integer page);

	/**
	 * @title 查询用户的'ta账户'账单
	 * @param userId 用户ID
	 * @param page 页码
	 * @return
	 */
	List<BillListQueryDTO> getTaBillListByUserIdAndType(@Param("userId")String userId,@Param("page")Integer page);
	
	/**
	 * @title 查询用户的'提现'账单
	 * @param userId 用户ID 
	 * @param page 页码
	 * @return
	 */
	List<BillListQueryDTO> getWithdrawBillListByUserIdAndType(@Param("userId")String userId,@Param("page")Integer page);
	
	/**
	 * @title 查询用户的'定期理财'账单
	 * @param userId 用户ID 
	 * @param page 页码
	 * @return
	 */
	List<BillListQueryDTO> getRegularMoneyBillListByUserIdAndType(@Param("userId")String userId,@Param("page")Integer page);
	
	/**
	 * @title 查询用户的'收益'账单
	 * @param userId 用户ID 
	 * @param page 页码
	 * @return
	 */
	List<BillListQueryDTO> getIncomeBillListByUserIdAndType(@Param("userId")String userId,@Param("page")Integer page);

	/**
	 * 查询用户的全部冻结金额
	 * @param userId 用户ID
	 * @param page 页码
	 * @return
	 */
	List<BillListQueryDTO> getFrozenAllBillListByUserIdAndType(@Param("userId")String userId,@Param("page")Integer page);
	
	/**
	 * @title 查询用户的'借款'账单
	 * @param userId 用户ID 
	 * @param page 页码
	 * @return
	 */
	List<BillListQueryDTO> getLoanBillListByUserIdAndType(@Param("userId")String userId,@Param("page")Integer page);

	
	/**
	 * @title 查询用户的'还款'账单
	 * @param userId 用户ID 
	 * @param page 页码
	 * @return
	 */
	List<BillListQueryDTO> getRepayBillListByUserIdAndType(@Param("userId")String userId,@Param("page")Integer page);

	/**
	 * 查询用户的'推荐返现'账单
	 * @param userId
	 * @param page
     * @return
     */
	List<BillListQueryDTO> getCashBackBillListByUserIdAndType(@Param("userId")String userId,@Param("page")Integer page);
	/**
	 * 查询用户的'红包'账单
	 * @param userId
	 * @param page
	 * @return
	 */
	List<BillListQueryDTO> getRpBillListByUserIdAndType(@Param("userId")String userId,@Param("page")Integer page);
	
	/**
	 * 查询用户的'工资定存代扣'账单
	 * @param userId
	 * @param page
     * @return
     */
	List<BillListQueryDTO> getBankcardWithholdBillListByUserIdAndType(@Param("userId")String userId,@Param("page")Integer page);
}
