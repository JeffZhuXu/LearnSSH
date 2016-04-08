package com.yisinian.mdfs.action;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;


public class TestA extends ActionSupport {
	
	protected static final Logger log = Logger.getLogger(TestA.class);
	private static final long serialVersionUID = 1;
	
	public String test(){
		log.warn("--------test action teatA----------");
		return "success";
	}
	public String error(){
		log.warn("--------test action teatAerror----------");
		return "error";
	}

}
