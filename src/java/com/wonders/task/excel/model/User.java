/**
 * 
 */
package com.wonders.task.excel.model;

/** 
 * @ClassName: User 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-9-26 上午11:26:59 
 *  
 */
public class User {

	private String loginName;
	private String userName;
	private String description;
	
	public User(String loginName,String userName,String description){
		this.loginName = loginName;
		this.userName = userName;
		this.description = description;
	}
	
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
