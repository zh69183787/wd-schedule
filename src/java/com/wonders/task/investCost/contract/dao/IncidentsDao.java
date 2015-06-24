
package com.wonders.task.investCost.contract.dao;

import java.util.Date;
import java.util.List;

import com.wonders.task.investCost.contract.model.bo.HtXx;



public interface IncidentsDao  {
	
	public List<HtXx> findIncidentsByDate(Date date);
	
}
