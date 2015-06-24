package com.wonders.task.investCost.contract.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.wonders.task.investCost.contract.dao.HtBaDao;
import com.wonders.task.investCost.contract.model.bo.HtBa;
import com.wonders.task.investCost.contract.service.HtBaService;

@Repository("htBaService")
public class HtBaServiceImpl implements HtBaService{

	private HtBaDao htBaDao;
	
	@Autowired(required=false)
	public void setHtBaDao(@Qualifier(value="htBaDao")HtBaDao htBaDao) {
		this.htBaDao = htBaDao;
	}

	@Override
	public HtBa findByHtXxId(Long htXxId) {
		return htBaDao.findByHtXxId(htXxId);
	}

	
}
