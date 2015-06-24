/**
 * 
 */
package com.wonders.task.build.main;


import java.io.File;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wonders.schedule.util.SpringBeanUtil;
import com.wonders.task.build.model.vo.BuildPlanMonthVo;
import com.wonders.task.build.model.vo.BuildPlanTotalVo;
import com.wonders.task.build.model.vo.BuildPlanVo;
import com.wonders.task.build.model.vo.BuildPlanYearVo;
import com.wonders.task.build.service.BuildService;
import com.wonders.task.build.util.BuildTaskUtil;
import com.wonders.task.build.util.BuildStatusUtil;
import com.wonders.task.sample.ITaskService;

/** 
 * @ClassName: BuildMain 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-10-29 上午9:53:55 
 *  
 */

@Transactional(value = "txManager",propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
@Service("buildMain")
public class BuildMain implements ITaskService{
	
	private BuildService service;
	
	public BuildService getService() {
		return service;
	}
	@Autowired(required=false)
	public void setService(@Qualifier("buildService")BuildService service) {
		this.service = service;
	}



	@SuppressWarnings("rawtypes")
	@Override
	public String exec(String param) {
		List<BuildPlanVo> list = this.service.getTaskInfo();
		List result = BuildStatusUtil.statBuildStatus(list);
		System.out.println(result.size());
		List<BuildPlanTotalVo> total = this.service.getTotalInfo();
		System.out.println(total.size());
		List<BuildPlanYearVo> lastYear = this.service.getLastYearInfo();
		System.out.println(lastYear.size());
		List<BuildPlanYearVo> thisYear = this.service.getNowYearInfo();
		System.out.println(thisYear.size());
		List<BuildPlanMonthVo> monthResult = this.service.getMonthInfo();
		System.out.println(monthResult.size());
		BuildTaskUtil.statByYear(total, lastYear, thisYear,result);
		BuildTaskUtil.statMonth(monthResult, result);
		System.out.println(result.size());
		this.service.saveBatch(result);
		
		
		
		
		return null;
	}
	
	
	
	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
		ApplicationContext applicationContext = null;  
		String[] fileUrl = new String[]{"classpath*:*Context*.xml"};  
		applicationContext = new ClassPathXmlApplicationContext(fileUrl);  
		ITaskService task = (ITaskService) SpringBeanUtil.getBean("buildMain");
		//System.out.println(BuildMain.class.getClassLoader().getResource("com/wonders/task/build/main/build.xml").getPath());
		// SAXReader reader = new SAXReader();   
       //  Document document = reader.read(new File(BuildMain.class.getClassLoader().getResource("com/wonders/task/build/main/build.xml").getPath());
       //  System.out.println(document.getRootElement().getName());
         
		task.exec("");
		// System.out.println(DateFormat.getDateInstance(DateFormat.DEFAULT).format(new Date()));
	}
	

}
