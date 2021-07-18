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

	public AuthToken() {

	}

	public AuthToken(String token, String userUuid, LocalDateTime createdAt, String expireInSeconds) {
		this.token = token;
		this.userUuid = userUuid;
		this.createdAt = createdAt;
		this.expireInSeconds = expireInSeconds;
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
}
