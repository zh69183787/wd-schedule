/**
 * 
 */
package com.wonders.task.build.model.vo;

/** 
 * @ClassName: BuildPlanYearVo 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013年12月2日 下午3:04:40 
 *  
 */
public class BuildPlanYearVo {
	private String projectId;
	private String projectName;
	private String typeId;
	private String typeName;
	private String planFinish;
	private String totalFinish;
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getPlanFinish() {
		return planFinish;
	}
	public void setPlanFinish(String planFinish) {
		this.planFinish = planFinish;
	}
	public String getTotalFinish() {
		return totalFinish;
	}
	public void setTotalFinish(String totalFinish) {
		this.totalFinish = totalFinish;
	}
	
	
	
}
