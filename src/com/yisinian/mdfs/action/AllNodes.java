package com.yisinian.mdfs.action;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yisinian.mdfs.orm.BlockDAO;
import com.yisinian.mdfs.orm.MdfsfileDAO;
import com.yisinian.mdfs.orm.Node;
import com.yisinian.mdfs.orm.NodeDAO;
import com.yisinian.mdfs.orm.SystemDAO;
//获取所有节点信息的口接口
public class AllNodes extends ActionSupport {

	
	
	
	//日志文件
	protected static final Logger log = Logger.getLogger(AllNodes.class);
	//版本号
	private static final long serialVersionUID = 1;

	private SystemDAO systemDAO;
	private MdfsfileDAO mdfsfileDao;
	private NodeDAO nodeDAO;
	private BlockDAO blockDAO;
	
	public MdfsfileDAO getMdfsfileDao() {
		return mdfsfileDao;
	}

	public void setMdfsfileDao(MdfsfileDAO mdfsfileDao) {
		this.mdfsfileDao = mdfsfileDao;
	}


	public NodeDAO getNodeDAO() {
		return nodeDAO;
	}

	public void setNodeDAO(NodeDAO nodeDAO) {
		this.nodeDAO = nodeDAO;
	}

	public BlockDAO getBlockDAO() {
		return blockDAO;
	}

	public void setBlockDAO(BlockDAO blockDAO) {
		this.blockDAO = blockDAO;
	}

	public SystemDAO getSystemDAO() {
		return systemDAO;
	}

	public void setSystemDAO(SystemDAO systemDAO) {
		this.systemDAO = systemDAO;
	}

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
	
	public String getAllNodes() {
		
		JSONArray allNodesArray = new JSONArray();
		List<Node> nodes = nodeDAO.findAll();
		
		for (int i = 0; i < nodes.size(); i++) {
			
			JSONObject aNode = new JSONObject();
			aNode.put("nodeId", nodes.get(i).getNodeId());
			aNode.put("nodeName", nodes.get(i).getNodeName());
			aNode.put("company", nodes.get(i).getCompany());
			aNode.put("phoneType", nodes.get(i).getPhoneType());
			aNode.put("os", nodes.get(i).getOs());
			aNode.put("osVersion", nodes.get(i).getOsVersion());
			aNode.put("storage", nodes.get(i).getStorage());
			aNode.put("restStorage", nodes.get(i).getRestStorage());
			aNode.put("netType", nodes.get(i).getNetType());
			aNode.put("netSpeed", nodes.get(i).getNetSpeed());
			aNode.put("state", nodes.get(i).getState());
			allNodesArray.add(aNode);
		}
		getRequest().setAttribute("allNodesMsg", allNodesArray.toArray());
		log.warn("node 个数："+allNodesArray.size());
		log.warn("··········获取所有的节点信息··········");
		log.warn(allNodesArray.toString());
		return "success";
	}
	
	
}
