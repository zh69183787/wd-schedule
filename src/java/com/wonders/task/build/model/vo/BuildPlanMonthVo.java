/**
 * 
 */
package com.wonders.task.build.model.vo;

/** 
 * @ClassName: BuildPlanMonthVo 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013年12月2日 下午4:02:39 
 *  
 */
public class BuildPlanMonthVo {
	private String projectId;
	private String projectName;
	private String typeId;
	private String typeName;
	private String singleId;
	private String singleName;
	private String planFinish;
	private String realFinish;
	
	
	
	public String getPlanFinish() {
		return planFinish;
	}
	public void setPlanFinish(String planFinish) {
		this.planFinish = planFinish;
	}
	public String getRealFinish() {
		return realFinish;
	}
	public void setRealFinish(String realFinish) {
		this.realFinish = realFinish;
	}
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
	public String getSingleId() {
		return singleId;
	}
	public void setSingleId(String singleId) {
		this.singleId = singleId;
	}
	public String getSingleName() {
		return singleName;
	}
	public void setSingleName(String singleName) {
		this.singleName = singleName;
	}
	
	
}
