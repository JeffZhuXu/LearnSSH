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
		
		
		request.setCharacterEncoding("UTF-8"); // �����������
		response.setCharacterEncoding("UTF-8");//������Ӧ����
		log.warn("---------------�ϴ��ļ�-------------------");
		
		String filePath = "d:\\zhuxu\\ROOT\\MDFSFile\\"; //�ļ������·��
		String blockPath = "d:\\zhuxu\\ROOT\\MDFSFileBlocks\\"; //�ļ��ֿ鱣���·��
		
		
        FileOutputStream fos = null; //���浽�ļ��������
        FileOutputStream nodeFilefos = null; //���浽�ֿ��ļ��������
        InputStream is =null;
        
        String fileNameString=null;	//�ļ���
        long fileSize = 0;			//�ļ���С
        Integer blockNumber =0;			//�ļ��ֿ��С
        
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
                            int blockSerialNum=1; //�ļ��ֿ����к�
                            while ((len=is.read(buff))>0){
                                fos.write(buff,0,len);
                                //�ϴ��ļ���ͬʱ�����ļ����зֿ����   ��ʱ�ر�
//                                File blockFile = new File(blockPath+fileNameString.replaceAll(".txt", "")+"_"+blockSerialNum+".txt");
//                                nodeFilefos = new FileOutputStream(blockFile);//�ֿ��ļ�д��
//                                nodeFilefos.flush();
//                                nodeFilefos.close();
//                                blockSerialNum++;
                            }
                            log.warn("--------�ϴ��ļ��ɹ�----------");
                            log.warn("�ļ�����"+filePath+fileNameString+" �ļ���С��"+fileSize);
                            is.close();
                            fos.flush();
                            fos.close();
                            //�½�һ��MDFS�ļ������浽���ݿ⵱��
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
                            mdfsfileDao.save(aMdfsfile); //���������ݿ�
						}else {
							 log.warn("--------�ϴ��ļ��Ѵ���----------");
							 log.warn("�ļ�����"+filePath+fileNameString);
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
		response.sendRedirect("allFilesAndBlocks.action");//�ض�������ǰ�ļ�ҳ��

	}
}
