package com.yisinian.mdfs.action;

import com.opensymphony.xwork2.ActionSupport;


public class TestA extends ActionSupport {
	
public static void main(String[] args) {
	for (int i = 0; i < 100; i++) {
		int x=(int)(Math.random()*50);
		System.err.println(x+"");
	}
}

}
