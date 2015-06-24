package com.wonders.task.investCost.contract.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wonders.task.investCost.contract.dao.HtXxDao;
import com.wonders.task.investCost.contract.model.bo.HtXx;
import com.wonders.task.investCost.contract.service.HtXxService;

@Transactional(value = "txManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@Repository("htXxService")
public class HtXxServiceImpl implements HtXxService {
	
	private HtXxDao htXxDao;

	@Autowired(required=false)
	public void setHtXxDao(@Qualifier(value="htXxDao")HtXxDao htXxDao) {
		this.htXxDao = htXxDao;
	}

	@Override
	public List<HtXx> findByFlag(String flag) {
		
		return htXxDao.findByFlag(flag);
	}

	@Override
	public void updateAll(List<HtXx> htXxList) {
		htXxDao.updateAll(htXxList);
	}

	@Override
	public List<HtXx> findByTransferStatusAndFlag(String status,String flag) {
		return htXxDao.findByTransferStatusAndFlag(status,flag);
	}
	
	
}
	
