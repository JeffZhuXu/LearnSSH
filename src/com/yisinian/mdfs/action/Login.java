package com.yisinian.mdfs.action;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.junit.runner.Request;

import com.opensymphony.xwork2.ActionSupport;
import com.yisinian.mdfs.orm.Block;
import com.yisinian.mdfs.orm.BlockDAO;
import com.yisinian.mdfs.orm.MdfsfileDAO;
import com.yisinian.mdfs.orm.Node;
import com.yisinian.mdfs.orm.NodeDAO;
import com.yisinian.mdfs.orm.System;
import com.yisinian.mdfs.orm.SystemDAO;

public class Login extends ActionSupport {

	//��־�ļ�
	protected static final Logger log = Logger.getLogger(Login.class);
	//�汾��
	private static final long serialVersionUID = 1;
	
	private SystemDAO systemDAO;
	private MdfsfileDAO mdfsfileDao;
	private NodeDAO nodeDAO;
	private BlockDAO blockDAO;
	

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


	
	
	public SystemDAO getSystemDAO() {
		return systemDAO;
	}

	public void setSystemDAO(SystemDAO systemDAO) {
		this.systemDAO = systemDAO;
	}
	//�򿪵�¼����
	public String start() {
		return "success";
	}
	//��Ҫ��֤�˻�����
	public String login() {
		
		JSONObject loginMessage = new JSONObject();
		String user = getParam("user");
		String userpassword =getParam("userpassword");
		System aSystem = systemDAO.findById(1);
		if(user.equals(aSystem.getSystemName())&&userpassword.equals(aSystem.getSystemPassword())){
			String nodeNumber = nodeDAO.countAllNumber();//��ȡ�ڵ������
			String fileNumber = mdfsfileDao.countAllNumber();//��ȡ�ܵ��ļ�����
			String blockNumber = blockDAO.countAllNumber();//�����ļ��ķֿ�����
			String liveNodeNumber = nodeDAO.countLiveNode();//��ȡ���߽ڵ�����
			
			log.warn("------ϵͳ���ƣ�"+aSystem.getSystemName()+" ��¼�ɹ���------");
			log.warn("---�ڵ�������"+nodeNumber+"---");
			log.warn("---�ļ�������"+fileNumber+"---");
			log.warn("---�ֿ��ļ�������"+blockNumber+"---");
			log.warn("---���߽ڵ�������"+liveNodeNumber+"---");			
			
			getRequest().setAttribute("nodeNumber", nodeNumber);
			getRequest().setAttribute("fileNumber", fileNumber);
			getRequest().setAttribute("blockNumber", blockNumber);
			getRequest().setAttribute("liveNodeNumber", liveNodeNumber);
			getRequest().setAttribute("user", user);
			getRequest().setAttribute("userpassword", userpassword);
			
			return "success";
		}else{
			log.warn("-----�û��������������-----");
			return "failed";
		}
		
	}
	//����Ҫ�˻�����ֱ����ת
	public String loginWithoutPassword() {
		
		JSONObject loginMessage = new JSONObject();
		System aSystem = systemDAO.findById(1);
			String nodeNumber = nodeDAO.countAllNumber();		//��ȡ�ڵ������
			String fileNumber = mdfsfileDao.countAllNumber();	//��ȡ�ܵ��ļ�����
			String blockNumber = blockDAO.countAllNumber();		//�����ļ��ķֿ�����
			String liveNodeNumber = nodeDAO.countLiveNode();	//��ȡ���߽ڵ�����
			
			String liveBlockNumber = aSystem.getLiveBlockNum().toString();	//��ȡ�����ļ��������
			String cpuNumber = aSystem.getCpuNum().toString();			//��ȡCPU����
			String liveCpuNumber = aSystem.getLiveCpuNum().toString();		//��ȡ����CPU����
			String ram = aSystem.getRam().toString();				//��ȡRAMd��С
			String liveRam = aSystem.getLiveRam().toString();			//��ȡ����RAM��С
			String cpuFrequency = (aSystem.getCpuFrequency()/1000)+"";		//��ȡCPU��Ƶ��С
			String liveCpuFrequency = (aSystem.getLiveCpuFrequency()/1000)+"";	//��ȡ����CPU��Ƶ��С		
			String restStorage = (aSystem.getRestStorage()/1024/1024)+"";		//��ȡʣ��洢�ռ��С
			String liveStorage = (aSystem.getLiveStorage()/1024/1024)+"";		//��ȡ���ߴ洢�ռ��С
			String extendStorage = (aSystem.getExtendStorage()/1024/1024)+"";		//��ȡ����չ���ռ��С
			String liveExtendStorage = (aSystem.getLiveExtendStorage()/1024/1024)+"";	//��ȡ���߿���չ�洢�ռ��С
			
			//��ȡ���߽ڵ�id�б�
			List<String> liveNodesList=nodeDAO.getLiveNodeNumber();
			
			log.warn("------ϵͳ���ƣ�"+aSystem.getSystemName()+" ��¼�ɹ���------");
			log.warn("---�ڵ�������"+nodeNumber+"---");
			log.warn("---�ļ�������"+fileNumber+"---");
			log.warn("---�ֿ��ļ�������"+blockNumber+"---");
			log.warn("---���߽ڵ�������"+liveNodeNumber+"---");			
			log.warn("----���߽ڵ�id�б�"+liveNodesList.toString()+"--------------");
			getRequest().setAttribute("nodeNumber", nodeNumber);
			getRequest().setAttribute("fileNumber", fileNumber);
			getRequest().setAttribute("blockNumber", blockNumber);
			getRequest().setAttribute("liveNodeNumber", liveNodeNumber);
			
			getRequest().setAttribute("liveBlockNumber", liveBlockNumber);
			getRequest().setAttribute("cpuNumber", cpuNumber);
			getRequest().setAttribute("liveCpuNumber", liveCpuNumber);
			getRequest().setAttribute("ram", ram);
			getRequest().setAttribute("liveRam", liveRam);
			getRequest().setAttribute("cpuFrequency", cpuFrequency);
			getRequest().setAttribute("liveCpuFrequency", liveCpuFrequency);
			getRequest().setAttribute("restStorage", restStorage);
			getRequest().setAttribute("liveStorage", liveStorage);
			getRequest().setAttribute("extendStorage", extendStorage);
			getRequest().setAttribute("liveExtendStorage", liveExtendStorage);
			
			return "success";
		
	}
	
	
}
