package com.wonders.task.htxx.dao;

import java.util.List;

import com.wonders.task.htxx.model.bo.DwContractAssignStats;

public interface DwContractAssignStatsDao {
	
	public DwContractAssignStats findByContractTypeAndAssignTypeAndControlDate(String contractType,String assignType,String controlDate,String companyId);
	
	public void saveOrUpdateAll(List<DwContractAssignStats> dwContractAssignStatusList);
}
