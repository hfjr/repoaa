/**
 * apiRoutes管理
*/
define("lib/apiRoutes", function(){
	var VERSIONS = {
		V_1_0 : "/wallet-mobile/api/v1.0/app/",
		V_3_0 : "/wallet-mobile/api/v3.0/app/",
		V_3_0_WX : "/wallet-mobile/api/v3.0/weixin/",
		V_3_0_1_WX : "/wallet-mobile/api/v3.0.1/weixin/",
		V_3_1 : "/wallet-mobile/api/v3.1/app/",
		V_3_2 : "/wallet-mobile/api/v3.2/app/",
		V_3_3 : "/wallet-mobile/api/v3.3/app/",
		V_3_4 : "/wallet-mobile/api/v3.4/app/",
		V_3_5 : "/wallet-mobile/api/v3.5/app/",
		OLD : "/wallet-mobile/app/"
	};
	function _path(path, version){
		version = version || VERSIONS.V_3_0;
		return version + path.replace(/^\//g, "");
	}
	return {
		
		//焦点图
		"common/advertisement/queryInvestmentAdvertisementList" : _path("common/advertisement/queryInvestmentAdvertisementList", VERSIONS.V_3_0),
		//标列表
		"product/queryProductList" : _path("product/queryProductList", VERSIONS.V_3_0_1_WX),
		//标详情
		"product/queryProductDetail" : _path("product/queryProductDetail", VERSIONS.V_3_1),
		//标投资记录
		"product/queryProductInvestmentRecordList" : _path("product/queryProductInvestmentRecordList", VERSIONS.V_3_1),
		
		//登录提交
		"user/login" : _path("user/login", VERSIONS.V_3_0),
		
		//注册发送语音验证码
		"user/sendRegisterToneNotice" : _path("user/sendRegisterToneNotice", VERSIONS.OLD),
		//注册发送短信验证码
		"user/sendRegisterSMSNotice" : _path("user/sendRegisterSMSNotice", VERSIONS.OLD),
		//注册提交
		"user/register" : _path("user/register", VERSIONS.OLD),
		//邀请码注册
		"user/registerWithCode" : _path("user/registerWithCode", VERSIONS.OLD),
		//注册协议
		"user/registeredAgreement" : _path("user/individualClientService", VERSIONS.OLD),
		
		//激活发送语音验证码
		"user/sendActivateToneNotice" : _path("user/sendActivateToneNotice", VERSIONS.OLD),
		//激活发送短信验证码
		"user/sendActivateSMSNotice" : _path("user/sendActivateSMSNotice", VERSIONS.OLD),
		//激活提交
		"user/updateActivateUserInfo" : _path("user/updateActivateUserInfo", VERSIONS.OLD),
		
		//忘记密码发送语音验证码
		"user/sendForgetToneNotice" : _path("user/sendForgetToneNotice", VERSIONS.OLD),
		//忘记密码发送短信验证码
		"user/sendForgetSMSNotice" : _path("user/sendForgetSMSNotice", VERSIONS.OLD),
		//忘记密码提交
		"user/updateForgetPassword" : _path("user/updateForgetPassword", VERSIONS.OLD),
		
		//用户投资概要
		"order/queryInvestmentRecordSummary.security" : _path("order/queryInvestmentRecordSummary.security", VERSIONS.V_3_0),
		//用户投资列表
		"order/queryUserInvestmentRecordList.security" : _path("order/queryUserInvestmentRecordList.security", VERSIONS.V_3_0),
		//用户的投资详情
		"order/queryUserInvestmentDetail.security" : _path("order/queryUserInvestmentDetail.security", VERSIONS.V_3_0),
		//用户的投资动态流程
		"order/queryUserInvestmentNewsFlow.security" : _path("order/queryUserInvestmentNewsFlow.security", VERSIONS.V_3_0),
		//用户的投资合同详情
		"contract/investmentContractDetail.security" : _path("contract/investmentContractDetail.security", VERSIONS.V_3_0),
		//用户的投资回款计划
		"order/queryUserInvestmentNewsRepaymentPlan.security" : _path("order/queryUserInvestmentNewsRepaymentPlan.security", VERSIONS.V_3_0),
		//财富首页
		"account/queryMAccountInfo.security" : _path("account/queryMAccountInfo.security", VERSIONS.V_3_0),
		//累计收益
		"account/totalAccount/queryTotalAccountReceive.security" : _path("account/totalAccount/queryTotalAccountReceive.security", VERSIONS.OLD),
		//昨日收益
		"account/totalAccount/queryYesterdayReceive.security" : _path("account/totalAccount/queryTotalAccountReceive.security", VERSIONS.OLD),
		//查询已绑卡列表
		"queryRechargeCard" : _path("account/queryRechargeCard.security", VERSIONS.V_3_2),
		//查询支持银行
		"queryAllSupportBankList" : _path("user/queryAllSupportBankList", VERSIONS.V_3_2),
		"payAgreement" : _path("user/payAgreement", VERSIONS.OLD),
		//绑卡初始化界面查询用户信息接口
		"queryUserDefaultInfoForBindCard" : _path("user/queryUserDefaultInfo.security", VERSIONS.OLD),
		//用户未绑卡充值下发验证码接口
		"rechargeToMaSendCode" : _path("user/rechargeToMaSendCode.security", VERSIONS.V_3_2),
		//用户未绑卡确认充值接口
		"doRechargeToMa" : _path("user/doRechargeToMa.security", VERSIONS.OLD),
		//充值回调查询接口
		"queryRechargeNotice" : _path("user/queryRechargeNotice.security", VERSIONS.OLD),
		//用户已绑卡确认充值接口
		"doRechargeToMaAlreadyBind" : _path("user/doRechargeToMaAlreadyBind.security", VERSIONS.OLD),
		//用户已绑卡充值下发验证码接口
		"rechargeToMaAlreadyBindCardSendCode" : _path("user/rechargeToMaAlreadyBindCardSendCode.security", VERSIONS.V_3_2),
		//支付密码主页初始化接口
		"paymentPasswordIniti" : _path("paymentPassword/paymentPasswordIniti.security", VERSIONS.V_3_1),
		//设置支付密码
		"setPaymentPassword" : _path("paymentPassword/setPaymentPassword.security", VERSIONS.V_3_1),
		//验证支付密码
		"checkPaymentPassword" : _path("paymentPassword/checkPaymentPassword.security", VERSIONS.V_3_1),
		//关闭支付密码
		"closePaymentPassword" : _path("paymentPassword/closePaymentPassword.security", VERSIONS.V_3_1),
		//重置支付密码（有原密码）
		"resetPaymentPasswordForRememberPassword" : _path("paymentPassword/resetPaymentPasswordForRememberPassword.security", VERSIONS.V_3_1),
		//忘记支付密码初始化接口【无原密码】（解锁初始化同用）
		"resetPaymentPasswordForForgetPasswordIniti" : _path("paymentPassword/resetPaymentPasswordForForgetPasswordIniti.security", VERSIONS.V_3_1),
		//重置支付密码-发送短信验证码（忘记密码）
		"sendForgetSMSNoticeForForgetPassword" : _path("paymentPassword/sendForgetSMSNoticeForForgetPassword.security", VERSIONS.V_3_1),
		//重置支付密码-发送语音验证码（忘记密码）
		"sendForgetToneNoticeForForgetPassword" : _path("paymentPassword/sendForgetToneNoticeForForgetPassword.security", VERSIONS.V_3_1),
		//重置支付密码认证信息提交（忘记密码）
		"resetPaymentPasswordForForgetPassword" : _path("paymentPassword/resetPaymentPasswordForForgetPassword.security", VERSIONS.V_3_1),
		//解锁支付密码认证信息提交-【尝试支付密码超过最大次数】（自助）
		"unlockPaymentPassword" : _path("paymentPassword/unlockPaymentPassword.security", VERSIONS.V_3_1),
		//解锁-【尝试支付密码超过最大次数】（自助 文字短信）
		"sendForgetSMSNoticeForUnlockPaymentPassword" : _path("paymentPassword/sendForgetSMSNoticeForUnlockPaymentPassword.security", VERSIONS.V_3_1),
		//解锁-【尝试支付密码超过最大次数】（自助 语音短信）
		"sendForgetToneNoticeForUnlockPaymentPassword" : _path("paymentPassword/sendForgetToneNoticeForUnlockPaymentPassword.security", VERSIONS.V_3_1),
		//账户中心首页
		"accountCenterInfo" : _path("user/accountCenterInfo.security", VERSIONS.V_3_2),
		//提现初始化
		"queryWithdrawMaAccountAndEAccountInfo" : _path("account/queryWithdrawMaAccountAndEAccountInfo.security", VERSIONS.OLD),
		//余额提现
		"withdrawMa" : _path("order/withdrawMa.payment", VERSIONS.V_3_1),
		//e账户提现
		"withdrawEa" : _path("order/withdrawEa.payment", VERSIONS.V_3_1),
		//v理财 下订单初始化[判断产品是否能购买]
		"queryProductCanBuy" : _path("order/queryProductCanBuy.security", VERSIONS.V_3_1),
		//下单购买理财
		"placeOrder" : _path("order/placeOrder.payment", VERSIONS.V_3_1),
		//投资合同模版
		"investmentContractTemplate" : _path("contract/investmentContractTemplate", VERSIONS.V_3_0),
		//查询我的银行卡
		"queryMyBankCards" : _path("account/queryMyBankCards.security", VERSIONS.OLD),
		//安全卡充值卡温馨提示
		"mybankRule" : _path("user/mybankRule", VERSIONS.OLD),
		//帮助中心
		"goHelp" : _path("user/goHelp", VERSIONS.OLD),
		//安全保障
		"insurance" : _path("insurance/insuranceIndex.security", VERSIONS.OLD),
		//账单列表
		"queryBillList" : _path("bill/queryBillList.security", VERSIONS.V_3_0),
		//查询账单详情
		"queryBillDetail" : _path("bill/queryBillDetail.security", VERSIONS.V_3_0),
		//e账户账单
		"queryEAccountDetail" : _path("account/queryEAccountDetail.security", VERSIONS.OLD),
		//e账户冻结账单
		"queryEAccountFrozenDetail" : _path("account/queryEAccountFrozenDetail.security", VERSIONS.OLD),
		//e账户收益
		"queryEAccountReciveDetail" : _path("account/queryEAccountReciveDetail.security", VERSIONS.OLD),
		//查询e账户基本信息
		"queryEAccountInfo" : _path("account/queryEAccountInfo.security", VERSIONS.V_3_0),
		//e账户提现初始化
		"queryEAccountOutComeBalance" : _path("account/queryEAccountOutComeBalance.security", VERSIONS.OLD),
		
		//图表-近七日年化
		"weekReceiveRate/7" : "/wallet-mobile/report/weekReceiveRate/7",
		"weekReceiveRate/30" : "/wallet-mobile/report/weekReceiveRate/30",
		"weekReceiveRate/90" : "/wallet-mobile/report/weekReceiveRate/90",
		//图表-万份收益
		"wanReceive/7" : "/wallet-mobile/report/wanReceive/7",
		"wanReceive/30" : "/wallet-mobile/report/wanReceive/30",
		"wanReceive/90" : "/wallet-mobile/report/wanReceive/90",
		//转出到余额
		"transferEaToMa" : _path("order/transferEaToMa.payment", VERSIONS.V_3_1),
		//转出操作 安全卡初始化
		"queryWithdrawCard" : _path("account/queryWithdrawCard.security", VERSIONS.OLD),
		//余额转E账户
		"applyMaToEa" : _path("order/applyMaToEa.payment", VERSIONS.V_3_1),
		//余额转E账户初始化数据
		"queryEAccountInComeBalance" : _path("account/queryEAccountInComeBalance.security", VERSIONS.OLD),
		//电子工资单列表
		"queryPayRollList" : _path("user/queryPayRollList.security", VERSIONS.V_3_0),
		//电子工资单详情
		"queryPayRollDetail" : _path("user/queryPayRollDetail.security", VERSIONS.V_3_0),
		
		//活期初始化
		"cunqianguan/init" : _path("account/queryMAccountInfo.security", VERSIONS.V_3_1),
		//活期账单列表-明细
		"cunqianguan/queryTAccountDetail" : _path("account/queryTAccountDetail.security", VERSIONS.OLD),
		//活期 账单列表-收益
		"cunqianguan/queryTAccountReciveDetail" : _path("account/queryTAccountReciveDetail.security", VERSIONS.OLD),
		//活期冻结账单
		"cunqianguan/queryTAccountFrozenDetail" : _path("account/queryTAccountFrozenDetail.security", VERSIONS.OLD),
		//活期提现初始化
		"cunqianguan/queryTAccountOutComeBalance" : _path("account/queryTAccountOutComeBalance.security", VERSIONS.OLD),
		//活期转入初始化
		"cunqianguan/queryTAccountInComeBalance" : _path("account/queryTAccountInComeBalance.security", VERSIONS.OLD),
		//活期 转出操作 安全卡初始化
		"cunqianguan/queryWithdrawCard" : _path("account/queryWithdrawCard.security", VERSIONS.OLD),
		//申购
		"cunqianguan/shengou" : _path("order/applyMaToTa.payment", VERSIONS.V_1_0),
		//转出到银行卡
		"cunqianguan/withdrawTa" : _path("order/withdrawTa.payment", VERSIONS.V_1_0),
		//转出到余额
		"cunqianguan/transferTaToMa" : _path("order/transferTaToMa.security", VERSIONS.V_1_0),
		//v信贷充值初始化
		"loan/recharge/init" : _path("credit/applyForCredit/rechargeIniti.security", VERSIONS.V_3_3),
		//v信贷充值支持银行列表
		"loan/recharge/queryAllSupportBankList" : _path("credit/applyForCredit/queryAllSupportBankList.security", VERSIONS.V_3_3),
		//v信贷充值绑卡初始化
		"loan/recharge/bindCardInit" : _path("credit/applyForCredit/rechargeBindBankCardIniti.security", VERSIONS.V_3_3),
		//v信贷路由
		"loan/route" : _path("credit/route.security", VERSIONS.V_3_3),
		//初始化申请额度
		"loan/initApply" : _path("credit/applyForCredit/creditApplicationIniti.security", VERSIONS.V_3_3),
		//关于V信贷
		"loan/about" : _path("credit/financialLoan/timeWallet", VERSIONS.V_3_3),
		//完善信息初始化
		"loan/initImprove" : _path("credit/applyForCredit/improvePersonalInformationIniti.security", VERSIONS.V_3_3),
		//初始化公积金帐号
		"loan/initFoundAccount" : _path("credit/applyForCredit/fundAccountIniti.security", VERSIONS.V_3_3),
		//初始化社保帐号
		"loan/initSocialAccount" : _path("credit/applyForCredit/socialSecurityAccountIniti.security", VERSIONS.V_3_3),
		//保存个人信息
		"loan/savePersonalInformation" : _path("credit/applyForCredit/improvePersonalInformationSave.security", VERSIONS.V_3_3),
		//时光钱包产品列表
		"loan/productTypeList" : _path("credit/productTypeList.security", VERSIONS.V_3_3),
		//小金鱼首页初始化
		"goldFish/init" : _path("credit/financialLoan/financialLoanIniti.security", VERSIONS.V_3_3),
		//小金鱼任务列表
		"goldFish/taskList" : _path("credit/financialLoan/taskList.security", VERSIONS.V_3_3),
		//小金鱼介绍
		"goldFish/about" : _path("credit/financialLoan/financialLoanIntroduction", VERSIONS.V_3_3),
		//小金鱼公积金社保基本信息初始化
		"goldFish/initFundSocial" : _path("credit/financialLoan/taskDetail.security", VERSIONS.V_3_3),
		//小金鱼公积金城市列表
		"goldFish/cityData" : _path("credit/financialLoan/queryCityList.security", VERSIONS.V_3_3),
		//小金鱼公积金社保基本信息保存
		"goldFish/saveFundSocial" : _path("credit/financialLoan/fundAndSocialSecurityAccountTaskSave.security", VERSIONS.V_3_3),
		//投资项目初始化
		"goldFish/investProjectInit" : _path("credit/financialLoan/queryProductDetail.security", VERSIONS.V_3_3),
		//投资记录
		"goldFish/queryProductInvestmentRecordList" : _path("credit/financialLoan/queryProductInvestmentRecordList.security", VERSIONS.V_3_3),
		//借款详情
		"goldFish/borrowRecordDetail" : _path("credit/financialLoan/borrowRecordDetail.security", VERSIONS.V_3_3),
		//投资详情
		"goldFish/investmentDetail" : _path("credit/financialLoan/investmentDetail.security", VERSIONS.V_3_3),
		//投资动态
		"goldFish/investmentNewsFlow" : _path("credit/financialLoan/investmentNewsFlow.security", VERSIONS.V_3_3),
		//投资初始化
		"goldFish/initInvest" : _path("credit/financialLoan/investIniti.security", VERSIONS.V_3_3),
		//回款计划
		"goldFish/repaymentPlan" : _path("credit/financialLoan/repaymentPlan.security", VERSIONS.V_3_3),
		//投资合同
		"goldFish/investmentContract" : _path("contract/investmentContractDetail.security", VERSIONS.V_3_0),
		//确认投资
		"goldFish/confirmInvest" : _path("credit/financialLoan/loanInformationConfirmationIniti.security", VERSIONS.V_3_3),
		//提交投资
		"goldFish/submitInvest" : _path("credit/financialLoan/loanInformationConfirmationSave.security", VERSIONS.V_3_3),
		//投资合同模版
		"goldFish/investmentContractTemplate" : _path("contract/investmentContractTemplate", VERSIONS.V_3_0),
		//我的投资分页
		"goldFish/myInvestList" : _path("credit/financialLoan/borrowRecordList.security", VERSIONS.V_3_3),
		//账单详情-使用小金鱼
		"goldFish/myInvestDetailOrder" : _path("billDetail/queryFinacialLoanInvestmentDetail.security", VERSIONS.V_3_3),
		//账单详情-归还小金鱼
		"goldFish/myLoanDetailOrder" : _path("billDetail/queryFinacialLoanRepaymentDetail.security", VERSIONS.V_3_3),
		//首页初始化
		"index/init" : _path("weixinHomeInit", VERSIONS.V_3_0_1_WX),
		//工资易贷-发送短信验证码
		"salaryLoan/reserve/sendSMSCode" : _path("credit/easyLoan/easyLoanSendSMSNotice", VERSIONS.V_3_5),
		//工资易贷-提交预约信息
		"salaryLoan/reserve/submit" : _path("credit/easyLoan/easyLoanSave", VERSIONS.V_3_5)
		
	};
});