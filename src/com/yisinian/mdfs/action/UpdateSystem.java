package com.yisinian.mdfs.action;

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
import com.yisinian.mdfs.orm.System;
import com.yisinian.mdfs.orm.SystemDAO;

/*@author zhuxu
 *@param  null
 *@return String
 * ����ϵͳ��Ϣ�ӿ�
 * */




public class UpdateSystem extends ActionSupport {

	//��־�ļ�
	protected static final Logger LOG = Logger.getLogger(UpdateSystem.class);
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
	
	public String updateSystem(){
		try {
			
			getRequest().setCharacterEncoding("utf-8");
			getResponse().setCharacterEncoding("UTF-8");
			System system = systemDAO.findById(1);
			int blockSize=Integer.parseInt(getParam("blockSize"));	//	��ȡ�ֿ�ߴ�
			short backupTimes=Short.parseShort(getParam("backupTimes")); //��ȡ���ݴ���
			system.setBackupTime(backupTimes);
			system.setBlockSize(blockSize);
			systemDAO.merge(system);
			LOG.warn("������������������������������������������������ϵͳ��Ϣ����������������������������������������������");
			LOG.warn("----------------------������Ϣ--------------------------");
			LOG.warn("���ݴ�����"+backupTimes+" �ļ��ֿ��С��"+blockSize);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "success";
	}
}
