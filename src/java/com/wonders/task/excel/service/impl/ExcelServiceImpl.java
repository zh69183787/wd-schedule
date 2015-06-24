/**
 * 
 */
package com.wonders.task.excel.service.impl;

import com.wonders.schedule.util.DbUtil;
import com.wonders.schedule.util.StringUtil;
import com.wonders.task.excel.service.ExcelService;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/** 
 * @ClassName: ExcelServiceImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-5-15 上午9:43:37 
 *  
 */
@Service("excelService")
public class ExcelServiceImpl implements ExcelService{
    //注入hibernateTemplate
    @Resource(name="hibernateTemplate2")
    private HibernateTemplate hibernateTemplate;

    public <T> void saveAll(List<T> result){
        this.hibernateTemplate.saveOrUpdateAll(result);
    }

	public List<Map<String,Object>> getData(String sql){
		return DbUtil.getJdbcTemplate("stptinc").queryForList(sql);
	}

    /**
     * 返回对应BO对象
     * @param sql
     * @param c
     * @param <T>
     * @return
     */
    public <T> List<T> getBoInfo(String sql,Class<T> c){
        return DbUtil.getJdbcTemplate("stptinc").query(sql, new BeanPropertyRowMapper<T>(c));
    }

    /**
     * 返回map对象
     * @param sql
     * @param key
     * @param value
     * @return
     */
    public Map<String,String> getMapInfo(String sql,String key,String value){
        Map<String,String> result = new HashMap<String, String>();
        List<Map<String,Object>> list = DbUtil.getJdbcTemplate("stptinc").queryForList(sql);
        for(Map<String,Object> obj : list){
            result.put(StringUtil.getNotNullValueString(obj.get(key)), StringUtil.getNotNullValueString(obj.get(value)));
        }
        return result;
    }

    /**
     * 返回map对象
     * @param sql
     * @param key
     * @param value
     * @return
     */
    public Map<String,List<String>> getMapListInfo(String sql,String key,String value,Object[] object){
        Map<String,List<String>> result = new HashMap<String, List<String>>();
        List<Map<String,Object>> list = DbUtil.getJdbcTemplate("stptinc").queryForList(sql,object);
        String k , v;
        for(Map<String,Object> obj : list){
            k = StringUtil.getNotNullValueString(obj.get(key));
            v = StringUtil.getNotNullValueString(obj.get(value));
            if(result.containsKey(k)){
                result.get(k).add(v);
            }else{
                List<String> temp = new ArrayList<String>();
                temp.add(v);
                result.put(k,temp);
            }
        }
        return result;
    }
}
