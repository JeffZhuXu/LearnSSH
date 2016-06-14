package com.yisinian.mdfs.tool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.yisinian.mdfs.action.PushLTFileToNode;
import com.yisinian.mdfs.tool.LTCodeUtils.EncodeMsg;
import com.yisinian.mdfs.tool.LTCodeUtils.OriginalMsg;



/**
 * @explain MDFS
 * @author Jeff
 * 
 */
public class FileLTCodeUtils {
	//日志文件
	protected static final Logger LOG = Logger
			.getLogger(PushLTFileToNode.class);
	//版本号
	private static final long serialVersionUID = 1;


	public static String StringToBinary(String word)
			throws UnsupportedEncodingException {

		StringBuffer buffer = new StringBuffer(); 
		char[] wordChar = word.toCharArray();
		
		int wordLength = word.length();
		
		char[] decodeChar = new char[wordLength];
		
		String decodeString;
		for (int i = 0; i < wordChar.length; i++) {
			String fullBinaryStringGBK = stringFullFill(Integer
					.toBinaryString(wordChar[i]));
			buffer.append(fullBinaryStringGBK);
		}
		return buffer.toString();
	}

	
	public static String BinaryToString(String binaryString) {
		
		int stringNum = binaryString.length() / 16;
		
		char[] decodeChar = new char[stringNum];
		for (int i = 0; i < stringNum; i++) {
			decodeChar[i] = (char) Integer.valueOf(
					binaryString.substring(i * 16, (i + 1) * 16), 2).intValue();
		}
		return new String(decodeChar);
	}
  
//	public static void main(String[] args) throws UnsupportedEncodingException {
//		fileLTencode("data.txt","121",2);
//		System.out.println(recoveryFileFromCodes("data.txt","121"));
//
//	}
	public static void fileLTencode(String fileName,String fileNum,int decodeCost){
		try {

			int backupTimes=decodeCost;
			int blockNum=0;		
			int codeNum=0;		
			String filePath = "D:\\zhuxu\\ROOT\\MDFSFile\\"+fileName;

			String[] fileNameMsg = fileName.split("\\.");
			String nameString = fileNameMsg[0];
			String typeString = fileNameMsg[fileNameMsg.length-1];
			File aFile = new File(filePath);

			LOG.warn("开始文件 "+fileName+" 的LT码编码");
			Reader reader = null;

			if (aFile.exists() && aFile.isFile()) {

				reader = new InputStreamReader(new FileInputStream(aFile),
						"GBK");
				 BufferedReader bufferedReader = new BufferedReader(reader);
				 int charread = 0;

                 char[] tempchars = new char[10];
                 while(( charread=bufferedReader.read(tempchars)) != -1){
//                     System.out.println(tempchars);
                     String binaryString=StringToBinary(new String(tempchars));
                     writeFile(binaryString, "D:\\zhuxu\\ROOT\\blocks\\"+fileNum+"_"+blockNum+"."+typeString);
                     LOG.warn("生成文件块："+"D:\\zhuxu\\ROOT\\blocks\\"+fileNum+"_"+blockNum+"."+typeString);
                     blockNum++;
                 }
                 bufferedReader.close();
                 reader.close();

                 String codesPath = "D:\\zhuxu\\ROOT\\codes\\";
                 int codeName=0;
                 codeNum=blockNum*(backupTimes+1);
                 List<OriginalMsg> originalMsgs = new ArrayList<OriginalMsg>();
                 File blockPath = new File("D:\\zhuxu\\ROOT\\blocks");
                 if (blockPath.isDirectory()) {
					File[] blockFiles=blockPath.listFiles();
					for (File ablockFile : blockFiles) {
						String fileName1 = ablockFile.getName();
						String blockFilePath = ablockFile.getAbsolutePath();
						if (fileName1.indexOf(fileNum+"")==0) {
							OriginalMsg aOriginalMsg = new OriginalMsg(codeName, readFileAsString(blockFilePath), blockNum);
							originalMsgs.add(aOriginalMsg);
						}
					}
				}
                 LTCodeUtils aCodeUtils = new LTCodeUtils(originalMsgs);
                 List<EncodeMsg> encodeFiles=aCodeUtils.getPointEncodeMsgRSD(backupTimes+1);
                 for (int i = 0; i < encodeFiles.size(); i++) {
                	 EncodeMsg aEncodeMsg=encodeFiles.get(i);
                	 List<OriginalMsg> originalMsgs2 = aEncodeMsg.originalMsgList;
                	 String parents="";
                	 for (OriginalMsg aMsg:originalMsgs2) {
						parents+=aMsg.id+",";
					}
                	parents=parents.substring(0, parents.length()-1);
					String codeTextString = aEncodeMsg.originalMsgNum+" "+ aEncodeMsg.degree+" "+parents+" "+aEncodeMsg.encodeMessage;
					
					writeFile(codeTextString, codesPath+fileNum+"_"+i+"_lt"+"."+typeString);
					LOG.warn("生成编码块："+codesPath+fileNum+"_"+i+"."+typeString);
				}
			}else {
				System.err.println("指定文件不存在");
			}
		} catch (Exception e) {
			System.err.println("读取文件出错");
			e.printStackTrace();
		}
	}
	
