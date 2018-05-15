package com.zhuanyi.vjwealth.wallet.mobile.bill.server.impl.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.zhuanyi.vjwealth.wallet.mobile.bill.server.dto.BillListQueryDTO;

public abstract class AbstractBillTemplateService {

    /**
     * 模板方法：查询账单列表，并进行分页
     * @param userId 用户ID
     * @param page 页码
     * @return
     */
	public final Map<String, Object> getBillPageList(String userId, String page) {

		//	1.	返回列表
		List<BillListQueryDTO> billList = getListRows(userId, (Integer.parseInt(page) - 1) * 10);

		//	2. 包装分页信息
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("records", billList);
		returnMap.put("isMore", false);
		if (billList != null && billList.size() >= 10) {
			returnMap.put("isMore", true);
		}

		return returnMap;
	}
	
	/**
	 * 基本方法：由子类具体实现账单列表的查询
	 * @param userId 用户ID
	 * @param pageIndex 页码
	 * @return 账单列表
	 */
    public abstract List<BillListQueryDTO> getListRows(String userId, int pageIndex);
    
    
    
    
    /**
	 * 获取账单过程信息
	 * @param orderDate
	 * @param compeleteDate
	 * @return "operateStateInfos":[
               {"operateTitle":"提交成功","operateTime":"2015-10-14 21:21:58"},  
               {"operateTitle":"处理中","operateTime":""},
               {"operateTitle":"提现成功","operateTime":"2015-11-14 21:21:58"}
             ]
	 */
	public List<Map<String,String>> getOperateProcessInfo(String orderDate,String completeTitle,String compeleteDate,String flag){
		List<Map<String,String>> processList = new ArrayList<Map<String,String>>();
		//添加三个操作节点的信息
		processList.add(getProcessMap("提交成功",orderDate,null));
		processList.add(getProcessMap("处理中","",null));
		processList.add(getProcessMap(completeTitle,compeleteDate,flag));
		
		return processList;
		
	}
	
	//添加操作过程节点的信息
	public Map<String,String> getProcessMap(String title,String date,String flag){
		Map<String,String> processMap = new HashMap<String,String>();
		processMap.put("operateTitle", title);
		processMap.put("operateTime", date);
		if(StringUtils.isNotBlank(flag)){
			processMap.put("operateShow", flag);
		}
		return processMap;
	}
    

}
