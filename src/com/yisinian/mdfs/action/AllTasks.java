package com.yisinian.mdfs.action;

import java.io.UnsupportedEncodingException;
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
import com.yisinian.mdfs.orm.NodeDAO;
import com.yisinian.mdfs.orm.NodeTaskDAO;
import com.yisinian.mdfs.orm.SystemDAO;
import com.yisinian.mdfs.orm.Task;
import com.yisinian.mdfs.orm.TaskDAO;

public class AllTasks extends ActionSupport {
	/*
	 *@author zhuxu
	 *@time 下午03:24:092016
	 *@info 
	 *
	 */

	//日志文件
	protected static final Logger LOG = Logger.getLogger(AllTasks.class);
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

	public String getAllTasks() {
		try {
			getRequest().setCharacterEncoding("UTF-8");
			getResponse().setCharacterEncoding("UTF-8");
			
			JSONArray allTasksArray = new JSONArray();
			List<Task> allTasks = taskDAO.findAll();
			
			for(int i=0;i<allTasks.size();i++){
				JSONObject aTask = new JSONObject();
				Task a = allTasks.get(i);
				aTask.put("taskId", a.getTaskId());
				aTask.put("taskType", a.getType());
				aTask.put("introduction", a.getName());
				aTask.put("fileId", a.getFileId());
				aTask.put("fileName", mdfsfileDao.findById( a.getFileId()).getFileName());
				aTask.put("parameter", a.getContent());
				aTask.put("taskNumber", a.getNodeTaskNum());
				aTask.put("finishNumber", a.getFinishTaskNum());
				aTask.put("finishRate", a.getFinishRate()+"");
				aTask.put("result", a.getResultPath());
				aTask.put("finishTime", a.getTotalTime());
				allTasksArray.add(aTask);
			}
			LOG.warn("-----新建Task，列出所有文件-----");
			//log.warn(allFilesArray.toString());
			getRequest().setAttribute("taskMsg", allTasksArray.toArray());
			
			
			
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "success";
	}
}
