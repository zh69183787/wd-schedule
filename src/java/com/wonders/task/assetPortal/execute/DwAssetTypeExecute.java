package com.wonders.task.assetPortal.execute;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wonders.schedule.common.util.SimpleLogger;
import com.wonders.task.assetPortal.service.DwAssetTypeService;
import com.wonders.task.assetPortal.model.bo.DwAssetType;
import com.wonders.task.audit.dbx.execute.DbxExecute;
import com.wonders.task.sample.ITaskService;
import com.wonders.task.assetPortal.util.SetDwAssetType;

/**
 * @ClassName: SampleService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author sunjiawei
 * @date 2012-12-4 下午03:28:48
 * 
 */
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
/* 功能模块入口点，beanid即数据库配置中的name */
@Service("dwAssetTypeExecute")
@Scope("prototype")
public class DwAssetTypeExecute implements ITaskService{
	static SimpleLogger log = new SimpleLogger(DbxExecute.class);
	private List<String> allLine = new ArrayList<String>();
	private List<String> allStation = new ArrayList<String>();
	private List<String> allCompany = new ArrayList<String>();
	SetDwAssetType setDwAssetType = new SetDwAssetType();
	SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private DwAssetTypeService dwAssetTypeService;
	
	public DwAssetTypeService getDwAssetTypeService() {
		return dwAssetTypeService;
	}

	@Autowired(required = false)
	public void setDwAssetTypeService(@Qualifier(value = "dwAssetTypeService") DwAssetTypeService dwAssetTypeService) {
		this.dwAssetTypeService = dwAssetTypeService;
	}

	/**
	 * @Title: exec
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param 设定文件
	 * @throws
	 */

