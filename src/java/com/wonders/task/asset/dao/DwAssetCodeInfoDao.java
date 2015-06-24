package com.wonders.task.asset.dao;

import java.util.Collection;
import java.util.List;

public interface DwAssetCodeInfoDao {

	public List<Object[]> findAllDwAsset();

	public List<Object[]> findAllCountByTypeidDm();
	
	public String findIdByTypeIdAndDm(String typeId,String dm);
	
	@SuppressWarnings("unchecked")
	public Object load(String id,Class clazz);

	@SuppressWarnings("unchecked")
	public void saveOrUpdateAll(Collection col);
}
