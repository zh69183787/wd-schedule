/**
 * 
 */
package com.wonders.task.audit.dbx.execute;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.wonders.schedule.common.util.SimpleLogger;
import com.wonders.task.audit.dbx.service.DbxService;
import com.wonders.task.audit.model.bo.CaAuditInfo;
import com.wonders.task.audit.service.CaAuditInfoService;
import com.wonders.task.audit.tools.Common;
import com.wonders.task.sample.ITaskService;

/**
 * @ClassName: SampleService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author zhoushun
 * @date 2012-12-4 下午03:28:48
 * 
 */
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
/* 功能模块入口点，beanid即数据库配置中的name */
@Service("dbxExecute")
@Scope("prototype")
public class DbxExecute implements ITaskService {
	static SimpleLogger log = new SimpleLogger(DbxExecute.class);
	private DbxService dbxService;
	private CaAuditInfoService caAuditInfoService;
	private Common comm = new Common();

	public DbxService getDbxService() {
		return dbxService;
	}

	@Autowired(required = false)
	public void setDbxService(@Qualifier(value = "dbxService") DbxService dbxService) {
		this.dbxService = dbxService;
	}


	public CaAuditInfoService getCaAuditInfoService() {
		return caAuditInfoService;
	}

	@Autowired(required = false)
	public void setCaAuditInfoService(@Qualifier(value = "caAuditInfoService") CaAuditInfoService caAuditInfoService) {
		this.caAuditInfoService = caAuditInfoService;
	}

	/**
	 * @Title: exec
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param 设定文件
	 * @throws
	 */

