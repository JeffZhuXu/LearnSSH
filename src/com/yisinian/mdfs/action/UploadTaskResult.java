package com.yisinian.mdfs.action;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.yisinian.mdfs.orm.BlockDAO;
import com.yisinian.mdfs.orm.MdfsfileDAO;
import com.yisinian.mdfs.orm.NodeDAO;
import com.yisinian.mdfs.orm.NodeTask;
import com.yisinian.mdfs.orm.NodeTaskDAO;
import com.yisinian.mdfs.orm.SystemDAO;
import com.yisinian.mdfs.orm.Task;
import com.yisinian.mdfs.orm.TaskDAO;
import com.yisinian.mdfs.tool.TaskUtil;

public class UploadTaskResult {
	/*
	 *@author zhuxu
	 *@time 上午11:54:392016
	 *@info 
	 *
	 */

	//日志文件
	protected static final Logger LOG = Logger
			.getLogger(UploadTaskResult.class);
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

	public String uploadTaskResult() {

		try {
			
			getRequest().setCharacterEncoding("UTF-8");
			getResponse().setCharacterEncoding("UTF-8");

			int nodeTaskId = Integer.parseInt(getParam("nodeTaskId"));
			int taskId = Integer.parseInt(getParam("taskId"));
			String taskResult = getParam("taskResult");
			int finishTime = Integer.parseInt(getParam("finishTime"));
			

			NodeTask nodeTask=nodeTaskDAO.findById(nodeTaskId);
			//判断任务类型，决定采用哪一个函数
			if (nodeTask.getType().equals("文件检索")&&!(nodeTask.getState().equals("1"))) {
				
				LOG.warn("----------节点上传任务结果----------");
				LOG.warn("任务taskId:"+taskId);
				LOG.warn("任务nodeTaskId:"+nodeTaskId);
				LOG.warn("任务results:"+taskResult);
				LOG.warn("任务finishTime:"+finishTime);
				
				
				Task task=taskDAO.findById(taskId);

				
				String resultFilePath = "D:\\zhuxu\\ROOT\\MDFSResults\\"+task.getResultPath();
				//保存结果
				TaskUtil.writeSearchResultsIntoFile(resultFilePath, taskResult);
				//更新节点任务状态
				nodeTask.setState("1");
				nodeTask.setFinishTime(finishTime+"");
				nodeTaskDAO.merge(nodeTask);
				
				int allTaskNum = task.getNodeTaskNum();
				int totalTime = Integer.parseInt(task.getTotalTime());
				float finishRate = 0.00f;
				int finishTaskNum = task.getFinishTaskNum();
				finishRate=(float)(finishTaskNum+1)/allTaskNum;
				task.setFinishTaskNum(finishTaskNum+1);
				task.setFinishRate(finishRate);
				task.setTotalTime(totalTime+finishTime+"");
				taskDAO.merge(task);
				
				//任务类型不是文件检索或者任务已经提交过
			}else {
				LOG.warn("----------未知任务类型，任务重复提交----------");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "success";
	}
}
