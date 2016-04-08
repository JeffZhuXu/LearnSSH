package com.yisinian.mdfs.test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yisinian.mdfs.orm.Node;
import com.yisinian.mdfs.orm.NodeDAO;

public class PrintHtml extends ActionSupport {
	protected static final Logger log = Logger.getLogger(PrintHtml.class);
	private static final long serialVersionUID = 1;
	
	
	
	private NodeDAO nodeDAO;
	
	public NodeDAO getNodeDAO() {
		return nodeDAO;
	}
	public void setNodeDAO(NodeDAO nodeDAO) {
		this.nodeDAO = nodeDAO;
	}

	public String printHtml() {
		String nodeId = getParam("nodeId").toString();
		Node aNode = nodeDAO.findById(nodeId);
		getResponse().setContentType("text/html;charset=UTF-8");
		try {
			PrintWriter out = getResponse().getWriter();
			out = getResponse().getWriter();
			out.println("nodeId为 ："+nodeId+" 的节点名称为："+aNode.getNodeName());
			log.warn("··········查看某一个节点的节点名称··········");
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	protected String getParam(String key){
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
	
}
