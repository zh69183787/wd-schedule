package com.wonders.task.marquee.execute;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
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
import com.wonders.task.marquee.util.HelpUtil;
import com.wonders.task.sample.ITaskService;
import com.wonders.task.todoItem.util.StringUtil;

@Transactional(value = "txManager2", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
/* 功能模块入口点，beanid即数据库配置中的name */
@Service("trainInfoExecute")
public class TrainInfoExecute implements ITaskService {
	private OldService oldService;
	private NewService newService;
	private static String app = "train";
	private static String type = "教育培训信息";
	private static String portalPath = PropertyUtil.getValueByKey("config.properties", "portalPath");
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	DateFormat ydf = new SimpleDateFormat("yyyy");

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
		String a,b,c,d = "";
		Date date = new Date();
		String aSql = "select case sum(t.manage_plan+t.prod_plan) when 0 then '0%' "
				+ " else round(100*sum(t.manage_result+t.prod_result)/sum(t.manage_plan+t.prod_plan),2)"
				+ " ||'%' end persent from t_train_main t where year = ? ";
		String bSql = "select case sum(t.manage_plan+t.prod_plan) when 0 then '0%' "
				+ " else round(100*sum(t.manage_result+t.prod_result)/sum(t.manage_plan+t.prod_plan),2)"
				+ " ||'%' end persent from t_train_main t where year = ? and type = 1";
		String cSql = "select case sum(t.manage_plan+t.prod_plan) when 0 then '0%' "
				+ " else round(100*sum(t.manage_result+t.prod_result)/sum(t.manage_plan+t.prod_plan),2)"
				+ " ||'%' end persent from t_train_main t where year = ? and type = 2";
		String dSql = "select case sum(t.manage_plan+t.prod_plan) when 0 then '0%' "
				+ " else round(100*sum(t.manage_result+t.prod_result)/sum(t.manage_plan+t.prod_plan),2)"
				+ " ||'%' end persent from t_train_main t where year = ? and type = 3";
		List<Object> args = new ArrayList<Object>();args.add(ydf.format(date));
		a = newService.findUniqueString("persent", aSql, args);
		b = newService.findUniqueString("persent", bSql, args);
		c = newService.findUniqueString("persent", cSql, args);
		d = newService.findUniqueString("persent", dSql, args);

		List<MarqueeMsg> saveList = new ArrayList<MarqueeMsg>();
		oldService.updateBySql("delete from T_MARQUEE_MSG where app = '" + app
				+ "'");

		String pubtime = StringUtil.getNotNullValueString(sdf.format(new Date(new Date().getTime()-24*3600*1000)));
		String day = HelpUtil.getWeekOfDate(pubtime);
		
		MarqueeMsg bo = new MarqueeMsg();
		bo.setType(type);
		bo.setTitle("[" + pubtime + "]" + "(" + day + ") 各版块年度总计划完成率（人次）"+a+"，"
				+ " 其中：上级党校和在线学习"+b+"、各直属单位"+c+"、培训中心"+d+" 。");
		bo.setUrl(portalPath+"/portal/center/pxzx/pxzx_index.jsp");
		bo.setPriority("0");
		bo.setPublishTime(df.format(date));
		bo.setOperateTime(df.format(date));
		bo.setValidStartTime(sdf.format(date) + " 00:00:00");
		bo.setValidEndTime("3000-12-31 23:59:59");
		bo.setRemoved("0");
		bo.setApp(app);
		saveList.add(bo);
		
		
		oldService.saveOrUpdateAll(saveList);

		return "";
	}

	public static void main(String[] args) {
		ApplicationContext applicationContext = null;
		String[] fileUrl = new String[] { "classpath*:*Context*.xml" };
		applicationContext = new ClassPathXmlApplicationContext(fileUrl);
		ITaskService task = (ITaskService) SpringBeanUtil
				.getBean("trainInfoExecute");
		task.exec("");
	}
}
