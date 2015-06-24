/**
 * 
 */
package com.wonders.task.workflow.model.flow.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/** 
 * @ClassName: ReviewMainVo 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:33:43 
 *  
 */

@XmlRootElement(name="ReviewMainVo")
@XmlAccessorType(XmlAccessType.FIELD)
public class ReviewMainVo {
	private String id;
	private String projectName;
	private String projectIdentifier;
	private String projectNum;
	private String projectCompany;
	private String projectCharge;
	private String projectChargeDept;
	private String contractIdentifier;
	private String contractSelfNum;
	private String contractName;
	private String contractMoney;
	private String contractMoneyType;
	private String contractBudget;
	private String purchaseType;
	private String contractType1;
	private String contractType2;
	private String contractType;
	private String contractGroup;
	private String oppositeCompany;
	private String dealPerson;
	private String passTime;
	private String signTime;
	private String execPeriodStart;
	private String execPeriodEnd;
	private String dealDeptSuggest;
	private String remark;
	private String attach;
	private String projectId;
	private String contractType1Id;
	private String contractType2Id;
	private String contractGroupId;
	private String purchaseTypeId;
	private String contractMoneyTypeId;
	private String regPerson;
	private String moneySource;
	
	private String deptLeader;

    private String budgetType;
    private String budgetTypeCode;
    private String statType;

	/*
	 * 操作选项
	 * */
	private String choice = "";
	private String attachId = "";
	private String steplabel = "";
	private String suggest = "";

    public String getBudgetType() {
        return budgetType;
    }

    public void setBudgetType(String budgetType) {
        this.budgetType = budgetType;
    }

    public String getBudgetTypeCode() {
        return budgetTypeCode;
    }

    public void setBudgetTypeCode(String budgetTypeCode) {
        this.budgetTypeCode = budgetTypeCode;
    }

    public String getStatType() {
        return statType;
    }

    public void setStatType(String statType) {
        this.statType = statType;
    }

    public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectIdentifier() {
		return projectIdentifier;
	}
	public void setProjectIdentifier(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}
	public String getProjectNum() {
		return projectNum;
	}
	public void setProjectNum(String projectNum) {
		this.projectNum = projectNum;
	}
	public String getProjectCompany() {
		return projectCompany;
	}
	public void setProjectCompany(String projectCompany) {
		this.projectCompany = projectCompany;
	}
	public String getProjectCharge() {
		return projectCharge;
	}
	public void setProjectCharge(String projectCharge) {
		this.projectCharge = projectCharge;
	}
	public String getProjectChargeDept() {
		return projectChargeDept;
	}
	public void setProjectChargeDept(String projectChargeDept) {
		this.projectChargeDept = projectChargeDept;
	}
	public String getContractIdentifier() {
		return contractIdentifier;
	}
	public void setContractIdentifier(String contractIdentifier) {
		this.contractIdentifier = contractIdentifier;
	}
	public String getContractSelfNum() {
		return contractSelfNum;
	}
	public void setContractSelfNum(String contractSelfNum) {
		this.contractSelfNum = contractSelfNum;
	}
	public String getContractName() {
		return contractName;
	}
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	public String getContractMoney() {
		return contractMoney;
	}
	public void setContractMoney(String contractMoney) {
		this.contractMoney = contractMoney;
	}
	public String getContractMoneyType() {
		return contractMoneyType;
	}
	public void setContractMoneyType(String contractMoneyType) {
		this.contractMoneyType = contractMoneyType;
	}
	public String getContractBudget() {
		return contractBudget;
	}
	public void setContractBudget(String contractBudget) {
		this.contractBudget = contractBudget;
	}
	public String getPurchaseType() {
		return purchaseType;
	}
	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}
	public String getContractType1() {
		return contractType1;
	}
	public void setContractType1(String contractType1) {
		this.contractType1 = contractType1;
	}
	public String getContractType2() {
		return contractType2;
	}
	public void setContractType2(String contractType2) {
		this.contractType2 = contractType2;
	}
	public String getContractType() {
		return contractType;
	}
	public void setContractType(String contractType) {
		this.contractType = contractType;
	}
	public String getContractGroup() {
		return contractGroup;
	}
	public void setContractGroup(String contractGroup) {
		this.contractGroup = contractGroup;
	}
	public String getOppositeCompany() {
		return oppositeCompany;
	}
	public void setOppositeCompany(String oppositeCompany) {
		this.oppositeCompany = oppositeCompany;
	}
	public String getDealPerson() {
		return dealPerson;
	}
	public void setDealPerson(String dealPerson) {
		this.dealPerson = dealPerson;
	}
	public String getPassTime() {
		return passTime;
	}
	public void setPassTime(String passTime) {
		this.passTime = passTime;
	}
	public String getSignTime() {
		return signTime;
	}
	public void setSignTime(String signTime) {
		this.signTime = signTime;
	}
	public String getExecPeriodStart() {
		return execPeriodStart;
	}
	public void setExecPeriodStart(String execPeriodStart) {
		this.execPeriodStart = execPeriodStart;
	}
	public String getExecPeriodEnd() {
		return execPeriodEnd;
	}
	public void setExecPeriodEnd(String execPeriodEnd) {
		this.execPeriodEnd = execPeriodEnd;
	}
	public String getDealDeptSuggest() {
		return dealDeptSuggest;
	}
	public void setDealDeptSuggest(String dealDeptSuggest) {
		this.dealDeptSuggest = dealDeptSuggest;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getContractType1Id() {
		return contractType1Id;
	}
	public void setContractType1Id(String contractType1Id) {
		this.contractType1Id = contractType1Id;
	}
	public String getContractType2Id() {
		return contractType2Id;
	}
	public void setContractType2Id(String contractType2Id) {
		this.contractType2Id = contractType2Id;
	}
	public String getContractGroupId() {
		return contractGroupId;
	}
	public void setContractGroupId(String contractGroupId) {
		this.contractGroupId = contractGroupId;
	}
	public String getPurchaseTypeId() {
		return purchaseTypeId;
	}
	public void setPurchaseTypeId(String purchaseTypeId) {
		this.purchaseTypeId = purchaseTypeId;
	}
	public String getContractMoneyTypeId() {
		return contractMoneyTypeId;
	}
	public void setContractMoneyTypeId(String contractMoneyTypeId) {
		this.contractMoneyTypeId = contractMoneyTypeId;
	}
	public String getRegPerson() {
		return regPerson;
	}
	public void setRegPerson(String regPerson) {
		this.regPerson = regPerson;
	}
	public String getDeptLeader() {
		return deptLeader;
	}
	public void setDeptLeader(String deptLeader) {
		this.deptLeader = deptLeader;
	}
	public String getChoice() {
		return choice;
	}
	public void setChoice(String choice) {
		this.choice = choice;
	}
	public String getAttachId() {
		return attachId;
	}
	public void setAttachId(String attachId) {
		this.attachId = attachId;
	}
	public String getSteplabel() {
		return steplabel;
	}
	public void setSteplabel(String steplabel) {
		this.steplabel = steplabel;
	}
	public String getSuggest() {
		return suggest;
	}
	public void setSuggest(String suggest) {
		this.suggest = suggest;
	}
	public String getMoneySource() {
		return moneySource;
	}
	public void setMoneySource(String moneySource) {
		this.moneySource = moneySource;
	}

	
}
