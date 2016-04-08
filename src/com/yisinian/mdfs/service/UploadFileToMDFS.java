package com.yisinian.mdfs.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;



import com.yisinian.mdfs.orm.BlockDAO;
import com.yisinian.mdfs.orm.Mdfsfile;
import com.yisinian.mdfs.orm.MdfsfileDAO;
import com.yisinian.mdfs.orm.NodeDAO;
import com.yisinian.mdfs.orm.SystemDAO;
import com.yisinian.mdfs.tool.MDFSTime;

public class UploadFileToMDFS extends HttpServlet {

	//日志文件
	protected static final Logger log = Logger.getLogger(UploadFileToMDFS.class);
	//版本号
	private static final long serialVersionUID = 1;

	

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//加载spring配置文件，获取DAO类
		ApplicationContext wac = (ApplicationContext) request.getSession().getServletContext().getAttribute("org.springframework.web.context.WebApplicationContext.ROOT");

		SystemDAO systemDAO = (SystemDAO) wac.getBean("SystemDAO");
		MdfsfileDAO mdfsfileDao = (MdfsfileDAO) wac.getBean("MdfsfileDAO");
		NodeDAO nodeDAO = (NodeDAO) wac.getBean("NodeDAO");
		BlockDAO blockDAO = (BlockDAO) wac.getBean("BlockDAO");
		
		
		request.setCharacterEncoding("UTF-8"); // 设置请求编码
		response.setCharacterEncoding("UTF-8");//设置相应编码
		log.warn("---------------上传文件-------------------");
		
		String filePath = "d:\\zhuxu\\ROOT\\MDFSFile\\"; //文件保存的路径
		String blockPath = "d:\\zhuxu\\ROOT\\MDFSFileBlocks\\"; //文件分块保存的路径
		
		
        FileOutputStream fos = null; //保存到文件的输出流
        FileOutputStream nodeFilefos = null; //保存到分块文件的输出流
        InputStream is =null;
        
        String fileNameString=null;	//文件名
        long fileSize = 0;			//文件大小
        Integer blockNumber =0;			//文件分块大小
        
        ArrayList<FileItem> itemImgList = new ArrayList<FileItem>();
		boolean isMultiPart = ServletFileUpload.isMultipartContent(request);
		if (isMultiPart) {
			log.warn("----------------------------------");
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			Iterator items;
			try {
				items = upload.parseRequest(request).iterator();
				while (items.hasNext()) {
					FileItem item = (FileItem) items.next();
					boolean isText = item.isFormField();
					// 说明是字符串组件
					if (isText) {
						log.warn("内容："+item.getFieldName());
					} else {
						log.warn("---------------获取文件内容-------------------");
						//如果是文件
						fileNameString=item.getName(); // 获取文件名
						fileSize = item.getSize();	//获取文件大小,单位是Byte
						log.warn("文件名："+fileNameString);
                        File aFile = new File(filePath+fileNameString);
                        
                        //文件不存在的时候执行的操作
                        if (!aFile.exists()) {
                            fos = new FileOutputStream(filePath+fileNameString);
                            is = item.getInputStream();//获取文件输入流
                            byte[] buff = new byte[1024*50];
                            int len = 0;
                            int blockSerialNum=1; //文件分块序列号
                            while ((len=is.read(buff))>0){
                                fos.write(buff,0,len);
                                //上传文件的同时，将文件进行分块操作   暂时关闭
//                                File blockFile = new File(blockPath+fileNameString.replaceAll(".txt", "")+"_"+blockSerialNum+".txt");
//                                nodeFilefos = new FileOutputStream(blockFile);//分块文件写入
//                                nodeFilefos.flush();
//                                nodeFilefos.close();
//                                blockSerialNum++;
                            }
                            log.warn("--------上传文件成功----------");
                            log.warn("文件名："+filePath+fileNameString+" 文件大小："+fileSize);
                            is.close();
                            fos.flush();
                            fos.close();
                            //新建一个MDFS文件，保存到数据库当中
                            Mdfsfile aMdfsfile = new Mdfsfile();
                            aMdfsfile.setFileName(fileNameString);
                            aMdfsfile.setFileStorageName(fileNameString);
                            aMdfsfile.setFileSize(fileSize);
                            aMdfsfile.setUploadTime(MDFSTime.getStandardTimeAsString());
                            aMdfsfile.setBlockNumber(blockNumber);
                            aMdfsfile.setDeleteStatus("0");
                            aMdfsfile.setDeleteTime("0");
                            aMdfsfile.setFilePath(filePath+fileNameString);
                            aMdfsfile.setFileDownloadTime("0");
                            aMdfsfile.setProcessTime("0");
                            mdfsfileDao.save(aMdfsfile); //保存至数据库
						}else {
							 log.warn("--------上传文件已存在----------");
							 log.warn("文件名："+filePath+fileNameString);
						}
					}
				}
				
//	            PrintWriter writer = response.getWriter();
//	            writer.println("success");
//	            writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		response.sendRedirect("allFilesAndBlocks.action");//重定向至当前文件页面

	}
}
