package com.wonders.task.investCost.contract.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.wonders.task.investCost.contract.dao.ContractDao;
import com.wonders.task.investCost.contract.model.bo.Contract;

@Repository("contractDao")
public class ContractDaoImpl implements ContractDao{

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
	public void saveAll(List<Contract> contractList) {
		getHibernateTemplate().saveOrUpdateAll(contractList);
	}
	@Override
	public List<Object[]> findAllContractForDestoryNumber() {
		String sql="select c.id,c.contract_name,c.contract_no,c.build_supplier_name,c.contract_price,c.nation_verify_price,c.verify_price,c.final_price,c.contract_status,p.id,p.project_name,p.project_no,p.project_type,c.contract_signed_date from c_contract c,c_project p where " +
				"c.removed='0' and p.removed='0' and c.contract_type in ('2','2,1','2,2','2,3') and c.contract_status in ('1','3') and c.project_id = p.id";
		Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
		return session.createSQLQuery(sql).list();
		
	}
	@Override
	public Double findChangedMoneyByContractId(String id) {
		String sql = "select sum(s.money) from c_contract_status s where s.removed='0' and s.id='"+id+"' and s.type='1'";
		List<Double> result = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql).list();
		if(result!=null && result.size()>0){
			return result.get(0);
		}
		return null;
	}
	
	@Override
	public void bindProjectId() {
		String sql="update c_contract c set c.project_id = ("+
			"select p.id from c_project p where c.project_name = p.project_name and p.removed='0' )"+
			"where c.removed='0' and c.project_name is not null and c.project_id is null";
		Session session =null;
		try {
			session = hibernateTemplate.getSessionFactory().openSession();
			Transaction tx = session.beginTransaction();
			session.createSQLQuery(sql).executeUpdate();
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			if(session!=null) session.close();
		}
	}
	
	
}
