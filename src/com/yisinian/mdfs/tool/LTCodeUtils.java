package com.yisinian.mdfs.tool;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


/**
 * @author Jeff
 * @explain LT码程�?
 * @time 2016.5.5
 * 
 * */

public class LTCodeUtils {
	static public double c = 0.01; // 鲁棒孤子度分析中，计算波纹�?，即度数�?的期望�?中用到的常数
	static public int K; // 信息分组个数
	static public int N; // 产生的编码分组的个数
	static public int S; // robust solition 分布中度�?的校验（编码后产生）节点个数的期望�?�?
	static public double Z; // Z：rou和tao对应每个度的求和，要使成功进行译码的概率至少为（1-sig）则要接收KZ�?
	static public int KS; // K除以S的�?取整，tao函数中的�?��参数�?
	static public int KZ; // 以概率（1-sigma）成功解码所�?��的接收的编码信号的个�?
	static public double sigma = 0.01d; // 接收�?��信息分组后译码失败概率的边界�?
	static public List<OriginalMsg> originalMsg; // 原始的信源信�?
	static public double errorRate=0.000000;  //每个文件块的故障�?
	static public double decodeCost=0.000000;  //译码�?��
	// 具体的度数分布情�?
	static private Double[] ISD; // 理想孤子度数分布数组
	static private Double[] RSD; // 添加调整函数过后的鲁棒孤子度数分布数�?

	// 初始化编�?
	public LTCodeUtils(List<OriginalMsg> originalMsgs) {
		// 初始的信源信息赋�?
		this.originalMsg = originalMsgs;
		// 信源信号个数
		this.K = originalMsgs.size();
		// 计算S，波纹�?
		this.S = (int) Math.ceil(c * Math.sqrt(K) * Math.log(K / sigma));
		// 计算K除以S取整
		this.KS = this.K / this.S;
		// 计算初始化KZ，即�?��编码的个�?
		for (int i = 1; i <= K; i++) {
			Z += rho(i, K) + tau(i, S, K);
		}
		KZ = (int) (K * Z);
		initDegree();
	}

	public void initDegree() {
		// 给两种度数分布确定初始元素个�?
		ISD = new Double[K];
		RSD = new Double[K];
		Double isdValue = 0d;
		Double rsdValue = 0d;
		for (int i = 0; i < K; i++) {
			isdValue += rho(i + 1, K);
			rsdValue += miu(i + 1, K);
			ISD[i] = isdValue;
			RSD[i] = rsdValue;
		}
	}

	// 理想孤子分布
	public static double rho(int d, int k) {
		if (d == 1)
			return 1 / (double) k;
		else
			return 1 / (double) (d * (d - 1));
	}

	// 调节函数
	public static double tau(int d, int S, int k) {
		if (d > (k / S))
			return 0;
		else if (d == k / S)
			return S * Math.log(S / sigma) / k;
		else
			return S / (double) (d * k);
	}

	// 鲁棒孤子度分�?
	public static double miu(int d, int k) {
		return (rho(d, k) + tau(d, S, k)) / Z;
	}

	// 获取默认个数的编码个数，编码个数为KZ
	public static List<EncodeMsg> getDefaultEncodeMsg() {
		return encode(originalMsg, 2);
	}

	// 获取指定倍数的编码信息，编码个数为KZ
	public static List<EncodeMsg> getPointEncodeMsg(int times) {
		return encode(originalMsg, times);
	}

	// 获取默认个数的编码个数，编码个数为KZ,度数分布为理想孤子分�?
	public static List<EncodeMsg> getDefaultEncodeMsgISD() {
		return encodeISD(originalMsg, Z);
	}

	// 获取指定倍数的编码信息，编码个数为KZ,度数分布为理想孤子分�?
	public static List<EncodeMsg> getPointEncodeMsgISD(double times) {
		return encodeISD(originalMsg, times);
	}

	// 获取默认个数的编码个数，编码个数为KZ，度数分布为鲁棒孤子分布
	public static List<EncodeMsg> getDefaultEncodeMsgRSD() {
		return encodeRSD(originalMsg, Z);
	}

