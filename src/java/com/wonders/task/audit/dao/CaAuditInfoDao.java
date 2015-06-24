package com.wonders.task.audit.dao;

import java.util.Collection;
import java.util.List;

public interface CaAuditInfoDao {
	public void save(Object obj);
	
	public List<Object[]> findActiveUser();
	
	public List<Object[]> findAllDept(String login_name);
	
	public String findIdByNameAndDept(String login_name,String dept_id);
	
	@SuppressWarnings("unchecked")
	public Object load(String id,Class clazz);
	
	public void update(Object obj);
	
	@SuppressWarnings("unchecked")
	public void saveOrUpdateAll(Collection col);
}