	public String exec(String param) {
		Integer[] dbx = new Integer[3];
		List<CaAuditInfo> listModel = new ArrayList<CaAuditInfo>();
		int urgeCount = 0;
		long countMessage = 0l;
		int countJbx = 0;
		int count = 0;
		int count1 = 0;
		int count2 = 0;
		int count3 = 0;
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String updateTime=df.format(date);
		try {
			List<Object[]> list = caAuditInfoService.findActiveUser();
			System.out.println("共活跃用户"+list.size()+"个");
			List<Object[]> listDept = new ArrayList<Object[]>();
			String allDeptId = "";
			String[] allDeptIdSplit;
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					count1++;
					//Thread.sleep(5*1000);
					listDept = caAuditInfoService.findAllDept(String.valueOf(list.get(i)));
					if(listDept!=null&&listDept.size()>0){
						count2++;
						for(int j=0;j<listDept.size();j++){
							if(j>0){
								allDeptId += ",";
							}
							allDeptId += String.valueOf(listDept.get(j));
						}
						allDeptIdSplit = allDeptId.split(",");
						for(int j=0;j<allDeptIdSplit.length;j++){
							dbx = getJrswDbx(allDeptIdSplit[j],allDeptId,String.valueOf(list.get(i)));
							urgeCount = getUrgeCount(allDeptIdSplit[j],allDeptId,String.valueOf(list.get(i)));
							countMessage = countMessage(String.valueOf(list.get(i)));
							countJbx = countJbx(allDeptIdSplit[j],String.valueOf(list.get(i)));
							if(dbx!=null){
								count3++;
								CaAuditInfo caAuditInfo = new CaAuditInfo();
								String id = caAuditInfoService.findIdByNameAndDept(String.valueOf(list.get(i)), allDeptIdSplit[j]);
								if(id==null){
									caAuditInfo.setLoginName(String.valueOf(list.get(i)));
									caAuditInfo.setDeptId(allDeptIdSplit[j]);
									caAuditInfo.setExt1(String.valueOf(dbx[0]));
									caAuditInfo.setExt2(String.valueOf(dbx[1]));
									caAuditInfo.setExt3(String.valueOf(dbx[2]));
									caAuditInfo.setExt4(String.valueOf(urgeCount));
									caAuditInfo.setExt5(String.valueOf(countMessage));
									caAuditInfo.setExt6(String.valueOf(countJbx));
									caAuditInfo.setUpdateTime(updateTime);
									caAuditInfo.setRemoved("0");
									caAuditInfo.setPlatform("0");
									listModel.add(caAuditInfo);
									//caAuditInfoService.save(caAuditInfo);
									//System.out.println("num1=="+result[0]+"&num2=="+result[1]+"&num3=="+result[2]);
								}else{
									caAuditInfo = (CaAuditInfo)caAuditInfoService.load(id, caAuditInfo.getClass());
									caAuditInfo.setExt1(String.valueOf(dbx[0]));
									caAuditInfo.setExt2(String.valueOf(dbx[1]));
									caAuditInfo.setExt3(String.valueOf(dbx[2]));
									caAuditInfo.setExt4(String.valueOf(urgeCount));
									caAuditInfo.setExt5(String.valueOf(countMessage));
									caAuditInfo.setExt6(String.valueOf(countJbx));
									caAuditInfo.setUpdateTime(updateTime);
									listModel.add(caAuditInfo);
									//caAuditInfoService.update(caAuditInfo);
									//System.out.println("update=======");
								}
								count++;
							}
						}
						allDeptId = "";
					}	
				}
				caAuditInfoService.saveOrUpdateAll(listModel);
			}
			System.out.println("本次共更新数据"+count+"条");
			System.out.println("其中"+count1+"条进入循环");
			System.out.println("其中"+count2+"条进入第一个if");
			System.out.println("其中"+count3+"条进入第二个if");
			log.debug("nativeService run+++++++++++++++++++++");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	@SuppressWarnings("unused")
	public Integer[] getJrswDbx(String deptId,String allDeptId,String tLoginName){
		Integer[] result = new Integer[3];
//		String deptId = "2116";
//	    String allDeptId = "2116,10651";
//	    String tLoginName = "G00100000161";
	    if(deptId!=null&&allDeptId!=null){
	    	String[] allDeptIdSplit = allDeptId.split(",");
		    List<String> deptIds = new ArrayList<String>();//拿到所有的部门id
		    for(int i=0;i<allDeptIdSplit.length;i++){
		    	deptIds.add(allDeptIdSplit[i]);
		    }
			Map<String,String> mapDept = new HashMap<String,String>();
			Map<String,Integer> map = findTasksingByLoginName(tLoginName, deptIds, deptId);			
			//曾静 2010-8-6 start
			String countTimeOut = dbxService.countTimeOut(tLoginName);//统计超时流程的最大时间,没有则为0;
			//end
			if(countTimeOut==null){
				countTimeOut = "0";
			}
			Integer num = null;
			Integer num1 = null;
			Integer num2 = null;
			num = map.get(deptId);
			//num1 = map.get(deptId+"急件");
			num2 = map.get(deptId+"超时");
			
			if (num!= null) {
				result[0] = num;
				result[1] = num2;
				result[2] = Integer.parseInt(countTimeOut);
				return result;
			}else{
				return null;
			}
	    }else{
	    	return null;
	    }
	}
	
