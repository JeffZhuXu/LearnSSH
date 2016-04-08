package com.yisinian.mdfs.orm;

/**
 * People entity. @author MyEclipse Persistence Tools
 */

public class People implements java.io.Serializable {

	// Fields

	private Integer peopleId;
	private String name;
	private Short age;
	private Integer salary;

	// Constructors

	/** default constructor */
	public People() {
	}

	/** full constructor */
	public People(String name, Short age, Integer salary) {
		this.name = name;
		this.age = age;
		this.salary = salary;
	}

	// Property accessors

	public Integer getPeopleId() {
		return this.peopleId;
	}

	public void setPeopleId(Integer peopleId) {
		this.peopleId = peopleId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Short getAge() {
		return this.age;
	}

	public void setAge(Short age) {
		this.age = age;
	}

	public Integer getSalary() {
		return this.salary;
	}

	public void setSalary(Integer salary) {
		this.salary = salary;
	}

}