/**
 * 
 */
package com.wonders.task.workflow.model.flow.vo;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.wonders.task.workflow.model.flow.bo.DiscardAssetBo;


/** 
 * @ClassName: DiscardMainVo 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:33:43 
 *  
 */
@XmlRootElement(name="DiscardMainVo")
@XmlAccessorType(XmlAccessType.FIELD)
public class DiscardMainVo {
	private String id;
	private String indexNum;
	private String projectId;
	private String projectName;
	private String projectNo;
	private String dispatchNo;
	private String startDate;
	private String finishDate;
	private String finishPrice;
	private String approvalBudget;
	private String payType;			//成本属性
	private String moneySource;	
	private String attach;
	
	private String operator;
	private String operatorName;
	private String operatorMobile;

	private String remark;
	private String flag = "0";
	
	private String status;
	
	private List<DiscardAssetBo> assets = new ArrayList<DiscardAssetBo>();
	/*
	 * 部门领导
	 */
	private String deptLeader;
	
	/*
	 * 操作选项
	 * */
	private String choice = "";
	private String steplabel = "";
	private String suggest = "";
	private String attachId = "";
	
	public String getProjectId() {
		return projectId;
	}


	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}


	public String getProjectName() {
		return projectName;
	}


	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectNo() {
		return projectNo;
	}

	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}

	/**
	 * @return the choice
	 */
	public String getChoice() {
		return choice;
	}
	/**
	 * @param choice the choice to set
	 */
	public void setChoice(String choice) {
		this.choice = choice;
	}

	/**
	 * @return the steplabel
	 */
	public String getSteplabel() {
		return steplabel;
	}
	/**
	 * @param steplabel the steplabel to set
	 */
	public void setSteplabel(String steplabel) {
		this.steplabel = steplabel;
	}
	/**
	 * @return the suggest
	 */
	public String getSuggest() {
		return suggest;
	}
	/**
	 * @param suggest the suggest to set
	 */
	public void setSuggest(String suggest) {
		this.suggest = suggest;
	}

	/**
	 * @return the attach
	 */
	public String getAttach() {
		return attach;
	}
	/**
	 * @param attach the attach to set
	 */
	public void setAttach(String attach) {
		this.attach = attach;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getDeptLeader() {
		return deptLeader;
	}


	public void setDeptLeader(String deptLeader) {
		this.deptLeader = deptLeader;
	}


	public String getDispatchNo() {
		return dispatchNo;
	}


	public void setDispatchNo(String dispatchNo) {
		this.dispatchNo = dispatchNo;
	}


	public String getStartDate() {
		return startDate;
	}


	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}


	public String getFinishDate() {
		return finishDate;
	}


	public void setFinishDate(String finishDate) {
		this.finishDate = finishDate;
	}


	public String getFinishPrice() {
		return finishPrice;
	}


	public void setFinishPrice(String finishPrice) {
		this.finishPrice = finishPrice;
	}


	public String getApprovalBudget() {
		return approvalBudget;
	}


	public void setApprovalBudget(String approvalBudget) {
		this.approvalBudget = approvalBudget;
	}


	public String getMoneySource() {
		return moneySource;
	}


	public void setMoneySource(String moneySource) {
		this.moneySource = moneySource;
	}


	public String getOperatorMobile() {
		return operatorMobile;
	}


	public void setOperatorMobile(String operatorMobile) {
		this.operatorMobile = operatorMobile;
	}


	public String getOperator() {
		return operator;
	}


	public void setOperator(String operator) {
		this.operator = operator;
	}


	public String getOperatorName() {
		return operatorName;
	}


	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}


	public String getAttachId() {
		return attachId;
	}


	public void setAttachId(String attachId) {
		this.attachId = attachId;
	}

	public String getIndexNum() {
		return indexNum;
	}

	public void setIndexNum(String indexNum) {
		this.indexNum = indexNum;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public List<DiscardAssetBo> getAssets() {
		return assets;
	}

	public void setAssets(List<DiscardAssetBo> assets) {
		this.assets = assets;
	}

	public double getAssetCostSum(){
		double oriVal = 0;
		if(assets != null && assets.size() > 0){
			for(DiscardAssetBo asset : assets){
				if("费用性".equals(asset.getCostType())){
					try{
						oriVal += Double.valueOf(asset.getOriginalValue());					
					}catch (NumberFormatException e) {}					
				}
			}
		}
		return oriVal;
	}
	
	public double getAssetCapitalSum(){
		double oriVal = 0;
		if(assets != null && assets.size() > 0){
			for(DiscardAssetBo asset : assets){
				if("资本性".equals(asset.getCostType())){
					try{
						oriVal += Double.valueOf(asset.getOriginalValue());					
					}catch (NumberFormatException e) {}					
				}
			}
		}
		return oriVal;
	}

	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}
	
}
