package com.wonders.task.investCost.contract.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.wonders.task.investCost.contract.model.bo.Contract;
import com.wonders.task.investCost.contract.model.bo.HtXx;
import com.wonders.task.investCost.contract.service.HtBaService;

public class HtXxUtil {

	private HtBaService htBaService;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	@Autowired(required=false)
	public void setHtBaService(@Qualifier(value="htBaService")HtBaService htBaService) {
		this.htBaService = htBaService;
	}


	public static List<HtXx> convertObjectArrayToHtXxList(List<Object[]> objList){
		if(objList!=null && objList.size()>0){
			List<HtXx> htxxList = new ArrayList<HtXx>();
			String[] array = new String[200000];
			int num=0;
			for(int i=0; i<objList.size(); i++){
				Object[] currentObj = objList.get(i);
				if(currentObj!=null && currentObj.length==50){
					HtXx currentHtXx = new HtXx();
					currentHtXx.setId(currentObj[0]!=null ? Long.valueOf(currentObj[0].toString()) : null);
					currentHtXx.setPlanNum(currentObj[1]!=null ? currentObj[1].toString() : "");
					currentHtXx.setContractName(currentObj[2]!=null ? currentObj[2].toString():"");
					currentHtXx.setContractName1(currentObj[3]!=null ? currentObj[3].toString():"");
					currentHtXx.setProjectCoName(currentObj[4]!= null ? currentObj[4].toString():"");
					currentHtXx.setContractNum(currentObj[5]!= null ? currentObj[5].toString():"");
					currentHtXx.setProjectNum(currentObj[6]!= null ? currentObj[6].toString():"");
					currentHtXx.setContractMoney(currentObj[7]!= null ? currentObj[7].toString():"");
					currentHtXx.setDealPerson(currentObj[8]!= null ? currentObj[8].toString():"");
					currentHtXx.setDealDepSuggest(currentObj[9]!= null ? currentObj[9].toString():"");
					currentHtXx.setCurrentLink(currentObj[10]!= null ? currentObj[10].toString():"");
					currentHtXx.setAddPerson(currentObj[11]!= null ? currentObj[11].toString():"");
					currentHtXx.setAddTime(currentObj[12]!= null ? currentObj[12].toString():"");
					currentHtXx.setTotalDate(currentObj[13]!= null ? currentObj[13].toString():"");
					currentHtXx.setCurrentSj2(currentObj[14]!= null ? currentObj[14].toString():"");
					currentHtXx.setCurrentSj(currentObj[15]!= null ? currentObj[15].toString():"");
					currentHtXx.setRemark(currentObj[16]!= null ? currentObj[16].toString():"");
					currentHtXx.setNextDep(currentObj[17]!= null ? currentObj[17].toString():"");
					currentHtXx.setNextPerson(currentObj[18]!= null ? currentObj[18].toString():"");
					currentHtXx.setContractStage(currentObj[19]!= null ? currentObj[19].toString():"");
					currentHtXx.setDealState(currentObj[20]!= null ? currentObj[20].toString():"");
					currentHtXx.setCurrentPerson(currentObj[21]!= null ? currentObj[21].toString():"");
					currentHtXx.setCurrentDep(currentObj[22]!= null ? currentObj[22].toString():"");
					currentHtXx.setFlag1(currentObj[23]!= null ? currentObj[23].toString():"");
					currentHtXx.setAttr1(currentObj[24]!= null ? currentObj[24].toString():"");
					currentHtXx.setAttr2(currentObj[25]!= null ? currentObj[25].toString():"");
					currentHtXx.setAttr3(currentObj[26]!= null ? currentObj[26].toString():"");
					currentHtXx.setAttr4(currentObj[27]!= null ? currentObj[27].toString():"");
					currentHtXx.setAttr5(currentObj[28]!= null ? currentObj[28].toString():"");
					currentHtXx.setAttr6( currentObj[29]!=null ? (Date) currentObj[29] : null);
					currentHtXx.setPinstanceId(currentObj[30]!= null ? currentObj[30].toString():"");
					currentHtXx.setWorkitemId(currentObj[31]!= null ? currentObj[31].toString():"");
					currentHtXx.setUserId(currentObj[32]!= null ? currentObj[32].toString():"");
					currentHtXx.setModelId(currentObj[33]!= null ? currentObj[33].toString():"");
					currentHtXx.setContractMoneyType(currentObj[34]!= null ? currentObj[34].toString():"");
					currentHtXx.setFlag2(currentObj[35]!= null ? currentObj[35].toString():"");
					currentHtXx.setFlag3(currentObj[36]!= null ? currentObj[36].toString():"");
					currentHtXx.setFlag4(currentObj[37]!= null ? currentObj[37].toString():"");
					currentHtXx.setTaskid(currentObj[38]!= null ? currentObj[38].toString():"");
					currentHtXx.setTaskuser(currentObj[39]!= null ? currentObj[39].toString():"");
					currentHtXx.setSelfNum(currentObj[40]!= null ? currentObj[40].toString():"");
					currentHtXx.setFileNum(currentObj[41]!= null ? currentObj[41].toString():"");
					currentHtXx.setSignCop(currentObj[42]!= null ? currentObj[42].toString():"");
					currentHtXx.setHtAttach(currentObj[43]!= null ? currentObj[43].toString():"");
					currentHtXx.setStatusHt(currentObj[44]!= null ? currentObj[44].toString():"");
					currentHtXx.setFlag(currentObj[45]!= null ? currentObj[45].toString():"");
					currentHtXx.setAutScanFlag(currentObj[46]!=null ? Long.valueOf(currentObj[46].toString()) : null);
					currentHtXx.setRemoved(currentObj[47]!=null ? Long.valueOf(currentObj[47].toString()):null);
					currentHtXx.setBudget(currentObj[48]!= null ? currentObj[48].toString():"");
					currentHtXx.setContractType(currentObj[49]!= null ? currentObj[49].toString():"");
					htxxList.add(currentHtXx);
				}
			}
			return htxxList;
		}
		return null;
	}
	
	
	public Contract convertHtXxToContract(HtXx htXx){
		Contract contract = null;
		if(htXx!=null){
			contract = new Contract();
			contract.setContractName(htXx.getContractName());
			contract.setContractNo(htXx.getContractNum());
			contract.setPayType(htXx.getContractMoneyType());
			contract.setContractPrice(htXx.getContractMoney());
			contract.setRemark(htXx.getRemark());
			contract.setSelfNo(htXx.getSelfNum());
			contract.setRemoved(htXx.getRemoved()+"");
			contract.setBuildSupplierName(htXx.getSignCop());
//			contract.setContractAttachment(htXx.getHtAttach());
		}
		return contract;
	}
	
	
	
	
}
