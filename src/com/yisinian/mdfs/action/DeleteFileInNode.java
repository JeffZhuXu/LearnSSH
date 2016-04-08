package com.yisinian.mdfs.action;

import java.io.File;
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
import com.yisinian.mdfs.orm.Mdfsfile;
import com.yisinian.mdfs.orm.MdfsfileDAO;
import com.yisinian.mdfs.orm.NodeDAO;
import com.yisinian.mdfs.orm.SystemDAO;
import com.yisinian.mdfs.tool.FileOption;

public class DeleteFileInNode extends ActionSupport {
	/*
	 *@author zhuxu
	 *@time 下午08:02:102016
	 *@info 
	 *
	 */

	//日志文件
	protected static final Logger LOG = Logger
			.getLogger(DeleteFileInNode.class);
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

	public String deleteFileInNode() {
		
		try {
		getRequest().setCharacterEncoding("UTF-8");
		getResponse().setCharacterEncoding("UTF-8");
		//获取要删除的文件id
		int fileId= Integer.parseInt(getParam("fileId"));
		//找到要删除的文件在数据库中的信息
		Mdfsfile mdfsfile=mdfsfileDao.findById(fileId);
		//数据库里有这条记录的时候
		if (mdfsfile!=null){
			//获取文件保存路径
			String filePathString=mdfsfile.getFilePath();
			FileOption.deleteFile(filePathString);
			LOG.warn("删除源文件："+filePathString);
			//找到该文件的所有分块文件信息
			List<Block> blocks=blockDAO.findByFileId(fileId);
			//删除所有的分块文件
			for (int i = 0; i < blocks.size(); i++) {
				Block aBlock=blocks.get(i);
				String blockPath=aBlock.getBlockPath();
				FileOption.deleteFile(blockPath);
				LOG.warn("删除分块文件："+blockPath);
			}
			//删除数据库中的文件信息，文件块信息级联外键自动删除
			mdfsfileDao.delete(mdfsfile);
		}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "success";
	}
}
