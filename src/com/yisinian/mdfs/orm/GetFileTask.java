package com.yisinian.mdfs.orm;

import java.util.HashSet;
import java.util.Set;

/**
 * GetFileTask entity. @author MyEclipse Persistence Tools
 */

public class GetFileTask extends com.opensymphony.xwork2.ActionSupport
		implements java.io.Serializable {

	// Fields

	private Integer getFileTaskId;
	private Integer fileId;
	private Integer getFileNum;
	private Integer needFileNum;
	private Integer allFileNum;
	private String fileType;
	private String setTime;
	private String sucess;
	private Set nodeFileTasks = new HashSet(0);

	// Constructors

	/** default constructor */
	public GetFileTask() {
	}

	/** minimal constructor */
	public GetFileTask(Integer fileId, Integer getFileNum, Integer needFileNum,
			Integer allFileNum, String fileType, String setTime, String sucess) {
		this.fileId = fileId;
		this.getFileNum = getFileNum;
		this.needFileNum = needFileNum;
		this.allFileNum = allFileNum;
		this.fileType = fileType;
		this.setTime = setTime;
		this.sucess = sucess;
	}

	/** full constructor */
	public GetFileTask(Integer fileId, Integer getFileNum, Integer needFileNum,
			Integer allFileNum, String fileType, String setTime, String sucess,
			Set nodeFileTasks) {
		this.fileId = fileId;
		this.getFileNum = getFileNum;
		this.needFileNum = needFileNum;
		this.allFileNum = allFileNum;
		this.fileType = fileType;
		this.setTime = setTime;
		this.sucess = sucess;
		this.nodeFileTasks = nodeFileTasks;
	}

	// Property accessors

	public Integer getGetFileTaskId() {
		return this.getFileTaskId;
	}

	public void setGetFileTaskId(Integer getFileTaskId) {
		this.getFileTaskId = getFileTaskId;
	}

	public Integer getFileId() {
		return this.fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	public Integer getGetFileNum() {
		return this.getFileNum;
	}

	public void setGetFileNum(Integer getFileNum) {
		this.getFileNum = getFileNum;
	}

	public Integer getNeedFileNum() {
		return this.needFileNum;
	}

	public void setNeedFileNum(Integer needFileNum) {
		this.needFileNum = needFileNum;
	}

	public Integer getAllFileNum() {
		return this.allFileNum;
	}

	public void setAllFileNum(Integer allFileNum) {
		this.allFileNum = allFileNum;
	}

	public String getFileType() {
		return this.fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getSetTime() {
		return this.setTime;
	}

	public void setSetTime(String setTime) {
		this.setTime = setTime;
	}

	public String getSucess() {
		return this.sucess;
	}

	public void setSucess(String sucess) {
		this.sucess = sucess;
	}

	public Set getNodeFileTasks() {
		return this.nodeFileTasks;
	}

	public void setNodeFileTasks(Set nodeFileTasks) {
		this.nodeFileTasks = nodeFileTasks;
	}

}