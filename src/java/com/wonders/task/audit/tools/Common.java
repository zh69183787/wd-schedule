package com.wonders.task.audit.tools;

public class Common {
	public boolean isNull(String str){
		if(str==null||str.trim().equals("")||str.trim().equalsIgnoreCase("null")){
			return true;
		}else{
			return false;
		}
	}
}