	/**
	 * @explian 
	 * @author Jeff
	 * @param text
	 * @param path
	 * @return null
	 */
	public static void writeFile(String text, String path) {
		BufferedWriter br;
		try {
			File aFile = new File(path);
			if (!aFile.exists()) {
				br = new BufferedWriter(new FileWriter(aFile));
				br.write(text);
				br.flush(); // 刷新缓冲区的数据到文件
				br.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @explian
	 * @param path
	 */
	public static String readFileAsString(String path) {
		StringBuffer buffer = new StringBuffer();
		try {

			Reader reader = new InputStreamReader(new FileInputStream(path), "GBK");
			BufferedReader bufferedReader = new BufferedReader(reader);
			int charread = 0;
			String lineString=null;
			while ((lineString = bufferedReader.readLine()) != null) {
				buffer.append(lineString);
			}
			bufferedReader.close();
			reader.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return buffer.toString();
	}

	/**
	 * @explian 
	 * @author Jeff
	 * @param String
	 * @return String
	 * 
	 */
	public static String stringFullFill(String binaryStringGBK) {
		int length = binaryStringGBK.length();
		StringBuffer buffer = new StringBuffer();
		if (length < 16) {
			for (int i = 0; i < 16 - length; i++) {
				buffer.append("0");
			}
			buffer.append(binaryStringGBK);
			return buffer.toString();
		} else {
			return binaryStringGBK;
		}
	}
	/**
	 * @explian
	 * @param fileNum
	 * @return null
	 */
	public static boolean recoveryFileFromCodes(String fileName,String fileNum){
		String codePathString="D:\\zhuxu\\ROOT\\getCodes";
        File codePath = new File(codePathString);
        List<EncodeMsg> encodeMsgs = new LinkedList<EncodeMsg>();
        if (codePath.isDirectory()) {
			File[] blockFiles=codePath.listFiles();
			for (File ablockFile : blockFiles) {
				String codeFileName = ablockFile.getName();
				String codeFilePath = ablockFile.getAbsolutePath();
				
				if (codeFileName.indexOf(fileNum+"")==0) {
					String codeText = readFileAsString(codeFilePath);
					String[] codeMessages =codeText.split(" ");

					int originalBlockNum = Integer.parseInt(codeMessages[0].trim());

					int degree = Integer.parseInt(codeMessages[1].trim());

					String[] fatherBlocks = codeMessages[2].trim().split(",");
					Integer[] fatherBlocksId = new Integer[fatherBlocks.length];
					for (int i = 0; i < fatherBlocks.length; i++) {
						fatherBlocksId[i] = Integer.parseInt(fatherBlocks[i]);
					}

					String codeTextString = codeMessages[3].trim();
					EncodeMsg aEncodeMsg = new EncodeMsg(codeTextString, fatherBlocksId, originalBlockNum);
					encodeMsgs.add(aEncodeMsg);
					
				}
			}

		}
		boolean decodeRst=LTCodeUtils.decodeFile(encodeMsgs,fileName);
		return decodeRst;
	}
}
