/**   
* @Title: PortalUtil.java 
* @Package com.wonders.task.marquee.util 
* @Description: TODO(用一句话描述该文件做什么) 
* @author zhoushun   
* @date 2014年4月1日 下午4:08:58 
* @version V1.0   
*/
package com.wonders.task.marquee.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;


/** 
 * @ClassName: PortalUtil 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年4月1日 下午4:08:58 
 *  
 */
public class PortalUtil {
	public static String webExec(String urls,String mail,String password) {
		String result = "";
		try {
			URL url = null;
			HttpURLConnection http = null;

			try {
				Thread.sleep(2000);
				url = new URL(urls);
				http = (HttpURLConnection) url.openConnection();
				http.setDoInput(true);
				http.setDoOutput(true);
				http.setUseCaches(false);
				http.setConnectTimeout(50000);
				http.setReadTimeout(50000);
				http.setRequestMethod("POST");
				// http.setRequestProperty("Content-Type",
				// "text/xml; charset=UTF-8");
				http.setRequestProperty("Content-Type",
						"application/x-www-form-urlencoded");
				http.connect();
				String param = "";
//				for(Map.Entry<String, String> entry : maps.entrySet()){
//					param = "&"+entry.getKey()+"="+entry.getValue();
//				}
//				

				param = "&mail="+mail +"&password="+password;
				OutputStreamWriter osw = new OutputStreamWriter(http
						.getOutputStream(), "utf-8");
				osw.write(param);
				osw.flush();
				osw.close();

				if (http.getResponseCode() == 200) {
					BufferedReader in = new BufferedReader(
							new InputStreamReader(http.getInputStream(),
									"utf-8"));
					String inputLine;
					while ((inputLine = in.readLine()) != null) {
						result += inputLine;
					}
					in.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				result = "-1";
			} finally {
				if (http != null)
					http.disconnect();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
