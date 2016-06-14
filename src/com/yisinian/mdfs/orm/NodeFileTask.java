package com.yisinian.mdfs.orm;

/**
 * NodeFileTask entity. @author MyEclipse Persistence Tools
 */

public class NodeFileTask extends com.opensymphony.xwork2.ActionSupport
		implements java.io.Serializable {

	// Fields

	private Integer nodeFileTaskId;
	private GetFileTask getFileTask;
	private String nodeId;
	private Integer fileId;
	private Integer blockId;
	private String getState;
	private String getTime;

	// Constructors

	/** default constructor */
	public NodeFileTask() {
	}

	/** full constructor */
	public NodeFileTask(GetFileTask getFileTask, String nodeId, Integer fileId,
			Integer blockId, String getState, String getTime) {
		this.getFileTask = getFileTask;
		this.nodeId = nodeId;
		this.fileId = fileId;
		this.blockId = blockId;
		this.getState = getState;
		this.getTime = getTime;
	}

	// Property accessors

	public Integer getNodeFileTaskId() {
		return this.nodeFileTaskId;
	}

	public void setNodeFileTaskId(Integer nodeFileTaskId) {
		this.nodeFileTaskId = nodeFileTaskId;
	}

	public GetFileTask getGetFileTask() {
		return this.getFileTask;
	}

	public void setGetFileTask(GetFileTask getFileTask) {
		this.getFileTask = getFileTask;
	}

	public String getNodeId() {
		return this.nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
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

	public String getGetState() {
		return this.getState;
	}

	public void setGetState(String getState) {
		this.getState = getState;
	}

	public String getGetTime() {
		return this.getTime;
	}

	public void setGetTime(String getTime) {
		this.getTime = getTime;
	}

}