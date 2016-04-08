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

	//��־�ļ�
	protected static final Logger log = Logger.getLogger(UploadFile.class);
	//�汾��
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
		log.warn("---------------�������-------------------");
		log.warn("................."+getRequest().toString()+".................");
		boolean flag = ServletFileUpload.isMultipartContent(getRequest());
		log.warn("---------------�ϴ��ļ�-------------------");
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
					if (isText) {// ˵�����ַ���
						log.warn("���ݣ�"+item.getFieldName());
					} else {
						log.warn("�ļ�����"+item.getName());
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
//            	log.warn("---------------��ʼ�ϴ��ļ�-------------------");
//            	
//            	DiskFileItemFactory factory = new DiskFileItemFactory();
//    			// ���� ����Ĵ�С 50M
//    			factory.setSizeThreshold(1024 * 1024 * 10 * 5);
//    			String path = "c:\\zhuxu\\ROOT\\MDFSFile\\";
//    			factory.setRepository(new File(path));
//    			ServletFileUpload upload = new ServletFileUpload(factory);
//                
//    			// �����ϴ�����ļ�
//    			List<FileItem> list = (List<FileItem>) upload.parseRequest(getRequest());
//    			log.warn("�ϴ�Item������"+list.size());
//    			String nameString = "";
//    			fileNameString = "";
//    			for (FileItem item : list) {
//    				// �������ͨ�� �ı� ������Ϊ���ļ���
//    				if (item.isFormField()) {
//    					nameString = item.getString("UTF-8");
//    
//    					LOG.warn("upload string is  " + nameString);
//    				} else {
//    					// ��ȡ·����
//    					String value = item.getName();
//    					// ���������һ����б��
//    					int start = value.lastIndexOf("\\");
//    					// ��ȡ �ϴ��ļ��� �ַ������֣���1�� ȥ����б�ܣ�
//    					fileNameString = value.substring(start + 1);
//    					LOG.warn("file name is " + fileNameString);
//    					// д��������
//    					OutputStream out = new FileOutputStream(new File(path,
//    							fileNameString));
//    					InputStream in = item.getInputStream();
//    					int length = 0;
//    					byte[] buf = new byte[1024];
//    					// in.read(buf) ÿ�ζ��������ݴ���� buf ������
//    					while ((length = in.read(buf)) != -1) {
//    						// �� buf ������ ȡ������ д�� ���������������
//    						out.write(buf, 0, length);
//    					}
//    					in.close();
//    					out.flush();
//    					out.close();
//    				}
    			
    				
    			
//    			FileItemIterator iter = upload.getItemIterator(getRequest());
//                while (iter.hasNext()){
//                	log.warn("---------------�ϴ������ݷǿ�-------------------");
//                    FileItemStream fis = iter.next();
//                    is = fis.openStream();
//                    //�������ͨ�ı�
//                    if(fis.isFormField()){
//                    	log.warn("---------------��ȡ������-------------------");
//                    	log.warn("�ı������ݣ�"+fis.getFieldName());
//                        System.out.print(fis.getFieldName());
//                        System.out.println(":"+ Streams.asString(is));
//                    }else{
//                    	//������ļ�
//                    	fileNameString = fis.getName();//��ȡ�ļ���
//                    	log.warn("---------------��ȡ�ļ�����-------------------");
//                        System.out.println(fis.getName());
////                        String path = getRequest().getSession().getServletContext().getRealPath("/upload");
////                        path = path+"/"+fis.getName();
////                        System.out.println(path);
//                        log.warn("�ļ�����"+fileNameString);
//                        File aFile = new File("c:\\zhuxu\\ROOT\\MDFSFile\\"+fileNameString);
//                        //�ļ������ڵ�ʱ��ִ�еĲ���
//                        if (!aFile.exists()) {
//                            fos = new FileOutputStream("c:\\zhuxu\\ROOT\\MDFSFile\\"+fileNameString);
//                            byte[] buff = new byte[1024];
//                            int len = 0;
//                            while ((len=is.read(buff))>0){
//                                fos.write(buff,0,len);
//                            }
//                            log.warn("--------�ϴ��ļ��ɹ�----------");
//                            log.warn("�ļ�����"+"c:\\zhuxu\\ROOT\\MDFSFile\\"+fileNameString);
//                            fos.flush();
//                            fos.close();
//						}else {
//							 log.warn("--------�ϴ��ļ��Ѵ���----------");
//							 log.warn("�ļ�����"+"c:\\zhuxu\\ROOT\\MDFSFile\\"+fileNameString);
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
