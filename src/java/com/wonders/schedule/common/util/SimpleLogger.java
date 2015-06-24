package com.wonders.schedule.common.util;

import org.apache.log4j.Logger;

public class SimpleLogger {
	public static Logger getLogger(String name){
		return Logger.getLogger(name);
	}
	@SuppressWarnings("unchecked")
	public static Logger getLogger(Class c){
		return Logger.getLogger(c.getName());
	}
	
	private void init(String name){
		if(log==null) log = SimpleLogger.getLogger(name);
	}
	
	@SuppressWarnings("unchecked")
	private void init(Class c){
		if(log==null) log = SimpleLogger.getLogger(c);
	}
	
	public SimpleLogger(String name){
		this.init(name);
	}
	
	@SuppressWarnings("unchecked")
	public SimpleLogger(Class c){
		this.init(c);
	}
	
	private Logger log;
	
	public void debug(Object obj){
		if(log.isDebugEnabled()){
			log.debug(obj.toString());
		}
	}
	
	public void debug(String message){
		if(log.isDebugEnabled()){
			log.debug(message);
		}
	}
	
	public void info(Object obj){
		if(log.isDebugEnabled()){
			log.info(obj.toString());
		}
	}
	
	public void info(String message){
		if(log.isInfoEnabled()){
			log.info(message);
		}
	}
	
	public void warn(Object obj){
		log.warn(obj.toString());
	}
	
	public void warn(String message){
		log.warn(message);
	}
	
	
	public void error(String message){
		log.error(message);
	}
	
	public void fatal(String message){
		log.fatal(message);
	}
}
