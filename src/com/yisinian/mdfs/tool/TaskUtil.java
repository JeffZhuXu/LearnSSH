package com.yisinian.mdfs.tool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 *@author zhuxu
 *@time 上午10:34:502016
 *@info 分布式任务工具类
 *
 */

public class TaskUtil {

	//文件信息检索类
	public static String searchFileA(String filePath,String message){
		String searchResult = "";
		
		try {
            String encoding="UTF-8";
            File file=new File(filePath);
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){
                	Matcher matcher = Pattern.compile(message).matcher(lineTxt);
                	while(matcher.find()){
                		searchResult=searchResult+"\n"+lineTxt;
                		//System.out.println(lineTxt);
                	}
                }
                read.close();
    }else{
        System.out.println("找不到指定的文件");
    }
    } catch (Exception e) {
        System.out.println("读取文件内容出错");
        e.printStackTrace();
    }
		//System.out.println("查询结果："+searchResult);
		return searchResult; 
	}
	
	//将字符串写入指定文本文件
	public static void writeSearchResultsIntoFile(String resultFilePath,String result){
		String localFilePath=resultFilePath;
		String searchResult = result;
		File resultFile = new File(localFilePath);
		try {
		if(resultFile.isFile()&&resultFile.exists()){
			
			FileOutputStream writerStream = new FileOutputStream(resultFile,true);    
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(writerStream, "GBK")); 
			writer.write(searchResult);
			writer.close();  

		}else {
			resultFile.createNewFile();
			FileOutputStream writerStream = new FileOutputStream(resultFile,true);    
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(writerStream, "GBK")); 
			writer.write(searchResult);
			writer.close();  
		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		String filePath ="F:\\1.log";
		String message = "手机扫码";
		String searchResult=searchFileA(filePath,message);
		writeSearchResultsIntoFile("F:\\result.txt", searchResult);
	}
}
