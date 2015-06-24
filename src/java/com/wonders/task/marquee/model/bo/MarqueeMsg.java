package com.wonders.task.marquee.model.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "T_MARQUEE_MSG")
public class MarqueeMsg {
	
	//@Version
	//private int version;
	private String id;
	private String type;
	private String title;
	private String content;
	private String priority;
	private String publishTime;
	private String validStartTime;
	private String validEndTime;
	private String loginName;
	private String deptId;
	private String rank;
	private String sourceDept;
	private String sourceSystem;
	private String operateTime;
	private String removed;
	private String url;
	private String app;
	
	
	//public int getVersion() {
	//	return version;
	//}
	//private void setVersion(int version) {
	//	this.version = version;
	//}
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "ID")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "TYPE", nullable = true, length = 200)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@Column(name = "TITLE", nullable = true, length = 500)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name = "CONTENT", nullable = true, length = 1000)
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Column(name = "PRIORITY", nullable = true, length = 50)
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	
	@Column(name = "PUBLISH_TIME", nullable = true, length = 50)
	public String getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}
	
	@Column(name = "VALID_START_TIME", nullable = true, length = 50)
	public String getValidStartTime() {
		return validStartTime;
	}
	public void setValidStartTime(String validStartTime) {
		this.validStartTime = validStartTime;
	}
	
	@Column(name = "VALID_END_TIME", nullable = true, length = 50)
	public String getValidEndTime() {
		return validEndTime;
	}
	public void setValidEndTime(String validEndTime) {
		this.validEndTime = validEndTime;
	}
	
	@Column(name = "LOGIN_NAME", nullable = true, length = 50)
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	@Column(name = "DEPT_ID", nullable = true, length = 50)
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	
	@Column(name = "RANK", nullable = true, length = 500)
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	
	@Column(name = "SOURCE_DEPT", nullable = true, length = 500)
	public String getSourceDept() {
		return sourceDept;
	}
	public void setSourceDept(String sourceDept) {
		this.sourceDept = sourceDept;
	}
	
	@Column(name = "SOURCE_SYSTEM", nullable = true, length = 500)
	public String getSourceSystem() {
		return sourceSystem;
	}
	public void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
	}
	
	@Column(name = "OPERATE_TIME", nullable = true, length = 20)
	public String getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}
	
	@Column(name = "REMOVED", nullable = true, length = 1)
	public String getRemoved() {
		return removed;
	}
	public void setRemoved(String removed) {
		this.removed = removed;
	}
	
	@Column(name = "URL", nullable = true, length = 1000)
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Column(name = "APP", nullable = true, length = 200)
	public String getApp() {
		return app;
	}
	public void setApp(String app) {
		this.app = app;
	}
	
	
}
