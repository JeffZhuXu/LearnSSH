package com.yisinian.mdfs.orm;

/**
 * Block entity. @author MyEclipse Persistence Tools
 */

public class Block implements java.io.Serializable {

	// Fields

	private Integer blockId;
	private Integer fileId;
	private String nodeId;
	private String blockName;
	private Integer fileSerialNumber;
	private String blockPath;
	private Integer blockSize;
	private String blockSetTime;
	private String state;
	private String stateTime;
	private String downloadTime;

	// Constructors

	/** default constructor */
	public Block() {
	}

	/** full constructor */
	public Block(Integer fileId, String nodeId, String blockName,
			Integer fileSerialNumber, String blockPath, Integer blockSize,
			String blockSetTime, String state, String stateTime,
			String downloadTime) {
		this.fileId = fileId;
		this.nodeId = nodeId;
		this.blockName = blockName;
		this.fileSerialNumber = fileSerialNumber;
		this.blockPath = blockPath;
		this.blockSize = blockSize;
		this.blockSetTime = blockSetTime;
		this.state = state;
		this.stateTime = stateTime;
		this.downloadTime = downloadTime;
	}

	// Property accessors

	public Integer getBlockId() {
		return this.blockId;
	}

	public void setBlockId(Integer blockId) {
		this.blockId = blockId;
	}

	public Integer getFileId() {
		return this.fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	public String getNodeId() {
		return this.nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getBlockName() {
		return this.blockName;
	}

	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}

	public Integer getFileSerialNumber() {
		return this.fileSerialNumber;
	}

	public void setFileSerialNumber(Integer fileSerialNumber) {
		this.fileSerialNumber = fileSerialNumber;
	}

	public String getBlockPath() {
		return this.blockPath;
	}

	public void setBlockPath(String blockPath) {
		this.blockPath = blockPath;
	}

	public Integer getBlockSize() {
		return this.blockSize;
	}

	public void setBlockSize(Integer blockSize) {
		this.blockSize = blockSize;
	}

	public String getBlockSetTime() {
		return this.blockSetTime;
	}

	public void setBlockSetTime(String blockSetTime) {
		this.blockSetTime = blockSetTime;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStateTime() {
		return this.stateTime;
	}

	public void setStateTime(String stateTime) {
		this.stateTime = stateTime;
	}

	public String getDownloadTime() {
		return this.downloadTime;
	}

	public void setDownloadTime(String downloadTime) {
		this.downloadTime = downloadTime;
	}

}