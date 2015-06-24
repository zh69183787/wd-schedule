package com.wonders.task.audit.dbx.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wonders.task.audit.dbx.dao.DbxDao;
import com.wonders.task.audit.dbx.service.DbxService;


@Transactional(value = "txManager2", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@Repository("dbxService")
@Scope("prototype")
public class DbxServiceImpl implements DbxService{
	private DbxDao dbxDao;
	
	public DbxDao getDbxDao() {
		return dbxDao;
	}

	@Autowired(required = false)
	public void setDbxDao(@Qualifier(value = "dbxDao") DbxDao dbxDao) {
		this.dbxDao = dbxDao;
	}

	public List<String[]> findTasksingByLoginName(String loginName){
		return dbxDao.findTasksingByLoginName(loginName);
	}
	
	public Map<Long,Integer> getDeptsLevel(List<String> deptIds){
		return dbxDao.getDeptsLevel(deptIds);
	}
	
	public String countTimeOut(String loginName){
		return dbxDao.countTimeOut(loginName);
	}
	
	public int getUrgeCount(String userName,String nowDeptId,boolean falg,List<String> deptIds){
		return dbxDao.getUrgeCount(userName, nowDeptId, falg, deptIds);
	}
	public long countMessage(String login_name){
		return dbxDao.countMessage(login_name);
	}
	public int countJbx(String loginName,String dept_id){
		return dbxDao.countJbx(loginName, dept_id);
	}
}
