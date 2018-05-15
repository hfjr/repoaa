package com.zhuanyi.vjwealth.wallet.service.mainbiz.account.server.integration.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;
import com.zhuanyi.vjwealth.wallet.service.balance.common.dto.WjEbatongTradeHistoryDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.account.dto.WjCheckingAccountDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.account.dto.WjCheckingAccountDetailDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.account.dto.WjCheckingAccountErrorDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBOrderInfoDTO;

@Mapper
public interface ICheckingAccountMapper {
	public static final String NAME_SPACE = ICheckingAccountMapper.class.getName();
	
//	//分页查询
//	public static final String queryCheckingAccountDetailPage = NAME_SPACE + ".queryCheckingAccountDetailPage";
	
	/**
	 * 
	* @Title: insertCheckingAccount 
	* @Description:  (插入对账单主信息) 
	* @param @param WjCheckingAccountDTO    
	* @return void    返回类型 
	* @throws
	 */
	int insertCheckingAccount(WjCheckingAccountDTO wjCheckingAccountDTO);
	
	/**
	 * 
	* @Title: insertCheckingAccountDetail 
	* @Description:  (插入对账单主信息) 
	* @param @param WjCheckingAccountDTO    
	* @return void    返回类型 
	* @throws
	 */
	void insertCheckingAccountDetail(WjCheckingAccountDetailDTO wjCheckingAccountDetailDTO);
	
	/**
	 * 
	* @Title: insertCheckingAccountDetail 
	* @Description:  (批量插入对账单明细信息) 
	* @param @param WjCheckingAccountDTO    
	* @return void    返回类型 
	* @throws
	 */
	void insertCheckingAccountDetailList(List<WjCheckingAccountDetailDTO> list);
	
	/**
	 * @Title: queryCheckingAccountDetailList 
	 * @Description:  (查询对账单明细信息) 
	 * @param wjCheckingAccountDetailDTO
	 * @return List
	 */
	List<WjCheckingAccountDetailDTO> queryCheckingAccountDetailList(WjCheckingAccountDetailDTO wjCheckingAccountDetailDTO);
	
	/**
	 * @Title: queryCheckingAccount 
	 * @Description:  (查询对账单主信息) 
	 * @param checkingData
	 * @return
	 */
	List<WjCheckingAccountDTO> queryCheckingAccount(WjCheckingAccountDTO checkingAccountDTO);
	
	/**
	 * @Title: queryTradeHistoryListToCheck 
	 * @Description:  (查询充值历史数据)
	 * @param map
	 * @return
	 */
	List<WjEbatongTradeHistoryDTO> queryTradeHistoryListToCheck(Map<String,Object> map);
	
	/**
	 * @Title: insertCheckingAccountError 
	 * @Description:  (插入对账差错信息)
	 * @param wjCheckingAccountErrorDTO
	 */
	void insertCheckingAccountError(WjCheckingAccountErrorDTO wjCheckingAccountErrorDTO);
	
	/**
	 * @Title: updateCheckingAccountDetailWithContrast 
	 * @Description:  (修改对账明细,对账标志为'Y')
	 * @param id
	 */
	void updateCheckingAccountDetailWithContrast(@Param("id")Long id);
	
	/**
	 * @Title: updateTradeHistoryWithContrast 
	 * @Description:  (修改充值历史,对账标志为'Y')
	 * @param id
	 */
	void updateTradeHistoryWithContrast(@Param("id")Long id);
	
	/**
	 * @Title: updateCheckingAccount 
	 * @Description:  (修改对账主信息,对账开始时间,结束时间,结果)
	 * @param wjCheckingAccountDTO
	 */
	void updateCheckingAccount(WjCheckingAccountDTO wjCheckingAccountDTO);
	
	/**
	 * @Title: queryCheckingAccountDetailSum 
	 * @Description:  (查询对账单明细单日未对账总数 )
	 * @param accountId
	 * @return
	 */
	Integer queryCheckingAccountDetailSum(@Param("checkNo")String checkNo);
	
	/**
	 * @Title: queryPriceInOrderByOrderNo 
	 * @Description:  (查询订单中金额)
	 * @param orderNo
	 * @return
	 */
	String queryPriceInOrderByOrderNo(@Param("orderNo")String orderNo);
	
	/**
	 * 修改订单对账标记
	 * @param orderNo
	 */
	void updateOrderWithContrast(@Param("orderNo")String orderNo);
	
	/**
	 * 查询未对账的订单
	 * @param map
	 * @return
	 */
	List<MBOrderInfoDTO> queryOrderInfoToCheck(Map<String,Object> map);
	
//	/**
//	 * @Title: queryCheckingAccountDetailPage 
//	 * @Description:  (分页查询对账单明细)
//	 * @param wjCheckingAccountDetailDTO
//	 * @return
//	 */
//	List<WjCheckingAccountDetailDTO> queryCheckingAccountDetailPage(WjCheckingAccountDetailDTO wjCheckingAccountDetailDTO);
}
