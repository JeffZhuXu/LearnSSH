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
	 *@time ����02:47:582016
	 *@info 
	 *
	 */

	//��־�ļ�
	protected static final Logger LOG = Logger.getLogger(Ping.class);
	//�汾��
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
		LOG.warn("------�ڵ�ping������------");
		return "success";
	}
}
