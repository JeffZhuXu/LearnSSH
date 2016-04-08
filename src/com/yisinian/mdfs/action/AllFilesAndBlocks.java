package com.yisinian.mdfs.action;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.yisinian.mdfs.orm.Block;
import com.yisinian.mdfs.orm.BlockDAO;
import com.yisinian.mdfs.orm.Mdfsfile;
import com.yisinian.mdfs.orm.MdfsfileDAO;
import com.yisinian.mdfs.orm.NodeDAO;
import com.yisinian.mdfs.orm.SystemDAO;

public class AllFilesAndBlocks {

	//日志文件
	protected static final Logger log = Logger
			.getLogger(AllFilesAndBlocks.class);
	//版本号
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
	
	
public String getAllFilesAndBlocks() {
		
	try{
		JSONArray allFilesArray = new JSONArray();
		List<Mdfsfile> allFiles = mdfsfileDao.findAll();
		
		for(int i=0;i<allFiles.size();i++){
			
			JSONObject aFile = new JSONObject();
			//获取文件id
			int fileID = allFiles.get(i).getFileId();	
			Mdfsfile aMdfsfile = allFiles.get(i);
			
			aFile.put("fileId", aMdfsfile.getFileId());
			aFile.put("fileName", aMdfsfile.getFileName());
			aFile.put("fileSize", aMdfsfile.getFileSize());
			aFile.put("fileUploadTime", aMdfsfile.getUploadTime());
			aFile.put("fileBlockNumber", aMdfsfile.getBlockNumber());
			aFile.put("status", aMdfsfile.getDeleteStatus());
			aFile.put("fileDownloadTime", aMdfsfile.getFileDownloadTime());
			aFile.put("processTime", aMdfsfile.getProcessTime());
			//根据文件id找到所有的文件分块信息
			JSONArray blocksArray = new JSONArray();
			List<Block> blocks = blockDAO.findByProperty("fileId", fileID);
			for (int j = 0; j < blocks.size(); j++) {
				Block aBlockFile = blocks.get(j);
				JSONObject aBlock = new JSONObject();
				aBlock.put("blockName", aBlockFile.getBlockName());
				aBlock.put("blockSize", aBlockFile.getBlockSize());
				aBlock.put("fileSerialNumber", aBlockFile.getFileSerialNumber());
				aBlock.put("blockSetTime", aBlockFile.getBlockSetTime());
				aBlock.put("state", aBlockFile.getState());
				aBlock.put("location", aBlockFile.getNodeId());
				aBlock.put("blockDownloadTime", aBlockFile.getDownloadTime());
				blocksArray.add(aBlock);
			}
			
			//将文件的所有分块信息保存进去
			aFile.put("allBlocks", blocksArray);
			allFilesArray.add(aFile);
		}
		log.warn("·················列出所有文件和文件的分块信息····················");
		//log.warn(allFilesArray.toString());
		getRequest().setAttribute("fileAndBlocksMsg", allFilesArray.toString());
		return "success";
		
	} catch (Exception e) {
		// TODO: handle exception
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		log.warn(sw.toString());
		log.warn("get all file and blocks exception ,return exception");
		return null;
		}
	}

	public String a() {
		return "success";
	}
}
