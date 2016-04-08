package com.yisinian.mdfs.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yisinian.mdfs.orm.BlockDAO;
import com.yisinian.mdfs.orm.MdfsfileDAO;
import com.yisinian.mdfs.orm.NodeDAO;
import com.yisinian.mdfs.orm.SystemDAO;

public class UploadFile extends ActionSupport {

	//日志文件
	protected static final Logger log = Logger.getLogger(UploadFile.class);
	//版本号
	private static final long serialVersionUID = 1;

	private File file;
	private SystemDAO systemDAO;
	private MdfsfileDAO mdfsfileDao;
	private NodeDAO nodeDAO;
	private BlockDAO blockDAO;

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
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
	
	public String upload() {
		log.warn("---------------请求参数-------------------");
		log.warn("................."+getRequest().toString()+".................");
		boolean flag = ServletFileUpload.isMultipartContent(getRequest());
		log.warn("---------------上传文件-------------------");
		log.warn(flag);
        FileOutputStream fos = null;
        InputStream is =null;
        String fileNameString=null;
        
        ArrayList<FileItem> itemImgList = new ArrayList<FileItem>();
		boolean isMultiPart = ServletFileUpload.isMultipartContent(getRequest());
		if (isMultiPart) {
			log.warn("----------------------------------");
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			Iterator items;
			try {
				items = upload.parseRequest(getRequest()).iterator();
				while (items.hasNext()) {
					FileItem item = (FileItem) items.next();
					boolean isText = item.isFormField();
					if (isText) {// 说明是字符串
						log.warn("内容："+item.getFieldName());
					} else {
						log.warn("文件名："+item.getName());
					}
				}
	            PrintWriter writer = getResponse().getWriter();
	            writer.println("success");
	            writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

        
        
//        try {
//            if (flag) {
//            	log.warn("---------------开始上传文件-------------------");
//            	
//            	DiskFileItemFactory factory = new DiskFileItemFactory();
//    			// 设置 缓存的大小 50M
//    			factory.setSizeThreshold(1024 * 1024 * 10 * 5);
//    			String path = "c:\\zhuxu\\ROOT\\MDFSFile\\";
//    			factory.setRepository(new File(path));
//    			ServletFileUpload upload = new ServletFileUpload(factory);
//                
//    			// 可以上传多个文件
//    			List<FileItem> list = (List<FileItem>) upload.parseRequest(getRequest());
//    			log.warn("上传Item个数："+list.size());
//    			String nameString = "";
//    			fileNameString = "";
//    			for (FileItem item : list) {
//    				// 如果是普通的 文本 ，则认为是文件名
//    				if (item.isFormField()) {
//    					nameString = item.getString("UTF-8");
//    
//    					LOG.warn("upload string is  " + nameString);
//    				} else {
//    					// 获取路径名
//    					String value = item.getName();
//    					// 索引到最后一个反斜杠
//    					int start = value.lastIndexOf("\\");
//    					// 截取 上传文件的 字符串名字，加1是 去掉反斜杠，
//    					fileNameString = value.substring(start + 1);
//    					LOG.warn("file name is " + fileNameString);
//    					// 写到磁盘上
//    					OutputStream out = new FileOutputStream(new File(path,
//    							fileNameString));
//    					InputStream in = item.getInputStream();
//    					int length = 0;
//    					byte[] buf = new byte[1024];
//    					// in.read(buf) 每次读到的数据存放在 buf 数组中
//    					while ((length = in.read(buf)) != -1) {
//    						// 在 buf 数组中 取出数据 写到 （输出流）磁盘上
//    						out.write(buf, 0, length);
//    					}
//    					in.close();
//    					out.flush();
//    					out.close();
//    				}
    			
    				
    			
//    			FileItemIterator iter = upload.getItemIterator(getRequest());
//                while (iter.hasNext()){
//                	log.warn("---------------上传的内容非空-------------------");
//                    FileItemStream fis = iter.next();
//                    is = fis.openStream();
//                    //如果是普通文本
//                    if(fis.isFormField()){
//                    	log.warn("---------------获取表单内容-------------------");
//                    	log.warn("文本框内容："+fis.getFieldName());
//                        System.out.print(fis.getFieldName());
//                        System.out.println(":"+ Streams.asString(is));
//                    }else{
//                    	//如果是文件
//                    	fileNameString = fis.getName();//获取文件名
//                    	log.warn("---------------获取文件内容-------------------");
//                        System.out.println(fis.getName());
////                        String path = getRequest().getSession().getServletContext().getRealPath("/upload");
////                        path = path+"/"+fis.getName();
////                        System.out.println(path);
//                        log.warn("文件名："+fileNameString);
//                        File aFile = new File("c:\\zhuxu\\ROOT\\MDFSFile\\"+fileNameString);
//                        //文件不存在的时候执行的操作
//                        if (!aFile.exists()) {
//                            fos = new FileOutputStream("c:\\zhuxu\\ROOT\\MDFSFile\\"+fileNameString);
//                            byte[] buff = new byte[1024];
//                            int len = 0;
//                            while ((len=is.read(buff))>0){
//                                fos.write(buff,0,len);
//                            }
//                            log.warn("--------上传文件成功----------");
//                            log.warn("文件名："+"c:\\zhuxu\\ROOT\\MDFSFile\\"+fileNameString);
//                            fos.flush();
//                            fos.close();
//						}else {
//							 log.warn("--------上传文件已存在----------");
//							 log.warn("文件名："+"c:\\zhuxu\\ROOT\\MDFSFile\\"+fileNameString);
//						}
//
//                    }
//                }
//            }
//            PrintWriter writer = getResponse().getWriter();
//            writer.println("success");
//            writer.close();
//            }
//        }catch (Exception e){
//        	log.warn(e.toString());
//        }
        
	 return "success";
	}
}
