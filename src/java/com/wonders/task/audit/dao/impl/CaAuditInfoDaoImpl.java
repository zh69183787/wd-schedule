package com.wonders.task.audit.dao.impl;

import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.wonders.task.audit.dao.CaAuditInfoDao;

@Repository("caAuditInfoDao")
public class CaAuditInfoDaoImpl implements CaAuditInfoDao{
	private HibernateTemplate hibernateTemplate;
	private HibernateTemplate hibernateTemplate2;
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	
	//注入hibernateTemplate
	@Resource(name="hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	public HibernateTemplate getHibernateTemplate2() {
		return hibernateTemplate2;
	}
	
	//注入hibernateTemplate2
	@Resource(name="hibernateTemplate2")
	public void setHibernateTemplate2(HibernateTemplate hibernateTemplate2) {
		this.hibernateTemplate2 = hibernateTemplate2;
	}
	
	public void save(Object obj) {
		hibernateTemplate.save(obj);
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> findActiveUser(){
		String sql = "select distinct u.login_name from ca_visit_log c,t_user_relation t,t_user u where c.appname='AUTO_LOGIN' and c.visit_time>sysdate-14 and t.c_id = c.userid and u.id = t.t_id";
		SQLQuery query = hibernateTemplate.getSessionFactory().getCurrentSession().createSQLQuery(sql);
		query.addScalar("login_name", Hibernate.STRING);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> findAllDept(String login_name){
		String sql = "select distinct parent_node_id from v_userdep where login_name = '"+login_name+"'";
		SQLQuery query = hibernateTemplate2.getSessionFactory().getCurrentSession().createSQLQuery(sql);
		query.addScalar("parent_node_id", Hibernate.INTEGER);
		return query.list();
	}
	
	public String findIdByNameAndDept(String login_name,String dept_id){
		String sql = "select t.id from ca_audit_info t where t.login_name = '"+login_name+"' and t.dept_id = '"+dept_id+"' and t.removed = '0'";
		SQLQuery query = hibernateTemplate.getSessionFactory().getCurrentSession().createSQLQuery(sql);
		query.addScalar("id", Hibernate.STRING);
		return (String)query.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public Object load(String id,Class clazz) {
		Object instance = getHibernateTemplate().get(clazz, id);
		return instance;
	}
	
	public void update(Object obj) {
		getHibernateTemplate().update(obj);
	}
	
	@SuppressWarnings("unchecked")
	public void saveOrUpdateAll(Collection col) {
		getHibernateTemplate().saveOrUpdateAll(col);
	}
}
