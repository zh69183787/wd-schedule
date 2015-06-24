package com.wonders.task.marquee.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wonders.task.marquee.dao.NewDao;
import com.wonders.task.marquee.service.NewService;

@Transactional(value = "txManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@Repository("newService")
public class NewServiceImpl implements NewService{
	private NewDao newDao;

	@Autowired(required = false)
	public void setNewDao(@Qualifier(value = "newDao") NewDao newDao) {
		this.newDao = newDao;
	}
	
	public void saveOrUpdateAll(Collection cols){
		newDao.saveOrUpdateAll(cols);
	}
	
	public void updateBySql(String sql){
		newDao.updateBySql(sql);
	}
	
	public List<Object[]> findBySql(String sql){
		return newDao.findBySql(sql);
	}
	
	public String findUniqueString(String name,String sql,List<Object> sss){
		return newDao.findUniqueString(name, sql, sss);
	}
	
	public List<Object[]> findBySql(String sql,List<Object> args){
		return newDao.findBySql(sql, args);
	}
}
