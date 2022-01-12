package com.nidhi.cms.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

	@Value("${inds.ind.bank.key}")
	private String indBankKey;

	@Value("${X-IBM-CLIENT-ID}")
	private String xIBMClientId;

	@Value("${X-IBM-CLIENT-SECRET}")
	private String xIBMClientSecret;

	public static Map<String, String> loggedInUsers = new ConcurrentHashMap<>();

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

	public String getIndBankKey() {
		return indBankKey;
	}

	public String getxIBMClientId() {
		return xIBMClientId;
	}

	public String getxIBMClientSecret() {
		return xIBMClientSecret;
	}

}
