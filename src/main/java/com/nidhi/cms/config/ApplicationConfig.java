package com.nidhi.cms.config;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.nidhi.cms.constants.enums.SystemKey;
import com.nidhi.cms.domain.SystemConfig;
import com.nidhi.cms.repository.SystemConfigRepo;

@Configuration
public class ApplicationConfig {
	
	@Autowired
	private SystemConfigRepo systemConfigRepo;

	@Value("${token.secret.key}")
	private String tokenSecretKey;

	@Value("${token.validity.seconds}")
	private String tokenValiditySeconds;

	public static Map<String, String> loggedInUsers = new ConcurrentHashMap<>();


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

	@PostConstruct
	public void addSystemKeys() {
		
		List<SystemConfig> systemConfigs = systemConfigRepo.findAll();
		if (CollectionUtils.isEmpty(systemConfigs)) {
			saveSystemProperties();
		} 
	}

	private void saveSystemProperties() {
		for (SystemKey systemKey : SystemKey.values()) { 
			SystemConfig systemConfig = new SystemConfig();
			systemConfig.setSystemKey(systemKey.name());
			
			if (systemKey.equals(SystemKey.LIST_API_LAST_RUN_TIME)) {
				continue;
			}
			
			if (systemKey.equals(SystemKey.INDS_IND_BANK_KEY)) {
				systemConfig.setValue("c0ff5a0e2000a62951400b3489fc41f2");
			}
			
			if (systemKey.equals(SystemKey.OTP_EXPIRE_MINUTES)) {
				systemConfig.setValue("5");
			}
			if (systemKey.equals(SystemKey.TEXT_LOCAL_API_KEY)) {
				systemConfig.setValue("U78FiH4n8Z0-bK7Q0sR5WE6m0zIkF0yslVExmFac8d");
			}
			if (systemKey.equals(SystemKey.TEXT_LOCAL_API_SENDER)) {
				systemConfig.setValue("NIDCMS");
			}
			if (systemKey.equals(SystemKey.X_IBM_CLIENT_ID)) {
				systemConfig.setValue("8d7a4330-57fb-4d66-853d-32fd27a8f32f");
			}
			if (systemKey.equals(SystemKey.X_IBM_CLIENT_SECRET)) {
				systemConfig.setValue("Q2kJ6hQ6gO8cC0xR1eY6uN4xC2aC5oW5bC4oB4lH3qP5vY7sJ0");
			}
			if (systemKey.equals(SystemKey.INDUS_PGMERCHANTID)) {
				systemConfig.setValue("INDB000001862820");
			}
			systemConfigRepo.save(systemConfig);
		}
		
	}
	
}