	public Map<String,Integer> findTasksingByLoginName(String loginName,List<String> deptIds,String mainDeptId){
		if(comm.isNull(loginName)||deptIds == null || deptIds.isEmpty()) return null;
		
		Map<String,Integer> map = null;
		
		List<String[]> listTasks = dbxService.findTasksingByLoginName(loginName);
		//初始化

		map = new HashMap<String,Integer>();
		for(String strTemp : deptIds){
			map.put(strTemp, 0);
			map.put(strTemp+"急件", 0);//急件数量
			map.put(strTemp+"超时", 0);//超时数量
		}
		
		
		//没有代办项

		if(listTasks == null || listTasks.isEmpty()) return map;
		
		if(deptIds.size() == 1){
			map.put(deptIds.get(0), listTasks.size());
			//if()
			int mapValue = 0;
			//曾静 2010-08-05 start
			int mapValues = 0;//初始化超时的累计
			//end
			for(String[] listTasksArray:listTasks){
				if(!comm.isNull(listTasksArray[3])){//设置急件
					mapValue = map.get(deptIds.get(0)+"急件");
					mapValue ++;					
					map.put(deptIds.get(0)+"急件", mapValue);					
				}
				//曾静 2010-08-05 start
				if(!comm.isNull(listTasksArray[4])&&"3".equals(listTasksArray[4])&&!comm.isNull(listTasksArray[5])){//设置超时				
					mapValues = map.get(deptIds.get(0)+"超时");
					mapValues ++;//超时的累计

					map.put(deptIds.get(0)+"超时", mapValues);//超时的累计赋到map中

				}
				//end
			}
			return map;
		}
		//有代办项
		//查找登陆人所在的部门的等级

		Map<Long,Integer> deptLevelMap = dbxService.getDeptsLevel(deptIds);
		Set<Long> deptLevelMapKey = deptLevelMap.keySet();
		Integer deptLevelMapValue = null;
		if(!comm.isNull(mainDeptId)){
			for(Long l :deptLevelMapKey){
				for(String[] listTasksArray:listTasks)
					setMapTasks((mainDeptId.equals(l+"")),l,map,loginName,listTasksArray[2],listTasksArray[3],listTasksArray[4],listTasksArray[5]);
			}
		}else{
			//如果没有主部门

			for(Long l :deptLevelMapKey){
				deptLevelMapValue = deptLevelMap.get(l);
				if(deptLevelMapValue != null||deptLevelMapValue != 0){
					for(String[] listTasksArray:listTasks)
						setMapTasks((deptLevelMapValue == 1),l,map,loginName,listTasksArray[2],listTasksArray[3],listTasksArray[4],listTasksArray[5]);
				}
			}
		}
		return map;
	}
	
	private void setMapTasks(boolean flag,Long l,Map<String,Integer> map,String loginName,String helpUrl,String properties,String substatus,String overduetime){
		Integer mapValue = null;
		Integer mapValues = null;
		if(flag){
			if(comm.isNull(helpUrl)
					||helpUrl.indexOf("ST/"+loginName+":"+l+"<+>")>=0
					||helpUrl.indexOf("ST/"+loginName+":<+>")>=0){
				mapValue = map.get(l+"");
				mapValue ++;
				map.put(l+"", mapValue);
				
				if(!comm.isNull(properties)){//设置急件
					mapValue = map.get(l+"急件");
					mapValue ++;
					map.put(l+"急件", mapValue);
				}
				if(!comm.isNull(substatus)&&"3".equals(substatus)&&!comm.isNull(overduetime)){//设置超时
					mapValues = map.get(l+"超时");
					mapValues ++;
					map.put(l+"超时", mapValues);
				}
			}
		}else{
			if(comm.isNull(helpUrl)||helpUrl.indexOf("ST/"+loginName+":"+l+"<+>")>=0){
				mapValue = map.get(l+"");
				mapValue ++;
				map.put(l+"", mapValue);
				
				if(!comm.isNull(properties)){//设置急件
					mapValue = map.get(l+"急件");
					mapValue ++;
					map.put(l+"急件", mapValue);
				}
				
				if(!comm.isNull(substatus)&&"3".equals(substatus)&&!comm.isNull(overduetime)){//设置超时
					mapValues = map.get(l+"超时");
					mapValues ++;
					map.put(l+"超时", mapValues);
				}
			}
		}
	}
	
	public int getUrgeCount(String deptId,String allDeptId,String tLoginName){
		int num =0;
		if(deptId!=null&&allDeptId!=null){
	    	String[] allDeptIdSplit = allDeptId.split(",");
		    List<String> deptIds = new ArrayList<String>();//拿到所有的部门id
		    for(int i=0;i<allDeptIdSplit.length;i++){
		    	deptIds.add(allDeptIdSplit[i]);
		    }
		    boolean multiDept = false;
		    if(allDeptIdSplit!=null && allDeptIdSplit.length>1){
				multiDept = true;
			}
		     num = dbxService.getUrgeCount(tLoginName, deptId, multiDept, deptIds);
	    }
		return num;
	}
	
	public long countMessage(String tLoginName){
		long num = dbxService.countMessage(tLoginName);
		return num;
	}
	
	public int countJbx(String deptId,String tLoginName){
		int num = 0;
		if(deptId!=null){
			num = dbxService.countJbx(tLoginName, deptId);
		}
		return num;
	}
}
