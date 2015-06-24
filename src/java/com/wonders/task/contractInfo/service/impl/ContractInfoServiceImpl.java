/**
 * 
 */
package com.wonders.task.contractInfo.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.wonders.schedule.util.DbUtil;
import com.wonders.task.contractInfo.dao.ContractInfoDao;
import com.wonders.task.contractInfo.service.ContractInfoService;
import com.wonders.task.contractInfo.util.ContractInfoUtil;

/** 
 * @ClassName: ContractInfoServiceImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-5-12 上午8:43:45 
 *  
 */
@Service("contractInfoService")
public class ContractInfoServiceImpl implements ContractInfoService{
	private ContractInfoDao dao;

	public ContractInfoDao getDao() {
		return dao;
	}
	
	@Autowired(required=false)
	public void setDao(@Qualifier("contractInfoDao")ContractInfoDao dao) {
		this.dao = dao;
	}
	
	public void saveBatch(List<?> c){
		this.dao.saveBatch(c);
	}
	
	public List<String> contractList(){
		String sql = ContractInfoUtil.generateContractSql();
		List<String> list = DbUtil.getJdbcTemplate("").queryForList(sql, String.class);
		return list;
	}
	
	public Map<String,String> projectName(){
		String sql = ContractInfoUtil.generateProjectNameSql();
		List<Map<String,Object>> list = DbUtil.getJdbcTemplate("").queryForList(sql);
		Map<String,String> map = new HashMap<String,String>();
		if(list != null && list.size() > 0){
			for(Map<String,Object> m : list){
				map.put((String) m.get("contractId"), m.get("result")+"");
			}
		}
		//System.out.println("process"+map.size());
		return map;
	}
	
	public Map<String,String> projectNo(){
		String sql = ContractInfoUtil.generateProjectNoSql();
		List<Map<String,Object>> list = DbUtil.getJdbcTemplate("").queryForList(sql);
		Map<String,String> map = new HashMap<String,String>();
		if(list != null && list.size() > 0){
			for(Map<String,Object> m : list){
				map.put((String) m.get("contractId"), m.get("result")+"");
			}
		}
		//System.out.println("process"+map.size());
		return map;
	}
	
	public Map<String,String> processStat(String date, String time, String stat){
		String sql = ContractInfoUtil.generateProcessSql(date, time, stat);
		//System.out.println(sql);
		List<Map<String,Object>> list = DbUtil.getJdbcTemplate("").queryForList(sql);
		Map<String,String> map = new HashMap<String,String>();
		if(list != null && list.size() > 0){
			for(Map<String,Object> m : list){
				map.put((String) m.get("contractId"), m.get("result")+"");
			}
		}
		//System.out.println("process"+map.size());
		return map;
	}
	
	public Map<String,String> statusStat(String date, String time, String stat, String value, String type){
		String sql = ContractInfoUtil.generateStatusSql(date, time, stat, value, type);
		//System.out.println(sql);
		List<Map<String,Object>> list = DbUtil.getJdbcTemplate("").queryForList(sql);
		Map<String,String> map = new HashMap<String,String>();
		if(list != null && list.size() > 0){
			for(Map<String,Object> m : list){
				map.put((String)m.get("contractId"), m.get("result")+"");
			}
		}
		//System.out.println("status"+map.size());
		return map;
	}
	
}
