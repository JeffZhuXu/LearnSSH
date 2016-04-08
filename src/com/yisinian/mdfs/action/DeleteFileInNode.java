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
	 *@time ����08:02:102016
	 *@info 
	 *
	 */

	//��־�ļ�
	protected static final Logger LOG = Logger
			.getLogger(DeleteFileInNode.class);
	//�汾��
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
		//��ȡҪɾ�����ļ�id
		int fileId= Integer.parseInt(getParam("fileId"));
		//�ҵ�Ҫɾ�����ļ������ݿ��е���Ϣ
		Mdfsfile mdfsfile=mdfsfileDao.findById(fileId);
		//���ݿ�����������¼��ʱ��
		if (mdfsfile!=null){
			//��ȡ�ļ�����·��
			String filePathString=mdfsfile.getFilePath();
			FileOption.deleteFile(filePathString);
			LOG.warn("ɾ��Դ�ļ���"+filePathString);
			//�ҵ����ļ������зֿ��ļ���Ϣ
			List<Block> blocks=blockDAO.findByFileId(fileId);
			//ɾ�����еķֿ��ļ�
			for (int i = 0; i < blocks.size(); i++) {
				Block aBlock=blocks.get(i);
				String blockPath=aBlock.getBlockPath();
				FileOption.deleteFile(blockPath);
				LOG.warn("ɾ���ֿ��ļ���"+blockPath);
			}
			//ɾ�����ݿ��е��ļ���Ϣ���ļ�����Ϣ��������Զ�ɾ��
			mdfsfileDao.delete(mdfsfile);
		}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "success";
	}
}
