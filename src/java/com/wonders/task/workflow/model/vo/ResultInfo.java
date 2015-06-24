package com.wonders.task.workflow.model.vo;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class ResultInfo {
		
	
	/**效验结果*/
	@Expose
	public boolean checkFlag = true;
	
	/**
	 * 错误信息
	 */
	@Expose
	private List<MessageBo> errors = new ArrayList<MessageBo>();
	
	
	public void addErrors(MessageBo error){
		setFalse();
		errors.add(error);
		//log.debug(error.textCn);
	}
	
	public void setFalse(){
		checkFlag = false;
	}
	
	
	
	/**合并错误
	 * @param resultAdd
	 */
	public void mergeResultInfo(ResultInfo resultAdd){
		if(resultAdd!=null&&resultAdd.getErrors().size()>0){
			for(int i=0;i<resultAdd.getErrors().size();i++){
				this.addErrors(resultAdd.getErrors().get(i));
			}
		}
	}
	
	/**是否可以进行流程操作
	 * @return
	 */
	public boolean getOperateFlag(){
		return this.checkFlag;
	}

	public List<MessageBo> getErrors() {
		return errors;
	}
}
