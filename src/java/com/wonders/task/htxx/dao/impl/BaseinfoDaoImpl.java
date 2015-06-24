package com.wonders.task.htxx.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.wonders.task.htxx.dao.BaseinfoDao;
import com.wonders.task.htxx.model.bo.DwContractBaseinfo;

@Repository("baseinfoDao")
public class BaseinfoDaoImpl implements BaseinfoDao{

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
	@Resource(name="hibernateTemplate2")
	public void setHibernateTemplate2(HibernateTemplate hibernateTemplate2) {
		this.hibernateTemplate2 = hibernateTemplate2;
	}
	@Override
	public void save(DwContractBaseinfo dwContractBaseinfo) {
		this.getHibernateTemplate().saveOrUpdate(dwContractBaseinfo);
	}
	@Override
	public String executeSQLReturnOneData(String sql) {
		List<Object> result = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql).list();
		if(result==null || result.size()<1 || result.get(0)==null) return "0";
		return result.get(0).toString();
	}
	
	@Override
	public String executeSQLReturnOneData2(String sql) {
		List<Object> result = getHibernateTemplate2().getSessionFactory().getCurrentSession().createSQLQuery(sql).list();
		if(result==null || result.size()<1 || result.get(0)==null) return "0";
		return result.get(0).toString();
	}
	
	
	@Override
	public DwContractBaseinfo findByType(String type) {
		String hql = "from DwContractBaseinfo t where t.type='"+type+"'";
		Query query = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql);
		List<DwContractBaseinfo> list = query.setMaxResults(1).list();
		if(list==null || list.size()<1) return null;
		return list.get(0);
	}
	@Override
	public List<Object[]> executeSqlWithResult(String sql) {
		Query query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		List<Object[]> list = query.list();
		return list;
	}
	@Override
	public void executeSqlUpdate(String sql) {
		Query query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		query.executeUpdate();
	}
	
	
	
	
	
}
