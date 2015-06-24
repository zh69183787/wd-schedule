package com.wonders.task.marquee.execute;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

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
import com.wonders.task.marquee.service.NewService;
import com.wonders.task.marquee.service.OldService;
import com.wonders.task.marquee.service.StfbService;
import com.wonders.task.marquee.util.HelpUtil;
import com.wonders.task.sample.ITaskService;
import com.wonders.task.todoItem.util.StringUtil;

@Transactional(value = "txManager2", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
/* 功能模块入口点，beanid即数据库配置中的name */
@Service("buildInfoExecute")
public class BuildInfoExecute implements ITaskService {
	private StfbService stfbService;
	private OldService oldService;
	private NewService newService;
	private static String app = "build";
	private static String type = "生产业务信息";
	private static String portalPath = PropertyUtil.getValueByKey("config.properties", "portalPath");
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired(required = false)
	public void setStfbService(@Qualifier(value = "stfbService") StfbService stfbService) {
		this.stfbService = stfbService;
	}
	
	
	@Autowired(required = false)
	public void setOldService(
			@Qualifier(value = "oldService") OldService oldService) {
		this.oldService = oldService;
	}

	@Autowired(required = false)
	public void setNewService(
			@Qualifier(value = "newService") NewService newService) {
		this.newService = newService;
	}

	@Override
	public String exec(String param) {
		
		if(false){
			int todoCount = 0;
			int doneCount = 0;
			int totalCount = 0;
			double doneMoney = 0.0;
			double totalMoney = 0.0;
			double monthMoney = 0.0;
	
			Date date = new Date();
			String todoSql = "select count(id),count(id) from t_before_task b where b.removed=0 "
					+ " and b.status=1 and b.update_time >=to_char(sysdate-1,'yyyy-mm-dd')";
			String doneSql = "select count(id),count(id) from t_before_task b where b.removed=0 "
					+ " and b.status=4 and b.update_time >=to_char(sysdate-1,'yyyy-mm-dd')";
			String totalSql = "select count(id),count(id) from t_before_task b where b.removed=0 "
					+ " and b.status=4";
			List<Object[]> todoList = oldService.findBySql(todoSql);
			List<Object[]> doneList = oldService.findBySql(doneSql);
			List<Object[]> totalList = oldService.findBySql(totalSql);
	
			List<MarqueeMsg> saveList = new ArrayList<MarqueeMsg>();
			String saveStrNew = "";
			todoCount = ((BigDecimal)todoList.get(0)[0]).intValue();
			doneCount = ((BigDecimal) doneList.get(0)[0]).intValue();
			totalCount = ((BigDecimal) totalList.get(0)[0]).intValue();
	
			String amountSql = "select sum(nvl(a.done_contract_amount,0)) doneMoney,"
					+ " sum(nvl(a.total_payment,0)) totalMoney,"
					+ " sum(nvl(a.this_month_payment,0)) monthMoney"
					+ " from greata_asset_execute a ";
			List<Object[]> amountList = newService.findBySql(amountSql);
			doneMoney =	((BigDecimal)amountList.get(0)[0]).intValue();
			totalMoney = ((BigDecimal)amountList.get(0)[1]).intValue();
			monthMoney = ((BigDecimal)amountList.get(0)[2]).intValue();
			
			oldService.updateBySql("delete from T_MARQUEE_MSG where app = '" + app
					+ "'");
	
			String pubtime = StringUtil.getNotNullValueString(sdf.format(new Date(new Date().getTime()-24*3600*1000)));
			String day = HelpUtil.getWeekOfDate(pubtime);
			
			MarqueeMsg bo = new MarqueeMsg();
			bo.setType(type);
			bo.setTitle("[" + pubtime + "]" + "(" + day + ") 工程建设前期办证全网全日：新增"+todoCount+"个待办，"
					+ " 办结"+doneCount+"个证件；年累计办结"+totalCount+"个证件。");
			bo.setUrl(portalPath+"/portal/beforeCount/toCountList.action?listType=project&oldDeptId=&role=2");
			bo.setPriority("0");
			bo.setPublishTime(df.format(date));
			bo.setOperateTime(df.format(date));
			bo.setValidStartTime(sdf.format(date) + " 00:00:00");
			bo.setValidEndTime("3000-12-31 23:59:59");
			bo.setRemoved("0");
			bo.setApp(app);
			saveList.add(bo);
			
			MarqueeMsg bo2 = new MarqueeMsg();
			bo2.setType(type);
			bo2.setTitle("[" + pubtime + "]" + "(" + day + ") 工程建设全网已签合同"+doneMoney+"万元；"
					+ "累计支付"+totalMoney+"万元；本月拟支付"+monthMoney+"万元。");
			bo2.setUrl(portalPath+"/portal/themeDatabase/toEstimate.action?projectId=12");
			bo2.setPriority("0");
			bo2.setPublishTime(df.format(date));
			bo2.setOperateTime(df.format(date));
			bo2.setValidStartTime(sdf.format(date) + " 00:00:00");
			bo.setValidEndTime("3000-12-31 23:59:59");
			bo2.setRemoved("0");
			bo2.setApp(app);
			//saveList.add(bo2);
			
			oldService.saveOrUpdateAll(saveList);
		}else{
			String newsSql = "select * from ( "+
					" select t.title,to_char(t.pub_date,'yyyy-mm-dd') pubtime"+
						" from tb_content t "+
					" where sj_id = 2155 and t.del_flag = '0' and t.publish_flag = '1' "+
					" order by t.pub_date desc "+
					" ) where rownum = 1";
			List<Map<String,Object>> list = stfbService.findBySql(newsSql);
			oldService.updateBySql("delete from T_MARQUEE_MSG where app = '" + app
					+ "'");
			List<MarqueeMsg> saveList = new ArrayList<MarqueeMsg>();
			String pubtime = StringUtil.getNotNullValueString(sdf.format(new Date(new Date().getTime()-24*3600*1000)));
			Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
			c.set(Calendar.DAY_OF_WEEK, 2);
			pubtime = StringUtil.getNotNullValueString(sdf.format(c.getTime()));
			String day = HelpUtil.getWeekOfDate(pubtime);
			
			
			Date date = new Date();
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					MarqueeMsg bo = new MarqueeMsg();
					bo.setType(type);
					bo.setTitle("[" + pubtime + "]" + "(" + day + ")" +(String)list.get(i).get("title"));
					bo.setUrl("");
					bo.setPriority("0");
					bo.setPublishTime(df.format(date));
					bo.setOperateTime(df.format(date));
					bo.setValidStartTime(sdf.format(date) + " 00:00:00");
					bo.setValidEndTime("3000-12-31 23:59:59");
					bo.setRemoved("0");
					bo.setApp(app);
					saveList.add(bo);
				}
			}
			oldService.saveOrUpdateAll(saveList);
		}
		
		return "";
	}

	public static void main(String[] args) {
		ApplicationContext applicationContext = null;
		String[] fileUrl = new String[] { "classpath*:*Context*.xml" };
		applicationContext = new ClassPathXmlApplicationContext(fileUrl);
		ITaskService task = (ITaskService) SpringBeanUtil
				.getBean("buildInfoExecute");
		//task.exec("");
		
		
	}
}
