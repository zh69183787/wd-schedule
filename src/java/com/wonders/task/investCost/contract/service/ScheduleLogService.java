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

import java.util.Date;



public interface ScheduleLogService {
	
	/**
	 * 查询上次执行的日期
	 * @return
	 */
	public Date findLastExecuteDate(String name,String process);
}
