package com.yisinian.mdfs.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

import com.yisinian.mdfs.orm.Block;
import com.yisinian.mdfs.orm.BlockDAO;
import com.yisinian.mdfs.orm.GetFileTask;
import com.yisinian.mdfs.orm.GetFileTaskDAO;
import com.yisinian.mdfs.orm.Mdfsfile;
import com.yisinian.mdfs.orm.MdfsfileDAO;
import com.yisinian.mdfs.orm.NodeDAO;
import com.yisinian.mdfs.orm.NodeFileTask;
import com.yisinian.mdfs.orm.NodeFileTaskDAO;
import com.yisinian.mdfs.orm.SystemDAO;
import com.yisinian.mdfs.tool.FileLTCodeUtils;
import com.yisinian.mdfs.tool.MDFSTime;

/**
 * @info 节点上传LT码文件块的接口
 * 朱旭 Jeff
 * 2016-6-13
 */
public class UploadLTCodeFromNodes extends HttpServlet {

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
		GetFileTaskDAO getFileTaskDAO = (GetFileTaskDAO) wac.getBean("GetFileTaskDAO");
		NodeFileTaskDAO nodeFileTaskDAO = (NodeFileTaskDAO) wac.getBean("NodeFileTaskDAO");
		
		request.setCharacterEncoding("UTF-8"); // 设置请求编码
		response.setCharacterEncoding("UTF-8");//设置相应编码
		log.warn("---------------节点回传LT码文件-------------------");
		
		String filePath = "d:\\zhuxu\\ROOT\\getCodes\\"; //文件保存的路径

		
		
        FileOutputStream fos = null; //保存到文件的输出流

        InputStream is =null;
        
        String fileNameString=null;	//文件名
        long fileSize = 0;			//文件大小
        Integer blockNumber =0;			//文件分块大小
        

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
                            while ((len=is.read(buff))>0){
                                fos.write(buff,0,len);
                                //上传文件的同时，将文件进行分块操作   暂时关闭
                            }
                            log.warn("--------上传文件成功----------");
                            log.warn("文件名："+filePath+fileNameString+" 文件大小："+fileSize);
                            is.close();
                            fos.flush();
                            fos.close();
                            
                            
                            
                            //找到节点上传的子任务信息，以及总的回收文件任务信息，更新相关状态
                            List<Block> ltBlocks=blockDAO.findByBlockName(fileNameString);
                            if (ltBlocks.size()>0) {
								int blockId=ltBlocks.get(0).getBlockId();
								int fileId = ltBlocks.get(0).getFileId();
								
								NodeFileTask aFileTask=(NodeFileTask)nodeFileTaskDAO.findByBlockId(blockId).get(0);
								aFileTask.setGetState("1");
								aFileTask.setGetTime(MDFSTime.getStandardTimeAsString());
								nodeFileTaskDAO.update(aFileTask);
								
								GetFileTask aGetFileTask=(GetFileTask)getFileTaskDAO.findByFileId(fileId).get(0);
								int getNum=aGetFileTask.getGetFileNum();
								int needCodeNum=aGetFileTask.getNeedFileNum();
								aGetFileTask.setGetFileNum(getNum+1);
								getFileTaskDAO.update(aGetFileTask);
								log.warn("文件块ID："+blockId+" 文件名："+fileNameString+" 文件ID："+fileId);
								//当收集到的编码个数达到指定个数的时候，开始执行文件的LT码解码工作
								if ((getNum+1)>=needCodeNum) {
									Mdfsfile originalFile=mdfsfileDao.findById(fileId);
									boolean decodeResult=FileLTCodeUtils.recoveryFileFromCodes(originalFile.getFileName(), fileId+"");
									//如果解码成功，那么就没有必要再回收子任务了，直接删掉所有的回收任务
									if (decodeResult) {
										log.warn("文件： "+originalFile.getFileName()+" LT解码回收成功");
										List<GetFileTask> getFileTasks=getFileTaskDAO.findByFileId(fileId);
										if (getFileTasks.size()>0) {
											//删除这条回收文件块的任务,同时删除回收的已经用过了的编码文件块
											getFileTaskDAO.delete(getFileTasks.get(0));
											File codeDir = new File(filePath);
											if (codeDir.exists()&&codeDir.isDirectory()) {
												for (File acodeFile:codeDir.listFiles()) {
													if (acodeFile.getName().indexOf(fileId+"")==0) {
														acodeFile.delete();
													}
												}
											}
										}
									} 
								}
                            }
						}else {
							 log.warn("--------上传文件已存在----------");
							 log.warn("文件名："+filePath+fileNameString);
						}
					}
				}
				
	            PrintWriter writer = response.getWriter();
	            writer.println("success");
	            writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
//		response.sendRedirect("allFilesAndBlocks.action");//重定向至当前文件页面

	}
}
