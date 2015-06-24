/**
 * 
 */
package com.wonders.task.autoUrge.model.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/** 
 * @ClassName: TUrgeLog 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-4-29 上午10:30:01 
 *  
 */
@Entity
@Table(name = "T_URGE_LOG")
public class TUrgeLog implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6548644661813538042L;
	
	private String id;
	private String status;
	private String name;
	private String content;
	private String insertdate;
	private String error;
	private String dept;
	
	public TUrgeLog(){
		this.status = "55";
		this.insertdate = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
	}
	
	/**
	 * @return the id
	 */
	
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	@Column(name = "ID")
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the status
	 */
	@Column(name = "STATUS", nullable = true, length = 5)
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the mobile
	 */
	
	@Column(name = "NAME", nullable = true, length = 300)
	public String getName() {
		return name;
	}
	/**
	 * @param mobile the mobile to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the content
	 */
	
	@Column(name = "CONTENT", nullable = true, length = 500)
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the insertdate
	 */
	@Column(name = "INSERTDATE", nullable = true, length = 20)
	public String getInsertdate() {
		return insertdate;
	}

	/**
	 * @param insertdate the insertdate to set
	 */
	public void setInsertdate(String insertdate) {
		this.insertdate = insertdate;
	}

	/**
	 * @return the error
	 */
	@Column(name = "ERROR", nullable = true, length = 300)
	public String getError() {
		return error;
	}

	/**
	 * @param error the error to set
	 */
	public void setError(String error) {
		this.error = error;
	}

	/**
	 * @return the dept
	 */
	@Column(name = "DEPT", nullable = true, length = 300)
	public String getDept() {
		return dept;
	}

	/**
	 * @param dept the dept to set
	 */
	public void setDept(String dept) {
		this.dept = dept;
	}
	
	
}
