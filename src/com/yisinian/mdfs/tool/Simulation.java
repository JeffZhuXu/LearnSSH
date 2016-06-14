package com.yisinian.mdfs.tool;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.yisinian.mdfs.tool.LTCodeUtils.EncodeMsg;
import com.yisinian.mdfs.tool.LTCodeUtils.OriginalMsg;

/**
 * @explain 喷泉码仿真程�? * @explain 冗余备份方案失败�?LT码方案失败率对比仿真
 * @explain 失败率是按照文件块的失败率来计算�? * @author Jeff
 * @time 16.5.25
 * 
 * */

// 模拟文件不完整率
public class Simulation {
	public static int experimentNum; // 模拟仿真实验次数
	public static int originalBlockNum; // 初始文件块的个数
	public static int backupTimes; // 冗余备份的个�?	
	public static int allBlickNum; // 总共的文件块个数
	public static List<String> allBlockNameList = new ArrayList<String>(); // �?��文件块的信息列表
	public static double errorRate; // 每个文件块的故障�?
	public double getErrorRate() {
		return errorRate;
	}

	public void setErrorRate(double errorRate) {
		this.errorRate = errorRate;
	}

	public Simulation(int experimentNum, int originalBlockNum, int backupTimes) {
		this.experimentNum = experimentNum;
		this.originalBlockNum = originalBlockNum;
		this.backupTimes = backupTimes;
		allBlickNum = originalBlockNum * (backupTimes + 1);
		for (int i = 0; i <= backupTimes; i++) {
			for (int j = 1; j <= originalBlockNum; j++) {
				allBlockNameList.add(j + "");
			}
		}
	}

	/***
	 * @explain 冗余备份方案中，从�?的文件块中，获取指定块数文件块后，能够成功复原初始文件的概率
	 * @param int selectNum 要�?择的个数
	 * @return double sucessRate 选择指定文件个数成功的概�?	 */
	public double getSucessRateByAppointedSelectNum(int selcectNum) {
		// 有效实验次数
		int num = 0;
		Set<String> results = null;
		for (int i = 0; i < experimentNum; i++) {
			// �?��解析结果
			results = new HashSet<String>();
			Set<Integer> selectNum = getRandom(selcectNum, allBlickNum);
			// System.out.println(selectNum);
			Iterator<Integer> iterator = selectNum.iterator();
			while (iterator.hasNext()) {
				// set里面的�?不能重复，正好用来标识唯�??
				String aValue = allBlockNameList.get(iterator.next());
				results.add(aValue);
			}
			// 判断是否包含�?��的文件块
			int count = 0;
			if (results.size() == originalBlockNum) {
				num++;
			}
		}
		// System.out.println("总实验次数："+experimentNum);
		// System.out.println("成功次数�?+num);
		// System.out.println("成功率："+(double)num/experimentNum);
		return (double) num / experimentNum;
	}

	/***
	 * @explain 从给定数目序号中随机抽取执行个数序号
	 * @param allNum
	 *            ,selectNum
	 * @return set<Integer>随机抽取的序号，不重�?	 */
	public static Set<Integer> getRandom(int selectnum, int allNum) {
		Set<Integer> set = new HashSet<Integer>();
		while (true) {
			set.add((int) (Math.random() * allNum));
			if (set.size() == selectnum) {
				break;
			}
		}
		return set;
	}

	/***
	 * @explain 在指定文件故障率情况下文件完整度概率
	 * @param errorRate
	 * @return double fileRate
	 * 
	 */
	public double getFullFileRateByAppointedErrorRate(double errorRate) {
		setErrorRate(errorRate);
		// �?��的文件完整的概率
		double finalRate = 0.0d;
		// 计算在该错误概率下的文件完整的概�?		
		for (int i = originalBlockNum; i <= allBlickNum; i++) {
			finalRate += getAoopintNumFromAllNumTimes(allBlickNum, i)
					* Math.pow((1 - errorRate), i)
					* Math.pow(errorRate, allBlickNum - i)
					* getSucessRateByAppointedSelectNum(i);
		}
		return finalRate;
	}

	/**
	 * @explain 冗余备份方案和LT码方案可靠�?对比�?	 * @param null
	 * @return null
	 */
	public static void main(String[] args) {

		// 冗余备份方案，实验数1000，初始文件块100，冗余度2
		Simulation simulation = new Simulation(1000, 100, 2);
		System.err.println("���౸�ݷ���");
		double errorRate = 0.000000d;
		for (int i = 0; i <= 100; i++) {
			errorRate = (double) i / 1000;
			System.out
					.println(errorRate
							+ "\t"
							+ simulation
									.getFullFileRateByAppointedErrorRate(errorRate));
		}

		// LT码方案，实验�?000，初始文件块100，译码开�?		
		System.err.println("LT�뷽��");
		// 仿真编码解码次数
		int simulationNum = 1000;
		double errorRate1 = 0.000000d;
		// 冗余度译码开�?		
		double decodeCost = 0;
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
		System.err.println("��ʼ�ļ��飺" + aCodeUtils.K + " " + "���뿪����"
				+ aCodeUtils.decodeCost + " " + "�ܱ�������" + aCodeUtils.N + " "
				+ "��Ҫ��ȡ��С��������" + aCodeUtils.KZ + " ");
		for (int i = 0; i <= 100; i++) {
			errorRate1 = (double) i / 1000;
			double finalRate1 = aCodeUtils.getFullFileRateByAppointedErrorRate(
					errorRate1, 2);
			System.out.println(errorRate1 + "\t" + finalRate1);
		}
	}

	public static double getRate(double errorRate, double decodeCost) {
		// �?��的文件完整的概率
		double finalRate1 = 0.00000000d;
		// 计算在该错误概率下的文件完整的概�?		
		System.out.println("�ܱ��������" + 300 + " ������Ҫ���������" + 109);
		for (int i = 109; i <= 300; i++) {
			finalRate1 += getAoopintNumFromAllNumTimes(i, 300) * (1 - 0.01)
					* Math.pow((1 - errorRate), i)
					* Math.pow(errorRate, (300 - i));
		}
		return finalRate1;
	}

	// 从指定数量文件块中抓取一定数量文件块的所有可能�?
	public static double getAoopintNumFromAllNumTimes(int selectNum, int allNum) {
		double result = 0.0;
		BigDecimal a = new BigDecimal("1");
		BigDecimal b = new BigDecimal("1");
		for (int i = 1; i <= selectNum; i++) {
			a = a.multiply(new BigDecimal(i + ""));
		}
		for (int i = allNum; i >= allNum - selectNum + 1; i--) {
			b = b.multiply(new BigDecimal(i + ""));
		}
		return b.divide(a, 16, 5).doubleValue();
	}
}
