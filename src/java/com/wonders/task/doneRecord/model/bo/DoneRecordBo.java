package com.wonders.task.doneRecord.model.bo;

import com.wonders.task.todoItem.util.DateUtil;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


@Entity
@Table(name ="T_DONE_RECORD")
public class DoneRecordBo implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1437476948633768884L;
	private String id;
	private String pname;
	private String pincident;
	private String summary;
	private String taskUser;
	private String stepName;
	private String processStatus;
	private String trackStatus;
	private String operateTime;
	private String doneTime;
	private String updateTime;
	private String removed;
	private String type;
	private String orders;
	private String taskId;

	public DoneRecordBo() {
		this.updateTime = DateUtil.getCurrDate(DateUtil.TIME_FORMAT);
		this.removed = "0";
	}

	public DoneRecordBo(String id) {
		this.updateTime = DateUtil.getCurrDate(DateUtil.TIME_FORMAT);
		this.id = id;
	}

	public DoneRecordBo(String id, String pname, String pincident,
			String summary, String taskUser, String stepName,
			String processStatus, String trackStatus, String operateTime,
			String doneTime, String updateTime, String removed, String type,
			String orders, String taskId) {
		this.id = id;
		this.pname = pname;
		this.pincident = pincident;
		this.summary = summary;
		this.taskUser = taskUser;
		this.stepName = stepName;
		this.processStatus = processStatus;
		this.trackStatus = trackStatus;
		this.operateTime = operateTime;
		this.doneTime = doneTime;
		this.updateTime = updateTime;
		this.removed = removed;
		this.type = type;
		this.orders = orders;
		this.taskId = taskId;
		this.updateTime = DateUtil.getCurrDate(DateUtil.TIME_FORMAT);
	}

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	@Column(name = "ID")
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "PNAME", length = 50)
	public String getPname() {
		return this.pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	@Column(name = "PINCIDENT", length = 50)
	public String getPincident() {
		return this.pincident;
	}

	public void setPincident(String pincident) {
		this.pincident = pincident;
	}

	@Column(name = "SUMMARY", length = 1000)
	public String getSummary() {
		return this.summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@Column(name = "TASK_USER", length = 20)
	public String getTaskUser() {
		return this.taskUser;
	}

	public void setTaskUser(String taskUser) {
		this.taskUser = taskUser;
	}

	@Column(name = "STEP_NAME", length = 40)
	public String getStepName() {
		return this.stepName;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	@Column(name = "PROCESS_STATUS", length = 1)
	public String getProcessStatus() {
		return this.processStatus;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

	@Column(name = "TRACK_STATUS", length = 1)
	public String getTrackStatus() {
		return this.trackStatus;
	}

	public void setTrackStatus(String trackStatus) {
		this.trackStatus = trackStatus;
	}

	@Column(name = "OPERATE_TIME", length = 19)
	public String getOperateTime() {
		return this.operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

	@Column(name = "DONE_TIME", length = 19)
	public String getDoneTime() {
		return this.doneTime;
	}

	public void setDoneTime(String doneTime) {
		this.doneTime = doneTime;
	}

	@Column(name = "UPDATE_TIME", length = 19)
	public String getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "REMOVED", length = 1)
	public String getRemoved() {
		return this.removed;
	}

	public void setRemoved(String removed) {
		this.removed = removed;
	}

	@Column(name = "TYPE", length = 100)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "ORDERS", length = 50)
	public String getOrders() {
		return this.orders;
	}

	public void setOrders(String orders) {
		this.orders = orders;
	}

	@Column(name = "TASK_ID", length = 50)
	public String getTaskId() {
		return this.taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
}
