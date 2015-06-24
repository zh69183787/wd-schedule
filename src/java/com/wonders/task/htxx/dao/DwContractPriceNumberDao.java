package com.wonders.task.htxx.dao;

import java.util.List;

import com.wonders.task.htxx.model.bo.DwContractPriceNumber;


public interface DwContractPriceNumberDao {
	public List<DwContractPriceNumber> findDwContractPriceNumber(String controlYear,int controlType,String companyName,String type);

	public List<DwContractPriceNumber> findDwContractPriceNumberThisMonth(String contractType,String companyId,String dataType);

	public List<DwContractPriceNumber> findDwContractPriceNumberSum(String controlYear,String contractType,String companyId,String dataType);
}
