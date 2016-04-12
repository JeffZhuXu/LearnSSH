package com.yisinian.mdfs.action;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class Ping extends ActionSupport {
	/*
	 *@author zhuxu
	 *@time 下午02:47:582016
	 *@info 
	 *
	 */

	//日志文件
	protected static final Logger LOG = Logger.getLogger(Ping.class);
	//版本号
	private static final long serialVersionUID = 1;

	protected String getParam(String key) {
		return ServletActionContext.getRequest().getParameter(key);
	}

	protected HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	protected HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();

	}

	protected HttpSession getSession() {
		return ServletActionContext.getRequest().getSession();
	}

	protected Cookie[] getCookie() {
		return ServletActionContext.getRequest().getCookies();
	}

	public String ping() {
		LOG.warn("------节点ping服务器------");
		return "success";
	}
}
