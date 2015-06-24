package com.wonders.task.htxx.dao;

import com.wonders.task.htxx.model.bo.DwContractAssignType;

public interface DwContractAssignTypeDao {
	
	
	public DwContractAssignType findByControlYearAndContractTypeAndCompanyId(String contractType,String assignType,String controlYear,String companyId);
	
	public void saveOrUpdate(DwContractAssignType dwContractAssignType);
}
