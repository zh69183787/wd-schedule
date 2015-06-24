package com.wonders.task.investCost.contract.dao;

import java.util.List;

import com.wonders.task.investCost.contract.model.bo.Attach;

public interface AttachDao_HT {

	public List<Attach> findByGroupId(String groupId);
	
}
