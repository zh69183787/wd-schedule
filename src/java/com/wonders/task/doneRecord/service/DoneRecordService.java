package com.wonders.task.doneRecord.service;

import com.wonders.schedule.util.DbUtil;
import com.wonders.task.doneRecord.model.bo.DoneRecordBo;
import com.wonders.task.doneRecord.model.bo.TodoRecordBo;
import org.springframework.orm.hibernate3.HibernateTemplate;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface DoneRecordService {
    public List<TodoRecordBo> getTodoInfo();
	public List<DoneRecordBo> getDoneInfo();
    public Map<String,String> getMapResult(String sql,String datasource);
	public void save(Collection<?> c);
	public void delete(String sql);
    public <T> List<T> getListResult(String sql,String datasource,java.lang.Class<T> clazz);
    public <T> Map<String,String> getMapResultByList(String sql,List<T> param ,String datasource);

    public HibernateTemplate getH();

    public DbUtil getDbUtil();
}
