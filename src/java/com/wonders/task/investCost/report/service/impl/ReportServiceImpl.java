package com.wonders.task.investCost.report.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.digester.ObjectParamRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import com.wonders.task.investCost.contract.model.bo.Contract;
import com.wonders.task.investCost.report.bo.DwContractChange;
import com.wonders.task.investCost.report.bo.DwContractDestoryNumber;
import com.wonders.task.investCost.report.bo.DwProject;
import com.wonders.task.investCost.report.bo.Project;
import com.wonders.task.investCost.report.dao.ReportDao;
import com.wonders.task.investCost.report.service.ReportService;

@Repository("reportService")
public class ReportServiceImpl implements ReportService{

	
	private ReportDao reportDao;
	
	public ReportDao getReportDao() {
		return reportDao;
	}

	@Autowired(required=false)
	public void setReportDao(@Qualifier(value="reportDao")ReportDao reportDao) {
		this.reportDao = reportDao;
	}

	@Override
	public void saveAll(List<DwContractDestoryNumber> list) {
		reportDao.saveAll(list);
	}

	@Override
	public void deleteAllDestoryNumber() {
		reportDao.deleteAllDestoryNumber();
	}

	@Override
	public List<Object> findAllLine() {
		
		return reportDao.findAllLine();
	}

	@Override
	public List<DwProject> setReport1ByYear(int year,List<Object> lineNameList) {
		if(lineNameList==null || lineNameList.size()<1 ) return null;
		if(year<2012) return null;
		List<DwProject> resultList = new ArrayList<DwProject>();
		
		for(int i=0,len=lineNameList.size(); i<len; i++){		//循环线路名称
			DwProject dwProject = new DwProject();		//每个线路,6个专业分类一条数据
			dwProject.setYear(year+"");
			dwProject.setCreateDate(new Date());
			dwProject.setLineName(lineNameList.get(i).toString());
			dwProject.setOrder(Long.valueOf(i+1));
			for(int j=1; j<=6; j++){			//循环项目-专业分类1-6
				List<Project> projectList = reportDao.findProjectByYearAndLineAndType(year, lineNameList.get(i).toString(), j+"");
				if(projectList!=null){		//改线路、专业分类下有数据
					double result = 0.0;
					for(int k=0;k<projectList.size(); k++){		//循环项目list,得到资金来源的总数
						if(projectList.get(k).getProjectMoneysource()!=null && !"".equals(projectList.get(k).getProjectMoneysource())){
							JSONObject moneySourceObject = JSONObject.fromObject(projectList.get(k).getProjectMoneysource());
							//JSONArray jsonArray = JSONArray.fromObject(projectList.get(k).getProjectMoneysource());
							JSONArray jsonArray = moneySourceObject.getJSONArray("moneySource");
							if(jsonArray!=null){
								for(int l=0;l<jsonArray.size(); l++){
									JSONObject jsonObject = (JSONObject) jsonArray.get(l);
									Object lineNameObject = (String) jsonObject.get("lineName");
									if(lineNameObject!=null && lineNameObject.toString().equals(lineNameList.get(i).toString())){	//线路名称一致
										Object moneyObject = jsonObject.getDouble("money");
										if(moneyObject!=null){
											result += Double.valueOf(moneyObject.toString());
										}
									}
									
								}
							}
						}
					}
					if(result!=0.0){
						switch (j) {
						case 1:
							dwProject.setType1(result+"");
							break;
						case 2:
							dwProject.setType2(result+"");
							break;
						case 3:
							dwProject.setType3(result+"");
							break;
						case 4:
							dwProject.setType4(result+"");
							break;
						case 5:
							dwProject.setType5(result+"");
							break;
						case 6:
							dwProject.setType6(result+"");
							break;
						
						}
					}
				}
			}
			resultList.add(dwProject);
		}
		return resultList;
	}

	@Override
	public void saveAllDwProject(List<DwProject> list) {
		reportDao.saveAllDwProject(list);
	}

	@Override
	public void deleteAllDwProject() {
		reportDao.deleteAllDwProject();
	}

	@Override
	public void deleteAllDwProjectApprove() {
		reportDao.deleteAllDwProjectApprove();
	}

	@Override
	public void deleteDwContractChange() {
		reportDao.deleteDwContractChange();
	}

	@Override
	public List<Object[]> findAllContractBySignedDateAndProjectType() {
		return reportDao.findAllContractBySignedDateAndProjectType();
	}

	@Override
	public List<DwContractChange> setDwContractChange(List<Object[]> list) {
		if(list==null || list.size()<1) return null;
		List<DwContractChange> dwList = new ArrayList<DwContractChange>();
		for(int i=0,len = list.size(); i<len ; i++){
			Object[] object = list.get(i);
			if(object.length!=8) continue;		//查了7个字段，如不是的话就跳出本次循环
			DwContractChange change = new DwContractChange();
			change.setProjectNo(object[0]==null?"":object[0].toString());
			change.setProjectName(object[1]==null?"":object[1].toString());
			if(object[2]!=null){
				if(object[2].toString().equals("1")){
					change.setProjectType("1");  //外部项目
				}else if("2".equals(object[2].toString()) || "3".equals(object[2].toString()) || "4".equals(object[2].toString())){
					change.setProjectType("2");	//内部项目
				}
			}
			change.setContractId(object[3]==null?"":object[3].toString());
			change.setContractName(object[4]==null?"":object[4].toString());
			change.setContractPrice(object[5]==null?"":object[5].toString());
			change.setSignedDate(object[6]==null?"":object[6].toString());
			change.setPartyBCompany(object[7]==null?"":object[7].toString());
			change.setCreateDate(new Date());
			List<Object[]> changeList = reportDao.findContractChange(change.getContractId());
			if(changeList==null || changeList.size()<1){
				change.setChangeCounts(0);
			}else{
				change.setChangeCounts(changeList.size());
				double changeMoney = 0.0;
				String reason = "";
				for(int j=0;j<changeList.size(); j++){
					changeMoney += Double.valueOf(changeList.get(j)[0].toString());
					reason += changeList.get(j)==null?"":changeList.get(j)[1].toString()+System.getProperty("line.separator");
				}
				change.setChangeMoney(changeMoney+"");
				change.setChangeReason(reason);
			}
			dwList.add(change);
			
		}
		return dwList;
	}

	@Override
	public void saveAlLDwContractChange(List<DwContractChange> list) {
		reportDao.saveAlLDwContractChange(list);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
}
