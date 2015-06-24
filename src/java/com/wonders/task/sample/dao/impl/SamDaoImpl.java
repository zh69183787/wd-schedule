/**
 * 
 */
package com.wonders.task.sample.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.wonders.task.sample.dao.SamDao;

/** 
 * @ClassName: SamDaoImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-17 上午9:21:34 
 *  
 */
@Repository("samDao")
public class SamDaoImpl implements SamDao{
	private HibernateTemplate hibernateTemplate;
	/** 
	* @Title: save 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param o    设定文件 
	* @throws 
	*/
	@Override
	public void save(Object o) {
		this.getHibernateTemplate().save(o);
		
	}

	@Override
	public void saveAll(List l) {
		this.getHibernateTemplate().saveOrUpdateAll(l);
		
	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	
	//注入hibernateTemplate
	@Resource(name="hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
}
