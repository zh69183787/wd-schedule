/**
 * 
 */
package com.wonders.task.build.model.vo;

/** 
 * @ClassName: BuildPlanTotalVo 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013年12月3日 上午11:12:56 
 *  
 */
public class BuildPlanTotalVo {
	private String projectId;
	private String projectName;
	private String typeId;
	private String typeName;
	private String planTotal;

	
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getPlanTotal() {
		return planTotal;
	}

	public void setPlanTotal(String planTotal) {
		this.planTotal = planTotal;
	}

	

	
}
