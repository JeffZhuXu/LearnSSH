package com.yisinian.mdfs.tool;

import java.io.UnsupportedEncodingException;

/*
 * �ַ�������
 * 
 * */


public class ByteChar {

	//��ȡchar[]����ĳ��ȣ��ַ������ʽΪUTF-8
	public static int getCharBufferLengthAsUTF8(char[] buff ) throws UnsupportedEncodingException{
    	String a = new String(buff);
    	String c = new String(a.getBytes("UTF-8"));
    	byte[] b = c.getBytes();
    	return b.length;
	}
}
