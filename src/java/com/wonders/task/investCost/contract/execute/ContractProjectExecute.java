package com.wonders.task.investCost.contract.execute;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wonders.task.investCost.contract.service.ContractService;
import com.wonders.task.sample.ITaskService;

/**
 * 每天轮循设置contract.projectId
 * @author ycl
 *
 */
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@Service("contractProjectExecute")
@Scope("prototype")
public class ContractProjectExecute implements ITaskService{
	
	
	
	public ContractProjectExecute() {
		System.out.println("ContractProjectExecute init.......");
	}

	private ContractService contractService; 
	
	@Autowired(required=false)
	public void setContractService(@Qualifier(value="contractService")ContractService contractService) {
		this.contractService = contractService;
	}
	
	@Override
	public String exec(String param) {
		contractService.bindProjectId();
		return "";
	}
	
	
	
	
}
