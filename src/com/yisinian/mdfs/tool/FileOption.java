package com.yisinian.mdfs.tool;

import java.io.File;

public class FileOption {
	/*
	 *@author zhuxu
	 *@time ����08:48:302016
	 *@info 
	 *
	 */

	public static boolean deleteFile(String sPath) {  
	    boolean flag = false;  
	    File file = new File(sPath);  
	    // ·��Ϊ�ļ��Ҳ�Ϊ�������ɾ��  
	    if (file.isFile()&& file.exists()) {  
	        file.delete();  
	        flag = true;  
	    }  
	    return flag;  
	}  
}
