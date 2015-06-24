package com.wonders.task.investCost.contract.service;

import java.util.List;

import com.wonders.task.investCost.contract.model.bo.Contract;

public interface ContractService {

	public void saveAll(List<Contract> contractList);
	
	public List<Object[]> findAllContractForDestoryNumber();
	
	public Double findChangedMoneyByContractId(String id);
	
	public void bindProjectId();
}
