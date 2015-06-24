package com.wonders.task.investCost.contract.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.wonders.task.investCost.contract.dao.HtBaDao;
import com.wonders.task.investCost.contract.model.bo.HtBa;

@Repository("htBaDao")
public class HtBaDaoImpl implements HtBaDao{

	private HibernateTemplate hibernateTemplate;

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	@Resource(name="hibernateTemplate2")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	@Override
	public HtBa findByHtXxId(Long htXxId) {
		String hql = "from HtBa t where t.htId="+htXxId;
		List<HtBa> htBaList = getHibernateTemplate().find(hql);
		if(htBaList!=null && htBaList.size()>0){
			return htBaList.get(0);
		}
		return null;
	}
	
	
}
