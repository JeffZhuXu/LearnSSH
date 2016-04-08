package com.yisinian.mdfs.orm;

/**
 * Mdfsfile entity. @author MyEclipse Persistence Tools
 */

public class Mdfsfile implements java.io.Serializable {

	// Fields

	private Integer fileId;
	private String fileName;
	private String fileStorageName;
	private Long fileSize;
	private String uploadTime;
	private Integer blockNumber;
	private String deleteStatus;
	private String deleteTime;
	private String filePath;
	private String fileDownloadTime;
	private String processTime;

	// Constructors

	/** default constructor */
	public Mdfsfile() {
	}

	/** full constructor */
	public Mdfsfile(String fileName, String fileStorageName, Long fileSize,
			String uploadTime, Integer blockNumber, String deleteStatus,
			String deleteTime, String filePath, String fileDownloadTime,
			String processTime) {
		this.fileName = fileName;
		this.fileStorageName = fileStorageName;
		this.fileSize = fileSize;
		this.uploadTime = uploadTime;
		this.blockNumber = blockNumber;
		this.deleteStatus = deleteStatus;
		this.deleteTime = deleteTime;
		this.filePath = filePath;
		this.fileDownloadTime = fileDownloadTime;
		this.processTime = processTime;
	}

	// Property accessors

	public Integer getFileId() {
		return this.fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileStorageName() {
		return this.fileStorageName;
	}

	public void setFileStorageName(String fileStorageName) {
		this.fileStorageName = fileStorageName;
	}

	public Long getFileSize() {
		return this.fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public String getUploadTime() {
		return this.uploadTime;
	}

	public void setUploadTime(String uploadTime) {
		this.uploadTime = uploadTime;
	}

	public Integer getBlockNumber() {
		return this.blockNumber;
	}

	public void setBlockNumber(Integer blockNumber) {
		this.blockNumber = blockNumber;
	}

	public String getDeleteStatus() {
		return this.deleteStatus;
	}

	public void setDeleteStatus(String deleteStatus) {
		this.deleteStatus = deleteStatus;
	}

	public String getDeleteTime() {
		return this.deleteTime;
	}

	public void setDeleteTime(String deleteTime) {
		this.deleteTime = deleteTime;
	}

	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileDownloadTime() {
		return this.fileDownloadTime;
	}

	public void setFileDownloadTime(String fileDownloadTime) {
		this.fileDownloadTime = fileDownloadTime;
	}

	public String getProcessTime() {
		return this.processTime;
	}

	public void setProcessTime(String processTime) {
		this.processTime = processTime;
	}

}