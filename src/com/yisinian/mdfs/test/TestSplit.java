package com.yisinian.mdfs.test;

public class TestSplit {
	/*
	 *@author zhuxu
	 *@time ионГ11:55:342016
	 *@info 
	 *
	 */
		
	public static void main(String[] args) {
		String aString="xiaohidao.log";
		String[] bStrings=aString.split("\\.");
		System.out.println(bStrings[0]);
		System.out.println(bStrings[1]);
	}
}
