package com.wonders.task.investCost.report.dao;

import java.util.List;

import com.wonders.task.investCost.contract.model.bo.Contract;
import com.wonders.task.investCost.report.bo.DwContractChange;
import com.wonders.task.investCost.report.bo.DwContractDestoryNumber;
import com.wonders.task.investCost.report.bo.DwProject;
import com.wonders.task.investCost.report.bo.Project;

public interface ReportDao {

	//合同销号
	public void saveAll(List<DwContractDestoryNumber> list);
	public void deleteAllDestoryNumber();
	
	//查询所有线路
	public List<Object> findAllLine();
	
	//根据年份、线路、专业分类查询项目
	public List<Project> findProjectByYearAndLineAndType(int year,String lineName,String type);
	
	//保存报表1数据
	public void saveAllDwProject(List<DwProject> list);
	
	//删除报表1数据
	public void deleteAllDwProject();
	
	//删除报表4数据
	public void deleteAllDwProjectApprove();
	
	//删除报表7的数据
	public void deleteDwContractChange();
	
	//找到所有有签约合同时间，且合同的项目有项目类型的合同数据
	public List<Object[]> findAllContractBySignedDateAndProjectType();
	//查询合同变更
	public List<Object[]> findContractChange(String contractId);
	
	//保存报表7数据
	public void saveAlLDwContractChange(List<DwContractChange> list);
}
