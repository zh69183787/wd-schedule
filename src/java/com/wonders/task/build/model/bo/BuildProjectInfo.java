/**
 * 
 */
package com.wonders.task.build.model.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/** 
 * @ClassName: BuildProjectInfo 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-10-29 上午10:03:48 
 *  
 */

@Entity
@Table(name = "DW_BUILD_PROJECT_INFO")
public class BuildProjectInfo implements Serializable{
	private static final long serialVersionUID = 2306129728596045536L;
	private String id;
	private String projectId;
	private String planId;
	private String singleId;
	private String companyId;
	private String milestone;
	private Long planTotal = 0L;
	private Long milestoneDelayTotal= 0L;
	private Long milestoneTotal= 0L;
	private Long abnormalTotal= 0L;
	private Long normalTotal= 0L;
	private Long beginDelayTotal= 0L;
	private Long finishDelayTotal= 0L;
	private Long waitingTotal= 0L;
	private Long doingTotal= 0L;
	private Long doneTotal= 0L;
	private String operateTime;
	
	public BuildProjectInfo(){
		this.operateTime = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
	}

	
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	@Column(name = "ID")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "PROJECT_ID", nullable = true, length = 50)
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	@Column(name = "PLAN_ID", nullable = true, length = 50)
	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	@Column(name = "SINGLE_ID", nullable = true, length = 50)
	public String getSingleId() {
		return singleId;
	}

	public void setSingleId(String singleId) {
		this.singleId = singleId;
	}

	@Column(name = "COMPANY_ID", nullable = true, length = 50)
	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	
	@Column(name = "MILESTONE", nullable = true, length = 50)
	public String getMilestone() {
		return milestone;
	}

	public void setMilestone(String milestone) {
		this.milestone = milestone;
	}

	@Column(name = "PLAN_TOTAL")
	public Long getPlanTotal() {
		return planTotal;
	}

	public void setPlanTotal(Long planTotal) {
		this.planTotal = planTotal;
	}

	@Column(name = "MILESTONE_DELAY_TOTAL")
	public Long getMilestoneDelayTotal() {
		return milestoneDelayTotal;
	}

	public void setMilestoneDelayTotal(Long milestoneDelayTotal) {
		this.milestoneDelayTotal = milestoneDelayTotal;
	}

	
	@Column(name = "MILESTONE_TOTAL")
	public Long getMilestoneTotal() {
		return milestoneTotal;
	}

	public void setMilestoneTotal(Long milestoneTotal) {
		this.milestoneTotal = milestoneTotal;
	}

	@Column(name = "ABNORMAL_TOTAL")
	public Long getAbnormalTotal() {
		return abnormalTotal;
	}

	public void setAbnormalTotal(Long abnormalTotal) {
		this.abnormalTotal = abnormalTotal;
	}

	
	@Column(name = "NORMAL_TOTAL")
	public Long getNormalTotal() {
		return normalTotal;
	}

	public void setNormalTotal(Long normalTotal) {
		this.normalTotal = normalTotal;
	}

	
	@Column(name = "BEGIN_DELAY_TOTAL")
	public Long getBeginDelayTotal() {
		return beginDelayTotal;
	}

	public void setBeginDelayTotal(Long beginDelayTotal) {
		this.beginDelayTotal = beginDelayTotal;
	}

	
	@Column(name = "FINISH_DELAY_TOTAL")
	public Long getFinishDelayTotal() {
		return finishDelayTotal;
	}

	public void setFinishDelayTotal(Long finishDelayTotal) {
		this.finishDelayTotal = finishDelayTotal;
	}

	@Column(name = "WAITING_TOTAL")
	public Long getWaitingTotal() {
		return waitingTotal;
	}

	public void setWaitingTotal(Long waitingTotal) {
		this.waitingTotal = waitingTotal;
	}

	
	@Column(name = "DOING_TOTAL")
	public Long getDoingTotal() {
		return doingTotal;
	}

	public void setDoingTotal(Long doingTotal) {
		this.doingTotal = doingTotal;
	}

	
	@Column(name = "DONE_TOTAL")
	public Long getDoneTotal() {
		return doneTotal;
	}

	public void setDoneTotal(Long doneTotal) {
		this.doneTotal = doneTotal;
	}

	
	@Column(name = "OPERATETIME", nullable = true, length = 20)
	public String getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}
	
	
}
