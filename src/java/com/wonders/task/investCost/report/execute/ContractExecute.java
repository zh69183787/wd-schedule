package com.wonders.task.investCost.report.execute;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wonders.task.investCost.contract.service.ContractService;
import com.wonders.task.investCost.report.bo.DwContractChange;
import com.wonders.task.investCost.report.bo.DwContractDestoryNumber;
import com.wonders.task.investCost.report.bo.DwProject;
import com.wonders.task.investCost.report.service.ReportService;
import com.wonders.task.sample.ITaskService;

@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@Service("investCostReportExecute")
@Scope("prototype")
public class ContractExecute implements ITaskService{
	private ContractService contractService; 
	private ReportService reportService;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	@Autowired(required=false)
	public void setContractService(@Qualifier(value="contractService")ContractService contractService) {
		this.contractService = contractService;
	}
	
	@Autowired(required=false)
	public void setReportService(@Qualifier(value="reportService")ReportService reportService) {
		this.reportService = reportService;
	}


	@Override
	public String exec(String param) {
		setReport5();
		setReport1();
		setReport7();
		return "";
	}
	
	/**
	 * 报表5的数据
	 */
	public void setReport5(){
		reportService.deleteAllDestoryNumber();
		List<Object[]> dataList = contractService.findAllContractForDestoryNumber();
		List<DwContractDestoryNumber> saveList = null;
		if(dataList!=null && dataList.size()>0){
			saveList = new ArrayList<DwContractDestoryNumber>();
			for(int i=0,len = dataList.size(); i<len ; i++){
				DwContractDestoryNumber ob = setValue(dataList.get(i));
				if(ob!=null){
					saveList.add(ob);
				}
			}
			if(saveList!=null && saveList.size()>0){
				reportService.saveAll(saveList);
			}
		}
	}
	
	public DwContractDestoryNumber setValue(Object[] value){
		if(value==null) return null;
		if(value.length!=14) return null;
		if(value[0]==null || "".equals(value[0])) return null;
		Double changedMoney = contractService.findChangedMoneyByContractId(value[0].toString());
		Double contractPrice = value[4]==null?null:Double.valueOf(value[4].toString());
		double price = 0;
		if(contractPrice!=null){
			price += contractPrice;
		}
		if(changedMoney!=null){
			price += changedMoney;
		}
		DwContractDestoryNumber cdm = new DwContractDestoryNumber();
		cdm.setContractId(value[0].toString());
		cdm.setProjectId(value[9].toString());
		cdm.setContractName(value[1]==null?"":value[1].toString().trim());
		cdm.setContractNo(value[2]==null?"":value[2].toString().trim());
		cdm.setSupplier(value[3]==null?"":value[3].toString().trim());
		cdm.setContractPrice(price);
		String finalPrice ="";
		if(value[7]!=null && !"".equals(value[7])){
			finalPrice = value[7].toString().trim();
		}
		if(value[6]!=null && !"".equals(value[6])){
			finalPrice = value[6].toString().trim();
		}
		if(value[5]!=null && !"".equals(value[5])){
			finalPrice = value[5].toString().trim();
		}
		if(!"".equals(finalPrice)){
			cdm.setFinalPrice(Double.valueOf(finalPrice));
		}
		cdm.setContractStatus(value[8]==null?"":value[8].toString().trim());
		cdm.setProjectName(value[10]==null?"":value[10].toString().trim());
		cdm.setProjectNo(value[11]==null?"":value[11].toString().trim());
		cdm.setProjectType(value[12]==null?"":value[12].toString().trim());
		cdm.setContractSignedDate(value[13]==null?"":value[13].toString().trim());
		cdm.setCreateDate(new Date());
		
		return cdm;
	}

	/**
	 * 报表1的数据
	 * @throws ParseException 
	 */
	public void setReport1(){
		reportService.deleteAllDwProject();
		List<Object> allLine = reportService.findAllLine(); 	//所有线路
		//数据从2012年开始
		Date start;
		try {
			start = sdf.parse("2012-01-01");
			Calendar startYear = Calendar.getInstance();
			startYear.setTime(start);
			
			Calendar endYear = Calendar.getInstance();
			endYear.setTime(new Date());
			List<DwProject> list = new ArrayList<DwProject>();
			if(startYear.get(Calendar.YEAR) != endYear.get(Calendar.YEAR)){
				int sYear = startYear.get(Calendar.YEAR);
				while (sYear-1!=endYear.get(Calendar.YEAR)) {
					list.addAll(reportService.setReport1ByYear(sYear,allLine));
					sYear++;
				}
			}else{
				list = reportService.setReport1ByYear(startYear.get(Calendar.YEAR),allLine);
			}
			if(list!=null){
				reportService.saveAllDwProject(list);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 报表7的数据
	 */
	public void setReport7(){
		reportService.deleteDwContractChange();
		List<Object[]> contractObjecttList = reportService.findAllContractBySignedDateAndProjectType();
		List<DwContractChange> dwList = reportService.setDwContractChange(contractObjecttList);
		reportService.saveAlLDwContractChange(dwList);
		
	}
	
	
	
}
