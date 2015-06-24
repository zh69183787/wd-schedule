package com.wonders.task.htxx.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.wonders.task.htxx.dao.DwContractAssignTypeDao;
import com.wonders.task.htxx.model.bo.DwContractAssignType;

@Repository("dwContractAssignTypeDao")
public class DwContractAssignTypeDaoImpl implements DwContractAssignTypeDao {

	private HibernateTemplate hibernateTemplate;

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	@Resource(name="hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	@Override
	public DwContractAssignType findByControlYearAndContractTypeAndCompanyId(String contractType,String assignType,String controlYear,String companyId) {
		String hql ="from DwContractAssignType t where t.contractType='"+contractType+"' and t.controlYear='"+controlYear+"' and t.assignType='"+assignType+"'" ;
		if(companyId!=null && !"".equals(companyId)){
				hql += " and t.controlCompanyId='"+companyId+"'";
		}
		List<DwContractAssignType> list = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql).setMaxResults(1).list();
		if(list==null || list.size()<1) return null;
		return list.get(0); 
	}
	@Override
	public void saveOrUpdate(DwContractAssignType dwContractAssignType) {
		getHibernateTemplate().saveOrUpdate(dwContractAssignType);
	}
	
	

}
