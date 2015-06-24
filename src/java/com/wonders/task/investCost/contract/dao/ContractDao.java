package com.wonders.task.investCost.contract.dao;

import java.util.List;

import com.wonders.task.investCost.contract.model.bo.Contract;

public interface ContractDao {

	public void saveAll(List<Contract> contractList);
	
	public List<Object[]> findAllContractForDestoryNumber();
	
	public Double findChangedMoneyByContractId(String id);
	
	public void bindProjectId();
}
