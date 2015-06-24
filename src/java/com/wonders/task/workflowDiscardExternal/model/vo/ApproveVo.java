/**   
* @Title: ApproveVo.java 
* @Package com.wonders.task.workflowDiscardExternal.model.vo 
* @Description: TODO(用一句话描述该文件做什么) 
* @author zhoushun   
* @date 2014年7月1日 下午2:44:40 
* @version V1.0   
*/
package com.wonders.task.workflowDiscardExternal.model.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/** 
 * @ClassName: ApproveVo 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年7月1日 下午2:44:40 
 *  
 */

@XmlRootElement(name = "b")
@XmlAccessorType(XmlAccessType.FIELD)
public class ApproveVo {
	private String process = "";
	private String incidentno = "";
	private String dept = "";
	private String deptId = "";
	private String username = "";
	private String stepname = "";
	private String userfullname = "";
	private String remark = "";
	private String upddate = "";
	private String approveAttach = "";
	public String getProcess() {
		return process;
	}
	public void setProcess(String process) {
		this.process = process;
	}
	public String getIncidentno() {
		return incidentno;
	}
	public void setIncidentno(String incidentno) {
		this.incidentno = incidentno;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getStepname() {
		return stepname;
	}
	public void setStepname(String stepname) {
		this.stepname = stepname;
	}
	public String getUserfullname() {
		return userfullname;
	}
	public void setUserfullname(String userfullname) {
		this.userfullname = userfullname;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getUpddate() {
		return upddate;
	}
	public void setUpddate(String upddate) {
		this.upddate = upddate;
	}
	public String getApproveAttach() {
		return approveAttach;
	}
	public void setApproveAttach(String approveAttach) {
		this.approveAttach = approveAttach;
	}	
	
	
}
