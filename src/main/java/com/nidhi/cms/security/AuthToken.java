package com.nidhi.cms.security;

import java.time.LocalDateTime;

/**
 * 
 *
 * @author Ankur Bansala
 */

public class AuthToken {

	private String token;

	private String userUuid;

	private LocalDateTime createdAt;

	private String expireInSeconds;
	
	private Boolean isCreatedByAdmin;
	
	private Boolean isUserVerified;
	
	private Boolean doesVerificationFlowRequired;
	
	private String userEmail;

	private String userPhone;

	public AuthToken() {

	}

	public AuthToken(String token, String userUuid, LocalDateTime createdAt, String expireInSeconds, Boolean isCreatedByAdmin,  
			Boolean isUserVerified, boolean doesVerificationFlowRequired, String userEmail, String userPhone) {
		this.token = token;
		this.userUuid = userUuid;
		this.createdAt = createdAt;
		this.expireInSeconds = expireInSeconds;
		this.isCreatedByAdmin = isCreatedByAdmin;
		this.isUserVerified = isUserVerified;
		this.doesVerificationFlowRequired = doesVerificationFlowRequired;
		this.userEmail = userEmail;
		this.userPhone = userPhone;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUserUuid() {
		return userUuid;
	}

	public void setUserUuid(String userUuid) {
		this.userUuid = userUuid;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getExpireInSeconds() {
		return expireInSeconds;
	}

	public void setExpireInSeconds(String expireInSeconds) {
		this.expireInSeconds = expireInSeconds;
	}

	public Boolean getIsCreatedByAdmin() {
		return isCreatedByAdmin;
	}

	public void setIsCreatedByAdmin(Boolean isCreatedByAdmin) {
		this.isCreatedByAdmin = isCreatedByAdmin;
	}

	public Boolean getIsUserVerified() {
		return isUserVerified;
	}

	public void setIsUserVerified(Boolean isUserVerified) {
		this.isUserVerified = isUserVerified;
	}

	public Boolean getDoesVerificationFlowRequired() {
		return doesVerificationFlowRequired;
	}

	public void setDoesVerificationFlowRequired(Boolean doesVerificationFlowRequired) {
		this.doesVerificationFlowRequired = doesVerificationFlowRequired;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	
}
