package com.wonders.task.htxx.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.wonders.task.htxx.dao.ContractHtxxDao;
import com.wonders.task.htxx.dao.DwContractAssignStatsDao;
import com.wonders.task.htxx.dao.DwContractAssignTypeDao;
import com.wonders.task.htxx.model.bo.DwContractAssignStats;
import com.wonders.task.htxx.model.bo.DwContractAssignType;
import com.wonders.task.htxx.service.ContractHtxxService;
import com.wonders.task.investCost.contract.model.bo.Contract;

@Service("contractHtxxService")
public class ContractHtxxServiceImpl implements ContractHtxxService{
	int index = 0;
	//List<String[]> logList = new ArrayList<String[]>();
	private ContractHtxxDao contractHtxxDao;
	private DwContractAssignTypeDao dwContractAssignTypeDao;
	private DwContractAssignStatsDao dwContractAssignStatsDao;
	@Autowired(required=false)
	public void setContractHtxxDao(@Qualifier("contractHtxxDao")ContractHtxxDao contractHtxxDao) {
		this.contractHtxxDao = contractHtxxDao;
	}
	@Autowired
	public void setDwContractAssignTypeDao(
			@Qualifier("dwContractAssignTypeDao")DwContractAssignTypeDao dwContractAssignTypeDao) {
		this.dwContractAssignTypeDao = dwContractAssignTypeDao;
	}
	@Autowired(required=false)
	public void setDwContractAssignStatsDao(
			@Qualifier("dwContractAssignStatsDao")DwContractAssignStatsDao dwContractAssignStatsDao) {
		this.dwContractAssignStatsDao = dwContractAssignStatsDao;
	}
	@Override
	public String findEarliestYearOfContractSignedDate() {
		Contract contract = this.contractHtxxDao.findEarliestContract(); 
		return contract.getContractSignedDate();
	}

