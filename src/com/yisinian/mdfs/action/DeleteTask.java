package com.yisinian.mdfs.action;

import java.io.File;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yisinian.mdfs.orm.BlockDAO;
import com.yisinian.mdfs.orm.MdfsfileDAO;
import com.yisinian.mdfs.orm.NodeDAO;
import com.yisinian.mdfs.orm.NodeTaskDAO;
import com.yisinian.mdfs.orm.SystemDAO;
import com.yisinian.mdfs.orm.Task;
import com.yisinian.mdfs.orm.TaskDAO;

public class DeleteTask extends ActionSupport {
	/*
	 *@author zhuxu
	 *@time 下午09:36:442016
	 *@info 
	 *
	 */

	//日志文件
	protected static final Logger LOG = Logger.getLogger(DeleteTask.class);
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

	public String deleteTask() {
		try {
			getRequest().setCharacterEncoding("UTF-8");
			getResponse().setCharacterEncoding("UTF-8");
			
			int taskId=Integer.parseInt(getParam("taskId"));
			Task deleteTask= taskDAO.findById(taskId);
			String fileName= deleteTask.getResultPath();
			//删除结果文件
			File resultFile= new File("d:\\zhuxu\\ROOT\\MDFSResults\\"+fileName);
			if (resultFile.exists()) {
				resultFile.delete();
			}
			taskDAO.delete(deleteTask);
			LOG.warn("---------"+"删除任务："+taskId+"---------");
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

		return "success";
	}
}
