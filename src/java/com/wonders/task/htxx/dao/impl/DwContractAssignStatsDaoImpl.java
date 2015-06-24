package com.wonders.task.htxx.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.wonders.task.htxx.dao.DwContractAssignStatsDao;
import com.wonders.task.htxx.model.bo.DwContractAssignStats;

@Repository("dwContractAssignStatsDao")
public class DwContractAssignStatsDaoImpl implements DwContractAssignStatsDao {
	private HibernateTemplate hibernateTemplate;

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	@Resource(name="hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	@Override
	public DwContractAssignStats findByContractTypeAndAssignTypeAndControlDate(String contractType, String assignType,String controlDate, String companyId) {
		String hql ="from DwContractAssignStats t where t.contractType='"+contractType+"' and t.assignType='"+assignType+"'" +
				" and t.controlDate='"+controlDate+"'";
		if(companyId!=null && !"".equals(companyId)){
			hql += " and t.controlCompanyId='"+companyId+"'";
		}
		List<DwContractAssignStats> list = hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(hql).setMaxResults(1).list();
		if(list==null || list.size()<1) return null ;
		return list.get(0);
	}
	@Override
	public void saveOrUpdateAll(List<DwContractAssignStats> dwContractAssignStatusList) {
		this.getHibernateTemplate().saveOrUpdateAll(dwContractAssignStatusList);
	}
	
	

}
