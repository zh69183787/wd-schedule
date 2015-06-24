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
 * @ClassName: TShortMsg 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-4-29 上午10:30:01 
 *  
 */
@Entity
@Table(name = "T_SHORT_MSG")
public class TShortMsg implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6548644661813538042L;
	
	private String id;
	private String status;
	private String mobile;
	private String content;
	
	public TShortMsg(){
		this.status = "0";
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
	
	@Column(name = "MOBILE", nullable = true, length = 300)
	public String getMobile() {
		return mobile;
	}
	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
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
	
	
}
