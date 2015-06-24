package com.wonders.task.audit.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wonders.task.audit.dao.CaAuditInfoDao;
import com.wonders.task.audit.service.CaAuditInfoService;


@Transactional(value = "txManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@Repository("caAuditInfoService")
@Scope("prototype")
public class CaAuditInfoServiceImpl implements CaAuditInfoService{
	private CaAuditInfoDao caAuditInfoDao;

	public CaAuditInfoDao getCaAuditInfoDao() {
		return caAuditInfoDao;
	}

	@Autowired(required = false)
	public void setCaAuditInfoDao(@Qualifier(value = "caAuditInfoDao") CaAuditInfoDao caAuditInfoDao) {
		this.caAuditInfoDao = caAuditInfoDao;
	}
	
	public void save(Object obj) {
		caAuditInfoDao.save(obj);
	}
	
	public List<Object[]> findActiveUser(){
		return caAuditInfoDao.findActiveUser();
	}
	
	public List<Object[]> findAllDept(String login_name){
		return caAuditInfoDao.findAllDept(login_name);
	}
	
	public String findIdByNameAndDept(String login_name,String dept_id){
		return caAuditInfoDao.findIdByNameAndDept(login_name, dept_id);
	}
	
	@SuppressWarnings("unchecked")
	public Object load(String id,Class clazz){
		return caAuditInfoDao.load(id, clazz);
	}
	
	public void update(Object obj){
		caAuditInfoDao.update(obj);
	}
	
	@SuppressWarnings("unchecked")
	public void saveOrUpdateAll(Collection col){
		caAuditInfoDao.saveOrUpdateAll(col);
	}
}
