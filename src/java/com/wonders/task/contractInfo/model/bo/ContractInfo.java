/**
 * 
 */
package com.wonders.task.contractInfo.model.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/** 
 * @ClassName: DW_CONTRACT_INFO 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-5-12 上午9:16:13 
 *  
 */

@Entity
@Table(name = "DW_CONTRACT_INFO")
public class ContractInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4297845190219412451L;

	
	private String id;
	private String type;
	private String processCountMonth = "0";
	private String processCountYear= "0";
	private String processCountTotal= "0";
	private String changeCountMonth= "0";
	private String changeCountYear= "0";
	private String changeCountTotal= "0";
	private String changeMoneyMonth= "0";
	private String changeMoneyYear= "0";
	private String changeMoneyTotal= "0";
	private String changePercentMonth= "0";
	private String changePercentYear= "0";
	private String changePercentTotal= "0";
	private String payPlanMoneyMonth= "0";
	private String payPlanMoneyYear= "0";
	private String payPlanMoneyTotal= "0";
	private String payPlanPercentMonth= "0";
	private String payPlanPercentYear= "0";
	private String payPlanPercentTotal= "0";
	private String payActualMoneyMonth= "0";
	private String payActualMoneyYear= "0";
	private String payActualMoneyTotal= "0";
	private String payActualPercentMonth= "0";
	private String payActualPercentYear= "0";
	private String payActualPercentTotal= "0";
	private String operateTime;
	private String statDate;
	private String contractId;
	private String projectNo;
	private String projectName;
	/**
	 * @return the id
	 */
	
	public ContractInfo(){
		this.operateTime =  new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());	
	}
	
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
	 * @return the type
	 */
	
	@Column(name = "TYPE", nullable = true, length = 5)
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the processCountMonth
	 */
	
	@Column(name = "PROCESS_COUNT_MONTH", nullable = true, length = 50)
	public String getProcessCountMonth() {
		return processCountMonth;
	}
	/**
	 * @param processCountMonth the processCountMonth to set
	 */
	public void setProcessCountMonth(String processCountMonth) {
		this.processCountMonth = processCountMonth;
	}
	/**
	 * @return the processCountYear
	 */
	@Column(name = "PROCESS_COUNT_YEAR", nullable = true, length = 50)
	public String getProcessCountYear() {
		return processCountYear;
	}
	/**
	 * @param processCountYear the processCountYear to set
	 */
	public void setProcessCountYear(String processCountYear) {
		this.processCountYear = processCountYear;
	}
	/**
	 * @return the processCountTotal
	 */
	@Column(name = "PROCESS_COUNT_TOTAL", nullable = true, length = 50)
	public String getProcessCountTotal() {
		return processCountTotal;
	}
	/**
	 * @param processCountTotal the processCountTotal to set
	 */
	public void setProcessCountTotal(String processCountTotal) {
		this.processCountTotal = processCountTotal;
	}
	/**
	 * @return the changeMoneyMonth
	 */
	@Column(name = "CHANGE_MONEY_MONTH", nullable = true, length = 50)
	public String getChangeMoneyMonth() {
		return changeMoneyMonth;
	}
	/**
	 * @param changeMoneyMonth the changeMoneyMonth to set
	 */
	public void setChangeMoneyMonth(String changeMoneyMonth) {
		this.changeMoneyMonth = changeMoneyMonth;
	}
	/**
	 * @return the changeMoneyYear
	 */
	
	@Column(name = "CHANGE_MONEY_YEAR", nullable = true, length = 50)
	public String getChangeMoneyYear() {
		return changeMoneyYear;
	}
	/**
	 * @param changeMoneyYear the changeMoneyYear to set
	 */
	public void setChangeMoneyYear(String changeMoneyYear) {
		this.changeMoneyYear = changeMoneyYear;
	}
	/**
	 * @return the changeMoneyTotal
	 */
	
	@Column(name = "CHANGE_MONEY_TOTAL", nullable = true, length = 50)
	public String getChangeMoneyTotal() {
		return changeMoneyTotal;
	}
	/**
	 * @param changeMoneyTotal the changeMoneyTotal to set
	 */
	public void setChangeMoneyTotal(String changeMoneyTotal) {
		this.changeMoneyTotal = changeMoneyTotal;
	}
	/**
	 * @return the changePercentMonth
	 */
	
	@Column(name = "CHANGE_PERCENT_MONTH", nullable = true, length = 50)
	public String getChangePercentMonth() {
		return changePercentMonth;
	}
	/**
	 * @param changePercentMonth the changePercentMonth to set
	 */
	public void setChangePercentMonth(String changePercentMonth) {
		this.changePercentMonth = changePercentMonth;
	}
	/**
	 * @return the changePercentYear
	 */
	@Column(name = "CHANGE_PERCENT_YEAR", nullable = true, length = 50)
	public String getChangePercentYear() {
		return changePercentYear;
	}
	/**
	 * @param changePercentYear the changePercentYear to set
	 */
	public void setChangePercentYear(String changePercentYear) {
		this.changePercentYear = changePercentYear;
	}
	/**
	 * @return the changePercentTotal
	 */
	@Column(name = "CHANGE_PERCENT_TOTAL", nullable = true, length = 50)
	public String getChangePercentTotal() {
		return changePercentTotal;
	}
	/**
	 * @param changePercentTotal the changePercentTotal to set
	 */
	public void setChangePercentTotal(String changePercentTotal) {
		this.changePercentTotal = changePercentTotal;
	}

	/**
	 * @return the operateTime
	 */
	@Column(name = "OPERATE_TIME", nullable = true, length = 50)
	public String getOperateTime() {
		return operateTime;
	}
	/**
	 * @param operateTime the operateTime to set
	 */
	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}
	/**
	 * @return the statDate
	 */
	
	@Column(name = "STAT_DATE", nullable = true, length = 50)
	public String getStatDate() {
		return statDate;
	}
	/**
	 * @param statDate the statDate to set
	 */
	public void setStatDate(String statDate) {
		this.statDate = statDate;
	}

	/**
	 * @return the contractId
	 */
	
	@Column(name = "CONTRACT_ID", nullable = true, length = 200)
	public String getContractId() {
		return contractId;
	}

	/**
	 * @param contractId the contractId to set
	 */
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	/**
	 * @return the changeCountMonth
	 */
	
	@Column(name = "CHANGE_COUNT_MONTH", nullable = true, length = 50)
	public String getChangeCountMonth() {
		return changeCountMonth;
	}

	/**
	 * @param changeCountMonth the changeCountMonth to set
	 */
	public void setChangeCountMonth(String changeCountMonth) {
		this.changeCountMonth = changeCountMonth;
	}

	/**
	 * @return the changeCountYear
	 */
	@Column(name = "CHANGE_COUNT_YEAR", nullable = true, length = 50)
	
	public String getChangeCountYear() {
		return changeCountYear;
	}

	/**
	 * @param changeCountYear the changeCountYear to set
	 */
	public void setChangeCountYear(String changeCountYear) {
		this.changeCountYear = changeCountYear;
	}

	/**
	 * @return the changeCountTotal
	 */
	@Column(name = "CHANGE_COUNT_TOTAL", nullable = true, length = 50)
	
	public String getChangeCountTotal() {
		return changeCountTotal;
	}

	/**
	 * @param changeCountTotal the changeCountTotal to set
	 */
	public void setChangeCountTotal(String changeCountTotal) {
		this.changeCountTotal = changeCountTotal;
	}

	/**
	 * @return the payPlanMoneyMonth
	 */
	
	@Column(name = "PAY_PLAN_MONEY_MONTH", nullable = true, length = 50)
	
	public String getPayPlanMoneyMonth() {
		return payPlanMoneyMonth;
	}

	/**
	 * @param payPlanMoneyMonth the payPlanMoneyMonth to set
	 */
	public void setPayPlanMoneyMonth(String payPlanMoneyMonth) {
		this.payPlanMoneyMonth = payPlanMoneyMonth;
	}

	/**
	 * @return the payPlanMoneyYear
	 */
	
	@Column(name = "PAY_PLAN_MONEY_YEAR", nullable = true, length = 50)
	
	public String getPayPlanMoneyYear() {
		return payPlanMoneyYear;
	}

	/**
	 * @param payPlanMoneyYear the payPlanMoneyYear to set
	 */
	public void setPayPlanMoneyYear(String payPlanMoneyYear) {
		this.payPlanMoneyYear = payPlanMoneyYear;
	}

	/**
	 * @return the payPlanMoneyTotal
	 */
	@Column(name = "PAY_PLAN_MONEY_TOTAL", nullable = true, length = 50)
	
	public String getPayPlanMoneyTotal() {
		return payPlanMoneyTotal;
	}

	/**
	 * @param payPlanMoneyTotal the payPlanMoneyTotal to set
	 */
	public void setPayPlanMoneyTotal(String payPlanMoneyTotal) {
		this.payPlanMoneyTotal = payPlanMoneyTotal;
	}

	/**
	 * @return the payPlanPercentMonth
	 */
	
	@Column(name = "PAY_PLAN_PERCENT_MONTH", nullable = true, length = 50)
	
	public String getPayPlanPercentMonth() {
		return payPlanPercentMonth;
	}

	/**
	 * @param payPlanPercentMonth the payPlanPercentMonth to set
	 */
	public void setPayPlanPercentMonth(String payPlanPercentMonth) {
		this.payPlanPercentMonth = payPlanPercentMonth;
	}

	/**
	 * @return the payPlanPercentYear
	 */
	@Column(name = "PAY_PLAN_PERCENT_YEAR", nullable = true, length = 50)
	public String getPayPlanPercentYear() {
		return payPlanPercentYear;
	}

	/**
	 * @param payPlanPercentYear the payPlanPercentYear to set
	 */
	
	public void setPayPlanPercentYear(String payPlanPercentYear) {
		this.payPlanPercentYear = payPlanPercentYear;
	}

	/**
	 * @return the payPlanPercentTotal
	 */
	@Column(name = "PAY_PLAN_PERCENT_TOTAL", nullable = true, length = 50)
	public String getPayPlanPercentTotal() {
		return payPlanPercentTotal;
	}

	/**
	 * @param payPlanPercentTotal the payPlanPercentTotal to set
	 */
	public void setPayPlanPercentTotal(String payPlanPercentTotal) {
		this.payPlanPercentTotal = payPlanPercentTotal;
	}

	/**
	 * @return the payActualMoneyMonth
	 */
	@Column(name = "PAY_ACTUAL_MONEY_MONTH", nullable = true, length = 50)
	public String getPayActualMoneyMonth() {
		return payActualMoneyMonth;
	}

	/**
	 * @param payActualMoneyMonth the payActualMoneyMonth to set
	 */
	public void setPayActualMoneyMonth(String payActualMoneyMonth) {
		this.payActualMoneyMonth = payActualMoneyMonth;
	}

	/**
	 * @return the payActualMoneyYear
	 */
	@Column(name = "PAY_ACTUAL_MONEY_YEAR", nullable = true, length = 50)
	public String getPayActualMoneyYear() {
		return payActualMoneyYear;
	}

	/**
	 * @param payActualMoneyYear the payActualMoneyYear to set
	 */
	public void setPayActualMoneyYear(String payActualMoneyYear) {
		this.payActualMoneyYear = payActualMoneyYear;
	}

	/**
	 * @return the payActualMoneyTotal
	 */
	@Column(name = "PAY_ACTUAL_MONEY_TOTAL", nullable = true, length = 50)
	public String getPayActualMoneyTotal() {
		return payActualMoneyTotal;
	}

	/**
	 * @param payActualMoneyTotal the payActualMoneyTotal to set
	 */
	public void setPayActualMoneyTotal(String payActualMoneyTotal) {
		this.payActualMoneyTotal = payActualMoneyTotal;
	}

	/**
	 * @return the payActualPercentMonth
	 */
	@Column(name = "PAY_ACTUAL_PERCENT_MONTH", nullable = true, length = 50)
	public String getPayActualPercentMonth() {
		return payActualPercentMonth;
	}

	/**
	 * @param payActualPercentMonth the payActualPercentMonth to set
	 */
	public void setPayActualPercentMonth(String payActualPercentMonth) {
		this.payActualPercentMonth = payActualPercentMonth;
	}

	/**
	 * @return the payActualPercentYear
	 */
	@Column(name = "PAY_ACTUAL_PERCENT_YEAR", nullable = true, length = 50)
	public String getPayActualPercentYear() {
		return payActualPercentYear;
	}

	/**
	 * @param payActualPercentYear the payActualPercentYear to set
	 */
	public void setPayActualPercentYear(String payActualPercentYear) {
		this.payActualPercentYear = payActualPercentYear;
	}

	/**
	 * @return the payActualPercentTotal
	 */
	@Column(name = "PAY_ACTUAL_PERCENT_TOTAL", nullable = true, length = 50)
	public String getPayActualPercentTotal() {
		return payActualPercentTotal;
	}

	/**
	 * @param payActualPercentTotal the payActualPercentTotal to set
	 */
	public void setPayActualPercentTotal(String payActualPercentTotal) {
		this.payActualPercentTotal = payActualPercentTotal;
	}

	/**
	 * @return the projectNo
	 */
	@Column(name = "PROJECT_NO", nullable = true, length = 200)	
	public String getProjectNo() {
		return projectNo;
	}

	/**
	 * @param projectNo the projectNo to set
	 */
	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}

	/**
	 * @return the projectName
	 */
	@Column(name = "PROJECT_NAME", nullable = true, length = 200)	
	public String getProjectName() {
		return projectName;
	}

	/**
	 * @param projectName the projectName to set
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	
	
}
