package com.nidhi.cms.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.nidhi.cms.constants.enums.RoleEum;
import com.nidhi.cms.domain.Role;
import com.nidhi.cms.domain.UpiRegistrationDetail;
import com.nidhi.cms.domain.User;
import com.nidhi.cms.modal.request.IndsIndRequestModal;
import com.nidhi.cms.modal.request.UserCreateModal;
import com.nidhi.cms.modal.request.indusind.PayeeType;
import com.nidhi.cms.modal.request.indusind.RequestInfo;
import com.nidhi.cms.modal.request.indusind.UpiAddressValidateReqModel;
import com.nidhi.cms.modal.response.ErrorResponse;
import com.nidhi.cms.modal.response.TextLocalResponseModal;
import com.nidhi.cms.utils.indsind.UPISecurity;

/**
 * 
 *
 * @author Ankur Bansala
 */

public class Utility {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Utility.class);


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

	public static String sendAndGetMobileOTP(String textLocalApiKey, String textLocalApiSender, String expireMin, String mobileNumber) {
		String mobileOtp = getRandomNumberString();
		try {
			WebClient webClient = WebClient.create("https://api.textlocal.in");
			WebClient.ResponseSpec responseSpec = webClient.get().uri(uriBuilder -> uriBuilder.path("/send")
					.queryParam("apikey", textLocalApiKey).queryParam("sender", textLocalApiSender)
					.queryParam("numbers", mobileNumber)
					.queryParam("message", "Use the OTP " + mobileOtp
							+ " to verify your NidhiCMS account . This is valid for "+ expireMin.trim() +" minutes.%nDO NOT SHARE OTP WITH ANYONE. Regards NidhiCms")
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

	public static String createJsonRequestAsString(Object clazz) throws IllegalArgumentException, IllegalAccessException {
		String request = "{";
			Class<? extends Object> cls = clazz.getClass();
			Field[] fields = cls.getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				if (field.getName().equals("merchantId") || field.getName().equals("fee") || field.getName().equals("feeType")) {
					System.out.println(" skipped == " +field.getName());
					continue;
				}
				if (field.getName().equals("remarks") && field.get(clazz) == null) {
					System.out.println(" field.get(clazz) === " +field.get(clazz));
					continue;
				}
				request = request + "\"" + field.getName().toUpperCase() + "\"" + ":";
				if (field.get(clazz) != null) {
					request = request + "\"" + field.get(clazz) + "\"" + ",";
				}
			}
			request = StringUtils.removeEnd(request, ",");
			request = request + "}";
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
		if (ip.equals(remoteIpAddress)) {
			return true;
		}
		return getClientIpAddress(ip, httpServletRequest);
	}

	private static final String[] HEADERS_TO_TRY = { "X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP",
			"HTTP_X_FORWARDED_FOR", "HTTP_X_FORWARDED", "HTTP_X_CLUSTER_CLIENT_IP", "HTTP_CLIENT_IP",
			"HTTP_FORWARDED_FOR", "HTTP_FORWARDED", "HTTP_VIA", "REMOTE_ADDR" };

	private static boolean getClientIpAddress(String ip2, HttpServletRequest request) {
		for (String header : HEADERS_TO_TRY) {
			String ip = request.getHeader(header);
			if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip) && ip.equalsIgnoreCase(ip2)) {
				return true;
			}
		}

		return false;
	}

	public static String getVirtualId() {
		//return RandomStringUtils.random(15, false, true);
		return "ZNIDCMS" + String.format("%08d", 1);
	}

	public static String getEncyptedReqBody(@Valid IndsIndRequestModal indsIndRequestModal, String indBankKey) throws Exception {
		UPISecurity uPISecurity = new UPISecurity();
		Gson gson = new Gson();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("pgMerchantId", indsIndRequestModal.getPgMerchantId());
		jsonObject.put("requestMsg", uPISecurity.encrypt(gson.toJson(indsIndRequestModal), indBankKey));
		return jsonObject.toString();
	}

	public static String decryptResponse(String encBodyContent, String responseKey, String indBankKey) throws  Exception {
		JSONObject json = new JSONObject(encBodyContent);
		UPISecurity uPISecurity = new UPISecurity();
		if (json.has(responseKey)) {
			return uPISecurity.decrypt(json.getString(responseKey), indBankKey);
		}
		return null;
	}

	public static LocalDateTime getDateTime(String dateTimeString) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"); 
			LocalDateTime dateAndTime = LocalDateTime.parse(dateTimeString, formatter);
			LOGGER.info("parsed dateAndTime --- {}", dateAndTime);
			return dateAndTime;
		} catch (Exception e) {
			LOGGER.error("Error ocurred during parsing localDatetime while geting response from credit amount --- {}", e);
		}
		return null;
	}
	
	public static LocalDateTime getDateTime(String dateTimeString, String format ) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format); 
			LocalDateTime dateAndTime = LocalDateTime.parse(dateTimeString, formatter);
			LOGGER.info("parsed dateAndTime --- {}", dateAndTime);
			return dateAndTime;
		} catch (Exception e) {
			LOGGER.error("Error ocurred during parsing localDatetime while geting response from credit amount --- {}", e);
		}
		return null;
	}

	public static LocalDate getLocalDateFromDateTime(String dateTimeString) {
		LocalDateTime localDateTime = getDateTime(dateTimeString);
		if (localDateTime == null) {
			return null;
		}
		return localDateTime.toLocalDate();
	}
	
	public static User getUser(UserCreateModal userCreateModal) {
		User user = new User();
		user.setIsUserCreatedByAdmin(false);
		user.setUserEmail(userCreateModal.getUserEmail());
		user.setMobileNumber(userCreateModal.getMobileNumber());
		user.setFullName(userCreateModal.getFullName());
		return user;
	}

	public static String getEncyptedReqBodyForUpiAddressValidation(String upiAddress, String indBankKey) throws Exception {
		UPISecurity uPISecurity = new UPISecurity();
		Gson gson = new Gson();
		JSONObject jsonObject = new JSONObject();
		
		UpiAddressValidateReqModel upiAddressValidateReqModel = new UpiAddressValidateReqModel();
		upiAddressValidateReqModel.setPayeeType(new PayeeType(upiAddress.toLowerCase()));
		upiAddressValidateReqModel.setvAReqType("R");
		upiAddressValidateReqModel.setRequestInfo(new RequestInfo("INDB000000003196", RandomStringUtils.random(30, true, false)));
		
		jsonObject.put("requestMsg", uPISecurity.encrypt(gson.toJson(upiAddressValidateReqModel), indBankKey));
		jsonObject.put("pgMerchantId", "INDB000000003196");
		return jsonObject.toString();
	}

	public static  <T> T getJavaObject(String jsonObjectAsString, Class<T> clazz) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(jsonObjectAsString, clazz);
		} catch (Exception e) {
			LOGGER.error("Error ocurred during getJavaObject --- {}", e);
		}
		return null;
	}
	
	public static JSONObject getJsonFromString(String jsonString) {
		return new JSONObject(jsonString);
	}

}
