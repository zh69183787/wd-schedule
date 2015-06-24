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
@Table(name = "DW_BUILD_PROJECT_INFO_PROCESS")
public class BuildProjectInfoProcess implements Serializable{
	private static final long serialVersionUID = 2306129728596045536L;
	private String id;
	private String projectId;
	private String planId;
	private String singleId;
	private String companyId;
	private String milestone;
	private String planYear;
	private String planMonth;
	
	
	private Long beginPlan = 0L;
	private Long beginReal= 0L;
	private Long beginDelay= 0L;
	private Long beginTotal= 0L;
	private Long finishPlan= 0L;
	private Long finishReal= 0L;
	private Long finishDelay= 0L;
	private Long finishTotal= 0L;

	private String operateTime;
	
	public BuildProjectInfoProcess(){
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

	@Column(name = "PLAN_YEAR", nullable = true, length = 50)
	public String getPlanYear() {
		return planYear;
	}


	public void setPlanYear(String planYear) {
		this.planYear = planYear;
	}

	@Column(name = "PLAN_MONTH", nullable = true, length = 50)
	public String getPlanMonth() {
		return planMonth;
	}


	public void setPlanMonth(String planMonth) {
		this.planMonth = planMonth;
	}

	@Column(name = "BEGIN_PLAN")
	public Long getBeginPlan() {
		return beginPlan;
	}


	public void setBeginPlan(Long beginPlan) {
		this.beginPlan = beginPlan;
	}

	@Column(name = "BEGIN_REAL")
	public Long getBeginReal() {
		return beginReal;
	}


	public void setBeginReal(Long beginReal) {
		this.beginReal = beginReal;
	}

	@Column(name = "BEGIN_DELAY")
	public Long getBeginDelay() {
		return beginDelay;
	}


	public void setBeginDelay(Long beginDelay) {
		this.beginDelay = beginDelay;
	}

	@Column(name = "BEGIN_TOTAL")
	public Long getBeginTotal() {
		return beginTotal;
	}


	public void setBeginTotal(Long beginTotal) {
		this.beginTotal = beginTotal;
	}

	@Column(name = "FINISH_PLAN")
	public Long getFinishPlan() {
		return finishPlan;
	}


	public void setFinishPlan(Long finishPlan) {
		this.finishPlan = finishPlan;
	}

	@Column(name = "FINISH_REAL")
	public Long getFinishReal() {
		return finishReal;
	}


	public void setFinishReal(Long finishReal) {
		this.finishReal = finishReal;
	}

	@Column(name = "FINISH_DELAY")
	public Long getFinishDelay() {
		return finishDelay;
	}


	public void setFinishDelay(Long finishDelay) {
		this.finishDelay = finishDelay;
	}

	@Column(name = "FINISH_TOTAL")
	public Long getFinishTotal() {
		return finishTotal;
	}


	public void setFinishTotal(Long finishTotal) {
		this.finishTotal = finishTotal;
	}


	@Column(name = "OPERATETIME", nullable = true, length = 20)
	public String getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}
	
	
}