	@Override
	public void updateContractAssingTypeBySignedDateAndConctractType(String contractType,String assignType,String controlYear,String companyId) {
		try {
			if(assignType==null || "".equals(assignType) || controlYear==null || "".equals(controlYear) || contractType==null || "".equals(contractType)) return ;
			if(companyId==null || "".equals(companyId)){
				Map<String, String> resultMap = this.findSumOfContractBySignedDateAndContractTypeAndInviteBidType(contractType,assignType,controlYear,null);		//改年的总数据,不分公司
				DwContractAssignType dwContractAssignType = this.dwContractAssignTypeDao.findByControlYearAndContractTypeAndCompanyId(contractType,assignType,controlYear,null);
				dwContractAssignType = this.setDwContractAssignTypeValues(dwContractAssignType, contractType, assignType,controlYear, resultMap);
				dwContractAssignTypeDao.saveOrUpdate(dwContractAssignType);
			}else{
				Map<String, String> resultMap = this.findSumOfContractBySignedDateAndContractTypeAndInviteBidType(contractType,assignType,controlYear,companyId);		//该年的总数据,分公司
				DwContractAssignType dwContractAssignType = this.dwContractAssignTypeDao.findByControlYearAndContractTypeAndCompanyId(contractType,assignType,controlYear,companyId);
				dwContractAssignType = this.setDwContractAssignTypeValues(dwContractAssignType, contractType, assignType,controlYear, resultMap);
				dwContractAssignType.setControlCompanyId(companyId);
				dwContractAssignTypeDao.saveOrUpdate(dwContractAssignType);
			}
		} catch (Exception e) {
			System.out.println("updateContractAssingTypeBySignedDateAndConctractType方法错误");
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 根据年份、合同类型、采购方式查询合同数量
	 */
	public Map<String, String> findSumOfContractBySignedDateAndContractTypeAndInviteBidType(String contractType,String assignType,String controlYear,String companyId){
		List<Object[]> result = null;
		if("1".equals(assignType)){
			result = contractHtxxDao.findSumOfInviteBidBySignedDateAndContractType(contractType,controlYear,companyId);
		}else if("2".equals(assignType)){
			result = contractHtxxDao.findSumOfContractStatusBySignedDateAndContractType(contractType, controlYear, companyId);
		}
		
		Map<String, String> map = null;
		if(result==null || result.size()<1) return null;
		map = new HashMap<String, String>();
		for(int i=0; i<result.size(); i++){
			map.put(result.get(i)[0].toString(), result.get(i)[1].toString());
		}
		
		return map;
	}
	
	/**
	 * 设置DwContractAssignType的值
	 * @param dwContractAssignType
	 * @param contractType
	 * @param assignType
	 * @return
	 */
	public DwContractAssignType setDwContractAssignTypeValues(DwContractAssignType dwContractAssignType,String contractType,String assignType,String controlYear,Map<String, String> resultMap){
		if(dwContractAssignType==null) dwContractAssignType = new DwContractAssignType();
		dwContractAssignType.setCreateDate(new Date());
		dwContractAssignType.setContractType(new BigDecimal(contractType));
		dwContractAssignType.setAssignType(new BigDecimal(assignType));
		dwContractAssignType.setControlYear(controlYear);
		
		if("1".equals(assignType)){
			
			if(resultMap==null || resultMap.get("1")==null) dwContractAssignType.setExe1(new BigDecimal(0));	//公开招标
			else dwContractAssignType.setExe1(new BigDecimal(resultMap.get("1")));
			
			if(resultMap==null || resultMap.get("2")==null) dwContractAssignType.setExe2(new BigDecimal(0));	//采购平台
			else dwContractAssignType.setExe2(new BigDecimal(resultMap.get("2")));
			
			if(resultMap==null || resultMap.get("3")==null) dwContractAssignType.setExe3(new BigDecimal(0));	//直接委托
			else dwContractAssignType.setExe3(new BigDecimal(resultMap.get("3")));
		}else if("2".equals(assignType)){
			if(resultMap==null || resultMap.get("0")==null) dwContractAssignType.setExe1(new BigDecimal(0));	//备案
			else dwContractAssignType.setExe1(new BigDecimal(resultMap.get("0")));
			
			if(resultMap==null || resultMap.get("1")==null) dwContractAssignType.setExe2(new BigDecimal(0));	//实施
			else dwContractAssignType.setExe2(new BigDecimal(resultMap.get("1")));
			
			if(resultMap==null || resultMap.get("2")==null) dwContractAssignType.setExe3(new BigDecimal(0));	//竣工
			else dwContractAssignType.setExe3(new BigDecimal(resultMap.get("2")));
			
			if(resultMap==null || resultMap.get("3")==null) dwContractAssignType.setExe4(new BigDecimal(0));	//销号
			else dwContractAssignType.setExe4(new BigDecimal(resultMap.get("3")));
		}
		
		return dwContractAssignType;
	}
	
	@Override
	public void updateContractChangeOrPay(String contractType,String assignType, String controlYear,String companyId) {
		List<DwContractAssignStats> wholeYearList;
		
		if(assignType==null || "".equals(assignType) || controlYear==null || "".equals(controlYear) || contractType==null || "".equals(contractType)) return ;
		if("2".equals(assignType)){
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR, Integer.valueOf(controlYear));
			wholeYearList = new ArrayList<DwContractAssignStats>();
			int minMonth = calendar.getMinimum(Calendar.MONTH)+1,maxMonth = calendar.getMaximum(Calendar.MONTH)+1;
			
			//2.算每个月有多少个合同发生了变更,一个合同变更多次算一次
			while (minMonth-1!=maxMonth) {	//循环本年度的每个月,查询变更数据
				String yyyyMM = controlYear;
				if(minMonth<10) yyyyMM = controlYear+"-0"+minMonth;
				else yyyyMM = controlYear +"-"+minMonth;
	//System.out.println(yyyyMM+"月份 start****************");
				Object[] resultArray =null;
				resultArray = this.findSumOfContractStatus(contractType, assignType, yyyyMM, companyId);
				DwContractAssignStats dwContractAssignStats = null;
				if(resultArray==null || (Double.valueOf(resultArray[0].toString())==0 && resultArray[1]==null)){
					resultArray =null;
				}
				dwContractAssignStats = this.dwContractAssignStatsDao.findByContractTypeAndAssignTypeAndControlDate(contractType, assignType, yyyyMM, companyId);
				dwContractAssignStats = this.setDwContractAssignStats(dwContractAssignStats, contractType, assignType, yyyyMM, resultArray);
				//if(companyId!=null && !"".equals(companyId)){
				dwContractAssignStats.setControlCompanyId(companyId);
				//}
				wholeYearList.add(dwContractAssignStats);
				minMonth++;
			}
			//logList.size();
			this.dwContractAssignStatsDao.saveOrUpdateAll(wholeYearList);
		}else{
			//1.算本年度有多少个合同发生了变更
			DwContractAssignStats yearDwContractAssignStats = null;
			yearDwContractAssignStats = this.dwContractAssignStatsDao.findByContractTypeAndAssignTypeAndControlDate(contractType, assignType, controlYear, companyId);
			String sql1 ="";
			if(companyId!=null && !"".equals(companyId)){
				if(!"center".equals(companyId)){
					sql1 = "select c.id from c_contract c where c.removed='0' and c.contract_type like '"+contractType+",%' and c.contract_owner_execute_id ='"+companyId+"'"+
					"and c.id in (select distinct t.contract_id from c_contract_status t where t.removed = '0' and t.type='1' and t.operate_date like '"+controlYear+"%')";
				}else{
					sql1 = "select c.id from c_contract c where c.removed='0' and c.contract_type like '"+contractType+",%' and c.contract_owner_execute_id in ('2718','2719','2720','2721','2722','2545')"+
					"and c.id in (select distinct t.contract_id from c_contract_status t where t.removed = '0' and t.type='1' and t.operate_date like '"+controlYear+"%')";
				}
				
			}else{
				sql1 = "select c.id from c_contract c where c.removed='0' and c.contract_type like '"+contractType+",%'"+
				"and c.id in (select distinct t.contract_id from c_contract_status t where t.removed = '0' and t.type='1' and t.operate_date like '"+controlYear+"%')";
			}
			
			List<String> contractIdList = contractHtxxDao.findBySQL(sql1);
			
			String queryId = "";
			if(contractIdList!=null && contractIdList.size()>0){	//有合同发生变更
				for(int i=0; i<contractIdList.size(); i++){
					queryId += "'"+contractIdList.get(i)+"',";
				}
				queryId = queryId.substring(0,queryId.length()-1);
				String sql2 = "select sum(t.money) from c_contract_status t where t.contract_id in ("+queryId+") and t.removed = '0' and t.type='1' and t.operate_date like '"+controlYear+"%'";
				List<String> result = contractHtxxDao.findBySQL(sql2);
				if(result!=null && result.get(0)!=null && result.get(0)!=null){		//总金额有数据
					Object[] r = new Object[2];
					r[0] = result.get(0);
					r[1] = contractIdList.size();
					yearDwContractAssignStats = this.setDwContractAssignStats(yearDwContractAssignStats, contractType, assignType, controlYear, r);
				}else{
					yearDwContractAssignStats = this.setDwContractAssignStats(yearDwContractAssignStats, contractType, assignType, controlYear, null);
				}
			}else{
				yearDwContractAssignStats = this.setDwContractAssignStats(yearDwContractAssignStats, contractType, assignType, controlYear, null);
			}
			yearDwContractAssignStats.setControlCompanyId(companyId);
			
	System.out.println(controlYear+"年的年数据已算好,开始计算每个月发生的变更数");			
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR, Integer.valueOf(controlYear));
			wholeYearList = new ArrayList<DwContractAssignStats>();
			int minMonth = calendar.getMinimum(Calendar.MONTH)+1,maxMonth = calendar.getMaximum(Calendar.MONTH)+1;
			
			//2.算每个月有多少个合同发生了变更,一个合同变更多次算一次
			while (minMonth-1!=maxMonth) {	//循环本年度的每个月,查询变更数据
				
				String yyyyMM = controlYear;
				if(minMonth<10) yyyyMM = controlYear+"-0"+minMonth;
				else yyyyMM = controlYear +"-"+minMonth;
//				Object[] resultArray =null;
				DwContractAssignStats dwContractAssignStats = null;
				dwContractAssignStats = this.dwContractAssignStatsDao.findByContractTypeAndAssignTypeAndControlDate(contractType, assignType, yyyyMM, companyId);
				
				String sub_sql1 = "";
				if(companyId!=null && !"".equals(companyId)){
					if(!"center".equals(companyId)){
						sub_sql1 = "select c.id from c_contract c where c.removed='0' and c.contract_type like '"+contractType+",%' and c.contract_owner_execute_id ='"+companyId+"'"+
						"and c.id in (select distinct t.contract_id from c_contract_status t where t.removed = '0' and t.type='1' and t.operate_date like '"+yyyyMM+"%')";
					}else{
						sub_sql1 = "select c.id from c_contract c where c.removed='0' and c.contract_type like '"+contractType+",%' and c.contract_owner_execute_id in ('2718','2719','2720','2721','2722','2545')"+
						"and c.id in (select distinct t.contract_id from c_contract_status t where t.removed = '0' and t.type='1' and t.operate_date like '"+yyyyMM+"%')";
					}
					
				}else{
					sub_sql1 = "select c.id from c_contract c where c.removed='0' and c.contract_type like '"+contractType+",%'"+
					"and c.id in (select distinct t.contract_id from c_contract_status t where t.removed = '0' and t.type='1' and t.operate_date like '"+yyyyMM+"%')";
				}
				List<String> sub_contractIdList = contractHtxxDao.findBySQL(sub_sql1);
				String sub_queryId = "";
				if(sub_contractIdList!=null && sub_contractIdList.size()>0){	//有合同发生变更
					for(int i=0; i<sub_contractIdList.size(); i++){
						sub_queryId += "'"+sub_contractIdList.get(i)+"',";
					}
					sub_queryId = sub_queryId.substring(0,sub_queryId.length()-1);
					String sub_sql2 = "select sum(t.money) from c_contract_status t where t.contract_id in ("+sub_queryId+") and t.removed = '0' and t.type='1' and t.operate_date like '"+yyyyMM+"%'";
					List<String> result = contractHtxxDao.findBySQL(sub_sql2);
					if(result!=null && result.get(0)!=null && result.get(0)!=null){		//总金额有数据
						Object[] r = new Object[2];
						r[0] = result.get(0);
						r[1] = sub_contractIdList.size();
						dwContractAssignStats = this.setDwContractAssignStats(dwContractAssignStats, contractType, assignType, yyyyMM, r);
					}else{
						dwContractAssignStats = this.setDwContractAssignStats(dwContractAssignStats, contractType, assignType, yyyyMM, null);
					}
				}else{
					dwContractAssignStats = this.setDwContractAssignStats(dwContractAssignStats, contractType, assignType, yyyyMM, null);
				}
				dwContractAssignStats.setControlCompanyId(companyId);
				
				/*resultArray = this.findSumOfContractStatus(contractType, assignType, yyyyMM, companyId);
				DwContractAssignStats dwContractAssignStats = null;
				if(resultArray==null || (Double.valueOf(resultArray[0].toString())==0 && resultArray[1]==null)){
					resultArray =null;
				}
				dwContractAssignStats = this.dwContractAssignStatsDao.findByContractTypeAndAssignTypeAndControlDate(contractType, assignType, yyyyMM, companyId);
				dwContractAssignStats = this.setDwContractAssignStats(dwContractAssignStats, contractType, assignType, yyyyMM, resultArray);
				//if(companyId!=null && !"".equals(companyId)){
				dwContractAssignStats.setControlCompanyId(companyId);
				//}
*/				wholeYearList.add(dwContractAssignStats);
				minMonth++;
			}
			//logList.size();
			wholeYearList.add(yearDwContractAssignStats);
			this.dwContractAssignStatsDao.saveOrUpdateAll(wholeYearList);
		}
		
		
	}
	
