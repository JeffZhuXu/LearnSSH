package com.yisinian.mdfs.action;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yisinian.mdfs.orm.Block;
import com.yisinian.mdfs.orm.BlockDAO;
import com.yisinian.mdfs.orm.GetFileTask;
import com.yisinian.mdfs.orm.GetFileTaskDAO;
import com.yisinian.mdfs.orm.Mdfsfile;
import com.yisinian.mdfs.orm.MdfsfileDAO;
import com.yisinian.mdfs.orm.Node;
import com.yisinian.mdfs.orm.NodeDAO;
import com.yisinian.mdfs.orm.NodeFileTask;
import com.yisinian.mdfs.orm.NodeFileTaskDAO;
import com.yisinian.mdfs.orm.NodeTaskDAO;
import com.yisinian.mdfs.orm.SystemDAO;
import com.yisinian.mdfs.orm.TaskDAO;
import com.yisinian.mdfs.tool.MDFSTime;

public class GetLTFileFromNode extends ActionSupport {
	/*
	 *@author zhuxu
	 *@time 上午10:49:192016
	 *@info 
	 *
	 */

	//日志文件
	protected static final Logger LOG = Logger
			.getLogger(GetLTFileFromNode.class);
	//版本号
	private static final long serialVersionUID = 1;

	private SystemDAO systemDAO;
	private MdfsfileDAO mdfsfileDao;
	private NodeDAO nodeDAO;
	private BlockDAO blockDAO;
	private TaskDAO taskDAO;
	private NodeTaskDAO nodeTaskDAO;
	private GetFileTaskDAO getFileTaskDAO;
	private NodeFileTaskDAO nodeFileTaskDAO;
	
	public GetFileTaskDAO getGetFileTaskDAO() {
		return getFileTaskDAO;
	}

	public void setGetFileTaskDAO(GetFileTaskDAO getFileTaskDAO) {
		this.getFileTaskDAO = getFileTaskDAO;
	}

	public NodeFileTaskDAO getNodeFileTaskDAO() {
		return nodeFileTaskDAO;
	}

	public void setNodeFileTaskDAO(NodeFileTaskDAO nodeFileTaskDAO) {
		this.nodeFileTaskDAO = nodeFileTaskDAO;
	}
	
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

	public String getLTFileFromNodes() {
		try {
			getRequest().setCharacterEncoding("UTF-8");
			getResponse().setCharacterEncoding("UTF-8");
			int fileId = Integer.parseInt(getParam("fileId")); //获取要回收的文件id
			Mdfsfile aMdfsfile=mdfsfileDao.findById(fileId);
			GetFileTask aGetFileTask = new GetFileTask();
			aGetFileTask.setFileId(fileId);
			aGetFileTask.setGetFileNum(0);
			aGetFileTask.setAllFileNum(aMdfsfile.getBlockNumber());
			aGetFileTask.setNeedFileNum((int)(aMdfsfile.getBlockNumber()*0.7));
			aGetFileTask.setFileType("0");
			aGetFileTask.setSetTime(MDFSTime.getStandardTimeAsString());
			aGetFileTask.setSucess("0");
			//生成回收任务信息
			getFileTaskDAO.update(aGetFileTask);
			List<Block> blocks=blockDAO.findByFileId(fileId);
			//生成每个节点上的子回收任务
			for (Block aBlock:blocks) {
				NodeFileTask aNodeFileTask = new NodeFileTask();
				aNodeFileTask.setBlockId(aBlock.getBlockId());
				aNodeFileTask.setFileId(aBlock.getFileId());
				aNodeFileTask.setGetFileTask(aGetFileTask);
				aNodeFileTask.setGetState("0");
				aNodeFileTask.setGetTime("0");
				aNodeFileTask.setNodeId(aBlock.getNodeId());
				LOG.warn("blockId:"+aBlock.getBlockId()+" fileID:"+aBlock.getFileId()+" "+" taskID:"+aGetFileTask.getGetFileTaskId()
						+" nodeId:"+aBlock.getNodeId());
				nodeFileTaskDAO.update(aNodeFileTask);
				
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return "success";
	}
}
