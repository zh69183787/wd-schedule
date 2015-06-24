package com.wonders.task.htxx.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.wonders.task.htxx.dao.ContractHtxxDao;
import com.wonders.task.investCost.contract.model.bo.Contract;

@Repository("contractHtxxDao")
public class ContractHtxxDaoImpl implements ContractHtxxDao{

	private HibernateTemplate hibernateTemplate;
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	//注入hibernateTemplate
	@Resource(name="hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	
	
	@Override
	public Contract findEarliestContract() {
		String hql ="from Contract t where t.removed='0' and t.contractSignedDate is not null order by t.contractSignedDate ASC";
		return (Contract) getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql).setMaxResults(1).uniqueResult();
	}
	@Override
	public List<Object[]> findSumOfInviteBidBySignedDateAndContractType(String contractType,String controlYear,String companyId) {
		String sql ="select t.invite_bid_type,count(*) from c_contract t where t.removed='0' " +
				" and t.contract_signed_date like '"+controlYear+"%' and t.contract_type like '"+contractType+",%' and t.invite_bid_type is not null ";
		if(companyId!=null && !"".equals(companyId)){
			if("other".equals(companyId)){
				sql += " and t.contract_owner_execute_id not in ('2541','2542','2543','2544','2540','2718','2719','2720','2721','2722','2545','2546')";
			}else if("center".equals(companyId)){
				sql += " and t.contract_owner_execute_id in ('2718','2719','2720','2721','2722','2545')";
			}else{
				sql += " and t.contract_owner_execute_id='"+companyId+"'";
			}
		}
			sql+= " group by t.invite_bid_type";
		Session session = this.hibernateTemplate.getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery(sql);
		return query.list(); 
	}
	
	@Override
	public List<Object[]> findSumOfContractStatusBySignedDateAndContractType(String contractType, String controlYear, String companyId) {
		String sql ="select t.contract_status,count(*) from c_contract t where t.removed='0' " +
				" and t.contract_signed_date like '"+controlYear+"%' and t.contract_type like '"+contractType+",%' and t.contract_status is not null ";
		if(companyId!=null && !"".equals(companyId)){
			if("other".equals(companyId)){
				sql += " and t.contract_owner_execute_id not in ('2541','2542','2543','2544','2540','2718','2719','2720','2721','2722','2545','2546')";
			}else if("center".equals(companyId)){
				sql += " and t.contract_owner_execute_id in ('2718','2719','2720','2721','2722','2545')";
			}else{
				sql += " and t.contract_owner_execute_id='"+companyId+"'";
			}
		}
			sql+= " group by t.contract_status";
		Session session = this.hibernateTemplate.getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery(sql);
		return query.list(); 
	}
	@Override
	public List<Object[]> findSumOfContractStatus(String contractType,String controlYear, String companyId,String contractStatusType) {
		String sql = "select count(*),sum(t.money) from c_contract_status t,c_contract c " +
				" where t.removed='0' and c.removed='0' and t.contract_id =c.id and c.contract_type like '"+contractType+",%'" +
				" and t.operate_date like '"+controlYear+"%' and t.type ='"+contractStatusType+"'";
		if(companyId!=null && !"".equals(companyId)){
			if("other".equals(companyId)){
				sql += " and c.contract_owner_execute_id not in ('2541','2542','2543','2544','2540','2718','2719','2720','2721','2722','2545','2546')";
			}else if("center".equals(companyId)){
				sql += " and c.contract_owner_execute_id in ('2718','2719','2720','2721','2722','2545')";
			}else{
				sql += " and c.contract_owner_execute_id='"+companyId+"'";
			}
			
		}
		//sql +=" group by t.contract_id";
		Session session = this.hibernateTemplate.getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery(sql);
		return query.list(); 
	}
	@Override
	public List<Object[]> findSumOfContractStatusByContractId(
			String contractType, String controlYear, String companyId,
			String contractStatusType) {
		String sql = "select count(*),sum(t.money),t.contract_id from c_contract_status t,c_contract c " +
				" where t.removed='0' and c.removed='0' and t.contract_id =c.id and c.contract_type like '"+contractType+",%'" +
				" and t.operate_date like '"+controlYear+"%' and t.type ='"+contractStatusType+"'";
		if(companyId!=null && !"".equals(companyId)){
			if("other".equals(companyId)){
				sql += " and c.contract_owner_execute_id not in ('2541','2542','2543','2544','2540','2718','2719','2720','2721','2722','2545','2546')";
			}else if("center".equals(companyId)){
				sql += " and c.contract_owner_execute_id in ('2718','2719','2720','2721','2722','2545')";
			}else{
				sql += " and c.contract_owner_execute_id='"+companyId+"'";
			}
		}
		sql +=" group by t.contract_id";
		Session session = this.hibernateTemplate.getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery(sql);
		return query.list(); 
	}
	@Override
	public List<String> findBySQL(String sql) {
		Session session = this.hibernateTemplate.getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery(sql);
		return query.list();
	}
	
	
	
	
}
