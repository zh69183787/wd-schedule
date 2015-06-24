/**
 * 
 */
package com.wonders.schedule.aspect;

import java.lang.reflect.Method;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import com.wonders.schedule.common.util.SimpleLogger;
import com.wonders.schedule.execute.IExecutable;
import com.wonders.schedule.model.bo.TScheduleConfig;

/**
 * @ClassName: ScheduleLog
 * @Description: TODO(日志记录)
 * @author zhoushun
 * @date 2012-12-5 上午11:27:15
 * 
 */
@Aspect
@Component("logAspect")
public class ScheduleLog {
	static SimpleLogger log = new SimpleLogger(ScheduleLog.class);

	@Before(value = "execution(* com.wonders.schedule.execute.IExecutable.*(..))")
	//也可使用 定义的切入点
	//@Before("com.wonders.schedule.aspect.PointCutDef.beforeTaskExec()")
	public void beforeTask(JoinPoint joinPoint) {
		log.debug("sql prepare================================");
		IExecutable target = (IExecutable) joinPoint.getTarget();
		TScheduleConfig t = (TScheduleConfig) joinPoint.getArgs()[0];
		executeMethod(t, target, "begin", "");
	}

	// 总会执行
	// @After(value="execution(* com.wonders.schedule.execute.IExecutable.*(..))")
	// public void afterTask(JoinPoint joinPoint){
	// log.debug("sql end================================");
	// IExecutable target = (IExecutable) joinPoint.getTarget();
	// TScheduleConfig t = (TScheduleConfig)joinPoint.getArgs()[0];
	// executeMethod(t,target,"end");
	// afterMethod(t,target);
	// }

	// 可获取返回值 返回值后执行
	@AfterReturning(value = "execution(* com.wonders.schedule.execute.IExecutable.*(..))",returning = "rtv")
	public void afterTask(JoinPoint joinPoint, Object rtv) {
		log.debug("sql end================================");
		IExecutable target = (IExecutable) joinPoint.getTarget();
		TScheduleConfig t = (TScheduleConfig) joinPoint.getArgs()[0];
		executeMethod(t, target, "end", rtv==null ? "" : rtv.toString());
		afterMethod(t, target);
	}
	
	// 可获取返回值 返回值后执行 切入点应放在执行的service 的接口上，这样能获得该service
	//内部方法及类的异常，若放在最外层，则只能获得外层service 
	//即方法抛出只能获得当前这层service内的 异常
	//若嵌套调用service 则需要将切入点放在最内层service 方能捕捉真正的内部 异常；
	//否则 异常停止在当前service层，不会深入内部
	// 抛出后通知（After throwing advice）
//	@AfterThrowing(value = "execution(* com.wonders.task.sample.ITaskService.*(..))", throwing = "error")
//	public void afterThrowAdvice(JoinPoint joinPoint, Throwable error) {
//		String exceptionInfo = "";
//		IExecutable target = (IExecutable) joinPoint.getTarget();
//		TScheduleConfig t = (TScheduleConfig) joinPoint.getArgs()[0];
//		exceptionInfo += "代理对象：" + joinPoint.getTarget().getClass().getName()
//				+ "。 异常方法：" + joinPoint.getSignature() + "。异常详细信息："
//				+ error.toString() + "。" + "\n" + constructError(error);
//		log.debug("exceptionInfo log================================"
//				+ exceptionInfo);
//		exceptionMethod(t, target, "exception", exceptionInfo);
//	}
	
	// 抛出后通知（After throwing advice） 应写为 itaskservice 即可捕捉异常及返回至
	@AfterThrowing(value = "execution(* com.wonders.schedule.execute.IExecutable.*(..))", throwing = "error")
	public void afterThrowAdvice(JoinPoint joinPoint, Throwable error) {
		String exceptionInfo = "";
		IExecutable target = (IExecutable) joinPoint.getTarget();
		TScheduleConfig t = (TScheduleConfig) joinPoint.getArgs()[0];
		exceptionInfo += "代理对象：" + joinPoint.getTarget().getClass().getName()
				+ "。 异常方法：" + joinPoint.getSignature() + "。异常详细信息："
				+ error.toString() + "。" + "\n" + constructError(error);
		log.debug("exceptionInfo log================================"
				+ exceptionInfo);
		exceptionMethod(t, target, "exception", exceptionInfo);
	}

	private void executeMethod(TScheduleConfig t, IExecutable target,
			String process, String rtv) {
		log.debug("sql log================================"+"TScheduleConfig:"+t.getName()+"rtv="+rtv);
		try {
			Method method = LogUtil.class.getMethod(
					"writeLog",
					new Class[] { TScheduleConfig.class, String.class,
							String.class });
			method.invoke(null, new Object[] { t, process, rtv });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void exceptionMethod(TScheduleConfig t, IExecutable target,
			String process, String error) {
		log.debug("sql log================================");
		try {
			Method method = LogUtil.class.getMethod(
					"writeException",
					new Class[] { TScheduleConfig.class, String.class,
							String.class });
			method.invoke(null, new Object[] { t, process, error });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void afterMethod(TScheduleConfig t, IExecutable target) {
		try {
			Method method = LogUtil.class.getMethod("updateTime",
					new Class[] { TScheduleConfig.class });
			method.invoke(null, new Object[] { t });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 构建错误对象
	private String constructError(Throwable e) {
		StringBuffer exInfo = new StringBuffer();
		StackTraceElement[] messages = e.getStackTrace();
		int length = messages.length;
		for (int i = 0; i < length; i++) {
			if (messages[i].getClassName().indexOf("com.wonders") > -1) {
				exInfo.append("ClassName:" + messages[i].getClassName() + "\n");
				exInfo.append("getLineNumber:" + messages[i].getLineNumber()
						+ "\n");
				exInfo.append("getMethodName:" + messages[i].getMethodName()
						+ "\n");
				exInfo.append("toString:" + messages[i].toString() + "\n");
			}
		}
		return exInfo.toString();
	}
}
