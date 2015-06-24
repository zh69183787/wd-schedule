package com.wonders.task.htxx.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.wonders.task.htxx.dao.BaseinfoDao;
import com.wonders.task.htxx.model.bo.DwContractBaseinfo;
import com.wonders.task.htxx.service.BaseinfoService;

@Service("baseinfoService")
public class BaseinfoServiceImpl implements BaseinfoService{

	@Autowired(required=false)
	private BaseinfoDao baseinfoDao;
	
	public void setBaseinfoDao(@Qualifier("baseinfoDao")BaseinfoDao baseinfoDao) {
		this.baseinfoDao = baseinfoDao;
	}

	@Override
	public void save(DwContractBaseinfo dwContractBaseinfo) {
		this.baseinfoDao.save(dwContractBaseinfo);
	}

	@Override
	public String findOneData(String sql) {
		
		return this.baseinfoDao.executeSQLReturnOneData(sql);
	}
	
	@Override
	public String findOneData2(String sql) {
		return this.baseinfoDao.executeSQLReturnOneData2(sql);
	}

	@Override
	public DwContractBaseinfo findByType(String type) {

		return baseinfoDao.findByType(type);
	}

	@Override
	public List<Object[]> findBySql(String sql) {
	
		return this.baseinfoDao.executeSqlWithResult(sql);
	}

	@Override
	public boolean isResultExist(String sql) {
		List<Object[]> list = this.baseinfoDao.executeSqlWithResult(sql);
		if(list==null || list.size()<1) return false;
		return true;
	}

	@Override
	public void executeUpdateUpdate(String sql) {
		baseinfoDao.executeSqlUpdate(sql);
	}
	
	
	
	
}
