package com.nidhi.cms.utils;

import java.util.Collections;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;

import com.nidhi.cms.constants.enums.RoleEum;
import com.nidhi.cms.domain.Role;
import com.nidhi.cms.modal.response.TextLocalResponseModal;

/**
 * 
 *
 * @author Ankur Bansala
 */

public class Utility {

	private Utility() {
		// Utility
	}

	public static final String getUniqueUuid() {
		return UUID.randomUUID().toString();
	}

	public static String getRandomNumberString() {
		return String.format("%06d", new Random().nextInt(999999));
	}

	public static Set<Role> getRole(RoleEum roleEum) {
		Role role = new Role();
		role.setName(roleEum);
		return Collections.singleton(role);
	}

	public static String sendAndGetMobileOTP(String textLocalApiKey, String textLocalApiSender, String mobileNumber) {
		String mobileOtp = getRandomNumberString();
		try {
			WebClient webClient = WebClient.create("https://api.textlocal.in");
			WebClient.ResponseSpec responseSpec = webClient.get().uri(uriBuilder -> uriBuilder.path("/send")
					.queryParam("apikey", textLocalApiKey)
					.queryParam("sender", textLocalApiSender)
					.queryParam("numbers", mobileNumber)
					.queryParam("message", "Use the OTP " + mobileOtp
							+ " to verify your NidhiCMS account . This is valid for 30 minutes.%nDO NOT SHARE OTP WITH ANYONE. Regards NidhiCms")
					.build()).retrieve();
			TextLocalResponseModal responseBody = responseSpec.bodyToMono(TextLocalResponseModal.class).block();
			if (responseBody.getStatus().equals("success")) {
				return mobileOtp;
			}
		} catch (Exception e) {
			return StringUtils.EMPTY;
		}
		return StringUtils.EMPTY;
	}

}
