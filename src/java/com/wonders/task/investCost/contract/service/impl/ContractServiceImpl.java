package com.wonders.task.investCost.contract.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.wonders.task.investCost.contract.dao.ContractDao;
import com.wonders.task.investCost.contract.model.bo.Contract;
import com.wonders.task.investCost.contract.service.ContractService;

@Repository("contractService")
public class ContractServiceImpl implements ContractService{

	private ContractDao contractDao;
	@Autowired(required=false)
	public void setContractDao(@Qualifier(value="contractDao")ContractDao contractDao) {
		this.contractDao = contractDao;
	}
	@Override
	public void saveAll(List<Contract> contractList) {
		contractDao.saveAll(contractList);
	}
	@Override
	public List<Object[]> findAllContractForDestoryNumber(){
		
		return contractDao.findAllContractForDestoryNumber();
	}
	@Override
	public Double findChangedMoneyByContractId(String id) {
		return contractDao.findChangedMoneyByContractId(id);
	}
	@Override
	public void bindProjectId() {
		contractDao.bindProjectId();
	}
	
	
}
