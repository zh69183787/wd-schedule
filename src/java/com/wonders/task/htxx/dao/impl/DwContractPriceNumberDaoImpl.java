package com.wonders.task.htxx.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.wonders.task.htxx.dao.DwContractPriceNumberDao;
import com.wonders.task.htxx.model.bo.DwContractPriceNumber;

@Repository("dwContractPriceNumberDao")
public class DwContractPriceNumberDaoImpl implements DwContractPriceNumberDao{
	private HibernateTemplate hibernateTemplate;

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	@Resource(name="hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DwContractPriceNumber> findDwContractPriceNumber(String controlYear,int controlType,String companyName,String type) {
		String hql = "from DwContractPriceNumber t where t.dataType = 1 and t.contractType = "+controlType;
		if(!"".equals(companyName)){
			hql += " and t.companyId = '"+companyName+"'";
		}
		if("default".equals(type)){
			hql += " and t.controlDate < to_char(sysdate,'YYYY-MM-DD') and t.controlDate > to_char(add_months(sysdate,-12),'YYYY-MM-DD') ";
		}else if("month".equals(type)){
			hql += " and t.controlDate like '%"+controlYear+"%' ";
		}else if("year".equals(type)){
			hql += " and t.controlDate < '"+(Integer.valueOf(controlYear)+1)+"' ";
		}
		hql += " order by t.controlDate ";
		Session session = this.hibernateTemplate.getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		List<DwContractPriceNumber> list = query.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DwContractPriceNumber> findDwContractPriceNumberThisMonth(String contractType,String companyId,String dataType){
		String hql = "from DwContractPriceNumber t where t.dataType = "+dataType+" and t.controlDate = to_char(sysdate,'YYYY-MM') and t.contractType = "+contractType;
		Session session = this.hibernateTemplate.getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		List<DwContractPriceNumber> list = query.list();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DwContractPriceNumber> findDwContractPriceNumberSum(String controlYear,String contractType,String companyId,String dataType){
		String hql = "from DwContractPriceNumber t where t.dataType = "+dataType+" and t.contractType = "+contractType;
		Session session = this.hibernateTemplate.getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		List<DwContractPriceNumber> list = query.list();
		return list;
	}
}
