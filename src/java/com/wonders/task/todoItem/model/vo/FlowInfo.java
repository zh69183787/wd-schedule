/**
 * 
 */
package com.wonders.task.todoItem.model.vo;

/** 
 * @ClassName: FlowInfo 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-1-22 下午02:26:39 
 *  
 *  
 **apply_username
deptName
endtime
overduetime
summary
memo
taskuser_name
priorities_show
taskid
UserName
pname
pincident
steplabel
task_type
processname
incident
 */
public class FlowInfo {
	private String summary;//摘要
	private String memo;//备注
	private String priorities_show;//重要性 字符串拼接
	private String pname;//父流程名
	private String pincident;//父流程ID
	private String processname;//子流程
	private String incident;//子流程id
	private String steplabel;//当前步骤
	private String overduetime;//超时时间
	private String endtime;//完成时间
	private String taskid;//id
	private String task_type;//流程类型 0 待办 1 已办
	private String apply_username;//发起人中文名
	private String taskuser_name;//当前用户中文名
	private String UserName;//ST/G00100000161
	private String deptname;//申请部门名称
	private String deptId; //2016
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getPriorities_show() {
		return priorities_show;
	}
	public void setPriorities_show(String priorities_show) {
		this.priorities_show = priorities_show;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getPincident() {
		return pincident;
	}
	public void setPincident(String pincident) {
		this.pincident = pincident;
	}
	public String getProcessname() {
		return processname;
	}
	public void setProcessname(String processname) {
		this.processname = processname;
	}
	public String getIncident() {
		return incident;
	}
	public void setIncident(String incident) {
		this.incident = incident;
	}
	public String getSteplabel() {
		return steplabel;
	}
	public void setSteplabel(String steplabel) {
		this.steplabel = steplabel;
	}
	public String getOverduetime() {
		return overduetime;
	}
	public void setOverduetime(String overduetime) {
		this.overduetime = overduetime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getTaskid() {
		return taskid;
	}
	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}
	public String getTask_type() {
		return task_type;
	}
	public void setTask_type(String task_type) {
		this.task_type = task_type;
	}
	public String getApply_username() {
		return apply_username;
	}
	public void setApply_username(String apply_username) {
		this.apply_username = apply_username;
	}
	public String getTaskuser_name() {
		return taskuser_name;
	}
	public void setTaskuser_name(String taskuser_name) {
		this.taskuser_name = taskuser_name;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getDeptname() {
		return deptname;
	}
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	
	
	
	
	
}