	// 获取指定倍数的编码信息，编码个数为KZ，度数分布为鲁棒孤子分布
	public static List<EncodeMsg> getPointEncodeMsgRSD(double times) {
		return encodeRSD(originalMsg, times);
	}
	//设置故障概率
	public void setErrorRate(double errorRate){
		this.errorRate=errorRate;
	}
	//设置译码�?��
	public void setDecodeCost(double decodeCost){
		this.decodeCost=decodeCost;
		this.N=(int)(K*(decodeCost+1));
	}
	// 获取指定故障率及指定译码�?��情况下，文件完整度概�?
	public double getFullFileRateByAppointedErrorRate(double errorRate,double decodeCost) {
		setDecodeCost(decodeCost);
		//�?��的文件完整的概率
		double finalRate1 = 0.00000000d;
		//计算在该错误概率下的文件完整的概�?
		for (int i = KZ; i <= N; i++) {
			finalRate1 += Simulation.getAoopintNumFromAllNumTimes(i, N)*(1-sigma)*Math.pow((1-errorRate), i)*Math.pow(errorRate, N-i);
		}
		return finalRate1;
	}
	public static void main(String[] args) {
		// 仿真编码解码次数
		int simulationNum = 10000;
		// 冗余度译码开�?
		double decodeCost = 1.0;
		// isd解码成功次数
		int isdSucessNum = 0;
		// rsd解码成功次数
		int rsdSucessNum = 0;

		String originalString = "1010000100101010101111001010000101000111111101010100001111010101111111001010101011010101110000110100";
		// 初始分组个数
		int num = originalString.length() / 1;
		// 保存�?��初始信息分组的集�?
		List<OriginalMsg> originalMsgs = new ArrayList<OriginalMsg>();
		for (int i = 0; i < num; i++) {
			String aMessagee = originalString.substring(i * 1, i * 1 + 1);
			OriginalMsg oneMsg = new OriginalMsg(i, aMessagee, num);
			originalMsgs.add(oneMsg);
		}

		LTCodeUtils aCodeUtils = new LTCodeUtils(originalMsgs);
		
		//译码�?���?~3，间�?.01
		System.out.println("ISD");
		for (int i = 0; i <= 40; i=i+2) {
			decodeCost=(double)i/20;
			for (int j = 0; j < simulationNum; j++) {
				// ISD度数分布默认编码个数
				List<EncodeMsg> encodeMsgs2 = aCodeUtils
						.getPointEncodeMsgISD(decodeCost + 1.0);
				// ISD解码
				boolean isdResult = decode(encodeMsgs2,"");
				// ISD解码成功，计数加�?
				if (isdResult) {
					isdSucessNum++;
				}
			}
			System.out.println(decodeCost + "\t"
					+ (1 - (double) isdSucessNum / simulationNum));
			isdSucessNum=0;
		}
		System.out.println("\nRSD");
		for (int i = 0; i <= 40; i=i+2) {
			decodeCost=(double)i/20;
			for (int j = 0; j < simulationNum; j++) {
				// RSD度数分布默认编码个数
				List<EncodeMsg> encodeMsgs3 = aCodeUtils
						.getPointEncodeMsgRSD(decodeCost + 1.0);
				// RSD解码
				boolean rsdResult = decode(encodeMsgs3,"");
				if (rsdResult) {
					rsdSucessNum++;
				}
			}
			System.out.println(decodeCost + "\t"
					+ (1 - (double) rsdSucessNum / simulationNum));
			rsdSucessNum=0;
		}
	}

	/**
	 * @explain 初始信息�?
	 * */
	public static class OriginalMsg implements Comparable<OriginalMsg> {
		// 信息编号
		public int id;
		// 总的原始信息个数
		public int originalMsgNum;
		public String message = null;

		public OriginalMsg() {
		}

		// 构�?函数
		public OriginalMsg(int id) {
			this.id = id;
		}

		// 构�?函数
		public OriginalMsg(int id, String originalMessage, int originalMsgNum) {
			this.id = id;
			this.message = originalMessage;
		}

		
		public int compareTo(OriginalMsg o) {
			// TODO Auto-generated method stub
			return this.id - o.id;
		}

		// 给原始信息组赋�?
		public void addMessage(String message) {
			// 信息为空的时候，赋�?，已经有值了，不赋�?
			if (this.message == null) {
				this.message = message;
			}
		}

