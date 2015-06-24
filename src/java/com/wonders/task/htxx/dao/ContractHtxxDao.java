package com.wonders.task.htxx.dao;

import java.util.List;

import com.wonders.task.investCost.contract.model.bo.Contract;

public interface ContractHtxxDao {

	public Contract findEarliestContract(); 
	
	public List<Object[]> findSumOfInviteBidBySignedDateAndContractType(String contractType,String controlYear,String companyId);
	
	public List<Object[]> findSumOfContractStatusBySignedDateAndContractType(String contractType,String controlYear,String companyId);
	
	/**
	 * 查询合同计划支付、实际支付
	 */
	public List<Object[]> findSumOfContractStatus(String contractType,String controlYear,String companyId,String contractStatusType);
	
	
	/**
	 * 查询合同变更
	 * @param contractType
	 * @param controlYear
	 * @param companyId
	 * @param contractStatusType
	 * @return
	 */
	public List<Object[]> findSumOfContractStatusByContractId(String contractType,String controlYear,String companyId,String contractStatusType);
	
	
	public List<String> findBySQL(String sql);
}
