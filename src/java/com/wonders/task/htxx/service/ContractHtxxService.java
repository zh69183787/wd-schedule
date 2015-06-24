package com.wonders.task.htxx.service;

public interface ContractHtxxService {

	public String findEarliestYearOfContractSignedDate();
	
	/**
	 * 更新合同信息
	 * @param contractType 合同类型 1：建设类，2：运维类
	 * @param assignType	指定的类型 1:合同采购方式，2：合同状态统计
	 * @param year	计算的年份
	 */
	public void updateContractAssingTypeBySignedDateAndConctractType(String contractType,String assignType, String year,String companyId);
	
	
	/**
	 * 更新合同信息
	 * @param contractType 	合同类型 1：建设类，2：运维类
	 * @param assignType	指定的类型 1:变更情况,2:支付情况
	 * @param controlYear	计算的年份
	 */
	public void updateContractChangeOrPay(String contractType,String assignType,String controlYear,String companyId);
	
	
	
}
