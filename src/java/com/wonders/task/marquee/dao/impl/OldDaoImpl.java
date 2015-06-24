package com.wonders.task.marquee.dao.impl;

import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.wonders.task.marquee.dao.OldDao;

@Repository("oldDao")
public class OldDaoImpl implements OldDao{
	private HibernateTemplate hibernateTemplate;
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	
	//注入hibernateTemplate2
	@Resource(name="hibernateTemplate2")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	public void saveOrUpdateAll(Collection cols){
		hibernateTemplate.saveOrUpdateAll(cols);
	}
	
	public void updateBySql(String sql){
		Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.executeUpdate();
	}
	
	public List<Object[]> findBySql(String sql){
		Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery(sql);
		return query.list();
	}
}
