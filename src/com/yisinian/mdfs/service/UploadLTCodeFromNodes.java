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
 * @info �ڵ��ϴ�LT���ļ���Ľӿ�
 * ���� Jeff
 * 2016-6-13
 */
public class UploadLTCodeFromNodes extends HttpServlet {

	//��־�ļ�
	protected static final Logger log = Logger.getLogger(UploadFileToMDFS.class);
	//�汾��
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
		
		//����spring�����ļ�����ȡDAO��
		ApplicationContext wac = (ApplicationContext) request.getSession().getServletContext().getAttribute("org.springframework.web.context.WebApplicationContext.ROOT");

		SystemDAO systemDAO = (SystemDAO) wac.getBean("SystemDAO");
		MdfsfileDAO mdfsfileDao = (MdfsfileDAO) wac.getBean("MdfsfileDAO");
		NodeDAO nodeDAO = (NodeDAO) wac.getBean("NodeDAO");
		BlockDAO blockDAO = (BlockDAO) wac.getBean("BlockDAO");
		GetFileTaskDAO getFileTaskDAO = (GetFileTaskDAO) wac.getBean("GetFileTaskDAO");
		NodeFileTaskDAO nodeFileTaskDAO = (NodeFileTaskDAO) wac.getBean("NodeFileTaskDAO");
		
		request.setCharacterEncoding("UTF-8"); // �����������
		response.setCharacterEncoding("UTF-8");//������Ӧ����
		log.warn("---------------�ڵ�ش�LT���ļ�-------------------");
		
		String filePath = "d:\\zhuxu\\ROOT\\getCodes\\"; //�ļ������·��

		
		
        FileOutputStream fos = null; //���浽�ļ��������

        InputStream is =null;
        
        String fileNameString=null;	//�ļ���
        long fileSize = 0;			//�ļ���С
        Integer blockNumber =0;			//�ļ��ֿ��С
        

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
					// ˵�����ַ������
					if (isText) {
						log.warn("���ݣ�"+item.getFieldName());
					} else {
						log.warn("---------------��ȡ�ļ�����-------------------");
						//������ļ�
						fileNameString=item.getName(); // ��ȡ�ļ���
						fileSize = item.getSize();	//��ȡ�ļ���С,��λ��Byte
						log.warn("�ļ�����"+fileNameString);
                        File aFile = new File(filePath+fileNameString);
                        
                        //�ļ������ڵ�ʱ��ִ�еĲ���
                        if (!aFile.exists()) {
                            fos = new FileOutputStream(filePath+fileNameString);
                            is = item.getInputStream();//��ȡ�ļ�������
                            byte[] buff = new byte[1024*50];
                            int len = 0;
                            while ((len=is.read(buff))>0){
                                fos.write(buff,0,len);
                                //�ϴ��ļ���ͬʱ�����ļ����зֿ����   ��ʱ�ر�
                            }
                            log.warn("--------�ϴ��ļ��ɹ�----------");
                            log.warn("�ļ�����"+filePath+fileNameString+" �ļ���С��"+fileSize);
                            is.close();
                            fos.flush();
                            fos.close();
                            
                            
                            
                            //�ҵ��ڵ��ϴ�����������Ϣ���Լ��ܵĻ����ļ�������Ϣ���������״̬
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
								log.warn("�ļ���ID��"+blockId+" �ļ�����"+fileNameString+" �ļ�ID��"+fileId);
								//���ռ����ı�������ﵽָ��������ʱ�򣬿�ʼִ���ļ���LT����빤��
								if ((getNum+1)>=needCodeNum) {
									Mdfsfile originalFile=mdfsfileDao.findById(fileId);
									boolean decodeResult=FileLTCodeUtils.recoveryFileFromCodes(originalFile.getFileName(), fileId+"");
									//�������ɹ�����ô��û�б�Ҫ�ٻ����������ˣ�ֱ��ɾ�����еĻ�������
									if (decodeResult) {
										log.warn("�ļ��� "+originalFile.getFileName()+" LT������ճɹ�");
										List<GetFileTask> getFileTasks=getFileTaskDAO.findByFileId(fileId);
										if (getFileTasks.size()>0) {
											//ɾ�����������ļ��������,ͬʱɾ�����յ��Ѿ��ù��˵ı����ļ���
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
							 log.warn("--------�ϴ��ļ��Ѵ���----------");
							 log.warn("�ļ�����"+filePath+fileNameString);
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
//		response.sendRedirect("allFilesAndBlocks.action");//�ض�������ǰ�ļ�ҳ��

	}
}
