package com.yisinian.mdfs.action;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.junit.runner.Request;

import com.opensymphony.xwork2.ActionSupport;
import com.yisinian.mdfs.orm.Block;
import com.yisinian.mdfs.orm.BlockDAO;
import com.yisinian.mdfs.orm.MdfsfileDAO;
import com.yisinian.mdfs.orm.Node;
import com.yisinian.mdfs.orm.NodeDAO;
import com.yisinian.mdfs.orm.System;
import com.yisinian.mdfs.orm.SystemDAO;

public class Login extends ActionSupport {

	//日志文件
	protected static final Logger log = Logger.getLogger(Login.class);
	//版本号
	private static final long serialVersionUID = 1;
	
	private SystemDAO systemDAO;
	private MdfsfileDAO mdfsfileDao;
	private NodeDAO nodeDAO;
	private BlockDAO blockDAO;
	

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
	//打开登录界面
	public String start() {
		return "success";
	}
	//需要验证账户密码
	public String login() {
		
		JSONObject loginMessage = new JSONObject();
		String user = getParam("user");
		String userpassword =getParam("userpassword");
		System aSystem = systemDAO.findById(1);
		if(user.equals(aSystem.getSystemName())&&userpassword.equals(aSystem.getSystemPassword())){
			String nodeNumber = nodeDAO.countAllNumber();//获取节点的数量
			String fileNumber = mdfsfileDao.countAllNumber();//获取总的文件数量
			String blockNumber = blockDAO.countAllNumber();//所有文件的分块数量
			String liveNodeNumber = nodeDAO.countLiveNode();//获取在线节点数量
			
			log.warn("------系统名称："+aSystem.getSystemName()+" 登录成功！------");
			log.warn("---节点数量："+nodeNumber+"---");
			log.warn("---文件数量："+fileNumber+"---");
			log.warn("---分块文件数量："+blockNumber+"---");
			log.warn("---在线节点数量："+liveNodeNumber+"---");			
			
			getRequest().setAttribute("nodeNumber", nodeNumber);
			getRequest().setAttribute("fileNumber", fileNumber);
			getRequest().setAttribute("blockNumber", blockNumber);
			getRequest().setAttribute("liveNodeNumber", liveNodeNumber);
			getRequest().setAttribute("user", user);
			getRequest().setAttribute("userpassword", userpassword);
			
			return "success";
		}else{
			log.warn("-----用户名或者密码错误-----");
			return "failed";
		}
		
	}
	//不需要账户密码直接跳转
	public String loginWithoutPassword() {
		
		JSONObject loginMessage = new JSONObject();
		System aSystem = systemDAO.findById(1);
			String nodeNumber = nodeDAO.countAllNumber();		//获取节点的数量
			String fileNumber = mdfsfileDao.countAllNumber();	//获取总的文件数量
			String blockNumber = blockDAO.countAllNumber();		//所有文件的分块数量
			String liveNodeNumber = nodeDAO.countLiveNode();	//获取在线节点数量
			
			String liveBlockNumber = aSystem.getLiveBlockNum().toString();	//获取在线文件块的数量
			String cpuNumber = aSystem.getCpuNum().toString();			//获取CPU数量
			String liveCpuNumber = aSystem.getLiveCpuNum().toString();		//获取在线CPU数量
			String ram = aSystem.getRam().toString();				//获取RAMd大小
			String liveRam = aSystem.getLiveRam().toString();			//获取在线RAM大小
			String cpuFrequency = (aSystem.getCpuFrequency()/1000)+"";		//获取CPU主频大小
			String liveCpuFrequency = (aSystem.getLiveCpuFrequency()/1000)+"";	//获取在线CPU主频大小		
			String restStorage = (aSystem.getRestStorage()/1024/1024)+"";		//获取剩余存储空间大小
			String liveStorage = (aSystem.getLiveStorage()/1024/1024)+"";		//获取在线存储空间大小
			String extendStorage = (aSystem.getExtendStorage()/1024/1024)+"";		//获取可扩展储空间大小
			String liveExtendStorage = (aSystem.getLiveExtendStorage()/1024/1024)+"";	//获取在线可扩展存储空间大小
			
			//获取在线节点id列表
			List<String> liveNodesList=nodeDAO.getLiveNodeNumber();
			
			log.warn("------系统名称："+aSystem.getSystemName()+" 登录成功！------");
			log.warn("---节点数量："+nodeNumber+"---");
			log.warn("---文件数量："+fileNumber+"---");
			log.warn("---分块文件数量："+blockNumber+"---");
			log.warn("---在线节点数量："+liveNodeNumber+"---");			
			log.warn("----在线节点id列表："+liveNodesList.toString()+"--------------");
			getRequest().setAttribute("nodeNumber", nodeNumber);
			getRequest().setAttribute("fileNumber", fileNumber);
			getRequest().setAttribute("blockNumber", blockNumber);
			getRequest().setAttribute("liveNodeNumber", liveNodeNumber);
			
			getRequest().setAttribute("liveBlockNumber", liveBlockNumber);
			getRequest().setAttribute("cpuNumber", cpuNumber);
			getRequest().setAttribute("liveCpuNumber", liveCpuNumber);
			getRequest().setAttribute("ram", ram);
			getRequest().setAttribute("liveRam", liveRam);
			getRequest().setAttribute("cpuFrequency", cpuFrequency);
			getRequest().setAttribute("liveCpuFrequency", liveCpuFrequency);
			getRequest().setAttribute("restStorage", restStorage);
			getRequest().setAttribute("liveStorage", liveStorage);
			getRequest().setAttribute("extendStorage", extendStorage);
			getRequest().setAttribute("liveExtendStorage", liveExtendStorage);
			
			return "success";
		
	}
	
	
}
