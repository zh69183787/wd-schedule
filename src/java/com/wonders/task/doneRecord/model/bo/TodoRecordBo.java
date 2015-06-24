package com.wonders.task.doneRecord.model.bo;

import com.wonders.task.todoItem.util.DateUtil;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: zhoushun
 * Date: 2015/1/4
 * Time: 10:49
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name ="T_TODO_RECORD")
public class TodoRecordBo implements java.io.Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 1432333768884L;
    private String id;
    private String pname;
    private String pincident;
    private String dealDept;
    private String updateTime;
    private String removed;

    @Transient
    private String taskUser;

    @Transient
    private String deptId;


    @Transient
    public String getTaskUser() {
        return taskUser;
    }

    public void setTaskUser(String taskUser) {
        this.taskUser = taskUser;
    }

    @Transient
    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }


    public TodoRecordBo(){
        this.updateTime = DateUtil.getCurrDate(DateUtil.TIME_FORMAT);
        this.removed = "0";
    }

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "PNAME", length = 50)
    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    @Column(name = "PINCIDENT", length = 50)
    public String getPincident() {
        return pincident;
    }

    public void setPincident(String pincident) {
        this.pincident = pincident;
    }

    @Column(name = "DEAL_DEPT", length = 4000,columnDefinition="VARCHAR2(4000)")
    public String getDealDept() {
        return dealDept;
    }

    public void setDealDept(String dealDept) {
        this.dealDept = dealDept;
    }

    @Column(name = "UPDATE_TIME", length = 19)
    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Column(name = "REMOVED", length = 1)
    public String getRemoved() {
        return removed;
    }

    public void setRemoved(String removed) {
        this.removed = removed;
    }
}
