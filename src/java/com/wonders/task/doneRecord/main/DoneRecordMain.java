package com.wonders.task.doneRecord.main;

import com.wonders.schedule.util.SpringBeanUtil;
import com.wonders.schedule.util.StringUtil;
import com.wonders.task.doneRecord.model.bo.DoneRecordBo;
import com.wonders.task.doneRecord.model.bo.TodoRecordBo;
import com.wonders.task.doneRecord.service.DoneRecordService;
import com.wonders.task.sample.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional(value = "txManager2",propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
@Service("doneRecordMain")
public class DoneRecordMain implements ITaskService{

	@Autowired(required=false)
	private DoneRecordService service;

    private void setDept(TodoRecordBo bo , String dept){
        if(bo.getDealDept() == null){
            bo.setDealDept(dept);
        }else if(bo.getDealDept().indexOf(dept) >= 0){
            return;
        }else if(bo.getDealDept().length() > 0
                && bo.getDealDept().indexOf("、") < 0
                && bo.getDealDept().indexOf("...") < 0){
            bo.setDealDept(bo.getDealDept()+"、"+dept);
        }else if(bo.getDealDept().length() > 0
                && bo.getDealDept().indexOf("、") > 0
                && bo.getDealDept().indexOf("...") < 0){
            bo.setDealDept(bo.getDealDept() + "...");
        }
    }

	@Override
	public String exec(String param) {
        List<String> s = new ArrayList<String>();
        //部门ID列表 旧
        Map<String,String> oldDeptMap = this.service.getMapResult(
                "select distinct v.parent_node_id a ,v.deptName b from v_userdep v","stptinc");
        //部门ID列表 新
        Map<String,String> newDeptMap = this.service.getMapResult(
                "select distinct v.dept_id a,v.dept_name b from v_userdep v","stptdemo");
        //找到所需工号 旧
        List<String> oldLoginNameList = this.service.getListResult(
                "select distinct t.taskUser  from v_todo_record t \n" +
                        "where t.deptId is null and length(t.taskUser)<=16","stptinc",String.class);
        oldLoginNameList.add("zzz");
        //找到所需工号 新
        List<String> newLoginNameList = this.service.getListResult(
                "select distinct t.taskUser  from v_todo_record t \n" +
                        "where t.deptId is null and length(t.taskUser)>16","stptinc",String.class);
        newLoginNameList.add("zzz");
        //工号对应部门 12位
        Map<String,String> name12DeptMap = this.service.getMapResultByList(
                "select distinct a,b from (\n" +
                        "select 'ST/'||login_name a ,deptname b ,\n" +
                        "row_number() over(partition by login_name order by parent_node_id) rn from v_userdep v \n" +
                        "where 'ST/'||v.LOGIN_NAME in (:user)\n" +
                        ") where rn=1", oldLoginNameList, "stptinc");
        //工号对应部门 16位
        Map<String,String> name16DeptMap = this.service.getMapResultByList(
                "select distinct 'ST/'||v.login_name a,\n" +
                        "v.dept_name b\n" +
                        " from v_userdep v where 'ST/'||v.login_name\n" +
                        "in (:user)", newLoginNameList, "stptdemo");

        //初始化 处理中部门
        List<TodoRecordBo> todoList = this.service.getTodoInfo();
        Map<String,TodoRecordBo> todoMap = new HashMap<String, TodoRecordBo>();
        TodoRecordBo temp = null;
        for(TodoRecordBo todo : todoList){
            if(todoMap.containsKey(todo.getPname() + "," + todo.getPincident())) {
                temp = todoMap.get(todo.getPname() + "," + todo.getPincident());
            }else{
                temp = todo;
                todoMap.put(todo.getPname() + "," + todo.getPincident(), temp);
            }

            //12 位 旧
            if (todo.getTaskUser().length() <= 16) {
                if (null == todo.getDeptId() || todo.getDeptId().length() == 0) {
                    //无部门ID
                    this.setDept(temp, StringUtil.getNotNullValueString(name12DeptMap.get(todo.getTaskUser())));
                } else {
                    //有部门ID
                    this.setDept(temp, StringUtil.getNotNullValueString(oldDeptMap.get(todo.getDeptId())));
                }
                //16 位 新
            } else if (todo.getTaskUser().length() > 16) {
                if (null == todo.getDeptId() || todo.getDeptId().length() == 0) {
                    //无部门ID
                    this.setDept(temp, StringUtil.getNotNullValueString(name16DeptMap.get(todo.getTaskUser())));
                } else {
                    //有部门ID
                    this.setDept(temp, StringUtil.getNotNullValueString(newDeptMap.get(todo.getDeptId())));
                }
            }
        }


        List<TodoRecordBo> todoResult = new ArrayList<TodoRecordBo>();
        for(Map.Entry<String,TodoRecordBo> entry : todoMap.entrySet()){
            todoResult.add(entry.getValue());
        }
        //清空当前处理部门记录
        this.service.delete("delete from t_todo_record t");
        this.service.save(todoResult);

        List<DoneRecordBo> doneResult = this.service.getDoneInfo();
        //清空后续跟踪记录
		this.service.delete("delete from t_done_record t");
		this.service.save(doneResult);

		return "";
	}

	
	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
		ApplicationContext applicationContext = null;  
		String[] fileUrl = new String[]{"classpath*:*Context*.xml"};  
		applicationContext = new ClassPathXmlApplicationContext(fileUrl);
        ITaskService task = (DoneRecordMain) SpringBeanUtil.getBean("doneRecordMain");
		task.exec("");
        //System.out.println(task.getClass().getName());
        System.out.println(new DoneRecordBo().getClass().getSuperclass() == Object.class);
	}
}
