package com.wonders.task.investCost.contract.service;

import java.util.List;

import com.wonders.task.investCost.contract.model.bo.Attach;

public interface AttachService_HT {

	public List<Attach> findBYGroupId(String groupId);
}
