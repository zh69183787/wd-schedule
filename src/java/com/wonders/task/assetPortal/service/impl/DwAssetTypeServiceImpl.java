package com.wonders.task.assetPortal.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wonders.task.assetPortal.dao.DwAssetTypeDao;
import com.wonders.task.assetPortal.service.DwAssetTypeService;

@Transactional(value = "txManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@Repository("dwAssetTypeService")
public class DwAssetTypeServiceImpl implements DwAssetTypeService{
	private DwAssetTypeDao dao;

	public DwAssetTypeDao getDao() {
		return dao;
	}

	@Autowired(required = false)
	public void setDao(@Qualifier(value = "dwAssetTypeDao") DwAssetTypeDao dao) {
		this.dao = dao;
	}
	
	public List<Object[]> countTypeByStation(){
		return dao.countTypeByStation();
	}
	
	public List<Object[]> countTypeByLine(){
		return dao.countTypeByLine();
	}
	
	public String findIdByLine(String line){
		return dao.findIdByLine(line);
	}
	
	@SuppressWarnings("unchecked")
	public Object load(String id,Class clazz){
		return dao.load(id, clazz);
	}
	
	@SuppressWarnings("unchecked")
	public void saveOrUpdateAll(Collection col){
		dao.saveOrUpdateAll(col);
	}
	
	public List<Object[]> countTypeAllLine(){
		return dao.countTypeAllLine();
	}
	
	public String findIdByStation(String line,String station){
		return dao.findIdByStation(line, station);
	}
	
	public List<Object[]> countValueByCompany(){
		return dao.countValueByCompany();
	}
	
	public String findIdByCompany(String line,String company){
		return dao.findIdByCompany(line, company);
	}
}
