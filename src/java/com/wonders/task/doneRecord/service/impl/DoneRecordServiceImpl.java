package com.wonders.task.doneRecord.service.impl;

import com.wonders.schedule.util.DbUtil;
import com.wonders.schedule.util.StringUtil;
import com.wonders.task.doneRecord.model.bo.DoneRecordBo;
import com.wonders.task.doneRecord.model.bo.TodoRecordBo;
import com.wonders.task.doneRecord.service.DoneRecordService;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional(value = "txManager2",propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
@Service("doneRecordService")
public class DoneRecordServiceImpl implements DoneRecordService{
	@Autowired
	@Qualifier("hibernateTemplate2")
	private HibernateTemplate h;


    @Autowired
    @Qualifier("dbUtil")
    private DbUtil dbUtil;

    public DbUtil getDbUtil() {
        return dbUtil;
    }

    public void setDbUtil(DbUtil dbUtil) {
        this.dbUtil = dbUtil;
    }

    public HibernateTemplate getH() {
        return h;
    }

    public void setH(HibernateTemplate h) {
        this.h = h;
    }

    public List<DoneRecordBo> getDoneInfo(){
//        String sql = "select r.pname,r.pincident,r.summary,r.task_user taskuser,"
//                + "r.process_status processstatus,r.operate_time operateTime,"
//                + " r.done_time doneTime,r.task_id taskId,r.step_name stepname,"
//                + " case when i.type_name is null then '其他' else type_name end type,"
//                + " case when d.track_status is null then '1' else d.track_status end trackStatus,"
//                + " case when i.type_name is null then '99' else i.orders end orders "
//                + " from v_done_record r left join v_doneconfig_info i "
//                + " on r.pname=i.record_name and r.task_user = i.login_name"
//                + " left join t_done_record d on "
//                + " r.pname = d.pname and r.pincident = d.pincident and r.task_user = d.task_user";

        String sql = "select r.pname,r.pincident,r.summary,r.task_user taskuser,\n" +
                " r.process_status processstatus,r.operate_time operateTime,\n" +
                " r.done_time doneTime,r.task_id taskId,\n" +
                " case when v.deal_dept is null then r.step_name else v.deal_dept end stepname,\n" +
                " case when i.type_name is null then '其他' else type_name end type,\n" +
                " case when d.track_status is null then '1' else d.track_status end trackStatus,\n" +
                " case when i.type_name is null then '99' else i.orders end orders \n" +
                " from v_done_record r left join v_doneconfig_info i \n" +
                " on r.pname=i.record_name and r.task_user = i.login_name\n" +
                " left join t_done_record d on \n" +
                " r.pname = d.pname and r.pincident = d.pincident and r.task_user = d.task_user\n" +
                " left join t_todo_record v on r.pname=v.pname and r.pincident=v.pincident";
        List<DoneRecordBo> list = dbUtil.getJdbcTemplate("stptinc").
                query(sql,new BeanPropertyRowMapper<DoneRecordBo>(DoneRecordBo.class));

        return list;
    }

    public List<TodoRecordBo> getTodoInfo(){
        String sql = "select t.pname,t.pincident,t.taskUser,t.deptId from v_todo_record t";

        List<TodoRecordBo> list = dbUtil.getJdbcTemplate("stptinc").
                query(sql,new BeanPropertyRowMapper<TodoRecordBo>(TodoRecordBo.class));

        return list;
    }

    @Override
    public Map<String,String> getMapResult(String sql,String datasource){
        Map<String,String> map = new HashMap<String, String>();
        List<Map<String,Object>> list = dbUtil.getJdbcTemplate(datasource).queryForList(sql);
        for(Map<String,Object> temp : list){
            map.put(StringUtil.getNotNullValueString(temp.get("a")),StringUtil.getNotNullValueString(temp.get("b")));
        }
        return map;
    }

    @Override
    public <T> List<T> getListResult(String sql,String datasource,java.lang.Class<T> clazz){
        return dbUtil.getJdbcTemplate(datasource).queryForList(sql,clazz);
    }

    @Override
    public <T> Map<String,String> getMapResultByList(String sql,List<T> param ,String datasource){
        Map<String,String> map = new HashMap<String, String>();
        Map<String,Object> parameters = new HashMap<String,Object>();
        parameters.put("user", param);
        NamedParameterJdbcTemplate npjt = new NamedParameterJdbcTemplate(dbUtil.getJdbcTemplate(datasource));
        List<Map<String,Object>> list = npjt.queryForList(sql, parameters);
        for(Map<String,Object> temp : list){
            map.put(StringUtil.getNotNullValueString(temp.get("a")),StringUtil.getNotNullValueString(temp.get("b")));
        }
        return map;
    }

	public void delete(String sql){
        final String temp = sql;
		//DbUtil.getJdbcTemplate("stptinc").update(sql);
        this.h.execute(new HibernateCallback<Object>() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                session.createSQLQuery(temp).executeUpdate();
                return null;
            }
        });
	}
	
	public void save(Collection<?> c){
		this.h.saveOrUpdateAll(c);
        this.h.flush();
	}
}
