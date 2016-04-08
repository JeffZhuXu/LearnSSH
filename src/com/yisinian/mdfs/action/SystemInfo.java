package com.yisinian.mdfs.action;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.junit.runner.Request;

import com.opensymphony.xwork2.ActionSupport;
import com.yisinian.mdfs.orm.BlockDAO;
import com.yisinian.mdfs.orm.MdfsfileDAO;
import com.yisinian.mdfs.orm.NodeDAO;
import com.yisinian.mdfs.orm.System;
import com.yisinian.mdfs.orm.SystemDAO;

public class SystemInfo extends ActionSupport {

	//日志文件
	protected static final Logger log = Logger.getLogger(SystemInfo.class);
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
	
	public String getSystemInfo() {
		System aSystem = systemDAO.findById(1);
		JSONObject aJsonObject = new JSONObject();
		aJsonObject.put("name", aSystem.getSystemName());
		aJsonObject.put("establishTimeString", aSystem.getEstablishTime());
		aJsonObject.put("introductionString", aSystem.getIntroduction());
		aJsonObject.put("totalNodeNum", aSystem.getNodeNum());
		aJsonObject.put("liveNodeNum", aSystem.getLiveNodeNum());
		aJsonObject.put("sleepNodeNum", aSystem.getSleepNodeNum());
		aJsonObject.put("deadNodeNum", aSystem.getDeadNodeNum());
		aJsonObject.put("totalStorage", aSystem.getTotalStorage());
		aJsonObject.put("restStorafe", aSystem.getRestStorage());
		aJsonObject.put("blockSize", aSystem.getBlockSize());
		aJsonObject.put("backupTimes", aSystem.getBackupTime());
		aJsonObject.put("netSpeed", aSystem.getNetSpeed());
		aJsonObject.put("compress", aSystem.getCompress());
		aJsonObject.put("adapt", aSystem.getAdaptTransmission());
		
		getRequest().setAttribute("systemMsg", aJsonObject.toString());
		log.warn("-----获取系统的基本信息-----");
		return "success";
		
	}
	//修改节点信息调用的方法
	public String changeSystemInfo() {

		try {
			System aSystem = systemDAO.findById(1);
			getRequest().setCharacterEncoding("UTF-8");
			getResponse().setCharacterEncoding("UTF-8");
			//获取要删除的文件id
			String backup = getParam("backupTimes");	
			String compress = getParam("compress");	
			String adapt = getParam("adapt");	
			String blockSize = getParam("blockSize");	
			aSystem.setBackupTime(Short.valueOf(backup));
			aSystem.setCompress(compress);
			aSystem.setAdaptTransmission(adapt);
			aSystem.setBlockSize(Integer.parseInt(blockSize));
			systemDAO.merge(aSystem);
			log.warn("-----------更新系统的基本信息-------------");
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "success";
	}
}
