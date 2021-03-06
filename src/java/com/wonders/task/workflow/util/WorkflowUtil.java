/**
 * 
 */
package com.wonders.task.workflow.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.stereotype.Component;



/** 
 * @ClassName: DataExchangeUtil 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年3月12日 上午9:16:40 
 *  
 */

@Component("dataExchangeUtil")
public class WorkflowUtil {
	
	public static Object parseXml(String xml,Class c){
		Document doc = null;
		Object bo = null;
		try {
			doc = DocumentHelper.parseText(xml);
			JAXBContext context = JAXBContext.newInstance(c);
		    Unmarshaller unmarshaller = context.createUnmarshaller();
		    bo = unmarshaller.unmarshal(
		    		 new StringReader(doc.asXML()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bo;
	}
	
	public static String rebuildXml(String xml,String groupId){
		XMLWriter writer = null;	
		StringWriter out = new StringWriter();
		try {
			Document doc = DocumentHelper.parseText(xml);
			Element root = doc.getRootElement();   
			Element attchElement = (Element)doc.selectSingleNode("//root/attachData");
			Element basicDataElement = (Element)doc.selectSingleNode("//root/basicData");
			root.remove(attchElement);
			Element addElement = basicDataElement.addElement("attach");
			addElement.setText(groupId);			
			OutputFormat format = OutputFormat.createPrettyPrint();    
			format.setEncoding("UTF-8");    // 指定XML编码            
			format.setIndent(true);      // 设置是否缩进
			format.setIndent("   ");     // 以空格方式实现缩进
			format.setNewlines(true);    // 设置是否换行
			format.setPadText(true);
			//format.setTrimText(false);
			// XMLWriter writer = new XMLWriter(new FileWriter("D://zzz.xml"),format);        
			writer = new XMLWriter(out,format);		
			writer.write(doc);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				out.flush();
				out.close();
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}    
		
		return out.toString();
	}
	
	
	
	//根据方法名及class名反射调用方法
	public static void invoke(Object c , String name, Object[] obj){
		 Class[] argsClass = new Class[obj.length];
		  for (int i = 0, j = obj.length; i < j; i++) {
		   argsClass[i] = obj[i].getClass();
		  }
		try {
			Method method = c.getClass().getDeclaredMethod(name, argsClass);
			method.setAccessible(true);   
			method.invoke(c);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//根据方法名及class名反射调用方法
		public static void invoke(Object c , String name){
			try {
				Method method = c.getClass().getDeclaredMethod(name);
				method.setAccessible(true);   
				method.invoke(c);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	public static Map<String,Object> generateMap(Object obj) {
		Map<String,Object> map = new HashMap<String,Object>();
		Field[] fields = obj.getClass().getDeclaredFields();
		String varName = null;
		for (int i = 0; i < fields.length; i++) {
			varName = fields[i].getName();
			//System.out.println(varName);
				boolean accessFlag = fields[i].isAccessible();
				fields[i].setAccessible(true);
				Object res = null;
				try {
					res = fields[i].get(obj);
					if(!(res instanceof Collection)){
						map.put(varName, res==null?"":res);
					}

				} catch (Exception e) {
				}
				fields[i].setAccessible(accessFlag);
			}
		return map;
	}
	
	public static void main(String[] args){
		System.out.println(UUID.randomUUID().toString());
	}
}
