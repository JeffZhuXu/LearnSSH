package com.yisinian.mdfs.action;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.yisinian.mdfs.orm.Block;
import com.yisinian.mdfs.orm.BlockDAO;
import com.yisinian.mdfs.orm.Mdfsfile;
import com.yisinian.mdfs.orm.MdfsfileDAO;
import com.yisinian.mdfs.orm.NodeDAO;
import com.yisinian.mdfs.orm.NodeTaskDAO;
import com.yisinian.mdfs.orm.SystemDAO;
import com.yisinian.mdfs.orm.TaskDAO;
import com.yisinian.mdfs.tool.MDFSTime;

public class UploadFileResult {
	/*
	 *@author zhuxu
	 *@time 上午10:06:192016
	 *@info 
	 *
	 */

	//日志文件
	protected static final Logger LOG = Logger
			.getLogger(UploadFileResult.class);
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

	//节点下载完成文件块后，将文件下载的时间等信息传上来
	public String uploadFileDownloadResult() {
			try {
			
			getRequest().setCharacterEncoding("UTF-8");
			getResponse().setCharacterEncoding("UTF-8");

			int blockId = Integer.parseInt(getParam("blockId"));	//获取文件块id
			String downloadTime =getParam("blockDownloadTime");		//获取文件块下载时间
			//找到该文件块
			Block aBlock=blockDAO.findById(blockId);
			int fileId =  aBlock.getFileId();					//获得所在文件ID
			Mdfsfile aMdfsfile=mdfsfileDao.findById(fileId);	//找到对应的源文件信息
			aBlock.setDownloadTime(downloadTime);				//更新下载时间
			aBlock.setState("1");								//更新下载状态	
			aBlock.setStateTime(MDFSTime.getStandardTimeAsString());
			//更新源文件下载的时间
			aMdfsfile.setFileDownloadTime(blockDAO.countTotalDownloadTimeForOneFileByFileId(fileId+""));
			//更新数据库
			blockDAO.update(aBlock);
			mdfsfileDao.update(aMdfsfile);
			LOG.warn("------节点反馈文件块下载信息-------");
			LOG.warn("文件块id："+blockId+" 文件块下载时间："+downloadTime);

			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "success";
	}
}
