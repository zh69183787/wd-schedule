package com.wonders.task.investCost.contract.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.wonders.task.investCost.contract.dao.AttachDao_IC;
import com.wonders.task.investCost.contract.model.bo.Attach;
import com.wonders.task.investCost.contract.service.AttachService_IC;

@Repository("attachServiceImpl_IC")
public class AttachServiceImpl_IC implements AttachService_IC{

	private AttachDao_IC attachDao_IC;

	@Autowired(required=false)
	public void setAttachDao_IC(@Qualifier(value="ittachDaoImpl_IC")AttachDao_IC attachDao_IC) {
		this.attachDao_IC = attachDao_IC;
	}

	@Override
	public void saveAll(List<Attach> attachList) {
		attachDao_IC.saveAll(attachList);
	}

	@Override
	public void save(Attach attach) {
		attachDao_IC.save(attach);
	}
	
	
	
	
}
