package com.wonders.task.marquee.execute;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Clob;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wonders.schedule.util.PropertyUtil;
import com.wonders.schedule.util.SpringBeanUtil;
import com.wonders.task.marquee.model.bo.MarqueeMsg;
import com.wonders.task.marquee.service.OldService;
import com.wonders.task.marquee.util.HelpUtil;
import com.wonders.task.sample.ITaskService;
import com.wonders.task.todoItem.util.StringUtil;

@Transactional(value = "txManager2",propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
/* 功能模块入口点，beanid即数据库配置中的name */
@Service("yysbExecute")
public class YysbExecute implements ITaskService{
	private OldService oldService;
	private static int rowNum = 2;
	private static String saveStr = null;
	private static String app = "yysb";
	private static String type = "生产业务信息";
	private static String portalPath = PropertyUtil.getValueByKey("config.properties", "portalPath");
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired(required = false)
	public void setOldService(@Qualifier(value = "oldService") OldService oldService) {
		this.oldService = oldService;
	}

	@Override
	public String exec(String param) {
		Date date = new Date();
		String sql = "select * from ( "+
			" select t.id,t.accident_title,t.accident_time,t.accident_date,"
			+ " t.accident_detail  from t_metro_express t "+
			" where t.removed = '0' and t.operate_time >= to_char(sysdate-1/12,'yyyy-mm-dd hh24:mi:ss') "+
			" and t.operate_time <= to_char(sysdate,'yyyy-mm-dd hh24:mi:ss') order by t.operate_time desc "+
			" ) where rownum <= "+rowNum;
		List<Object[]> list = oldService.findBySql(sql);
		List<MarqueeMsg> saveList = new ArrayList<MarqueeMsg>();
		String saveStrNew = "";
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				saveStrNew += (String)list.get(i)[0];
			}
		}
		
		if(saveStr==null||!saveStrNew.equals(saveStr)){
			oldService.updateBySql("delete from T_MARQUEE_MSG where app = '"+app+"'");
			
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					String pubtime = StringUtil.getNotNullValueString(list.get(i)[3]);
					String day = HelpUtil.getWeekOfDate(pubtime);
					String content = "";
					try {
						content = oracleClob2Str((Clob)list.get(i)[4]);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						content = "";
					}
					MarqueeMsg bo = new MarqueeMsg();
					bo.setType(type);
					bo.setTitle("["+pubtime+"]"+"("+day+") 运营速报："+list.get(i)[2]+"，"+content);
					bo.setUrl(portalPath+"/portal/metroExpress/metroExpressView.action?metroExpressId="+list.get(i)[0]);
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
		System.out.println("done");
		return "";
	}
	
	private String oracleClob2Str(Clob clob) throws Exception {
        return (clob != null ? clob.getSubString(1, (int) clob.length()) : "");
    }

	public static void main(String[] args){
		ApplicationContext applicationContext = null;  
		String[] fileUrl = new String[]{"classpath*:*Context*.xml"};  
		applicationContext = new ClassPathXmlApplicationContext(fileUrl);  
		ITaskService task = (ITaskService) SpringBeanUtil.getBean("yysbExecute");
		task.exec("");

	}
}