	/**
	 * 查询合同状态表(记录了合同变更、合同计划支付、合同实际支付)
	 * @param contractType 	合同类型
	 * @param assignType	指定类型,1:合同变更，2：合同支付
	 * @param controlYear yyyy-MM 必须带月份
	 * @param companyId
	 * @return Map<String Object[]>  key:yyyyMM  value:0:变更合同数量,1:变更合同总价
	 */
	public Object[] findSumOfContractStatus(String contractType,String assignType,String controlYear,String companyId){
		List<Object[]> result = null;
		if("1".equals(assignType)){			//合同变更
			List<Object[]> list  = this.contractHtxxDao.findSumOfContractStatusByContractId(contractType, controlYear, companyId,"1");
			if(list!=null && list.size()>0){
				int num = 0;
				double moneySum = 0.0;
				List<String> contractIdList = new ArrayList<String>();
				for(int i=0; i<list.size();i++){
					if(list.get(i)[0]!=null && !"".equals(list.get(i)[0])){
						num++;		//变更数加1
						if(list.get(i)[2]!=null){
							contractIdList.add(list.get(i)[2].toString());
						}
						if(list.get(i)[1]!=null){
							moneySum += Double.valueOf(list.get(i)[1].toString());
						}
					}
				}
				if(num!=0){
					Object[] r = new Object[3];
					r[0] = moneySum;
					r[1] = num;
					r[2] = contractIdList;
					result = new ArrayList<Object[]>();
					result.add(r);
				}
			}
		}else if("2".equals(assignType)){	//计划支付，实际支付
			result = new ArrayList<Object[]>();
			Object[] objects = new Object[2];
			List<Object[]> result1 = this.contractHtxxDao.findSumOfContractStatus(contractType, controlYear, companyId,"2");		//计划支付
			List<Object[]> result2 = this.contractHtxxDao.findSumOfContractStatus(contractType, controlYear, companyId,"3");		//实际支付
			if(result1==null || "0".equals(result1.get(0)[0].toString())){
				objects[0] = 0;
			}else{
				objects[0] = result1.get(0)[1];
			}
			if(result2==null || "0".equals(result2.get(0)[0].toString())){
				objects[1] = 0;
			}else{
				objects[1] = result2.get(0)[1];
				if(!objects[1].toString().equals(0)){
					String[] logString = new String[4];
					logString[0]=controlYear;
					logString[1] = result2.get(0)[1].toString();
					//logList.add(logString);
					//System.out.println(controlYear+"年有数据===================================="+index++);					
				}
			}
			result.add(objects);
		}
		if(result==null || result.size()<1) return null;
		return result.get(0);
	}
	
