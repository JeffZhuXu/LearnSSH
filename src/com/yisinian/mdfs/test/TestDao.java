package com.yisinian.mdfs.test;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


import com.yisinian.mdfs.orm.Node;
import com.yisinian.mdfs.orm.NodeDAO;

public class TestDao {
	protected static final Logger log = Logger.getLogger(TestDao.class);
	private static final long serialVersionUID = 1;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext wac = new ClassPathXmlApplicationContext(
		"classpath:applicationContext.xml");
		NodeDAO nodeDAO = (NodeDAO)wac.getBean("NodeDAO");
		List<Node> nodes = nodeDAO.findByState("1");
		for(int i=0;i<=nodes.size();i++){
			System.out.println("½ÚµãÃû³Æ£º"+nodes.get(i).getNodeName());
		}
		
	}
}
