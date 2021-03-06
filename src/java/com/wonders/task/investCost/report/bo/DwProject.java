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

package com.wonders.task.investCost.report.bo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * DwProjectʵ�嶨��
 * 
 * @author ycl
 * @version $Revision$
 * @date 2013-9-16
 * @author modify by $Author$
 * @since 1.0
 */

@Entity
@Table(name = "DW_PROJECT")
public class DwProject implements Serializable{

	private Date createDate; // createDate

	private String id; // id

	private String lineId; // lineId

	private String lineName; // lineName

	private String type1; // type1

	private String type2; // type2

	private String type3; // type3

	private String type4; // type4

	private String type5; // type5

	private String type6; // type6

	private Date updateDate; // updateDate

	private String year;
	private Long order;
	
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "CREATE_DATE", nullable = true, length = 11)
	public Date getCreateDate() {
		return createDate;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(generator="system.uuid")
	@GenericGenerator(name="system.uuid",strategy="uuid")
	@Column(name = "ID", nullable = false, length = 40)
	public String getId() {
		return id;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	@Column(name = "LINE_ID", nullable = true, length = 40)
	public String getLineId() {
		return lineId;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	@Column(name = "LINE_NAME", nullable = true, length = 400)
	public String getLineName() {
		return lineName;
	}

	public void setType1(String type1) {
		this.type1 = type1;
	}

	@Column(name = "TYPE1", nullable = true, length = 40)
	public String getType1() {
		return type1;
	}

	public void setType2(String type2) {
		this.type2 = type2;
	}

	@Column(name = "TYPE2", nullable = true, length = 40)
	public String getType2() {
		return type2;
	}

	public void setType3(String type3) {
		this.type3 = type3;
	}

	@Column(name = "TYPE3", nullable = true, length = 40)
	public String getType3() {
		return type3;
	}

	public void setType4(String type4) {
		this.type4 = type4;
	}

	@Column(name = "TYPE4", nullable = true, length = 40)
	public String getType4() {
		return type4;
	}

	public void setType5(String type5) {
		this.type5 = type5;
	}

	@Column(name = "TYPE5", nullable = true, length = 40)
	public String getType5() {
		return type5;
	}

	public void setType6(String type6) {
		this.type6 = type6;
	}

	@Column(name = "TYPE6", nullable = true, length = 40)
	public String getType6() {
		return type6;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Column(name = "UPDATE_DATE", nullable = true, length = 11)
	public Date getUpdateDate() {
		return updateDate;
	}

	@Column(name = "YEAR", nullable = true, length = 4)
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Column(name = "ORDER_", nullable = true, length = 4)
	public Long getOrder() {
		return order;
	}

	public void setOrder(Long order) {
		this.order = order;
	}
}
