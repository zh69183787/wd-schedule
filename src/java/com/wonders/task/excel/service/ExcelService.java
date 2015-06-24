/**
 * 
 */
package com.wonders.task.excel.service;

import java.util.List;
import java.util.Map;

/** 
 * @ClassName: ExcelService 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-5-15 上午9:44:05 
 *  
 */
public interface ExcelService {

    public <T> void saveAll(List<T> result);

	public List<Map<String,Object>> getData(String sql);

    /**
     * 返回map对象
     * @param sql
     * @param key
     * @param value
     * @return
     */
    public Map<String,String> getMapInfo(String sql,String key,String value);

    /**
     * 返回map对象
     * @param sql
     * @param key
     * @param value
     * @return
     */
    public Map<String,List<String>> getMapListInfo(String sql,String key,String value,Object[] object);

    /**
     * 返回对应BO对象
     * @param sql
     * @param c
     * @param <T>
     * @return
     */
    public <T> List<T> getBoInfo(String sql,Class<T> c);
}
