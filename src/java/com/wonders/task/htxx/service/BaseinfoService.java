package com.wonders.task.htxx.service;

import java.util.List;

import com.wonders.task.htxx.model.bo.DwContractBaseinfo;

public interface BaseinfoService {
	
	public void save(DwContractBaseinfo dwContractBaseinfo);
	
	public String findOneData(String sql);
	
	//数据源用datasource2
	public String findOneData2(String sql);
	
	public DwContractBaseinfo findByType(String type);
	
	public List<Object[]> findBySql(String sql);
	
	public boolean isResultExist(String sql);
	
	public void executeUpdateUpdate(String sql);
}
