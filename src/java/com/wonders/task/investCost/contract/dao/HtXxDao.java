

package com.wonders.task.investCost.contract.dao;

import java.util.List;

import com.wonders.task.investCost.contract.model.bo.HtXx;


public interface HtXxDao {
	
	public List<HtXx> findByFlag(String flag);
	
	public void updateAll(List<HtXx> htXxList);
	
	public List<HtXx> findByTransferStatusAndFlag(String status,String flag);
}
	
