package com.yisinian.mdfs.tool;

import java.io.UnsupportedEncodingException;

/*
 * 字符串工具
 * 
 * */


public class ByteChar {

	//获取char[]数组的长度，字符编码格式为UTF-8
	public static int getCharBufferLengthAsUTF8(char[] buff ) throws UnsupportedEncodingException{
    	String a = new String(buff);
    	String c = new String(a.getBytes("UTF-8"));
    	byte[] b = c.getBytes();
    	return b.length;
	}
}
