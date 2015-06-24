package com.wonders.task.marquee.execute;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.wonders.task.marquee.util.PortalUtil;
import com.wonders.task.sample.ITaskService;
import com.wonders.task.todoItem.util.StringUtil;

@Transactional(value = "txManager2",propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
/* 功能模块入口点，beanid即数据库配置中的name */
@Service("mailExecute")
public class MailExecute implements ITaskService{
	private OldService oldService;
	private NewService newService;
	private static String saveStr = null;
	private static String app = "mail";
	private static String type = "办公业务信息";
	private static String url = "http://10.1.44.18/mail.jsp";
	private static String mailPath = PropertyUtil.getValueByKey("config.properties", "mailPath");
	private static String ip = "222.66.3.199";
	private static String port = "8888";
	private static String errMsg = "-";
	private static String okMsg = "+";
	
	
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired(required = false)
	public void setOldService(@Qualifier(value = "oldService") OldService oldService) {
		this.oldService = oldService;
	}
	
	@Autowired(required = false)
	public void setNewService(@Qualifier(value = "newService") NewService newService) {
		this.newService = newService;
	}

	@Override
	public String exec(String param) {
		Date date = new Date();
		long nowTime = date.getTime();
		long lastTime = date.getTime()-24*60*60*1000;
		
		String temp = "select distinct t.login_name,removed from "
				+ " t_user t where t.operate_time is not null"
				+ " and t.operate_time > to_char (sysdate-1/4,'yyyy-MM-dd hh24:mi:ss') "
				+ " and t.removed=0";
		String users = "";
		List<Object[]> userList = newService.findBySql(temp);
		if(userList != null && userList.size() > 0){
			for(Object[] o : userList)
			{
				users += "'"+StringUtil.getNotNullValueString(o[0]) + "',";
			}
			if(users.length() > 0){
				users = users.substring(0,users.length()-1);
			}
		}
		
		String sql = "select "
				+ " 'ST/'||c.login_name loginName, c.email,t.email_passwd password"
				+ "  from cs_user c ,"
				+ " t_cs_user t where c.removed=0 and c.id > 0 and c.email is not null"
				+ " and instr(c.email,'shmetro.com')>0 and c.login_name in ("+users+")"
				+ " and t.email_passwd is not null and c.id =t.id";
		System.out.println(sql);
		List<Object[]> list = oldService.findBySql(sql);
		List<MarqueeMsg> saveList = new ArrayList<MarqueeMsg>();
		String saveStrNew = "";
		Map<String,String> map = new HashMap<String,String>();
		
		
		if(list!=null&&list.size()>0){
			
			for(int i=0;i<list.size();i++){
				String loginName = StringUtil.getNotNullValueString(list.get(i)[0]);
				String mail = StringUtil.getNotNullValueString(list.get(i)[1]);
				String password = StringUtil.getNotNullValueString(list.get(i)[2]);
				String result = PortalUtil.webExec(url, mail,password);
				int count = -1;
				try{
					count = Integer.parseInt(result);
				}catch(Exception e){
					count = -1;
				}
				//System.out.println(count);
				if(count != -1 && count != 0){
					map.put(loginName, result);
				}
//				System.out.println("loginName:"+loginName);
//				System.out.println("mail:"+mail);
//				System.out.println("result:"+result);
			}
		}
		
		oldService.updateBySql("delete from T_MARQUEE_MSG where app = '"+app+"'");
		
		for(Map.Entry<String, String> entry : map.entrySet()){
				MarqueeMsg bo = new MarqueeMsg();
				bo.setTitle("您有新的未读邮件"+entry.getValue() +"封，请查收。");
				bo.setType(type);
				bo.setUrl(mailPath);
				bo.setPriority("0");
				bo.setPublishTime(df.format(date));
				bo.setOperateTime(df.format(date));
				bo.setValidStartTime(sdf.format(date)+" 00:00:00");
				bo.setValidEndTime("3000-12-31 23:59:59");
				bo.setRemoved("0");
				bo.setApp(app);
				bo.setLoginName(entry.getKey());
				saveList.add(bo);
		}
		
		oldService.saveOrUpdateAll(saveList);

		return "";
	}

	
	public static int getMailInfo(String line) {
		if(line.matches("\\S+\\s+\\S+\\s+\\d+")){
			String re=line.split("\\s+")[2];
			return Integer.parseInt(re);
		}
		//System.out.println("==="+line+"===");
		if(line.matches(".")){
			//System.out.println("yes");
			return -2;
		}
		return -1;
	}
	
	public static int getMailCount(String ip,String port,String mail,String password){
		int unReadCount = 0;
		Socket socket = null;
		BufferedReader input = null;
		PrintWriter out = null;
		try{
			socket = new Socket(ip, Integer.parseInt(port));
			if(socket==null){
				//continue;
				//return "connectionerr";
				return -1;
			}
			input = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));// 接受
			out = new PrintWriter(socket.getOutputStream(),
					true/* autoFlush */);// 传输	
			String info = null;// 接受信息
			// read information from server
			info = input.readLine();		
			//BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			//输入邮件地址，密码
			//System.out.println(input.ready());
	  		String line = null;
	  		out.println("USER " + mail);
	  		out.flush();
	  		line = input.readLine();
	  		System.out.println("line " + line);
	  		if(StringUtil.isNull(line)||line.startsWith(errMsg)){
	  			//continue;
	  			//return "errloginname";
	  			return -1;
	  		}
	  		//System.out.println("1"+line);
	  		out.println("PASS " + password);
	  		out.flush();
	  		line = input.readLine();
	  		//System.out.println("line2 " + line);
	  		if(StringUtil.isNull(line)||line.startsWith(errMsg)){
	  			//continue;
	  			//return "errpwd";
	  			return -1;
	  		}
	  		//System.out.println("2"+line);
	  		//进入目录查找邮件信息。
	
	  		out.println("CHDIR inbox");
	  		line = input.readLine();
	  		if(StringUtil.isNull(line)||line.startsWith(errMsg)){
	  			//continue;
	  			//return "errchdir";
	  			return -1;
	  		}
	  		//System.out.println("3"+line);
	  		out.println("LIST");
	  		line = input.readLine();
	  		if(StringUtil.isNull(line)||line.startsWith(errMsg)){
	  			//continue;
	  			//return "errchdir";
	  			return -1;
	  		}
	  		//获取邮件信息并统计
	
	  		while((line = input.readLine()) != null){
	  			//System.out.println(i++);
	  			int temp=getMailInfo(line);
	  			if(temp!=-1){
	  				if(temp!=-2){
	  					//allMailCount++;
	  					if(temp==0){
	  						unReadCount++;
	  					}
	  				}else{
	  					
	      				break;
	  				}
	  			}else{
	  				//break;
	  				//return "errmailinfo";
	  				return -1;
	  			}
	  			//System.out.println(line);
	  		}
		}catch(Exception e){
			e.printStackTrace();
			return -1;
		}finally {
			if(input !=null){
				try {
					input.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(out !=null){
				out.close();
			}
			if (socket != null){
				try {
					socket.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return unReadCount;
	}
	
	public static void main(String[] args){
		ApplicationContext applicationContext = null;  
		String[] fileUrl = new String[]{"classpath*:*Context*.xml"};  
		applicationContext = new ClassPathXmlApplicationContext(fileUrl);  
		ITaskService task = (ITaskService) SpringBeanUtil.getBean("mailExecute");
		task.exec("");
		//System.out.println(new Date().getTime());
		//System.out.println(new Date().getTime()-24*60*60*1000);
	}
}
