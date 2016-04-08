package com.yisinian.mdfs.test;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;
import com.yisinian.mdfs.orm.Node;
import com.yisinian.mdfs.orm.NodeDAO;

public class TestAction extends ActionSupport {
	protected static final Logger log = Logger.getLogger(TestAction.class);
	private static final long serialVersionUID = 1;
	private NodeDAO nodeDAO;
	public NodeDAO getNodeDAO() {
		return nodeDAO;
	}
	public void setNodeDAO(NodeDAO nodeDAO) {
		this.nodeDAO = nodeDAO;
	}
	
	public String getNodeMessage(){
		log.warn("----------test action testB---------");
		Node aNode = nodeDAO.findById("3");
		log.warn("----------节点id： "+aNode.getNodeId()+" node节点名称："+aNode.getNodeName()+"------------");
		
		return "success";
	}
	
}

