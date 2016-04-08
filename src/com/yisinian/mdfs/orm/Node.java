package com.yisinian.mdfs.orm;

/**
 * Node entity. @author MyEclipse Persistence Tools
 */

public class Node implements java.io.Serializable {

	// Fields

	private Integer id;
	private String nodeId;
	private Integer systemId;
	private String nodeName;
	private String nodePassword;
	private String company;
	private String phoneType;
	private String os;
	private String osVersion;
	private String localStorage;
	private String restLocalStorage;
	private String storage;
	private String restStorage;
	private String ram;
	private String cpuFrequency;
	private String coreNumber;
	private String netType;
	private String netSpeed;
	private String phoneModel;
	private String imel;
	private String serialNumber;
	private String jpushId;
	private String state;
	private String startTime;
	private String endTime;

	// Constructors

	/** default constructor */
	public Node() {
	}

	/** full constructor */
	public Node(String nodeId, Integer systemId, String nodeName,
			String nodePassword, String company, String phoneType, String os,
			String osVersion, String localStorage, String restLocalStorage,
			String storage, String restStorage, String ram,
			String cpuFrequency, String coreNumber, String netType,
			String netSpeed, String phoneModel, String imel,
			String serialNumber, String jpushId, String state,
			String startTime, String endTime) {
		this.nodeId = nodeId;
		this.systemId = systemId;
		this.nodeName = nodeName;
		this.nodePassword = nodePassword;
		this.company = company;
		this.phoneType = phoneType;
		this.os = os;
		this.osVersion = osVersion;
		this.localStorage = localStorage;
		this.restLocalStorage = restLocalStorage;
		this.storage = storage;
		this.restStorage = restStorage;
		this.ram = ram;
		this.cpuFrequency = cpuFrequency;
		this.coreNumber = coreNumber;
		this.netType = netType;
		this.netSpeed = netSpeed;
		this.phoneModel = phoneModel;
		this.imel = imel;
		this.serialNumber = serialNumber;
		this.jpushId = jpushId;
		this.state = state;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNodeId() {
		return this.nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public Integer getSystemId() {
		return this.systemId;
	}

	public void setSystemId(Integer systemId) {
		this.systemId = systemId;
	}

	public String getNodeName() {
		return this.nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getNodePassword() {
		return this.nodePassword;
	}

	public void setNodePassword(String nodePassword) {
		this.nodePassword = nodePassword;
	}

	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPhoneType() {
		return this.phoneType;
	}

	public void setPhoneType(String phoneType) {
		this.phoneType = phoneType;
	}

	public String getOs() {
		return this.os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getOsVersion() {
		return this.osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	public String getLocalStorage() {
		return this.localStorage;
	}

	public void setLocalStorage(String localStorage) {
		this.localStorage = localStorage;
	}

	public String getRestLocalStorage() {
		return this.restLocalStorage;
	}

	public void setRestLocalStorage(String restLocalStorage) {
		this.restLocalStorage = restLocalStorage;
	}

	public String getStorage() {
		return this.storage;
	}

	public void setStorage(String storage) {
		this.storage = storage;
	}

	public String getRestStorage() {
		return this.restStorage;
	}

	public void setRestStorage(String restStorage) {
		this.restStorage = restStorage;
	}

	public String getRam() {
		return this.ram;
	}

	public void setRam(String ram) {
		this.ram = ram;
	}

	public String getCpuFrequency() {
		return this.cpuFrequency;
	}

	public void setCpuFrequency(String cpuFrequency) {
		this.cpuFrequency = cpuFrequency;
	}

	public String getCoreNumber() {
		return this.coreNumber;
	}

	public void setCoreNumber(String coreNumber) {
		this.coreNumber = coreNumber;
	}

	public String getNetType() {
		return this.netType;
	}

	public void setNetType(String netType) {
		this.netType = netType;
	}

	public String getNetSpeed() {
		return this.netSpeed;
	}

	public void setNetSpeed(String netSpeed) {
		this.netSpeed = netSpeed;
	}

	public String getPhoneModel() {
		return this.phoneModel;
	}

	public void setPhoneModel(String phoneModel) {
		this.phoneModel = phoneModel;
	}

	public String getImel() {
		return this.imel;
	}

	public void setImel(String imel) {
		this.imel = imel;
	}

	public String getSerialNumber() {
		return this.serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getJpushId() {
		return this.jpushId;
	}

	public void setJpushId(String jpushId) {
		this.jpushId = jpushId;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStartTime() {
		return this.startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return this.endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}