package com.wonders.task.htxx.model.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "DW_CONTRACT_P_BASEINFO")
public class DwContractBaseinfo {

	private String id;
	private String type;
	private String projectCountCurrentMonth;
	private String projectMoneyCurrentMonth;
	private String projectCountAll;
	private String projectMoneyAll;
	private String needToCompleteProject;
	private String contractCountCurrentMonth;
	private String contractMoneyCurrentMonth;
	private String contractCountAll;
	private String contractMoneyAll;
	private String needToCompleteContract;
	
	@Id
	@GeneratedValue(generator="system.uuid")
	@GenericGenerator(name="system.uuid",strategy="uuid")
	@Column(name="ID",length=40,nullable=false)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name="TYPE",nullable=true,length=2)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Column(name="PROJECT_COUNT_CURRENT_MONTH",nullable=true,length=100)
	public String getProjectCountCurrentMonth() {
		return projectCountCurrentMonth;
	}
	public void setProjectCountCurrentMonth(String projectCountCurrentMonth) {
		this.projectCountCurrentMonth = projectCountCurrentMonth;
	}
	
	@Column(name="PROJECT_MONEY_CURRENT_MONTH",nullable=true,length=100)
	public String getProjectMoneyCurrentMonth() {
		return projectMoneyCurrentMonth;
	}
	public void setProjectMoneyCurrentMonth(String projectMoneyCurrentMonth) {
		this.projectMoneyCurrentMonth = projectMoneyCurrentMonth;
	}
	
	@Column(name="PROJECT_COUNT_ALL",nullable=true,length=100)
	public String getProjectCountAll() {
		return projectCountAll;
	}
	public void setProjectCountAll(String projectCountAll) {
		this.projectCountAll = projectCountAll;
	}
	
	@Column(name="PROJECT_MONEY_ALL",nullable=true,length=100)
	public String getProjectMoneyAll() {
		return projectMoneyAll;
	}
	public void setProjectMoneyAll(String projectMoneyAll) {
		this.projectMoneyAll = projectMoneyAll;
	}
	@Column(name="NEED_TO_COMPLETE_PROJECT",nullable=true,length=40)
	public String getNeedToCompleteProject() {
		return needToCompleteProject;
	}
	public void setNeedToCompleteProject(String needToCompleteProject) {
		this.needToCompleteProject = needToCompleteProject;
	}
	
	@Column(name="CONTRACT_COUNT_CURRENT_MONTH",nullable=true,length=100)
	public String getContractCountCurrentMonth() {
		return contractCountCurrentMonth;
	}
	public void setContractCountCurrentMonth(String contractCountCurrentMonth) {
		this.contractCountCurrentMonth = contractCountCurrentMonth;
	}
	
	@Column(name="CONTRACT_MONEY_CURRENT_MONTH",nullable=true,length=100)
	public String getContractMoneyCurrentMonth() {
		return contractMoneyCurrentMonth;
	}
	public void setContractMoneyCurrentMonth(String contractMoneyCurrentMonth) {
		this.contractMoneyCurrentMonth = contractMoneyCurrentMonth;
	}
	
	@Column(name="CONTRACT_COUNT_ALL",nullable=true,length=100)
	public String getContractCountAll() {
		return contractCountAll;
	}
	public void setContractCountAll(String contractCountAll) {
		this.contractCountAll = contractCountAll;
	}
	
	@Column(name="CONTRACT_MONEY_ALL",nullable=true,length=100)
	public String getContractMoneyAll() {
		return contractMoneyAll;
	}
	public void setContractMoneyAll(String contractMoneyAll) {
		this.contractMoneyAll = contractMoneyAll;
	}
	
	@Column(name="NEED_TO_COMPLETE_CONTRACT",nullable=true,length=40)
	public String getNeedToCompleteContract() {
		return needToCompleteContract;
	}
	public void setNeedToCompleteContract(String needToCompleteContract) {
		this.needToCompleteContract = needToCompleteContract;
	}
	
	
}
