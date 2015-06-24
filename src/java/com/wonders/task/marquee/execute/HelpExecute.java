/**   
* @Title: HelpExecute.java 
* @Package com.wonders.task.marquee.execute 
* @Description: TODO(用一句话描述该文件做什么) 
* @author zhoushun   
* @date 2014年5月6日 下午1:45:42 
* @version V1.0   
*/
package com.wonders.task.marquee.execute;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wonders.schedule.util.SpringBeanUtil;
import com.wonders.task.marquee.model.bo.MarqueeMsg;
import com.wonders.task.marquee.service.OldService;
import com.wonders.task.marquee.service.StfbService;
import com.wonders.task.marquee.util.HelpUtil;
import com.wonders.task.sample.ITaskService;
import com.wonders.task.todoItem.util.DateUtil;
import com.wonders.task.todoItem.util.StringUtil;

/** 
 * @ClassName: HelpExecute 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年5月6日 下午1:45:42 
 *  
 */

@Transactional(value = "txManager2",propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
/* 功能模块入口点，beanid即数据库配置中的name */
@Service("helpExecute")
public class HelpExecute implements ITaskService{

	private StfbService stfbService;
	private OldService oldService;
	private static final String XXXX_ID = "2134";
	private static final String DOWNLOAD_ID = "2115";
	private static final String HELP_ID = "2114";
	private static final int rowNum = 1;
	private static final String app = "help";
	private static final String XXXX_TYPE = "使用技巧";
	private static final String DOWNLOAD_TYPE = "下载中心";
	private static final String HELP_TYPE = "系统帮助";
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	
	@Autowired(required = false)
	public void setStfbService(@Qualifier(value = "stfbService") StfbService stfbService) {
		this.stfbService = stfbService;
	}
	
	@Autowired(required = false)
	public void setOldService(@Qualifier(value = "oldService") OldService oldService) {
		this.oldService = oldService;
	}
	
	/** 
	* @Title: exec 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param param
	* @param @return    设定文件 
	* @throws 
	*/
	/** 
	* @Title: exec 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param param
	* @param @return    设定文件 
	* @throws 
	*/
	@Override
	public String exec(String param) {
		Date date = new Date();
		//XXXXX
		StringBuffer updateId = new StringBuffer();
		List<MarqueeMsg> saveList = new ArrayList<MarqueeMsg>();
		String XXXXid = this.getMarqueeNews(XXXX_TYPE);
		List<Map<String,Object>> XXXXlist = this.getStfbNews(XXXX_ID, XXXXid);
		if(XXXXlist != null && XXXXlist.size() > 0){
			Map<String,Object> map = XXXXlist.get(0);
			MarqueeMsg bo = new MarqueeMsg();
			bo.setTitle((String)map.get("title"));
			bo.setType(XXXX_TYPE);
			bo.setUrl("http://10.1.44.18/stfb"+map.get("url")+"/con"+map.get("identified_no")+".htm");
			bo.setPriority("2");
			bo.setPublishTime(df.format(date));
			bo.setOperateTime(df.format(date));
			bo.setValidStartTime(sdf.format(date)+" 00:00:00");
			bo.setValidEndTime("3000-12-31 23:59:59");
			bo.setRemoved("0");
			bo.setApp(app);
			bo.setDeptId(map.get("id")+"");
			saveList.add(bo);
			updateId.append(map.get("id"));
			updateId.append(",");
		}
		//系统帮助
		String HELPid = this.getMarqueeNews(HELP_TYPE);
		List<Map<String,Object>> HELPlist = this.getStfbNews(HELP_ID, HELPid);
		if(HELPlist != null && HELPlist.size() > 0){
			Map<String,Object> map = HELPlist.get(0);
			MarqueeMsg bo = new MarqueeMsg();
			bo.setTitle((String)map.get("title"));
			bo.setType(HELP_TYPE);
			bo.setUrl("http://10.1.44.18/stfb"+map.get("url")+"/con"+map.get("identified_no")+".htm");
			bo.setPriority("2");
			bo.setPublishTime(df.format(date));
			bo.setOperateTime(df.format(date));
			bo.setValidStartTime(sdf.format(date)+" 00:00:00");
			bo.setValidEndTime("3000-12-31 23:59:59");
			bo.setRemoved("0");
			bo.setApp(app);
			bo.setDeptId(map.get("id")+"");
			saveList.add(bo);
			updateId.append(map.get("id")); 
			updateId.append(",");
		}
		
		//下载中心
		String DOWNLOADid = this.getMarqueeNews(DOWNLOAD_TYPE);
		List<Map<String,Object>> DOWNLOADlist = this.getStfbNews(DOWNLOAD_ID, DOWNLOADid);
		if(DOWNLOADlist != null && DOWNLOADlist.size() > 0){
			Map<String,Object> map = DOWNLOADlist.get(0);
			MarqueeMsg bo = new MarqueeMsg();
			bo.setTitle((String)map.get("title"));
			bo.setType(DOWNLOAD_TYPE);
			bo.setUrl("http://10.1.44.18/stfb"+map.get("url")+"/con"+map.get("identified_no")+".htm");
			bo.setPriority("2");
			bo.setPublishTime(df.format(date));
			bo.setOperateTime(df.format(date));
			bo.setValidStartTime(sdf.format(date)+" 00:00:00");
			bo.setValidEndTime("3000-12-31 23:59:59");
			bo.setRemoved("0");
			bo.setApp(app);
			bo.setDeptId(map.get("id")+"");
			saveList.add(bo);
			updateId.append(map.get("id")); 
			updateId.append(",");
		}
		
		oldService.updateBySql("delete from T_MARQUEE_MSG where app = '"+app+"'");
		if(updateId.toString().length() > 0){
			this.updateTime(updateId.toString().substring(0,updateId.toString().length()-1));
		}
		this.oldService.saveOrUpdateAll(saveList);
		
		return "";
	}
	
	private void updateTime(String id){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(date);
		stfbService.updateSql("update tb_content t "
				+ " set t.pub_date = to_date('"+time+"','yyyy-mm-dd hh24:mi:ss')"
				+ "  where t.id in (" +id+")");
	}
	
	private String getMarqueeNews(String type){
		String sql = "select DEPT_ID,ID from t_marquee_msg t "
				+ " where t.removed='0' and"
				+ "  t.type='"+type+"'";
		List<Object[]> list = oldService.findBySql(sql);
		String id = "0";
		if(list !=null && list.size() > 0){
			id = (list.get(0))[0].toString();
		}
		
		return id;
	}
	
	private List<Map<String,Object>> getStfbNews(String sj_id,String no_id){
		String sql = "select t.id,t.title,t.url,t.identified_no,"
				+ " to_char(t.pub_date,'yyyy-mm-dd') pubtime "
				+ " from tb_content t where t.sj_id =  "+sj_id
				+ " and t.del_flag = '0' and t.publish_flag='1'"
				+ " order by t.pub_date desc";
		List<Map<String,Object>> list = stfbService.findBySql(sql);
		if(list!=null && list.size() == 1){
			
		}else{
			
			String sqlLatest = "select * from (  select t.id,t.title,t.url,t.identified_no,"
					+ " to_char(t.pub_date,'yyyy-mm-dd') pubtime "
					+ " from tb_content t where t.sj_id =  "+sj_id
					+ " and t.id != "+no_id+" and t.del_flag = '0' and t.publish_flag='1'"
					+ " and t.pub_date > sysdate - 1 ) where rownum = " + rowNum;
			List<Map<String,Object>> listLatest = stfbService.findBySql(sqlLatest);
			if(listLatest != null && listLatest.size() > 0){
				return listLatest;
			}else{
				String sqlNew = "select * from (select t.id,t.title,t.url,t.identified_no,"
						+ " to_char(t.pub_date,'yyyy-mm-dd') pubtime "
						+ " from tb_content t where t.sj_id =  "+sj_id
						+ " and t.id != "+no_id+" and t.del_flag = '0' and t.publish_flag='1'"
						+ " order by t.pub_date) where rownum = " + rowNum;
				list = stfbService.findBySql(sqlNew);
				
			}
		}
		return list;
	}
	
	public static void main(String[] args){
		ApplicationContext applicationContext = null;  
		String[] fileUrl = new String[]{"classpath*:*Context*.xml"};  
		applicationContext = new ClassPathXmlApplicationContext(fileUrl);  
		ITaskService task = (ITaskService) SpringBeanUtil.getBean("helpExecute");
		task.exec("");

	}

}
