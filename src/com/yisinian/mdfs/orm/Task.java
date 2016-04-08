package com.yisinian.mdfs.orm;

import java.util.HashSet;
import java.util.Set;

/**
 * Task entity. @author MyEclipse Persistence Tools
 */

public class Task implements java.io.Serializable {

	// Fields

	private Integer taskId;
	private String type;
	private Integer fileId;
	private String name;
	private String content;
	private String resultPath;
	private Integer nodeTaskNum;
	private Integer finishTaskNum;
	private Float finishRate;
	private String totalTime;
	private String buildTime;
	private String state;
	private Set nodeTasks = new HashSet(0);

	// Constructors

	/** default constructor */
	public Task() {
	}

	/** minimal constructor */
	public Task(String type, Integer fileId, String name, String content,
			String resultPath, Integer nodeTaskNum, Integer finishTaskNum,
			Float finishRate, String totalTime, String buildTime, String state) {
		this.type = type;
		this.fileId = fileId;
		this.name = name;
		this.content = content;
		this.resultPath = resultPath;
		this.nodeTaskNum = nodeTaskNum;
		this.finishTaskNum = finishTaskNum;
		this.finishRate = finishRate;
		this.totalTime = totalTime;
		this.buildTime = buildTime;
		this.state = state;
	}

	/** full constructor */
	public Task(String type, Integer fileId, String name, String content,
			String resultPath, Integer nodeTaskNum, Integer finishTaskNum,
			Float finishRate, String totalTime, String buildTime, String state,
			Set nodeTasks) {
		this.type = type;
		this.fileId = fileId;
		this.name = name;
		this.content = content;
		this.resultPath = resultPath;
		this.nodeTaskNum = nodeTaskNum;
		this.finishTaskNum = finishTaskNum;
		this.finishRate = finishRate;
		this.totalTime = totalTime;
		this.buildTime = buildTime;
		this.state = state;
		this.nodeTasks = nodeTasks;
	}

	// Property accessors

	public Integer getTaskId() {
		return this.taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getFileId() {
		return this.fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getResultPath() {
		return this.resultPath;
	}

	public void setResultPath(String resultPath) {
		this.resultPath = resultPath;
	}

	public Integer getNodeTaskNum() {
		return this.nodeTaskNum;
	}

	public void setNodeTaskNum(Integer nodeTaskNum) {
		this.nodeTaskNum = nodeTaskNum;
	}

	public Integer getFinishTaskNum() {
		return this.finishTaskNum;
	}

	public void setFinishTaskNum(Integer finishTaskNum) {
		this.finishTaskNum = finishTaskNum;
	}

	public Float getFinishRate() {
		return this.finishRate;
	}

	public void setFinishRate(Float finishRate) {
		this.finishRate = finishRate;
	}

	public String getTotalTime() {
		return this.totalTime;
	}

	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}

	public String getBuildTime() {
		return this.buildTime;
	}

	public void setBuildTime(String buildTime) {
		this.buildTime = buildTime;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Set getNodeTasks() {
		return this.nodeTasks;
	}

	public void setNodeTasks(Set nodeTasks) {
		this.nodeTasks = nodeTasks;
	}

}