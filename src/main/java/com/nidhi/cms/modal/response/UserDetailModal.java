package com.nidhi.cms.modal.response;

import java.time.LocalDate;
import java.util.Set;

import com.nidhi.cms.constants.enums.KycStatus;
import com.nidhi.cms.domain.Role;

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

	private Set<Role> roles;

	private KycStatus kycStatus;

	private Boolean isSubAdmin;

	private String[] privilageNames;

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

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public KycStatus getKycStatus() {
		return kycStatus;
	}

	public void setKycStatus(KycStatus kycStatus) {
		this.kycStatus = kycStatus;
	}

	public Boolean getIsSubAdmin() {
		return isSubAdmin;
	}

	public void setIsSubAdmin(Boolean isSubAdmin) {
		this.isSubAdmin = isSubAdmin;
	}

	public String[] getPrivilageNames() {
		return privilageNames;
	}

	public void setPrivilageNames(String[] privilageNames) {
		this.privilageNames = privilageNames;
	}

}
