package com.rqb.ips.depository.platform.menu;

/**
 * 枚举类型
 * 
 * @author fan
 * @version 1.0, 2017-11-29
 * @since 1.0
 */
public class Define {

	/**
	 * IPS操作类型
	 * 
	 * @author fan
	 * @version 1.0, 2017-11-29
	 * @since 1.0
	 */
	public static final class OperationType {
		/**  用户开户 */
		public static final String REGISTER = "user.register";
		
		/** 商户直连银行查询 */
		public static final String BANKQUERY = "query.bankQuery";
		
		/** 充值 */
		public static final String DEPOSIT = "trade.deposit";
		
		/** 提现 */
		public static final String WITHDRAW = "trade.withdraw";
		
		/** 项目登记 */
		public static final String REGPROJECT = "project.regProject";
		
		/**  追加登记 */
		public static final String ASSUREPROJECT = "project.assureProject";
		
		/** 冻结 */
		public static final String FREEZE = "trade.freeze";
		
		/** 红包组合冻结 */
		public static final String COMBFREEZE = "trade.combFreeze";
		
		/** 解冻 */
		public static final String UNFREEZE = "trade.unFreeze";
		
		/** 转账 */
		public static final String TRANSFER = "trade.transfer";
		
		/** 自动签约 */
		public static final String AUTOSIGN = "user.autoSign";
		
		/** 查询 */
		public static final String COMMONQUERY = "query.commonQuery";
	}

	
	/**
	 * IPS返回结果代码
	 * 
	 * @author fan
	 * @version 1.0, 2017-11-29
	 * @since 1.0
	 */
	public static final class IPSResultCode{
		/** 成功 */
		public static final String SUCESS = "000000";
		
		/** 失败 */
		public static final String FAIL = "999999";
	}
	
	/**
	 * IPS返回状态
	 * 
	 * @author fan
	 * @version 1.0, 2017-11-29
	 * @since 1.0
	 */
	public static final class IPSStatus{
		/** 失败 */
		public static final String FAIL = "0";

		/** 成功 */
		public static final String SUCESS = "1";
		
		/** 待审核 */
		public static final String EXAMINE = "2";
	}
}
