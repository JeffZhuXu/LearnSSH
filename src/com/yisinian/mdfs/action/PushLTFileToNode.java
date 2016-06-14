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
	 *@time 下午02:31:46 2016
	 *@info 将文件进行LT码编码，并push到存储节点上的接口
	 *
	 */

	//日志文件
	protected static final Logger LOG = Logger
			.getLogger(PushLTFileToNode.class);
	//版本号
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

		
		String blockPath = "d:\\zhuxu\\ROOT\\codes\\"; //文件分块保存的路径
		
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
			LOG.warn("备份数："+backupTime);
			
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
			String fileStatus = aMdfsfile.getDeleteStatus();	//文件块状态，0表示未push，1表示分割push，2表示LT push
			String fileName = aMdfsfile.getFileName();
			String filePath = aMdfsfile.getFilePath();
			String[] fileMsg=fileName.split("\\.");
			String fileTyle=fileMsg[fileMsg.length-1];//获取后缀名称
			//数据库里有这条记录且当前有节点在线
			if (aMdfsfile!=null&&liveNodeNumberList.size()>0&&fileStatus.equals("0")) {
				LOG.warn("需要推送的文件id："+fileId);
				LOG.warn("需要推送的文件名："+fileName);
				LOG.warn("需要推送的文件："+filePath);
				
	            //用于保存文件处理分割加密压缩等损耗的时间
	            long processTime = java.lang.System.currentTimeMillis();
	            
				//生成编码块
				FileLTCodeUtils.fileLTencode(fileName, fileId+"", backupTime);
				LOG.warn("LT编码文件名："+fileName+" 文件ID："+fileId+" 编码冗余度："+backupTime);
				//编码块保存目录
				File codeMdir = new File(blockPath);
       
				int blockSerialNum = 1;
	             //在线节点ID的次序
	            int nodeNumberSerialNum=0;
	            
	         	//判定，如果使用了压缩，处理刚才生成的文件块
            	if (compressType.equals("1")&&codeMdir.isDirectory()) {
            		LOG.warn("---------使用压缩---------");
            		for (File aCodeFile : codeMdir.listFiles()) {
						String codeFileName = aCodeFile.getName();
						String codeFilePath = aCodeFile.getAbsolutePath();
						long fileSize = aCodeFile.length();
						if (codeFileName.indexOf(fileId+"")==0) {
							String compressCodeName=GZipUtils.compress(aCodeFile);
							codeFilePath.replaceAll(codeFileName, compressCodeName);
		                	//保存到数据库
		                	Block aBlock = new Block();
		                	aBlock.setFileId(fileId);
		                	//如果开启了自适应，就从自适应列表第中随机拿，如果不是自适应，那么平均分
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
				}else if(codeMdir.isDirectory()) {//如果没有使用压缩
					LOG.warn("---------未使用压缩---------");
            		for (File aCodeFile : codeMdir.listFiles()) {
						String codeFileName = aCodeFile.getName();
						String codeFilePath = aCodeFile.getAbsolutePath();
						long fileSize = aCodeFile.length();
						if (codeFileName.indexOf(fileId+"")==0) {
		                	//保存到数据库
		                	Block aBlock = new Block();
		                	aBlock.setFileId(fileId);
		                	//如果开启了自适应，就从自适应列表第中随机拿，如果不是自适应，那么平均分
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
                //更新文件总共的文件块个数
                aMdfsfile.setBlockNumber(blockSerialNum-1);
                aMdfsfile.setProcessTime((java.lang.System.currentTimeMillis()-processTime)+"");
                aMdfsfile.setDeleteStatus("2");
                mdfsfileDao.update(aMdfsfile);
                LOG.warn("------LT 码 push 文件-----");
                LOG.warn("文件ID："+fileId);
			}else{
				//没有节点在线的时候，不执行文件分割操作
				LOG.warn("------没有节点在线，文件上传不执行-----");
				LOG.warn("------在线节点个数:"+liveNodeNumber+"-----");
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
