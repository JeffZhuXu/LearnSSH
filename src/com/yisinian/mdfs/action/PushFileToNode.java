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

	//将文件分块推送到节点的接口
	
	
	//日志文件
	protected static final Logger LOG = Logger
			.getLogger(PushFileToNode.class);
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
	public String pushFileToNode() {
		
		String blockPath = "d:\\zhuxu\\ROOT\\MDFSFileBlocks\\"; //文件分块保存的路径
		
		try {
			//获取在线节点的id列表集合，未开启自适应
			List<String> liveNodeNumberList=nodeDAO.getLiveNodeNumber();
			//获取在线节点id列表集合，开启自适应
			List<String> adaptLiveNodeNumberList = new ArrayList<String>();
			//自适应中列表元素个数
			int adaptNodeListNum = 0;
			//在线节点数量
			int liveNodeNumber = liveNodeNumberList.size();
			getRequest().setCharacterEncoding("UTF-8");
			getResponse().setCharacterEncoding("UTF-8");
			
			System system = systemDAO.findById(1);
			//获取文件块大小
			int blockSize =system.getBlockSize();
			//获取备份数
			short backupTime=system.getBackupTime();
			//获取文件下载分配类型，平均分配或者是自适应分配
			String adaptType = system.getAdaptTransmission();
			//如果开启了自适应
			if (adaptType.equals("1")) {
				adaptLiveNodeNumberList = getAdaptNodeId();
				adaptNodeListNum= adaptLiveNodeNumberList.size();
			}
			//获取是否压缩标识
			String compressType = system.getCompress();
			
			int fileId = Integer.parseInt(getParam("fileId")); //获取要push的文件id
			Mdfsfile aMdfsfile=mdfsfileDao.findById(fileId);
			String fileName = aMdfsfile.getFileName();
			String filePath = aMdfsfile.getFilePath();
			String[] fileMsg=fileName.split("\\.");
			String fileTyle=fileMsg[1];//获取后缀名称
			//数据库里有这条记录且当前有节点在线
			if (aMdfsfile!=null&&liveNodeNumberList.size()>0) {
				LOG.warn("需要推送的文件id："+fileId);
				LOG.warn("需要推送的文件名："+fileName);
				LOG.warn("需要推送的文件："+filePath);
				File aFile =  new File(filePath);
				int blockSerialNum = 1;
	             //在线节点ID的次序
	            int nodeNumberSerialNum=0;
	            //用于保存问价处理分割加密压缩等损耗的时间
	            long processTime = java.lang.System.currentTimeMillis();
	        	//如果文件存在的话
				if(aFile.exists()){
					LOG.warn("-----需要push的文件："+filePath+" 存在-----");
					InputStreamReader read = new InputStreamReader(new FileInputStream(aFile),"UTF-8");//考虑到编码格式
					//OutputStreamWriter writer = new OutputStreamWriter(new File(aBlockFile),"UTF-8");
					 char[] buff = new char[blockSize];
		             int len = 0;
		                while ((len=read.read(buff))>0){
		                	//分块文件名称 文件id+"_"+序列号 例如：1_1.txt
		                	String blockNameString =fileId+"_"+blockSerialNum+"."+fileTyle;
		                	
		                	//新建分块文件
		                	File aBlockFile = new File(blockPath+blockNameString);
		                	if(aBlockFile.exists()){
		                		LOG.warn("文件："+filePath+"已经push到节点上了");
		                		//已经push过了，那么直接跳出循环
		                		break;
		                	}else {
		                		
			                	LOG.warn("保存 第"+blockSerialNum+"块"+" 文件类型："+fileTyle+" 文件名："+blockNameString);
			                	FileOutputStream fs = new FileOutputStream(aBlockFile);
			                	OutputStreamWriter os = new OutputStreamWriter(fs, "UTF-8");
			                	//获取文件的大小，单位Byte
			                	int blockFileSize = ByteChar.getCharBufferLengthAsUTF8(buff);
			                	LOG.warn("文件大小："+blockFileSize);
			                	os.write(buff, 0, len);
			                	os.flush();
			                	os.close();
			                	fs.close();
			                	//判定，如果使用了压缩，处理刚才生成的文件块
			                	if (compressType.equals("1")) {
			                		blockNameString = blockNameString+".gz";
			                		//压缩文件
			                		GZipUtils.compress(aBlockFile);
			                		LOG.warn("压缩后文件名："+blockNameString);
								}
			                	
			                	//保存到数据
			                	Block aBlock = new Block();
			                	aBlock.setFileId(fileId);
			                	//如果开启了自适应，就从自适应列表第中随机拿，如果不是自适应，那么平均分
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
			                	//轮流将文件存储到每一个在线节点上
			                	//到最后一个在线节点上的时候，重新从第一个开始
			                	if(nodeNumberSerialNum==liveNodeNumber-1){
			                		nodeNumberSerialNum=0;
			                	}else{
			                		nodeNumberSerialNum++;
			                	}			                		
							}
		                }
		                //更新文件总共的文件块个数
		                aMdfsfile.setBlockNumber(blockSerialNum-1);
		                aMdfsfile.setProcessTime((java.lang.System.currentTimeMillis()-processTime)+"");
		                mdfsfileDao.update(aMdfsfile);
				read.close();
				}else{
					LOG.warn("------需要push的文件："+filePath+" 不存在-----");
				}
			}else{
				//没有节点在线的时候，不执行文件分割操作
				LOG.warn("------没有节点在线，文件上传不执行-----");
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
	//获取在线且网络等级小于10的自适应的文节点列表
	public List<String> getAdaptNodeId(){
		//找到在线的节点ID
		List<Node> liveNodeList = nodeDAO.getAdaptLiveNodeNumber();
		//存放返回的节点ID列表
		List<String> liveNumberNodeList = new ArrayList<String>();
		for (Node aNode:liveNodeList) {
			int netLevel = Integer.parseInt(aNode.getNetLevel());
			String nodeId = aNode.getNodeId();
			//添加节点id，网络等级越好即网络等级值越小，该节点在列表中的个数也就越多，被抽中的概率也就越大
			for (int i = 10; i > netLevel; i--) {
				liveNumberNodeList.add(nodeId);
			}
		}
		return liveNumberNodeList;
	}
}
