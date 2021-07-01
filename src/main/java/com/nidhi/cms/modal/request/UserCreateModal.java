package com.nidhi.cms.modal.request;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;

/**
 * 
 *
 * @author Ankur Bansala
 */

public class UserCreateModal {

	@NotBlank(message = "username : username can not be empty")
	private String username;

	@NotBlank(message =  "userEmail : userEmail can not be empty")
	private String userEmail;

	@NotBlank(message =  "firstName : firstName can not be empty")
	private String firstName;

	@NotBlank(message =  "middleName : middleName can not be empty")
	private String middleName;

	@NotBlank(message =  "lastName : lastName can not be empty")
	private String lastName;

	@NotBlank(message =  "password : password can not be empty")
	private String password;

	@NotBlank(message =  "dob : dob can not be empty")
	private LocalDate dob;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

}
