package com.yisinian.mdfs.orm;

/**
 * NodeTask entity. @author MyEclipse Persistence Tools
 */

public class NodeTask implements java.io.Serializable {

	// Fields

	private Integer nodeTaskId;
	private Integer taskId;
	private String type;
	private Integer fileId;
	private Integer blockId;
	private String nodeId;
	private String content;
	private String finishTime;
	private String state;
	private String buildTime;

	// Constructors

	/** default constructor */
	public NodeTask() {
	}

	/** full constructor */
	public NodeTask(Integer taskId, String type, Integer fileId,
			Integer blockId, String nodeId, String content, String finishTime,
			String state, String buildTime) {
		this.taskId = taskId;
		this.type = type;
		this.fileId = fileId;
		this.blockId = blockId;
		this.nodeId = nodeId;
		this.content = content;
		this.finishTime = finishTime;
		this.state = state;
		this.buildTime = buildTime;
	}

	// Property accessors

	public Integer getNodeTaskId() {
		return this.nodeTaskId;
	}

	public void setNodeTaskId(Integer nodeTaskId) {
		this.nodeTaskId = nodeTaskId;
	}

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

	public Integer getBlockId() {
		return this.blockId;
	}

	public void setBlockId(Integer blockId) {
		this.blockId = blockId;
	}

	public String getNodeId() {
		return this.nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFinishTime() {
		return this.finishTime;
	}

	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getBuildTime() {
		return this.buildTime;
	}

	public void setBuildTime(String buildTime) {
		this.buildTime = buildTime;
	}

}