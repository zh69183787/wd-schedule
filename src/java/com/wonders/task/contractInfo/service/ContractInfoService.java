/**
 * 
 */
package com.wonders.task.contractInfo.service;

import java.util.List;
import java.util.Map;

/** 
 * @ClassName: ContractInfoService 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-5-12 上午8:43:53 
 *  
 */
public interface ContractInfoService {
	public void saveBatch(List<?> c);
	public Map<String,String> projectName();
	public Map<String,String> projectNo();
	public List<String> contractList();
	public Map<String,String> processStat(String date, String time, String stat);
	public Map<String,String> statusStat(String date, String time, String stat, String value, String type);
}
