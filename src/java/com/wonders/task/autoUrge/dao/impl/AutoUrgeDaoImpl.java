/**
 * 
 */
package com.wonders.task.autoUrge.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.wonders.task.autoUrge.dao.AutoUrgeDao;

/** 
 * @ClassName: AutoUrgeDaoImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-4-29 上午11:11:39 
 *  
 */

@Repository("autoUrgeDao")
public class AutoUrgeDaoImpl implements AutoUrgeDao{
	private HibernateTemplate hibernateTemplate;
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	
	//注入hibernateTemplate
	@Resource(name="hibernateTemplate2")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	
	//批量插入短信 
	public void saveBatch(List<?> c){
		this.hibernateTemplate.saveOrUpdateAll(c);
	}
	
	
}
