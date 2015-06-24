/**
 * 
 */
package com.wonders.task.contractInfo.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.wonders.task.contractInfo.dao.ContractInfoDao;

/** 
 * @ClassName: ContractInfoDaoImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-5-12 上午8:43:22 
 *  
 */

@Repository("contractInfoDao")
public class ContractInfoDaoImpl implements ContractInfoDao{
private HibernateTemplate hibernateTemplate;
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	
	//注入hibernateTemplate
	@Resource(name="hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	
	//批量插入 
	public void saveBatch(List<?> c){
		this.hibernateTemplate.saveOrUpdateAll(c);
	}
}
