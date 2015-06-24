/**
 * 
 */
package com.wonders.task.todoItem.model.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/** 
 * @ClassName: TodoItem 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-1-22 下午03:29:06 
 *  
 */

@Entity
@Table(name = "T_TODO_ITEM")
public class TodoItem implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8471458287730081764L;
	private String id = "";
	private String app;
	private Integer type;
	private String key;
	private String occurTime;
	private String title;
	private String data;
	private String userId;
	private String loginName;
	private String deptId;
	private Integer status;
	private Integer removed;
	private String typename;
	private String applydept;
	
	public TodoItem(){
		this.removed = 0;
		this.app = "schedule";
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
	
	@Column(name = "APP", nullable = true, length = 50)
	public String getApp() {
		return app;
	}
	public void setApp(String app) {
		this.app = app;
	}
	
	@Column(name = "TYPE")
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	@Column(name = "KEY", nullable = true, length = 50)
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	@Column(name = "OCCURTIME", nullable = true, length = 50)
	public String getOccurTime() {
		return occurTime;
	}
	public void setOccurTime(String occurTime) {
		this.occurTime = occurTime;
	}
	
	@Column(name = "TITLE", nullable = true, length = 1000)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name = "DATA", nullable = true, length = 4000)
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	@Column(name = "USERID", nullable = true, length = 50)
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Column(name = "LOGINNAME", nullable = true, length = 50)
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	@Column(name = "DEPTID", nullable = true, length = 50)
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	
	@Column(name = "STATUS")
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Column(name = "REMOVED")
	public Integer getRemoved() {
		return removed;
	}
	public void setRemoved(Integer removed) {
		this.removed = removed;
	}
	
	@Column(name = "TYPENAME", nullable = true, length = 50)
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}
	
	@Column(name = "APPLYDEPT", nullable = true, length = 50)
	public String getApplydept() {
		return applydept;
	}
	public void setApplydept(String applydept) {
		this.applydept = applydept;
	}
}
	
