/**
 * 
 */
package com.wonders.task.sample.bo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/** 
 * @ClassName: A 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-17 上午9:15:16 
 *  
 */
@Entity
@Table(name = "t_user_msg")
public class A implements Serializable{

	private String id;
	private String title;
	private Date dd;
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
	 * @return the title
	 */
	@Column(name = "title", nullable = true, length = 50)
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DD")
	public Date getDd() {
		return dd;
	}
	public void setDd(Date dd) {
		this.dd = dd;
	}

	
}
