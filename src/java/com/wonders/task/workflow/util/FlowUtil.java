package com.wonders.task.workflow.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.wonders.schedule.util.DbUtil;



/**流程操作相关方法
 * @author zhoushun
 * @version 1.0 2013-3-2
 */
@Component("flowUtil")
public class FlowUtil {
	
	
	/** 
	* @Title: getNodeUsersByConfig 
	* @Description: 根据相关属性得到配置表中节点处理人
	* @param @param processName
	* @param @param incidentNo
	* @param @param stepName
	* @param @param typeId
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	*/
	public static Map<String,Object> getNodeUsersByConfig(String processName, String stepName,String typeId){
		Map<String,Object> map = new HashMap<String,Object>();
		String sql = "select 'ST/'||username username,userfullname,parent_dept from t_flowsetp_config where processname=?" +
				"and stepname = ? and typeid = ?";
		Object[] params = new Object[]{processName,stepName,typeId};
		
		List<Map<String,Object>> list = DbUtil.getJdbcTemplate("stptinc").queryForList(sql, params);
		if(list.size()>0){
			map = list.get(0);
			return map;
		}
		return map;
	}
	
	public static String getLoginNameLink(Object o,String deptId){
		String result = "";
		if(o instanceof List){
			for(String temp : (List<String>)o){
				result += temp + ":" + deptId +"<+>";
			}
		}else{
			result += o.toString() + ":" +deptId +"<+>";
		}
		
		return result;
	}
	

	public static List<String> getUltimusListInfo(List<String> src){
		//List<String> src = (List<String>) obj;
		List<String> target = new ArrayList<String>();
		if(src != null && src.size()>0){
			target.addAll(src);
			if(target.size() < 15){
				for(int i = 0 ; i<(15-target.size());i++){
					target.add("");
				}
			}
		}else{
			for(int i = 0; i< 15; i++){
				target.add("");
			}
		}
		return target;
	}
	
	public static List<String> getUltimusDeptListInfo(List<String> src){
		//List<String> src = (List<String>) obj;
		List<String> target = new ArrayList<String>();
		if(src != null && src.size()>0){
			target.addAll(src);
		}
		return target;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void putUltimusMap(String code ,List<String> list,Map map){
		List<String> newList = getUltimusListInfo(list);
		map.put(code , newList);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void putUltimusDeptMap(String code ,List<String> list,Map map){
		if(list != null && list.size() > 0){
			List<String> newList = getUltimusDeptListInfo(list);
			map.put(code , newList);
		}else{
			
		}
	}
	
	
	/**
	 * 按给定条件在流程步骤配置表中查找部门ids信息
	 * @author liu_feng
	 * @param processName - 流程名称
	 * @param stepName - 步骤名称
	 * @param typeId - 业务分类ID
	 * @param loginName - 登录名

	 * @return 部门ids - deptId1,deptId2,...deptIdn
	 * @throws HibernateException
	 */
	public static String findDeptIdsFromFC(String processName, String stepName, String typeId, String loginName ) {
		String deptId = "";
		String sql = "select distinct fc.dept" +
			" from t_flowsetp_config fc" +
			" where fc.processname = ? and fc.stepname = ?" +
			" and fc.typeid = ? and and fc.username = ?";		
		Object[] params = new Object[]{processName,stepName,typeId,loginName};		
		List<String> list = DbUtil.getJdbcTemplate("stptinc").queryForList(sql, String.class, params);
		if(list !=null && list.size() > 0){
			deptId = list.get(0);
		}
		return deptId;	
	}
	

	//判断部门为 单位 或者 部门
	public static boolean judgeDeptInfo(String deptId){
		//在流程步骤配置表中查找部门ids
		String deps = findDeptIdsFromFC("new_processes", "new_dept", "0", "ADMIN");
		if(deptId!=null&&deps.indexOf(deptId)>-1){
			return true;
		}
		return false;
	}
	
	
}
