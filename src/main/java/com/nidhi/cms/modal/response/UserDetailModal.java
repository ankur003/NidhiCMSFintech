package com.nidhi.cms.modal.response;

import java.time.LocalDate;

/**
 * 
 *
 * @author Ankur Bansala
 */

public class UserDetailModal {

	private String userEmail;

	private String mobileNumber;

	private String fullName;

	private String referralCode;

	private String firstName;

	private String middleName;

	private String lastName;

	private LocalDate dob;

	private Boolean isUserVerified;

	private String userUuid;

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

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public Boolean getIsUserVerified() {
		return isUserVerified;
	}

	public void setIsUserVerified(Boolean isUserVerified) {
		this.isUserVerified = isUserVerified;
	}

	public String getUserUuid() {
		return userUuid;
	}

	public void setUserUuid(String userUuid) {
		this.userUuid = userUuid;
	}

}
