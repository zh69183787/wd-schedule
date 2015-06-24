/**
 * 
 */
package com.wonders.schedule.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import com.wonders.schedule.common.util.SimpleLogger;
import com.wonders.schedule.execute.IExecutable;
import com.wonders.schedule.model.bo.TScheduleConfig;

/**
 * @ClassName: ScheduleManager
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author zhoushun
 * @date 2012-12-4 下午02:58:39
 * 
 */
@Component("scheduleManager")
public class ScheduleManager {
	private static IExecutable executeService;
	static SimpleLogger log = new SimpleLogger(ScheduleManager.class);

	public static void operate(TScheduleConfig t) {
		String result = "0";
		try {
			result = executeService.execute(t);
			log.debug("ScheduleManager " + t.getDescription() + "--------"
					+ result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static IExecutable getExecuteService() {
		return executeService;
	}

	@Autowired
	public void setExecuteService(
			@Qualifier(value = "executeService") IExecutable executeService) {
		ScheduleManager.executeService = executeService;
	}

	public static void main(String[] args) {
		System.out.println(ScheduleManager.class.getName());
		System.out.println(new java.util.Date().getTime());
	}
}
