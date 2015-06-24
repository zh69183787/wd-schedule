
package com.wonders.task.investCost.contract.service;

import java.util.Date;
import java.util.List;

import com.wonders.task.investCost.contract.model.bo.HtXx;


public interface IncidentsService {
	
	public List<HtXx> findIncidentsByDate(Date date);
	
}
