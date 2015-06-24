package com.wonders.task.htxx.dao;

import java.util.List;

import com.wonders.task.htxx.model.bo.DwContractBaseinfo;


public interface BaseinfoDao {
	
	public void save(DwContractBaseinfo dwContractBaseinfo);
	
	/**
	 * 执行sql语句，只查询一个值
	 * @param sql
	 * @return
	 */
	public String executeSQLReturnOneData(String sql);
	
	/**
	 * datasource2的数据源
	 * @param sql
	 * @return
	 */
	public String executeSQLReturnOneData2(String sql);
	
	public DwContractBaseinfo findByType(String type);
	
	public List<Object[]> executeSqlWithResult(String sql);
	
	public void executeSqlUpdate(String sql);
}
