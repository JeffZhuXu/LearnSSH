package com.yisinian.mdfs.tool;

import java.io.File;

public class FileOption {
	/*
	 *@author zhuxu
	 *@time 下午08:48:302016
	 *@info 
	 *
	 */

	public static boolean deleteFile(String sPath) {  
	    boolean flag = false;  
	    File file = new File(sPath);  
	    // 路径为文件且不为空则进行删除  
	    if (file.isFile()&& file.exists()) {  
	        file.delete();  
	        flag = true;  
	    }  
	    return flag;  
	}  
}
