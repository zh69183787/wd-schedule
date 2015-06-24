/**   
* @Title: ProcessInfoVo.java 
* @Package com.wonders.task.workflowExternal.model.vo 
* @Description: TODO(用一句话描述该文件做什么) 
* @author zhoushun   
* @date 2014年7月1日 下午3:02:40 
* @version V1.0   
*/
package com.wonders.task.workflowExternal.model.vo;

/** 
 * @ClassName: ProcessInfoVo 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年7月1日 下午3:02:40 
 *  
 */
public class ProcessInfoVo {
	public String process;
	public String incident;
	public String stepName;
	public String loginName;
	public String getProcess() {
		return process;
	}
	public void setProcess(String process) {
		this.process = process;
	}
	public String getIncident() {
		return incident;
	}
	public void setIncident(String incident) {
		this.incident = incident;
	}
	public String getStepName() {
		return stepName;
	}
	public void setStepName(String stepName) {
		this.stepName = stepName;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	
}
