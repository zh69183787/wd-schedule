package com.wonders.task.marquee.execute;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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

@Transactional(value = "txManager2",propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
/* 功能模块入口点，beanid即数据库配置中的name */
@Service("yyzbExecute")
public class YyzbExecute implements ITaskService{
	private OldService oldService;
	private static String saveStr = null;
	private static String app = "yyzb";
	private static String type = "生产业务信息";
	private static String portalPath = PropertyUtil.getValueByKey("config.properties", "portalPath");
	private static java.text.DecimalFormat ddf = new java.text.DecimalFormat("0.00");
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private static double passengerCapacityControl = 270000;
	private static double ticketIncomeControl = 630000;
	private static double onTimeControl = 98;
	private static double onWorkControl = 98;
	@Autowired(required = false)
	public void setOldService(@Qualifier(value = "oldService") OldService oldService) {
		this.oldService = oldService;
	}

	public static String cal(double a,double b){
		String result = "0";
		if(b==0){
			return "100";
		}else{
			result = ddf.format((a/b)*100);
		}
		return result;
	}
	
	@Override
	public String exec(String param) {
		Date date = new Date();
		Date yeDate = new Date(date.getTime()-24*60*60*1000);
		String weekDate = HelpUtil.getWeekOfDate(sdf.format(yeDate));
		
		String sql = "select * from ( "+
			" select t.id,round(t.passenger_capacity_year/10000,2),round(t.ticket_income_year/10000,2),round(t.metro_distance_year/10000000,2), "+
			" (t.online_eight_section+t.online_seven_section+t.online_six_section+t.online_four_section+t.online_three_section) online_all," +
			" round(t.passenger_capacity_daily/10000,2),round(t.ticket_income_daily/10000,2),round(t.metro_distance_daily/10000000,2),"
			+ "t.operate_time  "+
			" from dw_metro_production t where t.indicator_date = '"+sdf.format(yeDate)+"' and t.indicator_line = '0' "+
			" and t.removed = '0' order by t.operate_time desc "+
			" ) where rownum < 2 ";
		List<Object[]> list = oldService.findBySql(sql);
		List<MarqueeMsg> saveList = new ArrayList<MarqueeMsg>();
		String saveStrNew = "";
		if(list!=null&&list.size()>0){
			saveStrNew += (String)list.get(0)[0] + (String)list.get(0)[8];
		}
		
		sql = "select * from ( "+
			" select t.id,t.passenger_capacity_year,t.ticket_income_year,t.metro_distance_year," +
			" t.passenger_capacity_daily,t.ticket_income_daily,t.metro_distance_daily,t.operate_time "+
			" from dw_metro_production_control t where t.indicator_line = '0' "+
			" and t.removed = '0' order by t.operate_time desc "+
			" ) where rownum < 2";
		List<Object[]> list2 = oldService.findBySql(sql);
		if(list2!=null&&list2.size()>0){
			saveStrNew += (String)list2.get(0)[0] + (String)list2.get(0)[7];
		}
		
		sql = "select * from ( "+
			" select t.id,t.metro_ontime_daily*100,t.metro_onwork_daily*100,t.operate_time"
			+ " from dw_metro_quality t "+
			" where t.indicator_date = '"+sdf.format(yeDate)+"' and t.indicator_line = '0' "+
			" and t.removed = '0' order by t.operate_time desc "+
			" ) where rownum < 2";
		List<Object[]> list3 = oldService.findBySql(sql);
		if(list3!=null&&list3.size()>0){
			saveStrNew += (String)list3.get(0)[0] + (String)list3.get(0)[3];
		}
		
		if(saveStr==null||!saveStrNew.equals(saveStr)){
			oldService.updateBySql("delete from T_MARQUEE_MSG where app = '"+app+"'");
			
			if(list!=null&&list.size()>0){
				MarqueeMsg bo = new MarqueeMsg();
				String title = "["+sdf.format(yeDate)+"]"+"("+weekDate+") 全路网 客流量"+list.get(0)[5]+"万人次，年累计"+list.get(0)[1]+"万人次";
//				if(list2!=null&&list2.size()>0&&list2.get(0)[1]!=null){
//					title += "，管控值"+list2.get(0)[1]+"万人次/日";
//				}
				title += "，管控值"+passengerCapacityControl+"万人次/日，完成";
				title += cal(Double.parseDouble(list.get(0)[1].toString()),passengerCapacityControl)+"%";
				title += "。";
				bo.setTitle(title);
				bo.setUrl(portalPath+"/portal/center/yygl/yg_index.jsp");
				bo.setPriority("0");
				bo.setPublishTime(df.format(date));
				bo.setOperateTime(df.format(date));
				bo.setValidStartTime(sdf.format(date)+" 00:00:00");
				bo.setValidEndTime("3000-12-31 23:59:59");
				bo.setRemoved("0");
				bo.setApp(app);
				bo.setType(type);
				saveList.add(bo);
				
				bo = new MarqueeMsg();
				title = "["+sdf.format(yeDate)+"]"+"("+weekDate+") 全路网 客运收入"+list.get(0)[6]+"万元，年累计"+list.get(0)[2]+"万元";
//				if(list2!=null&&list2.size()>0&&list2.get(0)[2]!=null){
//					title += "，管控值"+list2.get(0)[2]+"万元";
//				}
				title += "，管控值"+ticketIncomeControl+"万元，完成";
				title += cal(Double.parseDouble(list.get(0)[2].toString()),ticketIncomeControl)+"%";
				
				title += "。";
				bo.setTitle(title);
				bo.setUrl(portalPath+"/portal/center/yygl/yg_index.jsp");
				bo.setPriority("0");
				bo.setPublishTime(df.format(date));
				bo.setOperateTime(df.format(date));
				bo.setValidStartTime(sdf.format(date)+" 00:00:00");
				bo.setValidEndTime("3000-12-31 23:59:59");
				bo.setRemoved("0");
				bo.setApp(app);
				bo.setType(type);
				saveList.add(bo);
				
//				bo = new MarqueeMsg();
//				title = "["+sdf.format(yeDate)+"] 运营里程"+list.get(0)[7]+"万车公里，年累计"+list.get(0)[3]+"万车公里";
//				if(list2!=null&&list2.size()>0&&list2.get(0)[3]!=null){
//					title += "，管控值"+list2.get(0)[3]+"万车公里";
//				}
//				title += "。";
//				bo.setTitle(title);
//				bo.setUrl(portalPath+"/portal/center/yygl/yg_index.jsp");
//				bo.setPriority("0");
//				bo.setPublishTime(df.format(date));
//				bo.setOperateTime(df.format(date));
//				bo.setValidStartTime(sdf.format(date)+" 00:00:00");
//				bo.setValidEndTime(sdf.format(date)+" 23:59:59");
//				bo.setRemoved("0");
//				bo.setApp(app);
//				saveList.add(bo);
				
				bo = new MarqueeMsg();
				title = "["+sdf.format(yeDate)+"]"+"("+weekDate+") 全路网 上线车辆数"+list.get(0)[4]+"列，正点率"+list3.get(0)[1]+
						"%，管控指标"+onTimeControl+"%；兑现率"+list3.get(0)[2]+"%，管控指标"+onWorkControl+"%。";
				bo.setTitle(title);
				bo.setUrl(portalPath+"/portal/center/yygl/yg_index.jsp");
				bo.setPriority("0");
				bo.setPublishTime(df.format(date));
				bo.setOperateTime(df.format(date));
				bo.setValidStartTime(sdf.format(date)+" 00:00:00");
				bo.setValidEndTime("3000-12-31 23:59:59");
				bo.setRemoved("0");
				bo.setApp(app);
				bo.setType(type);
				saveList.add(bo);
				oldService.saveOrUpdateAll(saveList);
			}
			saveStr = saveStrNew;
		}
		System.out.println("done");
		return "";
	}
	
	public static void main(String[] args){
		ApplicationContext applicationContext = null;  
		String[] fileUrl = new String[]{"classpath*:*Context*.xml"};  
		applicationContext = new ClassPathXmlApplicationContext(fileUrl);  
		ITaskService task = (ITaskService) SpringBeanUtil.getBean("yyzbExecute");
		task.exec("");

	}

}
