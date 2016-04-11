package com.yisinian.mdfs.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yisinian.mdfs.constant.StatusCode;
import com.yisinian.mdfs.orm.BlockDAO;
import com.yisinian.mdfs.orm.MdfsfileDAO;
import com.yisinian.mdfs.orm.Node;
import com.yisinian.mdfs.orm.NodeDAO;
import com.yisinian.mdfs.orm.SystemDAO;

public class NodeLogin extends ActionSupport {

	//��־�ļ�
	protected static final Logger log = Logger.getLogger(NodeLogin.class);
	//�汾��
	private static final long serialVersionUID = 1;

	private SystemDAO systemDAO;
	private MdfsfileDAO mdfsfileDao;
	private NodeDAO nodeDAO;
	private BlockDAO blockDAO;

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
	
	public String nodeLogin() throws IOException{
		getRequest().setCharacterEncoding("UTF-8");
		getResponse().setCharacterEncoding("UTF-8");
		JSONObject results= new JSONObject();//����ֵ
		
		List<Node> nodes =nodeDAO.findByProperty("nodeId", getParam("node_id").toString());
		//�ж�����ڵ��Ƿ���ڣ������ڵĻ��½������ڵĻ���������
		if (nodes.size()==0){
			Node aNode = new Node();
			aNode.setNodeId(getParam("node_id").toString());
			log.warn(getParam("node_id").toString());
			aNode.setSystemId(Integer.parseInt(getParam("system_id").toString()));
			aNode.setNodeName(getParam("node_name").toString());
			aNode.setNodePassword("000000");//���ó�ʼ����
			aNode.setCompany(getParam("company").toString());
			aNode.setPhoneType(getParam("phone_type").toString());
			aNode.setOs(getParam("os").toString());
			aNode.setOsVersion(getParam("os_version").toString());
			aNode.setLocalStorage(getParam("local_storage").toString());
			aNode.setRestLocalStorage(getParam("rest_local_storage").toString());
			aNode.setStorage(getParam("storage").toString());
			aNode.setRestStorage(getParam("rest_storage").toString());		
			aNode.setRam(getParam("ram").toString());
			aNode.setRelayTime("0");
			//������ϴ�����ƵΪN/A��������ΪĬ�ϵ�500
			if(getParam("cpu_frequency").toString().equals("N/A")){
				aNode.setCpuFrequency("1000000");
			}else {
				aNode.setCpuFrequency(getParam("cpu_frequency").toString());
			}
			aNode.setCoreNumber(getParam("core_number").toString());
			aNode.setNetType(getParam("net_type").toString());
			aNode.setNetSpeed(getParam("net_speed").toString());
			aNode.setPhoneModel(getParam("phone_model").toString());
			aNode.setImel(getParam("imel").toString());
			aNode.setSerialNumber(getParam("serial_number").toString());
			aNode.setJpushId(getParam("jpush_id").toString());
			aNode.setState(getParam("state").toString());
			aNode.setStartTime(getParam("start_time").toString());
			aNode.setEndTime(getParam("end_time").toString());
			nodeDAO.merge(aNode); // �½����߸��½ڵ�����
		}
		results.put("statusCode", StatusCode.SUCESS);//����ֵ
		results.put("node_id", getParam("node_id").toString());
		PrintWriter out=getResponse().getWriter();
		out.println(results.toString());
		log.warn("MDFS------�ڵ��¼------");
		log.warn("�ڵ�id��"+getParam("node_id").toString());
		out.flush();
		out.close();
		return "success";
	}
	
	
}
