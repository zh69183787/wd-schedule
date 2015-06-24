package com.wonders.task.htxx.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.wonders.task.htxx.dao.DwContractManagementDao;
import com.wonders.task.htxx.model.bo.DwContractManagement;

@Repository("dwContractManagementDao")
public class DwContractManagementDaoImpl implements DwContractManagementDao {
	private HibernateTemplate hibernateTemplate;

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	@Resource(name="hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	@Override
	public List<DwContractManagement> findDwContractManagement() {
		String hql = "from DwContractManagement t order by t.orderNo";
		Session session = this.hibernateTemplate.getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		List<DwContractManagement> list = query.list();
		return list;
	}

}
