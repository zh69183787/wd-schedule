/**
 * 
 */
package com.wonders.task.contractInfo.dao;

import java.util.List;

/** 
 * @ClassName: ContractInfoDao 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-5-12 上午8:43:31 
 *  
 */
public interface ContractInfoDao {
	public void saveBatch(List<?> c);
}
