/**
 * 
 */
package com.wonders.task.build.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.wonders.schedule.util.StringUtil;
import com.wonders.task.build.model.vo.BuildPlanVo;
import com.wonders.task.build.model.bo.BuildProjectInfo;
import com.wonders.task.build.model.bo.BuildProjectInfoProcess;

/** 
 * @ClassName: BuildUtil 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-10-29 上午11:04:17 
 *  
 */
public class BuildStatusUtil {
	public static String today = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
	
	/**
	 * time1 计划开工
	 * time2 计划完成
	 * time3 实际开工
	 * time4 实际完成
	 **/
	public static void convert(BuildPlanVo vo,BuildProjectInfo bo){
		BeanUtils.copyProperties(vo, bo);
		String time1 = StringUtil.getNotNullValueString(vo.getPbtime());
		String time2 = StringUtil.getNotNullValueString(vo.getPftime());
		String time3 = StringUtil.getNotNullValueString(vo.getRbtime());
		String time4 = StringUtil.getNotNullValueString(vo.getRftime());
		boolean delayFlag = true;
		bo.setPlanTotal(bo.getPlanTotal()+1);
		if("1".equals(vo.getMilestone())){
			bo.setMilestoneTotal(bo.getMilestoneTotal()+1);
		}
		//已延误任务
		if(("".equals(time3) && today.compareTo(time1) > 0) ||
				(!"".equals(time3) && time3.compareTo(time1) > 0)	){
			bo.setAbnormalTotal(bo.getAbnormalTotal()+1);
			bo.setBeginDelayTotal(bo.getBeginDelayTotal()+1);
			if("1".equals(vo.getMilestone())){
				bo.setMilestoneDelayTotal(bo.getMilestoneDelayTotal()+1);
			}
			delayFlag = false;
		}
		
		if(("".equals(time4) && today.compareTo(time2) > 0) ||
				(!"".equals(time4) && time4.compareTo(time2) > 0)	){
			if(delayFlag){
				bo.setAbnormalTotal(bo.getAbnormalTotal()+1);
			}
			bo.setFinishDelayTotal(bo.getFinishDelayTotal()+1);
			if(delayFlag && "1".equals(vo.getMilestone())){
				bo.setMilestoneDelayTotal(bo.getMilestoneDelayTotal()+1);
			}
		}

		//待开工任务
		if(today.compareTo(time1) < 0){
			bo.setWaitingTotal(bo.getWaitingTotal()+1);
			bo.setNormalTotal(bo.getNormalTotal()+1);
		}
		
		//正常实施任务
		else if(!"".equals(time3) && time3.compareTo(time1) <=0 &&
				 "".equals(time4) && today.compareTo(time2) < 0){
			bo.setDoingTotal(bo.getDoingTotal()+1);
			bo.setNormalTotal(bo.getNormalTotal()+1);
		}
		
		//正常完成任务
		else if(!"".equals(time3)&&!"".equals(time4) 
				&& time3.compareTo(time1)<=0 && time4.compareTo(time2)<=0){
			bo.setDoneTotal(bo.getDoingTotal()+1);
			bo.setNormalTotal(bo.getNormalTotal()+1);
		}
			
	}
	
	
	/**
	 * time1 计划开工
	 * time2 计划完成
	 * time3 实际开工
	 * time4 实际完成
	 * type 0 计划开始 1 计划完成2实际开始3实际完成
	 **/
	public static void convert(BuildPlanVo vo,BuildProjectInfoProcess bo,String type){
		BeanUtils.copyProperties(vo, bo);
		String time1 = StringUtil.getNotNullValueString(vo.getPbtime());
		String time2 = StringUtil.getNotNullValueString(vo.getPftime());
		String time3 = StringUtil.getNotNullValueString(vo.getRbtime());
		String time4 = StringUtil.getNotNullValueString(vo.getRftime());
		
		if("1".equals(type)){
			bo.setPlanYear(getYear(time1));bo.setPlanMonth(getMonth(time1));
			bo.setBeginPlan(bo.getBeginPlan()+1);
			if(("".equals(time3) && today.compareTo(time3)>0) ||
					(!"".equals(time3)&&time3.compareTo(time1)>0)){
				bo.setBeginDelay(bo.getBeginDelay()+1);
			}
		}else if("2".equals(type)){
			bo.setPlanYear(getYear(time2));bo.setPlanMonth(getMonth(time2));
			bo.setFinishPlan(bo.getFinishPlan()+1);
			if(("".equals(time4) && today.compareTo(time4)>0) ||
					(!"".equals(time4)&&time4.compareTo(time2)>0)){
				bo.setFinishDelay(bo.getFinishDelay()+1);
			}
		}else if("3".equals(type)){
			bo.setPlanYear(getYear(time3));bo.setPlanMonth(getMonth(time3));
			bo.setBeginReal(bo.getBeginReal()+1);
		}else if("4".equals(type)){
			bo.setPlanYear(getYear(time4));bo.setPlanMonth(getMonth(time4));
			bo.setFinishReal(bo.getFinishReal()+1);
		}
			
	}
	
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List statBuildStatus(List<BuildPlanVo> voList){
		List list = new ArrayList();
		Map<String,BuildProjectInfo> map = new HashMap<String,BuildProjectInfo>();	
		Map<String,BuildProjectInfoProcess> processMap = new HashMap<String,BuildProjectInfoProcess>();	
		String key = "";
		String processKey1 = "";
		String processKey2 = "";
		String processKey3 = "";
		String processKey4 = "";
		for(BuildPlanVo vo : voList){
		
			key = vo.getProjectId()+","+vo.getCompanyId()+
					","+vo.getPlanId()+","+vo.getSingleId()+","+vo.getMilestone();
			if(map.containsKey(key)){
				convert(vo,map.get(key));
			}else{
				BuildProjectInfo bo = new BuildProjectInfo();
				convert(vo,bo);
				map.put(key, bo);
			}
			
			
			if(!"".equals(StringUtil.getNotNullValueString(vo.getPbtime()))){
				processKey1 = getYear(vo.getPbtime())+","+getMonth(vo.getPbtime())
						+","+vo.getProjectId()+","+vo.getCompanyId()+
						","+vo.getPlanId()+","+vo.getSingleId()+","+vo.getMilestone();
				//月度 计划1
				if(processMap.containsKey(processKey1)){
					convert(vo,processMap.get(processKey1),"1");
				}else{
					BuildProjectInfoProcess processBo = new BuildProjectInfoProcess();
					convert(vo,processBo,"1");
					processMap.put(processKey1, processBo);
				}
			}

			if(!"".equals(StringUtil.getNotNullValueString(vo.getPftime()))){
				processKey2 = getYear(vo.getPftime())+","+getMonth(vo.getPftime())
						+","+vo.getProjectId()+","+vo.getCompanyId()+
						","+vo.getPlanId()+","+vo.getSingleId()+","+vo.getMilestone();
				//月度 计划1
				if(processMap.containsKey(processKey2)){
					convert(vo,processMap.get(processKey2),"2");
				}else{
					BuildProjectInfoProcess processBo = new BuildProjectInfoProcess();
					convert(vo,processBo,"2");
					processMap.put(processKey2, processBo);
				}
			}

			if(!"".equals(StringUtil.getNotNullValueString(vo.getRbtime()))){
				processKey3 = getYear(vo.getRbtime())+","+getMonth(vo.getRbtime())
						+","+vo.getProjectId()+","+vo.getCompanyId()+
						","+vo.getPlanId()+","+vo.getSingleId()+","+vo.getMilestone();
				//月度 计划1
				if(processMap.containsKey(processKey3)){
					convert(vo,processMap.get(processKey3),"3");
				}else{
					BuildProjectInfoProcess processBo = new BuildProjectInfoProcess();
					convert(vo,processBo,"3");
					processMap.put(processKey3, processBo);
				}
			}
			if(!"".equals(StringUtil.getNotNullValueString(vo.getRftime()))){
				processKey4 = getYear(vo.getRftime())+","+getMonth(vo.getRftime())
						+","+vo.getProjectId()+","+vo.getCompanyId()+
						","+vo.getPlanId()+","+vo.getSingleId()+","+vo.getMilestone();
				//月度 计划1
				if(processMap.containsKey(processKey4)){
					convert(vo,processMap.get(processKey4),"4");
				}else{
					BuildProjectInfoProcess processBo = new BuildProjectInfoProcess();
					convert(vo,processBo,"4");
					processMap.put(processKey4, processBo);
				}
			}
			
		}
		
		for(Map.Entry<String, BuildProjectInfo> entry : map.entrySet()){
			list.add(entry.getValue());
		}
		for(Map.Entry<String, BuildProjectInfoProcess> entry : processMap.entrySet()){
			list.add(entry.getValue());
		}
		
		
		return list;
	}
	
	
	public static String getYear(String time1){
		String year = "";
		year = time1.substring(0,4);
		
		
		return year;
	}
	
	public static String getMonth(String time1){
		String month = "";
		
		month = time1.substring(5,7);
		
		return month;
	}

	
	public static String getFormatDate(String d){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(d);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			date = new Date();
		}
		return sdf.format(date);
	}
}
