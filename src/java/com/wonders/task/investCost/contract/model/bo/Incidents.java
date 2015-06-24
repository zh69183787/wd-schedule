/** 
 * Copyright (c) 1995-2011 Wonders Information Co.,Ltd. 
 * 1518 Lianhang Rd,Shanghai 201112.P.R.C.
 * All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of WondersGroup.
 * You shall not disclose such Confidential Information and shall use it only in accordance 
 * with the terms of the license agreement you entered into with WondersGroup. 
 *
 */

package com.wonders.task.investCost.contract.model.bo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Incidentsʵ�嶨��
 * 
 * @author ycl
 * @version $Revision$
 * @date 2013-3-26
 * @author modify by $Author$
 * @since 1.0
 */

@Entity
@Table(name = "INCIDENTS")
public class Incidents implements Serializable{

	private Long incident; // incident

	private String processname; // processname

	private Date endtime; // endtime

	private String helpurl; // helpurl

	private String initiator; // initiator

	private String mainss; // mainss

	private String parenttaskid; // parenttaskid

	private String priorities; // priorities

	private Long priority; // priority

	private Long processversion; // processversion

	private Date starttime; // starttime

	private Long status; // status

	private String summary; // summary

	private Date timelimit; // timelimit

	public void setIncident(Long incident) {
		this.incident = incident;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "INCIDENT")
	public Long getIncident() {
		return incident;
	}

	public void setProcessname(String processname) {
		this.processname = processname;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PROCESSNAME")
	public String getProcessname() {
		return processname;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	@Column(name = "ENDTIME", nullable = true, length = 7)
	public Date getEndtime() {
		return endtime;
	}

	public void setHelpurl(String helpurl) {
		this.helpurl = helpurl;
	}

	@Column(name = "HELPURL", nullable = true, length = 1024)
	public String getHelpurl() {
		return helpurl;
	}

	public void setInitiator(String initiator) {
		this.initiator = initiator;
	}

	@Column(name = "INITIATOR", nullable = true, length = 1024)
	public String getInitiator() {
		return initiator;
	}

	public void setMainss(String mainss) {
		this.mainss = mainss;
	}

	@Column(name = "MAINSS", nullable = true, length = 4000)
	public String getMainss() {
		return mainss;
	}

	public void setParenttaskid(String parenttaskid) {
		this.parenttaskid = parenttaskid;
	}

	@Column(name = "PARENTTASKID", nullable = true, length = 64)
	public String getParenttaskid() {
		return parenttaskid;
	}

	public void setPriorities(String priorities) {
		this.priorities = priorities;
	}

	@Column(name = "PRIORITIES", nullable = true, length = 200)
	public String getPriorities() {
		return priorities;
	}

	public void setPriority(Long priority) {
		this.priority = priority;
	}

	@Column(name = "PRIORITY", nullable = true, length = 20)
	public Long getPriority() {
		return priority;
	}

	public void setProcessversion(Long processversion) {
		this.processversion = processversion;
	}

	@Column(name = "PROCESSVERSION", nullable = true, length = 20)
	public Long getProcessversion() {
		return processversion;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	@Column(name = "STARTTIME", nullable = true, length = 7)
	public Date getStarttime() {
		return starttime;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	@Column(name = "STATUS", nullable = true, length = 20)
	public Long getStatus() {
		return status;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@Column(name = "SUMMARY", nullable = true, length = 1600)
	public String getSummary() {
		return summary;
	}

	public void setTimelimit(Date timelimit) {
		this.timelimit = timelimit;
	}

	@Column(name = "TIMELIMIT", nullable = true, length = 7)
	public Date getTimelimit() {
		return timelimit;
	}

}
