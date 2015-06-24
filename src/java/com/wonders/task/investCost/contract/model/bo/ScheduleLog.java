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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ScheduleLogʵ�嶨��
 * 
 * @author ycl
 * @version $Revision$
 * @date 2013-3-26
 * @author modify by $Author$
 * @since 1.0
 */

@Entity
@Table(name = "T_SCHEDULE_LOG")
public class ScheduleLog implements Serializable{

	private String id; // id

	private String exception; // exception

	private String execTime; // execTime

	private String ext1; // ext1

	private String ext2; // ext2

	private String ext3; // ext3

	private String method; // method

	private String name; // name

	private String param; // param

	private String process; // process

	private String result; // result

	private String type; // type

	public void setId(String id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	public String getId() {
		return id;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	@Column(name = "EXCEPTION", nullable = true, length = 2000)
	public String getException() {
		return exception;
	}

	public void setExecTime(String execTime) {
		this.execTime = execTime;
	}

	@Column(name = "EXEC_TIME", nullable = true, length = 19)
	public String getExecTime() {
		return execTime;
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	@Column(name = "EXT1", nullable = true, length = 200)
	public String getExt1() {
		return ext1;
	}

	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}

	@Column(name = "EXT2", nullable = true, length = 200)
	public String getExt2() {
		return ext2;
	}

	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}

	@Column(name = "EXT3", nullable = true, length = 200)
	public String getExt3() {
		return ext3;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	@Column(name = "METHOD", nullable = true, length = 200)
	public String getMethod() {
		return method;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "NAME", nullable = true, length = 200)
	public String getName() {
		return name;
	}

	public void setParam(String param) {
		this.param = param;
	}

	@Column(name = "PARAM", nullable = true, length = 200)
	public String getParam() {
		return param;
	}

	public void setProcess(String process) {
		this.process = process;
	}

	@Column(name = "PROCESS", nullable = true, length = 100)
	public String getProcess() {
		return process;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Column(name = "RESULT", nullable = true, length = 200)
	public String getResult() {
		return result;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "TYPE", nullable = true, length = 20)
	public String getType() {
		return type;
	}

}
