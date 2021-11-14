package com.nidhi.cms.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import com.nidhi.cms.constants.enums.RoleEum;
import com.nidhi.cms.domain.Role;
import com.nidhi.cms.domain.User;
import com.nidhi.cms.modal.request.UserTxWoOtpReqModal;
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
		if (mobileOtp != null) {
			return mobileOtp;
		}
		try {
			WebClient webClient = WebClient.create("https://api.textlocal.in");
			WebClient.ResponseSpec responseSpec = webClient.get().uri(uriBuilder -> uriBuilder.path("/send")
					.queryParam("apikey", textLocalApiKey).queryParam("sender", textLocalApiSender)
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

	public static File convertMultipartFileToFile(MultipartFile profilePic) {
		final File convFile = new File(
				System.getProperty("java.io.tmpdir") + File.separator + profilePic.getOriginalFilename());
		try (final FileOutputStream fos = new FileOutputStream(convFile)) {
			fos.write(profilePic.getBytes());
			return convFile;
		} catch (final Exception e) {
			return null;
		}
	}

	public static LocalDate stringToLocalDate(String localDate) {
		return asLocalDate(getDateFromString(localDate, "dd-MM-yyyy"));
	}

	private static LocalDate asLocalDate(Date dateFromString) {
		if (dateFromString == null) {
			return null;
		}
		return dateFromString.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public static Date getDateFromString(final String date, final String format) {
		if (date == null) {
			return null;
		}

		if (date.length() > 0) {
			SimpleDateFormat formatter = new SimpleDateFormat(format);
			try {
				return formatter.parse(date);
			} catch (final ParseException parseEx) {
			}
		}
		return null;
	}

	public static String createJsonRequestAsString(Object clazz) {
		String request = "{";
		try {
			 Class<? extends Object> cls = clazz.getClass();
			Field[] fields = cls.getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				request = request + "\"" + field.getName().toUpperCase() +"\"" + ":";
				if (field.get(clazz) != null) {
					request = request + "\"" + field.get(clazz) +"\"" + ",";
				}
			}
			request = StringUtils.removeEnd(request, ",");
			request = request + "}";
		} catch (Exception e) {
			System.out.println(e);
		}
		return request;
	}
	
	public static boolean getRemoteIpAddress(User user, final HttpServletRequest httpServletRequest) {
		String ip = user.getWhiteListIp();
		if (ip == null) {
			return false;
		}
		String remoteIpAddress = httpServletRequest.getHeader("X-FORWARDED-FOR");

		if (ip.equals(remoteIpAddress)) {
			return true;
		}
		remoteIpAddress = httpServletRequest.getHeader("X-REAL-IP");
		if (ip.equals(remoteIpAddress)) {
			return true;
		}
		remoteIpAddress = httpServletRequest.getRemoteAddr();
		if(ip.equals(remoteIpAddress)) {
			return true;
		}
		return getClientIpAddress(ip, httpServletRequest);
	}
	
	private static final String[] HEADERS_TO_TRY = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR" };

private static boolean getClientIpAddress(String ip2, HttpServletRequest request) {
    for (String header : HEADERS_TO_TRY) {
        String ip = request.getHeader(header);
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip) && ip.equalsIgnoreCase(ip2)) {
            	 return true;
        }
    }

    return false;
}


}
