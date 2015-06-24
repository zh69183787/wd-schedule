/**
 * 
 */
package com.wonders.task.build.service;

import java.util.List;

import com.wonders.task.build.model.vo.BuildPlanMonthVo;
import com.wonders.task.build.model.vo.BuildPlanTotalVo;
import com.wonders.task.build.model.vo.BuildPlanVo;
import com.wonders.task.build.model.vo.BuildPlanYearVo;

/** 
 * @ClassName: BuildService 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-10-29 上午10:00:02 
 *  
 */
public interface BuildService {
	public void saveBatch(List<?> c);
	public List<BuildPlanVo> getTaskInfo();
	public List<BuildPlanYearVo> getNowYearInfo();
	public List<BuildPlanYearVo> getLastYearInfo();
	public List<BuildPlanMonthVo> getMonthInfo();
	public List<BuildPlanTotalVo> getTotalInfo();
}
