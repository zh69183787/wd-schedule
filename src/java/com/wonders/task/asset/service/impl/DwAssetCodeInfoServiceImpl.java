package com.wonders.task.asset.service.impl;

import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.wonders.task.asset.dao.DwAssetCodeInfoDao;
import com.wonders.task.asset.service.DwAssetCodeInfoService;


@Transactional(value = "txManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@Repository("dwAssetCodeInfoService")
public class DwAssetCodeInfoServiceImpl implements DwAssetCodeInfoService {
	private DwAssetCodeInfoDao dwAssetCodeInfoDao;

	public DwAssetCodeInfoDao getDwAssetCodeInfoDao() {
		return dwAssetCodeInfoDao;
	}

	@Autowired(required = false)
	public void setDwAssetCodeInfoDao(@Qualifier(value = "dwAssetCodeInfoDao") DwAssetCodeInfoDao dwAssetCodeInfoDao) {
		this.dwAssetCodeInfoDao = dwAssetCodeInfoDao;
	}

	@Override
	public List<Object[]> findAllCountByTypeidDm() {
		return dwAssetCodeInfoDao.findAllCountByTypeidDm();
	}

	@Override
	public List<Object[]> findAllDwAsset() {
		return dwAssetCodeInfoDao.findAllDwAsset();
	}

	@SuppressWarnings("unchecked")
	public void saveOrUpdateAll(Collection col) {
		dwAssetCodeInfoDao.saveOrUpdateAll(col);
	}

	@Override
	public String findIdByTypeIdAndDm(String typeId, String dm) {
		return dwAssetCodeInfoDao.findIdByTypeIdAndDm(typeId, dm);
	}

	@SuppressWarnings("unchecked")
	public Object load(String id, Class clazz) {
		return dwAssetCodeInfoDao.load(id, clazz);
	}

}
