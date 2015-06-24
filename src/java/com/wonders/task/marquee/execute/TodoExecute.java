package com.wonders.task.marquee.execute;

import com.wonders.schedule.util.PropertyUtil;
import com.wonders.task.marquee.model.bo.MarqueeMsg;
import com.wonders.task.marquee.service.OldService;
import com.wonders.task.sample.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Transactional(value = "txManager2",propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
/* 功能模块入口点，beanid即数据库配置中的name */
@Service("todoExecute")
public class TodoExecute implements ITaskService{
	private OldService oldService;
	private static int rowNum = 2;
	private static Map<String,String> saveMap = new HashMap<String,String>();
	private static String portalPath = PropertyUtil.getValueByKey("config.properties", "portalPath");
	private static String app = "todo";
	private static String type = "办公业务信息";
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired(required = false)
	public void setOldService(@Qualifier(value = "oldService") OldService oldService) {
		this.oldService = oldService;
	}

	@Override
	public String exec(String param) {
		Date date = new Date();
		
		String sql = "select distinct i.processname,i.incident,round(sysdate-t.starttime) days," +
			"i.summary,substr(t.assignedtouser,1,15) loginname from incidents i,tasks t "+ 
			" where t.processname = i.processname and t.incident = i.incident "+
			" and i.status = 1 and t.status = 1 and t.assignedtouser like 'ST/%' "+
			" and (sysdate-t.starttime)>5 order by loginname,days desc ";
		List<Object[]> list = oldService.findBySql(sql);
		List<MarqueeMsg> saveList = new ArrayList<MarqueeMsg>();
		Map<String,List<Object[]>> map = new HashMap<String,List<Object[]>>();
		String initKey = "";
		
		//初始化
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				initKey = list.get(i)[4]+"";
				if(!map.containsKey(initKey)){
					List<Object[]> dlist = new ArrayList<Object[]>();
					dlist.add(list.get(i));
					map.put(initKey, dlist);
				}else if(map.get(initKey).size() < rowNum){
					map.get(initKey).add(list.get(i));
				}
			}
		}
		
		oldService.updateBySql("delete from T_MARQUEE_MSG where app = '"+app+"'");
		
		for(String key : map.keySet()){
			for(int i=0;i<map.get(key).size();i++){
				MarqueeMsg bo = new MarqueeMsg();
				bo.setType(type);
				bo.setTitle("您的待办事项："+map.get(key).get(i)[3]+"，已超时"+map.get(key).get(i)[2]+"日，请尽快办理。");
				bo.setUrl(portalPath+"/portal/todo/todoItemList.action");
				bo.setPriority("1");
				bo.setPublishTime(df.format(new Date(date.getTime()-i*60*1000)));
				bo.setOperateTime(df.format(date));
				bo.setValidStartTime(sdf.format(date)+" 00:00:00");
				bo.setValidEndTime("3000-01-01 23:59:59");
				bo.setRemoved("0");
				bo.setApp(app);
				bo.setLoginName(key);
				saveList.add(bo);
			}
		}

		oldService.saveOrUpdateAll(saveList);
			
		System.out.println("done");
		return "";
	}
	
	public static void main(String[] args) throws Exception{
//		ApplicationContext applicationContext = null;
//		String[] fileUrl = new String[]{"classpath*:*Context*.xml"};
//		applicationContext = new ClassPathXmlApplicationContext(fileUrl);
//		ITaskService task = (ITaskService) SpringBeanUtil.getBean("todoExecute");
//		//task.exec("");
//		//Date dd = new Date();
//		//System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dd));
//		//System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(dd.getTime()+60*1000)));
//		System.out.println("//12321//".replaceAll("\\||:|\\?|\\*|\"|<|>|\\p{Cntrl}", "_"));
//		String filepath="d://";
//		String filename = "zs9.jsp\00ddd.txt";
//		filename = filename.replaceAll("\\||:|\\?|\\*|\"|<|>|\\p{Cntrl}", "_");
//		filepath = filepath + filename;
//		//正则表达中\\p{Cntrl}这个是处理00字符的。）
//		System.out.println(filepath);
//		FileOutputStream fos=new FileOutputStream(filepath);
//		fos.write("hello".getBytes());
//		fos.close();
//		byte [] b=new byte[100];
//		int i=0;
//		FileInputStream fis=new FileInputStream(filepath);
//		i=fis.read(b);
//		System.out.println(new String(b,0,i));
//		fis.close();
//		System.out.println(filepath);
//
//		String aa = "\\\\";
//		System.out.println(aa.replaceAll("\\\\", "1"));
//
		FileOutputStream fff=new FileOutputStream("d://a123.jsp\00ddd.txt");
		fff.write("hello发射点发射点发射点".getBytes("UTF-8"));
		fff.close();
		
//		File gg = new File("d://fff.jsp\000ddd.txt");
//		 FileInputStream ggf=new FileInputStream(gg);
//		 FileInputStream gga=new FileInputStream(gg);
//		 BufferedReader r = new BufferedReader(new InputStreamReader(ggf));
//		 System.out.println(r.readLine());
//		byte [] bb=new byte[100];
//		int cc = gga.read(bb);
//		System.out.println(new String(bb,0,cc));
//		System.out.println(gg.getName());
	}

}
