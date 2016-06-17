package com.yisinian.mdfs.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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
import com.yisinian.mdfs.constant.StatusCode;
import com.yisinian.mdfs.orm.Block;
import com.yisinian.mdfs.orm.BlockDAO;
import com.yisinian.mdfs.orm.GetFileTaskDAO;
import com.yisinian.mdfs.orm.MdfsfileDAO;
import com.yisinian.mdfs.orm.Node;
import com.yisinian.mdfs.orm.NodeDAO;
import com.yisinian.mdfs.orm.NodeFileTask;
import com.yisinian.mdfs.orm.NodeFileTaskDAO;
import com.yisinian.mdfs.orm.NodeTask;
import com.yisinian.mdfs.orm.NodeTaskDAO;
import com.yisinian.mdfs.orm.SystemDAO;
import com.yisinian.mdfs.orm.TaskDAO;
import com.yisinian.mdfs.tool.MDFSTime;

public class NodeUpdate extends ActionSupport {

	//��־�ļ�
	protected static final Logger log = Logger.getLogger(NodeUpdate.class);
	//�汾��
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
	
	public String nodeUpdate(){
		
		try {
			
			getRequest().setCharacterEncoding("UTF-8");
			getResponse().setCharacterEncoding("UTF-8");
			JSONObject results= new JSONObject();//����ֵ
			JSONObject blockMsg = new JSONObject();//��Žڵ���Ϣ
			JSONArray taskMsg = new JSONArray();	//��Žڵ���Ӧ���е�������Ϣ
			JSONArray fileUploadMsg = new JSONArray();	//��Žڵ���Ӧ���ϴ����ļ�����Ϣ
			
			List<Node> nodes =nodeDAO.findByProperty("nodeId", getParam("node_id").toString());
			//�ҵ��ڵ���Ϣ

			if (nodes.size()>0){
				String nodeId= getParam("node_id").toString();
				
				Node aNode = nodes.get(0);
				String relayTime = getParam("relay_time").toString();
				//����״���ȼ���10Ϊ��ȼ�������10�Ļ�����ʾ�ýڵ㲻����
				int netLevel =10;
				netLevel = (int)(Float.valueOf(relayTime)/50);
				
				
				aNode.setNodeId(getParam("node_id").toString());
				aNode.setRestLocalStorage(getParam("rest_local_storage").toString());
				aNode.setStorage(getParam("storage").toString());
				aNode.setRestStorage(getParam("rest_storage").toString());		
				aNode.setNetType(getParam("net_type").toString());
				aNode.setNetSpeed(getParam("net_speed").toString());
				aNode.setState(getParam("state").toString());
				aNode.setRelayTime(relayTime);
				aNode.setNetLevel(netLevel+"");
				
				aNode.setEndTime(MDFSTime.getStandardTimeAsString());
				nodeDAO.merge(aNode); // �½����߸��½ڵ�����
				
				//�ҳ�����ڵ������еķֿ��ļ�
				List<Block> blocks=blockDAO.findByNodeId(nodeId);
				for (int i = 0; i < blocks.size(); i++) {
					Block aBlock = blocks.get(i);
					blockMsg.put(aBlock.getBlockId(), aBlock.getBlockName());
				}
				//�ҵ��ýڵ������е�����,������
				List<NodeTask> tasks=nodeTaskDAO.findByNodeIdAndState(nodeId);
				for (int i = 0; i < tasks.size(); i++) {
					NodeTask aTask = tasks.get(i);
					//������δ���״̬��ʱ��
						JSONObject aNodeTask = new JSONObject();
						aNodeTask.put("nodeTaskId", aTask.getNodeTaskId());
						aNodeTask.put("taskId", aTask.getTaskId());
						aNodeTask.put("content", aTask.getContent());
						aNodeTask.put("taskType", aTask.getType());
						aNodeTask.put("taskType", aTask.getType());
						aNodeTask.put("blockId", aTask.getBlockId());
						taskMsg.add(aNodeTask);
				}
				
				//�ҵ��ýڵ���Ӧ���ϴ����ļ���
				List<NodeFileTask> nodeFileTasks=nodeFileTaskDAO.findUnfinishNodeLTtaskByNodeId(nodeId);
				for (NodeFileTask aNodeFileTask:nodeFileTasks) {
					fileUploadMsg.add(aNodeFileTask.getBlockId());
				}
				results.put("statusCode", StatusCode.SUCESS);//������ȷ��
				results.put("nodeId", getParam("node_id").toString());
				results.put("nodeId", getParam("node_id").toString());
				results.put("blockMsg", blocks);//���ظýڵ���Ӧ�ô�ŵĽڵ���Ϣ
				results.put("taskMsg", taskMsg);//���ظýڵ���Ӧ��ִ�е�����
				results.put("fileUploadMsg", fileUploadMsg);//���ظýڵ���Ӧ���ϴ����ļ���
				log.warn("MDFS------�ڵ���³ɹ�------");
				log.warn("�ڵ�id��"+getParam("node_id").toString());
			}else {
				results.put("statusCode", StatusCode.FAILD);//���ش�����
				results.put("node_id", getParam("node_id").toString());
				log.warn("MDFS------�ڵ����ʧ��------");
				log.warn("�ڵ�id��"+getParam("node_id").toString()+"������");
			}
			PrintWriter out=getResponse().getWriter();
			out.println(results.toString());
			out.flush();
			out.close();
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "success";
	}
	
	
}
