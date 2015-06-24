package com.wonders.task.investCost.contract.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.wonders.task.investCost.contract.dao.AttachDao_HT;
import com.wonders.task.investCost.contract.model.bo.Attach;

@Repository("ittachDaoImpl_HT")
public class AttachDaoImpl_HT implements AttachDao_HT{

	private HibernateTemplate hibernateTemplate;
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	@Resource(name="hibernateTemplate2")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	@Override
	public List<Attach> findByGroupId(String groupId) {
		String hql= "from Attach t where t.groupid = '"+groupId+"' and t.removed=0";
		return getHibernateTemplate().find(hql);
	}
	
	
	
	
}
