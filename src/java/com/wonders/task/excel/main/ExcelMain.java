/**
 * 
 */
package com.wonders.task.excel.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import jxl.JXLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wonders.schedule.util.SpringBeanUtil;
import com.wonders.schedule.util.StringUtil;
import com.wonders.task.excel.model.User;
import com.wonders.task.excel.service.ExcelService;
import com.wonders.task.excel.util.FileUtil;
import com.wonders.task.sample.ITaskService;

/** 
 * @ClassName: ExcelMain 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-5-15 上午10:02:10 
 *  
 */

@Transactional(value = "txManager",propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
@Service("excelMain")
public class ExcelMain implements ITaskService{
	private  ExcelService service;

	/** 
	* @Title: exec 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param param
	* @param @return    设定文件 
	* @throws 
	*/
	@Override
	public String exec(String param) {
		param = "select * from t_asset_info t where t.registry=1 and t.removed=0";
//		param = "select substr(v.login_name ,0 ,12) as loginName,v.name as userName ,v.description,t.* from (" +
//				" select c.id,c.login_name,c.name,n.id as dept_id,n.name as dept_name,n.description,o.orders from cs_user_organnode o,cs_organ_node n,cs_user c where c.id=o.security_user_id and o.organ_node_id=n.id" +
//				" and n.organ_node_type_id=2 and c.removed=0 and n.removed=0 and c.id!=1" +
//				" ) v ,t_user t where  substr(v.login_name ,0 ,12) = t.login_name" +
//				" and t.removed=0 order by v.dept_id";
		List<Map<String,Object>> data = this.service.getData(param);
		Map<String,User> result = new TreeMap<String,User>();
//		for(Map<String,Object> map : data){
//			String loginName = StringUtil.getNotNullValueString(map.get("loginName"));
//			String userName = StringUtil.getNotNullValueString(map.get("userName"));
//			String description = StringUtil.getNotNullValueString(map.get("description"));
//			
//			if(result.containsKey(loginName)){
//				result.get(loginName).setDescription(result.get(loginName).getDescription()+","+description);
//			}else{
//				result.put(loginName, new User(loginName,userName,description));
//			}
//		}
//		
//		List<List<String>> j = new ArrayList<List<String>>();
//		for(Map.Entry<String, User> t : result.entrySet()){
//			List<String> g = new ArrayList<String>();
//			g.add(t.getValue().getLoginName());
//			g.add(t.getValue().getUserName());
//			g.add(t.getValue().getDescription());
//			j.add(g);
//		}
		File file = new File("D://ky.xls");
		List<String> head = new ArrayList<String>();
		try {
			FileUtil.createXls(new FileOutputStream(file), "孔琰", head, data);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JXLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @return the service
	 */
	public ExcelService getService() {
		return service;
	}

	/**
	 * @param service the service to set
	 */
	@Autowired(required=false)
	public void setService(@Qualifier("excelService")ExcelService service) {
		this.service = service;
	}
	
	
	public static void main(String[] args){
		ApplicationContext applicationContext = null;  
		String[] fileUrl = new String[]{"classpath*:*Context*.xml"};  
		applicationContext = new ClassPathXmlApplicationContext(fileUrl);  
		ITaskService task = (ITaskService) SpringBeanUtil.getBean("excelMain");
		task.exec("");

	}
	
}
