package com.nidhi.cms.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

	@Value("${otp.expire.minutes}")
	private String otpExpireMinutes;

	@Value("${token.secret.key}")
	private String tokenSecretKey;

	@Value("${token.validity.seconds}")
	private String tokenValiditySeconds;

	@Value("${text.local.api.key}")
	private String textLocalApiKey;

	@Value("${text.local.api.sender}")
	private String textLocalApiSender;

	public String getOtpExpireMinutes() {
		return otpExpireMinutes;
	}

	public void setOtpExpireMinutes(String otpExpireMinutes) {
		this.otpExpireMinutes = otpExpireMinutes;
	}

	public String getTokenSecretKey() {
		return tokenSecretKey;
	}

	public void setTokenSecretKey(String tokenSecretKey) {
		this.tokenSecretKey = tokenSecretKey;
	}

	public String getTokenValiditySeconds() {
		return tokenValiditySeconds;
	}

	public void setTokenValiditySeconds(String tokenValiditySeconds) {
		this.tokenValiditySeconds = tokenValiditySeconds;
	}

	public String getTextLocalApiKey() {
		return textLocalApiKey;
	}

	public void setTextLocalApiKey(String textLocalApiKey) {
		this.textLocalApiKey = textLocalApiKey;
	}

	public String getTextLocalApiSender() {
		return textLocalApiSender;
	}

	public void setTextLocalApiSender(String textLocalApiSender) {
		this.textLocalApiSender = textLocalApiSender;
	}

}
