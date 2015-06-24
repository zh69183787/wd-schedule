/**
 * 
 */
package com.wonders.task.sample;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.wonders.schedule.common.util.SimpleLogger;

/**
 * @ClassName: SampleService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author zhoushun
 * @date 2012-12-4 下午03:28:48
 * 
 */

@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
/* 功能模块入口点，beanid即数据库配置中的name */
@Service("sampleService2")
@Scope("prototype")
public class SampleService2 implements ITaskService {
	static SimpleLogger log = new SimpleLogger(SampleService.class);

	/**
	 * @Title: exec
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param 设定文件
	 * @throws
	 */

	public String exec(String param) {
		String result = "0";
		try {
			result = "1";
			System.out.println("sampleService2 run+++++++++++++++++++++");
			Thread.sleep(5000);
			System.out.println("sampleService2 end+++++++++++++++++++++");
		} catch (Exception e) {
			result = "0";
			e.printStackTrace();
		}
		return result;
	}
}
