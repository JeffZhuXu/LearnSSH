package com.yisinian.mdfs.tool;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MDFSTime {
/*
 * @author zhuux
 * MDFSϵͳ��ʱ�������
 * */
	
	
	//��׼ʱ���ʽ2015-12-21 15:15:15��ȡ��ǰϵͳ��ʱ��
	public static String getStandardTimeAsString(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
		return (df.format(new Date()));
	}
//	public static void main(String[] args) {
//		System.out.println(getStandardTimeAsString());
//	}
}
