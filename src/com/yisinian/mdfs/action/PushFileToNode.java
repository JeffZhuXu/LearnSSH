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
import com.yisinian.mdfs.orm.System;
import com.yisinian.mdfs.orm.SystemDAO;
import com.yisinian.mdfs.tool.ByteChar;
import com.yisinian.mdfs.tool.GZipUtils;
import com.yisinian.mdfs.tool.MDFSTime;

public class PushFileToNode extends ActionSupport {

	//���ļ��ֿ����͵��ڵ�Ľӿ�
	
	
	//��־�ļ�
	protected static final Logger LOG = Logger
			.getLogger(PushFileToNode.class);
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
	public String pushFileToNode() {
		
		String blockPath = "d:\\zhuxu\\ROOT\\MDFSFileBlocks\\"; //�ļ��ֿ鱣���·��
		
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
			String fileName = aMdfsfile.getFileName();
			String filePath = aMdfsfile.getFilePath();
			String[] fileMsg=fileName.split("\\.");
			String fileTyle=fileMsg[1];//��ȡ��׺����
			//���ݿ�����������¼�ҵ�ǰ�нڵ�����
			if (aMdfsfile!=null&&liveNodeNumberList.size()>0) {
				LOG.warn("��Ҫ���͵��ļ�id��"+fileId);
				LOG.warn("��Ҫ���͵��ļ�����"+fileName);
				LOG.warn("��Ҫ���͵��ļ���"+filePath);
				File aFile =  new File(filePath);
				int blockSerialNum = 1;
	             //���߽ڵ�ID�Ĵ���
	            int nodeNumberSerialNum=0;
	            //���ڱ����ʼ۴���ָ����ѹ������ĵ�ʱ��
	            long processTime = java.lang.System.currentTimeMillis();
	        	//����ļ����ڵĻ�
				if(aFile.exists()){
					LOG.warn("-----��Ҫpush���ļ���"+filePath+" ����-----");
					InputStreamReader read = new InputStreamReader(new FileInputStream(aFile),"UTF-8");//���ǵ������ʽ
					//OutputStreamWriter writer = new OutputStreamWriter(new File(aBlockFile),"UTF-8");
					 char[] buff = new char[blockSize];
		             int len = 0;
		                while ((len=read.read(buff))>0){
		                	//�ֿ��ļ����� �ļ�id+"_"+���к� ���磺1_1.txt
		                	String blockNameString =fileId+"_"+blockSerialNum+"."+fileTyle;
		                	
		                	//�½��ֿ��ļ�
		                	File aBlockFile = new File(blockPath+blockNameString);
		                	if(aBlockFile.exists()){
		                		LOG.warn("�ļ���"+filePath+"�Ѿ�push���ڵ�����");
		                		//�Ѿ�push���ˣ���ôֱ������ѭ��
		                		break;
		                	}else {
		                		
			                	LOG.warn("���� ��"+blockSerialNum+"��"+" �ļ����ͣ�"+fileTyle+" �ļ�����"+blockNameString);
			                	FileOutputStream fs = new FileOutputStream(aBlockFile);
			                	OutputStreamWriter os = new OutputStreamWriter(fs, "UTF-8");
			                	//��ȡ�ļ��Ĵ�С����λByte
			                	int blockFileSize = ByteChar.getCharBufferLengthAsUTF8(buff);
			                	LOG.warn("�ļ���С��"+blockFileSize);
			                	os.write(buff, 0, len);
			                	os.flush();
			                	os.close();
			                	fs.close();
			                	//�ж������ʹ����ѹ��������ղ����ɵ��ļ���
			                	if (compressType.equals("1")) {
			                		blockNameString = blockNameString+".gz";
			                		//ѹ���ļ�
			                		GZipUtils.compress(aBlockFile);
			                		LOG.warn("ѹ�����ļ�����"+blockNameString);
								}
			                	
			                	//���浽����
			                	Block aBlock = new Block();
			                	aBlock.setFileId(fileId);
			                	//�������������Ӧ���ʹ�����Ӧ�б��������ã������������Ӧ����ôƽ����
			                	if (adaptType.equals("1")) {
			                		aBlock.setNodeId(adaptLiveNodeNumberList.get((int)(Math.random()*adaptNodeListNum)));
								}else {
									aBlock.setNodeId(liveNodeNumberList.get(nodeNumberSerialNum));
								}
			                	aBlock.setBlockName(blockNameString);
			                	aBlock.setFileSerialNumber(blockSerialNum);
			                	aBlock.setBlockPath(blockPath+blockNameString);
			                	aBlock.setBlockSize(blockFileSize);
			                	aBlock.setBlockSetTime(MDFSTime.getStandardTimeAsString());
			                	aBlock.setState("0");
			                	aBlock.setStateTime("0000-00-00 00:00:00");
			                	aBlock.setDownloadTime("0");
			                	blockDAO.update(aBlock);
			                	blockSerialNum++;
			                	//�������ļ��洢��ÿһ�����߽ڵ���
			                	//�����һ�����߽ڵ��ϵ�ʱ�����´ӵ�һ����ʼ
			                	if(nodeNumberSerialNum==liveNodeNumber-1){
			                		nodeNumberSerialNum=0;
			                	}else{
			                		nodeNumberSerialNum++;
			                	}			                		
							}
		                }
		                //�����ļ��ܹ����ļ������
		                aMdfsfile.setBlockNumber(blockSerialNum-1);
		                aMdfsfile.setProcessTime((java.lang.System.currentTimeMillis()-processTime)+"");
		                mdfsfileDao.update(aMdfsfile);
				read.close();
				}else{
					LOG.warn("------��Ҫpush���ļ���"+filePath+" ������-----");
				}
			}else{
				//û�нڵ����ߵ�ʱ�򣬲�ִ���ļ��ָ����
				LOG.warn("------û�нڵ����ߣ��ļ��ϴ���ִ��-----");
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
