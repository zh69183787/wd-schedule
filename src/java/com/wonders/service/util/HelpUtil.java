/**
 * 
 */
package com.wonders.service.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/** 
 * @ClassName: HelpUtil 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013年12月10日 下午3:59:07 
 *  
 */
public class HelpUtil {
	public static String getMD5(String value) {  
        String result = null;  
        try{  
            byte[] valueByte = value.getBytes();  
            MessageDigest md = MessageDigest.getInstance("MD5");  
            md.update(valueByte);  
            result = toHex(md.digest());  
        }catch(NoSuchAlgorithmException e1){  
            e1.printStackTrace();  
        }  
        return result;  
    }  
	
	 // 将传递进来的字节数组转换成十六进制的字符串形式并返回  
    public static String toHex(byte[] buffer){  
        StringBuffer sb = new StringBuffer(buffer.length * 2);  
        for (int i = 0; i < buffer.length; i++){  
            sb.append(Character.forDigit((buffer[i] & 0xf0) >> 4, 16));  
            sb.append(Character.forDigit(buffer[i] & 0x0f, 16));  
        }  
        return sb.toString();  
    }  
    
    private static double compareDate(Date beginDate, Date endDate) {
        Calendar begin = new GregorianCalendar();
        Calendar end = new GregorianCalendar();
        begin.setTime(beginDate);
        end.setTime(endDate);
        int diffDay = ((end.get(Calendar.YEAR) - begin.get(Calendar.YEAR)) * 
        		12 + end.get(Calendar.MONTH) - begin.get(Calendar.MONTH)) * 3;
        double beginDay = Math.ceil(begin.get(Calendar.DATE) / 10.0F) > 3.0D ? 3.0D : 
        	Math.ceil(begin.get(Calendar.DATE) / 10.0F);
        double endDay = Math.ceil(end.get(Calendar.DATE) / 10.0F) > 3.0D ? 3.0D :
        	Math.ceil(end.get(Calendar.DATE) / 10.0F);
        double result = endDay + diffDay - beginDay;
        return result == 0.0D ? 0.0D : result + 1.0D;
      }

      public static void main(String[] args) {
    	  byte b = 123; 
    	  System.out.println((b & 0xf0)>>4);
    	  System.out.println(Integer.toHexString((b & 0xf0)>>4));
    	  System.out.println(Integer.toHexString(b & 0x0f));
        System.out.println(compareDate(new GregorianCalendar(2014, 4, 4).getTime(), new GregorianCalendar(2014, 4, 15).getTime()));
      }


}
