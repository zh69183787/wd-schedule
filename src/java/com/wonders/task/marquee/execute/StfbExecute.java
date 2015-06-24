package com.wonders.task.marquee.execute;

import java.math.BigDecimal;
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
import com.wonders.task.todoItem.util.StringUtil;

@Transactional(value = "txManager2",propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
/* 功能模块入口点，beanid即数据库配置中的name */
@Service("stfbExecute")
public class StfbExecute implements ITaskService{
	private StfbService stfbService;
	private OldService oldService;
	private static int rowNum = 2;
	private static String saveStr = null;
	private static String app = "ldjh";
	private static String type = "领导讲话";
	
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

	@Override
	public String exec(String param) {
		Date date = new Date();
		String sql = "select * from ( "+
			" select t.id,t.title,t.url,t.identified_no,to_char(t.pub_date,'yyyy-mm-dd') pubtime"+
				" from tb_content t "+
			" where sj_id = 854 and t.del_flag = '0' and t.publish_flag = '1' "+
			" and t.pub_date < sysdate and t.pub_date > (sysdate-5) order by t.pub_date desc "+
			" ) where rownum <= "+rowNum;
		List<Map<String,Object>> list = stfbService.findBySql(sql);
		List<MarqueeMsg> saveList = new ArrayList<MarqueeMsg>();
		String saveStrNew = "";
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				saveStrNew += ((BigDecimal)list.get(i).get("id")).longValue()+"";
			}
		}
		if(saveStr==null||!saveStrNew.equals(saveStr)){
			oldService.updateBySql("delete from T_MARQUEE_MSG where app = '"+app+"'");
			
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					String pubtime = StringUtil.getNotNullValueString(list.get(i).get("pubtime"));
					String day = HelpUtil.getWeekOfDate(pubtime);
					MarqueeMsg bo = new MarqueeMsg();
					bo.setTitle("["+pubtime+"]"+"("+day+") "+"："+(String)list.get(i).get("title"));
					bo.setType(type);
					bo.setUrl("http://10.1.44.18/stfb"+list.get(i).get("url")+"/con"+list.get(i).get("identified_no")+".htm");
					bo.setPriority("0");
					bo.setPublishTime(df.format(date));
					bo.setOperateTime(df.format(date));
					bo.setValidStartTime(sdf.format(date)+" 00:00:00");
					bo.setValidEndTime("3000-12-31 23:59:59");
					bo.setRemoved("0");
					bo.setApp(app);
					saveList.add(bo);
				}
				oldService.saveOrUpdateAll(saveList);
			}
			saveStr = saveStrNew;
		}
		return "";
	}

	public static void main(String[] args){
		ApplicationContext applicationContext = null;  
		String[] fileUrl = new String[]{"classpath*:*Context*.xml"};  
		applicationContext = new ClassPathXmlApplicationContext(fileUrl);  
		ITaskService task = (ITaskService) SpringBeanUtil.getBean("stfbExecute");
		task.exec("");

	}
}
