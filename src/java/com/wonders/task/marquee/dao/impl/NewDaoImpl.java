package com.wonders.task.marquee.dao.impl;

import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.jdbc.object.SqlQuery;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.wonders.task.marquee.dao.NewDao;

@Repository("newDao")
public class NewDaoImpl implements NewDao{
	private HibernateTemplate hibernateTemplate;
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	
	//注入hibernateTemplate2
	@Resource(name="hibernateTemplate")
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
	
	public List<Object[]> findBySql(String sql,List<Object> args){
		Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery(sql);
		if(args !=null && args.size() > 0){
			for(int i =0;i<args.size();i++){
				query.setParameter(i, args.get(i));
			}
		}
		return query.list();
	}
	
	public String findUniqueString(String name,String sql,List<Object> sss){
		Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
		SQLQuery query = session.createSQLQuery(sql);
		if(sss !=null && sss.size() > 0){
			for(int i =0;i<sss.size();i++){
				query.setParameter(i, sss.get(i));
			}
		}
		return (String) query.addScalar(name, Hibernate.STRING).uniqueResult();
	}
}
