/**   
* @Title: TimeUtil.java 
* @Package com.wonders.task.marquee.util 
* @Description: TODO(用一句话描述该文件做什么) 
* @author zhoushun   
* @date 2014年3月31日 下午1:52:27 
* @version V1.0   
*/
package com.wonders.task.marquee.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 
 * @ClassName: TimeUtil 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年3月31日 下午1:52:27 
 *  
 */
public class HelpUtil {
	/*
	 * 	Date date=new Date();
   		SimpleDateFormat dateFm = new SimpleDateFormat("EEEE");
   		dateFm.format(date);
		注：格式化字符串存在区分大小写
		对于创建SimpleDateFormat传入的参数：EEEE代表周，如“周四”；MMMM代表中文月份，如“十一月”；MM代表月份，如“11”；
		yyyy代表年份，如“2010”；dd代表天，如“25”
	 * 
	 * */
	public static String getWeekOfDate(String date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		Date d = null;
		try {
			d = sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			d= new Date();
		}
		  String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(d);
	        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
	        if (w < 0)
	            w = 0;
	        return weekDays[w];
	}
	
	public static void main(String[] args){
		Map<String,List<String>> map = new HashMap<String,List<String>>();
		List<String> list = new ArrayList<String>();
		list.add("1");
		map.put("a", list);
		map.get("a").add("ddddd");
		System.out.println(map.get("a"));
		
		Map<String,String> saveMapNew = new HashMap<String,String>();
		System.out.println(saveMapNew.size());
		System.out.println(getWeekOfDate("2014-03-30"));
	}
}
