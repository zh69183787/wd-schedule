package com.wonders.task.investCost.contract.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.wonders.task.investCost.contract.dao.AttachDao_IC;
import com.wonders.task.investCost.contract.model.bo.Attach;

@Repository("ittachDaoImpl_IC")
public class AttachDaoImpl_IC implements AttachDao_IC{
	private HibernateTemplate hibernateTemplate;
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	@Resource(name="hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	@Override
	public synchronized void saveAll(List<Attach> attachList) {
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		tx = session.beginTransaction();
		if(attachList!=null && attachList.size()>0){
			for(int i=0; i<attachList.size(); i++){
				session.save(attachList.get(i));
			}
		}
		tx.commit();
		session.close();
//		getHibernateTemplate().saveOrUpdateAll(attachList);
	}
	
	
	
	@Override
	public void save(Attach attach) {
		this.hibernateTemplate.save(attach);
	}
	
	
	
}
