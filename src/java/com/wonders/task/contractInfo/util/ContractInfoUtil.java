/**
 * 
 */
package com.wonders.task.contractInfo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/** 
 * @ClassName: ContractInfoUtil 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-5-12 上午8:44:32 
 *  
 */
public class ContractInfoUtil {
	private static String contractSql = "";
	private static String processSql = "";
	private static String statusSql = "";
	private static String projectSql = "";
	
	public static String generateContractSql(){
		contractSql = "select c.id " +
				" from c_contract c where c.removed = 0" +
				" and c.contract_type in ('1,1','1,2','1,3','1,4','1,5','1,6','1,7','1,8','2,1','2,2','2,3')";
		return contractSql;
	}
	
	//
	public static String generateProjectNameSql(){
		projectSql = "select c.id contractId ,c.project_name result " +
				" from c_contract c where c.removed = 0" +
				" and c.contract_type in ('1,1','1,2','1,3','1,4','1,5','1,6','1,7','1,8','2,1','2,2','2,3')";
		return projectSql;
	}
	
	public static String generateProjectNoSql(){
		projectSql = "select c.id contractId ,c.project_no result " +
				" from c_contract c where c.removed = 0" +
				" and c.contract_type in ('1,1','1,2','1,3','1,4','1,5','1,6','1,7','1,8','2,1','2,2','2,3')";
		return projectSql;
	}
	
	//time 1 month 2 year 3 total
	//stat count sum
	public static String generateProcessSql(String statDate,String time,String stat){	
		String first,last = null;
		Map<String,String> d = null;
		if("1".equals(time) || "2".equals(time)){
			if("1".equals(time)){
				d = getFirstday_Lastday_Month(statDate);	
			}else if("2".equals(time)){
				d = getFirstday_Lastday_Year(statDate);	
			}
			first = d.get("first");
			last = d.get("last");
			processSql = "select t.object_id contractId, "+stat+"(id) result" +
					" from c_progress t where to_date(t.time,'yyyy-mm-dd hh24:mi:ss')" +
					" between to_date('"+first+"','yyyy-mm-dd hh24:mi:ss') " +
					" and to_date('"+last+"','yyyy-mm-dd hh24:mi:ss') and t.removed =0 " +
					" and t.time is not null "+
					" group by t.object_id";
		}else{
			processSql = "select t.object_id contractId, "+stat+"(id) result" +
					" from c_progress t where t.removed =0  " +
					//" and t.time is not null "+
					" group by t.object_id";
		}
		return processSql;
	}
	
	//time 1 month 2 year 3 total
	//type 1 变更 2计划支付 3 实际支付
	//value money  persent  id(用于计算变更次数)
	public static String generateStatusSql(String statDate,String time, String stat, String value, String type){
		String first,last = null;
		Map<String,String> d = null;
		if("1".equals(time) || "2".equals(time)){
			if("1".equals(time)){
				d = getFirstday_Lastday_Month(statDate);	
			}else if("2".equals(time)){
				d = getFirstday_Lastday_Year(statDate);	
			}
			first = d.get("first");
			last = d.get("last");
			statusSql = "select "+stat+"(t."+value+") result ,t.contract_id contractId from c_contract_status t " +
					" where to_date(t.operate_date,'yyyy-mm-dd hh24:mi:ss')" +
					" between to_date('"+first+"','yyyy-mm-dd hh24:mi:ss')" +
					" and to_date('"+last+"','yyyy-mm-dd hh24:mi:ss') " +
					" and t.removed=0 and t.type = " +type +
					" and t.operate_date is not null "+
					" and regexp_like (t."+value+",'^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0|[1-9]\\d*)$') "+
					" group by t.contract_id";
		}else{
			statusSql = "select "+stat+"(t."+value+") result,t.contract_id contractId from c_contract_status t " +
					" where t.removed=0 and t.type = "+type +
					//" and t.operate_date is not null "+
					" and regexp_like (t."+value+",'^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0|[1-9]\\d*)$') "+
					" group by t.contract_id";
			
		}
	
		
		return statusSql;
	}

	
	/**
     * 某一个月第一天和最后一天
     * 
     * @param date
     * @return
     */
    private static Map<String, String> getFirstday_Lastday_Month(String statDate) {	
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(statDate);
		} catch (ParseException e) {
			date = new Date();
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        String day_first = df.format(calendar.getTime());
        StringBuffer str = new StringBuffer().append(day_first).append(
                " 00:00:00");
        day_first = str.toString();
 
        calendar.clear();
        calendar.setTime(date);
        // 上个月最后一天
        calendar.add(Calendar.MONTH, 1); // 加一个月
        calendar.set(Calendar.DATE, 1); // 设置为该月第一天
        calendar.add(Calendar.DATE, -1); // 再减一天即为上个月最后一天
        String day_last = df.format(calendar.getTime());
        StringBuffer endStr = new StringBuffer().append(day_last).append(
                " 23:59:59");
        day_last = endStr.toString();
 
        Map<String, String> map = new HashMap<String, String>();
        map.put("first", day_first);
        map.put("last", day_last);
        return map;
    }
    
    private static Map<String,String> getFirstday_Lastday_Year(String statDate){
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(statDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			date = new Date();
		}

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        Map<String, String> map = new HashMap<String, String>();
        map.put("first", year+"-01-01 00:00:00");
        map.put("last", year+"-12-31 23:59:59");
        return map;
    }
    
    public static void main(String[] args){
    	String date = "2013-09-02";
    	Map<String,String> map1 = getFirstday_Lastday_Month(date);
    	Map<String,String> map2 = getFirstday_Lastday_Year(date);
    	System.out.println(map1.get("first"));
    	System.out.println(map1.get("last"));
    	System.out.println(map2.get("first"));
    	System.out.println(map2.get("last"));
    	
    }
}