	/**
	 * 
	 * @param dwContractAssignStats
	 * @param contractType
	 * @param assignType
	 * @param controlYear  必须是yyyyMM格式
	 * @param resultArray
	 * @return
	 */
	public DwContractAssignStats setDwContractAssignStats(DwContractAssignStats dwContractAssignStats,String contractType,String assignType,String controlYear,Object[] resultArray){
		if(dwContractAssignStats==null) dwContractAssignStats = new DwContractAssignStats();
		dwContractAssignStats.setCreateDate(new Date());
		dwContractAssignStats.setContractType(new BigDecimal(contractType));
		dwContractAssignStats.setAssignType(new BigDecimal(assignType));
		dwContractAssignStats.setControlDate(controlYear);
		if("1".equals(assignType)){
			dwContractAssignStats.setName1("变更金额");
			dwContractAssignStats.setName2("变更数量");
			if(resultArray==null){
				dwContractAssignStats.setValue1(new BigDecimal(0));
				dwContractAssignStats.setValue2(new BigDecimal(0));
			}else{
				dwContractAssignStats.setValue1(new BigDecimal(resultArray[0].toString()));
				dwContractAssignStats.setValue2(new BigDecimal(resultArray[1].toString()));
			}
		}else if("2".equals(assignType)){		//resultArray,0:计划支付，2：实际支付
			dwContractAssignStats.setName1("计划支付");
			dwContractAssignStats.setName2("实际支付");
			if(resultArray==null){
				dwContractAssignStats.setValue1(new BigDecimal(0));
				dwContractAssignStats.setValue2(new BigDecimal(0));
			}else{
				dwContractAssignStats.setValue1(new BigDecimal(resultArray[0].toString()));
				dwContractAssignStats.setValue2(new BigDecimal(resultArray[1].toString()));
			}
		}
		return dwContractAssignStats;
	}
	
	

	
}
