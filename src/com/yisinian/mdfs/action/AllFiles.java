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
import com.yisinian.mdfs.orm.Mdfsfile;
import com.yisinian.mdfs.orm.MdfsfileDAO;
import com.yisinian.mdfs.orm.NodeDAO;
import com.yisinian.mdfs.orm.SystemDAO;

public class AllFiles extends ActionSupport {

	//日志文件
	protected static final Logger log = Logger.getLogger(AllFiles.class);
	//版本号
	private static final long serialVersionUID = 1;

	
	private SystemDAO systemDAO;
	private MdfsfileDAO mdfsfileDao;
	private NodeDAO nodeDAO;
	private BlockDAO blockDAO;
	
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
	
	public String getAllFiles() {
		
		JSONArray allFilesArray = new JSONArray();
		List<Mdfsfile> allFiles = mdfsfileDao.findAll();
		
		for(int i=0;i<allFiles.size();i++){
			
			JSONObject aFile = new JSONObject();
			aFile.put("fileId", allFiles.get(i).getFileId());
			aFile.put("fileName", allFiles.get(i).getFileName());
			aFile.put("fileSize", allFiles.get(i).getFileSize());
			aFile.put("fileUploadTime", allFiles.get(i).getUploadTime());
			aFile.put("fileBlockNumber", allFiles.get(i).getBlockNumber());
			aFile.put("status", allFiles.get(i).getDeleteStatus());
			allFilesArray.add(aFile);
			
			
		}
		log.warn("-----列出所有文件信息-----");
		//log.warn(allFilesArray.toString());
		getRequest().setAttribute("fileMsg", allFilesArray.toArray());
		return "success";
	}
}
