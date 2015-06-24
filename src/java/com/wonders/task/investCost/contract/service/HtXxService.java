
/** 
 * Copyright (c) 1995-2011 Wonders Information Co.,Ltd. 
 * 1518 Lianhang Rd,Shanghai 201112.P.R.C.
 * All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of WondersGroup.
 * You shall not disclose such Confidential Information and shall use it only in accordance 
 * with the terms of the license agreement you entered into with WondersGroup. 
 *
 */
 
package com.wonders.task.investCost.contract.service;

import java.util.List;

import com.wonders.task.investCost.contract.model.bo.HtXx;



 
public interface HtXxService
{
	public List<HtXx> findByFlag(String flag);
	
	public void updateAll(List<HtXx> htXxList);
	
	public List<HtXx> findByTransferStatusAndFlag(String status,String flag);
}
	
