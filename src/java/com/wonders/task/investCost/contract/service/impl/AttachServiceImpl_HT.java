package com.wonders.task.investCost.contract.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.wonders.task.investCost.contract.dao.AttachDao_HT;
import com.wonders.task.investCost.contract.model.bo.Attach;
import com.wonders.task.investCost.contract.service.AttachService_HT;

@Repository("attachServiceImpl_HT")
public class AttachServiceImpl_HT implements AttachService_HT{

	private AttachDao_HT attachDao_HT;

	
	@Autowired(required=false)
	public void setAttachDao_HT(@Qualifier(value="ittachDaoImpl_HT")AttachDao_HT attachDao_HT) {
		this.attachDao_HT = attachDao_HT;
	}


	@Override
	public List<Attach> findBYGroupId(String groupId) {
		
		return attachDao_HT.findByGroupId(groupId);
	}
	

}
