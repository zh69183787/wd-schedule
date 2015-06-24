package com.wonders.task.investCost.report.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.wonders.task.investCost.contract.model.bo.Contract;
import com.wonders.task.investCost.report.bo.DwContractChange;
import com.wonders.task.investCost.report.bo.DwContractDestoryNumber;
import com.wonders.task.investCost.report.bo.DwProject;
import com.wonders.task.investCost.report.bo.Project;
import com.wonders.task.investCost.report.dao.ReportDao;

@Repository("reportDao")
public class ReportDaoImpl implements ReportDao{

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
	public void saveAll(List<DwContractDestoryNumber> list) {
		getHibernateTemplate().saveOrUpdateAll(list);
	}

	@Override
	public void deleteAllDestoryNumber() {
		String sql ="delete DW_CONTRACT_DESTORY_NUMBER";
		getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql).executeUpdate();
	}

	@Override
	public List<Object> findAllLine() {
		String sql ="select t.object_name from c_company_route t where t.type='2' and t.pid ='01' and t.removed='0' order by t.order_ ASC";
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		return session.createSQLQuery(sql).list();
	}

	@Override
	public List<Project> findProjectByYearAndLineAndType(int year,
			String lineName, String type) {
		String moneySource ="\"lineName\":\""+lineName+"\"";
		
		String hql="from Project t where t.removed='0' and t.approvalDate like '"+year+"%' and t.professionalType='"+type+"' and t.projectMoneysource like '%"+moneySource+"%'";
		return getHibernateTemplate().find(hql);
	}

	@Override
	public void saveAllDwProject(List<DwProject> list) {
		getHibernateTemplate().saveOrUpdateAll(list);
	}

	@Override
	public void deleteAllDwProject() {
		String sql ="delete DW_PROJECT";
		getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql).executeUpdate();
	}

	@Override
	public void deleteAllDwProjectApprove() {
		String sql ="delete DW_PROJECT_APPROVAL";
		getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql).executeUpdate();
	}

	@Override
	public void deleteDwContractChange() {
		String sql ="delete DW_CONTRACT_CHANGE";
		getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql).executeUpdate();
	}

	@Override
	public List<Object[]> findAllContractBySignedDateAndProjectType() {
		String sql="select p.project_no,p.project_name,p.project_type,c.id,c.contract_name,c.contract_price,c.contract_signed_date,c.build_Supplier_Name from c_contract c,c_project p " +
				"where c.removed='0' and p.removed='0' and c.contract_signed_date is not null " +
				" and c.project_id = p.id and p.project_type in ('1','2','3','4')";
		return getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql).list();
	}

	@Override
	public List<Object[]> findContractChange(String contractId) {
		String sql ="select s.money,s.reason from c_contract_status s where s.removed='0' and s.type ='1' and s.contract_id = '"+contractId+"'";
		return getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql).list();
	}

	@Override
	public void saveAlLDwContractChange(List<DwContractChange> list) {
		getHibernateTemplate().saveOrUpdateAll(list);
	}

	
}
