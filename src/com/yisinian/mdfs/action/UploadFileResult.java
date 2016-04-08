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
	 *@time ����10:06:192016
	 *@info 
	 *
	 */

	//��־�ļ�
	protected static final Logger LOG = Logger
			.getLogger(UploadFileResult.class);
	//�汾��
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

	//�ڵ���������ļ���󣬽��ļ����ص�ʱ�����Ϣ������
	public String uploadFileDownloadResult() {
			try {
			
			getRequest().setCharacterEncoding("UTF-8");
			getResponse().setCharacterEncoding("UTF-8");

			int blockId = Integer.parseInt(getParam("blockId"));	//��ȡ�ļ���id
			String downloadTime =getParam("blockDownloadTime");		//��ȡ�ļ�������ʱ��
			//�ҵ����ļ���
			Block aBlock=blockDAO.findById(blockId);
			int fileId =  aBlock.getFileId();					//��������ļ�ID
			Mdfsfile aMdfsfile=mdfsfileDao.findById(fileId);	//�ҵ���Ӧ��Դ�ļ���Ϣ
			aBlock.setDownloadTime(downloadTime);				//��������ʱ��
			aBlock.setState("1");								//��������״̬	
			aBlock.setStateTime(MDFSTime.getStandardTimeAsString());
			//����Դ�ļ����ص�ʱ��
			aMdfsfile.setFileDownloadTime(blockDAO.countTotalDownloadTimeForOneFileByFileId(fileId+""));
			//�������ݿ�
			blockDAO.update(aBlock);
			mdfsfileDao.update(aMdfsfile);
			LOG.warn("------�ڵ㷴���ļ���������Ϣ-------");
			LOG.warn("�ļ���id��"+blockId+" �ļ�������ʱ�䣺"+downloadTime);

			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "success";
	}
}
