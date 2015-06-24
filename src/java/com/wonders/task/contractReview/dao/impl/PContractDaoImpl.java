/**
 * 
 */
package com.wonders.task.contractReview.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import com.wonders.task.contractReview.model.bo.PContract;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.wonders.task.contractReview.dao.PContractDao;

/** 
 * @ClassName: PContractDaoImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年1月21日 下午3:21:09 
 *  
 */
@Repository("pContractDao")
public class PContractDaoImpl implements PContractDao{
	private HibernateTemplate hibernateTemplate;
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	
	//注入hibernateTemplate
	@Resource(name="hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	public void save(Object entity){
		this.hibernateTemplate.save(entity);
	}

    @Override
    public List<PContract> findBySelfNo(String selfNo) {
        Session session  =this.hibernateTemplate.getSessionFactory().getCurrentSession();
        List<PContract> list =  ( List<PContract> )session.createQuery("from PContract where selfNo = :selfNo and removed = :removed order by update_date desc").setString("selfNo",selfNo).setString("removed","0").list();
        session.flush();
        session.clear();
        return list;
    }

    //批量插入
	public void saveBatch(List<?> c){
		this.hibernateTemplate.saveOrUpdateAll(c);
	}
}