		@Override
		public boolean equals(Object obj) {
			OriginalMsg o = (OriginalMsg) obj;
			if (this.id - o.id == 0) {
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * @explain 编码后的信息�?
	 * */
	public static class EncodeMsg implements Comparable<EncodeMsg> {
		// 编码组的度数
		public int degree;
		// 总的原始信息个数
		public int originalMsgNum;
		// 编码后的�?
		public String encodeMessage = null;
		// 来源节点
		public LinkedList<OriginalMsg> originalMsgList = new LinkedList<OriginalMsg>();
        
		public EncodeMsg() {
		}

		public EncodeMsg(int degree, String encodeMessage) {
			this.degree = degree;
			this.encodeMessage = encodeMessage;
		}

		public EncodeMsg(String encodeMsg, Integer[] originalMessageId,
				int originalMessageNum) {
			this.encodeMessage = encodeMsg;
			this.degree = originalMessageId.length;
			this.originalMsgNum = originalMessageNum;
			// 将原始编码分组信息添加到编码信息组上
			for (int i = 0; i < originalMessageId.length; i++) {
				OriginalMsg originalMsg = new OriginalMsg(originalMessageId[i]);
				originalMsgList.add(originalMsg);
			}
		}

		
		public int compareTo(EncodeMsg o) {
			// TODO Auto-generated method stub
			return this.degree - o.degree;
		}

		// 异或�?��父节点，度数减一,同时将其从父节点列表中删�?
		public void deleteOriginalMsg(OriginalMsg oneMsg) {
			degree--;
			int length = oneMsg.message.length();
			String oneMessage = oneMsg.message;
			StringBuffer buffer = new StringBuffer();
			for (int i = 0; i < length; i++) {
				if (encodeMessage.charAt(i) == oneMessage.charAt(i)) {
					buffer.append('0');
				} else {
					buffer.append('1');
				}
			}
			// 给编码后的�?重新赋�?
			encodeMessage = buffer.toString();
			// 将该节点从父节点列表中删�?
			originalMsgList.remove(originalMsgList.indexOf(oneMsg));
		}
	}

	/**
	 * 
	 * @info 根据指定度数分布获取选中的信源信号的序号索引数组
	 * @param int num 总的信源个数
	 * @return Integer[] 随机抽取的元数据的索引列�?
	 * 
	 * 
	 * */
	public static Integer[] getSelectMessages(int num) {
		// 均匀分配先吧
		int degree = 0;
		// 概率分布
		Double[] rates = new Double[] { 0.2, 0.4, 0.6, 0.7, 0.8, 0.93, 0.94,
				0.95, 0.96, 1d };
		Double selectValue = Math.random();
		for (int i = 0; i < rates.length; i++) {
			if (selectValue <= rates[i]) {
				degree = i + 1;
				break;
			}
		}
		Integer[] selectMessages = getRandonNum(degree, num);
		return selectMessages;
	}

	/**
	 * 
	 * @info 根据理想孤子分布ISD度数分布获取选中的信源信号的序号索引数组
	 * @return Integer[] 随机抽取的元数据的索引列�?
	 * 
	 * 
	 * */
	public static Integer[] getISDSelectMessages() {
		int degree = 0;
		// 概率分布
		Double[] rates = ISD;
		Double selectValue = Math.random();
		for (int i = 0; i < rates.length; i++) {
			if (selectValue <= rates[i]) {
				degree = i + 1;
				break;
			}
		}
		Integer[] selectMessages = getRandonNum(degree, K);
		return selectMessages;
	}

	/**
	 * 
	 * @info 根据鲁棒孤子分布RSD度数分布获取选中的信源信号的序号索引数组
	 * @return Integer[] 随机抽取的元数据的索引列�?
	 * 
	 * 
	 * */
	public static Integer[] getRSDSelectMessages() {
		int degree = 0;
		// 概率分布
		Double[] rates = RSD;
		Double selectValue = Math.random();
		for (int i = 0; i < rates.length; i++) {
			if (selectValue <= rates[i]) {
				degree = i + 1;
				break;
			}
		}
		Integer[] selectMessages = getRandonNum(degree, K);
		return selectMessages;
	}

	/**
	 * @param degree
	 *            选随机数的个�?
	 * @param num
	 *            总的随机数个�?
	 * @return int[] 随机数集�?
	 * 
	 * */
	public static Integer[] getRandonNum(int degree, int num) {
		Integer[] numbers = new Integer[degree];
		// set内的integer是不同的
		Set<Integer> set = new HashSet<Integer>();
		while (true) {
			set.add((int) (Math.random() * num));
			if (set.size() == degree)
				break;
		}
		numbers = (Integer[]) set.toArray(numbers);
		return numbers;
	}

	/**
	 * @explain 多个原始数据求模二加的函�?
	 * @param List
	 *            <String> messages,二进制字符串
	 * @return String xorResult
	 * 
	 * */
	public static String calEncodeMessage(List<String> originalMsgs) {
		int length = originalMsgs.size();
		if (length == 0) {
			return null;
		}
		String encodeMsg;
		if (length > 1) {
			encodeMsg = originalMsgs.get(0);
			for (int i = 1; i < length; i++) {
				String oneMsg = originalMsgs.get(i);
				StringBuffer buffer = new StringBuffer();
				for (int j = 0; j < encodeMsg.length(); j++) {
					if (encodeMsg.charAt(j) == oneMsg.charAt(j)) {
						buffer.append('0');
					} else {
						buffer.append('1');
					}
				}
				encodeMsg = buffer.toString();
			}
			return encodeMsg;
		} else {
			return originalMsgs.get(0);
		}
	}

	/**
	 * @exnpain LT编码函数
	 * @param List
	 *            <OriginalMsg>
	 * @param double times 编码信号数目相对于信源信号个数的比例
	 * @return List<EncodeMsg>
	 * 
	 * */
	public static List<EncodeMsg> encode(List<OriginalMsg> messages,
			double times) {
		// 保存�?��初始信息分组的集�?
		List<OriginalMsg> originalMsgs = messages;
		// 保存编码后的编码信息�?
		List<EncodeMsg> encodeMsgs = new LinkedList<EncodeMsg>();
		// 初始编码个数
		int num = originalMsgs.size();
		// 产生的编码个�?
		int encodeNum = (int) (num * times);
		// �?��计算encodeNum指定编码个数
		for (int i = 0; i < encodeNum; i++) {
			// 初始信息码源个数�?0，按照指定度数分布返回码源索�?
			Integer[] select = getSelectMessages(10);
			List<String> msg = new ArrayList<String>();
			for (int j = 0; j < select.length; j++) {
				msg.add(originalMsgs.get(select[j]).message);
			}
			String encodeMessage = calEncodeMessage(msg);
			// 编码后对�?
			EncodeMsg oneEncodeMsg = new EncodeMsg(encodeMessage, select, num);
			encodeMsgs.add(oneEncodeMsg);
		}
		return encodeMsgs;
	}

	/**
	 * @exnpain LT编码函数，度数分布是理想孤子分布
	 * @param List
	 *            <OriginalMsg>
	 * @param double times 编码信号数目相对于信源信号个数的比例
	 * @return List<EncodeMsg>
	 * 
	 * */
	public static List<EncodeMsg> encodeISD(List<OriginalMsg> messages,
			double times) {
		// 保存�?��初始信息分组的集�?
		List<OriginalMsg> originalMsgs = messages;
		// 保存编码后的编码信息�?
		List<EncodeMsg> encodeMsgs = new LinkedList<EncodeMsg>();
		// 初始编码个数
		int num = originalMsgs.size();
		// 产生的编码个�?
		int encodeNum = (int) (num * times);
		// �?��计算encodeNum指定编码个数
		for (int i = 0; i < encodeNum; i++) {
			// 初始信息码源个数�?0，按照指定度数分布返回码源索�?
			Integer[] select = getISDSelectMessages();
			List<String> msg = new ArrayList<String>();
			for (int j = 0; j < select.length; j++) {
				msg.add(originalMsgs.get(select[j]).message);
			}
			String encodeMessage = calEncodeMessage(msg);
			// 编码后对�?
			EncodeMsg oneEncodeMsg = new EncodeMsg(encodeMessage, select, num);
			encodeMsgs.add(oneEncodeMsg);
		}
		return encodeMsgs;
	}

	/**
	 * @exnpain LT编码函数,度数分布是RSD鲁棒孤子分布
	 * @param List
	 *            <OriginalMsg>
	 * @param double times 编码信号数目相对于信源信号个数的比例
	 * @return List<EncodeMsg>
	 * 
	 * */
	public static List<EncodeMsg> encodeRSD(List<OriginalMsg> messages,
			double times) {
		// 保存�?��初始信息分组的集�?
		List<OriginalMsg> originalMsgs = messages;
		// 保存编码后的编码信息�?
		List<EncodeMsg> encodeMsgs = new LinkedList<EncodeMsg>();
		// 初始编码个数
		int num = originalMsgs.size();
		// 产生的编码个�?
		int encodeNum = (int) (num * times);
		// �?��计算encodeNum指定编码个数
		for (int i = 0; i < encodeNum; i++) {
			// 初始信息码源个数�?0，按照指定度数分布返回码源索�?
			Integer[] select = getRSDSelectMessages();
			List<String> msg = new ArrayList<String>();
			for (int j = 0; j < select.length; j++) {
				msg.add(originalMsgs.get(select[j]).message);
			}
			String encodeMessage = calEncodeMessage(msg);
			// 编码后对�?
			EncodeMsg oneEncodeMsg = new EncodeMsg(encodeMessage, select, num);
			encodeMsgs.add(oneEncodeMsg);
		}
		return encodeMsgs;
	}

	/**
	 * @exnpain LT解码方法
	 * @param List
	 *            <EncodeMsg>
	 * @param String fileName 文件编号
	 *            
	 * @return boolean是否解码成功
	 * 
	 * */
	public static boolean decode(List<EncodeMsg> encodeMsgs,String fileNum) {
		// 保存�?��初始信息分组的集�?
		List<OriginalMsg> originalMsgs1 = new LinkedList<OriginalMsg>();
		// 初始信息分组的个�?
		int originalMsgNum = 0;
		// 已经成功解码的原始问价块个数
		int decodeBlockNum = 0;
		// 解码是否成功的标�?
		boolean result = false;
		int encodeNum = encodeMsgs.size();
		// 为空
		if (encodeNum == 0) {
			return result;
		}
		// 只有�?��参数
		if (encodeNum == 1) {
			originalMsgNum = encodeMsgs.get(0).originalMsgNum;
			OriginalMsg aMsg = new OriginalMsg(1,
					encodeMsgs.get(0).encodeMessage, originalMsgNum);
			originalMsgs1.add(aMsg);
			result = true;
			return result;
		} else {
			// 获取初始信息组个�?
			originalMsgNum = encodeMsgs.get(0).originalMsgNum;
			// System.err.println("原始信息个数�? + originalMsgNum);
			// 生成指定个数的原始信息，只有编号，没有具体信�?
			for (int i = 0; i < originalMsgNum; i++) {
				OriginalMsg aOriginalMsg = new OriginalMsg(i);
				originalMsgs1.add(aOriginalMsg);
			}
			// �?��解码
			boolean condition = true;
			while (condition) {
				if (encodeMsgs.size() == 1) {
					break;
				}
				// 编码信息按照度数升序排列，最低的应该是度数为1�?
				Collections.sort(encodeMsgs);
				// 找出度数�?��的编码信�?
				EncodeMsg aEncodeMsg = encodeMsgs.get(0);
				if (aEncodeMsg.degree == 0) {
					// 如果�?��的度数为0，删掉这个度数为0的家�?继续循环
					encodeMsgs.remove(0);
					continue;
				}
				// �?��的度数都大于1，无解，直接跳出循环
				if (aEncodeMsg.degree > 1) {
					break;
				} else {
					// 找出度数�?的父�?
					OriginalMsg aOriginalMsg = aEncodeMsg.originalMsgList
							.get(0);
					// 将度数为1的节点移除待解码列表
					encodeMsgs.remove(0);
					// 获取源节点ID
					int originalMsgId = aOriginalMsg.id;
					// 找到对应源节点，给源节点赋�?
					OriginalMsg oneoOriginalMsg = originalMsgs1
							.get(originalMsgId);
					// 该节点信息为空的时�?
					if (oneoOriginalMsg.message == null) {
						oneoOriginalMsg.addMessage(aEncodeMsg.encodeMessage);
						// 成功解码原始信息块个�?
						decodeBlockNum++;
						// 遍历每一个编码信息，发现父节点包含这个节点的，清除掉
						Iterator<EncodeMsg> encodeIterator = encodeMsgs
								.iterator();
						while (encodeIterator.hasNext()) {
							EncodeMsg nextEncodeMsg = encodeIterator.next();
							// 如果父节点包含该节点，删除对应关系，重新计算
							if (nextEncodeMsg.originalMsgList
									.contains(oneoOriginalMsg)) {
								nextEncodeMsg
										.deleteOriginalMsg(oneoOriginalMsg);
							}
						}
						continue;
					} else {
						continue;
					}
				}
			}
			//如果解码陈功，将解码后的文件
			if (decodeBlockNum == originalMsgNum) {
				result = true;
			}
			return result;
		}
	}
	
	/**
	 * @exnpain LT解码文件
	 * @param List
	 *            <EncodeMsg>
	 * @param String fileName 文件编号
	 *            
	 * @return boolean是否解码成功
	 * 
	 * */
	public static boolean decodeFile(List<EncodeMsg> encodeMsgs,String fileName) {
		// 保存�?��初始信息分组的集�?
		List<OriginalMsg> originalMsgs1 = new LinkedList<OriginalMsg>();
		// 初始信息分组的个�?
		int originalMsgNum = 0;
		// 已经成功解码的原始问价块个数
		int decodeBlockNum = 0;
		// 解码是否成功的标�?
		boolean result = false;
		int encodeNum = encodeMsgs.size();
		// 为空
		if (encodeNum == 0) {
			return result;
		}
		// 只有�?��参数
		if (encodeNum == 1) {
			originalMsgNum = encodeMsgs.get(0).originalMsgNum;
			OriginalMsg aMsg = new OriginalMsg(1,
					encodeMsgs.get(0).encodeMessage, originalMsgNum);
			originalMsgs1.add(aMsg);
			result = true;
			return result;
		} else {
			// 获取初始信息组个�?
			originalMsgNum = encodeMsgs.get(0).originalMsgNum;
			// System.err.println("原始信息个数�? + originalMsgNum);
			// 生成指定个数的原始信息，只有编号，没有具体信�?
			for (int i = 0; i < originalMsgNum; i++) {
				OriginalMsg aOriginalMsg = new OriginalMsg(i);
				originalMsgs1.add(aOriginalMsg);
			}
			// �?��解码
			boolean condition = true;
			while (condition) {
				if (encodeMsgs.size() == 1) {
					break;
				}
				// 编码信息按照度数升序排列，最低的应该是度数为1�?
				Collections.sort(encodeMsgs);
				// 找出度数�?��的编码信�?
				EncodeMsg aEncodeMsg = encodeMsgs.get(0);
				if (aEncodeMsg.degree == 0) {
					// 如果�?��的度数为0，删掉这个度数为0的家�?继续循环
					encodeMsgs.remove(0);
					continue;
				}
				// �?��的度数都大于1，无解，直接跳出循环
				if (aEncodeMsg.degree > 1) {
					break;
				} else {
					// 找出度数�?的父�?
					OriginalMsg aOriginalMsg = aEncodeMsg.originalMsgList
					.get(0);
					// 将度数为1的节点移除待解码列表
					encodeMsgs.remove(0);
					// 获取源节点ID
					int originalMsgId = aOriginalMsg.id;
					// 找到对应源节点，给源节点赋�?
					OriginalMsg oneoOriginalMsg = originalMsgs1
					.get(originalMsgId);
					// 该节点信息为空的时�?
					if (oneoOriginalMsg.message == null) {
						oneoOriginalMsg.addMessage(aEncodeMsg.encodeMessage);
						// 成功解码原始信息块个�?
						decodeBlockNum++;
						// 遍历每一个编码信息，发现父节点包含这个节点的，清除掉
						Iterator<EncodeMsg> encodeIterator = encodeMsgs
						.iterator();
						while (encodeIterator.hasNext()) {
							EncodeMsg nextEncodeMsg = encodeIterator.next();
							// 如果父节点包含该节点，删除对应关系，重新计算
							if (nextEncodeMsg.originalMsgList
									.contains(oneoOriginalMsg)) {
								nextEncodeMsg
								.deleteOriginalMsg(oneoOriginalMsg);
							}
						}
						continue;
					} else {
						continue;
					}
				}
			}
			//如果解码陈功，将解码后的文件
			if (decodeBlockNum == originalMsgNum) {
				result = true;
				String decodeFilePathString="D:\\zhuxu\\ROOT\\decodeFile\\"+fileName;
				BufferedWriter br;
				try {
					br = new BufferedWriter(new FileWriter(new File(decodeFilePathString)));
					for (OriginalMsg aMsg:originalMsgs1) {
						//二进制转换成字符�?
						String decodeString=FileLTCodeUtils.BinaryToString(aMsg.message);
						br.write(decodeString);
					}
					br.flush(); // 刷新缓冲区的数据到文�?
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return result;
		}
	}
}
