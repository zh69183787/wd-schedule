package com.wonders.task.investCost.contract.service;

import java.util.List;

import com.wonders.task.investCost.contract.model.bo.Attach;

public interface AttachService_IC {

	public void saveAll(List<Attach> attachList);
	
	public void save(Attach attach);
}
