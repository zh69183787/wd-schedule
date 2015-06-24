package com.wonders.task.marquee.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wonders.task.marquee.dao.OldDao;
import com.wonders.task.marquee.service.OldService;

@Transactional(value = "txManager2", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@Repository("oldService")
public class OldServiceImpl implements OldService{
	private OldDao oldDao;

	@Autowired(required = false)
	public void setOldDao(@Qualifier(value = "oldDao") OldDao oldDao) {
		this.oldDao = oldDao;
	}
	
	public void saveOrUpdateAll(Collection cols){
		oldDao.saveOrUpdateAll(cols);
	}
	
	public void updateBySql(String sql){
		oldDao.updateBySql(sql);
	}
	
	public List<Object[]> findBySql(String sql){
		return oldDao.findBySql(sql);
	}
}
