package com.wonders.task.htxx.execute;

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

import com.wonders.task.asset.util.DateUtil;
import com.wonders.task.htxx.service.ContractHtxxService;
import com.wonders.task.sample.ITaskService;

@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@Service("htxxExecute")
@Scope("prototype")
public class HtxxExecute implements ITaskService{

	private ContractHtxxService contractHtxxService;
	@Autowired(required=false)
	public void setContractHtxxService(@Qualifier("contractHtxxService")ContractHtxxService contractHtxxService) {
		this.contractHtxxService = contractHtxxService;
	}


	/**
	 * 合同管理页面
	 */
	@Override
	public String exec(String param) {
		
		//写死页面上11个公司的id
		List<String> companyList = new ArrayList<String>();
		companyList.add("2541");		//运一公司
		companyList.add("2542");		//运二公司
		companyList.add("2543");		//运三公司
		companyList.add("2544");		//运四公司
		companyList.add("2540");		//运管中心
		companyList.add("2546");		//教育培训中心，新增的
		
		companyList.add("2718");		//车辆公司
		companyList.add("2719");		//供电公司
		companyList.add("2720");		//通号公司
		companyList.add("2721");		//工务公司
		companyList.add("2722");		//物资公司
		companyList.add("2545");		//维护保障中心
		
		companyList.add("center");		//维护中心,上面6家公司的数据加起来
		
		//1.先得到合同表中最早的签约时间,年份
		String earliestDateStr = this.contractHtxxService.findEarliestYearOfContractSignedDate();
		//earliestDateStr = "2006-01-01";			//使用该默认值，数据相对较准确
		earliestDateStr = "2013-01-01";
		Calendar first = Calendar.getInstance();		//最早的合同签约时间
		first.setTime(DateUtil.String2Date(earliestDateStr, "yyyy-MM-dd"));
		Calendar last = Calendar.getInstance();			//当前时间
		last.setTime(new Date());
		//1.计算全部
		while ((first.get(Calendar.YEAR)-1) != last.get(Calendar.YEAR)) {		//年不相等
			int currentYear = first.get(Calendar.YEAR);
			//计算合同采购方式、合同状态统计
			this.updateContractBySignedYearAndContractType(currentYear+"",null);
			
			//计算合同变更统计、计算合同支付统计
			this.updateContractByWholeYear(currentYear+"",null);
			
			first.set(Calendar.YEAR, (first.get(Calendar.YEAR)+1));		//年份+1
		}
		
		first.setTime(DateUtil.String2Date(earliestDateStr, "yyyy-MM-dd"));
		last.setTime(new Date());
		//2.计算指定公司下的数量
		for(int i=0; i<companyList.size(); i++){
			while ((first.get(Calendar.YEAR)-1) != last.get(Calendar.YEAR)) {		//年不相等
				int currentYear = first.get(Calendar.YEAR);
				//计算合同采购方式、合同状态统计
				this.updateContractBySignedYearAndContractType(currentYear+"",companyList.get(i));
				
				//计算合同变更统计、计算合同支付统计
				this.updateContractByWholeYear(currentYear+"",companyList.get(i));
				first.set(Calendar.YEAR, (first.get(Calendar.YEAR)+1));		//年份+1
			}
			first.setTime(DateUtil.String2Date(earliestDateStr, "yyyy-MM-dd"));
			last.setTime(new Date());
		}
		first.setTime(DateUtil.String2Date(earliestDateStr, "yyyy-MM-dd"));
		last.setTime(new Date());
		
		//3.计算其他
		while ((first.get(Calendar.YEAR)-1) != last.get(Calendar.YEAR)) {		//年不相等
			int currentYear = first.get(Calendar.YEAR);
			//计算合同采购方式、合同状态统计
			this.updateContractBySignedYearAndContractType(currentYear+"","other");
			
			//计算合同变更统计、计算合同支付统计
			this.updateContractByWholeYear(currentYear+"","other");
			
			first.set(Calendar.YEAR, (first.get(Calendar.YEAR)+1));		//年份+1
		}
		
		return "";
	}
	
	
	/**
	 * 合同采购方式轮循
	 * 合同分类contractType，建设类：1,运维类:2
	 * assignType ,1:采购方式,2:合同状态统计
	 */
	public void updateContractBySignedYearAndContractType(String controlYear,String companyId){
		try {
			if(controlYear==null || "".equals(controlYear)) return;
			//更新采购方式-运维类contractType='2,'
			contractHtxxService.updateContractAssingTypeBySignedDateAndConctractType("2","1",controlYear,companyId);
			
			//更新合同状态统计-运维类contractType='2,'
			contractHtxxService.updateContractAssingTypeBySignedDateAndConctractType("2","2",controlYear,companyId);
		} catch (Exception e) {
			System.out.println("updateContractBySignedYearAndContractType方法错误");
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * 
	 * 合同分类contractType，建设类：1,运维类:2
	 * assignType ,1:合同变更，2:合同实际支付、计划支付
	 */
	public void updateContractByWholeYear(String controlYear,String companyId){
		//更新变更情况,
		this.contractHtxxService.updateContractChangeOrPay("2", "1", controlYear,companyId);
		this.contractHtxxService.updateContractChangeOrPay("2", "2", controlYear,companyId);
	}
	
	
}
