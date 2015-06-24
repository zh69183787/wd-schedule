package com.wonders.task.investCost.report.service;

import java.util.List;

import com.wonders.task.investCost.report.bo.DwContractChange;
import com.wonders.task.investCost.report.bo.DwContractDestoryNumber;
import com.wonders.task.investCost.report.bo.DwProject;

public interface ReportService {

	//合同销号
	public void saveAll(List<DwContractDestoryNumber> list);
	public void deleteAllDestoryNumber();
	
	//查询所有线路
	public List<Object> findAllLine();
	public List<DwProject> setReport1ByYear(int year,List<Object> lineNameList);
	
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
	
	//找到所有dwContractChange的数据
	public List<DwContractChange> setDwContractChange(List<Object[]> list);
	//保存报表7数据
	public void saveAlLDwContractChange(List<DwContractChange> list);
}
