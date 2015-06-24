/**
 * 
 */
package com.wonders.schedule.manage;

import java.util.concurrent.Semaphore;

import com.wonders.schedule.model.bo.TScheduleConfig;

/**
 * @ClassName: ScheduleTask
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author zhoushun
 * @date 2012-12-4 下午02:52:19
 * 
 */
public class ScheduleTask implements Runnable {
	private TScheduleConfig t;
	/**
	 * 同步信号量
	 */
	private final Semaphore semaphore = new Semaphore(1);

	public ScheduleTask(TScheduleConfig t) {
		this.t = t;
	}

	/**
	 * @Title: run
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param 设定文件
	 * @throws
	 */
	@Override
	public void run() {
		try {
			semaphore.acquire();
			ScheduleManager.operate(t);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			semaphore.release();
		}
	}

}
