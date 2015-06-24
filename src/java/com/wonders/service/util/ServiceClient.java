/**
 * 
 */
package com.wonders.service.util;



import java.io.File;
import java.io.StringWriter;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.wonders.service.dataexchange.DataImportAPIDelegate;
import com.wonders.service.dataexchange.DataImportAPIService;

/** 
 * @ClassName: ServiceClient 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013年12月10日 下午3:59:57 
 *  
 */
public class ServiceClient {


	  public static void setDataInfo(String userName,String pwd,String result){
			DataImportAPIService service = new DataImportAPIService();
			DataImportAPIDelegate delegate = service.getDataImportAPIHandler();
			String o = delegate.setDataInfo(userName, HelpUtil.getMD5(pwd), new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()), result);
			System.out.println(o);
		}
	  
	  public static void main(String[] args) throws Exception{
		  //eam
		  //c2583acf910d83afb284d5ed6dce0d75
//		  	DataImportAPIService service = new DataImportAPIService();
//			DataImportAPIDelegate delegate = service.getDataImportAPIHandler();
//			String o = delegate.setDataInfo("xunNuo", "5073964bffbf21c7e9b8054e20f9648e", 
//					new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()),
//					"<?xml version=\"1.0\" encoding=\"UTF-8\"?><root date=\"2014-01-02\"  type=\"yysbRequest\">"
//							+ "<request>"
//							+ "<date>2012-03-15</date>"
//							+ "</request>"
//							+ "</root>");
//			System.out.println(o);
		
		DataImportAPIService service = new DataImportAPIService();
		DataImportAPIDelegate delegate = service.getDataImportAPIHandler();
		SAXReader reader = new SAXReader();   
		Document document = reader.read(new File("D://source.xml"));
		StringWriter out = new StringWriter();
        OutputFormat format = OutputFormat.createPrettyPrint();    
        format.setEncoding("GBK");    // 指定XML编码            
        format.setIndent(true);      // 设置是否缩进
        format.setIndent("   ");     // 以空格方式实现缩进
        format.setNewlines(true);    // 设置是否换行
        format.setPadText(true);
       // XMLWriter writer = new XMLWriter(new FileWriter("D://zzz.xml"),format);        
        XMLWriter writer = new XMLWriter(out,format);
        writer.write(document);    
        writer.close(); 
        System.out.println(HelpUtil.getMD5("jgj168"));
		//String o = delegate.setDataInfo("eam", HelpUtil.getMD5("eam2013!"), 
		//		new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()),
		//		out.toString());
		//System.out.println(HelpUtil.getMD5("eam2013!"));
		  
			
	  }

}
