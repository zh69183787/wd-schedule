package com.wonders.task.asset.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
	
	public static String Date2String(Date date){
		if(date != null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
			return sdf.format(date);
		}
		return "";
	}
	
	public static Date String2Date(String date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
		Date currentdate = null;
		try {
			currentdate = sdf.parse(date);
		} catch (ParseException e) {
		}
		return currentdate;
	}
	
	public static Date Date2Date(Date date){
		return String2Date(Date2String(date));
	}
	
	public static String Date2String(Date date, String pattern){
		if(date != null){
			SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.CHINA);
			return sdf.format(date);
		}
		return "";
	}
	
	public static Date String2Date(String date, String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.CHINA);
		Date currentdate = null;
		try {
			currentdate = sdf.parse(date);
		} catch (ParseException e) {
		}
		return currentdate;
	}
	
	public static Date Date2Date(Date date, String pattern){
		return String2Date(Date2String(date,pattern),pattern);
	}

    public static int timeCompare(String t1,String t2){ 
    	
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");  
        Calendar c1=Calendar.getInstance();  
        Calendar c2=Calendar.getInstance();  
        try {  
            c1.setTime(formatter.parse(t1));  
            c2.setTime(formatter.parse(t2)); 
        } catch (ParseException e) {  
            e.printStackTrace();  
        }  
        int result=c1.compareTo(c2);
        return result;  
    }

	public static boolean isValidDate(String s,String pattern)
    {
		 SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try
        {
             return sdf.format(sdf.parse(s)).equals(s);
         }
        catch (Exception e)
        {
            return false;
        }
    }

}
