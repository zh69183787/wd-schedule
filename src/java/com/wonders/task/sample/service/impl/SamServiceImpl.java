/**
 * 
 */
package com.wonders.task.sample.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wonders.task.sample.dao.SamDao;
import com.wonders.task.sample.service.SamService;

/** 
 * @ClassName: SamServiceImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-17 上午9:20:28 
 *  
 */
@Transactional(value = "txManager",propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
@Service("samService")
public class SamServiceImpl implements SamService{

	private SamDao dao;

	/**
	 * @return the dao
	 */
	public SamDao getDao() {
		return dao;
	}

	/**
	 * @param dao the dao to set
	 */
	@Autowired(required=false)
	public void setDao(@Qualifier("samDao")SamDao dao) {
		this.dao = dao;
	}
	
	public void saveAll(Object o1,Object o2){
		this.dao.save(o1);
		//System.out.println(0/0);
		//this.dao.save(o2);
	}
	
	public void saveAllList(List l ){
		this.dao.saveAll(l);
		//System.out.println(0/0);
		//this.dao.save(o2);
	}
}
