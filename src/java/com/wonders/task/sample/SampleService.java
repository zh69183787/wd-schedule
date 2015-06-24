/**
 * 
 */
package com.wonders.task.sample;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import com.wonders.task.sample.bo.*;
import com.wonders.task.sample.service.SamService;
import com.wonders.schedule.common.util.SimpleLogger;
import com.wonders.schedule.util.SpringBeanUtil;

/**
 * @ClassName: SampleService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author zhoushun
 * @date 2012-12-4 下午03:28:48
 * 
 */

@Transactional(value = "txManager",propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
/* 功能模块入口点，beanid即数据库配置中的name */
@Service("sampleService")
@Scope("prototype")
public class SampleService implements ITaskService {
	static SimpleLogger log = new SimpleLogger(SampleService.class);
	private SamService service;
	
	/**
	 * @return the service
	 */
	public SamService getService() {
		return service;
	}

	/**
	 * @param service the service to set
	 */
	@Autowired(required=false)
	public void setService(@Qualifier("samService")SamService service) {
		this.service = service;
	}

	/**
	 * @Title: exec
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param 设定文件
	 * @throws
	 */

	public String exec(String param) {
		String result = "0";
//		try {
//			result = "1";
//			System.out.println("sampleService run+++++++++++++++++++++");
//			Thread.sleep(20000);
//			System.out.println("sampleService end+++++++++++++++++++++");
//		} catch (Exception e) {
//			result = "0";
//			e.printStackTrace();
//		}
		List l =  new ArrayList();
		
		A a = new A();
		B b = new B();
		a.setId("11111");
		a.setTitle("1111111111111111111111");
		a.setDd(new Date());
		l.add(a);
		//this.service.saveAll(a, null);
		this.service.saveAllList(l);
//		a.setTitle("费发的是饭店");
//		b.setName("费发的是饭店");
//		
//		this.service.saveAll(a, b);
		return result;
	}
	
	public static void main(String[] args){
		ApplicationContext applicationContext = null;  
		String[] fileUrl = new String[]{"classpath*:*Context*.xml"};  
		applicationContext = new ClassPathXmlApplicationContext(fileUrl);  
		
//		SamService main = (SamService) SpringBeanUtil.getBean("samService");

		
		ITaskService main = (ITaskService)SpringBeanUtil.getBean("sampleService");
		main.exec("");
		
	}
}
