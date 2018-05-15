package com.rqb.ips.depository.platform.beans;

import com.fab.core.entity.dto.LigerGridPageDTO;

public class IpsUserInfoDTO extends LigerGridPageDTO {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	//手机
	private String name;

	//手机
	private String phone;
	

	//序号id
	private String id;
	
	//ips账号
	private String useripsid;

	//查询类型01:账户查询、02：交易查询、03:余额查询
	private String querytype;
	
	//商户订单号
	private String merBillNo;
	
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getQuerytype() {
		return querytype;
	}
	public void setQuerytype(String querytype) {
		this.querytype = querytype;
	}

	//账户信息
	//'账户开通状态   1 正常、2 异常',
	private String acctStatus;
	//身份证审核状态
	//'0 未上传身份证（默认）、1 审核成功、\r\n2 审核拒绝、3 审核中（已经上传身份\r\n证，但是未审核）、
	//4 未推送审核(已上\r\n传,但未发往运管审核)',
	private String uCardStatus;
	//'银行名称'
	private String bankName;
	//银行卡后四位 
	private String bankCard;
	//代扣签约状态  1 未申请、2 成功、3 失败',
	private String signStatus;
	
	//交易查询
	//交易日期
	private String merDate;
	//交易状态
	private String trdStatus;
	//IPS 订单号
	private String ipsBillNo;
	//IPS 处理时间   yyyy-MM-DD HH:mm:ss
	private String ipsDoTime;
	
	//账户余额
	//账户余额   当前账户余额
	private String curBal;
	//可用余额
	private String availBal;
	//冻结余额
	private String freezeBal;
	//风险准备金余额    账号为平台 IPS 存管账户时返回',
	private String marginBal;
	//还款专用余额   账号为用户 IPS 存管账户时返回',
	private String repaymentBal;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUseripsid() {
		return useripsid;
	}
	public void setUseripsid(String useripsid) {
		this.useripsid = useripsid;
	}
	public String getAcctStatus() {
		return acctStatus;
	}
	public void setAcctStatus(String acctStatus) {
		this.acctStatus = acctStatus;
	}
	public String getuCardStatus() {
		return uCardStatus;
	}
	public void setuCardStatus(String uCardStatus) {
		this.uCardStatus = uCardStatus;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankCard() {
		return bankCard;
	}
	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}
	public String getSignStatus() {
		return signStatus;
	}
	public void setSignStatus(String signStatus) {
		this.signStatus = signStatus;
	}
	public String getMerBillNo() {
		return merBillNo;
	}
	public void setMerBillNo(String merBillNo) {
		this.merBillNo = merBillNo;
	}
	public String getMerDate() {
		return merDate;
	}
	public void setMerDate(String merDate) {
		this.merDate = merDate;
	}
	public String getTrdStatus() {
		return trdStatus;
	}
	public void setTrdStatus(String trdStatus) {
		this.trdStatus = trdStatus;
	}
	public String getIpsBillNo() {
		return ipsBillNo;
	}
	public void setIpsBillNo(String ipsBillNo) {
		this.ipsBillNo = ipsBillNo;
	}
	public String getIpsDoTime() {
		return ipsDoTime;
	}
	public void setIpsDoTime(String ipsDoTime) {
		this.ipsDoTime = ipsDoTime;
	}
	public String getCurBal() {
		return curBal;
	}
	public void setCurBal(String curBal) {
		this.curBal = curBal;
	}
	public String getAvailBal() {
		return availBal;
	}
	public void setAvailBal(String availBal) {
		this.availBal = availBal;
	}
	public String getFreezeBal() {
		return freezeBal;
	}
	public void setFreezeBal(String freezeBal) {
		this.freezeBal = freezeBal;
	}
	public String getMarginBal() {
		return marginBal;
	}
	public void setMarginBal(String marginBal) {
		this.marginBal = marginBal;
	}
	public String getRepaymentBal() {
		return repaymentBal;
	}
	public void setRepaymentBal(String repaymentBal) {
		this.repaymentBal = repaymentBal;
	}
	
	
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
	@Override
	public String toString() {
		return "IpsUserInfoDTO [name=" + name + ", phone=" + phone + ", id=" + id + ", useripsid=" + useripsid
				+ ", querytype=" + querytype + ", merBillNo=" + merBillNo + ", acctStatus=" + acctStatus
				+ ", uCardStatus=" + uCardStatus + ", bankName=" + bankName + ", bankCard=" + bankCard + ", signStatus="
				+ signStatus + ", merDate=" + merDate + ", trdStatus=" + trdStatus + ", ipsBillNo=" + ipsBillNo
				+ ", ipsDoTime=" + ipsDoTime + ", curBal=" + curBal + ", availBal=" + availBal + ", freezeBal="
				+ freezeBal + ", marginBal=" + marginBal + ", repaymentBal=" + repaymentBal + "]";
	}
	
	
	
	
}
