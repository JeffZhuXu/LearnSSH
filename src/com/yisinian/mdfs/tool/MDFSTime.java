package com.yisinian.mdfs.tool;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MDFSTime {
/*
 * @author zhuux
 * MDFS系统的时间操作类
 * */
	
	
	//标准时间格式2015-12-21 15:15:15获取当前系统的时间
	public static String getStandardTimeAsString(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		return (df.format(new Date()));
	}
//	public static void main(String[] args) {
//		System.out.println(getStandardTimeAsString());
//	}
}
