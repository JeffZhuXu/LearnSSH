package com.yisinian.mdfs.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
import com.yisinian.mdfs.orm.Node;
import com.yisinian.mdfs.orm.NodeDAO;
import com.yisinian.mdfs.orm.NodeTaskDAO;
import com.yisinian.mdfs.orm.System;
import com.yisinian.mdfs.orm.SystemDAO;
import com.yisinian.mdfs.orm.TaskDAO;
import com.yisinian.mdfs.tool.ByteChar;
import com.yisinian.mdfs.tool.FileLTCodeUtils;
import com.yisinian.mdfs.tool.GZipUtils;
import com.yisinian.mdfs.tool.MDFSTime;

public class PushLTFileToNode extends ActionSupport {
	/*
	 *@author zhuxu
	 *@time ����02:31:46 2016
	 *@info ���ļ�����LT����룬��push���洢�ڵ��ϵĽӿ�
	 *
	 */

	//��־�ļ�
	protected static final Logger LOG = Logger
			.getLogger(PushLTFileToNode.class);
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

	public String pushLTFileToNode() {

		
		String blockPath = "d:\\zhuxu\\ROOT\\codes\\"; //�ļ��ֿ鱣���·��
		
		try {
			//��ȡ���߽ڵ��id�б��ϣ�δ��������Ӧ
			List<String> liveNodeNumberList=nodeDAO.getLiveNodeNumber();
			//��ȡ���߽ڵ�id�б��ϣ���������Ӧ
			List<String> adaptLiveNodeNumberList = new ArrayList<String>();
			//����Ӧ���б�Ԫ�ظ���
			int adaptNodeListNum = 0;
			//���߽ڵ�����
			int liveNodeNumber = liveNodeNumberList.size();
			getRequest().setCharacterEncoding("UTF-8");
			getResponse().setCharacterEncoding("UTF-8");
			
			System system = systemDAO.findById(1);
			//��ȡ�ļ����С
			int blockSize =system.getBlockSize();
			//��ȡ������
			short backupTime=system.getBackupTime();
			LOG.warn("��������"+backupTime);
			
			//��ȡ�ļ����ط������ͣ�ƽ���������������Ӧ����
			String adaptType = system.getAdaptTransmission();
			//�������������Ӧ
			if (adaptType.equals("1")) {
				adaptLiveNodeNumberList = getAdaptNodeId();
				adaptNodeListNum= adaptLiveNodeNumberList.size();
			}
			//��ȡ�Ƿ�ѹ����ʶ
			String compressType = system.getCompress();
			
			int fileId = Integer.parseInt(getParam("fileId")); //��ȡҪpush���ļ�id
			Mdfsfile aMdfsfile=mdfsfileDao.findById(fileId);
			String fileStatus = aMdfsfile.getDeleteStatus();	//�ļ���״̬��0��ʾδpush��1��ʾ�ָ�push��2��ʾLT push
			String fileName = aMdfsfile.getFileName();
			String filePath = aMdfsfile.getFilePath();
			String[] fileMsg=fileName.split("\\.");
			String fileTyle=fileMsg[fileMsg.length-1];//��ȡ��׺����
			//���ݿ�����������¼�ҵ�ǰ�нڵ�����
			if (aMdfsfile!=null&&liveNodeNumberList.size()>0&&fileStatus.equals("0")) {
				LOG.warn("��Ҫ���͵��ļ�id��"+fileId);
				LOG.warn("��Ҫ���͵��ļ�����"+fileName);
				LOG.warn("��Ҫ���͵��ļ���"+filePath);
				
	            //���ڱ����ļ�����ָ����ѹ������ĵ�ʱ��
	            long processTime = java.lang.System.currentTimeMillis();
	            
				//���ɱ����
				FileLTCodeUtils.fileLTencode(fileName, fileId+"", backupTime);
				LOG.warn("LT�����ļ�����"+fileName+" �ļ�ID��"+fileId+" ��������ȣ�"+backupTime);
				//����鱣��Ŀ¼
				File codeMdir = new File(blockPath);
       
				int blockSerialNum = 1;
	             //���߽ڵ�ID�Ĵ���
	            int nodeNumberSerialNum=0;
	            
	         	//�ж������ʹ����ѹ��������ղ����ɵ��ļ���
            	if (compressType.equals("1")&&codeMdir.isDirectory()) {
            		LOG.warn("---------ʹ��ѹ��---------");
            		for (File aCodeFile : codeMdir.listFiles()) {
						String codeFileName = aCodeFile.getName();
						String codeFilePath = aCodeFile.getAbsolutePath();
						long fileSize = aCodeFile.length();
						if (codeFileName.indexOf(fileId+"")==0) {
							String compressCodeName=GZipUtils.compress(aCodeFile);
							codeFilePath.replaceAll(codeFileName, compressCodeName);
		                	//���浽���ݿ�
		                	Block aBlock = new Block();
		                	aBlock.setFileId(fileId);
		                	//�������������Ӧ���ʹ�����Ӧ�б��������ã������������Ӧ����ôƽ����
		                	if (adaptType.equals("1")) {
		                		aBlock.setNodeId(adaptLiveNodeNumberList.get((int)(Math.random()*adaptNodeListNum)));
							}else {
								aBlock.setNodeId(liveNodeNumberList.get((int)(Math.random()*liveNodeNumberList.size())));
							}
		                	aBlock.setBlockName(compressCodeName);
		                	aBlock.setFileSerialNumber(blockSerialNum);
		                	aBlock.setBlockPath(codeFilePath);
		                	aBlock.setBlockSize((int)fileSize);
		                	aBlock.setBlockSetTime(MDFSTime.getStandardTimeAsString());
		                	aBlock.setState("0");
		                	aBlock.setStateTime("0000-00-00 00:00:00");
		                	aBlock.setDownloadTime("0");
		                	blockDAO.update(aBlock);
		                	blockSerialNum++;
						}
					}
				}else if(codeMdir.isDirectory()) {//���û��ʹ��ѹ��
					LOG.warn("---------δʹ��ѹ��---------");
            		for (File aCodeFile : codeMdir.listFiles()) {
						String codeFileName = aCodeFile.getName();
						String codeFilePath = aCodeFile.getAbsolutePath();
						long fileSize = aCodeFile.length();
						if (codeFileName.indexOf(fileId+"")==0) {
		                	//���浽���ݿ�
		                	Block aBlock = new Block();
		                	aBlock.setFileId(fileId);
		                	//�������������Ӧ���ʹ�����Ӧ�б��������ã������������Ӧ����ôƽ����
		                	if (adaptType.equals("1")) {
		                		aBlock.setNodeId(adaptLiveNodeNumberList.get((int)(Math.random()*adaptNodeListNum)));
							}else {
								aBlock.setNodeId(liveNodeNumberList.get((int)(Math.random()*liveNodeNumberList.size())));
							}
		                	aBlock.setBlockName(codeFileName);
		                	aBlock.setFileSerialNumber(blockSerialNum);
		                	aBlock.setBlockPath(codeFilePath);
		                	aBlock.setBlockSize((int)fileSize);
		                	aBlock.setBlockSetTime(MDFSTime.getStandardTimeAsString());
		                	aBlock.setState("0");
		                	aBlock.setStateTime("0000-00-00 00:00:00");
		                	aBlock.setDownloadTime("0");
		                	blockDAO.update(aBlock);
		                	blockSerialNum++;
						}
					}
				}
                //�����ļ��ܹ����ļ������
                aMdfsfile.setBlockNumber(blockSerialNum-1);
                aMdfsfile.setProcessTime((java.lang.System.currentTimeMillis()-processTime)+"");
                aMdfsfile.setDeleteStatus("2");
                mdfsfileDao.update(aMdfsfile);
                LOG.warn("------LT �� push �ļ�-----");
                LOG.warn("�ļ�ID��"+fileId);
			}else{
				//û�нڵ����ߵ�ʱ�򣬲�ִ���ļ��ָ����
				LOG.warn("------û�нڵ����ߣ��ļ��ϴ���ִ��-----");
				LOG.warn("------���߽ڵ����:"+liveNodeNumber+"-----");
			}
				
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "success";
	}
	
	//��ȡ����������ȼ�С��10������Ӧ���Ľڵ��б�
	public List<String> getAdaptNodeId(){
		//�ҵ����ߵĽڵ�ID
		List<Node> liveNodeList = nodeDAO.getAdaptLiveNodeNumber();
		//��ŷ��صĽڵ�ID�б�
		List<String> liveNumberNodeList = new ArrayList<String>();
		for (Node aNode:liveNodeList) {
			int netLevel = Integer.parseInt(aNode.getNetLevel());
			String nodeId = aNode.getNodeId();
			//��ӽڵ�id������ȼ�Խ�ü�����ȼ�ֵԽС���ýڵ����б��еĸ���Ҳ��Խ�࣬�����еĸ���Ҳ��Խ��
			for (int i = 10; i > netLevel; i--) {
				liveNumberNodeList.add(nodeId);
			}
		}
		return liveNumberNodeList;
	}
}
