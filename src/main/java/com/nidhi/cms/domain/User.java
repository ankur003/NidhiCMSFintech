package com.nidhi.cms.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nidhi.cms.constants.enums.KycStatus;
import com.nidhi.cms.utils.Utility;

/**
 * 
 *
 * @author Ankur Bansala
 */

@Entity
public class User extends BaseDomain {

	private static final long serialVersionUID = -1293783007980955204L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	@Column(unique = true)
	private String userEmail;

	@Column(unique = true)
	private String mobileNumber;

	private String fullName;

	private String referralCode;

	private String firstName;

	private String middleName;

	private String lastName;

	@JsonIgnore
	private String password;

	private LocalDate dob;

	private Boolean isAdmin;

	private Boolean isSubAdmin = false;

	private Boolean isUserVerified = false;

	@Column(unique = true, nullable = false, updatable = false)
	private String userUuid = Utility.getUniqueUuid();

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "USER_ROLES", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = {
			@JoinColumn(name = "ROLE_ID") })
	private Set<Role> roles = new HashSet<>();

	@Enumerated(EnumType.STRING)
	private KycStatus kycStatus = KycStatus.PENDING;

	private String whiteListIp;

	private Boolean isUserCreatedByAdmin = false;

	private String privilageNames;

	private String deactivateReason;

	private String token;

	private String apiKey;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

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

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
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

	public String getWhiteListIp() {
		return whiteListIp;
	}

	public void setWhiteListIp(String whiteListIp) {
		this.whiteListIp = whiteListIp;
	}

	public Boolean getIsUserCreatedByAdmin() {
		return isUserCreatedByAdmin;
	}

	public void setIsUserCreatedByAdmin(Boolean isUserCreatedByAdmin) {
		this.isUserCreatedByAdmin = isUserCreatedByAdmin;
	}

	public String getPrivilageNames() {
		return privilageNames;
	}

	public void setPrivilageNames(String privilageNames) {
		this.privilageNames = privilageNames;
	}

	public Boolean getIsSubAdmin() {
		return isSubAdmin;
	}

	public void setIsSubAdmin(Boolean isSubAdmin) {
		this.isSubAdmin = isSubAdmin;
	}

	public String getDeactivateReason() {
		return deactivateReason;
	}

	public void setDeactivateReason(String deactivateReason) {
		this.deactivateReason = deactivateReason;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

}
