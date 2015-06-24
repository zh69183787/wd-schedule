package com.wonders.task.investCost.contract.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.wonders.task.investCost.contract.dao.IncidentsDao;
import com.wonders.task.investCost.contract.model.bo.HtXx;
import com.wonders.task.investCost.contract.util.HtXxUtil;


@Repository("incidentsDao")
public class IncidentsDaoImpl implements IncidentsDao {
	private HibernateTemplate hibernateTemplate;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	@Resource(name="hibernateTemplate2")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	@Override
	public List<HtXx> findIncidentsByDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "SELECT h.* FROM Ht_Xx h LEFT JOIN Incidents t ON h.model_id = t.processname"+ 
			" WHERE h.pinstance_id = t.incident and h.removed='0' AND t.status = '2' AND h.flag='1' " ;
		if(date!=null){
			sql += "AND to_char(t.endtime,'yyyy-mm-dd hh24:mi:ss')>'"+sdf.format(date)+"'"; 
		}
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		List<Object[]> objList = session.createSQLQuery(sql).list();
		tx.commit();
		session.close();
		List<HtXx> htxxList = HtXxUtil.convertObjectArrayToHtXxList(objList);
		return htxxList;
	}
	
	
}
