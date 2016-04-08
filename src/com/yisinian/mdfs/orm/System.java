package com.yisinian.mdfs.orm;

/**
 * System entity. @author MyEclipse Persistence Tools
 */

public class System implements java.io.Serializable {

	// Fields

	private Integer systemId;
	private String systemName;
	private String systemPassword;
	private String establishTime;
	private String introduction;
	private Integer nodeNum;
	private Integer liveNodeNum;
	private Integer sleepNodeNum;
	private Integer deadNodeNum;
	private Long totalStorage;
	private Long restStorage;
	private Long liveStorage;
	private Integer blockSize;
	private Short backupTime;
	private Integer netSpeed;
	private Integer fileNum;
	private Integer blockNum;
	private Integer liveBlockNum;
	private Long extendStorage;
	private Long liveExtendStorage;
	private Integer cpuNum;
	private Integer liveCpuNum;
	private Integer cpuFrequency;
	private Integer liveCpuFrequency;
	private Integer ram;
	private Integer liveRam;
	private String compress;
	private String adaptTransmission;

	// Constructors

	/** default constructor */
	public System() {
	}

	/** full constructor */
	public System(String systemName, String systemPassword,
			String establishTime, String introduction, Integer nodeNum,
			Integer liveNodeNum, Integer sleepNodeNum, Integer deadNodeNum,
			Long totalStorage, Long restStorage, Long liveStorage,
			Integer blockSize, Short backupTime, Integer netSpeed,
			Integer fileNum, Integer blockNum, Integer liveBlockNum,
			Long extendStorage, Long liveExtendStorage, Integer cpuNum,
			Integer liveCpuNum, Integer cpuFrequency, Integer liveCpuFrequency,
			Integer ram, Integer liveRam, String compress,
			String adaptTransmission) {
		this.systemName = systemName;
		this.systemPassword = systemPassword;
		this.establishTime = establishTime;
		this.introduction = introduction;
		this.nodeNum = nodeNum;
		this.liveNodeNum = liveNodeNum;
		this.sleepNodeNum = sleepNodeNum;
		this.deadNodeNum = deadNodeNum;
		this.totalStorage = totalStorage;
		this.restStorage = restStorage;
		this.liveStorage = liveStorage;
		this.blockSize = blockSize;
		this.backupTime = backupTime;
		this.netSpeed = netSpeed;
		this.fileNum = fileNum;
		this.blockNum = blockNum;
		this.liveBlockNum = liveBlockNum;
		this.extendStorage = extendStorage;
		this.liveExtendStorage = liveExtendStorage;
		this.cpuNum = cpuNum;
		this.liveCpuNum = liveCpuNum;
		this.cpuFrequency = cpuFrequency;
		this.liveCpuFrequency = liveCpuFrequency;
		this.ram = ram;
		this.liveRam = liveRam;
		this.compress = compress;
		this.adaptTransmission = adaptTransmission;
	}

	// Property accessors

	public Integer getSystemId() {
		return this.systemId;
	}

	public void setSystemId(Integer systemId) {
		this.systemId = systemId;
	}

	public String getSystemName() {
		return this.systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getSystemPassword() {
		return this.systemPassword;
	}

	public void setSystemPassword(String systemPassword) {
		this.systemPassword = systemPassword;
	}

	public String getEstablishTime() {
		return this.establishTime;
	}

	public void setEstablishTime(String establishTime) {
		this.establishTime = establishTime;
	}

	public String getIntroduction() {
		return this.introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public Integer getNodeNum() {
		return this.nodeNum;
	}

	public void setNodeNum(Integer nodeNum) {
		this.nodeNum = nodeNum;
	}

	public Integer getLiveNodeNum() {
		return this.liveNodeNum;
	}

	public void setLiveNodeNum(Integer liveNodeNum) {
		this.liveNodeNum = liveNodeNum;
	}

	public Integer getSleepNodeNum() {
		return this.sleepNodeNum;
	}

	public void setSleepNodeNum(Integer sleepNodeNum) {
		this.sleepNodeNum = sleepNodeNum;
	}

	public Integer getDeadNodeNum() {
		return this.deadNodeNum;
	}

	public void setDeadNodeNum(Integer deadNodeNum) {
		this.deadNodeNum = deadNodeNum;
	}

	public Long getTotalStorage() {
		return this.totalStorage;
	}

	public void setTotalStorage(Long totalStorage) {
		this.totalStorage = totalStorage;
	}

	public Long getRestStorage() {
		return this.restStorage;
	}

	public void setRestStorage(Long restStorage) {
		this.restStorage = restStorage;
	}

	public Long getLiveStorage() {
		return this.liveStorage;
	}

	public void setLiveStorage(Long liveStorage) {
		this.liveStorage = liveStorage;
	}

	public Integer getBlockSize() {
		return this.blockSize;
	}

	public void setBlockSize(Integer blockSize) {
		this.blockSize = blockSize;
	}

	public Short getBackupTime() {
		return this.backupTime;
	}

	public void setBackupTime(Short backupTime) {
		this.backupTime = backupTime;
	}

	public Integer getNetSpeed() {
		return this.netSpeed;
	}

	public void setNetSpeed(Integer netSpeed) {
		this.netSpeed = netSpeed;
	}

	public Integer getFileNum() {
		return this.fileNum;
	}

	public void setFileNum(Integer fileNum) {
		this.fileNum = fileNum;
	}

	public Integer getBlockNum() {
		return this.blockNum;
	}

	public void setBlockNum(Integer blockNum) {
		this.blockNum = blockNum;
	}

	public Integer getLiveBlockNum() {
		return this.liveBlockNum;
	}

	public void setLiveBlockNum(Integer liveBlockNum) {
		this.liveBlockNum = liveBlockNum;
	}

	public Long getExtendStorage() {
		return this.extendStorage;
	}

	public void setExtendStorage(Long extendStorage) {
		this.extendStorage = extendStorage;
	}

	public Long getLiveExtendStorage() {
		return this.liveExtendStorage;
	}

	public void setLiveExtendStorage(Long liveExtendStorage) {
		this.liveExtendStorage = liveExtendStorage;
	}

	public Integer getCpuNum() {
		return this.cpuNum;
	}

	public void setCpuNum(Integer cpuNum) {
		this.cpuNum = cpuNum;
	}

	public Integer getLiveCpuNum() {
		return this.liveCpuNum;
	}

	public void setLiveCpuNum(Integer liveCpuNum) {
		this.liveCpuNum = liveCpuNum;
	}

	public Integer getCpuFrequency() {
		return this.cpuFrequency;
	}

	public void setCpuFrequency(Integer cpuFrequency) {
		this.cpuFrequency = cpuFrequency;
	}

	public Integer getLiveCpuFrequency() {
		return this.liveCpuFrequency;
	}

	public void setLiveCpuFrequency(Integer liveCpuFrequency) {
		this.liveCpuFrequency = liveCpuFrequency;
	}

	public Integer getRam() {
		return this.ram;
	}

	public void setRam(Integer ram) {
		this.ram = ram;
	}

	public Integer getLiveRam() {
		return this.liveRam;
	}

	public void setLiveRam(Integer liveRam) {
		this.liveRam = liveRam;
	}

	public String getCompress() {
		return this.compress;
	}

	public void setCompress(String compress) {
		this.compress = compress;
	}

	public String getAdaptTransmission() {
		return this.adaptTransmission;
	}

	public void setAdaptTransmission(String adaptTransmission) {
		this.adaptTransmission = adaptTransmission;
	}

}