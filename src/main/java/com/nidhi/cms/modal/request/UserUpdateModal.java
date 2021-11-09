package com.nidhi.cms.modal.request;

import java.util.List;

public class UserUpdateModal {

	private String fullName;

	private String firstName;

	private String lastName;

	private String middleName;
	
	private String dob;
	
	private String[] userPrivileges;

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String[] getUserPrivileges() {
		return userPrivileges;
	}

	public void setUserPrivileges(String[] userPrivileges) {
		this.userPrivileges = userPrivileges;
	}

	/*
	 * public List<String> getUserPrivileges() { return userPrivileges; }
	 * 
	 * public void setUserPrivileges(List<String> userPrivileges) {
	 * this.userPrivileges = userPrivileges; }
	 */

	
}
