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
 * @ClassName: DelayItem 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-1-22 下午03:29:06 
 *  
 */

@Entity
@Table(name = "T_Delay_ITEM")
public class TDelayItem implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8471458287730081764L;
	private String id = "";
	private String process;
	private String incident;
	private String status;
	private String delayDate;
	private String delayDay;
	private String delayPerson;
	private String removed;
	private String updateTime;
	
	
	public TDelayItem(){
		this.removed = "0";
		this.status = "0";
		this.updateTime =  new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
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
	
	@Column(name = "PROCESS", nullable = true, length = 50)
	public String getProcess() {
		return process;
	}
	public void setProcess(String process) {
		this.process = process;
	}
	
	@Column(name = "INCIDENT", nullable = true, length = 50)
	public String getIncident() {
		return incident;
	}
	public void setIncident(String incident) {
		this.incident = incident;
	}
	
	@Column(name = "STATUS", nullable = true, length = 1)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name = "DELAY_DATE", nullable = true, length = 50)
	public String getDelayDate() {
		return delayDate;
	}
	public void setDelayDate(String delayDate) {
		this.delayDate = delayDate;
	}
	
	@Column(name = "DELAY_DAY", nullable = true, length = 50)
	public String getDelayDay() {
		return delayDay;
	}
	public void setDelayDay(String delayDay) {
		this.delayDay = delayDay;
	}
	
	@Column(name = "DELAY_PERSON", nullable = true, length = 50)
	public String getDelayPerson() {
		return delayPerson;
	}
	public void setDelayPerson(String delayPerson) {
		this.delayPerson = delayPerson;
	}
	
	@Column(name = "REMOVED", nullable = true, length = 1)
	public String getRemoved() {
		return removed;
	}
	public void setRemoved(String removed) {
		this.removed = removed;
	}
	
	@Column(name = "UPDATE_TIME", nullable = true, length = 50)
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
	
	
}
	
