/**
 * 
 */
package com.wonders.task.build.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.wonders.task.build.dao.BuildDao;

/** 
 * @ClassName: BuildDaoImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-10-29 上午9:56:34 
 *  
 */

@Repository("buildDao")
public class BuildDaoImpl implements BuildDao{
	private HibernateTemplate hibernateTemplate;
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	
	//注入hibernateTemplate
	@Resource(name="hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	
	//批量插入统计表
	public void saveBatch(List<?> c){
		this.hibernateTemplate.saveOrUpdateAll(c);
	}
		
}
