package com.wonders.task.investCost.contract.dao;

import java.util.List;

import com.wonders.task.investCost.contract.model.bo.Attach;

public interface AttachDao_IC {

	public void saveAll(List<Attach> attachList);
	
	public void save(Attach attach);
}
