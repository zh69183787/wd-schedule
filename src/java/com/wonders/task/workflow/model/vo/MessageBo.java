package com.wonders.task.workflow.model.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.wonders.schedule.util.StringUtil;

public class MessageBo {
	
	public MessageBo(String textCn){
		this.textCn = StringUtil.getNotNullValueString(textCn);
	}

	/**
	 * 提示信息
	 */
	@Expose
	@SerializedName("错误信息")
	public String textCn = "";
}
