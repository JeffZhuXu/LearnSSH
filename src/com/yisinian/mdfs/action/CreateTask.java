package com.yisinian.mdfs.action;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import sun.util.logging.resources.logging;

import com.mysql.jdbc.log.Log;
import com.opensymphony.xwork2.ActionSupport;
import com.sun.xml.internal.ws.api.model.wsdl.WSDLBoundOperation.ANONYMOUS;
import com.yisinian.mdfs.orm.Block;
import com.yisinian.mdfs.orm.BlockDAO;
import com.yisinian.mdfs.orm.MdfsfileDAO;
import com.yisinian.mdfs.orm.NodeDAO;
import com.yisinian.mdfs.orm.NodeTask;
import com.yisinian.mdfs.orm.NodeTaskDAO;
import com.yisinian.mdfs.orm.SystemDAO;
import com.yisinian.mdfs.orm.Task;
import com.yisinian.mdfs.orm.TaskDAO;
import com.yisinian.mdfs.tool.MDFSTime;

public class CreateTask extends ActionSupport {
	/*
	 *@author zhuxu
	 *@time 下午05:37:352016
	 *@info 
	 *
	 */

	//日志文件
	protected static final Logger LOG = Logger.getLogger(CreateTask.class);
	//版本号
	private static final long serialVersionUID = 1;

	private SystemDAO systemDAO;
	private MdfsfileDAO mdfsfileDao;
	private NodeDAO nodeDAO;
	private BlockDAO blockDAO;
	private TaskDAO taskDAO;
	private NodeTaskDAO nodeTaskDAO;
	
	public TaskDAO getTaskDAO() {
		return taskDAO;
	}

	public void setTaskDAO(TaskDAO taskDAO) {
		this.taskDAO = taskDAO;
	}

	public NodeTaskDAO getNodeTaskDAO() {
		return nodeTaskDAO;
	}

	public void setNodeTaskDAO(NodeTaskDAO nodeTaskDAO) {
		this.nodeTaskDAO = nodeTaskDAO;
	}


	public SystemDAO getSystemDAO() {
		return systemDAO;
	}

	public void setSystemDAO(SystemDAO systemDAO) {
		this.systemDAO = systemDAO;
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

	public String createTask() {
		try {
			getRequest().setCharacterEncoding("UTF-8");
			getResponse().setCharacterEncoding("UTF-8");
			//JSONObject results= new JSONObject();//返回值
			String taskIntroduction=getParam("taskIntroduction");
			String taskType=getParam("taskType");
			String systemFileId=getParam("systemFileId");
			String taskParameter=getParam("taskParameter");
			String resultFileName=System.currentTimeMillis()+".txt";
			
			//新建结果保存的文件
			String filePath = "d:\\zhuxu\\ROOT\\MDFSResults\\"; //文件保存的路径
			File aFile = new File(filePath+resultFileName);
			if(!aFile.exists()){
				aFile.createNewFile();
			}
			
			LOG.warn("----------新建任务介绍："+taskIntroduction+"----------");
			LOG.warn("----------新建任务类型："+taskType+"----------");
			LOG.warn("----------涉及文件："+systemFileId+"----------");
			LOG.warn("----------任务参数："+taskParameter+"----------");
			
			//将新建的任务信息保存到数据库
			Task aTask = new Task();
			aTask.setName(taskIntroduction);
			aTask.setType(taskType);
			aTask.setFileId(Integer.parseInt(systemFileId));
			aTask.setContent(taskParameter);
			aTask.setFinishRate(new Float(0));
			aTask.setFinishTaskNum(0);
			aTask.setNodeTaskNum(Integer.parseInt(blockDAO.countAllNumberForOneFile(systemFileId)));
			aTask.setResultPath(resultFileName);
			aTask.setState("0");
			aTask.setTotalTime("0");
			aTask.setBuildTime(MDFSTime.getStandardTimeAsString());
			taskDAO.save(aTask);
			
			//生成每个移动终端节点上的子任务
			int fileId = aTask.getFileId();
			int taskId = aTask.getTaskId();
			String nodeTaskType = aTask.getType();
			String content=aTask.getContent();
			
			LOG.warn("----------生成子任务----------");
			//找到所有文件块及文件块所处的移动终端节点位置
			List<Block> blocks=blockDAO.findByFileId(fileId);
			
			for (int i = 0; i < blocks.size(); i++) {
				Block aBlock = (Block)blocks.get(i);
				NodeTask aNodeTask = new NodeTask();
				aNodeTask.setTaskId(taskId);
				aNodeTask.setType(nodeTaskType);
				aNodeTask.setFileId(fileId);
				aNodeTask.setBlockId(aBlock.getBlockId());
				aNodeTask.setNodeId(aBlock.getNodeId());
				aNodeTask.setContent(content);
				aNodeTask.setFinishTime("0");
				aNodeTask.setState("0");
				aNodeTask.setBuildTime(MDFSTime.getStandardTimeAsString());
				nodeTaskDAO.save(aNodeTask);
			}
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "success";
	}
}
