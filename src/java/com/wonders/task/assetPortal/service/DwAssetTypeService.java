package com.wonders.task.assetPortal.service;

import java.util.Collection;
import java.util.List;

public interface DwAssetTypeService {
	public List<Object[]> countTypeByStation();
	
	public List<Object[]> countTypeByLine();
	
	public String findIdByLine(String line);
	
	@SuppressWarnings("unchecked")
	public Object load(String id,Class clazz);
	
	@SuppressWarnings("unchecked")
	public void saveOrUpdateAll(Collection col);
	
	public List<Object[]> countTypeAllLine();
	
	public String findIdByStation(String line,String station);
	
	public List<Object[]> countValueByCompany();
	
	public String findIdByCompany(String line,String company);
}