	public String exec(String param) {
		try {
			List<DwAssetType> listModel = new ArrayList<DwAssetType>();
			Date date = new Date();
			String updateTime=df.format(date);
			Map<String,String> lineMap = countLineType();
			Map<String,String> stationMap = countStationType();
			Map<String,String> companyMap = countCompanyType();
			System.out.println("allLine=="+allLine.size());
			System.out.println("allStation=="+allStation.size());
			System.out.println("allCompany=="+allCompany.size());
			if(allLine!=null&&allLine.size()>0&&allStation!=null&&allStation.size()>0&&allCompany!=null&&allCompany.size()>0){
				listModel = executeLineType(updateTime,allLine,listModel,lineMap);
				listModel = executeStationType(updateTime,allStation,listModel,stationMap);
				listModel = executeCompanyType(updateTime,allCompany,listModel,companyMap);
				System.out.println("共更新数据=="+listModel.size());
				dwAssetTypeService.saveOrUpdateAll(listModel);
			}
			log.debug("nativeService run+++++++++++++++++++++");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public List<DwAssetType> executeLineType(String updateTime,List<String> allLine,List<DwAssetType> listModel,Map<String,String> lineMap){
		
		for(int i=0;i<allLine.size();i++){
			String id = dwAssetTypeService.findIdByLine(allLine.get(i));
			DwAssetType dwAssetType = new DwAssetType();
			if(id==null){
				dwAssetType.setLine(allLine.get(i));
				dwAssetType.setType("1");
				dwAssetType.setRemoved("0");
				dwAssetType.setUpdateTime(updateTime);
				setDwAssetType.setLineType(dwAssetType,lineMap,allLine.get(i));
			}else{
				dwAssetType = (DwAssetType)dwAssetTypeService.load(id, DwAssetType.class);
				dwAssetType.setUpdateTime(updateTime);
				setDwAssetType.setLineType(dwAssetType,lineMap,allLine.get(i));
			}
			listModel.add(dwAssetType);
		}
		return listModel;
	}
	
	public List<DwAssetType> executeStationType(String updateTime,List<String> allStation,List<DwAssetType> listModel,Map<String,String> stationMap){
		
		String line = "";
		String station = "";
		for(int i=0;i<allStation.size();i++){
			line = allStation.get(i).split("\\|")[0];
			station = allStation.get(i).split("\\|")[1];                                      
			String id = dwAssetTypeService.findIdByStation(line,station);
			DwAssetType dwAssetType = new DwAssetType();
			if(id==null){
				dwAssetType.setLine(line);
				dwAssetType.setStation(station);
				dwAssetType.setType("2");
				dwAssetType.setRemoved("0");
				dwAssetType.setUpdateTime(updateTime);
				setDwAssetType.setStationType(dwAssetType,stationMap,line,station);
			}else{
				dwAssetType = (DwAssetType)dwAssetTypeService.load(id, DwAssetType.class);
				dwAssetType.setUpdateTime(updateTime);
				setDwAssetType.setStationType(dwAssetType,stationMap,line,station);
			}
			listModel.add(dwAssetType);
		}
		return listModel;
	}
	
	public List<DwAssetType> executeCompanyType(String updateTime,List<String> allCompany,List<DwAssetType> listModel,Map<String,String> companyMap){
		
		String line = "";
		String company = "";
		for(int i=0;i<allCompany.size();i++){
			line = allCompany.get(i).split("\\|")[0];
			company = allCompany.get(i).split("\\|")[1];                                      
			String id = dwAssetTypeService.findIdByCompany(line,company);
			DwAssetType dwAssetType = new DwAssetType();
			if(id==null){
				dwAssetType.setLine(line);
				dwAssetType.setOwnerCompany(company);
				dwAssetType.setType("3");
				dwAssetType.setRemoved("0");
				dwAssetType.setUpdateTime(updateTime);
				setDwAssetType.setCompanyType(dwAssetType,companyMap,line,company);
			}else{
				dwAssetType = (DwAssetType)dwAssetTypeService.load(id, DwAssetType.class);
				dwAssetType.setUpdateTime(updateTime);
				setDwAssetType.setCompanyType(dwAssetType,companyMap,line,company);
			}
			listModel.add(dwAssetType);
		}
		return listModel;
	}
	
	public Map<String,String> countLineType(){
		Map<String,String> map = new HashMap<String,String>();
		List<Object[]> list = dwAssetTypeService.countTypeByLine();
		List<Object[]> listAllLine = dwAssetTypeService.countTypeAllLine();
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				map.put(list.get(i)[0]+"|"+list.get(i)[1], list.get(i)[2]+"|"+list.get(i)[3]);
				if(i==0){
					allLine.add((String) list.get(i)[0]);
				}
				if(i>0&&!((String) list.get(i)[0]).equals((String) list.get(i-1)[0])){
					allLine.add((String) list.get(i)[0]);
				}
			}
		}
		if(listAllLine!=null&&listAllLine.size()>0){
			for(int i=0;i<listAllLine.size();i++){
				map.put("全网|"+listAllLine.get(i)[0], listAllLine.get(i)[1]+"|"+listAllLine.get(i)[2]);
			}
			allLine.add("全网");
		}
		return map;
	}
	
	public Map<String,String> countStationType(){
		Map<String,String> map = new HashMap<String,String>();
		List<Object[]> list = dwAssetTypeService.countTypeByStation();
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				map.put(list.get(i)[0]+"|"+list.get(i)[1]+"|"+list.get(i)[2], list.get(i)[3]+"|"+list.get(i)[4]);
				if(i==0){
					allStation.add((String) list.get(i)[0]+"|"+(String) list.get(i)[1]);
				}
				if(i>0&&(!((String) list.get(i)[0]).equals((String) list.get(i-1)[0])||!((String) list.get(i)[1]).equals((String) list.get(i-1)[1]))){
					allStation.add((String) list.get(i)[0]+"|"+(String) list.get(i)[1]);
				}
			}
		}
		return map;
	}
	
	public Map<String,String> countCompanyType(){
		Map<String,String> map = new HashMap<String,String>();
		List<Object[]> list = dwAssetTypeService.countValueByCompany();
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				map.put(list.get(i)[0]+"|"+list.get(i)[1]+"|"+list.get(i)[2], list.get(i)[3]+"|"+list.get(i)[4]+"|"+list.get(i)[5]);
				if(i==0){
					allCompany.add((String) list.get(i)[0]+"|"+(String) list.get(i)[1]);
				}
				if(i>0&&(!((String) list.get(i)[0]).equals((String) list.get(i-1)[0])||!((String) list.get(i)[1]).equals((String) list.get(i-1)[1]))){
					allCompany.add((String) list.get(i)[0]+"|"+(String) list.get(i)[1]);
				}
			}
		}
		return map;
	}
	
	
}
