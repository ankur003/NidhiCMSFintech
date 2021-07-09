package com.nidhi.cms.modal.request;

import javax.validation.constraints.NotBlank;

/**
 * 
 *
 * @author Ankur Bansala
 */

public class UserCreateModal {

	@NotBlank(message =  "userEmail : userEmail can not be empty")
	private String userEmail;
	
	@NotBlank(message =  "mobileNumber : mobileNumber can not be empty")
	private String mobileNumber;

	@NotBlank(message =  "password : password can not be empty")
	private String password;

	@NotBlank(message = "fullName : fullName can not be empty")
	private String fullName;
	
	private String referralCode;

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getReferralCode() {
		return referralCode;
	}

	public void setReferralCode(String referralCode) {
		this.referralCode = referralCode;
	}

}
