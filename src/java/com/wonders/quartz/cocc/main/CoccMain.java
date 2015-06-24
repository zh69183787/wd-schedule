/**
 * 
 */
package com.wonders.quartz.cocc.main;

import java.io.StringWriter;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wonders.quartz.cocc.model.vo.CoccListVo;
import com.wonders.quartz.cocc.model.vo.CoccReportVo;
import com.wonders.quartz.cocc.model.xml.CoccReportXml;
import com.wonders.quartz.cocc.service.CoccReportService;
import com.wonders.schedule.util.SpringBeanUtil;
import com.wonders.service.util.ServiceClient;
import com.wonders.task.sample.ITaskService;

/** 
 * @ClassName: CoccMain 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013年12月10日 下午2:17:14 
 *  
 */

@Transactional(value = "stfb-txManager",propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
@Service("coccMain")
public class CoccMain {
	final public static String userName="eam";
	final public static String pwd="eam2013!";
	
	private CoccReportService service;
	
	public CoccReportService getService() {
		return service;
	}
	@Autowired(required=false)
	public void setService(@Qualifier("coccReportService")CoccReportService service) {
		this.service = service;
	}

	public void work(){
		String today = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
		//String today=  "2014-01-13";
		String sql = "select * from (select t.id,t.title,t.source,"
				+ " to_char(t.create_time,'yyyy-mm-dd') as createTime,"
				+ " to_char(t.pub_date,'yyyy-mm-dd') as publishTime, "
				+ " 'http://10.1.44.18/stfb'||t.url||'/con'||t.identified_no||'.htm' as url"
				+ " from tb_content t where t.del_flag=0 and t.sj_id=1254 ) where "
				+ " createTime = '"+today+"'";
		List<CoccReportVo> list = this.service.getResult(sql);
		CoccReportXml xml = new CoccReportXml();
		CoccListVo v = new CoccListVo();
		v.list = list ;
		xml.list = v;
		String result = "";
		try{
			StringWriter writer = new StringWriter();
			JAXBContext context = JAXBContext.newInstance(CoccReportXml.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); 
			m.setProperty(Marshaller.JAXB_ENCODING, "GBK"); //防止文件中文乱码
			m.marshal(xml, writer);
			result = writer.toString();
		}catch(Exception e){
			result = "none";
		}
		System.out.println(result);
		
		//ServiceClient.setDataInfo(userName,pwd,result);
	} 
	

	public static void main(String[] args){
		ApplicationContext applicationContext = null;  
		String[] fileUrl = new String[]{"classpath*:*Context*.xml"};  
		applicationContext = new ClassPathXmlApplicationContext(fileUrl);  
		CoccMain coccMain = (CoccMain) SpringBeanUtil.getBean("coccMain");
		coccMain.work();
		
	}
}
