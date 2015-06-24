/**
 * 
 */
package com.wonders.task.build.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import com.wonders.schedule.util.StringUtil;
import com.wonders.task.build.model.bo.BuildProjectInfo;
import com.wonders.task.build.model.bo.DwBuildPlantaskMonth;
import com.wonders.task.build.model.bo.DwBuildPlantaskYear;
import com.wonders.task.build.model.vo.BuildPlanMonthVo;
import com.wonders.task.build.model.vo.BuildPlanTotalVo;
import com.wonders.task.build.model.vo.BuildPlanYearVo;
/** 
 * @ClassName: BuildTaskUtil 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013年11月27日 上午10:58:55 
 *  
 */
public class BuildTaskUtil {
	
	public static String add(String s1,String s2){
		Double d1 , d2 = null;
		if("".equals(StringUtil.getNotNullValueString(s1))){
			s1 = "0";
		}
		if("".equals(StringUtil.getNotNullValueString(s2))){
			s2 = "0";
		}
		try{
			d1 = Double.valueOf(s1);
		}catch(Exception e){
			d1 = 0d;
		}
		try{
			d2 = Double.valueOf(s2);
		}catch(Exception e){
			d2 = 0d;
		}
		return (d1+d2) +"";
		
		
	}
	
	public static String getYear(String time1){
		String year = "";
		if(StringUtils.isNotEmpty(time1)){
			year = time1.substring(0,4);
		}
		
		
		return year;
	}
	
	public static String getMonth(String time1){
		String month = "";
		if(StringUtils.isNotEmpty(time1)){
			month = time1.substring(5,7);
		}
		return month;
	}

	
	@SuppressWarnings("unchecked")
	public static void statByYear(
			List<BuildPlanTotalVo> total,
			List<BuildPlanYearVo> lastYear,
			List<BuildPlanYearVo> thisYear,
			List result){
		Map<String,DwBuildPlantaskYear> map = new HashMap<String,DwBuildPlantaskYear>();	
		
		String key = "";
		for(BuildPlanTotalVo vo : total){
			key = vo.getProjectId()+","+vo.getTypeId();
			if(map.containsKey(key)){
				DwBuildPlantaskYear temp = map.get(key);
				temp.setTotalCompletion(add(temp.getTotalCompletion(),vo.getPlanTotal()));
			}else{	
				DwBuildPlantaskYear bo = new DwBuildPlantaskYear();
				BeanUtils.copyProperties(vo, bo);
				bo.setTotalCompletion(add(vo.getPlanTotal(),"0"));
				map.put(key, bo);
			}
		}
		
		
		for(BuildPlanYearVo vo : lastYear){
			key = vo.getProjectId()+","+vo.getTypeId();
			if(map.containsKey(key)){
				DwBuildPlantaskYear temp = map.get(key);
				temp.setLastCompletion(add(temp.getLastCompletion(),vo.getTotalFinish()));
			}else{	
				DwBuildPlantaskYear bo = new DwBuildPlantaskYear();
				BeanUtils.copyProperties(vo, bo);
				bo.setLastCompletion(add(vo.getTotalFinish(),"0"));
				map.put(key, bo);
			}
		}
		
		for(BuildPlanYearVo vo : thisYear){
			key = vo.getProjectId()+","+vo.getTypeId();
			if(map.containsKey(key)){
				DwBuildPlantaskYear temp = map.get(key);
				temp.setPlanCompletion(add(temp.getPlanCompletion(),vo.getPlanFinish()));
				temp.setRealCompletion(add(temp.getRealCompletion(),vo.getTotalFinish()));
			}else{	
				DwBuildPlantaskYear bo = new DwBuildPlantaskYear();
				BeanUtils.copyProperties(vo, bo);
				bo.setPlanCompletion(add(vo.getPlanFinish(),"0"));
				bo.setRealCompletion(add(vo.getTotalFinish(),"0"));
				map.put(key, bo);
			}
		}
		
		for(Map.Entry<String, DwBuildPlantaskYear> entry : map.entrySet()){
			result.add(entry.getValue());
		}
		
	}
	
	
	@SuppressWarnings("unchecked")
	public static void statMonth(List<BuildPlanMonthVo> voList,List result){
		Map<String,DwBuildPlantaskMonth> map = new HashMap<String,DwBuildPlantaskMonth>();	
		String key1,key2 = "";
		for(BuildPlanMonthVo vo : voList){
			if(vo.getPlanFinish()!=null && vo.getPlanFinish().length() > 0){
				key1 = vo.getProjectId()+","+vo.getTypeId()
						+","+getYear(vo.getPlanFinish())
						+","+getMonth(vo.getPlanFinish());
				if(map.containsKey(key1)){
					DwBuildPlantaskMonth temp = map.get(key1);
					if(temp.getPlanSingleId() != null &&
							StringUtils.isNotEmpty(vo.getSingleId())){
						temp.setPlanSingleId(temp.getPlanSingleId()+","+vo.getSingleId());
						temp.setPlanSingleName(temp.getPlanSingleName()+","+vo.getSingleName());
					}else{
						temp.setPlanSingleId(vo.getSingleId());
						temp.setPlanSingleName(vo.getSingleName());
					}
				}else{	
					if(StringUtils.isNotEmpty(vo.getSingleId())){
						DwBuildPlantaskMonth bo = new DwBuildPlantaskMonth();
						BeanUtils.copyProperties(vo, bo);
						bo.setStatYear(getYear(vo.getPlanFinish()));
						bo.setStatMonth(getMonth(vo.getPlanFinish()));
						
						bo.setPlanSingleId(vo.getSingleId());
						bo.setPlanSingleName(vo.getSingleName());
						map.put(key1, bo);
					}
					
				}
			}
			
			if(vo.getRealFinish()!=null && vo.getRealFinish().length() > 0){
				key2 = vo.getProjectId()+","+vo.getTypeId()
						+","+getYear(vo.getRealFinish())
						+","+getMonth(vo.getRealFinish());	
				if(map.containsKey(key2)){
					DwBuildPlantaskMonth temp = map.get(key2);
					if(temp.getRealSingleId() !=null && 
							StringUtils.isNotEmpty(vo.getSingleId())){
						temp.setRealSingleId(temp.getRealSingleId()+","+vo.getSingleId());
						temp.setRealSingleName(temp.getRealSingleName()+","+vo.getSingleName());
					}else{
						temp.setRealSingleId(vo.getSingleId());
						temp.setRealSingleName(vo.getSingleName());
					}
				}else{	
					if(StringUtils.isNotEmpty(vo.getSingleId())){
						DwBuildPlantaskMonth bo = new DwBuildPlantaskMonth();
						BeanUtils.copyProperties(vo, bo);
						bo.setStatYear(getYear(vo.getRealFinish()));
						bo.setStatMonth(getMonth(vo.getRealFinish()));
						System.out.println(vo.getSingleId());
						bo.setRealSingleId(vo.getSingleId());
						bo.setRealSingleName(vo.getSingleName());
						map.put(key2, bo);
					}
					
				}
			}
			
		}
		for(Map.Entry<String, DwBuildPlantaskMonth> entry : map.entrySet()){
			result.add(entry.getValue());
		}
		
		
	}
	
}
