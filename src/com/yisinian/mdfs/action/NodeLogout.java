package com.yisinian.mdfs.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import sun.security.x509.SerialNumber;

import com.opensymphony.xwork2.ActionSupport;
import com.yisinian.mdfs.constant.StatusCode;
import com.yisinian.mdfs.orm.BlockDAO;
import com.yisinian.mdfs.orm.MdfsfileDAO;
import com.yisinian.mdfs.orm.Node;
import com.yisinian.mdfs.orm.NodeDAO;
import com.yisinian.mdfs.orm.SystemDAO;

public class NodeLogout extends ActionSupport {

	//日志文件
	protected static final Logger log = Logger.getLogger(NodeLogout.class);
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
	
	public String nodeLogout() throws IOException{
		getRequest().setCharacterEncoding("UTF-8");
		getResponse().setCharacterEncoding("UTF-8");
		JSONObject results= new JSONObject();//返回值
		//节点id
		String nodeIdString = getParam("node_id").toString();
		List<Node> nodes=nodeDAO.findByProperty("nodeId", nodeIdString);
		if (nodes.size()>0) {
			Node aNode=nodes.get(0);
			aNode.setNetType("0");
			aNode.setNetSpeed("0");
			aNode.setState("0");
			nodeDAO.merge(aNode);
			results.put("statusCode", StatusCode.SUCESS);//返回值
		}else{
			results.put("statusCode", StatusCode.FAILD);//返回值
		}
		PrintWriter out=getResponse().getWriter();
		out.println(results.toString());
		log.warn("------节点登出------");
		log.warn("节点id："+getParam("node_id").toString());
		out.flush();
		out.close();
		return "success";
	}
}
